package com.CSED26.Numercal.Project.Factory.Methods.Iterations;

import com.CSED26.Numercal.Project.Derivative;
import com.CSED26.Numercal.Project.Expressionn;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.Queue;

public class Newton extends Iterations {
    private int Selector; // 0-->original , 1--> modified1 2-->modefied2
    private int MAX_ITERATIONS = 50;
    private double ea = 0.00001;
    private double initialguess;
    private int ConvergedAfter = -1;
    private Expressionn fx;
    private Queue<Double> xi;
    private Derivative derivative;
    private Queue <Double> ers;

    private int Significantfigures;
    private int multiplicity;
    private double diverged=0.0;
    private Long executiontime;

    public int getConvergedAfter() {
        return ConvergedAfter;
    }

    public Queue<Double> getXi() {
        return xi;
    }

    public Queue<Double> getErs() {
        return ers;
    }

    public double getDiverged() {
        return diverged;
    }

    public Long getExecutiontime() {
        return executiontime;
    }

    public Newton(int Selector, int multiplicity, double initialguess, Expressionn fx, int significantfigures) {
        this.Selector=Selector;
        this.multiplicity=multiplicity;
        this.initialguess = initialguess;
        this.fx = fx;
        this.derivative=new Derivative(fx);
        Significantfigures = significantfigures;
    }

    public Newton(int Selector,int multiplicity,int MAX_ITERATIONS, double ea, double initialguess, Expressionn fx,int significantfigures) {
        this.Selector=Selector;
        this.multiplicity=multiplicity;
        this.MAX_ITERATIONS = MAX_ITERATIONS;
        this.ea = ea;
        this.initialguess = initialguess;
        this.fx = fx;
        this.derivative=new Derivative(fx);
        Significantfigures = significantfigures;
    }

    private double original(int m)
    {
        ers=new LinkedList<>();
        xi=new LinkedList<>();
        double x0=initialguess;
        xi.add(chop_number(x0));
        double x1;
        double er=Double.MAX_VALUE;
        double erprev;
        if(MAX_ITERATIONS!=0)
        {   for(int i=1;i<MAX_ITERATIONS;i++)
          {
              diverged=1.0;
              x1=x0-m*(fx.substitute(x0)/derivative.first(x0));
              er=Math.abs(((x1-x0)/x0)*100);
              x0=x1;
              xi.add(chop_number(x0));
              ers.add(er);
              if(er<ea)
              {
                  diverged=0.0;
                 ConvergedAfter=i;
                  break;
              }

          }
        }
        else
        {
            int i=0;
            while (true)
            {
                erprev=er;
                x1=x0-m*(fx.substitute(x0)/derivative.first(x0));
                er=Math.abs(((x1-x0)/x0)*100);
                x0=x1;
                xi.add(chop_number(x0));
                ers.add(er);
                if(er<ea)
                {
                    diverged=0.0;
                    ConvergedAfter=i;
                    break;
                }
                if(erprev<er)
                {
                    diverged=1.0;
                    break;
                }
                i++;

            }

        }
          return x0;
    }

    private double modified2(){
        ers=new LinkedList<>();
        xi=new LinkedList<>();
        double x0=initialguess;
        xi.add(chop_number(x0));
        double x1;
        double er=Double.MAX_VALUE;
        double erprev;
        if(MAX_ITERATIONS!=0)
        {   for(int i=1;i<MAX_ITERATIONS;i++)
        {
            diverged=1.0;
            x1=x0-((fx.substitute(x0)*derivative.first(x0))/((derivative.first(x0)*derivative.first(x0))-(fx.substitute(x0)*derivative.second(x0))));
            er=Math.abs(((x1-x0)/x0)*100);
            x0=x1;
            xi.add(chop_number(x0));
            ers.add(er);
            if(er<ea)
            {
                diverged=0.0;
                ConvergedAfter=i;
                break;
            }

        }
        }
        else
        {
            int i=0;
            while (true)
            {
                erprev=er;
                x1=x0-((fx.substitute(x0)*derivative.first(x0))/((derivative.first(x0)*derivative.first(x0))-(fx.substitute(x0)*derivative.second(x0))));
                er=Math.abs(((x1-x0)/x0)*100);
                x0=x1;
                xi.add(chop_number(x0));
                ers.add(er);
                if(er<ea)
                {
                    diverged=0.0;
                    ConvergedAfter=i;
                    break;
                }
                if(erprev<er)
                {
                    diverged=1.0;
                    break;
                }
                i++;

            }

        }
        return x0;
    }
    public double solve_original()
    {
        return original(1);
    }
    public double solve_modified1(int m)
    {
        return original(m);
    }
    public double solve_modified2()
    {
        return modified2();
    }

    private Double chop_number(double value) {

        if (Double.isInfinite(value) || Double.isNaN(value) || Significantfigures <= 0) {
            throw new IllegalArgumentException("Invalid input");
        }

        BigDecimal bd = BigDecimal.valueOf(value);
        int scale = Significantfigures - bd.precision() + bd.scale();

        if (scale > 0) {
            bd = bd.setScale(scale, RoundingMode.HALF_UP);
        } else {
            bd = bd.round(new java.math.MathContext(Significantfigures, RoundingMode.HALF_UP));
        }

        return bd.doubleValue();

    }

    @Override
    protected double iteration(double point, Expressionn exp) {
        return 0;
    }

    @Override
    public double getAnswers() {
        long begin = System.currentTimeMillis();
        double res;
        if(Selector==0)
        {
            res= solve_original();
        }
        else if(Selector==1)
        {
             res= solve_modified1(multiplicity);
        } else if (Selector==2) {
            res= solve_modified2();
        }else
        {
            throw new RuntimeException("Selector take values from 0 to 2");
        }

        executiontime= System.currentTimeMillis()-begin;
      return res;
    }

    @Override
    public boolean evaluate() {
        return false;
    }
}
