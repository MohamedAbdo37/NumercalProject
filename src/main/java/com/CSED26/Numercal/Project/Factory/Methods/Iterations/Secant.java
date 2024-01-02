package com.CSED26.Numercal.Project.Factory.Methods.Iterations;

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

    public Secant(double xl, double xu, double Ea, int maxIterations, Expressionn ex) {
        this.Xl = xl;
        this.Xu = xu;
        this.maxError = Ea;
        this.maxIterations = maxIterations;
        this.numOfIterations = 1;
        this.expressionn = ex;
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
        System.out.println("ldskjflksjdfljsldkjflskjdflkjsldkfjlskdjflksjdflkjsdlkfjlsdkjf");
        this.Fl = iteration(this.Xl, this.expressionn);
        this.Fu = iteration(this.Xu, this.expressionn);
        this.Xr = ((this.Fu * this.Xl) - (this.Fl * this.Xu)) / (this.Fu - this.Fl);
        double error = errorCalc(this.Xr, this.Xu);
        System.out.println(this.Xr + " " + this.Xu + " " + error + "  " + this.maxError + " " + this.maxIterations);

        for (this.numOfIterations = 1; this.numOfIterations <= this.maxIterations
                && error > this.maxError; this.numOfIterations++) {
            System.out.println(this.Xu);
            System.out.println("------------------------------------------------------------------------");
            this.Xl = this.Xu;
            this.Xu = this.Xr;
            this.Fl = iteration(this.Xl, this.expressionn);
            this.Fu = iteration(this.Xu, this.expressionn);
            this.Xr = ((this.Fu * this.Xl) - (this.Fl * this.Xu)) / (this.Fu - this.Fl);
            error = errorCalc(this.Xr, this.Xu);
        }

        this.answer = this.Xr;
        return false;
    }
}
