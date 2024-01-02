package com.CSED26.Numercal.Project.Factory.Methods.Iterations;

import com.CSED26.Numercal.Project.Expressionn;
import com.CSED26.Numercal.Project.Factory.Numeric;
import com.CSED26.Numercal.Project.Matrix;

public class FixedPoint extends Iterations {

    private int maxIterations = 50;
    private double tol = 0.00001;
    private  int significantFigures = 5;

    private Expressionn exp;
    private double initialGuess;

    public static int convergedAfter = 0;

    public FixedPoint(double point, Expressionn exp, int maxIterations, int significantFigures, double tol) {
        this.initialGuess = point;
        this.exp = exp;
        convergedAfter = 0;
        this.maxIterations = maxIterations;
        this.tol = tol;
        this.significantFigures = significantFigures;
    }
//    public FixedPoint(int maxIterations, int significantFigures, double tol) {
//        this.convergedAfter = 0;
//        this.maxIterations = maxIterations;
//        this.tol = tol;
//        this.significantFigures = significantFigures;
//    }
    public FixedPoint(double point, Expressionn exp) {
        this.initialGuess = point;
        this.exp = exp;
        convergedAfter = 0;
    }

    @Override
    protected double iteration(double point, Expressionn exp) {
        double prev = point;
        double solution = 0.0;
        for (int i = 0; i < maxIterations; i++) {
            solution = Matrix.roundToSignificantFigures(exp.substitute(prev), this.significantFigures);
            if(Math.abs(solution - prev) <= tol) {
                convergedAfter = i;
                return solution;
            }
            prev = Matrix.roundToSignificantFigures(solution, this.significantFigures);
            System.out.println(solution);
        }
        return solution;
    }

    @Override
    public double getAnswers() {
        return iteration(this.initialGuess, this.exp);
    }

    @Override
    public boolean evaluate() {
        return false;
    }

    public static void main(String[] args) {
        Expressionn exp = new Expressionn();
        exp.addTerm("1+1/x");
        FixedPoint fp = new FixedPoint(2, exp);
        System.out.println(fp.getAnswers());
        
    }
}