import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) throws IOException {
        long startTime = System.nanoTime();
//       Scanner scanner = new Scanner(System.in);


//int[][] example = {{-1,1,-1,1},
//                    {1,0,0,0},
//                    {0,0,1,0},
//                    {-1,0,1,-1}};

//       Board board = new Board(example);
        Board board = new Board(4,4);
       MainGame game = new MainGame(board);

       Zobrist zob = new Zobrist(4,4);
       TranspositionTable t = new TranspositionTable();

        Alpha ai = new Alpha(1,game,zob, t);
       Alpha ai2 = new Alpha(-1,game,zob, t);
        RowCol black = null;
        RowCol white = null;
       boolean end = false;
        board.printBoard();

      do {


          black = ai.nextMove(game);


          System.out.println(black==null);


          game.placeStone(black.getRow(), black.getCol(), 1);
          System.out.println("1 at " + black.getRow() + " " + black.getCol());


          if (game.ifMakeLine(black.getRow(), black.getCol()) == 1) {
              game.setWinner(1);
              end = true;
              break;
          }
          board.printBoard();
          if (board.isFull()) break;
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
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println(totalTime+" seconds");
    }
}
