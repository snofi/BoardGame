import java.util.BitSet;
import java.util.Vector;

public class TranspositionTable {

    private long[] table;
    public TranspositionTable() {
        table = new long[(int)Math.pow(2,26)];

    }
    public boolean checkEntryExist(int entry){
        if(table[entry]==0){
            return false;
        }
        return true;
    }
    public void updateEntry(int entry, BitSet val, byte result, int depth){
        if(result ==-1){
            result = 2;
        }
        val.clear(0,26);
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
        BitSet prev = longToBitSet(p);

        current.clear(1,26);
        prev.clear(1,26);
        if(current.equals(prev)){
            return true;
        }
        return false;

    }
    public int getScore(int entry){
        long v = table[entry];
        BitSet bit = longToBitSet(v);
        BitSet score = new BitSet(2);

        score.or(bit);
        return (int)score.toLongArray()[0];
    }
    public int getDepth(int entry){
        long v = table[entry];
        BitSet bit = longToBitSet(v);

        bit.clear(0,2);
        long depth = bit.toLongArray()[0];
        depth = depth/4;
        return (int)depth;
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





}