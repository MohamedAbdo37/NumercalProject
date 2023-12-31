package com.CSED26.Numercal.Project;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.CSED26.Numercal.Project.Factory.Numeric;
import com.CSED26.Numercal.Project.Factory.Methods.GauseSedil;
import com.CSED26.Numercal.Project.Factory.Methods.GaussElimination;
import com.CSED26.Numercal.Project.Factory.Methods.GaussJordan;
import com.CSED26.Numercal.Project.Factory.Methods.Jacobi;
import com.CSED26.Numercal.Project.Factory.Methods.LUDeomp;
import com.CSED26.Numercal.Project.Factory.Methods.Iterations.Bisection;
import com.CSED26.Numercal.Project.Factory.Methods.Iterations.FalsePosition;
import com.CSED26.Numercal.Project.Factory.Methods.Iterations.FixedPoint;
import com.CSED26.Numercal.Project.Factory.Methods.Iterations.Newton;
import com.CSED26.Numercal.Project.Factory.Methods.Iterations.Secant;

public class Solver {
    private static final String String = null;
    private List<ArrayList<String>> variables = new ArrayList<>();
    private List<Double> answers = new ArrayList<>();
    private ArrayList<ArrayList<Float>> coefficients = new ArrayList<>();
    private Matrix matrix;
    private int niteration;
    private double tolerance;
    private Expressionn nonLinearExpression;
    private Secant secant;
    private Newton newton;

    public Numeric getMethod(String type) {
        if (type == null) {
            return null;
        }

        else if (type.equalsIgnoreCase("G")) {

            return new GaussElimination(matrix);
        } else if (type.equalsIgnoreCase("GJ")) {
            return new GaussJordan(matrix);
        } else if (type.equalsIgnoreCase("LU")) {
            return new LUDeomp(matrix);
        } else if (type.equalsIgnoreCase("GS")) {
            return new GauseSedil(matrix, getIteration(), getTolerance());
        } else if (type.equalsIgnoreCase("J")) {
            return new Jacobi(matrix, getIteration(), getTolerance());
        }
        return null;
    }

    public void setIteration(int niteration) {
        this.niteration = niteration;
    }

    public int getIteration() {
        return niteration;
    }

    public void setTolerance(double tolerance) {
        this.tolerance = tolerance;
    }

    public double getTolerance() {
        return tolerance;
    }

    public String getAnswer() {
        String answer = "";
        int l = 0;
        for (Double i : this.answers) {
            answer = answer.concat(this.variables.get(0).get(l));
            answer = answer.concat("=");
            answer = answer.concat(String.valueOf(Matrix.roundToSignificantFigures(i, Matrix.significantFigures)));
            answer = answer.concat(",");
            l++;
        }
        return answer;
    }

    public Matrix parseLinearEquation(String eq) {
        List<ArrayList<Double>> coefficients = new ArrayList<>();
        List<Double> constants = new ArrayList<>();

        String[] equations = eq.split("&");
        for (String equation : equations) {
            String[] terms = equation.split("(?=[-+])");
            ArrayList<Double> equationCoefficients = new ArrayList<>();
            ArrayList<String> ec = new ArrayList<>();

            for (int i = 0; i < terms.length; i++) {

                String term = terms[i].trim();
                if (term.matches(".*[a-z].*")) {
                    String variable;
                    String coefficientString;
                    if (term.contains("=")) {

                        String[] parts = term.split("=");
                        // System.out.println(parts[0].trim().length() - 1);
                        variable = parts[0].substring(parts[0].trim().length() - 1);
                        // System.out.println(variable);

                        coefficientString = parts[0].substring(0, parts[0].trim().length() - 1);
                        // System.out.println(coefficientString);
                    } else {
                        // while()
                        // System.out.println(term.trim().length() - 1);
                        variable = term.substring(term.trim().length() - 1);
                        // System.out.println(variable);

                        coefficientString = term.substring(0, term.trim().length() - 1);

                        // System.out.println(coefficientString);
                    }
                    try {

                        double coefficient;
                        if (coefficientString.isEmpty()) {
                            coefficient = 1.0;
                        } else if (coefficientString.equalsIgnoreCase("+")) {
                            coefficient = 1.0;
                        } else if (coefficientString.equalsIgnoreCase("-")) {
                            coefficient = -1.0;
                        } else {
                            coefficient = Double.parseDouble(coefficientString);
                        }
                        ec.add(variable);
                        equationCoefficients.add(coefficient);
                    } catch (NumberFormatException e) {
                        if (term.contains("=")) {
                            String[] parts = term.split("=");
                            // System.out.println(parts[0].trim().length() - 1);
                            variable = parts[0].substring(parts[0].trim().length() - 2);
                            // System.out.println(variable);

                            coefficientString = parts[0].substring(0, parts[0].trim().length() - 2);
                            // System.out.println(coefficientString);
                        } else {
                            // while()
                            // System.out.println(term.trim().length() - 1);
                            variable = term.substring(term.trim().length() - 2);
                            // System.out.println(variable);

                            coefficientString = term.substring(0, term.trim().length() - 2);

                            // System.out.println(coefficientString);
                        }
                        ec.add(variable);
                        double coefficient;
                        if (coefficientString.isEmpty()) {
                            coefficient = 1.0;
                        } else if (coefficientString.equalsIgnoreCase("+")) {
                            coefficient = 1.0;
                        } else if (coefficientString.equalsIgnoreCase("-")) {
                            coefficient = -1.0;
                        } else {
                            coefficient = Double.parseDouble(coefficientString);
                        }

                        equationCoefficients.add(coefficient);
                    }
                }
            }

            variables.add(ec);
            coefficients.add(equationCoefficients);
            String constantString = equation.split("=")[equation.split("=").length - 1].trim();
            double constant = Double.parseDouble(constantString);
            constants.add(constant);
        }
        int b = 0;
        int l = 0;
        int i = 0;
        int v = 0;
        int maxlen = 0;
        for (int e = 0; e < variables.size(); e++) {
            if (variables.get(e).size() > maxlen) {
                maxlen = variables.get(e).size();
            }
        }
        while (v == 0) {
            try {
                for (l = 0; l < variables.size(); l++) {
                    if (variables.get(l).size() == maxlen) {
                        for (i = 0; i < maxlen; i++) {
                            for (b = 0; b < variables.size(); b++) {
                                if (!variables.get(l).get(i).equalsIgnoreCase(variables.get(b).get(i))) {
                                    variables.get(b).add(i, variables.get(l).get(i));
                                    coefficients.get((b)).add(i, 0.0);
                                }
                            }
                        }
                    }
                    v = 1;
                }
            } catch (IndexOutOfBoundsException e) {
                variables.get(b).add(variables.get(l).get(i));
                coefficients.get((b)).add(0.0);
                v = 0;
            }
        }

        // Print the updated values
        System.out.println("Variables: " + variables);
        System.out.println("Coefficients: " + coefficients);
        System.out.println("Constants: " + constants);
        Matrix cof = new Matrix(coefficients.size(), coefficients.get(0).size());
        for (int z = 0; z < cof.getNumRows(); z++) {
            cof.setRow(z, coefficients.get(z));
        }
        cof.addColumn(constants);
        this.matrix = cof;
        b = 0;
        l = 0;
        i = 0;
        v = 0;
        maxlen = 0;
        return cof;
    }

