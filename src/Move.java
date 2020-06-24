import java.util.ArrayList;

public class Move {
    private RowCol rowCol;
    private ArrayList<RowCol> captured;
    private int score;
    public Move(RowCol rc, ArrayList<RowCol> captured){
        this.rowCol = rc;
        this.captured=captured;

    }
    public Move(RowCol rc, int score){
        this.rowCol = rc;
        this.score=score;

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
