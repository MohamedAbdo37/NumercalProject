package numbericalproject.demo.service;

public class GauseSedil {
        // Evaluate the system of linear equations
        public static double[] evaluate(double[][] coefficients, double[] variables) {
            int n = coefficients.length;
            double[] result = new double[n];

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    result[i] += coefficients[i][j] * variables[j];
                }
            }

            return result;
        }

        // Solve the system of linear equations using Gauss-Seidel method
        public static double[] solve(double[][] coefficients, double[] constants, int maxIterations, double tolerance) {
            int n = constants.length;
            double[] solution = new double[n];
            double[] previousSolution = new double[n];

            // Perform Gauss-Seidel iterations
            for (int iteration = 0; iteration < maxIterations; iteration++) {
                System.arraycopy(solution, 0, previousSolution, 0, n);

                for (int i = 0; i < n; i++) {
                    double sum = constants[i];
                    for (int j = 0; j < n; j++) {
                        if (j != i) {
                            sum -= coefficients[i][j] * solution[j];
                        }
                    }
                    solution[i] = sum / coefficients[i][i];
                }

                // Check for convergence
                if (converged(solution, previousSolution, tolerance)) {
                    System.out.println("Converged after " + (iteration + 1) + " iterations.");
                    return solution;
                }
            }

            System.out.println("Did not converge within the specified number of iterations.");
            return solution;
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
}
