/* -----------------------------------------------------------------------------
	The programming is licensed to you under the ZJUT Consortium license:
	Copyright (C)  2019  Jiaquan Gao
	  E-mail: 73025@njnu.edu.cn

	This program is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.
* ----------------------------------------------------------------------------*/

#ifndef UTIL_H
#define UTIL_H

#include <vector>

using namespace std;

/*-----------------------------------------------------------------------------
 * Define the data type
**----------------------------------------------------------------------------*/
texture<int2, 1, cudaReadModeElementType> doubleTexRef; //A generalized texture
__constant__  int cstOffsets[1024];   //Offsets for DIA storage

//---for sort
int compInc(const void* a, const void* b)
{
	return *(int*)a - *(int*)b;
}

int compDec(const void* a, const void* b)
{
	return *(int*)b - *(int*)a;
}

//----find the position of index
int findIndexPos(int* diaIndex, int nCol, int newIndex)
{
	int rValue = nCol + 2;
	for (int i = 0; i < nCol; i++) {
		if (diaIndex[i] == newIndex) {
			rValue = i;
			break;
		}
	}
	return rValue;
}

//----judge if the index is existing?
bool isExist(int* diaIndex, int diaNum, int newIndex)
{
	bool rValue = false;
	for (int i = 0; i < diaNum; i++) {
		if (diaIndex[i] == newIndex) {
			rValue = true;
			break;
		}
	}
	return rValue;
}

//---quick sort
void quick_sort(int s[], int sf[], int l, int r)
{
	int tempi, tempj;
	if (l < r)
	{
		int i = l, j = r, x = s[l], xf = sf[l];
		while (i < j)
		{
			while (i < j && s[j] >= x) // 从右向左找第一个小于x的数  
				j--;
			if (i < j) {
				tempi = i++;
				s[tempi] = s[j];
				sf[tempi] = sf[j];
			}

			while (i < j && s[i] < x) // 从左向右找第一个大于等于x的数  
				i++;
			if (i < j) {
				tempj = j--;
				s[tempj] = s[i];
				sf[tempj] = sf[i];
			}
		}
		s[i] = x;
		sf[i] = xf;

		quick_sort(s, sf, l, i - 1); // 递归调用   
		quick_sort(s, sf, i + 1, r);
	}
}

//--compare b and c
void compareVec(double* b, double* c, int n) {
	int k = 0;
	for (int i = 0; i < n; i++) {
		if (abs(b[i] - c[i]) > 1e-5) {
			if (k == 0)
				printf("b[%d]=%f, c[%d]=%f\n", i, b[i], i, c[i]);
			k++;
		}
	}
	printf("total fail==%d", k);
}

//--compute zero padding for DIA, HDI, BRCSD-I, and BRCSD-II
int getZeroPadding(double* data, int size) {
	int zeropadding = 0;

	for (int i = 0; i < size; i++) {
		if (data[i] == 0) zeropadding++;
	}

	return zeropadding;
}

//--compute zero padding for CRSD

int getZeroPaddingInCRSD(double* data, int size, double* elldata, int ellsize) {
	int zeropadding = 0;

	for (int i = 0; i < size; i++) {
		if (data[i] == 0) zeropadding++;
	}

	for (int i = 0; i < ellsize; i++) {
		if (elldata[i] == 0) zeropadding++;
	}

	return zeropadding;
}


#endif