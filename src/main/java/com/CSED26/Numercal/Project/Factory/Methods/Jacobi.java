package com.CSED26.Numercal.Project.Factory.Methods;

import com.CSED26.Numercal.Project.Matrix;
import com.CSED26.Numercal.Project.Factory.Numeric;

import java.util.Arrays;

public class Jacobi extends Numeric {
    private Matrix augMatrix;
    public static int maxIterations = 100;
    public static double tol = 0.00001;

    public Jacobi(Matrix matrix) {
        this.augMatrix = matrix;
    }
    public Jacobi(Matrix matrix, int maxIterations) {
        this.augMatrix = matrix;
        this.maxIterations = maxIterations;
    }
    public Jacobi(Matrix matrix, int maxIterations, int tol) {
        this.augMatrix = matrix;
        this.maxIterations = maxIterations;
        this.tol = tol;
    }
    public double[] evaluate() {
        int iterations = 0;
        int n = augMatrix.getNumRows();
        double[] nextsolution = new double[n];
        double[] currentsolution = new double[n];
        Arrays.fill(nextsolution, 0);
        Arrays.fill(currentsolution, 0);
        while(true) {
            for(int i = 0; i < n; i++) {
                nextsolution[i] = augMatrix.getElement(i, n);
                for(int j = 0; j < n; j++) {
                    if(j != i)
                        nextsolution[i] -= augMatrix.getElement(i, j) * currentsolution[j];
                }
                nextsolution[i] /= augMatrix.getElement(i, i);
            }
            iterations++;
            if(iterations == maxIterations) {
                break;
            }
            currentsolution = (double[]) nextsolution.clone();
        }
        return nextsolution;
    }
    @Override
    public Matrix forwardElim() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'forwardElim'");
    }
    @Override
    public Matrix backElim() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'backElim'");
    }
}
