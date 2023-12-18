package com.CSED26.Numercal.Project.Factory.Methods;

import java.util.ArrayList;

import com.CSED26.Numercal.Project.Matrix;
import com.CSED26.Numercal.Project.Factory.Numeric;

public class GaussElimination extends Numeric {
    protected Matrix matrix;

    public GaussElimination(Matrix matrix) {
        this.matrix = matrix;
    }

    protected void InterChangeRows(int r1, int r2) {
        ArrayList<Double> row1 = matrix.getRow(r1);
        ArrayList<Double> row2 = matrix.getRow(r2);
        matrix.setRow(r1, row2);
        matrix.setRow(r2, row1);
    }

    protected boolean checkValidaty() {
        if (matrix.getNumCols() == matrix.getNumRows() + 1)
            return true;
        return false;
    }

    protected int getMaxpivot(int i) {
        Double max = 0.0;
        int max_index = i;
        for (int j = i; j < matrix.getNumRows(); j++) {
            if (matrix.getRow(j).get(i) > max) {
                max = matrix.getRow(j).get(i);
                max_index = j;
            }
        }
        return max_index;
    }

    protected ArrayList<Double> multadd(double factor, ArrayList<Double> r1, ArrayList<Double> r2) {
        factor=chop_number(factor);
        for (int i = 0; i < r1.size(); i++) {
            double x= r1.get(i) * factor - r2.get(i);
            r2.set(i, chop_number(x));
        }
        return r2;

    }

    @Override
    public Matrix forwardElim() {

        for (int i = 0; i < matrix.getNumRows(); i++) {
            int r = getMaxpivot(i);
            InterChangeRows(r, i);
            double pivot = matrix.getRow(i).get(i);
            for (int j = i + 1; j < matrix.getNumRows(); j++) {
                double factor = matrix.getRow(j).get(i) / pivot;
                matrix.setRow(j, multadd(factor, matrix.getRow(i), matrix.getRow(j)));

            }
        }
        return matrix;
    }
     private ArrayList<Double> BackWordSub()
     {
     ArrayList<Double>result=new ArrayList<>();
     for(int i=0;i<matrix.getNumRows();i++)
     {
     result.add(matrix.getRow(i).get(matrix.getNumCols()-1));

     }
     double sum=0;
     for (int i=matrix.getNumRows()-1;i>=0;i--)
     {
     for (int j=i+1;j<matrix.getNumRows();j++)
     {
         double x=chop_number(matrix.getRow(i).get(j)*result.get(j));
     sum+=x;
     }
     sum=chop_number(sum);
     double y=chop_number((matrix.getRow(i).get(matrix.getNumCols()-1)-sum)/matrix.getRow(i).get(i));
     result.set(i,y);

     sum=0;
     }


     return result;
     }




    @Override
    public Matrix backElim() {
        return null;
    }

    @Override
    public ArrayList<Double> solve() {
        if(!checkValidaty())
        {
            throw new RuntimeException("Matrix isn't wellformated --> {A(Square)|B}");
        }
        forwardElim();
        ArrayList<Double>result=BackWordSub();
        return result;
    }
   protected Double chop_number(Double number)
    {
        int n=(int) Math.log10(number)+1 ;
        int y=(int) (number*Math.pow(10,matrix.getSignificantFigures()-n+1)); //1039//1059
        int z=(int) Math.floor(number*Math.pow(10,matrix.getSignificantFigures()-n)); //1030
        z=z*10;
        y=y-z;
        int x=(int) (number*Math.pow(10,matrix.getSignificantFigures()-n+1));
        x=y>=5? x+1:x;
        number=x/Math.pow(10,matrix.getSignificantFigures()-n+1);
        return number;
    }
}
