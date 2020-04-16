

import java.util.ArrayList;
import java.util.Arrays;

public class MainGame {
    private Board board;

    private final int BLACK = 1;
    private final int WHITE = -1;
    private final int EMPTY = 0;
    private int winner =0;

    private int rowLength = 3;
    private int colLength = 3;

    private int[][] currentBoard;
    private int currentPlayer;

    private ArrayList<RowCol> availableMoves;


    public MainGame(Board board){
        this.board = board;
        currentPlayer = 1;
        currentBoard = board.getBoard();
        rowLength = board.getRowLength();
        colLength = board.getColLength();
    }
    public MainGame(Board board, int currentPlayer){

        this.board = board;
        this.currentPlayer = currentPlayer;
        currentBoard = board.getBoard();
        this.availableMoves = getAvailableMoves();
        rowLength = board.getRowLength();
        colLength = board.getColLength();
    }

    public boolean ifEnd(){

        if(checkRows() || checkColumns() || checkDiagonals() || board.isFull()){
//            if(-currentPlayer==1){
//            System.out.println("BLACK wins");
//            }
//            else{
//                System.out.println("WHITE wins");
//            }

            return true;
        }

        return false;
    }



    private boolean checkRows(){
        for(int i=0; i<rowLength; i++){
            for(int j=0; j<colLength-2; j+=3){
                if(checkLine(currentBoard[i][j], currentBoard[i][j+1], currentBoard[i][j+2])){
                    winner = currentPlayer;
                    return true;
                }
            }
        }
        return false;
    }


    public boolean checkColumns(){
        for(int i=0; i<colLength; i++){
            for(int j=0; j<rowLength-2; j+=3){
                if(checkLine(currentBoard[j][i], currentBoard[j+1][i], currentBoard[j+2][i])){
                    winner = currentPlayer;
//                    System.out.println("true");
                    return true;
                }

            }
        }
        return false;
    }
    public boolean checkDiagonals(){
        for(int i=0; i<rowLength-2; i++){
            for(int j=0; j<colLength-2; j++){
                if(checkLine(currentBoard[i][j],currentBoard[i+1][j+1],currentBoard[i+2][j+2])){
                    winner = currentPlayer;
                    return true;
                }
                if(checkLine(currentBoard[i+2][j],currentBoard[i+1][j+1],currentBoard[i][j+2])){
                    winner = currentPlayer;
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<RowCol> checkCapture(int row, int col, int currentPlayer){
        ArrayList<RowCol> captured = new ArrayList<>();
        final int[][] direction = {{-1,0,-2,0},{-1,1,-2,2},{0,1,0,2},{1,1,2,2},{1,0,2,0},{1,-1,2,-2},{0,-1,0,-2},{-1,-1,-2,-2}};
        int captureNum = 0;
        for(int i=0; i< direction.length; i++){

            int[] temp = direction[i];
            int[] mid = {row+temp[0],col+temp[1]};
            int[] end = {row+temp[2],col+temp[3]};
            if(posValid(mid[0],mid[1]) && posValid(end[0],end[1])) {
                if (currentBoard[mid[0]][mid[1]] == currentPlayer * -1) {
                    if (currentBoard[end[0]][end[1]] == currentPlayer) {
                        captureStone(mid[0], mid[1]);

                        captured.add(new RowCol(mid[0],mid[1]));
                    }
                }
            }
        }

        return captured;
    }
    private boolean posValid(int row,int col){
        if(row>=0 && row<rowLength){
            if(col>=0 && col< colLength){
                return true;
            }
        }
        return false;
    }
    private void captureStone(int row, int col){
        board.setBoard(row,col, EMPTY);
        updateBoard();

    }
    private boolean checkLine(int c1, int c2, int c3){
        if(c1 != 0 && c1==c2 && c2==c3) {
            winner = c1;
            return true;}

        return false;
    }
    public void changePlayer(){
        currentPlayer= -currentPlayer;
    }
    public boolean isEmpty(int c){return c==0;}


    public ArrayList<RowCol> placeStone(int row, int col, int state){
        board.setBoard(row, col, state);
        updateBoard();
//      ArrayList<RowCol> captured = checkCapture(row, col, currentPlayer);
        RowCol nextMove = null;
//        if(captured.size()>0){
//            nextMove= captured.get(0);
//        }
//        captured = checkCapture(row, col, state);



//        if(captured.size()>0){
//            return captured;
//        }
        return null;
    }
    public ArrayList getAvailableMoves(){

        updateBoard();

        availableMoves = new ArrayList<>();

        for (int i=0; i<rowLength; i++){
            for (int j=0; j<colLength; j++){
                if (currentBoard[i][j] == 0){

                    availableMoves.add(new RowCol(i,j));
                }
            }
        }


        return availableMoves;
    }
    public int getCurrentPlayer() {
        return currentPlayer;
    }
    public Board getBoard() {
        return board;
    }
    public MainGame copy(int currentPlayer){
        return new MainGame(board.copy(), currentPlayer);
    }
public int getWinner(){
        return winner;
}
    public void updateBoard(){
        this.currentBoard = board.getBoard();
    }

}
