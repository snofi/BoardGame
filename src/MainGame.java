

import java.util.*;

public class MainGame {
    private Board board;
    public final boolean CAPTURE_ONE =false;
    public final boolean CAPTURE_TWO =false;
    public final boolean THREE_IN_A_ROW = true;

    public final boolean FOUR_IN_A_ROW =false;
    private final boolean MOVE_ORDERING = true;
    private final int BLACK = 1;
    private final int WHITE = -1;
    private final int EMPTY = 0;

    private int blackCapCount=0;
    private int whiteCapCount=0;
    private int winner =0;
    private int captureNum = 0;
    private int rowLength;
    private int colLength;

    private int[][] currentBoard;
    private int currentPlayer;

    private ArrayList<RowCol> availableMoves;

    private ArrayList<RowCol> newCap;
    public RowCol capturedOne;
    private ArrayList<RowCol> capturedTwo=new ArrayList<>();


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

        rowLength = board.getRowLength();
        colLength = board.getColLength();
    }

    public boolean ifEnd(){

        if(checkDiagonals()||checkColumns()||checkRows()|| board.isFull()){

            return true;
        }

        return false;
    }
    public RowCol ifAlmostLine(int player){
        ArrayList<RowCol> aList = board.getAvailableMoves();
        if(THREE_IN_A_ROW){
            return ifMakeThree(aList, player);
        }
        if(FOUR_IN_A_ROW){
            return ifMakeFour(aList, player);
        }
        return null;
    }
    public boolean ifLine(){
        setWinner(0);
//        checkRows();
//        checkColumns();
//        checkDiagonals();
        if(checkDiagonals()||checkColumns()||checkRows()){
            return true;
        }

        return false;
    }
    public RowCol ifMakeThree(ArrayList<RowCol> aList,int player){
        RowCol defend = null;
        final int[][] directions = {{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}};
        final int[][] centers = {{-1, 0}, {0, 1}, {-1, 1}, {-1, -1}};
        for(RowCol rc: aList) {
            int row = rc.getRow();
            int col = rc.getCol();
            for (int i = 0; i < directions.length; i++) {
                int[] dir = directions[i];
                if (posValid(row + dir[0] * 2, col + dir[1] * 2) && board.getValue(row + dir[0], col + dir[1]) != 0) {
                    if (board.getValue(row + dir[0], col + dir[1]) == board.getValue(row + dir[0] * 2, col + dir[1] * 2)) {
                        if (board.getValue(row + dir[0], col + dir[1]) ==player) {
                            return rc;
                        }else if(defend==null){
                            defend = rc;
                        }
                    }
                }
            }

            for (int i = 0; i < centers.length; i++) {
                int[] dir = directions[i];
                if (posValid(row + dir[0], col + dir[1]) && posValid(row - dir[0], col - dir[1]) && board.getValue(row + dir[0], col + dir[1]) != 0) {
                    if (board.getValue(row + dir[0], col + dir[1]) == board.getValue(row - dir[0], col - dir[1])) {
                        if (board.getValue(row + dir[0], col + dir[1]) ==player) {
                            return rc;
                        }else if(defend==null){
                            defend = rc;
                        }
//                    return board.getValue(row+dir[0],col+dir[1]);
                    }
                }
            }
        }
        return defend;

    }

    private RowCol ifMakeFour(ArrayList<RowCol> aList, int player){
        RowCol defend = null;
        final int[][] directions = {{-1,0},{-1,1},{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1}};
        final int[][] centers = {{-1, 0}, {0, 1}, {-1, 1}, {-1, -1}};
        for(RowCol rc: aList) {
            int row = rc.getRow();
            int col = rc.getCol();
            for (int i = 0; i < directions.length; i++) {
                int[] dir = directions[i];
                int[] end = {row + dir[0] * 3, col + dir[1] * 3};
                if (posValid(end[0], end[1]) && board.getValue(end[0], end[1]) != 0) {
                    int[] mid1 = {row + dir[0], col + dir[1]};
                    int[] mid2 = {row + dir[0] * 2, col + dir[1] * 2};
                    if (board.getValue(end[0], end[1]) == board.getValue(mid1[0], mid1[1]) && board.getValue(mid1[0], mid1[1]) == board.getValue(mid2[0], mid2[1])) {
                        if (board.getValue(end[0], end[1]) ==player) {
                            return rc;
                        }
                        else if(defend==null){
                            defend = rc;
                        }
                    }
                }
            }

            for (int i = 0; i < centers.length; i++) {
                int[] dir = directions[i];

                int[] one = {row + dir[0] * 2, col + dir[1] * 2};
                int[] two = {row + dir[0], col + dir[1]};
                int[] three = {row - dir[0], col - dir[1]};
                int[] four = {row - dir[0] * 2, col - dir[1] * 2};
                if (posValid(one[0], one[1]) && posValid(three[0], three[1]) && board.getValue(one[0], one[1]) != 0) {
                    if (board.getValue(one[0], one[1]) == board.getValue(two[0], two[1]) && board.getValue(two[0], two[1]) == board.getValue(three[0], three[1])) {
                        if (board.getValue(one[0], one[1]) ==player) {
                            return rc;
                        }else if(defend==null){
                            defend = rc;
                        }
//                        return board.getValue(one[0],one[1]);
                    }
                }
                if (posValid(two[0], two[1]) && posValid(four[0], four[1]) && board.getValue(two[0], two[1]) != 0) {
                    if (board.getValue(two[0], two[1]) == board.getValue(three[0], three[1]) && board.getValue(three[0], three[1]) == board.getValue(four[0], four[1])) {
                        if (board.getValue(four[0], four[1]) == player) {
                            return rc;
                        }else if(defend==null){
                            defend = rc;
                        }
//                        return board.getValue(two[0],two[1]);
                    }
                }


            }
        }
        return defend;
    }


    private boolean checkRows(){
        for(int i=0; i<rowLength; i++){
            for(int j=0; j<colLength-2; j++){
                if(THREE_IN_A_ROW){
                    if(checkLine(currentBoard[i][j], currentBoard[i][j+1], currentBoard[i][j+2])){
                        winner = currentBoard[i][j];
                        return true;}
                }
                if(FOUR_IN_A_ROW){
                    if(posValid(i,j+3)){
                        if(checkLine(currentBoard[i][j], currentBoard[i][j+1], currentBoard[i][j+2], currentBoard[i][j+3])){
                            winner = currentBoard[i][j];
                            return true;}
                    }
                }
            }
        }
        return false;
    }


    public boolean checkColumns(){
        for(int i=0; i<colLength; i++){
            for(int j=0; j<rowLength-2; j++){
                if(THREE_IN_A_ROW) {
                    if (checkLine(currentBoard[j][i], currentBoard[j + 1][i], currentBoard[j + 2][i])) {
                        winner = currentBoard[j][i];
                        return true;
                    }
                }
                if(FOUR_IN_A_ROW){
                    if(posValid(j+3,i)){
                        if(checkLine(currentBoard[j][i], currentBoard[j + 1][i], currentBoard[j + 2][i], currentBoard[j+3][i])){
                            winner = currentBoard[j][i];
                            return true;}
                    }
                }
            }
        }
        return false;
    }
    public boolean checkDiagonals(){
        for(int i=0; i<rowLength-2; i++){
            for(int j=0; j<colLength-2; j++){
                if(THREE_IN_A_ROW) {
                    if (checkLine(currentBoard[i][j], currentBoard[i + 1][j + 1], currentBoard[i + 2][j + 2])) {
                        winner = currentBoard[i][j];
                        return true;
                    }
                    if (checkLine(currentBoard[i + 2][j], currentBoard[i + 1][j + 1], currentBoard[i][j + 2])) {
                        winner = currentBoard[i + 2][j];
                        return true;
                    }
                }
                if(FOUR_IN_A_ROW){
                    if(posValid(i+3,j+3)) {
                        if (checkLine(currentBoard[i][j], currentBoard[i + 1][j + 1], currentBoard[i + 2][j + 2], currentBoard[i+3][j+3])) {
                            winner = currentBoard[i][j];
                            return true;
                        }
                        if (checkLine(currentBoard[i][j+3],currentBoard[i +1][j+2], currentBoard[i + 2][j +1], currentBoard[i+3][j])) {
                            winner = currentBoard[i + 2][j+1];
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public ArrayList<RowCol> checkCaptureOne(int row, int col, int currentPlayer){
        this.currentPlayer=currentPlayer;
        ArrayList<RowCol> newCapture = new ArrayList<>();
        int[][] direction = {{-1,0,-2,0},{-1,1,-2,2},{0,1,0,2},{1,1,2,2},{1,0,2,0},{1,-1,2,-2},{0,-1,0,-2},{-1,-1,-2,-2}};


        for(int i=0; i< direction.length; i++){

            int[] temp = direction[i];
            int[] mid = {row+temp[0],col+temp[1]};
            int[] end = {row+temp[2],col+temp[3]};
            if(posValid(mid[0],mid[1]) && posValid(end[0],end[1])) {
                if (board.getValue(mid[0],mid[1]) == currentPlayer * -1) {
                    if (board.getValue(end[0],end[1]) == currentPlayer) {
                        captureStone(mid[0], mid[1]);
                        captureNum++;
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

        this.currentPlayer = currentPlayer;
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
//                        System.out.println("captured");
                        captureStone(mid1[0], mid1[1]);
                        captureStone(mid2[0], mid2[1]);
                        captureNum+=2;
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
    private int calcDegree3(int row, int col){
        int degree=0;
        final int[][] directions = {{-1,0},{-1,1},{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1}};
        for(int i=0; i<directions.length; i++){
            int[] dir = directions[i];
            if(posValid(row+dir[0]*2,col+dir[1]*2)){
                if(board.getValue(row+dir[0],col+dir[1])!=WHITE&&board.getValue(row+dir[0]*2,col+dir[1]*2)!=WHITE){
                    degree++;
                }
                else if(CAPTURE_ONE &&board.getValue(row+dir[0],col+dir[1])==WHITE&&board.getValue(row+dir[0]*2,col+dir[1]*2)==BLACK ){
                    degree++;
                }
                else if(CAPTURE_TWO && posValid(row+dir[0]*3,col+dir[1]*3) && board.getValue(row+dir[0]*3,col+dir[1]*3)==WHITE&&board.getValue(row+dir[0],col+dir[1])==WHITE&&board.getValue(row+dir[0]*2,col+dir[1]*2)==BLACK){
                    degree ++;
                }
            }
        }
        final int[][] centers = {{-1,0},{0,1},{-1,1},{-1,-1}};
        for(int i=0; i<centers.length; i++){
            int[] dir = directions[i];
            if(posValid(row+dir[0],col+dir[1])&&posValid(row-dir[0],col-dir[1])){
                if(board.getValue(row+dir[0],col+dir[1])!=WHITE&& board.getValue(row-dir[0],col-dir[1])!=WHITE){
                   degree++;
//
                }
            }
        }
        return degree;
    }
    private int calcDegree4(int row, int col){
        int degree = 0;
        final int[][] directions = {{-1,0},{-1,1},{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1}};
        for(int i=0; i<directions.length; i++){
            int[] dir = directions[i];
            int[] end = {row+dir[0]*3,col+dir[1]*3};
            int[] mid1 = {row + dir[0], col + dir[1]};
            int[] mid2 = {row + dir[0] * 2, col + dir[1] * 2};
            if (posValid(end[0],end[1])&& board.getValue(end[0], end[1]) != WHITE) {

                if (board.getValue(mid1[0], mid1[1]) != WHITE && board.getValue(mid2[0], mid2[1]) != WHITE) {
                    degree++;
//                        System.out.println(i);
                }
                else if(CAPTURE_TWO && board.getValue(end[0], end[1]) == BLACK&& board.getValue(mid1[0], mid1[1]) == WHITE && board.getValue(mid2[0], mid2[1]) == WHITE){
                    degree+=2;
                }
            }
            if(CAPTURE_ONE && posValid(mid2[0],mid2[1]) && board.getValue(mid2[0], mid2[1]) == BLACK && board.getValue(mid1[0], mid1[1]) == WHITE){
                    degree+=2;
            }

        }
        final int[][] centers = {{-1,0},{0,1},{-1,1},{-1,-1}};
        for(int i=0; i<centers.length; i++){
            int[] dir = directions[i];

            int[] one = {row+dir[0]*2,col+dir[1]*2};
            int[] two = {row+dir[0],col+dir[1]};
            int[] three = {row-dir[0],col-dir[1]};
            int[] four = {row-dir[0]*2, col-dir[1]*2};
            if (posValid(one[0],one[1]) && posValid(three[0],three[1])) {
                if (board.getValue(one[0],one[1]) != WHITE && board.getValue(two[0],two[1]) != WHITE && board.getValue(three[0],three[1])!=WHITE) {
                    degree++;
//                    System.out.println(i+"one");
                }
            }
            if (posValid(two[0],two[1]) && posValid(four[0],four[1])) {
                if (board.getValue(two[0],two[1])!= WHITE && board.getValue(three[0],three[1])!=WHITE && board.getValue(four[0],four[1] )!=WHITE) {
                    degree++;
//                    System.out.println(i+"two");
                }
            }


        }
        return degree;
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
        board.setBoard(row,col, 0);

        updateBoard();

    }
    private boolean checkLine(int c1, int c2, int c3){
        if(c1 != 0 && c1==c2 && c2==c3) {
            winner = c1;
            return true;}

        return false;
    }
    private boolean checkLine(int c1, int c2, int c3, int c4){
        if(c1 != 0 && c1==c2 && c2==c3 && c3==c4) {
            winner = c1;
            return true;}

        return false;
    }
    public void changePlayer(){
        currentPlayer= -currentPlayer;
    }



    public ArrayList<RowCol> placeStone(int row, int col, int state){
        board.setBoard(row, col, state);
        updateBoard();


        newCap= new ArrayList<>();
        if(CAPTURE_ONE){

         newCap.addAll(checkCaptureOne(row, col, state));

        }
        if(CAPTURE_TWO){

           newCap.addAll(checkCaptureTwo(row, col, state));
        }

        updateBoard();

        return newCap.size()>0 ? newCap : null;
    }

    public void setCaptureNum(int captureNum) {
        this.captureNum = captureNum;
    }
    public int getCaptureNum(){return captureNum;}
    public ArrayList<RowCol> getCaptured(){
        return this.newCap;
    }

    public ArrayList<RowCol> getAvailableMoves(){
        updateBoard();
        ArrayList<RowCol> alist = new ArrayList();
        for (int i=0; i<rowLength; i++){
            for (int j=0; j<colLength; j++){
                if (currentBoard[i][j] == 0){
                    alist.add(new RowCol(i,j));
                }
            }
        }
        return alist;

    }
    public ArrayList getAvailableMovesDegree(){

        updateBoard();

        ArrayList<MoveD> availableMovesD = new ArrayList<>();

        for (int i=0; i<rowLength; i++){
            for (int j=0; j<colLength; j++){
                if (currentBoard[i][j] == 0){
                    int degree=0;
                    if(MOVE_ORDERING){
                        if(FOUR_IN_A_ROW) {
                            degree = calcDegree4(i,j);}
                        else if(THREE_IN_A_ROW){
                            degree = calcDegree3(i,j);
                        }
                    }
                    availableMovesD.add(new MoveD(new RowCol(i,j), degree));
                }
            }
        }

        Collections.sort(availableMovesD, new Comparator<MoveD>() {
            public int compare(MoveD a1, MoveD a2) {
                return -Integer.compare(a1.getDegree(),(a2.getDegree()));
            }
        });
//        System.out.println("first e "+ availableMoves.get(0).getDegree()+ " "+availableMoves.get(availableMoves.size()-1).getDegree());
        availableMoves = new ArrayList<>();
        for(MoveD mD: availableMovesD ){
            availableMoves.add(mD.getRc());
        }

        return availableMoves;
    }
    public void revertMove(int thisPlayer, Move m){

        board.setBoard(m.getRowCol().getRow(),m.getRowCol().getCol(),EMPTY);
        if(m.getCaptured()!=null&& m.getCaptured().size()>0) {
            for (RowCol stone : m.getCaptured()) {
                board.setBoard(stone.getRow(), stone.getCol(), -thisPlayer);
                captureNum=captureNum--;
                if(thisPlayer==BLACK){
                    blackCapCount--;
                }
                else if(thisPlayer==WHITE){
                    whiteCapCount--;
                }
            }
        }


        updateBoard();
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
    public static void main(String[] args){
        int[][] b = {{1,0,0,0},
                {0,0,0,0},
                {0,0,0,0},
                {0,0,0,-1}};
        MainGame mg = new MainGame(new Board(b));
        for(int i=0; i<4; i++){
            for (int j=0; j<4; j++){
//                System.out.print(mg.calcDegree(i,j)+" ");
            }
            System.out.println();
        }
//        System.out.println(mg.calcDegree(0,1));


    }
}
