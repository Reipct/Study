#include <time.h>
#include <cuda_runtime.h>
#include <cuda.h>
#include <math.h>
#include <cuda_runtime.h>

#define div_round_up(a, b) ((a % b == 0)? a / b : a / b + 1)   //取整
#define WARP 32
#define OMEGA 32
#define BLOCK_SIZE 256
#define SPMV_TRI_NUM  50

unsigned int flp2(unsigned int x)
{
	x |= (x >> 1);
	x |= (x >> 2);
	x |= (x >> 4);
	x |= (x >> 8);
	x |= (x >> 16);
	return x - (x >> 1);
}

__forceinline__ __device__
int sum_32_shfl(int sum)
{

	for (int mask = WARP / 2; mask > 0; mask >>= 1)
		sum += __shfl_xor_sync(0xffffffff,sum, mask);
	return sum;
}

__inline__ __device__
int binary_search_right_boundary_kernel(const int* d_row_pointer,
	const int  key_input,
	const int  size)
{
	int start = 0;
	int stop = size - 1;
	int median;
	int key_median;

	while (stop >= start)
	{
		median = (stop + start) / 2;

		key_median = d_row_pointer[median];
		if (key_input >= key_median)
			start = median + 1;
		else if (key_input < key_median)
			stop = median - 1;
	}
	return start;
}

// 定义并行扫描函数
__forceinline__ __device__
double scan_32_shfl(double x, const int local_id)
{
	double y = __shfl_up_sync(0xffffffff, x, 1);
	x = local_id >= 1 ? x + y : x;
	y = __shfl_up_sync(0xffffffff, x, 2);
	x = local_id >= 2 ? x + y : x;
	y = __shfl_up_sync(0xffffffff, x, 4);
	x = local_id >= 4 ? x + y : x;
	y = __shfl_up_sync(0xffffffff, x, 8);
	x = local_id >= 8 ? x + y : x;
	y = __shfl_up_sync(0xffffffff, x, 16);
	x = local_id >= 16 ? x + y : x;

	return x;
}

__forceinline__ __device__
double segmented_sum_shfl(double tmp_sum,
	const int scansum_offset,
	const int lane_id)
{
	//double sum = __shfl_down(tmp_sum, 0);   //取sum右边一位
	double sum = tmp_sum;
	sum = lane_id == OMEGA - 1 ? 0 : sum;    //最后一位置0
	double scan_sum = scan_32_shfl(sum, lane_id);   //对sum求前缀和
	tmp_sum = __shfl_down_sync(0xffffffff, scan_sum, scansum_offset);   //取scan_sum的右边第scansum_offset位
	tmp_sum = tmp_sum - scan_sum + sum;

	return tmp_sum;
}


__global__ void prefix_sum_kernel(const int* input, int* output)
{
	const int tid = threadIdx.x;
	const int gid = blockIdx.x * blockDim.x + tid;

	// 将向量中的每个元素进行并行扫描
	const int x = input[gid];
	const int y = scan_32_shfl(x, tid);

	output[gid] = y;

}

__global__ void generate_bit_flag(int* mptr, int m, int sigma, int* bit_flag, int nnz,int p)
{
	int thread_id = blockIdx.x * blockDim.x + threadIdx.x;

	if (thread_id >= m) {      //一个线程处理一行
		return;
	}

	int tile_start = mptr[thread_id];

	bit_flag[tile_start] = 1;

	int tile_first = thread_id * sigma * OMEGA;

	for (int i = tile_first; i < nnz; i += m * sigma * OMEGA) {
		bit_flag[i] = 1;
	}
	/*while (tile_first < nnz) {
		bit_flag[tile_first] = 1;
		tile_first += m * sigma * OMEGA;
	}*/
}

__global__ void generate_partition_pointer(int* mptr, int* tileptr,
	int sigma, int m, int nnz, int p)
{
	int thread_id = blockIdx.x * blockDim.x + threadIdx.x;
	int boundary = thread_id * sigma * OMEGA;
	//第thread_id个tile的起始位置，找到这个位置对应原矩阵的第几行
	boundary = boundary > nnz ? nnz : boundary;  //考虑最后一块

	// binary search
	if (thread_id <= p) {
		int start = 0, stop = m;
		int mid, key_mid;

		while (stop >= start)
		{
			mid = (stop + start) / 2;
			key_mid = mptr[mid];
			if (boundary >= key_mid)
				start = mid + 1;
			else
				stop = mid - 1;
		}
		tileptr[thread_id] = start - 1;
	}
	//if (thread_id > 4280&&thread_id<4300) printf("tileptr[%d]===%d\n", thread_id, tileptr[thread_id]);
}

