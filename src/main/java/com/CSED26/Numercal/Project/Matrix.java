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
    public Matrix(int i) {
        this.setColumns(i);
        this.setRows(i);
    }
    public double[] getRow(int i) {
        return null;
    }
    public int[] getDim() {
        return null;
    }
    public Matrix delColumn(int column) {
        return null;
    }
    public Matrix mulRow(int i, double value) {
        return null;
    }
    public Matrix addRows(int row1, int row2) {
        return null;
    }
    public double getEle(int row, int column) {
        return 0;
    }
    public void setEle(int i, int j, double d) {
    }
    public double[] getColumn(int columns2) {
        return null;
    }
    public void addColumn(double[] xArray) {
    }
    public Matrix mulCol(int i, double value) {
        return null;
    }
}
