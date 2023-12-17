import java.util.ArrayList;

public class GaussElimination extends Numeric{
   protected Matrix matrix;
    GaussElimination(Matrix matrix)
    {
        this.matrix=matrix;
    }

    protected void InterChangeRows(int r1,int r2)
    {
        ArrayList<Float> row1=matrix.getRow(r1);
        ArrayList<Float>row2=matrix.getRow(r2);
       matrix.setRow(r1,row2);
       matrix.setRow(r2,row1);
    }
    protected boolean checkValidaty()
    {
        if(matrix.getNumCols()==matrix.getNumRows()+1)
        return true;
        return false;
    }
    protected int getMaxpivot(int i)
    {
       float max=0;
        int max_index=i;
        for(int j=i;j<matrix.getNumRows();j++)
        {
            if(matrix.getRow(j).get(i)>max) {
                max = matrix.getRow(j).get(i);
               max_index=j;
            }
        }
        return max_index;
    }
    protected ArrayList<Float> multadd(float factor, ArrayList<Float> r1, ArrayList<Float>r2 )
    {
        for(int i=0;i<r1.size();i++)
        {
            r2.set(i,r1.get(i)*factor- r2.get(i));
        }
        return r2;

    }
    @Override
    public Matrix forwardElim()
    {

        for(int i=0;i<matrix.getNumRows();i++)
        {
            int r=getMaxpivot(i);
            InterChangeRows(r, i);
            float pivot=matrix.getRow(i).get(i);
            for (int j=i+1;j<matrix.getNumRows();j++)
            {
                float factor=matrix.getRow(j).get(i)/pivot;
                matrix.setRow(j,multadd(factor,matrix.getRow(i),matrix.getRow(j)));

            }
        }
       return matrix;
    }
//    private ArrayList<Float> BackWordSub()
//    {
//        ArrayList<Float>result=new ArrayList<>();
//        for(int i=0;i<matrix.getNumRows();i++)
//        {
//           result.add(matrix.getRow(i).get(matrix.getNumCols()-1));
//
//        }
//        float sum=0;
//        for (int i=matrix.getNumRows()-1;i>=0;i--)
//        {
//            for (int j=i+1;j<matrix.getNumRows();j++)
//            {
//                 sum+=matrix.getRow(i).get(j)*result.get(j);
//            }
//
//            result.set(i,(matrix.getRow(i).get(matrix.getNumCols()-1)-sum)/matrix.getRow(i).get(i));
//
//            sum=0;
//        }
//
//
//         return result;
//    }
//    public ArrayList<Float> Solve()
//    {
//        if(!checkValidaty())
//        {
//            throw new RuntimeException("Matrix isn't wellformated --> {A(Square)|B}");
//        }
//        forwardElim();
//        ArrayList<Float>result=BackWordSub();
//        return result;
//    }



    @Override
    public Matrix backElim() {
        return null;
    }
}
