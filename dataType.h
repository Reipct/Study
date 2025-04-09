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

/* ----------------------------------------------------------------------------
 *  This .h file is used to define the data structure of matrix and vector
** ---------------------------------------------------------------------------*/

#ifndef MATRIX_VECTOR_H
#define MATRIX_VECTOR_H

typedef struct {
	int n;
	int nonzeroes;
	int* mIndex;
	int* mRow;
	int* mPtr;
	int* mCol;
	double* mData;
	int nRow;
	int nCol;
} CSR_Matrix;

typedef struct {
	int nRow;
	int nCol;
	int nDIG;
	int nonzeroes;
	int* mOffset;
	double* mData;
} DIA_Matrix;

typedef struct {
	int nRow;
	int nCol;
	int nonzeroes;
	int* mIndex;
	double* mData;
} ELL_Matrix;

typedef struct {
	int n;
	int nonzeroes;
	int* mI;
	int* mJ;
	double* mV;
} COO_Matrix;

typedef struct {
	int nRow;
	int nCol;
	int nDIG;
	int nonzeroes;
	int s;
	int nrows;
	int* offsetSize;
	int* dataSize;
	int* mOffset;
	double* mData;
	int ellRows;
	int ellCols;
	int* ellRowi;
	int* ellIndex;
	double* ellData;
} CRSD_Matrix;

typedef struct {
	int nRow;
	int nCol;
	int nDIG;
	int nonzeroes;
	int s;
	int nrows;
	int* r;
	int* p;
	int* offsetSize;
	int* dataSize;
	int* mOffset;
	double* mData;
} BRCSD_Matrix;

typedef struct {
	int nRow;
	int nCol;
	int nonzeroes;
	int ndiags;
	int hacks;
	int hackSize;
	int* hackOffsets;
	int* mOffset;
	double* mData;
	int* mPtr;
} HDI_Matrix;

typedef struct {
	int* mIndex;
	double* mData;
	int* mPtr;
	int nRow;
	int nCol;
	int n; //for square matrix
	int nonzeroes;
} CSC_Matrix;

#endif


