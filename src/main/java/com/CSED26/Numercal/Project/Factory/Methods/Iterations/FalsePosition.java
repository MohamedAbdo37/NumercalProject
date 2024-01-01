package com.CSED26.Numercal.Project.Factory.Methods.Iterations;

import com.CSED26.Numercal.Project.Expressionn;

public class FalsePosition extends Iterations{

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
