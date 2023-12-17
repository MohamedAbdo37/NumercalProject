package com.CSED26.Numercal.Project.Factory.Methods;

import com.CSED26.Numercal.Project.Matrix;
import com.CSED26.Numercal.Project.Factory.Numeric;

public class LUDeomp extends Numeric{
    private Matrix mat;
    private double[] lMatrix;
    private double[] uMatrix;
    private double[] xArray;
    public LUDeomp(Matrix auMatrix){
        int size = (auMatrix.getNumRows() *(auMatrix.getNumRows()+1))/2;
        this.lMatrix = new double[size];
        this.uMatrix = new double[size];
        this.mat = auMatrix.deleteColumn(auMatrix.getNumCols());
        this.xArray = auMatrix.getColumn(auMatrix.getNumCols());
    }

    @Override
    public Matrix forwardElim() {
        this.lMatrix[0] = 1;
        Matrix m = this.mat;
        for (int i = 0; i < this.mat.getNumRows(); i++) {
            if(i != 0) this.lMatrix[(int)((i+1)*(i+2)*0.5) - 1] = 1;
            for (int j = i+1; j < this.mat.getNumRows(); j++) {
                double valu = (-this.mat.getElement(j,i)) / this.mat.getElement(i,i);
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
            this.uMatrix[index] =  m.getElement(i, j);
            if(j == m.getNumRows()){
                i++;
                j = i;
            }else j++;
        }
    }
    private void setLMatrix(Matrix m) {
        int i = 0;
        int j = 0;
        for (int index = 0; index < this.uMatrix.length; index++) {
            this.lMatrix[index] =  m.getElement(i, j);
            if(i == j){
                i++;
                j = 0;
            }else j++;
        }
    }

    public Matrix getUMatrix() {
        Matrix m = new Matrix(this.mat.getNumRows());
        int i = 0;
        int j = 0;
        for (int index = 0; index < this.uMatrix.length; index++) {
            m.setEle(i, j, this.uMatrix[index]);
            if(j == m.getNumRows()){
                i++;
                j = i;
            }else j++;
        }
        return m;
    }

    public Matrix getLMatrix() {
        Matrix m = new Matrix(this.mat.getNumRows());
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

    public double[] crout() {
        Matrix newL = this.getLMatrix();
        Matrix newU = this.getUMatrix();
        //newL.setEle();
        for (int i = 0; i < mat.getNumRows(); i++) {
            newL = newL.mulCol(i, newU.getElement(i, i));
            newU = newU.mulRow(i, 1 / newU.getElement(i, i));
        }
        setLMatrix(newL);
        setUMatrix(newU);
        return this.doLittle();
    }
    public double[] cholesky() {
        Matrix newL = this.getLMatrix();
        Matrix newU = this.getUMatrix();
        //newL.setEle();
        for (int i = 0; i < mat.getNumRows(); i++) {
            newL = newL.mulCol(i, Math.sqrt(newU.getElement(i, i)));
            newU = newU.mulRow(i, Math.sqrt(1 / newU.getElement(i, i)));
        }
        setLMatrix(newL);
        setUMatrix(newU);
        return this.doLittle();
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
