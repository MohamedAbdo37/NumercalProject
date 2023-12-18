package com.CSED26.Numercal.Project.Factory.Methods;

import java.util.ArrayList;

import com.CSED26.Numercal.Project.Matrix;
import com.CSED26.Numercal.Project.Factory.Numeric;

public class GauseSedil  extends Numeric{

        private Matrix augMatrix;
        public static int maxIterations;
        public static double tolerance;

        public GauseSedil(Matrix matrix){
            this.solve(matrix.deleteColumn(matrix.getNumCols()-1), matrix.getColumn((matrix.getNumCols()-1)));
        }

        // Evaluate the system of linear equations
        public static double[] evaluate(Matrix coefficients, double[] variables) {
            int n = coefficients.getNumRows();
            double[] result = new double[n];

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    result[i] += coefficients.getElement(i,j) * variables[j];
                }
            }

            return result;
        }

        // Solve the system of linear equations using Gauss-Seidel method
        public double[] solve(Matrix coefficients, ArrayList<Double> arrayList) {
            int n = arrayList.size();
            double[] solution = new double[n];
            double[] previousSolution = new double[n];

            // Perform Gauss-Seidel iterations
            for (int iteration = 0; iteration < maxIterations; iteration++) {
                System.arraycopy(solution, 0, previousSolution, 0, n);

                for (int i = 0; i < n; i++) {
                    double sum = arrayList.get(i);
                    for (int j = 0; j < n; j++) {
                        if (j != i) {
                            sum -= coefficients.getElement(i,j) * solution[j];
                        }
                    }
                    solution[i] = sum / coefficients.getElement(i,i);
                }

                // Check for convergence
                if (converged(solution, previousSolution, tolerance)) {
                    System.out.println("Converged after " + (iteration + 1) + " iterations.");
                    return solution;
                }
            }

            return  solution;
        }

        // Check for convergence based on the Euclidean norm
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
            throw new UnsupportedOperationException("Unimplemented method 'solve'");
        }
}
