package com.CSED26.Numercal.Project.Factory.Methods.Iterations;

import com.CSED26.Numercal.Project.Expressionn;
import com.CSED26.Numercal.Project.Factory.Numeric;
import com.CSED26.Numercal.Project.Matrix;

import java.util.LinkedList;
import java.util.Queue;

public class FixedPoint extends Iterations {

    private int maxIterations = 50;
    private double tol = 0.00001;
    private  int significantFigures = 5;

    private Expressionn exp;
    private double initialGuess;

    private long excutiontime;

    private Queue<Double> queue = new LinkedList<>();

    public static int convergedAfter = 0;

    public FixedPoint(double point, Expressionn exp, int maxIterations, int significantFigures, double tol) {
        this.initialGuess = point;
        this.exp = exp;
        convergedAfter = 0;
        if(maxIterations != 0)
            this.maxIterations = maxIterations;
        if(tol != 0)
            this.tol = tol;
        if(significantFigures != 0)
            this.significantFigures = significantFigures;
    }

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
            this.queue.add(solution);
            if(((Math.abs(solution - prev) / solution) * 100) <= tol) {
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
        long start = System.currentTimeMillis();
        double solution =  iteration(this.initialGuess, this.exp);
        long end = System.currentTimeMillis();
        this.excutiontime = end - start;
        return solution;
    }

    @Override
    public boolean evaluate() {
        return false;
    }

    public long getExcutiontime() {
        return excutiontime;
    }

    public Queue<Double> getQueue() {
        return queue;
    }

    public static void main(String[] args) {
        Expressionn exp = new Expressionn();
        exp.addTerm("1+1/x");
        FixedPoint fp = new FixedPoint(2, exp, 100, 10, 0);
        System.out.println(fp.getAnswers());
        System.out.println(fp.getExcutiontime());
    }
}
