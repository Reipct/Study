/* -----------------------------------------------------------------------------
	The programming is licensed to you under the ZJUT Consortium license:
	Copyright (C)  2012  Jiaquan Gao
	  E-mail: gaojq@zjut.edu.cn

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
 *  This .h file is used to caculate multiplication of a matrix and a vector
** ---------------------------------------------------------------------------*/

#ifndef SPMV_H
#define SPMV_H

/* ----------------------------------------------------------------------------
 * CSR Format
** ---------------------------------------------------------------------------*/
void csr_SpMV(CSR_Matrix* A, double* w, double* v)
{
	int row_start, row_end;
	for (int i = 0; i < A->nRow; i++)
	{
		row_start = A->mPtr[i];
		row_end = A->mPtr[i + 1];
		double sum = 0.0;
		for (int j = row_start; j < row_end; j++)
		{
			sum += A->mData[j] * v[A->mIndex[j]];
		}
		w[i] = sum;
	}
}

/* ----------------------------------------------------------------------------
 * DIA Format
** ---------------------------------------------------------------------------*/
void diags_SpMV(DIA_Matrix* A, double* w, double* v)
{
	for (int i = 0; i < A->nRow; i++)
	{

		double sum = 0.0;
		for (int j = 0; j < A->nDIG; j++)
		{
			int nTmpCol = i + A->mOffset[j];

			if ((nTmpCol >= 0) && (nTmpCol < A->nCol)) {
				double val = A->mData[j * A->nRow + i];
				sum += val * v[nTmpCol];
				//if( i == 47376 && A->mData[j * A->nRow + i] != 0 ) printf("col= %d, value = %f\n", nTmpCol, A->mData[j * A->nRow + i]);
			}
		}
		w[i] = sum;
	}
	printf("\n");
}

/* ----------------------------------------------------------------------------
 * ELL Format
** ---------------------------------------------------------------------------*/
void ell_SpMV(ELL_Matrix* A, double* w, double* v)
{
	for (int i = 0; i < A->nRow; i++)
	{
		double sum = 0.0;
		for (int j = 0; j < A->nCol; j++) {
			double val = A->mData[i + j * A->nRow];
			if (val != 0.0) {
				sum += val * v[A->mIndex[i + j * A->nRow]];
			}
		}

		w[i] = sum;
	}
}

/* ----------------------------------------------------------------------------
 * BRCSDI Format
** ---------------------------------------------------------------------------*/
void BRCSDI_SpMV(BRCSD_Matrix* A, double* w, double* v)
{
	for (int i = 0; i < A->s; i++) {
		printf("r[%d]=%d, r[%d]=%d, offsetSize[%d]=%d, dataSize[%d]=%d\n", i, A->r[i], i + 1, A->r[i + 1], i, A->offsetSize[i], i, A->dataSize[i]);
		if (i < A->s - 1) {
			for (int j = A->r[i]; j < A->r[i + 1]; j++) {
				double tmp = 0;
				for (int k = 0; k < A->offsetSize[i]; k++) {
					int loc_data = A->dataSize[i] + k * (A->r[i + 1] - A->r[i]) + j - A->r[i];
					int loc_v = j + A->mOffset[i * A->nDIG + k];
					//if( j == 47376 && A->mData[loc_data] != 0 ) printf("col= %d, value = %f\n", loc_data, A->mData[loc_data]);
					//if( j == 47376 && loc_data == 342288 ) printf("col= %d, value = %f\n", loc_data, A->mData[loc_data]);
					if (loc_v >= 0 && loc_v < A->nCol) {
						tmp += A->mData[loc_data] * v[loc_v];
					}
				}

				w[j] = tmp;
			}
		}
		else if (i == A->s - 1) {
			for (int j = A->r[i]; j < A->nRow; j++) {
				double tmp = 0;
				for (int k = 0; k < A->offsetSize[i]; k++) {
					int loc_data = A->dataSize[i] + k * (A->nRow - A->r[i]) + j - A->r[i];
					int loc_v = j + A->mOffset[i * A->nDIG + k];
					if (loc_v >= 0 && loc_v < A->nCol) {
						tmp += A->mData[loc_data] * v[loc_v];
					}
				}
				w[j] = tmp;
			}
		}
	}

	//printf("\n");
}


