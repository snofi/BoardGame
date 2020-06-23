public class Entry {
    private long key;
    private short value;
    public Entry(long key, short value) {
        this.key = key;

        this.value = value;
    }

    public void setKey(long key) {
        this.key = key;
    }

    public void setValue(short value) {
        this.value = value;
    }

    public long getKey() {
        return key;
    }

    public short getValue() {
        return value;
    }



}
