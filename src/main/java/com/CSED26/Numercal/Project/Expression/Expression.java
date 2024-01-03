package com.CSED26.Numercal.Project.Expression;

import java.util.ArrayList;
import java.util.List;

public class Expression {
    private List<Term> terms ;

    public Expression(){
        this.terms = new ArrayList<>();
    }

    public Expression(String[] terms){
        this.terms = new ArrayList<>();
        for (String term : terms) {
            this.addTerm(term);
        }
    }

    public Expression(String term) {
    }

    public String getExpression(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.terms.size()-1; i++) {
            sb.append(this.terms.get(i).toString());
            sb.append("+");
        }
        sb.append(this.terms.get(this.terms.size()-1).toString());
        return sb.toString().replaceAll("+-","-");
    }

    public double substitute(double value){
        double result = 0.0;
        for (Term term : terms) {
            result += term.substitute(value);
        }
        return result;
    }

    public int size(){
        return this.terms.size();
    }

    public void addTerm(String term){
        this.terms.add(new Term(term));
    }
}
