import java.util.ArrayList;

public class Move {
    private RowCol rowCol;
    private ArrayList<RowCol> captured;
    public Move(RowCol rc, ArrayList<RowCol> captured){
        this.rowCol = rc;
        this.captured=captured;

    }

    public void setRowCol(RowCol rowCol) {
        this.rowCol = rowCol;
    }

    public void setCaptured(ArrayList<RowCol> captured) {
        this.captured = captured;
    }

    public RowCol getRowCol() {
        return rowCol;
    }

    public ArrayList<RowCol> getCaptured() {
        return captured;
    }
}
