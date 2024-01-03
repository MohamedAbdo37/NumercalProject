package com.CSED26.Numercal.Project.Expression;

public class Term {
    private String term;
    private String type;
    private double cofficent;
    private String variable;
    private double power;
    private Expression inside;

    public Term(String term) {
        this.term = term;
        this.setCofficent();
        this.setType();
        this.setVariable();
        this.setPower();
    }

    // Setters
    private void setCofficent() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < term.length(); i++) {
            if(String.valueOf(term.charAt(i)).matches("[a-zA-Z]")){
                this.term = this.term.substring(i);
                break;
            }
            if (term.charAt(i) == '(') {
                this.term = this.term.substring(i);
                break;
            }
            sb.append(term.charAt(i));
        }
        this.cofficent = Double.parseDouble(sb.toString());
    }

    private void setVariable() {

    }

    private void setPower() {
    }

    private void setType() {
        if (this.term.toLowerCase().contains("sin")) {
            this.type = "sin";
            this.term = this.term.replace("sin", "");
            this.inside = new Expression(this.term);
        }
        if (this.term.toLowerCase().contains("cos")) {
            this.type = "cos";
            this.term = this.term.replace("cos", "");
            this.inside = new Expression(this.term);

        }
        if (this.term.toLowerCase().contains("tan")) {
            this.type = "tan";
            this.term = this.term.replace("tan", "");
            this.inside = new Expression(this.term);
        }
        if (this.term.toLowerCase().contains("ln")) {
            this.type = "ln";
            this.term = this.term.replace("ln", "");
            this.inside = new Expression(this.term);
        }
        if (this.term.toLowerCase().contains("log")) {
            this.type = "log";
            this.term = this.term.replace("log", "");
            this.inside = new Expression(this.term);
        }
        if (this.term.toLowerCase().contains("e^(")) {
            this.type = "exp-e";
            this.term = this.term.replace("e^", "");
            this.inside = new Expression(this.term);
        }
        if (this.term.toLowerCase().contains("^(")) {
            this.type = "exp";
            int open = 0;
            for (int i = 0; i < term.length(); i++) {
                if (term.charAt(i) == '(') {
                    open = i;
                }
                if (term.charAt(i) == ')') {
                    this.type = this.type + String.valueOf('-');
                    for (int j = open + 1; j < i; j++) {
                        this.type = this.type + String.valueOf(term.charAt(j));
                    }
                    open = i;
                    break;
                }
            }
            this.inside = new Expression(term);

        } else
            this.type = "poly";

    }

    // Getters

    public String getVariable() {
        return variable;
    }

    public double getCofficent() {
        return cofficent;
    }
    public Expression getInside() {
        return inside;
    }
    public double getPower() {
        return power;
    }

    public String getType() {
        return type;
    }
    ///

    public double substitute(double value) {
        return 0;
    }

    @Override
    public String toString() {
        return this.term;
    }
}
