import java.util.Scanner;

public class Main {
    public static void main(String args[]){
       Scanner scanner = new Scanner(System.in);


//int[][] example = {{-1,1,-1},
//                    {1,0,0},
//                    {0,0,0}};

//       Board board = new Board(example);
        Board board = new Board(4,4);
       MainGame game = new MainGame(board);
        AlphaBeta ai = new AlphaBeta(1);
       AlphaBeta ai2 = new AlphaBeta(-1);
        RowCol black = null;
        RowCol white = null;
       boolean end = false;
        board.printBoard();
      do{

//               board.printBoard();
//               int row = scanner.nextInt();
//               int col = scanner.nextInt();
//               game.placeStone(row,col, 1);

          black = ai.nextMove(game);
          game.placeStone(black.getRow(), black.getCol(), 1);
          System.out.println("1 at " + black.getRow()+ " "+ black.getCol());
          if(game.ifMakeLine(black.getRow(),black.getCol())==1){
              game.setWinner(1);
              end = true;
              break;}
              board.printBoard();
if(board.isFull()) break;
    white = ai2.nextMove(game);
    game.placeStone(white.getRow(), white.getCol(), -1);
    System.out.println("-1 at " + white.getRow() + " " + white.getCol());

    board.printBoard();
    if (game.ifMakeLine(white.getRow(), white.getCol()) == -1) {
        game.setWinner((-1));
        end = true;
        break;
    }

      }while(!board.isFull()&&!end);


       board.printBoard();
        System.out.println(game.getWinner());
    }
}
