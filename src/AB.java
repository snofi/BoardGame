import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;

public class AB {

        private int aiPlayer;
        private Tree tree;
        public int zobCount;
//        private final static int maxDepth =10;
        private boolean record;
        private Zobrist zob;
        private TranspositionTable t;
        public AB(int aiPlayer, MainGame mg, Zobrist z, TranspositionTable t) throws IOException {
            this.aiPlayer = aiPlayer;
            this.zob = z;
            this.t =t;
            if(aiPlayer==1){
                tree = new Tree(mg);}
            this.record=true;
            this.zobCount=0;
        }
        public int miniMax(MainGame mg, int currentDepth, int a, int b, int player, boolean maxPlayer, RowCol rc) {
            int sta =0;
            if(mg.ifLine()){
            BitSet[] bitSet = zob.hash(mg.getBoard().getBoard());
            int ent[] = zob.entryCalc(bitSet);
//
//
            if(mg.getWinner()==aiPlayer){
                mg.setWinner(0);
                mg.setCaptureNum(0);
//                System.out.println("aiPlayer wins");
                t.updateEntry(ent[0],bitSet[0], (byte)1,(byte)3, currentDepth,player);
//                System.out.println(t.getScore(ent,aiPlayer));
                return 1;

            }
            if(mg.getWinner()==-aiPlayer){
                mg.setWinner(0);
                mg.setCaptureNum(0);
//                System.out.println("aiPlayer loses");
                t.updateEntry(ent[0],bitSet[0], (byte)-1,(byte)3, currentDepth,player);
                return -1;
            }
             }
//             int res = mg.ifMakeLine(rc.getRow(),rc.getCol());
//
//            if(res!=0){
//                BitSet[] bitSet = zob.hash(mg.getBoard().getBoard());
//                int ent[] = zob.entryCalc(bitSet);
//                t.updateEntry(ent[0],bitSet[0], (byte)res,(byte)3, currentDepth,player);
//                return res;
//            }


        if(mg.getBoard().isFull()){
            BitSet[] bitSet = zob.hash(mg.getBoard().getBoard());
            int ent[] = zob.entryCalc(bitSet);
            t.updateEntry(ent[0],bitSet[0], (byte)0, (byte)3,currentDepth,player);
//            mg.getBoard().printBoard();
            return 0;
        }
            ArrayList<RowCol> moves = mg.getAvailableMoves();
            int nodeCnt = moves.size();
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
                BitSet[] bitBoard = zob.hash(mg.getBoard().getBoard());
                int score=0;
                int entry[] = zob.entryCalc(bitBoard);
//                int ind = -1;
                int writeInd=-1;
                int ind = t.checkEntryExist(entry);
               if( ind!=-1){
                   if(t.ifSameBoard(entry[ind],bitBoard[ind],b, player)){
                       zobCount++;
                    score =t.getScore(entry[ind],aiPlayer);
//                       System.out.println("depth"+currentDepth+" looked"+ " score"+score+", "+player+" at "+ move.getRow()+","+move.getCol());
                   }
                   else{
                       score = miniMax(mg, currentDepth+1, a, b,-player, false,move);
//                       System.out.println("depth"+currentDepth+ " score"+score+", "+player+" at "+ move.getRow()+","+move.getCol());
                       if(ind!=0){
//                           System.out.println("depth"+currentDepth+ " score"+score+", "+player+" at "+ move.getRow()+","+move.getCol()+ " write");
                           writeInd = 0;
                       }
                       else if(currentDepth<=t.getDepth(entry[ind])){
//                           System.out.println("depth"+currentDepth+ " score"+score+", "+player+" at "+ move.getRow()+","+move.getCol()+ " write");
                           writeInd= ind;
                       }
                   }
               }
               else{
                   score = miniMax(mg, currentDepth+1, a, b,-player, false,move);
                   writeInd=0;
//                   System.out.println("depth"+currentDepth+ " score"+score+", "+player+" at "+ move.getRow()+","+move.getCol()+" write");
               }

//                if(currentDepth<9) {
//                    System.out.println("depth"+currentDepth+ " score"+score+", "+player+" at "+ move.getRow()+","+move.getCol()+" write");
//                    mg.getBoard().printBoard();
////                    tree.solve(mg, move, moves.size(),currentDepth,score);
//                }


                if(score==1&&currentDepth==6){
//                    System.out.println("why");
                }
                mg.revertMove(player,m);
//                if(score>maxScore){
//                    maxScore=score;
//                    indexMaxScore = i;
//                }
               maxScore = Math.max(maxScore, score);

//                System.out.println("max"+maxScore+"score"+score);
                a = Math.max(a, maxScore);
                if(currentDepth<3)
                {System.out.println(a+ ", "+b);}
                if(writeInd!=-1){
                t.updateEntry(entry[writeInd],bitBoard[writeInd],(byte)score,(byte)b,currentDepth,player);}
                if (a>=b||maxScore==1){
//                    System.out.println("pruned");
                    break;
                }


//
            }
//            if(currentDepth<5) {
//                tree.solve(mg, moves.get(indexMaxScore), moves.size(),currentDepth);
//            }
            return maxScore;
        }
        else{
            int minScore = Integer.MAX_VALUE;
//            int indexMinScore = -1;
            for(int i=0; i< moves.size(); i++){

                RowCol move = moves.get(i);


                ArrayList cap = mg.placeStone(move.getRow(), move.getCol(), player);

                Move m = new Move(move,cap);
//                if(currentDepth<5&&record) {
//                    tree.solve(mg, move, moves.size(),currentDepth,-100);
//                }
//               mg.getBoard().printBoard();
                BitSet[] bitBoard = zob.hash(mg.getBoard().getBoard());
                int score;
                int entry[] = zob.entryCalc(bitBoard);
               int writeInd = -1;
                int inm = t.checkEntryExist(entry);
                if( inm!=-1){

                    if(t.ifSameBoard(entry[inm],bitBoard[inm],a,player)){
zobCount++;
                        score =t.getScore(entry[inm],aiPlayer);
//                        System.out.println("depth"+currentDepth+" looked"+ " score"+score+", "+player+" at "+ move.getRow()+","+move.getCol());

                    }
                    else{
                        score = miniMax(mg, currentDepth+1, a, b,-player, true,move);
//                        System.out.println("depth"+currentDepth+ " score"+score+", "+player+" at "+ move.getRow()+","+move.getCol());

                        if(inm!=0){
//                            System.out.println("depth"+currentDepth+ " score"+score+", "+player+" at "+ move.getRow()+","+move.getCol()+ " write");
                           writeInd=0;
                        }
                        else if(currentDepth<=t.getDepth(entry[inm])){
//                            System.out.println("depth"+currentDepth+ " score"+score+", "+player+" at "+ move.getRow()+","+move.getCol()+ " write");
                            writeInd=inm;
                        }
                    }
                }
                else{
                    score = miniMax(mg, currentDepth+1, a, b,-player, true,move);
                    writeInd=0;
//                    System.out.println("depth"+currentDepth+ " score"+score+", "+player+" at "+ move.getRow()+","+move.getCol()+" write");
                }
//                if(currentDepth<9) {
//                    System.out.println("depth"+currentDepth+ " score"+score+", "+player+" at "+ move.getRow()+","+move.getCol()+" write");
//                    mg.getBoard().printBoard();
////                    tree.solve(mg, move, moves.size(),currentDepth,score);
//                }

//                if(score<minScore){
//                    minScore=score;
////                    indexMinScore = i;
//                }
                minScore = Math.min(score, minScore);
                if(currentDepth==7&&move.getRow()==2&&move.getCol()==1&&mg.getBoard().getBoard()[0][0]==1&&mg.getBoard().getBoard()[0][1]==-1&&mg.getBoard().getBoard()[0][2]==-1){
                    if(mg.getBoard().getBoard()[1][0]==-1&&mg.getBoard().getBoard()[1][1]==1&&mg.getBoard().getBoard()[1][2]==1){
                    System.out.println("stop");}
                }


//                minScore = Math.min(minScore, score);
                mg.revertMove(player,m);

                b= Math.min(b, minScore);
                if(currentDepth<3){
                System.out.println(a+ ", "+b);}
                if(writeInd!=-1){
                t.updateEntry(entry[writeInd],bitBoard[writeInd],(byte)score,(byte)a,currentDepth,player);}
                if(a>=b||minScore==-1){
//                    System.out.println("pruned");
                    break;
                }
            }
//            if(currentDepth<5) {
//                tree.solve(mg, moves.get(indexMinScore), moves.size(),currentDepth);
//            }
            return minScore;
        }

        }
}
