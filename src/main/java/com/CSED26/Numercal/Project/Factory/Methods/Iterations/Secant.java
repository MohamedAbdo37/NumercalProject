package com.CSED26.Numercal.Project.Factory.Methods.Iterations;

import java.math.BigDecimal;
import java.math.MathContext;

import com.CSED26.Numercal.Project.Expressionn;

public class Secant extends Iterations {
    private double Xl;
    private double Fl;
    private double Xu;
    private double Fu;
    private double Xr;
    private double maxError;
    private int maxIterations;
    private int numOfIterations;
    private double answer;
    private Expressionn expressionn;
    private int significantFigures;
    private long convTime;
    private long timeOfExecution;

    public Secant(double xl, double xu, double Ea, int maxIterations, Expressionn ex, int significantFigures) {
        this.Xl = xl;
        this.Xu = xu;
        if (Ea != 0)
            this.maxError = Ea;
        else
            this.maxError = 0.0005;
        if (maxIterations != 0)
            this.maxIterations = maxIterations;
        else
            this.maxIterations = 1000;
        this.numOfIterations = 0;
        this.convTime = 0;
        this.expressionn = ex;
        if (significantFigures != 0)
            this.significantFigures = significantFigures;
        else
            this.significantFigures = 5;

    }

    private double errorCalc(double xr1, double xr2) {
        return Math.abs((xr1 - xr2) / xr1) * 100;
    }

    @Override
    protected double iteration(double point, Expressionn exp) {
        return exp.substitute(point);
    }

    @Override
    public double getAnswers() {
        return this.answer;
    }

    public int getNumOfIterations() {
        return this.numOfIterations;
    }

    @Override
    public boolean evaluate() {
        // estimating time
        long begin = System.currentTimeMillis();

        this.Fl = iteration(this.Xl, this.expressionn);
        this.Fu = iteration(this.Xu, this.expressionn);
        if (this.Fu - this.Fl != 0)
            this.Xr = ((this.Fu * this.Xl) - (this.Fl * this.Xu)) / (this.Fu - this.Fl);
        else
            return false;
        double error = errorCalc(this.Xr, this.Xu);
        System.out.println(this.Xr + " " + this.Xu + " " + error + "  " + this.maxError + " " + this.maxIterations);
        boolean checkConv = false;
        for (int i = 1; i <= this.maxIterations; i++) {
            if (error <= this.maxError && !checkConv) {
                // getting point of convergence
                long end = System.currentTimeMillis();
                this.convTime = end - begin;
                this.numOfIterations = i;
                checkConv = true;
            }
            this.Xl = this.Xu;
            this.Xu = this.Xr;
            this.Fl = iteration(this.Xl, this.expressionn);
            this.Fu = iteration(this.Xu, this.expressionn);
            if (this.Fu - this.Fl != 0)
                this.Xr = ((this.Fu * this.Xl) - (this.Fl * this.Xu)) / (this.Fu - this.Fl);
            else
                break;
            error = errorCalc(this.Xr, this.Xu);
        }
        // getting time of execution
        long end = System.currentTimeMillis();
        this.timeOfExecution = end - begin;

        // rounding to the least significat figures given or the default

        double d = this.Xr;
        BigDecimal bd = new BigDecimal(d);
        bd = bd.round(new MathContext(this.significantFigures));
        double rounded = bd.doubleValue();
        this.answer = rounded;
        return true;
    }

    public double getTimeOfExecution() {
        return (double) this.timeOfExecution;
    }

    public double getTimeOfConvergence() {
        return (double) this.convTime;
    }

    public double getNoOfIterations() {
        return (double) this.numOfIterations;
    }

}
