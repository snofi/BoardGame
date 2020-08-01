import java.io.IOException;

public class Solver {
    public static void main(String args[]) throws IOException {
        long startTime = System.nanoTime();
//       Scanner scanner = new Scanner(System.in);


//       Board board = new Board(example);
        int[][] b = {{1,1,1},
                {-1,1,-1},
                {0,0,0}};
        Board board = new Board(5,5);
        MainGame game = new MainGame(board);

//        System.out.println(game.ifMakeLine(1,1));

        Zobrist zob = new Zobrist(board.getRowLength(),board.getColLength());
        TranspositionTable t = new TranspositionTable();

        AB ai = new AB(1,game,zob, t);
        board.printBoard();

        int result = ai.miniMax(game, 0,-5, 5, 1, true, new RowCol(0,0));

        System.out.println("result: "+ result);

        long endTime   = System.nanoTime();
        long totalTime = (endTime - startTime);
        double duration = (double)totalTime/100000000;
        System.out.println(duration+" seconds");
        System.out.println("writing");
//        TimeUnit.SECONDS.sleep(15);
        System.out.println("zobCOunt: "+ ai.zobCount);

    }
}
