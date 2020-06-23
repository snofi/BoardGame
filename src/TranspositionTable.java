import java.util.Vector;

public class TranspositionTable {

    private Vector<Entry> table;
    public TranspositionTable(int size) {
        table = new Vector<>();
        table.setSize(size);
        for (int i=0;i<size;i++){


        }
    }
    public int index(long key){
        return (int)(key%table.size());
    }
    public void put(long key, short value){
        int i = index(key);

    }


}