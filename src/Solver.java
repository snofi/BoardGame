import java.io.IOException;

public class Solver {
    public static void main(String args[]) throws IOException {
        long startTime = System.nanoTime();
//       Scanner scanner = new Scanner(System.in);

        int[][] b = {{0,0,0,0,0,0},
                {0,0,1,0,0,1},
                {0,0,1,1,-1,0},
                {0,0,0,-1,-1,0},
                {0,0,-1,0,0,0}};
        //  Two way to initialize board, 1) pass on a board array 2) pass on a dimension n,k for empty board

//      Board board = new Board(b);
        Board board = new Board(3,4);
        MainGame game = new MainGame(board);

//        System.out.println(game.ifMakeLine(1,1));

        Zobrist zob = new Zobrist(board.getRowLength(),board.getColLength()); // initialize zobrist hashing
        TranspositionTable t = new TranspositionTable(); // initialize transposition table

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
