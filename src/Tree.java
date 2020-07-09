import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
public class Tree {
    private int depth;
    private int maxDepth;
    private int aiPlayer;
    private int count;
    private MainGame mg;
    PrintWriter printLine;
    private String message;
    private String path;

    public Tree(MainGame mg)throws IOException {
        this.mg = mg;
        depth = 0;
        maxDepth=5;
        count=1;

        String pattern = "yyyy-MM-dd-HH:mm:ss";
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat(pattern, new Locale("en", "US"));
        String date = simpleDateFormat.format(new Date());
//        path = "Tree_Logs/print:" + date + "boardsize_" + mg.getBoard().getRowLength()+ "x" + mg.getBoard().getColLength() + ".txt";
        path = "log_4x4capture2.txt";
        FileWriter write = new FileWriter(path,true);
        printLine = new PrintWriter(write);
        printLine.printf("%s" + "%n", "------------------new game--------------------------");
    }
//    public void initialSolve(MainGame mg, Move move, int currentDepth, int nodeNumber){
//        this.mg = mg;
//        depth = currentDepth;
//        message = "Depth is " + depth +", Node "+ nodeNumber+" Starting---";
//        printLine.printf("%s" + "%n", message);
//
////        printLine.println(message);
//
//    }
    public int solve(MainGame mg, RowCol rowCol, int nodeCnt, int currentDepth, int state){
        depth = currentDepth;


//        else System.out.println("---Player "+mg.getCurrentPlayer()+ " at "+ move.getRowCol().getRow()+", "+move.getRowCol().getCol());
        message="";
        for (int i=0;i<currentDepth;i++){
            message= message+"    ";
        }
    if (mg.getCurrentPlayer()==1) {
        message += "[Dep " + depth + "] 1, (" + rowCol.getRow() + "," + rowCol.getCol() + ") n= " + nodeCnt + " c= " + mg.getBlackCapCount() + ", r= " + state;
    }else{
        message += "[Dep " + depth + "] -1, (" + rowCol.getRow() + "," + rowCol.getCol() + ") n= " + nodeCnt + " c= " + mg.getWhiteCapCount() + ", r= " + state;
    }
        printLine.printf("%s" + "%n", message);

//        printLine.println(message);

//        System.out.println("The max depth is "+maxDepth);


        return count;

    }
}