/* ----------------------------------------------------------------------------
 * BRCSDI Format
** ---------------------------------------------------------------------------*/
void BRCSDII_SpMV(BRCSD_Matrix* A, double* w, double* v)
{
	int sump = 0;
	int nrows = A->nrows;
	int rowstart, rowend;
	double temp;
	int loc1, loc2;
	for (int i = 0; i < A->s; i++) {
		if (i < A->s - 1) {
			for (int j = 0; j < A->p[i]; j++) {
				rowstart = sump * nrows;
				rowend = (sump + 1) * nrows;
				for (int k = rowstart; k < rowend; k++) {
					temp = 0.0;
					for (int z = 0; z < A->offsetSize[i]; z++) {
						loc1 = A->dataSize[i] + j * nrows * A->offsetSize[i] + z * nrows + k - rowstart;
						loc2 = A->mOffset[i * A->nDIG + z] + k;
						if (loc2 >= 0 && loc2 < A->nCol) {
							temp += A->mData[loc1] * v[loc2];
						}
					}

					w[k] = temp;
				}
				sump++;
			}
		}
		else if (i == A->s - 1) {
			rowstart = sump * nrows;
			rowend = A->nRow;
			for (int k = rowstart; k < rowend; k++) {
				temp = 0.0;
				for (int z = 0; z < A->offsetSize[i]; z++) {
					loc1 = A->dataSize[i] + z * (rowend - rowstart) + k - rowstart;
					loc2 = A->mOffset[i * A->nDIG + z] + k;
					if (loc2 >= 0 && loc2 < A->nCol) {
						temp += A->mData[loc1] * v[loc2];
					}
				}

				w[k] = temp;
			}
		}
	}
}

/* ----------------------------------------------------------------------------
 * CRSD Format
** ---------------------------------------------------------------------------*/
void CRSD_SpMVV1(CRSD_Matrix* A, double* w, double* v)
{
	int nrows = A->nrows;
	int rowstart, rowend;
	double temp;
	int loc1, loc2;

	/*The part without scatter points*/
	for (int i = 0; i < A->s; i++) {
		if (i < A->s - 1) {
			rowstart = i * nrows;
			rowend = (i + 1) * nrows;
			for (int k = rowstart; k < rowend; k++) {
				temp = 0.0;
				for (int z = 0; z < A->offsetSize[i]; z++) {
					loc1 = A->dataSize[i] + z * nrows + k - rowstart;
					loc2 = A->mOffset[i * A->nDIG + z] + k;
					if (loc2 >= 0 && loc2 < A->nCol) {
						temp += A->mData[loc1] * v[loc2];
					}
				}

				w[k] = temp;
			}
		}
		else if (i == A->s - 1) {
			rowstart = i * nrows;
			rowend = A->nRow;
			for (int k = rowstart; k < rowend; k++) {
				temp = 0.0;
				for (int z = 0; z < A->offsetSize[i]; z++) {
					loc1 = A->dataSize[i] + z * (rowend - rowstart) + k - rowstart;
					loc2 = A->mOffset[i * A->nDIG + z] + k;
					if (loc2 >= 0 && loc2 < A->nCol) {
						temp += A->mData[loc1] * v[loc2];
					}
				}

				w[k] = temp;
			}
		}
	}

	for (int i = 0; i < A->nRow; i++) {
		printf("w[%d]=%f\n", i, w[i]);
	}
	printf("\n");

	/*The part with scatter points*/
	for (int i = 0; i < A->ellRows; i++) {
		temp = 0.0;
		for (int j = 0; j < A->ellCols; j++) {
			double value = A->ellData[j * A->ellRows + i];
			if (value != 0) {
				temp += value * v[A->ellIndex[j * A->ellRows + i]];
			}
		}
		w[A->ellRowi[i]] = temp;
	}

	for (int i = 0; i < A->ellRows; i++) {
		printf("w[%d]=%f\n", A->ellRowi[i], w[A->ellRowi[i]]);
	}
	printf("\n");

}