__global__ void generate_partition_pointer_s2(int* mptr, int* tileptr,int p)
{
	int thread_id = blockIdx.x * blockDim.x + threadIdx.x;

	if (thread_id >= p)
		return;
	//一个线程处理一个tile
	for (int j = tileptr[thread_id]; j < tileptr[thread_id + 1]; j++)
	{
		if (mptr[j] == mptr[j + 1])
		{
			if (thread_id == 0)
				tileptr[thread_id] = -1;
			else
				tileptr[thread_id] = -tileptr[thread_id];
			break;
		}
	}
	//if (thread_id > 4280&&thread_id<4300) printf("tileptr[%d]===%d\n", thread_id, tileptr[thread_id]);
}

__global__ void generate_partition_descriptor(int* mptr, int* bit_flag,
	int* y_offset, int* seg_offset, int* temp_bit, int n, int p, int sigma)
{
	int thread_id = blockIdx.x * blockDim.x + threadIdx.x;
	int start = thread_id / OMEGA;   //一个warp处理一个tile,连续的32*19个数
	int lane_id = thread_id & (OMEGA - 1);

	int size = OMEGA * sigma;
	int scansum = 0;

	if (start >= p)
		return;
	int sum = y_offset[start * OMEGA + lane_id];   //第几行(一个warp32行)
	int tile_startrow = start * size + lane_id * sigma;

	for (int j = 0; j < sigma; j++)   //每个线程遍历sigma列
	{
		sum += bit_flag[tile_startrow + j];
		temp_bit[start * OMEGA + lane_id] |= bit_flag[tile_startrow + j];
	}

	//step1.当前行存在T，记为0，不存在T记为1
	//cpu--->step2.遍历32行，累加原来的0位置到下一个0之间的1，将原来1位置重置为0
	//同一个warp是同步的？？？
	seg_offset[start * OMEGA + lane_id] = 1 - temp_bit[start * OMEGA + lane_id];

	y_offset[start * OMEGA + lane_id] = sum;
	//scansum = scan_32_shfl(sum, lane_id);

	for (int j = lane_id - 1; j >= 0; j--) {
		scansum += y_offset[start * OMEGA + j];
	}
	y_offset[start * OMEGA + lane_id] = scansum;

	if (lane_id == 0)
		y_offset[start * OMEGA + lane_id] = 0;

	//if (thread_id < 4285*32&&thread_id>4282*32) printf("after tile_startrow%d  ==%d y_offset[%d]  ==%d \n", thread_id, tile_startrow, thread_id, y_offset[start * OMEGA + lane_id]);
}

__global__ void generate_partition_descriptor_step2(int* seg_offset, int p)
{
	int thread_id = blockIdx.x * blockDim.x + threadIdx.x;
	//一个线程处理一个块
	if (thread_id >= p)
		return;

	int start_row = thread_id * OMEGA;
	int sum = 0, j = 0;
	/*while (i < OMEGA) {
		if (seg_offset[start_row + i] == 0) {
			for (j = i + 1; j < 32; j++) {
				sum += seg_offset[start_row + j];
				seg_offset[start_row + j] = 0;
			}
		}
		seg_offset[start_row + i] = sum;
		i = j;
	}*/
	for (int i = 0; i < OMEGA; i++) {
		if (seg_offset[start_row + i] == 0) {
			j = i + 1;
			sum = 0;
			while (seg_offset[start_row + j] == 1 && j < OMEGA) {
				sum += seg_offset[start_row + j];
				j++;
			}
			seg_offset[start_row + i] = sum;
		}
		else seg_offset[start_row + i] = 0;
	}
}

