package com.CSED26.Numercal.Project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.LUDecomposition;

import io.micrometer.common.lang.NonNull;

public class Matrix {
    private ArrayList<ArrayList<Double>> matrix;
    private int numRows;
    private int numCols;

    public static int significantFigures = 5;

    public Matrix(int n, int m) {
        matrix = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            matrix.add(new ArrayList<>(m));
            for (int j = 0; j < m; j++) {
                matrix.get(i).add(0.0d);
            }
        }
        numRows = n;
        numCols = m;
    } 
    public static int getSignificantFigures() {
        return significantFigures;
    }

    public Matrix(int numRows2) {
        matrix = new ArrayList<>(numRows2);
        for (int i = 0; i < numRows2; i++) {
            matrix.add(new ArrayList<>(numRows2));
            for (int j = 0; j < numRows2; j++) {
                matrix.get(i).add(0.0d);
            }
        }
        numRows = numRows2;
        numCols = numRows2;
    }

    public int getNumCols() {
        return numCols;
    }

    public int getNumRows() {
        return numRows;
    }

    public Matrix setRow(int rowIndex, ArrayList<Double> rowValues) {
        if (rowValues.size() != numCols) {
            throw new IllegalArgumentException("Invalid number of elements in the row.");
        }
        matrix.set(rowIndex, rowValues);
        return this;
    }

    public ArrayList<Double> getRow(int i) {
        return matrix.get(i);
    }

    public int getDim() {
        return numRows * numCols;
    }

    public double getElement(int r, int c) {
        if (r < 0 || r >= numRows || c < 0 || c >= numCols) {
            throw new IndexOutOfBoundsException("Invalid row or column index.");
        }
        return matrix.get(r).get(c);
    }

    public Matrix multiply(@NonNull Matrix mtx, int significantFigures) {
        if (numCols != mtx.numRows) {
            throw new IllegalArgumentException("Incompatible matrix dimensions for multiplication.");
        }

        Matrix result = new Matrix(numRows, mtx.numCols);

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < mtx.numCols; j++) {
                float sum = 0.0f;
                for (int k = 0; k < numCols; k++) {
                    sum += matrix.get(i).get(k) * mtx.getElement(k, j);
                }
                // Round the result to the specified number of significant figures
                result.matrix.get(i).set(j, roundToSignificantFigures(sum, significantFigures));
            }
        }

        result.numRows = matrix.size();
        result.numCols = mtx.numCols;

        return result;
    }

    // Helper method to round to a specified number of significant figures
    private double roundToSignificantFigures(float value, int significantFigures) {
        if (value == 0) {
            return 0;
        }

        double magnitude = Math.pow(10, significantFigures - Math.floor(Math.log10(Math.abs(value))));
        return (Math.round(value * magnitude) / magnitude);
    }

    public boolean compMatrix(Matrix mtx) {
        if (numRows != mtx.numRows || numCols != mtx.numCols) {
            return false;
        }
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (!matrix.get(i).get(j).equals(mtx.getElement(i, j))) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean Symetricmatrix(@NonNull Matrix mx) {
        if (mx.numRows != mx.numCols)
            return false;
        for (int i = 0; i < mx.numRows; i++)
            for (int j = 0; j < i; j++)
                if (mx.getElement(i, j) != mx.getElement(j, i))
                    return false;
        return true;

    }

    public Matrix mulRow(int r, double num) {
        if (r >= numRows) {
            throw new IllegalArgumentException("Invalid row index.");
        } else {
            Matrix result = new Matrix(numRows, numCols);

            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numCols; j++) {
                    double value = matrix.get(i).get(j);
                    if (i == r) {
                        value *= num;
                    }
                    result.matrix.get(i).set(j, value);
                }
            }

            return result;
        }
    }

    public Matrix addRows(int r1, int r2) {
        if (r1 >= numRows || r2 >= numRows) {
            throw new IllegalArgumentException("Invalid row index.");
        } else {
            Matrix result = this;

            for (int i = 0; i < numCols; i++) {
                double sum = matrix.get(r1).get(i) + matrix.get(r2).get(i);
                result.matrix.get(r1).set(i, roundToSignificantFigures(sum, significantFigures));
            }

            return result;
        }
    }

    public static double roundToSignificantFigures(double value, int n) {
        int significantFigures = n - 1;
        if (value == 0) {
            return 0;
        }
        double magnitude = Math.pow(10, Math.max(0, significantFigures - Math.floor(Math.log10(Math.abs(value)))));
        return (double) (Math.round(value * magnitude) / magnitude);
    }

    public Matrix Copy() {
        Matrix copy = new Matrix(numRows, numCols);
        for (int i = 0; i < numRows; i++)
            for (int j = 0; j < numCols; j++)
                copy.matrix.get(i).set(j, this.getElement(i, j));
        return copy;
    }

    public double calculateDeterminant() {
        if (numRows != numCols) {
            throw new IllegalArgumentException("Matrix must be a square matrix.");
        }
        double[][] dataArray = new double[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                dataArray[i][j] = matrix.get(i).get(j);
            }
        }
        Array2DRowRealMatrix realMatrix = new Array2DRowRealMatrix(dataArray);
        LUDecomposition luDecomposition = new LUDecomposition(realMatrix);

        return luDecomposition.getDeterminant();

    }

    public Matrix addRow(ArrayList<Double> rowValues) {
        if (rowValues.size() != numCols) {
            throw new IllegalArgumentException("Invalid number of elements in the row.");
        }
        matrix.add(rowValues);
        numRows++;
        return this;
    }

    public Matrix deleteColumn(int columnIndex) {
        if (columnIndex < 0 || columnIndex >= numCols) {
            throw new IllegalArgumentException("Invalid column index.");
        }
        for (ArrayList<Double> row : matrix) {
            row.remove(columnIndex);
        }
        numCols--;
        return this;
    }

    public ArrayList<Double> getColumn(int c) {
        if (c < 0 || c >= numCols) {
            throw new IndexOutOfBoundsException("Invalid column index.");
        }
        ArrayList<Double> column = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            column.add(matrix.get(i).get(c));
        }
        return column;
    }

    public void setEle(int i, int j, double d) {
        if (i < 0 || i >= numRows || j < 0 || j >= numCols) {
            throw new IndexOutOfBoundsException("Invalid row or column index.");
        }
        matrix.get(i).set(j, d);
    }

    public Matrix addColumn(List<Double> constants) {
        if (constants.size() != numRows) {
            throw new IllegalArgumentException("Invalid number of elements in the column.");
        }
        for (int i = 0; i < numRows; i++) {
            matrix.get(i).add(constants.get(i));
        }
        numCols++;
        return this;
    }

    public Matrix swapRows(int r1, int r2) {
        if (r1 >= numRows || r2 >= numRows)
            throw new IllegalArgumentException("Invalid number of elements in the row.");

        Matrix result = new Matrix(numRows, numCols);

        for (int i = 0; i < numCols; i++) {
            result.matrix.get(r1).set(i, this.matrix.get(r1).get(i));
            this.matrix.get(r1).set(i, this.matrix.get(r2).get(i));
            this.matrix.get(r2).set(i, result.matrix.get(r1).get(i));
        }

        return this;
    }

    public Matrix mulCol(int col, double scalar) {
        if (col >= numCols)
            throw new IllegalArgumentException("Invalid column index.");

        Matrix result = this;

        for (int i = 0; i < numRows; i++) {
            result.matrix.get(i).set(col, this.matrix.get(i).get(col) * scalar);
        }

        return result;
    }

    public Matrix swapCols(int c1, int c2) {
        if (c1 >= numCols || c2 >= numCols)
            throw new IllegalArgumentException("Invalid number of elements in the column.");

        Matrix result = new Matrix(numRows, numCols);

        for (int i = 0; i < numRows; i++) {
            result.matrix.get(i).set(c1, this.matrix.get(i).get(c1));
            this.matrix.get(i).set(c1, this.matrix.get(i).get(c2));
            this.matrix.get(i).set(c2, result.matrix.get(i).get(c1));
        }

        return result;
    }
    public void print(){
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                System.out.print(getElement(i, j) + " ");
            }
            System.out.println();
        }
    }
}