/* ----------------------------------------------------------------------------
 * CRSD Format
** ---------------------------------------------------------------------------*/
void CRSD_SpMV(CRSD_Matrix* A, double* w, double* v)
{
	int nrows = A->nrows;
	int rowstart, rowend;
	double temp;
	int loc1, loc2;

	/*The part without scatter points*/
	for (int i = 0; i < A->s; i++) {
		if (i < A->s - 1) {
			rowstart = i * nrows;
			rowend = (i + 1) * nrows;
			for (int k = rowstart; k < rowend; k++) {
				temp = 0.0;
				for (int z = 0; z < A->offsetSize[i]; z++) {
					loc1 = A->dataSize[i] + z * nrows + k - rowstart;
					loc2 = A->mOffset[i * A->nDIG + z] + k;
					if (loc2 >= 0 && loc2 < A->nCol) {
						temp += A->mData[loc1] * v[loc2];
					}
				}

				w[k] = temp;
			}
		}
		else if (i == A->s - 1) {
			rowstart = i * nrows;
			rowend = A->nRow;
			for (int k = rowstart; k < rowend; k++) {
				temp = 0.0;
				for (int z = 0; z < A->offsetSize[i]; z++) {
					loc1 = A->dataSize[i] + z * (rowend - rowstart) + k - rowstart;
					loc2 = A->mOffset[i * A->nDIG + z] + k;
					if (loc2 >= 0 && loc2 < A->nCol) {
						temp += A->mData[loc1] * v[loc2];
					}
				}

				w[k] = temp;
			}
		}
	}

	//	for(int i = 0 ; i < A->nRow; i++){
	//		printf("w[%d]=%f\n", i, w[i]);
	//	}
	//	printf("\n");

		/*The part with scatter points*/
	for (int i = 0; i < A->ellRows; i++) {
		temp = 0.0;
		for (int j = 0; j < A->ellCols; j++) {
			double value = A->ellData[j * A->ellRows + i];
			if (value != 0) {
				temp += value * v[A->ellIndex[j * A->ellRows + i]];
			}
		}
		//printf("w[%d]=%f\n", i, temp);
		w[A->ellRowi[i]] = temp + w[A->ellRowi[i]];
	}

	//	for(int i = 0 ; i < A->ellRows; i++){
	//		printf("w[%d]=%f\n", A->ellRowi[i], w[A->ellRowi[i]]);
	//	}
	//	printf("\n");

}


/* ----------------------------------------------------------------------------
 * HDI Format
** ---------------------------------------------------------------------------*/
void HDI_SpMV(HDI_Matrix* A, double* w, double* v)
{
	int hackSize = A->hackSize;
	int hacks = A->hacks;
	int rowstart, rowend;
	double temp;
	int offsetStart, offsetEnd, startLoc, tindex, loc;

	/*The part without scatter points*/
	for (int i = 0; i < hacks; i++) {
		if (i < hacks - 1) {
			rowstart = i * hackSize;
			rowend = (i + 1) * hackSize;
			startLoc = A->mPtr[i];
			offsetStart = A->hackOffsets[i];
			offsetEnd = A->hackOffsets[i + 1];
			for (int k = rowstart; k < rowend; k++) {
				temp = 0.0;
				for (int z = offsetStart; z < offsetEnd; z++) {
					loc = startLoc + (z - offsetStart) * hackSize + k - rowstart;
					tindex = A->mOffset[z] + k;
					if (tindex >= 0 && tindex < A->nCol) {
						temp += A->mData[loc] * v[tindex];
					}
				}

				w[k] = temp;
			}
		}
		else if (i == hacks - 1) {
			rowstart = i * hackSize;
			rowend = A->nRow;
			startLoc = A->mPtr[i];
			offsetStart = A->hackOffsets[i];
			offsetEnd = A->hackOffsets[i + 1];
			for (int k = rowstart; k < rowend; k++) {
				temp = 0.0;
				for (int z = offsetStart; z < offsetEnd; z++) {
					loc = startLoc + (z - offsetStart) * (rowend - rowstart) + k - rowstart;
					tindex = A->mOffset[z] + k;
					if (tindex >= 0 && tindex < A->nCol) {
						temp += A->mData[loc] * v[tindex];
					}
				}

				w[k] = temp;
			}
		}
	}
}

#endif



