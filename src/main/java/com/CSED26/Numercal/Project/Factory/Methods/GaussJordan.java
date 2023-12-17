import java.util.ArrayList;

public class GaussJordan extends GaussElimination {

    GaussJordan(Matrix matrix) {
        super(matrix);
    }
    @Override
    public Matrix backElim()
    {
        for(int i=matrix.getNumRows()-1;i>=0;i--)
        {

            float pivot=matrix.getRow(i).get(i);
            for (int j=i-1;j>=0;j--)
            {
                float factor=matrix.getRow(j).get(i)/pivot;
                matrix.setRow(j,multadd(factor,matrix.getRow(i),matrix.getRow(j)));

            }

        }
        return matrix;

    }
//    protected ArrayList<Float> subtitute()
//    {
//        ArrayList<Float>result=new ArrayList<>();
//        for(int i=0;i<matrix.getNumRows();i++)
//        {
//            result.add(matrix.getRow(i).get(matrix.getNumRows())/matrix.getRow(i).get(i));
//        }
//        return result;
//    }

//    @Override
//    public ArrayList<Float> Solve()
//    {
//        if(!checkValidaty())
//        {
//            throw new RuntimeException("Matrix isn't wellformated --> {A(Square)|B}");
//        }
//        ForwardElimination();
//        backwardElim();
//        ArrayList<Float>result=subtitute();
//        return result;
//    }
}
