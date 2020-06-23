import java.util.ArrayList;

public class RowCol {
    private int row;
    private int col;

    public RowCol(int row, int col){
        this.row = row;
        this.col = col;
    }
    public int getRow(){
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setRowCol(int row, int col) {
        this.row = row;
        this.col = col;
    }
    public boolean isEqual(RowCol a, RowCol b){
        if(a.getRow()==b.getRow()&&a.getCol()==b.getCol()){
            return true;
        }
        return false;
    }


}

