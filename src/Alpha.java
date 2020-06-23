import java.io.IOException;
import java.util.ArrayList;

public class Alpha {
    private int aiPlayer;
    private Tree tree;
    private final static int maxDepth =5;
    public Alpha(int aiPlayer, MainGame mg) throws IOException {
        this.aiPlayer = aiPlayer;

        tree = new Tree(mg);
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
                tree.initialSolve(mg,m, 0,i);
                int score = miniMax(mg, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, -aiPlayer, false);



                if(score>maxScore){
                    maxScore=score;
                    indexMaxScore = i;
                }
                tree.solve(mg, moves.size(),0);
                mg.revertMove(aiPlayer,m);
                if(score==1) {
                    return move;
                }
            }
        }


        return moves.get(indexMaxScore) ;
    }
    private int miniMax(MainGame mg, int currentDepth, int a, int b, int player, boolean maxPlayer){

        if(currentDepth==maxDepth)return -1;
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

            for(int i=0; i< moves.size(); i++){

                RowCol move = moves.get(i);


                ArrayList cap = mg.placeStone(move.getRow(),move.getCol(),player);
                Move m = new Move(move,cap);
//                mg.getBoard().printBoard();
//                if(mg.ifMakeLine(move.getRow(),move.getCol())==player){
//
//                    mg.revertMove(player,move);
//                    return 1;
//                }

                int score = miniMax(mg, currentDepth+1, a, b,-player, false);

                mg.revertMove(player,m);

                maxScore = Math.max(maxScore, score);
//                System.out.println("max"+maxScore+"score"+score);
                a = Math.max(a, maxScore);
                if (a>= b){
                    break;
                }
            }
            if(currentDepth<5) {
                tree.solve(mg, nodeCnt,currentDepth);
            }
            return maxScore;
        }
        else{
            int minScore = Integer.MAX_VALUE;
            for(int i=0; i< moves.size(); i++){

                RowCol move = moves.get(i);


                ArrayList cap = mg.placeStone(move.getRow(), move.getCol(), player);
                Move m = new Move(move,cap);
//               mg.getBoard().printBoard();
                int score = miniMax(mg,  currentDepth+1, a,b, -player,  true);


                minScore = Math.min(minScore, score);
                mg.revertMove(player,m);

                b= Math.min(b, minScore);
                if(a>=b){
                    break;}
            }
            if(currentDepth<5) tree.solve(mg, nodeCnt,currentDepth);
            return minScore;
        }

    }

}
