import java.io.IOException;
import java.util.ArrayList;

public class Alpha {
    private int aiPlayer;
    private Tree tree;
    private final static int maxDepth =30;
    private boolean record;
    public Alpha(int aiPlayer, MainGame mg, boolean record) throws IOException {
        this.aiPlayer = aiPlayer;

        tree = new Tree(mg);
        this.record=record;
    }
    public RowCol nextMove(MainGame mg){

        ArrayList<RowCol> moves = mg.getAvailableMoves();


        int maxScore = Integer.MIN_VALUE;
        int indexMaxScore = -1;
        if(moves.size()==1){ return moves.get(0);}

        else{
            for(int i=0; i<moves.size(); i++){
                RowCol move = moves.get(i);

                ArrayList cap =  mg.placeStone(move.getRow(),move.getCol(),aiPlayer);
                Move m = new Move(move,cap);
//                mg.getBoard().printBoard();

                int score = miniMax(mg, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, -aiPlayer, false);



                if(score>maxScore){
                    maxScore=score;
                    indexMaxScore = i;
                }

                mg.revertMove(aiPlayer,m);
                if(score==1) {
//                    System.out.println("initial pruned");
                    tree.solve(mg, moves.get(indexMaxScore), moves.size(),0);
                    return move;
                }
            }
        }

        tree.solve(mg, moves.get(indexMaxScore), moves.size(),0);
        return moves.get(indexMaxScore) ;
    }
    private int miniMax(MainGame mg, int currentDepth, int a, int b, int player, boolean maxPlayer){

        if(currentDepth==maxDepth)return 0;
        if(mg.ifLine()){
            if(mg.getWinner()==aiPlayer){
                mg.setWinner(0);
                mg.setCaptureNum(0);
                return 1;
            }
            if(mg.getWinner()==-aiPlayer){
                mg.setWinner(0);
                mg.setCaptureNum(0);
                return -1;
            }
        }
        if(mg.getBoard().isFull()){
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
                if(currentDepth<5&&record) {
                    tree.solve(mg, move, moves.size(),currentDepth);
                }
//                mg.getBoard().printBoard();
//                if(mg.ifMakeLine(move.getRow(),move.getCol())==player){
//
//                    mg.revertMove(player,move);
//                    return 1;
//                }

                int score = miniMax(mg, currentDepth+1, a, b,-player, false);

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
                if(currentDepth<5&&record) {
                    tree.solve(mg, move, moves.size(),currentDepth);
                }
//               mg.getBoard().printBoard();
                int score = miniMax(mg,  currentDepth+1, a,b, -player,  true);
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
