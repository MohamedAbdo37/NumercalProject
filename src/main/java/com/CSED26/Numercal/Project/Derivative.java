package com.CSED26.Numercal.Project;

public class Derivative {

    private Expressionn expressionn;
    private double h = 0.1;

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public Derivative(Expressionn expressionn) {
        this.expressionn = expressionn;
    }

    public Derivative(Expressionn expressionn, double h) {
        this.expressionn = expressionn;
        this.h = h;
    }

    public double first(double value) {
        double b2 = expressionn.substitute(value - 2 * h);
        double b1 = expressionn.substitute(value - h);
        double f1 = expressionn.substitute(value + h);
        double f2 = expressionn.substitute(value + 2 * h);

        double numerator = -f2 + (8 * f1) - (8 * b1) + b2;
        return numerator / (12 * h);
    }

    public double second(double value) {
        double b2 = expressionn.substitute(value - 2 * h);
        double b1 = expressionn.substitute(value - h);
        double i = expressionn.substitute(value);
        double f1 = expressionn.substitute(value + h);
        double f2 = expressionn.substitute(value + 2 * h);

        double numerator = -f2 + (16 * f1) - (30 * i) + (16 * b1) - b2;
        return numerator / (12 * h * h);
    }

}
