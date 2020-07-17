import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class Board {
    private int rowLength;
    private int colLength;
    private int[][] board;


    public Board(int row, int col){
        rowLength = row;
        colLength = col;

        board = new int[rowLength][colLength];
        for(int i=0; i<rowLength; i++){
            for(int j=0; j<colLength; j++){
                board[i][j] = 0;
            }
        }


    }
    public Board(int[][] board){
        rowLength= board.length;
        colLength = board[0].length;
        this.board = board;
    }
    public void printBoard(){
        for (int i=0; i<rowLength; i++){
            System.out.print("| ");
            for(int j=0; j<colLength; j++){
                System.out.print(board[i][j] + " | ");
            }
            System.out.println();
        }
        System.out.println();
    }


    public void setBoard(int row, int col, int state){
        board[row][col] = state;

    }
    public int[][] getBoard(){
        return this.board;
    }
    public int getRowLength(){
        return this.rowLength;
    }

    public int getColLength() {
        return this.colLength;
    }
    public Board copy(){
        int[][] newBoard = new int[rowLength][colLength];
        for(int i=0; i<rowLength; i++){
            for(int j=0; j<colLength; j++){
                newBoard[i][j] = board[i][j];
            }
        }
        return new Board(newBoard);
    }
    public boolean isEqual(int[][] b1, int[][] b2){
        for(int i=0; i<b1.length; i++){
            for(int j=0; j<b1[0].length; j++){
                if(b1[i][j]!=b2[i][j]){
                    return false;
                }
            }
        }
        return true;
    }
    public ArrayList getAvailableMoves(){
        ArrayList<RowCol> empty = new ArrayList<>();
        for (int i=0; i<rowLength; i++){
            for (int j=0; j<colLength; j++){
                if (board[i][j] == 0){

                    empty.add(new RowCol(i,j));
                }
            }
        }
        return empty;
    }
    public boolean isFull(){
        for(int i=0; i<rowLength; i++){
            for(int j=0; j<colLength; j++){
                if(board[i][j]==0) return false;
            }
        }
        return true;
    }
    public int getValue(int row, int col){
        return board[row][col];
    }
}
