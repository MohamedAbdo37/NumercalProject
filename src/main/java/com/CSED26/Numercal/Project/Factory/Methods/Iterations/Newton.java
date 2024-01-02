package com.CSED26.Numercal.Project.Factory.Methods.Iterations;

import com.CSED26.Numercal.Project.Expressionn;

public class Newton extends Iterations {
   int MAX_ITERATIONS=50;
    double ea=0.001;
    double initialguess;
    int ConvergedAfter=-1;
    Expressionn fx;
    Expressionn fxdx;
    Expressionn fxdx2;
    Queue <Double> xi;

    Queue <Double> ers;

    int Significantfigures;

    public int getConvergedAfter() {
        return ConvergedAfter;
    }


    public Queue<Double> getXi() {
        return xi;
    }

    public Queue<Double> getErs() {
        return ers;
    }

    public Newton(double initialguess, Expressionn fx, Expressionn fxdx, Expressionn fxdx2, int significantfigures) {
        this.initialguess = initialguess;
        this.fx = fx;
        this.fxdx = fxdx;
        this.fxdx2 = fxdx2;
        Significantfigures = significantfigures;
    }

    public Newton(int MAX_ITERATIONS, double ea, double initialguess, Expressionn fx, Expressionn fxdx, Expressionn fxdx2, int significantfigures) {
        this.MAX_ITERATIONS = MAX_ITERATIONS;
        this.ea = ea;
        this.initialguess = initialguess;
        this.fx = fx;
        this.fxdx = fxdx;
        this.fxdx2 = fxdx2;
        Significantfigures = significantfigures;
    }

    private void original(int m)
    {
        ers=new LinkedList<>();
        xi=new LinkedList<>();
        double x0=initialguess;
        xi.add(chop_number(x0));
        double x1;
        double er;
          for(int i=1;i<MAX_ITERATIONS;i++)
          {
              x1=x0-m*(fx.substitute(x0)/fxdx.substitute(x0));
              er=Math.abs(((x1-x0)/x0)*100);
              x0=x1;
              xi.add(chop_number(x0));
              ers.add(er);
              if(er<ea)
              {
                 ConvergedAfter=i;
                  break;
              }
          }
    }

    private void modified2(){
        ers=new LinkedList<>();
        xi=new LinkedList<>();
        double x0=initialguess;
        xi.add(chop_number(x0));
        double x1;
        double er;
        for(int i=1;i<MAX_ITERATIONS;i++)
        {
            x1=x0-((fx.substitute(x0)*fxdx.substitute(x0))/(fxdx.substitute(x0)*fxdx.substitute(x0)-fx.substitute(x0)*fxdx2.substitute(x0)));
            er=Math.abs(((x1-x0)/x0)*100);
            x0=x1;
            xi.add(chop_number(x0));
            ers.add(er);
            if(er<ea)
            {
                ConvergedAfter=i;
                break;
            }
        }
    }
    public void solve_original()
    {
        original(1);
    }
    public void solve_modified1(int m)
    {
        original(m);
    }
    public void solve_modified2()
    {
        modified2();
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
        return 0;
    }

    @Override
    public boolean evaluate() {
        return false;
    }
}
