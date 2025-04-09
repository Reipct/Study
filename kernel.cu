#include "cuda_runtime.h"
#include "device_launch_parameters.h"
#include <stdio.h>
#include <iostream>
#include <stdlib.h>
#include <memory.h>
#include <string>
#include <string> 
#include <vector>
#include "util.h"
#include "dataType.h"
#include "read.h"
#include "SpMV_cpu.h"
#include "csr5.h"

using namespace std;

/*
 *Read the matrix from the file that comes from the SuiteSparse Matrix Collection
 *and store the matrix by the csr fromat;
 *Tranfer csr to dia, and dia to HDI
 *Call HDI to execute HDI algorithm.
 */
int main(int argc, char** argv)
{
	char filename[50];

	cout << "Input the matrix filename:" << endl;
	cin >> filename;

	CSR_Matrix* CSR_A;
	CSR_A = (CSR_Matrix*)malloc(sizeof(CSR_Matrix));
	readMatrixToCSR(filename, CSR_A);

	double* x, * b;
	double* b1;

	x = (double*)malloc(sizeof(double) * (CSR_A->nCol));
	b = (double*)malloc(sizeof(double) * (CSR_A->nRow));
	b1 = (double*)malloc(sizeof(double) * (CSR_A->nRow));
	for (int i = 0; i < CSR_A->nCol; i++) {
		x[i] = 1.0;
	}
	for (int i = 0; i < CSR_A->nRow; i++) {
		b[i] = 0.0;
		b1[i] = 0.0;
	}

	printf("Begin to execute CSRMV kernel \n");

	//cusparse_DCSRMV(CSR_A, x, b);

	//gpu  warp
	spmvCSR(CSR_A, x, b);

	printf("The CSRMV kernel execution is finished!!!\n");

	//cpu
	csr_SpMV(CSR_A, b1, x);

	//比较结果
	compareVec(b1, b, CSR_A->nRow);

	printf("-----Print the first 10 rows");
	for (int i = 0; i < 10; i++) {
		printf("b[%d]=%16.9f \n", i, b[i]);
	}

	printf("---Print the last 10 rows");
	for (int i = CSR_A->nRow - 10; i < CSR_A->nRow; i++) {
		printf("b[%d]=%16.9f \n ", i, b[i]);
	}

	free(b);
	free(b1);
	free(x);
	free(CSR_A);
	return 0;
}