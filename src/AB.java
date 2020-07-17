import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;

public class AB {

        private int aiPlayer;
        private Tree tree;
        public int zobCount;
//        private final static int maxDepth =10;
        public int maxDepth;
        private boolean record;
        private ArrayList<int[][]> sequence;
        private int sequenceIndex;
        private Zobrist zob;
        private TranspositionTable t;
        public int runCounter;
        public AB(int aiPlayer, MainGame mg, Zobrist z, TranspositionTable t) throws IOException {
            this.aiPlayer = aiPlayer;
            this.zob = z;
            this.t =t;
            this.sequence = new ArrayList<>();
            this.sequenceIndex = -1;
            if(aiPlayer==1){
                tree = new Tree(mg);}
            this.record=true;
            this.zobCount=0;
            runCounter=0;
            maxDepth=0;
        }
        public int miniMax(MainGame mg, int currentDepth, int a, int b, int player, boolean maxPlayer, RowCol rc) {
            maxDepth=Math.max(maxDepth,currentDepth);
            runCounter++;
//            if(mg.ifMakeLine(rc.getRow(),rc.getCol())==-player){
//                BitSet[] bitSet = zob.hash(mg.getBoard().getBoard());
//                int ent[] = zob.entryCalc(bitSet);
//                int minEntry = zob.minEntryIndex(ent);
//                if(-player==aiPlayer){
//                    t.updateEntry(ent[minEntry], bitSet[minEntry], (byte) 1, currentDepth, aiPlayer);
//                    return 1;
//                }
//                else{
//                    t.updateEntry(ent[minEntry], bitSet[minEntry], (byte) -1, currentDepth, aiPlayer);
//                    return -1;
//                }
//
//            }
//            if(mg.getBoard().isFull()){
//                BitSet[] bitSet = zob.hash(mg.getBoard().getBoard());
//                int ent[] = zob.entryCalc(bitSet);
//                int minEntry = zob.minEntryIndex(ent);
//                t.updateEntry(ent[minEntry], bitSet[minEntry], (byte) 0, currentDepth, aiPlayer);
//                return 0;
//            }

            if (mg.ifEnd()) {
                BitSet[] bitSet = zob.hash(mg.getBoard().getBoard());
                int ent[] = zob.entryCalc(bitSet);
                int minEntry = zob.minEntryIndex(ent);
                if (mg.getWinner() == aiPlayer) {
                    mg.setWinner(0);
//                    mg.setCaptureNum(0);
//                System.out.println("aiPlayer wins");
                    t.updateEntry(ent[minEntry], bitSet[minEntry], (byte) 1, currentDepth, aiPlayer);
//                System.out.println(t.getScore(ent,aiPlayer));
                    return 1;
                }
                if (mg.getWinner() == -aiPlayer) {
                    mg.setWinner(0);
//                    mg.setCaptureNum(0);
//                System.out.println("aiPlayer loses");
                    t.updateEntry(ent[minEntry], bitSet[minEntry], (byte) -1, currentDepth, aiPlayer);
                    return -1;
                }
                if (mg.getWinner()==0) {
                    t.updateEntry(ent[minEntry], bitSet[minEntry], (byte) 0, currentDepth, aiPlayer);
                    return 0;
                }
            }




//                BitSet bitSet[] = zob.hash(mg.getBoard().getBoard());
//                int ent[] = zob.entryCalc(bitSet);
//                int minEntry = zob.minEntryIndex(ent);
//                t.updateEntry(ent[minEntry], bitSet[minEntry], (byte) 0, currentDepth, aiPlayer);
////            mg.getBoard().printBoard();
//                return 0;
//            }

            ArrayList<RowCol> moves = mg.getAvailableMoves();
            int nodeCnt = moves.size();

            if (maxPlayer == true) {
                int maxScore = Integer.MIN_VALUE;

                for (int i = 0; i < moves.size(); i++) {

                    RowCol move = moves.get(i);

                    ArrayList cap = mg.placeStone(move.getRow(), move.getCol(), player);
//                    System.out.println("p"+player+" at ("+move.getRow()+", "+move.getCol());
//                    mg.getBoard().printBoard();
                    BitSet bitSet[] = zob.hash(mg.getBoard().getBoard());
                    int ent[] = zob.entryCalc(bitSet);
                    int minEntry = zob.minEntryIndex(ent);
                    if(mg.CAPTURE_TWO&& cap.size()>0 ){
                        for(int j = sequence.size()-1; j>=0; j--){
                            if(Arrays.deepEquals(mg.getBoard().getBoard(),sequence.get(j))){
                                t.updateEntry(ent[minEntry], bitSet[minEntry], (byte) 0, currentDepth, aiPlayer);
//            mg.getBoard().printBoard();

                                return 0;
                            }
                        }

                    }
                    sequence.add(mg.getBoard().copy().getBoard());

                    Move m = new Move(move, cap);


                    BitSet[] bitBoard = zob.hash(mg.getBoard().getBoard());
                    int score = 0;

                    int entryIndex = t.checkEntryExist(ent);
                    if (entryIndex!=-1) {
                        if (t.ifSameBoard(ent[entryIndex], bitBoard[entryIndex])) {
                            score = t.getScore(ent[entryIndex], aiPlayer);
                            zobCount++;
                            if (currentDepth < 5 && record && aiPlayer == 1) {
                                tree.solve(mg, move, moves.size(), currentDepth, score,1);
                            }
                        } else {
//                            System.out.println("Collision");
                            score = miniMax(mg, currentDepth + 1, a, b, -player, false,move);
                            if (currentDepth <= t.getDepth(ent[entryIndex])) {
                                t.updateEntry(ent[entryIndex], bitBoard[entryIndex], (byte) score, currentDepth, aiPlayer);
                            }
                            if (currentDepth < 5 && record && aiPlayer == 1) {
                                tree.solve(mg, move, moves.size(), currentDepth, score,1);
                            }
                        }
                    } else {
                        score = miniMax(mg, currentDepth + 1, a, b, -player, false,move);
                        t.updateEntry(ent[minEntry], bitBoard[minEntry], (byte) score, currentDepth, aiPlayer);
                        if (currentDepth < 5 && record && aiPlayer == 1) {
                            tree.solve(mg, move, moves.size(), currentDepth, score,1);
                        }
                    }

                    mg.revertMove(player, m);
                    sequence.remove(sequence.size()-1);
                    if (score > maxScore) {
                        maxScore = score;
                    }

//                maxScore = Math.max(maxScore, score);
//                System.out.println("max"+maxScore+"score"+score);
                    a = Math.max(a, maxScore);
                    if (a >= b) {
//                    System.out.println("pruned");
                        break;
                    }
                }
//            if(currentDepth<5) {
//                tree.solve(mg, moves.get(indexMaxScore), moves.size(),currentDepth);
//            }
                return maxScore;
            } else {
                int minScore = Integer.MAX_VALUE;
                int indexMinScore = -1;
                for (int i = 0; i < moves.size(); i++) {

                    RowCol move = moves.get(i);


                    ArrayList cap = mg.placeStone(move.getRow(), move.getCol(), player);
//                    System.out.println("p"+player+" at ("+move.getRow()+", "+move.getCol());
//                    mg.getBoard().printBoard();
                    BitSet bitSet[] = zob.hash(mg.getBoard().getBoard());
                    int ent[] = zob.entryCalc(bitSet);
                    int minEntry = zob.minEntryIndex(ent);
                    if(mg.CAPTURE_TWO &&cap.size()>0 ){
                        for(int j = sequence.size()-1; j>=0; j--){
                            if(Arrays.deepEquals(mg.getBoard().getBoard(),sequence.get(j))){
//                                System.out.println(Arrays.deepToString(seq));
//                                System.out.println("Loop");
//                                System.out.println(Arrays.deepToString(mg.getBoard().getBoard()));

                                t.updateEntry(ent[minEntry], bitSet[minEntry], (byte) 0, currentDepth, aiPlayer);


                                return 0;
                            }
                        }

                    }
                    sequence.add(mg.getBoard().copy().getBoard());
                    Move m = new Move(move, cap);
//
                    BitSet[] bitBoard = zob.hash(mg.getBoard().getBoard());
                    int score = -100;


                    int entryIndex = t.checkEntryExist(ent);
                    if (entryIndex!=-1) {
                        if (t.ifSameBoard(ent[entryIndex], bitBoard[entryIndex])) {
                            score = t.getScore(ent[entryIndex], aiPlayer);
                            zobCount++;
                            if (currentDepth < 5 && record && aiPlayer == 1) {
                                tree.solve(mg, move, moves.size(), currentDepth, score,2);
                            }

                        } else {
//                            System.out.println("Collision");
                            score = miniMax(mg, currentDepth + 1, a, b, -player, true,move);
                            if (currentDepth <= t.getDepth(ent[entryIndex])) {
                                t.updateEntry(ent[entryIndex], bitBoard[entryIndex], (byte) score, currentDepth, aiPlayer);
                            }
                            if (currentDepth < 5 && record && aiPlayer == 1) {
                                tree.solve(mg, move, moves.size(), currentDepth, score,2);
                            }
                        }
                    } else {
                        score = miniMax(mg, currentDepth + 1, a, b, -player, true,move);
                        t.updateEntry(ent[minEntry], bitBoard[minEntry], (byte) score, currentDepth, aiPlayer);
                        if (currentDepth < 5 && record && aiPlayer == 1) {
                            tree.solve(mg, move, moves.size(), currentDepth, score,2);
                        }
                    }


                    if (score < minScore) {
                        minScore = score;
                        indexMinScore = i;
                    }

//                minScore = Math.min(minScore, score);
                    mg.revertMove(player, m);
                    sequence.remove(sequence.size()-1);
                    b = Math.min(b, minScore);
                    if (a >= b) {
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
