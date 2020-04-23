

import java.util.ArrayList;
import java.util.Arrays;

public class MainGame {
    private Board board;

    private final boolean CAPTURE_ONE = false;
    private final boolean CAPTURE_TWO = false;
    private final boolean THREE_IN_A_ROW = false;
    private final boolean FOUR_IN_A_ROW = true;
    private final int BLACK = 1;
    private final int WHITE = -1;
    private final int EMPTY = 0;

    private int blackCapCount=0;
    private int whiteCapCount=0;
    private int winner =0;

    private int rowLength;
    private int colLength;

    private int[][] currentBoard;
    private int currentPlayer;

    private ArrayList<RowCol> availableMoves;
    private ArrayList<RowCol> captured=new ArrayList<>();


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

        if(checkDiagonals()||checkColumns()||checkRows()|| board.isFull()){

            return true;
        }

        return false;
    }
    public int ifMakeLine(int row, int col){
        if(THREE_IN_A_ROW){
            return ifMakeThree(row,col);
        }
        if(FOUR_IN_A_ROW){
            return ifMakeFour(row,col);
        }
        return 0;
    }
    public int ifMakeThree(int row, int col){
        final int[][] directions = {{-1,0},{-1,1},{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1}};
       for(int i=0; i<directions.length; i++){
           int[] dir = directions[i];
           if(posValid(row+dir[0]*2,col+dir[1]*2)&&board.getValue(row+dir[0],col+dir[1])!=0){
               if(board.getValue(row+dir[0],col+dir[1])==board.getValue(row+dir[0]*2,col+dir[1]*2)){
                   return board.getValue(row+dir[0],col+dir[1]);
               }
           }
        }
        final int[][] centers = {{-1,0},{0,1},{-1,1},{-1,-1}};
        for(int i=0; i<centers.length; i++){
            int[] dir = directions[i];
            if(posValid(row+dir[0],col+dir[1])&&posValid(row-dir[0],col-dir[1])&&board.getValue(row+dir[0],col+dir[1])!=0){
                if(board.getValue(row+dir[0],col+dir[1])==board.getValue(row-dir[0],col-dir[1])){
                    return board.getValue(row+dir[0],col+dir[1]);
                }
            }
        }
        return 0;

    }

    private int ifMakeFour(int row, int col){
        final int[][] directions = {{-1,0},{-1,1},{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1}};
        for(int i=0; i<directions.length; i++){
            int[] dir = directions[i];
            int[] end = {row+dir[0]*3,col+dir[1]*3};
            if(posValid(end[0],end[1])&& board.getValue(end[0],end[1])!=0){
                int[] mid1 = {row+dir[0],col+dir[1]};
                int[] mid2 = {row+dir[0]*2,col+dir[1]*2};
                if(board.getValue(end[0],end[1])==board.getValue(mid1[0],mid1[1])&&board.getValue(mid1[0],mid1[1])==board.getValue(mid2[0],mid2[1])){
                    return board.getValue(end[0],end[1]);
                }
            }
        }
        final int[][] centers = {{-1,0},{0,1},{-1,1},{-1,-1}};
        for(int i=0; i<centers.length; i++){
            int[] dir = directions[i];
            for(int j=0; j<2; j++) {
                int[] one = {row+dir[0]*2,col+dir[1]*2};
                int[] two = {row+dir[0],col+dir[1]};
                int[] three = {row-dir[0],col-dir[1]};
                int[] four = {row-dir[0]*2, col-dir[1]*2};
                if (posValid(one[0],one[1]) && posValid(three[0],three[1]) && board.getValue(one[0],one[1]) != 0) {
                    if (board.getValue(one[0],one[1]) == board.getValue(two[0],two[1]) && board.getValue(two[0],two[1]) == board.getValue(three[0],three[1] )) {
                        return board.getValue(one[0],one[1]);
                    }
                }
                if (posValid(two[0],two[1]) && posValid(four[0],four[1]) && board.getValue(two[0],two[1]) != 0) {
                    if (board.getValue(two[0],two[1]) == board.getValue(three[0],three[1]) && board.getValue(three[0],three[1]) == board.getValue(four[0],four[1] )) {
                        return board.getValue(two[0],two[1]);
                    }
                }

            }
        }
        return 0;
    }


    private boolean checkRows(){
        for(int i=0; i<rowLength; i++){
            for(int j=0; j<colLength-2; j++){
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
            for(int j=0; j<rowLength-2; j++){
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

    public ArrayList<RowCol> checkCaptureOne(int row, int col, int currentPlayer){
        ArrayList<RowCol> newCapture = new ArrayList<>();
        int[][] direction = {{-1,0,-2,0},{-1,1,-2,2},{0,1,0,2},{1,1,2,2},{1,0,2,0},{1,-1,2,-2},{0,-1,0,-2},{-1,-1,-2,-2}};
        int captureNum = 0;
        for(int i=0; i< direction.length; i++){

            int[] temp = direction[i];
            int[] mid = {row+temp[0],col+temp[1]};
            int[] end = {row+temp[2],col+temp[3]};
            if(posValid(mid[0],mid[1]) && posValid(end[0],end[1])) {
                if (currentBoard[mid[0]][mid[1]] == currentPlayer * -1) {
                    if (currentBoard[end[0]][end[1]] == currentPlayer) {
                        captureStone(mid[0], mid[1]);

                        newCapture.add(new RowCol(mid[0],mid[1]));
                        if(currentPlayer==BLACK){ blackCapCount++;}
                        else if(currentPlayer==WHITE){ whiteCapCount++;}
                    }
                }
            }
        }

        return newCapture;
    }
    public ArrayList<RowCol> checkCaptureTwo(int row, int col, int currentPlayer){
        ArrayList<RowCol> newCapture = new ArrayList<>();
        int[][] directions = {{-1,0},{-1,1},{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1}};

        for(int i=0; i< directions.length; i++){

            int[] dir = directions[i];
            int[] mid1 = {row+dir[0],col+dir[1]};
            int[] mid2 = {row+dir[0]*2,col+dir[1]*2};
            int[] end = {row+dir[0]*3,col+dir[1]*3};
            if(posValid(end[0],end[1])) {
                if (currentBoard[mid1[0]][mid1[1]] == currentPlayer * -1 && currentBoard[mid2[0]][mid2[1]] == currentPlayer * -1 ) {
                    if (currentBoard[end[0]][end[1]] == currentPlayer) {
                        captureStone(mid1[0], mid1[1]);
                        captureStone(mid2[0], mid2[1]);
                        newCapture.add(new RowCol(mid1[0],mid1[1]));
                        newCapture.add(new RowCol(mid2[0],mid2[1]));
                        if(currentPlayer==BLACK){ blackCapCount+=2;}
                        else if(currentPlayer==WHITE){ whiteCapCount+=2;}
                    }
                }
            }
        }

        return newCapture;
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



    public void placeStone(int row, int col, int state){
        board.setBoard(row, col, state);
        updateBoard();
        captured = new ArrayList<>();

        if(CAPTURE_ONE){
            captured.addAll(checkCaptureOne(row, col, state));
        }
        if(CAPTURE_TWO){
            captured.addAll(checkCaptureTwo(row, col, state));
        }

        updateBoard();
    }
    public ArrayList<RowCol> getCaptured(){
        return captured;
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

    public void setWinner(int winner) {
        this.winner = winner;
    }

    public int getWinner(){
        return winner;
}
    public void updateBoard(){
        this.currentBoard = board.getBoard();
    }

    public int getBlackCapCount() { return blackCapCount; }
    public int getWhiteCapCount() { return whiteCapCount; }
}
