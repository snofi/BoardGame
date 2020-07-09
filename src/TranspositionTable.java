import java.io.IOException;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Vector;

public class TranspositionTable  {

    private long[] table;
    public TranspositionTable()  {
        table = new long[(int)Math.pow(2,26)];

    }
    public boolean checkEntryExist(int entry){
        if(table[entry]==0){
            return false;
        }
        return true;
    }
    public void updateEntry(int entry, BitSet val, byte result, int depth, int aiPlayer){
        if(aiPlayer==-1){
            result=(byte)-result;
        }
        if(result ==-1){
            result = 2;
        }
        if(result>2||result<0) {
            System.out.println("update error "+result);
        }
//        System.out.println(val);
        val.clear(0,26);
//        System.out.println(val);


//        BitSet bit38 = val.get(26,64);
//        System.out.println(bit38.length());
//        System.out.println(bit38);
        byte[] r = {result};

        BitSet bitResult = BitSet.valueOf(r);



        long d4 = depth*4;

        BitSet bitDepth = longToBitSet(d4);

        bitDepth.or(bitResult);

        val.or(bitDepth);

        table[entry]=val.toLongArray()[0];


    }
    public boolean ifSameBoard(int entry, BitSet current){
        long p = table[entry];
//        System.out.println(p);
        BitSet prev = longToBitSet(p);

        current.clear(1,26);

        prev.clear(1,26);
        if(current.equals(prev)){

            return true;
        }

        return false;

    }
    public int getScore(int entry, int aiPlayer){
        long v = table[entry];

        BitSet bit = longToBitSet(v);

        BitSet score = bit.get(0,2);
        BitSet temp = (BitSet) score.clone();
        temp.clear(0,2);
        if(score.equals(temp)){

            return 0;
        }


       int s =(int)score.toByteArray()[0];
        if(s==2){
            s=-1;
        }

        if(aiPlayer==-1){
            s = -s;
        }

        return s;
    }
    public int getDepth(int entry){
        long v = table[entry];
        BitSet bit = longToBitSet(v);

        BitSet depth = bit.get(0,26);
        depth.clear(0,2);
        BitSet temp = (BitSet) depth.clone();
        temp.clear(0,26);
        if(depth.equals(temp)){

            return 0;
        }


        long d = depth.toLongArray()[0];
        d = d/4;

        return (int)d;
    }
    public static BitSet longToBitSet(long var){
        long[] temp = {var};
        return BitSet.valueOf(temp);
    }
    public static BitSet byteToBitSet(byte var){
        long[] temp = {var};
        return BitSet.valueOf(temp);
    }
    public long getFromEntry(int entry){
        return table[entry];
    }

    public static void main(String[] args)throws IOException {
//        Zobrist z = new Zobrist(4,4);
//        TranspositionTable t = new TranspositionTable();
//        int[][] b = {{-1,1,-1,1},
//                {1,0,0,0},
//                {0,0,1,0},
//                {-1,0,1,-1}};
//        BitSet bitBoard=z.hash(new Board(b).getBoard());
//        int score=0;
//        int entry = z.entryCalc(bitBoard);
//        System.out.println("Entry= "+entry);
//
//        if( t.checkEntryExist(entry)){
//            if(t.ifSameBoard(entry,bitBoard)){
//                score =t.getScore(entry);
//                System.out.println("Score= "+score);
//                System.out.println("in data");
//            }
//            else{
//                score = -1;
//                t.updateEntry(entry,bitBoard,(byte)score,3);
//                System.out.println("Collision: actually its not in data ");
//            }
//        }
//        else{
//            score = -1;
//            t.updateEntry(entry,bitBoard,(byte)score,3);
//            System.out.println("not in data ");
//
//
//        }
//
//        System.out.println(entry);
//        if( t.checkEntryExist(entry)){
//            if(t.ifSameBoard(entry,bitBoard)){
//                score =t.getScore(entry);
//                System.out.println("Score= "+score);
//                System.out.println("second time: in data");
//            }
//            else{
//                score = -1;
//                t.updateEntry(entry,bitBoard,(byte)score,4);
//                System.out.println("second time: Collision: actually its not in data ");
//            }
//        }
//        else{
//            score = -1;
//            t.updateEntry(entry,bitBoard,(byte)score,4);
//            System.out.println("second time: not in data ");
//
//
//        }
//        System.out.println(t.getDepth(entry));
    }



}