package numbericalproject.demo.service;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.LUDecomposition;
import org.jetbrains.annotations.NotNull;

public class Matrix {
    private ArrayList<ArrayList<Float>> matrix;
    private int numRows;
    private int numCols;

    public Matrix(int n, int m) {
        matrix = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            matrix.add(new ArrayList<>(m));
            for (int j = 0; j < m; j++) {
                matrix.get(i).add(0.0f);
            }
        }
        numRows = n;
        numCols = m;
    }
    public numbericalproject.demo.service.Matrix setRow(int rowIndex, ArrayList<Float> rowValues) {
        if (rowValues.size() != numCols) {
            throw new IllegalArgumentException("Invalid number of elements in the row.");
        }
        matrix.set(rowIndex, rowValues);
        return this;
    }

    public int getDim() {
        return numRows * numCols;
    }

    public Float getElement(int r, int c) {
        if (r < 0 || r >= numRows || c < 0 || c >= numCols) {
            throw new IndexOutOfBoundsException("Invalid row or column index.");
        }
        return matrix.get(r).get(c);
    }

    public numbericalproject.demo.service.Matrix multiply(@NotNull numbericalproject.demo.service.Matrix mtx, int significantFigures) {
        if (numCols != mtx.numRows) {
            throw new IllegalArgumentException("Incompatible matrix dimensions for multiplication.");
        }

        numbericalproject.demo.service.Matrix result = new numbericalproject.demo.service.Matrix(numRows, mtx.numCols);

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
    private float roundToSignificantFigures(float value, int significantFigures) {
        if (value == 0) {
            return 0;
        }

        double magnitude = Math.pow(10, significantFigures - Math.floor(Math.log10(Math.abs(value))));
        return (float) (Math.round(value * magnitude) / magnitude);
    }
    public boolean compMatrix(numbericalproject.demo.service.Matrix mtx) {
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
    public boolean Symetricmatrix(@NotNull numbericalproject.demo.service.Matrix mx)
    {
        if(mx.numRows!=mx.numCols)
            return false;
        for (int i=0;i<mx.numRows;i++)
            for(int j=0;j<i;j++)
                if(mx.getElement(i,j)!=mx.getElement(j,i))
                    return false;
        return true;

    }
    public numbericalproject.demo.service.Matrix mulRow(int r, float num) {
        if (r >= numRows) {
            throw new IllegalArgumentException("Invalid row index.");
        } else {
            numbericalproject.demo.service.Matrix result = new numbericalproject.demo.service.Matrix(numRows, numCols);

            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numCols; j++) {
                    float value = matrix.get(i).get(j);
                    if (i == r) {
                        value *= num;
                    }
                    result.matrix.get(i).set(j, value);
                }
            }

            return result;
        }
    }
    public numbericalproject.demo.service.Matrix addRows(int r1, int r2, int significantFigures) {
        if (r1 >= numRows || r2 >= numRows) {
            throw new IllegalArgumentException("Invalid row index.");
        } else {
            numbericalproject.demo.service.Matrix result = new numbericalproject.demo.service.Matrix(numRows, numCols);

            for (int i = 0; i < numCols; i++) {
                float sum = matrix.get(r1).get(i) + matrix.get(r2).get(i);
                result.matrix.get(r1).set(i, roundToSignificantFigures(sum, significantFigures));
            }

            return result;
        }
    }
    public numbericalproject.demo.service.Matrix Copy()
    {
        numbericalproject.demo.service.Matrix copy=new numbericalproject.demo.service.Matrix(numRows,numCols);
        for(int i=0;i<numRows;i++)
            for(int j=0;j<numCols;j++)
                copy.matrix.get(i).set(j,this.getElement(i,j));
        return copy;
    }
    //    Apache Commons Math to calculate the determinant.
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
    public numbericalproject.demo.service.Matrix addRow(ArrayList<Float> rowValues) {
        if (rowValues.size() != numCols) {
            throw new IllegalArgumentException("Invalid number of elements in the row.");
        }
        matrix.add(rowValues);
        numRows++;
        return this;
    }
    public numbericalproject.demo.service.Matrix deleteColumn(int columnIndex) {
        if (columnIndex < 0 || columnIndex >= numCols) {
            throw new IllegalArgumentException("Invalid column index.");
        }
        for (ArrayList<Float> row : matrix) {
            row.remove(columnIndex);
        }
        numCols--;
        return this;
    }

}