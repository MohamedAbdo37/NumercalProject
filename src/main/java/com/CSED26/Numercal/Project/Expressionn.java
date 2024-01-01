package com.CSED26.Numercal.Project;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.ArrayList;

public class Expressionn {
    public ArrayList<String> terms;

    public Expressionn() {
        terms = new ArrayList<>();
    }

    public void addTerm(String term) {
        terms.add(term);
    }
    public void setexpression(ArrayList<String> inputs){
        for(String input:inputs){
            addTerm(input);
        }
    }

    public Expressionn getTerm(int i) throws Exception {
        if (terms.isEmpty()) {
            throw new Exception("No terms in the expression.");
        }

        Expressionn termExpression = new Expressionn();
        termExpression.addTerm(terms.get(i));

        return termExpression;
    }

    public int noOfTerms() {
        return terms.size();
    }

    public double substitute(double val) {
        double result = 0.0;

        for (String term : terms) {
            result += evaluateTerm(term, val);
        }

        return result;
    }

    public void deleteTerm(String term) {
        terms.remove(term);
    }

    private double evaluateTerm(String term, double val) {
        Expression expression = new ExpressionBuilder(term)
                .variable("x")
                .build();
        expression.setVariable("x", val);
        return expression.evaluate();
    }
    public static void main(String[] args) {
        Expressionn expression = new Expressionn();
        
        // Example usage
        expression.addTerm("5x^10");
        expression.addTerm("sin(x)^3");
        expression.addTerm("log(x^2+5)");
        expression.addTerm("log(x)/log(e)"); // Natural logarithm

        System.out.println("Number of terms: " + expression.noOfTerms());

        try {
            Expressionn termExpression = expression.getTerm(2);
            System.out.println("Term expression: " + termExpression.terms.get(0));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        double substitutionResult = expression.substitute(2.0);
        System.out.println("Substitution result: " + substitutionResult);

        expression.deleteTerm("sin(x)^3");
        System.out.println("Number of terms after deletion: " + expression.noOfTerms());
    }
}