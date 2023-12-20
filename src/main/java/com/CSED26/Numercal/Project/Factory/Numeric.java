package com.CSED26.Numercal.Project.Factory;

import java.util.ArrayList;

import com.CSED26.Numercal.Project.Matrix;

/**
 * Numeric
 */
public abstract class Numeric {
    // abstract methods
    public abstract Matrix forwardElim();

    public abstract Matrix backElim();

    public abstract ArrayList<Double> solve();

    private double solveFirstDeg(ArrayList<Double> variables, int i) {
        double result = variables.get(variables.size()-1);
        for (int j = 0; j < variables.size() - 1; j++) {
            if (j == i)
                continue;
            result -= variables.get(j);
        }
        result /= variables.get(i);
        return result;
    }

    public ArrayList<Double> forwardSub(Matrix m) {
        ArrayList<Double> results = new ArrayList<>(m.getNumRows());
        for (double value : new double[m.getNumRows()]) {
            results.add(value);
        }
        ArrayList<Double> variables;

        for (int i = 0; i < results.size(); i++) {
            variables = m.getRow(i);
            for (int j = 0; j < i; j++)
                variables.set(j, variables.get(j) * results.get(j));
            results.set(i, this.solveFirstDeg(variables, i));
        }

        return results;
    }

    public ArrayList<Double> backSub(Matrix m) {
        ArrayList<Double> results = new ArrayList<>(m.getNumRows());
        for (double value : new double[m.getNumRows()]) {
            results.add(value);
        }
        ArrayList<Double> variables;

        for (int i = results.size() - 1; i > -1; i--) {
            variables = m.getRow(i);
            for (int j = results.size() - 1; j > i; j--)
                variables.set(j, variables.get(j) * results.get(j));
            results.set(i, Matrix.roundToSignificantFigures(this.solveFirstDeg(variables, i), Matrix.significantFigures));
        }

        return results;
    }

}