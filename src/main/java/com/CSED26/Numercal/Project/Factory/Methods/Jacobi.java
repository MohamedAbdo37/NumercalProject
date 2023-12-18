package com.CSED26.Numercal.Project.Factory.Methods;

import com.CSED26.Numercal.Project.Matrix;
import com.CSED26.Numercal.Project.Factory.Numeric;

import java.util.ArrayList;
import java.util.Arrays;

public class Jacobi extends Numeric {
    private Matrix augMatrix;
    public static int maxIterations = 100;
    public static double tol = 0.00001;

    public int convergedAfter = 0;

    public Jacobi(Matrix matrix) {
        this.augMatrix = matrix;
    }

    public Jacobi(Matrix matrix, int maxIterations) {
        this.augMatrix = matrix;
        this.maxIterations = maxIterations;
    }

    public Jacobi(Matrix matrix, int maxIterations, double tol) {
        this.augMatrix = matrix;
        this.maxIterations = 100;
        this.tol = tol;
    }

    public double[] evaluate() {
        int iterations = 0;
        int n = augMatrix.getNumRows();
        double[] nextsolution = new double[n];
        double[] currentsolution = new double[n];
        Arrays.fill(nextsolution, 0);
        Arrays.fill(currentsolution, 0);
        while (true) {
            System.out.println(iterations);
            for (int i = 0; i < n; i++) {
                nextsolution[i] = augMatrix.getElement(i, n);
                for (int j = 0; j < n; j++) {
                    if (j != i)
                        nextsolution[i] -= augMatrix.getElement(i, j) * currentsolution[j];
                }
                nextsolution[i] /= augMatrix.getElement(i, i);
            }
            iterations++;
            if (converged(nextsolution, currentsolution, this.tol)) {
                System.out.println("Converged after " + (iterations + 1) + " iterations.");
                this.convergedAfter = iterations;
            }
            if (iterations == maxIterations) {
                break;
            }
            currentsolution = (double[]) nextsolution.clone();
        }
        return nextsolution;
    }

    private static boolean converged(double[] current, double[] previous, double tolerance) {
        double sum = 0.0;
        for (int i = 0; i < current.length; i++) {
            sum += Math.pow(current[i] - previous[i], 2);
        }
        double norm = Math.sqrt(sum);
        return norm < tolerance;
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

    @Override
    public ArrayList<Double> solve() {
        // TODO Auto-generated method stub
        ArrayList<Double> doubleList = new ArrayList<>();
        double[] solution = this.evaluate();

        for (double value : solution) {
            doubleList.add(Matrix.roundToSignificantFigures(value, Matrix.significantFigures));
        }
        // System.out.println(
        // "----------------------------------------------------------------------------------------------------------------------");
        // System.out.println(doubleList);

        return doubleList;
    }
}
