package com.CSED26.Numercal.Project.Factory.Methods;


import com.CSED26.Numercal.Project.Matrix;

import java.util.ArrayList;

import com.CSED26.Numercal.Project.Matrix;
import com.CSED26.Numercal.Project.Factory.Numeric;

public class GauseSedil  extends Numeric{
        private Matrix coefficients;
        private  ArrayList<Double> arrayList;
        public static int maxIterations;

        public static double tolerance;
        public static int convergedAfter = 0;
        public GauseSedil(Matrix matrix,int maxIterations,double tolerance){
            this.arrayList=matrix.getColumn((matrix.getNumCols()-1));
            this.coefficients=matrix.deleteColumn(matrix.getNumCols()-1);
            GauseSedil.maxIterations = maxIterations;
            this.solve1(coefficients,arrayList ,maxIterations,tolerance);
        }
        public double[] solve1(Matrix coefficients , ArrayList < Double >arrayList, int maxIterations,double tolerance){
                int n = arrayList.size();
                double[] solution = new double[n];
                double[] previousSolution = new double[n];

                // Perform Gauss-Seidel iterations
                for (int iteration = 0; iteration < maxIterations; iteration++) {
                    System.arraycopy(solution, 0, previousSolution, 0, n);
                    System.out.println("iteration");
                    for (int i = 0; i < n; i++) {
                        double sum = arrayList.get(i);
                        for (int j = 0; j < n; j++) {
                            if (j != i) {
                                sum -= coefficients.getElement(i, j) * solution[j];
                            }
                        }
                        solution[i] = sum / coefficients.getElement(i, i);
                    }
                    if (converged(solution, previousSolution, tolerance)) {
                        System.out.println("Converged after " + (iteration + 1) + " iterations.");
                        if (convergedAfter==0) {
                            convergedAfter = iteration;
                        }
                        return solution;
                    }
                }
                return solution;
            }
        private static boolean converged(double[] current, double[] previous, double tolerance) {
            double sum = 0.0;
            for (int i = 0; i < current.length; i++) {
                sum += Math.pow(((current[i] - previous[i])/current[i]), 2);
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
                double[] solution = solve1(coefficients , arrayList, maxIterations, tolerance);
                for (double value : solution) {
                    doubleList.add(Matrix.roundToSignificantFigures(value, Matrix.significantFigures));
                }
                return doubleList;
        }
}