import java.util.ArrayList;

public class AlphaBeta {
    private int aiPlayer;
    private final static int maxDepth =29;
    public AlphaBeta(int aiPlayer){
        this.aiPlayer = aiPlayer;
    }
    public RowCol nextMove(MainGame mg){
        if(mg.getCaptured().size()>0 ) {
            if(mg.getCaptured().get(0)!=null){
            return mg.getCaptured().get(0);}
        }
        ArrayList<RowCol> moves = mg.getAvailableMoves();


        int maxScore = Integer.MIN_VALUE;
        int indexMaxScore = -1;
        if(moves.size()==1){ return moves.get(0);}

        else{

            RowCol fix = winOrThreat(mg,aiPlayer, moves);
            if(fix!=null){ return fix;}

            for(int i=0; i<moves.size(); i++){
                RowCol move = moves.get(i);


                MainGame gameCopy = mg.copy(aiPlayer);

                gameCopy.placeStone(move.getRow(),move.getCol(),aiPlayer);


                int score = miniMax(gameCopy, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, -aiPlayer, false);
                if(score==aiPlayer)return move;

                if(score>maxScore){
                    maxScore=score;
                    indexMaxScore = i;
                }

            }
        }

        return moves.get(indexMaxScore) ;
    }
    private int miniMax(MainGame mg, int currentDepth, int a, int b, int player, boolean maxPlayer){

        if(mg.getBoard().isFull()||mg.getWinner()!=0){

            return calcHeuristic(mg);
        }

        ArrayList<RowCol> moves = mg.getAvailableMoves();

        if(maxDepth == currentDepth){

            return calcHeuristic(mg);
        }
        if(mg.getCaptured().size()>0){
            RowCol move = mg.getCaptured().get(0);
            MainGame gameCopy = mg.copy(player);
            gameCopy.placeStone(move.getRow(), move.getCol(), player);
            return miniMax(gameCopy, currentDepth+1, a, b,-player, !maxPlayer);
        }
        RowCol endMove = winOrThreat(mg,player, moves);
        if(endMove!=null){
            MainGame gameCopy = mg.copy(player);
            gameCopy.placeStone(endMove.getRow(), endMove.getCol(), player);
            if(gameCopy.ifMakeLine(endMove.getRow(),endMove.getCol())==player){
                gameCopy.setWinner(player);
            }
            return miniMax(gameCopy, currentDepth+1, a, b,-player, !maxPlayer);
        }

        if(maxPlayer == true){
            int maxScore = Integer.MIN_VALUE;

            for(int i=0; i< moves.size(); i++){
                MainGame gameCopy = mg.copy(player);
                RowCol move = moves.get(i);


                gameCopy.placeStone(move.getRow(),move.getCol(),player);
                if(gameCopy.ifMakeLine(move.getRow(),move.getCol())==player){
                    gameCopy.setWinner(player);
                }

                int score = miniMax(gameCopy, currentDepth+1, a, b,-player, false);

                maxScore = Math.max(maxScore, score);
//                System.out.println("max"+maxScore+"score"+score);
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

                if(gameCopy.ifMakeLine(move.getRow(),move.getCol())==player){
                    gameCopy.setWinner(player);
                }
                int score = miniMax(gameCopy,  currentDepth+1, a,b, -player,  true);
                minScore = Math.min(minScore, score);

//                    System.out.println(move.getRow()+" "+move.getCol() + "score"+ score);


                b= Math.min(b, minScore);
                if(a>=b){
                    break;}
            }

            return minScore;
        }


    }
    private int calcHeuristic(MainGame mg){
        int val = mg.getWinner();

        if(val==aiPlayer) {

//            System.out.println("com won" + aiPlayer);
            return 1;}
        if(val == -aiPlayer) {return -1;}
        return 0;
    }

    private RowCol winOrThreat(MainGame mg, int player, ArrayList<RowCol> moves){
        RowCol next = null;
        for(int i=0; i<moves.size();i++){
            RowCol move = moves.get(i);
            if(mg.ifMakeLine(move.getRow(),move.getCol())==player){
                return move;
            }
            if(mg.ifMakeLine(move.getRow(),move.getCol())==-player){
                next = move;
            }
        }
        if(next!=null){return next;}
        return null;
    }


}
