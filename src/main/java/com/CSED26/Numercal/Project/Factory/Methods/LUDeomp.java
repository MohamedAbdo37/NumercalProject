package com.CSED26.Numercal.Project.Factory.Methods;

import com.CSED26.Numercal.Project.Matrix;
import com.CSED26.Numercal.Project.Factory.Numeric;

public class LUDeomp extends Numeric{
    private Matrix mat;
    private double[] lMatrix;
    private double[] uMatrix;
    private double[] xArray;
    LUDeomp(Matrix auMatrix){
        int size = (auMatrix.getRows() *(auMatrix.getRows()+1))/2;
        this.lMatrix = new double[size];
        this.uMatrix = new double[size];
        this.mat = auMatrix.delColumn(auMatrix.getDim()[1]);
        this.xArray = auMatrix.getColumn(auMatrix.getColumns());
    }

    @Override
    public Matrix forwardElim() {
        this.lMatrix[0] = 1;
        Matrix m = this.mat;
        for (int i = 0; i < this.mat.getRows(); i++) {
            if(i != 0) this.lMatrix[(int)((i+1)*(i+2)*0.5) - 1] = 1;
            for (int j = i+1; j < this.mat.getRows(); j++) {
                double valu = (-this.mat.getEle(j,i)) / this.mat.getEle(i,i);
                m = this.mat.mulRow(i,valu).addRows(i,j);
                this.lMatrix[(int)(j*(j+1)*0.5)+i+1] = valu;
            }
        }
        this.setUMatrix(m);
        return m;
    }

    private void setUMatrix(Matrix m) {
        int i = 0;
        int j = 0;
        for (int index = 0; index < this.uMatrix.length; index++) {
            this.uMatrix[index] =  m.getEle(i, j);
            if(j == m.getRows()){
                i++;
                j = i;
            }else j++;
        }
    }

    public Matrix getUMatrix() {
        Matrix m = new Matrix(this.mat.getRows());
        int i = 0;
        int j = 0;
        for (int index = 0; index < this.uMatrix.length; index++) {
            m.setEle(i, j, this.uMatrix[index]);
            if(j == m.getRows()){
                i++;
                j = i;
            }else j++;
        }
        return m;
    }

    public Matrix getLMatrix() {
        Matrix m = new Matrix(this.mat.getRows());
        int i = 0;
        int j = 0;
        for (int index = 0; index < this.lMatrix.length; index++) {
            m.setEle(i, j, this.lMatrix[index]);
            if(i == j){
                i++;
                j = 0;
            }else j++;
        }
        return m;
    }

    @Override
    public Matrix backElim() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'backElim'");
    }

    public double[] doLittle(){
        double[] results;
        Matrix middel = this.getLMatrix();
        middel.addColumn(this.xArray);
        results = super.forwardSub(middel);

        middel = this.getUMatrix();
        middel.addColumn(results);
        results = super.forwardSub(middel);

        return results;
    }


}
