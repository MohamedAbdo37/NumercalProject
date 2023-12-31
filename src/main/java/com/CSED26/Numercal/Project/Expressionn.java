package com.CSED26.Numercal.Project;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;

public class Expressionn {
    private ArrayList<String> terms;

    public ArrayList<String> getTerms() {
        return terms;
    }

    public Expressionn() {
        terms = new ArrayList<>();
    }

    public void addTerm(String term) {
        terms.add(term);
    }

    public void setexpression(String[] inputs) {
        for (String input : inputs) {
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
        if (term.contains("ln")) {
            Expressionn exp = new Expressionn();
            term = exp.convertLnToLog(term);
            System.out.println(term);
        }
        Expression expression = new ExpressionBuilder(term)
                .variable("x")
                .build();
        expression.setVariable("x", val);
        try {
             return expression.evaluate();
        }
         catch (ArithmeticException e) {
            return -1;
        }
        catch (Exception e) {
            return -2;
        }
      
    }

    public static String convertLnToLog(String term) {
        String convertedTerm = term.replaceAll("ln", "log") + "/log(e)";
        return convertedTerm;
    }

    public static String extractContentWithinParentheses(String input) {
        Pattern pattern = Pattern.compile("\\(([^\\)]+)\\)");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return matcher.group(1);
        }

        return null;
    }

    public static String gettype(String input) {
        if (input.contains("ln")) {
            return "ln";
        } else if (input.contains("sin") || input.contains("cos") || input.contains("tan")) {
            return "trig";
        } else {
            return "polynomial";
        }
    }

    public static void main(String[] args) {
        Expressionn expression = new Expressionn();

        // Example usage
       expression.addTerm("1/x");

        System.out.println("Number of terms: " + expression.noOfTerms());

        try {
            Expressionn termExpression = expression.getTerm(0);
            System.out.println("Term expression: " + termExpression.terms.get(0));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        double substitutionResult = expression.substitute(0);
        System.out.println("Substitution result: " + substitutionResult);

        expression.deleteTerm("sin(x)^3");
        System.out.println("Number of terms after deletion: " + expression.noOfTerms());

        System.out.println(expression.extractContentWithinParentheses("ln(x+5xx)"));
    }

    public String getExpression() {
        return null;
    }
}