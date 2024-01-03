package com.CSED26.Numercal.Project.Factory.Methods.Iterations;

import com.CSED26.Numercal.Project.Expressionn;
import com.CSED26.Numercal.Project.Matrix;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class FalsePosition extends Iterations{
    private String function;
    private double xl;
    private double xu;
    private double Ea;
    private double releror;
    public static long timer;
    public static int iterations;
    public static boolean converge;
    Expressionn exp;
    private Queue<Double> qxr;
    private  Queue<Double> qxl;
    private Queue<Double> qxu;
    private Queue<Double> qfxr;
    private Queue<Double> qfxl;
    private Queue<Double> qfxu;
    private  int significantFigures = 5;
    public Queue<Double> getQxr() {
        return qxr;
    }

    public Queue<Double> getQxl() {
        return qxl;
    }

    public Queue<Double> getQxu() {
        return qxu;
    }

    public Queue<Double> getQfxr() {
        return qfxr;
    }

    public Queue<Double> getQfxl() {
        return qfxl;
    }

    public Queue<Double> getQfxu() {
        return qfxu;
    }




    public FalsePosition(Expressionn exp, double xl, double xu, double ea, double releror) {
        this.function = function;
        this.xl = xl;
        this.xu = xu;
        Ea = ea;
        this.releror = releror;
        converge = true;
        iterations = 0;
        this.exp = exp;
        qxr= new LinkedList<>();
        qxl= new LinkedList<>();
        qxu= new LinkedList<>();
        qfxr= new LinkedList<>();
        qfxl= new LinkedList<>();
        qfxu= new LinkedList<>();
    }
    @Override
    protected double iteration(double point, Expressionn exp) {
        return 0;
    }

    @Override
    public double getAnswers() {
        long begin = System.currentTimeMillis();
        long end = 0;
        double xr = 0.0;
        ArrayList<Double> arr = new ArrayList<>();
        for (int i = 0; true; i++) {
            double error = 0.0;
            double fl = exp.substitute(xl);
            double fu = exp.substitute(xu);
            xr = (xl * fu - xu * fl) / (fu - fl);
            xr= Matrix.roundToSignificantFigures(xr, this.significantFigures);
            double fxr = exp.substitute(xr);
            arr.add(xr);
            qfxr.add(fxr);
            qfxu.add(fu);
            qfxl.add(fl);
            qxu.add(xu);
            qxl.add(xl);
            qxr.add(xr);
            if (i > 0) {
                error = Math.abs((arr.get(i) - arr.get(i - 1)) / arr.get(i)) * 100.0;
            }
            if (fxr < 0) {
                xu = xr;
            } else if (fxr > 0) {
                xl = xr;
            } else {
                iterations = i + 1;
                end = System.currentTimeMillis();
                break;
            }

            if (error < releror && i > 0) {
                iterations = i + 1;
                end = System.currentTimeMillis();
                break;
            }
            if ((fl * fxr > 0 && fu * fxr > 0) ||xr==0) {
                iterations=i+1;
                converge = false;
                end = System.currentTimeMillis();
                break;
            }
        }
        FalsePosition.timer=begin-end;
        return xr;
    }


    @Override
    public boolean evaluate() {
        return false;
    }
}
