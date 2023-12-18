package com.CSED26.Numercal.Project;

import java.util.ArrayList;
import java.util.List;

import com.CSED26.Numercal.Project.Factory.Numeric;
import com.CSED26.Numercal.Project.Factory.Methods.GauseSedil;
import com.CSED26.Numercal.Project.Factory.Methods.GaussElimination;
import com.CSED26.Numercal.Project.Factory.Methods.GaussJordan;
import com.CSED26.Numercal.Project.Factory.Methods.Jacobi;
import com.CSED26.Numercal.Project.Factory.Methods.LUDeomp;


public class Solver {
    private static final String String = null;
    private List<ArrayList<String>> variables = new ArrayList<>();
    private List<Double> answers = new ArrayList<>();
    private ArrayList<ArrayList<Float>> coefficients = new ArrayList<>();
    private Matrix matrix;
    private int niteration;
    private double tolerance;

 /*    public Numeric getMethod(String type) {
        if (type == null) {
            return null;
        }

        else if (type.equalsIgnoreCase("G")) {

            return new GaussElimination(matrix);
        } else if (type.equalsIgnoreCase("GJ")) {
            return new GaussJordan(matrix);
        } else if (type.equalsIgnoreCase("LU")) {
            return new LUDeomp(matrix,getIteration(),getTolerance());
        } else if (type.equalsIgnoreCase("GS")) {
            return new GauseSedil(matrix,getIteration(),getTolerance());
        } else if (type.equalsIgnoreCase("J")) {
            return new Jacobi(matrix,getIteration(),getTolerance());
        }
        return null;
    }
    public void setIteration(int niteration){
        this.niteration=niteration;
    }
    public int getIteration(){
        return niteration;
    }
       public void setTolerance(double tolerance){
        this.tolerance=tolerance;
    }
    public double getTolerance(){
        return tolerance;
    }
    public String getAnswer() {
        String answer = "";
        int l = 0;
        for (Double i : this.answers) {
            answer = answer.concat(this.variables.get(0).get(l));
            answer = answer.concat("=");
            answer = answer.concat(String.valueOf(i));
            answer = answer.concat(",");
            l++;
        }
        return answer;
    }
*/
    public Matrix parseEquation(String eq) {
        List<ArrayList<Double>> coefficients = new ArrayList<>();
        List<Double> constants = new ArrayList<>();

        String[] equations = eq.split("&");
        for (String equation : equations) {
            String[] terms = equation.split("(?=[-+])");
            ArrayList<Double> equationCoefficients = new ArrayList<>();
             ArrayList<String> ec = new ArrayList<>();
            for (int i = 0; i < terms.length; i++) {
                String term = terms[i].trim();
                if (term.matches(".*[a-z].*")) {
                    String variable;
                    String coefficientString;
                    if (term.contains("=")) {

                        String[] parts = term.split("=");
                        // System.out.println(parts[0].trim().length() - 1);
                        variable = parts[0].substring(parts[0].trim().length() - 1);
                        // System.out.println(variable);

                        coefficientString = parts[0].substring(0, parts[0].trim().length() - 1);
                        // System.out.println(coefficientString);
                    } else {
                     //   while()
                        // System.out.println(term.trim().length() - 1);
                        variable = term.substring(term.trim().length() - 1);
                        // System.out.println(variable);

                        coefficientString = term.substring(0, term.trim().length() - 1);

                        // System.out.println(coefficientString);
                    }
                    ec.add(variable);
                    double coefficient;
                    if (coefficientString.isEmpty()) {
                        coefficient = 1.0;
                    } else if (coefficientString.equalsIgnoreCase("+")) {
                        coefficient = 1.0;
                    } else if (coefficientString.equalsIgnoreCase("-")) {
                        coefficient = -1.0;
                    } else {
                        coefficient = Double.parseDouble(coefficientString);
                    }

                    equationCoefficients.add(coefficient);
                }
            }
            variables.add(ec);
            coefficients.add(equationCoefficients);
            String constantString = equation.split("=")[equation.split("=").length - 1].trim();
            double constant = Double.parseDouble(constantString);
            constants.add(constant);
        }
    int b=0;
    int l=0 ;
    int i=0;
    int v=0;
   int  maxlen=0;
   for(int e =0 ;e<variables.size();e++){
    if(variables.get(e).size()>maxlen){
        maxlen=variables.get(e).size();
    }
   }
   while (v==0) {
        try {
            for( l =0 ; l<variables.size();l++){
        if(variables.get(l).size()==maxlen){
            for( i =0 ;i<maxlen;i++){
                for( b =0 ;b<variables.size();b++){
            if(!variables.get(l).get(i).equalsIgnoreCase(variables.get(b).get(i))){
            variables.get(b).add(i,variables.get(l).get(i));
            coefficients.get((b)).add(i, 0.0);
                    }
                                                }
                            }
                    }
            v=1;
        } 
        } catch (IndexOutOfBoundsException e) {
            variables.get(b).add(variables.get(l).get(i));
            coefficients.get((b)).add(0.0);
            v=0;
            }
        }
    






        // Print the updated values
        System.out.println("Variables: " + variables);
        System.out.println("Coefficients: " + coefficients);
        System.out.println("Constants: " + constants);
        Matrix cof = new Matrix(coefficients.size(), coefficients.get(0).size());
        for (int z = 0; z < cof.getNumRows(); z++) {
            cof.setRow(z, coefficients.get(z));
        }
        cof.addColumn(constants);
        this.matrix = cof;
        return cof;
    }

    public void solve(Numeric method){
        this.answers = method.solve();
    }

    public class Main {
        public static void main(String[] args) {
            Solver solver = new Solver();

            // Example equation string
            String equationString = "2x-3y=10&4x+5y=5&x-y=-5";
            // Parse the equation string and get the matrix
            Matrix matrix = solver.parseEquation(equationString);

            // Print the parsed matrix
            for (int i = 0; i < matrix.getNumRows(); i++) {
                for (int j = 0; j < matrix.getNumCols(); j++) {
                    System.out.print(matrix.getElement(i, j) + " ");
                }
                System.out.println();
            }
        }
    }
}
