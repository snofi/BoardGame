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
        public int miniMax(MainGame mg, int currentDepth, int a, int b, int player, boolean maxPlayer) {

            if (mg.ifLine()) {
                BitSet[] bitSet = zob.hash(mg.getBoard().getBoard());
                int ent[] = zob.entryCalc(bitSet);
                int minEntry = zob.minEntryIndex(ent);
                if (mg.getWinner() == aiPlayer) {
                    mg.setWinner(0);
                    mg.setCaptureNum(0);
//                System.out.println("aiPlayer wins");
                    t.updateEntry(ent[minEntry], bitSet[minEntry], (byte) 1, currentDepth, aiPlayer);
//                System.out.println(t.getScore(ent,aiPlayer));
                    return 1;
                }
                if (mg.getWinner() == -aiPlayer) {
                    mg.setWinner(0);
                    mg.setCaptureNum(0);
//                System.out.println("aiPlayer loses");
                    t.updateEntry(ent[minEntry], bitSet[minEntry], (byte) -1, currentDepth, aiPlayer);
                    return -1;
                }
            }
            if (mg.getBoard().isFull()) {
                BitSet bitSet[] = zob.hash(mg.getBoard().getBoard());
                int ent[] = zob.entryCalc(bitSet);
                int minEntry = zob.minEntryIndex(ent);
                t.updateEntry(ent[minEntry], bitSet[minEntry], (byte) 0, currentDepth, aiPlayer);
//            mg.getBoard().printBoard();
                return 0;
            }
            ArrayList<RowCol> moves = mg.getAvailableMoves();
            int nodeCnt = moves.size();
            if (maxPlayer == true) {
                int maxScore = Integer.MIN_VALUE;
                int indexMaxScore = -1;
                for (int i = 0; i < moves.size(); i++) {

                    RowCol move = moves.get(i);


                    ArrayList cap = mg.placeStone(move.getRow(), move.getCol(), player);

                    Move m = new Move(move, cap);


                    BitSet[] bitBoard = zob.hash(mg.getBoard().getBoard());
                    int score = 0;
                    int[] entry = zob.entryCalc(bitBoard);
                    int minEntry = zob.minEntryIndex(entry);
                    int entryIndex = t.checkEntryExist(entry);
                    if (entryIndex!=-1) {
                        if (t.ifSameBoard(entry[entryIndex], bitBoard[entryIndex])) {
                            score = t.getScore(entry[entryIndex], aiPlayer);
                            zobCount++;
                            if (currentDepth < 5 && record && aiPlayer == 1) {
                                tree.solve(mg, move, moves.size(), currentDepth, score,1);
                            }
                        } else {
                            score = miniMax(mg, currentDepth + 1, a, b, -player, false);
                            if (currentDepth <= t.getDepth(entry[entryIndex])) {
                                t.updateEntry(entry[entryIndex], bitBoard[entryIndex], (byte) score, currentDepth, aiPlayer);
                            }
                            if (currentDepth < 5 && record && aiPlayer == 1) {
                                tree.solve(mg, move, moves.size(), currentDepth, score,1);
                            }
                        }
                    } else {
                        score = miniMax(mg, currentDepth + 1, a, b, -player, false);
                        t.updateEntry(entry[minEntry], bitBoard[minEntry], (byte) score, currentDepth, aiPlayer);
                        if (currentDepth < 5 && record && aiPlayer == 1) {
                            tree.solve(mg, move, moves.size(), currentDepth, score,1);
                        }
                    }

                    mg.revertMove(player, m);
                    if (score > maxScore) {
                        maxScore = score;
                        indexMaxScore = i;
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

                    Move m = new Move(move, cap);
//
                    BitSet[] bitBoard = zob.hash(mg.getBoard().getBoard());
                    int score = -100;
                    int entry[] = zob.entryCalc(bitBoard);
                    int minEntry = zob.minEntryIndex(entry);
                    int entryIndex = t.checkEntryExist(entry);
                    if (entryIndex!=-1) {
                        if (t.ifSameBoard(entry[entryIndex], bitBoard[entryIndex])) {
                            score = t.getScore(entry[entryIndex], aiPlayer);
                            zobCount++;
                            if (currentDepth < 5 && record && aiPlayer == 1) {
                                tree.solve(mg, move, moves.size(), currentDepth, score,2);
                            }

                        } else {
                            score = miniMax(mg, currentDepth + 1, a, b, -player, true);
                            if (currentDepth <= t.getDepth(entry[entryIndex])) {
                                t.updateEntry(entry[entryIndex], bitBoard[entryIndex], (byte) score, currentDepth, aiPlayer);
                            }
                            if (currentDepth < 5 && record && aiPlayer == 1) {
                                tree.solve(mg, move, moves.size(), currentDepth, score,2);
                            }
                        }
                    } else {
                        score = miniMax(mg, currentDepth + 1, a, b, -player, true);
                        t.updateEntry(entry[minEntry], bitBoard[minEntry], (byte) score, currentDepth, aiPlayer);
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
