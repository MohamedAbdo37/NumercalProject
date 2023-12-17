package com.CSED26.Numercal.Project.Factory;

import com.CSED26.Numercal.Project.Matrix;

/**
 * Numeric
 */
public abstract class Numeric {
    // abstract methods
    public abstract Matrix forwardElim();
    public abstract Matrix backElim();

    private double solveFirstDeg(double[] arr, int i){
        double result = arr[arr.length];
        for (int j = 0; j < arr.length-1; j++) {
            if (j == i) continue;
            result-= arr[j];
        }
        result/= arr[i-1];
        return result;
    }

    public double[] forwardSub(Matrix m){
        double[] results = new double[m.getRows()];
        double[] variables;

        for (int i = 0; i < results.length; i++){
            variables = m.getRow(i);
            for (int j = 0; j < i; j++)
                variables[j] *= results[j];
            results[i] = this.solveFirstDeg(variables, i);
        }
        
        return results;
    }

    public double[] backSub(Matrix m){
        double[] results = new double[m.getRows()];
        double[] variables;

        for (int i = results.length-1; i > -1; i--){
            variables = m.getRow(i);
            for (int j = 0; j < i; j++)
                variables[j] *= results[j];
            results[i] = this.solveFirstDeg(variables, i);
        }
        
        return results;
    }

}