__global__ void transpose_matrix_v2(int sigma, int p, int size, int* bit_flag, double* aData, int* aIndex)
{
	int thread_id = blockIdx.x * blockDim.x + threadIdx.x;
	int warp_id = thread_id / WARP;   //一个warp处理一个tile
	int lane_id = thread_id & (WARP - 1);    //束内索引

	if (warp_id >= p - 1)
		return;
	int start = warp_id * size;

	__shared__ double check[1024];

	/*if (thread_id == 0) {
		for (int i = 0; i < 19; i++) {
			printf("before bit_flag trans  %d\n",  bit_flag[start + i]);
		}
	}*/

	//一个warp转置一个tile

	for (int j = 0; j < sigma; j++) {
		check[j * OMEGA + lane_id] = bit_flag[start + lane_id * sigma + j];
	}

	for (int i = lane_id; i < size; i += WARP) {
		bit_flag[start + i] = check[i];
	}

	for (int j = 0; j < sigma; j++) {
		check[j * OMEGA + lane_id] = aData[start + lane_id * sigma + j];
	}

	for (int i = lane_id; i < size; i += WARP) {
		aData[start + i] = check[i];
	}

	for (int j = 0; j < sigma; j++) {
		check[j * OMEGA + lane_id] = aIndex[start + lane_id * sigma + j];
	}

	for (int i = lane_id; i < size; i += WARP) {
		aIndex[start + i] = check[i];
	}

	/*if (thread_id == 1) {
		for (int i = 0; i < 19; i++) {
			printf("after trans flag[%d]====%d\n", i * OMEGA+1 , bit_flag[start + i * OMEGA+1]);
		}
	}*/
}

__device__ double atomicAdd_double(double* address, double val) {
	unsigned long long int* address_as_ull =
		(unsigned long long int*)address;
	unsigned long long int old = *address_as_ull, assumed;

	do {
		assumed = old;
		old = atomicCAS(address_as_ull, assumed,
			__double_as_longlong(val + __longlong_as_double(assumed)));

	} while (assumed != old);

	return __longlong_as_double(old);
}

