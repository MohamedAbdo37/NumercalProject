package com.CSED26.Numercal.Project;

public class Matrix {
    private int columns = 0;
    private int rows = 0;

    public int getColumns() {
        return columns;
    }
    public int getRows() {
        return rows;
    }
    public void setColumns(int columns) {
        this.columns = columns;
    }
    public void setRows(int rows) {
        this.rows = rows;
    }

    public Matrix(int rows, int columns){
        this.setColumns(columns);
        this.setRows(rows);
    }
    public double[] getRow(int i) {
        return null;
    }
}
