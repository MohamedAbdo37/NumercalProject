package com.CSED26.Numercal.Project.Factory.Methods.Iterations;

import com.CSED26.Numercal.Project.Expressionn;

import java.util.ArrayList;

public class Bisection extends Iterations {
    private String function;
    private double xl;
    private double xu;
    private double Ea;
    private double releror;
    public static int iterations;
    public static boolean converge;
    Expressionn exp;

    public Bisection(String function, double xl, double xu, double ea, double releror) {
        this.function = function;
        this.xl = xl;
        this.xu = xu;
        Ea = ea;
        this.releror = releror;
        converge = true;
        iterations = 0;
        Expressionn exp=new Expressionn();
    }
    @Override
    protected double iteration(double point, Expressionn exp) {
        return 0;
    }

    @Override
    public double getAnswers() {
        double xr = 0.0;
        ArrayList<Double> arr = new ArrayList<>();
        for (int i = 0; true; i++) {
            xr = (xl + xu) / 2.0;
            double fl = exp.substitute(xl);
            double fu = exp.substitute(xu);
            double fxr = exp.substitute(xr);
            arr.add(xr);
            double error = 0;
            if (i > 0) {
                error = Math.abs((arr.get(i) - arr.get(i - 1)) / arr.get(i)) * 100.0;
            }
            if (fxr < 0) {
                xu = xr;
            } else if (fxr > 0) {
                xl = xr;
            } else {
                iterations = i + 1;
                break;
            }

            if (error < releror && i > 0) {
                iterations = i + 1;
                break;
            }
            if (fl * fxr > 0 && fu * fxr > 0) {
                converge = false;
                break;
            }
        }

        return xr;
    }
    @Override
    public boolean evaluate() {
        return false;
    }

}