__global__ void csr5(int sigma, int* tileptr, int* y_offset, int* seg_offset, int* bit_flag,
	 double* mdata, int* mindex, int* mptr, double* vec, double* res, int m, int p)
{

	int thread_id = blockIdx.x * blockDim.x + threadIdx.x;
	int warp_id = thread_id / WARP;   //一个warp处理一个tile
	int local_warp_id = threadIdx.x / WARP;
	int lane_id = thread_id & (WARP - 1);    //束内索引

	//每个tile有32个temp（一列一个）
	int size = sigma * OMEGA;
	int tile_start_row = tileptr[warp_id];    //tile的起始行，一个块有32列
	int tile_nextstartrow = tileptr[warp_id + 1];
	int num_rows = abs(tile_nextstartrow) - abs(tile_start_row);
	int is_empty = tile_start_row;
	int i = lane_id;   //每个线程处理一列
	//if (thread_id == 0) {
	//	/*for (int i = 32*32; i < 32*33; i++) {
	//		printf("after y_offset[%d]===%d  seg_offset[%d]==%d bit_flag[%d]==%d \n", i, y_offset[i],i,seg_offset[i]);
	//	}*/
	//	printf("tile startrow==%d  is_empty====%d \n", tileptr[warp_id],is_empty);
	//	printf("tileptr====%d\n", tileptr[0]);
	//}

	double sum = 0;
	int flag = 0;
	int red = 0;
	__shared__ int dynamicOffset[8];
	__shared__ double temp[256];
	__shared__ double last_temp[256];

	temp[threadIdx.x] = 0;
	last_temp[threadIdx.x] = 0;

	if (lane_id == 0) {
		dynamicOffset[local_warp_id] = 0;
	}

	if (warp_id == 0 && tile_start_row == -1) {
		tile_start_row = 0;
	}
	/*if (thread_id == 0||thread_id==1*32||thread_id==2*32||thread_id==7*32)
		printf("is_empty==%d  tile_start_row==%d\n", is_empty, tile_start_row);*/

	if (warp_id < p - 1)   //不是最后一块
	{
		if (tile_start_row == tile_nextstartrow) 
		{
			//------csr-vector---todo    一整个块是一行
			double sum = 0;
			for (int j = 0; j < sigma; j++)
			{
				int ptr = lane_id + j * OMEGA + size * warp_id;   //遍历第i列的第j行
				sum += mdata[ptr] * vec[mindex[ptr]];
			}

			for (int mask = WARP / 2; mask >= 1; mask /= 2) {    //线程束规约
		        sum += __shfl_xor_sync(0xffffffff, sum, mask);
	        }
			if (lane_id == 0)
				atomicAdd_double(res + abs(tile_start_row), sum);
		}
		//首先判断该块是否有空行,如果有空行使用empty_offset，否则直接使用y_offset
		//tile_start_row=0
		else if (is_empty >=0)
		{

			for (int j = 0; j < sigma; j++)    //每个线程遍历一列
			{
				int ptr = i + j * OMEGA + size * warp_id;   //遍历第i列的第j行

				if (bit_flag[ptr] == 1 && flag == 0) {
					if (j == 0) {   //第一行为1
						flag++;
					}
					else {   //第一行为0，红色
						temp[threadIdx.x - 1] = sum;
						sum = 0;
						flag++;
					}

				}
				else if (bit_flag[ptr] == 1 && flag >= 1) {
					if (lane_id == 0 && flag == 1) {   //第一列的第一个绿色
						atomicAdd_double(res + tile_start_row + y_offset[warp_id * OMEGA + i], sum);
					}
					else {
						res[tile_start_row + y_offset[warp_id * OMEGA + i]] = sum;
					}
					y_offset[warp_id * OMEGA + i] += 1;
					sum = 0;
					flag++;
				}
				sum += mdata[ptr] * vec[mindex[ptr]];
			}

			if (flag == 0) {   //遍历一列全是0，红色
				temp[threadIdx.x - 1] = sum;
				red = 1;
			}
			else {   //从上一个1到末尾全是0，蓝色
				last_temp[threadIdx.x] = sum;
			}

			__syncthreads();
			//根据seg_offset对于temp求前缀和
			double segsum = temp[threadIdx.x];   //取出一个warp所有temp

			int scansum_offset = seg_offset[warp_id * OMEGA + lane_id];
			segsum = segmented_sum_shfl(segsum, scansum_offset, lane_id);

			/*if (thread_id >= 32 * 256 - 4 && thread_id < 32 * 257)
				printf("after %d segsum===%lf last_temp===%lf\n", thread_id, segsum, last_temp[thread_id]);*/

			//last_temp+temp
			segsum += last_temp[threadIdx.x];
			int tid = tile_start_row + y_offset[warp_id * OMEGA + lane_id];
			
			if (red == 0) {    //最后一个非红色需要原子加
				atomicAdd_double(res + tid, segsum);
			}

			/*if (thread_id >= 4282*32 && thread_id < 4286*32)
				printf("after after flag==%d red===%d %d %d segsum===%lf last_temp===%lf   res[%d]====%lf\n", flag,red,warp_id, lane_id, segsum, last_temp[thread_id], tid,res[tid]);*/
		}
		else if (is_empty < 0) //块内有空行
		{
			__shared__ int empty_offset[2048];
			int length = 0;
			int empty_offset_start=0;
			for (int j = 0; j < sigma; j++)    //每个线程都执行for
			{
				int ptr = i + j * OMEGA + size * warp_id;   //遍历第i列的第j行
				if (bit_flag[ptr] == 1) 
					length++;
			}

            //同一个warp的length相加 
			for (int mask = WARP / 2; mask > 0; mask >>= 1)
				length += __shfl_xor_sync(0xffffffff, length, mask);

			if (lane_id == 0) {   //一个线程块中，每个warp在共享内存中的起始偏移
				dynamicOffset[local_warp_id] = length;
			}
			__syncthreads();
			for (int s = local_warp_id-1; s >= 0; s--) {
				empty_offset_start += dynamicOffset[s];
			}

			//广播给同一warp内所有线程
			//empty_offset_start = __shfl_sync(0xffffffff, empty_offset_start, 0);

			//一个warp有一个length
			for (int k = lane_id; k < length; k += WARP) {
				empty_offset[empty_offset_start+k] = 0;
			}

			if (lane_id == 0)   //第一个线程计算empty_offset
			{
				int eid = 0;
				int idx = 0;
				for (int k = 0; k < OMEGA; k++)
				{
					for (int j = 0; j < sigma; j++)    //每个线程都执行for
					{
						int transptr = k + j * OMEGA + size * warp_id;   //遍历第i列的第j行
						int ptr = j + k * sigma + size * warp_id;
						if (bit_flag[transptr] == 1) {
							idx = binary_search_right_boundary_kernel(mptr+ abs(tile_start_row), ptr, num_rows+2)+abs(tile_start_row) - 1;
							empty_offset[empty_offset_start + eid] = idx - abs(tile_start_row);
							//if(warp_id==1)
							////printf("ptr==%d bit_flag[%d][%d]==%d  idx-tileptr==%d-%d==empty_offset==%d\n",ptr, j,k, bit_flag[ptr],idx,abs(tileptr[warp_id]),idx-abs(tileptr[warp_id]));
							//printf("[%d][%d] tile_ptr[]==%d empty_offset[%d]==%d ptr==%d  idx==%d  eid==%d\n", j, k, empty_offset[empty_offset_start + eid]+abs(tileptr[warp_id]),empty_offset_start + eid, empty_offset[empty_offset_start + eid], ptr, idx, eid);
							eid++;
				        }
			        }
				}
			}

			__syncthreads();

			for (int j = 0; j < sigma; j++)    //每个线程都执行for
			{
				int ptr = i + j * OMEGA + size * warp_id;   //遍历第i列的第j行

				if (bit_flag[ptr] == 1 && flag == 0) {
					if (bit_flag[i + size * warp_id] == 1) {   //第一行为1
						flag++;
					}
					else {   //第一行为0，红色
						temp[threadIdx.x - 1] = sum;
						sum = 0;
						flag++;
					}

				}
				else if (bit_flag[ptr] == 1 && flag >= 1) {
					if (lane_id == 0 && flag == 1) {
						atomicAdd_double(res + abs(tile_start_row) + empty_offset[empty_offset_start+y_offset[warp_id * OMEGA + i]], sum);
					}
					else {
						res[abs(tile_start_row) + empty_offset[empty_offset_start+y_offset[warp_id * OMEGA + i]]] = sum;
					}
					y_offset[warp_id * OMEGA + i]+= 1;
					sum = 0;
					flag++;
				}
				sum += mdata[ptr] * vec[mindex[ptr]];
			}

			if (flag == 0) {   //遍历一列全是0，红色
				temp[threadIdx.x - 1] = sum;
				red = 1;
			}
			else {   //从上一个1到末尾全是0，蓝色
				last_temp[threadIdx.x] = sum;
			}

			__syncthreads();
			//根据seg_offset对于temp求前缀和
			double segsum = temp[threadIdx.x];   //取出一个warp所有temp

			int scansum_offset = seg_offset[warp_id * OMEGA + lane_id];
			segsum = segmented_sum_shfl(segsum, scansum_offset, lane_id);

			int tid = abs(tile_start_row) + empty_offset[empty_offset_start + y_offset[warp_id * OMEGA + lane_id]];
			/*if (warp_id==1)
				printf("before lane_id==%d red==%d y_offset===%d seg_offset===%d tid===%d segsum===%lf last_temp===%lf\n", lane_id,red,y_offset[warp_id * OMEGA + lane_id], seg_offset[warp_id * OMEGA + lane_id],tid, segsum, last_temp[thread_id]);*/
				//last_temp+temp
			segsum += last_temp[threadIdx.x];

			/*if (warp_id==1)
				printf("after lane_id==%d red==%d y_offset===%d seg_offset===%d tid===%d segsum===%lf last_temp===%lf\n", lane_id,red,y_offset[warp_id * OMEGA + lane_id], seg_offset[warp_id * OMEGA + lane_id],tid,segsum, last_temp[thread_id]);*/
			
			if (red == 0) {    //最后一个非红色需要原子加
				atomicAdd_double(res + tid, segsum);
			}

			/*if (warp_id == 0||warp_id==1) {
				printf("%d  y_offset==%d seg_offset==%d res[%d]==%lf\n", warp_id, y_offset[lane_id], seg_offset[lane_id], lane_id, res[lane_id]);
			}*/
		}
	}
	else if (warp_id == p - 1)    //最后一个块，起始行是tile_start_row
	{
		int startRow = abs(tile_start_row) + lane_id;   //一个线程算一行
		int flag = 0;
		int startPoint;
		int nextstartPoint;

		while (startRow < m) {   //每个线程算一行
			double sum = 0;
			if (lane_id == 0 && flag == 0)
				startPoint = (p - 1) * sigma * OMEGA;
			else  startPoint = mptr[startRow];

			nextstartPoint = mptr[startRow + 1];
			flag++;
			for (int j = startPoint; j < nextstartPoint; j++) {    //遍历一行中所有列
				sum += mdata[j] * vec[mindex[j]];
			}

			if (lane_id == 0 && flag == 1)
				atomicAdd_double(res + startRow, sum);
			else res[startRow] = sum;

			startRow += WARP;
		}
	}

}
int spmvCSR(CSR_Matrix* A, double* x, double* b) //x是向量，b是结果
{
	cudaEvent_t start, stop;
	float preTime, elapsedTime, postTime;
	float elapsedTime_bitflag, elapsedTime_tileptr, 
		elapsedTime_tiledescriptor, elapsedTime_transpose;
	float ave_msec=0;

	//-------pretime计时开始-----
	cudaEventCreate(&start);
	cudaEventCreate(&stop);
	cudaEventRecord(start, 0);

	double* dev_aData;
	int* dev_aPtr, * dev_aIndex;
	double* dev_x, * dev_b;

	int m = A->nRow;
	int n = A->nCol;
	int nnz = A->nonzeroes;
	int sigma;

	//-----------allocate the memory for matrices and copy values from host to device
	cudaMalloc((void**)&dev_aData, sizeof(double) * A->nonzeroes);
	cudaMalloc((void**)&dev_aIndex, sizeof(int) * A->nonzeroes);
	cudaMalloc((void**)&dev_aPtr, sizeof(int) * (m + 1));

	cudaMemcpyAsync(dev_aData, A->mData, A->nonzeroes * sizeof(double), cudaMemcpyHostToDevice, 0);
	cudaMemcpyAsync(dev_aIndex, A->mIndex, A->nonzeroes * sizeof(int), cudaMemcpyHostToDevice, 0);
	cudaMemcpyAsync(dev_aPtr, A->mPtr, (m + 1) * sizeof(int), cudaMemcpyHostToDevice, 0);

	//----------allocate the memeory for x and b and compy values from host to device
	cudaMalloc((void**)&dev_x, sizeof(double) * n);
	cudaMalloc((void**)&dev_b, sizeof(double) * m);

	cudaMemcpyAsync(dev_x, x, n * sizeof(double), cudaMemcpyHostToDevice, 0);
	cudaMemcpyAsync(dev_b, b, m * sizeof(double), cudaMemcpyHostToDevice, 0);

	int BS = BLOCK_SIZE;
	int nnz_per_row = nnz / m;

	if (nnz_per_row <= 4)
		sigma = 4;
	else if (nnz_per_row > 4 && nnz_per_row <= 32)
		sigma = nnz_per_row;
	else if (nnz_per_row <= 256 && nnz_per_row > 32)
		sigma = 32;
	else // nnz_per_row > t
		sigma = 8;

	int size = sigma * OMEGA;
	//一个block处理一个tile
	int p = ceil((double)nnz / (double)(size));   //向上取整，最后一个块使用csr-vector

	printf("sigma=====%d p=====%d\n", sigma,p);
	int* dev_partition_pointer, * dev_y_offset, * dev_seg_offset, * dev_bit_flag;
	int* dev_temp_bit;
	int* bit_flag, * y_offset, * seg_offset, * tile_ptr;

	bit_flag = (int*)malloc(nnz * sizeof(int));
	y_offset = (int*)malloc(p * OMEGA * sizeof(int));
	seg_offset = (int*)malloc(p * OMEGA * sizeof(int));
	tile_ptr = (int*)malloc((p + 1) * sizeof(int));

	cudaMalloc((void**)&dev_partition_pointer, (p + 1) * sizeof(int));
	cudaMalloc((void**)&dev_y_offset, p * OMEGA * sizeof(int));
	cudaMalloc((void**)&dev_seg_offset, p * OMEGA * sizeof(int));
	cudaMalloc((void**)&dev_bit_flag, nnz * sizeof(int));
	cudaMalloc((void**)&dev_temp_bit, p * OMEGA * sizeof(int));

	cudaMemset(dev_partition_pointer, 0, (p + 1) * sizeof(int));
	cudaMemset(dev_y_offset, 0, p * OMEGA * sizeof(int));
	cudaMemset(dev_seg_offset, 0, p * OMEGA * sizeof(int));
	cudaMemset(dev_bit_flag, 0, nnz * sizeof(int));
	cudaMemset(dev_temp_bit, 0, p * OMEGA * sizeof(int));


	//int GS = div_round_up(m, (BS / nnz_per_row));
	int GS = div_round_up(p, 8);
	int GS2 = div_round_up(m, BS);
	int GS3 = div_round_up(p, BS);

	cudaEventRecord(stop, 0);
	cudaEventSynchronize(stop);

	cudaEventElapsedTime(&preTime, start, stop);
	cudaEventDestroy(start);
	cudaEventDestroy(stop);
	//-------pretime计时结束-----

	//-------generate_bit_flag计时开始-----
	cudaEventCreate(&start);
	cudaEventCreate(&stop);
	cudaDeviceSynchronize();
	cudaEventRecord(start, 0);

	generate_bit_flag << <GS2, BS >> > (dev_aPtr, m, sigma, dev_bit_flag, nnz,p);
	cudaMemcpyAsync(bit_flag, dev_bit_flag, nnz * sizeof(int), cudaMemcpyDeviceToHost, 0);

	cudaEventRecord(stop, 0);
	cudaEventSynchronize(stop);

	cudaEventElapsedTime(&elapsedTime_bitflag, start, stop);

	cudaEventDestroy(start);
	cudaEventDestroy(stop);

	//-------generate_bit_flag计时开始----------


	//-------generate_partition_pointer开始--------
	cudaEventCreate(&start);
	cudaEventCreate(&stop);
	cudaEventRecord(start, 0);
	generate_partition_pointer << <GS, BS >> > (dev_aPtr, dev_partition_pointer, sigma, m, nnz, p);
	generate_partition_pointer_s2 << <GS, BS >> > (dev_aPtr, dev_partition_pointer,p);
	cudaEventRecord(stop, 0);
	cudaEventSynchronize(stop);

	cudaEventElapsedTime(&elapsedTime_tileptr, start, stop);

	cudaEventDestroy(start);
	cudaEventDestroy(stop);
	//--------------generate_partition_pointer结束

	cudaMemcpyAsync(tile_ptr, dev_partition_pointer, (p + 1) * sizeof(int), cudaMemcpyDeviceToHost, 0);
	cudaDeviceSynchronize();

	for (int i = 0; i < p; i++) {
		if (tile_ptr[i] < 0) {
			printf("first empty tile_ptr[%d]====%d  next_tile=%d\n", i, tile_ptr[i], tile_ptr[i+1]);
			break;
		}
	}

	for (int i = 0; i < 20; i++){
		printf("tileptr[%d]===%d\n",i,tile_ptr[i]);
	}

	//-------generate_partition_descriptor开始--------
	cudaEventCreate(&start);
	cudaEventCreate(&stop);
	cudaDeviceSynchronize();
	cudaEventRecord(start, 0);

	generate_partition_descriptor << <GS, BS >> > (dev_aPtr, dev_bit_flag,
		dev_y_offset, dev_seg_offset, dev_temp_bit, m, p, sigma);

	generate_partition_descriptor_step2 << <GS3, BS >> > (dev_seg_offset, p);

	//cudaMemcpyAsync(y_offset, dev_y_offset, p * OMEGA * sizeof(int), cudaMemcpyDeviceToHost, 0);
	//cudaMemcpyAsync(seg_offset, dev_seg_offset, p * OMEGA * sizeof(int), cudaMemcpyDeviceToHost, 0);

	/*printf("seg_offset====\n");
	for (int i = 32*256; i < 32*257; i++) {
		printf("y_offset[%d]==%d   seg_offset[%d]==%d \n", i,y_offset[i],i,seg_offset[i]);
	}
	printf("\n");*/

	cudaEventRecord(stop, 0);
	cudaEventSynchronize(stop);

	cudaEventElapsedTime(&elapsedTime_tiledescriptor, start, stop);
	//cudaMemcpyAsync(b, dev_b, m * sizeof(double), cudaMemcpyDeviceToHost, 0);

	cudaEventDestroy(start);
	cudaEventDestroy(stop);
	//--------------generate_partition_descriptor结束---------


	//--------------transpose_matrix开始---------
	cudaEventCreate(&start);
	cudaEventCreate(&stop);
	cudaDeviceSynchronize();
	cudaEventRecord(start, 0);

	transpose_matrix_v2 << <p, WARP, 0, 0 >> > (sigma, p, size, dev_bit_flag, dev_aData, dev_aIndex);

	cudaEventRecord(stop, 0);
	cudaEventSynchronize(stop);
	cudaEventElapsedTime(&elapsedTime_transpose, start, stop);
	cudaEventDestroy(start);
	cudaEventDestroy(stop);
	
	//--------------transpose_matrix结束---------

	//--------------CSR5开始---------
	cudaEventCreate(&start);
	cudaEventCreate(&stop);
	cudaDeviceSynchronize();
	cudaEventRecord(start, 0);
	//CSR5
	csr5 << <GS, BS>> > (sigma, dev_partition_pointer, dev_y_offset, dev_seg_offset, dev_bit_flag,
		 dev_aData, dev_aIndex, dev_aPtr, dev_x, dev_b, m, p);

	cudaMemcpyAsync(b, dev_b, m * sizeof(double), cudaMemcpyDeviceToHost, 0);

	cudaEventRecord(stop, 0);
	cudaEventSynchronize(stop);
	cudaEventElapsedTime(&elapsedTime, start, stop);
	cudaEventDestroy(start);
	cudaEventDestroy(stop);

	//--------------CSR5结束---------
	
	//cudaEventCreate(&start);
	//cudaEventCreate(&stop);

	//for (int p = 0; p < SPMV_TRI_NUM; p++)
	//{
	//	if (p > 0) {
	//		cudaMemset(dev_b, 0, sizeof(double) * m);
	//		//执行一次后，temp和last_temp需要重新计算
	//	}
	//	cudaEventRecord(start, 0);
	//	//kernel启动
	//	csr5 << <GS, BS >> > (sigma, dev_partition_pointer, dev_y_offset, dev_seg_offset, dev_bit_flag,
	//		dev_aData, dev_aIndex, dev_aPtr, dev_x, dev_b, m, p);
	//	cudaEventRecord(stop, 0);
	//	cudaEventSynchronize(stop);
	//	cudaEventElapsedTime(&elapsedTime, start, stop);

	//	if (p > 0) {
	//		ave_msec += elapsedTime;
	//	}

	//}
	//ave_msec /= SPMV_TRI_NUM;

	//cudaEventDestroy(start);
	//cudaEventDestroy(stop);

	
	//-------posttime计时开始-----
	cudaEventCreate(&start);
	cudaEventCreate(&stop);
	cudaEventRecord(start, 0);

	cudaFree(dev_aData);
	cudaFree(dev_aIndex);
	cudaFree(dev_aPtr);
	cudaFree(dev_x);
	cudaFree(dev_b);
	cudaFree(dev_partition_pointer);
	cudaFree(dev_y_offset);
	cudaFree(dev_seg_offset);
	cudaFree(dev_bit_flag);

	//--------------------------------------------------
	cudaEventRecord(stop, 0);
	cudaEventSynchronize(stop);

	cudaEventElapsedTime(&postTime, start, stop);

	cudaEventDestroy(start);
	cudaEventDestroy(stop);
	//-------posttime计时结束-----

	//-------------++++++++++++++++++++++++++++++++++++++++++++++++++
	printf("preTime = : %12.4f ms \n ", preTime);
	printf("bit flag computeTime = : %12.4f ms \n ", elapsedTime_bitflag);
	printf("tile ptr computeTime = : %12.4f ms \n ", elapsedTime_tileptr);
	printf("tile descriptor computeTime = : %12.4f ms \n ", elapsedTime_tiledescriptor);
	printf("transpose computeTime = : %12.4f ms \n ", elapsedTime_transpose);
	printf("-------------CSR5 computeTime-------- = : %12.4f ms \n ", elapsedTime);
	//printf("-------------CSR5 ave computeTime-------- = : %12.4f ms \n ", ave_msec);
	printf("postTime = : %12.4f ms \n ", postTime);
	printf("totalTime = : %12.4f ms \n ", preTime + elapsedTime_bitflag + 
	elapsedTime_tileptr+ elapsedTime_tiledescriptor+ elapsedTime_transpose+ elapsedTime+postTime);

	return 0;
}



