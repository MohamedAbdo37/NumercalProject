package com.CSED26.Numercal.Project.Factory.Methods;

import java.util.ArrayList;

import com.CSED26.Numercal.Project.Matrix;
import com.CSED26.Numercal.Project.Factory.Numeric;

public class LUDeomp extends Numeric {
    private Matrix mat;
    private Matrix lMatrix;
    private Matrix uMatrix;
    private ArrayList<Double> xArray;

    public LUDeomp(Matrix auMatrix) {
        this.lMatrix = new Matrix(auMatrix.getNumRows());
        this.uMatrix = new Matrix(auMatrix.getNumRows());
        this.xArray = auMatrix.getColumn(auMatrix.getNumCols() - 1);
        this.mat = auMatrix.deleteColumn(auMatrix.getNumCols() - 1);
        this.mat = this.forwardElim();
    }

    @Override
    public Matrix forwardElim() {
        Matrix m = this.mat;
        for (int i = 0; i < this.mat.getNumRows(); i++) {
            this.lMatrix.setEle(i, i, 1);
            if (m.getElement(i, i) == 0) {
                for (int index = i + 1; index < this.mat.getNumRows(); index++) {
                    if ((m.getElement(index, i)) != 0) {
                        m = m.swapRows(index, i);
                        break;
                    }
                }
            }
            for (int j = i + 1; j < this.mat.getNumRows(); j++) {
                double valu = (m.getElement(j, i)) / m.getElement(i, i);
                double l = valu;
                if ((valu * m.getElement(i, i) * m.getElement(j, i)) > 0) {
                    valu = -valu;
                }
                if (valu != 0) {
                    m = m.mulRow(i, valu).addRows(j, i);
                    m = m.mulRow(i, (1 / valu));
                }
                this.lMatrix.setEle(j, i, l);
            }
        }
        this.setUMatrix(m);

        String s = IsSol();
        if (s.equals("inconsistent")) {
            throw new RuntimeException("inconsistent System");
        }
        return m;
    }

    private String IsSol() {
        int rank = 0;
        int ramkaug = 0;
        for (int i = 0; i < mat.getNumRows(); i++) {
            if (mat.getRow(i).get(i) != 0.0) {
                rank++;
            }
        }
        ramkaug = rank;
        if (mat.getRow(mat.getNumRows() - 1).get(mat.getNumCols() - 1) != 0
                && ramkaug != mat.getNumRows()) {
            ramkaug++;
        }
        if (ramkaug > rank) {
            return "inconsistent";

        } else if (ramkaug == rank && ramkaug == mat.getNumRows()) {
            return "onesol";
        } else {
            return "infinite";
        }
    }

    private void setUMatrix(Matrix m) {
        this.uMatrix = m;
    }

    private void setLMatrix(Matrix m) {
        this.lMatrix = m;
    }

    public Matrix getUMatrix() {
        return this.uMatrix;
    }

    public Matrix getLMatrix() {
        return this.lMatrix;
    }

    @Override
    public Matrix backElim() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'backElim'");
    }

    public ArrayList<Double> crout() {
        Matrix newL = this.getLMatrix();
        Matrix newU = this.getUMatrix();
        // newL.setEle();
        for (int i = 0; i < mat.getNumRows(); i++) {
            newL = newL.mulCol(i, newU.getElement(i, i));
            newU = newU.mulRow(i, 1 / newU.getElement(i, i));
        }
        setLMatrix(newL);
        setUMatrix(newU);
        return this.doLittle();
    }

    public ArrayList<Double> cholesky() {
        Matrix newL = this.getLMatrix();
        Matrix newU = this.getUMatrix();
        // newL.setEle();
        for (int i = 0; i < mat.getNumRows(); i++) {
            newL = newL.mulCol(i, Math.sqrt(newU.getElement(i, i)));
            newU = newU.mulRow(i, Math.sqrt(1 / newU.getElement(i, i)));
        }
        setLMatrix(newL);
        setUMatrix(newU);
        return this.doLittle();
    }

    public ArrayList<Double> doLittle() {
        ArrayList<Double> results;
        Matrix middel = this.getLMatrix();
        middel = middel.addColumn(this.xArray);
        results = super.forwardSub(middel);

        middel = this.getUMatrix();
        middel.addColumn(results);
        results = super.backSub(middel);

        return results;
    }

    @Override
    public ArrayList<Double> solve() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'solve'");
    }

}
