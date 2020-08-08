import java.io.IOException;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Vector;

public class TranspositionTable  {

    private long[] table;
    public TranspositionTable()  {
        table = new long[(int)Math.pow(2,27)];

    }
    public boolean checkEntryExist(int entry){
        if(table[entry]!=0){
            return true;
        }
        return false;
    }
    public int checkEntryExist(int[] entry){
        for(int i=0; i<entry.length; i++){
            if(checkEntryExist(entry[i])){
                return i;
            }
        }
        return -1;
    }
    public void updateEntry(int entry, BitSet val, byte result, byte cut, int depth, int aiPlayer){

        if(result ==-1){
            result = 2;
        }
        cut = (cut==-1)? 2:cut;
        if(result>2||result<0) {
            System.out.println("update error "+result);
        }
//        System.out.println(val);
        val.clear(0,27);
//        System.out.println(val);


//        BitSet bit38 = val.get(26,64);
//        System.out.println(bit38.length());
//        System.out.println(bit38);
        byte[] r = {result};

         cut = (byte)(4*cut);
        byte[] c = {cut};
        BitSet bitResult = BitSet.valueOf(r);
        BitSet bitCut = BitSet.valueOf(c);



        long d8 = depth*8;

        BitSet bitDepth = longToBitSet(d8);

        bitDepth.or(bitResult);
        bitDepth.or(bitCut);

        val.or(bitDepth);



           long ans     =val.toLongArray()[0];
//        System.out.println(ans);
        //find out why the value of entry is so small, it should be a large number

        table[entry] = ans;
    }
    public boolean ifSameBoard(int entry, BitSet current, int c, int player){
        long p = table[entry];
//        System.out.println(p);
        BitSet prev = longToBitSet(p);

        current.clear(1,27);

        prev.clear(1,27);
        if(current.equals(prev)){
            int prev_cut = getCut(entry,player);
            if(prev_cut==3){return true;}
            if(player==1){
                if(c<=prev_cut){ return true;}
            }
            else if(player==-1){

               if(c>=prev_cut) {return true;}
            }


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
    public int getCut(int entry, int aiPlayer){
        long v = table[entry];

        BitSet bit = longToBitSet(v);

        BitSet cut = bit.get(2,4);
//        System.out.println(cut);
        BitSet temp = (BitSet) cut.clone();
        temp.clear(0,2);
        if(cut.equals(temp)){

            return 0;
        }


        int s =(int)cut.toByteArray()[0];
        if(s==2){
            s=-1;
        }


        return s;
    }
    public int getDepth(int entry){
        long v = table[entry];
        BitSet bit = longToBitSet(v);

        BitSet depth = bit.get(0,27);
        depth.clear(0,4);
        BitSet temp = (BitSet) depth.clone();
        temp.clear(0,27);
        if(depth.equals(temp)){

            return 0;
        }


        long d = depth.toLongArray()[0];
        d = d/8;

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
        Zobrist z = new Zobrist(3,4);
        TranspositionTable t = new TranspositionTable();
        int[][] b = {{-1,1,-1,1},
                {1,0,0,0},
                {0,0,1,0},
                {-1,0,1,-1}};
        BitSet[] bitBoard=z.hash(new Board(b).getBoard());
        int score=0;
        int cut = -1;
        int entry[] = z.entryCalc(bitBoard);
        t.updateEntry(entry[0],bitBoard[0], (byte) score, (byte)cut, 4,1);
        System.out.println(t.getScore(entry[0],1));
        System.out.println(t.getCut(entry[0],1));
        System.out.println(t.getDepth(entry[0]));
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