    public void solve(Numeric method) {
        this.answers = method.solve();
    }

    public void solveLU(Numeric method, String type) {
        switch (type) {
            case "do":
                this.answers = ((LUDeomp) method).doLittle();
                break;
            case "Court":
                this.answers = ((LUDeomp) method).crout();
                break;

            case "Chelsky":
                this.answers = ((LUDeomp) method).cholesky();
                break;

        }
    }

    public void parseNonLinearEquation(String[] equations) {
        this.nonLinearExpression = new Expressionn();
        this.nonLinearExpression.setexpression(equations);
    }

    public double[] solveByFixedPoint(double point, int maxIterations, int significantFigures,
            double tol) {
        if (tol == 0)
            tol = 0.00001;
        if (significantFigures == 0)
            significantFigures = 5;
        FixedPoint fixedPoint = new FixedPoint(point, this.nonLinearExpression, maxIterations, significantFigures, tol);
        fixedPoint.evaluate();
        double[] answer = { fixedPoint.getAnswers(), fixedPoint.getExcutiontime(), fixedPoint.convergedAfter };
        return answer;
    }

    public double solveBySecant(double x0, double x1, double εa, int noIter, int significantFigures) {
        secant = new Secant(x0, x1, εa, noIter, this.nonLinearExpression, significantFigures);
        secant.evaluate();
        return secant.getAnswers();
    }

    public double getSecantTimeOfExecution() {
        return secant.getTimeOfExecution();
    }

    public double getSecantTimeOfConvergence() {
        return secant.getTimeOfConvergence();
    }

    public double getSecantNoOfIterations() {
        return secant.getNoOfIterations();
    }

    public double[] solveByONewton(int Selector, int multiplicity, int MAX_ITERATIONS, double ea, double initialguess,
            int significantfigures) {
        newton = new Newton(Selector, multiplicity, MAX_ITERATIONS, ea, initialguess, this.nonLinearExpression,
                significantfigures);
        double[] r = { newton.getAnswers(), newton.getExecutiontime(), newton.getConvergedAfter() };
        return r;
    }

    public double getONewtonTimeOfExecution() {
        return (double) newton.getExecutiontime();
    }

    public double getONewtonNoOfIterations() {
        return newton.getConvergedAfter();
    }

    public double[] BiSolver(double xl, double xu, double releror, double ea) {
        Bisection bi = new Bisection(this.nonLinearExpression, xl, xu, releror, releror);
        double[] r = { bi.getAnswers(), bi.timer, bi.iterations };
        return r;
    }

    public double[] falseSolver(double xl, double xu, double releror, double ea) {
        FalsePosition fp = new FalsePosition(this.nonLinearExpression, xl, xu, releror, releror);
        double[] r = { fp.getAnswers(), fp.timer, fp.iterations };
        return r;
    }
}
