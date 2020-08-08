import java.util.ArrayList;

public class Move {
    private RowCol rowCol;
    private ArrayList<RowCol> captured;
    private int score;
    private int degree;
    public Move(RowCol rc, ArrayList<RowCol> captured, int degree){
        this.rowCol = rc;
        this.captured=captured;
        this.degree = degree;

    }
    public Move(RowCol rc, int score){
        this.rowCol = rc;
        this.score=score;

    }

    public int compareTo(Move o) {

        return Integer.compare(this.degree, o.degree);
    }

    public void setRowCol(RowCol rowCol) {
        this.rowCol = rowCol;
    }

    public void setCaptured(ArrayList<RowCol> captured) {
        this.captured = captured;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public RowCol getRowCol() {
        return rowCol;
    }

    public ArrayList<RowCol> getCaptured() {
        return captured;
    }

    public int getScore() {
        return score;
    }
}
