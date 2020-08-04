
public class MoveD implements Comparable<MoveD>{
    private RowCol rc;
    private int degree;
    public MoveD(RowCol rc, int degree){
        this.rc = rc;
        this.degree = degree;
    }

    public RowCol getRc () {
        return rc;
    }

    public int getDegree () {
        return degree;
    }
    @Override
    public int compareTo(MoveD o) {

        return Integer.compare(this.degree, o.degree);
    }

}
