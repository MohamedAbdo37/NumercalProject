
package com.CSED26.Numercal.Project.Factory.Methods;

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
    // protected ArrayList<Float> subtitute()
    // {
    // ArrayList<Float>result=new ArrayList<>();
    // for(int i=0;i<matrix.getNumRows();i++)
    // {
    // result.add(matrix.getRow(i).get(matrix.getNumRows())/matrix.getRow(i).get(i));
    // }
    // return result;
    // }

    // @Override
    // public ArrayList<Float> Solve()
    // {
    // if(!checkValidaty())
    // {
    // throw new RuntimeException("Matrix isn't wellformated --> {A(Square)|B}");
    // }
    // ForwardElimination();
    // backwardElim();
    // ArrayList<Float>result=subtitute();
    // return result;
    // }
}
