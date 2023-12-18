
package com.CSED26.Numercal.Project.Factory.Methods;

import java.util.ArrayList;

import com.CSED26.Numercal.Project.Matrix;

/**
 * GaussJordan
 */
public class GaussJordan extends GaussElimination {

    public GaussJordan(Matrix matrix) {
        super(matrix);
    }



    @Override
    public Matrix backElim() {
        for (int i = matrix.getNumRows() - 1; i >= 0; i--) {

            Double pivot = matrix.getRow(i).get(i);
            for (int j = i - 1; j >= 0; j--) {

                Double factor = matrix.getRow(j).get(i) / pivot;
                matrix.setRow(j, multadd(factor, matrix.getRow(i), matrix.getRow(j)));

            }

        }
        return matrix;

    }
     protected ArrayList<Double> subtitute()
     {
     ArrayList<Double>result=new ArrayList<>();
     for(int i=0;i<matrix.getNumRows();i++)
     {
         if(matrix.getRow(i).get(i)==0.0)
         {
             result.set(i,chop_number(1.0));
             continue;
         }
         double x=chop_number(matrix.getRow(i).get(matrix.getNumRows())/matrix.getRow(i).get(i));
         result.add(x);
     }

     return result;
     }

     @Override
     public ArrayList<Double> solve()
     {
     if(!checkValidaty())
     {
     throw new RuntimeException("Matrix isn't wellformated --> {A(Square)|B}");
     }
     forwardElim();
     backElim();
     ArrayList<Double>result=subtitute();
     return result;
     }
}
