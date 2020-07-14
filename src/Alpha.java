import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;

public class Alpha {
    private int aiPlayer;
    private Tree tree;
    private final static int maxDepth =10;
    private boolean record;
    private Zobrist zob;
    private TranspositionTable t;
    public Alpha(int aiPlayer, MainGame mg, Zobrist z, TranspositionTable t) throws IOException {
        this.aiPlayer = aiPlayer;
this.zob = z;
this.t =t;
if(aiPlayer==1){
        tree = new Tree(mg);}
        this.record=true;
    }
    public RowCol nextMove(MainGame mg){

        ArrayList<RowCol> moves = mg.getAvailableMoves();


        int maxScore = Integer.MIN_VALUE;
        int indexMaxScore = -1;
        int last = 0;
        int[] list = new int[moves.size()];
        boolean[] tf = new boolean[moves.size()];
        if(moves.size()==1){ return moves.get(0);}
        else{

            for(int i=0; i<moves.size(); i++){
                RowCol move = moves.get(i);

                ArrayList cap =  mg.placeStone(move.getRow(),move.getCol(),aiPlayer);
//                tree.solve(mg, move, moves.size(),0,-100);
                Move m = new Move(move,cap);
//                mg.getBoard().printBoard();
                BitSet bitBoard = zob.hash(mg.getBoard().getBoard());
                int score=0;
                int entry = zob.entryCalc(bitBoard);
                if( t.checkEntryExist(entry)){
                    if(t.ifSameBoard(entry,bitBoard)){
                        score =t.getScore(entry,aiPlayer);
                        tf[i]=true;
                    }
                    else{
//                        System.out.println("collision!");
                        score =miniMax(mg, 1, Integer.MIN_VALUE, Integer.MAX_VALUE, -aiPlayer, false);
                        if(0<=t.getDepth(entry)){
                            t.updateEntry(entry,bitBoard,(byte)score,0,aiPlayer);
                        }
                    }
                }
                else{
                    score = miniMax(mg, 1, Integer.MIN_VALUE, Integer.MAX_VALUE, -aiPlayer, false);
                    t.updateEntry(entry,bitBoard,(byte)score,0,aiPlayer);
                }
                list[i]=score;
                if(aiPlayer==1) {
                    tree.solve(mg, move, moves.size(), 0, score);
                }


                if(score>maxScore){
                    maxScore=score;
                    indexMaxScore = i;
                }
                last = score;

                mg.revertMove(aiPlayer,m);
                if(score==1) {
//                    System.out.println("initial pruned");

                    return move;
                }
            }
        }

//        System.out.println("score of maxMove: "+maxScore);
//        System.out.println(Arrays.toString(list));
//        System.out.println(Arrays.toString(tf));
        return moves.get(indexMaxScore) ;
    }
    private int miniMax(MainGame mg, int currentDepth, int a, int b, int player, boolean maxPlayer){

//        if(currentDepth==maxDepth)return 0;
        if(mg.ifLine()){
            BitSet bitSet = zob.hash(mg.getBoard().getBoard());
            int ent = zob.entryCalc(bitSet);
            if(mg.getWinner()==aiPlayer){
                mg.setWinner(0);
                mg.setCaptureNum(0);
//                System.out.println("aiPlayer wins");
                t.updateEntry(ent,bitSet, (byte)1, currentDepth,aiPlayer);
//                System.out.println(t.getScore(ent,aiPlayer));
                return 1;
            }
            if(mg.getWinner()==-aiPlayer){
                mg.setWinner(0);
                mg.setCaptureNum(0);
//                System.out.println("aiPlayer loses");
                t.updateEntry(ent,bitSet, (byte)-1, currentDepth,aiPlayer);
                return -1;
            }
        }
        if(mg.getBoard().isFull()){
            BitSet bitSet = zob.hash(mg.getBoard().getBoard());
            int ent = zob.entryCalc(bitSet);
            t.updateEntry(ent,bitSet, (byte)0, currentDepth,aiPlayer);
//            mg.getBoard().printBoard();
            return 0;
        }
        ArrayList<RowCol> moves = mg.getAvailableMoves();
        int nodeCnt=moves.size();
        if(maxPlayer == true){
            int maxScore = Integer.MIN_VALUE;
            int indexMaxScore = -1;
            for(int i=0; i< moves.size(); i++){

                RowCol move = moves.get(i);


                ArrayList cap = mg.placeStone(move.getRow(),move.getCol(),player);

                Move m = new Move(move,cap);

//                if(currentDepth<5&&record) {
//                    tree.solve(mg, move, moves.size(),currentDepth,-100);
//                }
//                mg.getBoard().printBoard();
//                if(mg.ifMakeLine(move.getRow(),move.getCol())==player){
//
//                    mg.revertMove(player,move);
//                    return 1;
//                }
                BitSet bitBoard = zob.hash(mg.getBoard().getBoard());
                int score=0;
                int entry = zob.entryCalc(bitBoard);
               if( t.checkEntryExist(entry)){
                   if(t.ifSameBoard(entry,bitBoard)){
                    score =t.getScore(entry,aiPlayer);
                   }
                   else{
                       score = miniMax(mg, currentDepth+1, a, b,-player, false);
                       if(currentDepth<=t.getDepth(entry)){
                           t.updateEntry(entry,bitBoard,(byte)score,currentDepth,aiPlayer);
                       }
                   }
               }
               else{
                   score = miniMax(mg, currentDepth+1, a, b,-player, false);
                   t.updateEntry(entry,bitBoard,(byte)score,currentDepth,aiPlayer);
               }
                if(currentDepth<5&&record&&aiPlayer==1) {
                    tree.solve(mg, move, moves.size(),currentDepth,score);
                }
                mg.revertMove(player,m);
                if(score>maxScore){
                    maxScore=score;
                    indexMaxScore = i;
                }

//                maxScore = Math.max(maxScore, score);
//                System.out.println("max"+maxScore+"score"+score);
                a = Math.max(a, maxScore);
                if (a>= b){
//                    System.out.println("pruned");
                    break;
                }
            }
//            if(currentDepth<5) {
//                tree.solve(mg, moves.get(indexMaxScore), moves.size(),currentDepth);
//            }
            return maxScore;
        }
        else{
            int minScore = Integer.MAX_VALUE;
            int indexMinScore = -1;
            for(int i=0; i< moves.size(); i++){

                RowCol move = moves.get(i);


                ArrayList cap = mg.placeStone(move.getRow(), move.getCol(), player);

                Move m = new Move(move,cap);
//                if(currentDepth<5&&record) {
//                    tree.solve(mg, move, moves.size(),currentDepth,-100);
//                }
//               mg.getBoard().printBoard();
                BitSet bitBoard = zob.hash(mg.getBoard().getBoard());
                int score=0;
                int entry = zob.entryCalc(bitBoard);
                if( t.checkEntryExist(entry)){
                    if(t.ifSameBoard(entry,bitBoard)){
                        score =t.getScore(entry,aiPlayer);
                    }
                    else{
                        score = miniMax(mg, currentDepth+1, a, b,-player, true);
                        if(currentDepth<=t.getDepth(entry)){
                            t.updateEntry(entry,bitBoard,(byte)score,currentDepth,aiPlayer);
                        }
                    }
                }
                else{
                    score = miniMax(mg, currentDepth+1, a, b,-player, true);
                    t.updateEntry(entry,bitBoard,(byte)score,currentDepth,aiPlayer);
                }
                if(currentDepth<5&&record&&aiPlayer==1) {
                    tree.solve(mg, move, moves.size(),currentDepth,score);
                }

                if(score<minScore){
                    minScore=score;
                    indexMinScore = i;
                }

//                minScore = Math.min(minScore, score);
                mg.revertMove(player,m);

                b= Math.min(b, minScore);
                if(a>=b){
//                    System.out.println("pruned");
                    break;}
            }
//            if(currentDepth<5) {
//                tree.solve(mg, moves.get(indexMinScore), moves.size(),currentDepth);
//            }
            return minScore;
        }

    }

}
