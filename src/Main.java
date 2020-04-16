import java.util.Scanner;

public class Main {
    public static void main(String args[]){
       Scanner scanner = new Scanner(System.in);


//int[][] example = {{0,1,0},
//                    {-1,-1,0},
//                    {0,0,1}};

//       Board board = new Board(example);
        Board board = new Board(3,3);
       MainGame game = new MainGame(board);

       AlphaBeta ai = new AlphaBeta(-1);
       while(!game.ifEnd()){
           board.printBoard();
           int row = scanner.nextInt();
           int col = scanner.nextInt();
           game.placeStone(row,col, 1);


           board.printBoard();

           if(!game.ifEnd()) {
               RowCol rc = ai.nextMove(game);
               game.placeStone(rc.getRow(), rc.getCol(), -1);
           }

       }
       board.printBoard();

    }
}
