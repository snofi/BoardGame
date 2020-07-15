import java.io.IOException;

public class Solver {
    public static void main(String args[]) throws IOException {
        long startTime = System.nanoTime();
//       Scanner scanner = new Scanner(System.in);


//       Board board = new Board(example);
        Board board = new Board(5,5);
        MainGame game = new MainGame(board);

        Zobrist zob = new Zobrist(5,5);
        TranspositionTable t = new TranspositionTable();

        AB ai = new AB(1,game,zob, t);
        board.printBoard();

        int result = ai.miniMax(game, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1, true);

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
