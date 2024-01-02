package com.CSED26.Numercal.Project;

public class Derivative {

    private Expressionn expressionn;
    private Expressionn derivative;

    public Derivative(Expressionn e) {
        this.expressionn = e;
        this.evalute();
    }

    private void evalute() {
    }

    public void setExpressionn(Expressionn expressionn) {
        this.expressionn = expressionn;
    }

    public void setDerivative(Expressionn derivative) {
        this.derivative = derivative;
    }
    public Expressionn getExpressionn() {
        return expressionn;
    }
    public Expressionn getDerivative() {
        return derivative;
    }
    public double differentiation(double point){
        return 0;
    }

    private void polynomial(Expressionn exp){
        String term = Expressionn.extractContentWithinParentheses(exp.getTerms().get(0));
        if (!term.contains("x")) {
            this.derivative.addTerm("0");
            return;
        }
        String[] numStrings = term.split("x");
        if (!numStrings[1].contains("^")) {
            this.derivative.addTerm(numStrings[0]);
            return;
        }

        double power = Double.parseDouble(numStrings[1].split("^")[1]) ;
        double conffecent = Double.parseDouble(numStrings[0]) * power;
        term = "";
        term.concat(String.valueOf(conffecent));
        term.concat("x");
        term.concat(String.valueOf((power - 1)));
        this.derivative.addTerm(term);
    }

    private Expressionn exponential(Expressionn exp){
        String term = Expressionn.extractContentWithinParentheses(exp.getTerms().get(0));
        Expressionn e = new Expressionn();
        e.addTerm(term);
        Derivative d = new Derivative(e);
        term = "("+ d.getDerivative().getExpression() +")";
        term  = term.concat("*").concat(exp.getExpression());
        term = term.concat("ln(").concat("");
        return null;
    }

    private Expressionn trigonometric(Expressionn exp){
        return null;
    }

    // private Expressionn trigonometric(Expressionn exp){
    //     return null;
    // }

    private Expressionn multipleD(Expressionn exp){
        return null;
    }

    private Expressionn explicit(Expressionn exp){
        return null;
    }
}
