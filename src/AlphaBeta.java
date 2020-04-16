import java.util.ArrayList;

public class AlphaBeta {
    private int aiPlayer;
    private final static int maxDepth = 10;
    public AlphaBeta(int aiPlayer){
        this.aiPlayer = aiPlayer;
    }
    public RowCol nextMove(MainGame mg){

        ArrayList<RowCol> moves = mg.getAvailableMoves();

        int maxScore = Integer.MIN_VALUE;
        int indexMaxScore = -1;
        if(moves.size()==1){return moves.get(0);}
        else{
            for(int i=0; i<moves.size(); i++){
                MainGame gameCopy = mg.copy(aiPlayer);
                RowCol move = moves.get(i);
                gameCopy.placeStone(move.getRow(),move.getCol(),aiPlayer);
                int score = miniMax(gameCopy, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, -aiPlayer, false);
                if(score>=maxScore){
                    maxScore=score;
                    indexMaxScore = i;
                }
            }
        }

        return moves.get(indexMaxScore) ;
    }
    private int miniMax(MainGame mg, int currentDepth, int a, int b, int player, boolean maxPlayer){

        if(mg.ifEnd()){

            return calcHeuristic(mg);
        }

        ArrayList<RowCol> moves = mg.getAvailableMoves();

        if(maxDepth == currentDepth){

            return calcHeuristic(mg);
        }

        if(maxPlayer == true){
            int maxScore = Integer.MIN_VALUE;
            for(int i=0; i< moves.size(); i++){
                MainGame gameCopy = mg.copy(player);
                RowCol move = moves.get(i);

                ArrayList<RowCol> captured = gameCopy.placeStone(move.getRow(),move.getCol(),player);

                if(gameCopy.ifEnd()){
                    return calcHeuristic(gameCopy);
                }
//                if(captured.size()>0){
//                    for(RowCol: r in captured){
//
//                    }
//
//                }
                int score = miniMax(gameCopy, currentDepth+1, a, b,-player, false);

                maxScore = Math.max(maxScore, score);
                System.out.println("max"+maxScore+"score"+score);
                a = Math.max(a, maxScore);
                if (a>= b){
                    break;
                }
            }

            return maxScore;
        }
        else{
            int minScore = Integer.MAX_VALUE;
            for(int i=0; i< moves.size(); i++){
                MainGame gameCopy = mg.copy(player);
                RowCol move = moves.get(i);


                gameCopy.placeStone(move.getRow(), move.getCol(), player);

                if(gameCopy.ifEnd()){
                    return calcHeuristic(gameCopy);
                }

                int score = miniMax(gameCopy,  currentDepth+1, a,b, -player,  true);
                minScore = Math.min(minScore, score);

                b= Math.min(b, minScore);
                if(a>=b){
                    break;}
            }

            return minScore;
        }


    }
    private int calcHeuristic(MainGame mg){
        int val = mg.getWinner();
//        mg.getBoard().printBoard();
        if(val==aiPlayer) {

//            System.out.println("com won" + aiPlayer);
            return 1;}
        if(val == -aiPlayer) {return -1;}
        return 0;
    }


}
