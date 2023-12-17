package com.CSED26.Numercal.Project;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.CSED26.Numercal.Project.Factory.Numeric;
import com.CSED26.Numercal.Project.Factory.Methods.Jacobi;

import numbericalproject.demo.service.GauseSedil;


public class Solver{
private static final String String = null;
List<String> variables = new ArrayList<>();
List<Float> answers = new ArrayList<>();
ArrayList<ArrayList<Float>> coefficients = new ArrayList<>();

/* 
      public Numeric getMethod(String type){
        if(type==null){return null;}

        else if(type.equalsIgnoreCase("GauseElim")) {
           
            return  new GauseElim(matrix);
        }
        else  if(type.equalsIgnoreCase("GauseJordanElim")){
            return new GauseJordanElim(matrix);
        }
        else if(type.equalsIgnoreCase("LUDecomp")){
            return new LUDecomp(matrix);
        }
        else  if(type.equalsIgnoreCase("GauseSedil")){
            return new GauseSedil(matrix);
        }
        else  if(type.equalsIgnoreCase("Jacobi")){
            return new Jacobi(matrix);
        }
        return null;
    }
*/

    public String getAnswer(List<Float> ans,List<String> variables){
        String answer="" ;
        int l=0;
    for(Float i:ans){
    answer=answer.concat(variables.get(l));
    answer=answer.concat("=");
    answer=answer.concat(String.valueOf(i));
    answer=answer.concat(",");
    l++;
    }
        return answer;
    }
    public static int getMaxOccurrences(List<String> variables, String target) {
        int maxOccurrences = 0;

        for (String variable : variables) {
            int occurrences = Collections.frequency(variables, variable);
            if (variable.equals(target) && occurrences > maxOccurrences) {
                maxOccurrences = occurrences;
            }
        }

        return maxOccurrences;
    }

  public  Matrix parseEquation(String eq) {
    List<ArrayList<Double>> coefficients = new ArrayList<>();
    List<Double> constants = new ArrayList<>();

    String[] equations =  eq .split("&");
    for (String equation : equations) {
        String[] terms = equation.split("(?=[-+])");
        ArrayList<Double> equationCoefficients = new ArrayList<>();
        for (int i = 0; i < terms.length; i++) {
            String term = terms[i].trim();
            if (term.matches(".*[a-z].*")) {
                String variable;
                String coefficientString;
                if (term.contains("=")) {  
    
    
                    String[] parts = term.split("=");
                    //   System.out.println(parts[0].trim().length() - 1);
                    variable = parts[0].substring(parts[0].trim().length() - 1);
                   //  System.out.println(variable);
    
                    coefficientString = parts[0].substring(0, parts[0].trim().length() - 1);
                  //    System.out.println(coefficientString);
                } else {
                   // System.out.println(term.trim().length() - 1);
                    variable = term.substring(term.trim().length() - 1);
                  //  System.out.println(variable);
    
                    coefficientString = term.substring(0,term.trim().length() - 1 );
    
                  //  System.out.println(coefficientString);
                }
                variables.add(variable);
                double coefficient;
                if (coefficientString.isEmpty()) {
                    coefficient = 1.0;
                } else if(coefficientString.equalsIgnoreCase("+")) {
                    coefficient = 1.0;
                }
                else if(coefficientString.equalsIgnoreCase("-")) {
                    coefficient = -1.0;
                }
                else{
                    coefficient = Double.parseDouble(coefficientString);
                }

                equationCoefficients.add(coefficient);
            }
        }
        coefficients.add(equationCoefficients);
        String constantString = equation.split("=")[equation.split("=").length - 1].trim();
        double constant = Double.parseDouble(constantString);
        constants.add(constant);
    }
    
        int  maxlength=0;
        for(String m  :variables){
        int  l=getMaxOccurrences(variables,m);
            if(l>maxlength){
                maxlength=l;
            }
        }
        int size =maxlength*coefficients.size();
        int z=0;
            for(int q=0;q<variables.size();q++){
                boolean flag=false;
                String k =variables.get(q);
                if(getMaxOccurrences(variables,k)<maxlength){
                flag = true;
                    z=q;
                    System.out.println("z="+z);
                    int nq =0;
                    System.out.println("nq="+nq);
                    while(z>=0&&z<variables.size()&&nq<maxlength){
                    if(!variables.get(q).equalsIgnoreCase(variables.get(z))){
                    System.out.println(variables.get(q));
                    System.out.println(variables.get(z));
                    variables.add(z,k);
                    System.out.println("z="+z);
                    coefficients.get((z/maxlength)).add(z%maxlength, 0.0);
                    flag=false;
                    nq++;
                    System.out.println("nq="+nq);
                }
                    else{
                    System.out.println(variables.get(q));
                    System.out.println(variables.get(z));
                        z=z+maxlength;
                        System.out.println("z="+z);
                        if(z==size-1&&variables.get(z-1).equalsIgnoreCase(k)){
                        variables.add(k);
                        System.out.println("z="+z);
                        coefficients.get((z/maxlength)).add(z%maxlength, 0.0);
                        }
                        nq++;
                    System.out.println("nq="+nq);
                    }
                }
                    if(nq<maxlength){z=q; nq--;}
                     int fi =0;
                    while(z>=0&&z<size-1&&nq<maxlength){
                    if(!variables.get(q).equalsIgnoreCase(variables.get(z))){
                            System.out.println(variables.get(q));
                            System.out.println(variables.get(z));
                            variables.add(z,k);
                            nq++;
                            if(nq==3){fi=1;}
                            System.out.println("nq2="+nq);
                            if(z<0){z=z+maxlength;}
                            System.out.println("z="+z);
                            if(fi!=1){z++;}
                            coefficients.get((z/maxlength)).add(z%maxlength, 0.0);
                            if(fi!=1){z--;}

                            q++;
                            flag=false;
                    }
                    else{
                        System.out.println(variables.get(q));
                        System.out.println(variables.get(z));
                        z=z-maxlength+1;
                        nq++;
                        System.out.println("nq1="+nq);
                        if(nq==3){
                            variables.add(z,k);
                            System.out.println("z="+z);
                            coefficients.get((z/maxlength)).add(z%maxlength, 0.0);
                        }
                        }
                    }
                }
            }
    // Print the extracted values
    System.out.println("Variables: " + variables);
    System.out.println("Coefficients: " + coefficients);
    System.out.println("Constants: " + constants);
        Matrix cof =new Matrix(coefficients.size(), coefficients.get(0).size());
        for(int i =0 ; i<cof.getNumRows();i++){
        cof.setRow(i, coefficients.get(i));
        }
        return cof;
    }

    public class Main {
    public static void main(String[] args) {
        Solver solver = new Solver();
        
        // Example equation string
        String equationString = "-2y-z=10&x-4y+z=5&x-y-z=-5";
        // Parse the equation string and get the matrix
        Matrix matrix = solver.parseEquation(equationString);
        
        // Print the parsed matrix
        for (int i = 0; i <matrix.getNumRows(); i++) {
            for (int j = 0; j < matrix.getNumCols(); j++) {
                System.out.print(matrix.getElement(i, j) + " ");
            }
            System.out.println();
        }   
    }
}

}
