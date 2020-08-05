
import java.io.IOException;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Random;


public class Zobrist {
    private BitSet table[][][];
    private static int seed = 42;
    public Zobrist(int row, int col)throws IOException {
//        int board_size = row*col;
        Random r = new Random(seed);

        table = new BitSet[row][col][2];
        for (int i=0; i<row; i++){
            for(int j=0;j<col; j++){
                for(int k=0; k<2; k++){
                    table[i][j][k]  = new BitSet(64);
                    for(int l=0; l<64;l++){
                        if(r.nextBoolean()){
                            table[i][j][k].set(l);
                        }
                    }

//                System.out.println(table[i][j]);
                }
            }
        }

    }
//    public  BitSet hash(int[][] board) {
//
//        BitSet h = new BitSet(64);
//
////        System.out.println(h);
//
//        for (int i = 0; i < board.length; i++) {
//            for (int j = 0; j < board[0].length; j++) {
//                int k = -1;
//                if (board[i][j] != 0) {
//                    if (board[i][j] == 1) {
//                        k = 0;
//                    } else if (board[i][j] == -1) {
//                        k = 1;
//                    }
//                    h.xor(table[i][j][k]);
//                }
//
//            }
////            System.out.println(table[i][j].length());
//        }
//        return h;
//    }
    public  BitSet[] hash(int[][] board){

//        int[] boardArr=new int[(board.length)*(board[0].length)];
//        int c=0;
//        for(int i=0;i<board.length;i++){
//            for(int j=0;j<board[0].length;j++){
//                boardArr[c]=board[i][j];
//                c++;
//            }
//        }
        int row = board.length;
        int col = board[0].length;
        BitSet h[] = new BitSet[4];
        for(int i=0;i<4; i++){
            h[i]= new BitSet(64);
            h[i].clear();
        }

//        System.out.println(h);

        for (int i=0;i<board.length;i++){
            for(int j=0; j<board[0].length; j++){
                int k=-1;
                if(board[i][j]!=0){
                    if(board[i][j]==1){
                        k=0;
                    }
                    else if (board[i][j] == -1) {
                        k=1;
                    }

                    h[0].xor(table[i][j][k]);
//                    h[1].xor(table[i][j][k]);
//                    h[2].xor(table[i][j][k]);
//                    h[3].xor(table[i][j][k]);
                    h[1].xor(table[i][col-1-j][k]); //flip to the right
                    h[2].xor(table[row-1-i][j][k]); // flip down
                    h[3].xor(table[row-1-i][col-1-j][k]); //rotate 180 degree

                }

            }
//            System.out.println(table[i][j].length());

        }


//        for(int i=0; i<26;i++){
//            dfd.set(i);
//        }

        return h;
    }
    public static int entryCalc(BitSet b){
        BitSet temp = new BitSet(26);
        BitSet clo = (BitSet) temp.clone();
        temp.set(0,26);

        temp.and(b);
        //         b.clear(26,b.size());
        if(temp.equals(clo)){
            return 0;
        }
        long result=0;
        try {
           result = temp.toLongArray()[0];

        }
        catch(ArrayIndexOutOfBoundsException exception) {
            System.out.println("exception: "+exception);
            System.out.println(result);
        }

       return (int)result;
    }
    public static int[] entryCalc(BitSet[] b) {
        int[] bSet = new int[b.length];
        for(int i=0; i<b.length; i++){
            bSet[i] = entryCalc(b[i]);
        }
        return bSet;
    }
//    public String randomBitString(){
//        Random random = ThreadLocalRandom.current();
//        byte[] r = new byte[8];
//        random.nextBytes(r);
//        String s = new String(r);
//        return s;
//    }
    public static int minEntryIndex(int[] entry){
        int minIndex=-1;
        int minVal = Integer.MAX_VALUE;
        for(int i=0; i<entry.length; i++){
            if(entry[i]<minVal){
                minIndex=i;
                minVal = entry[i];
            }
        }
        return minIndex;
    }


    public static void main(String[] args)throws IOException{
//        Zobrist z = new Zobrist(4,4);
//        int[][] b = {{-1,1,0,1},
//                {1,0,0,0},
//                {0,1,1,0},
//                {-1,0,1,-1}};
        int[][] b = {{-1,1,0},
                {1,0,0},
                {0,1,1}};
//        z.hash(new Board(b).getBoard());
//        for (int i=0;i<b.length;i++){
//            for(int j=0; j<b[0].length; j++){
//                System.out.print(b[b.length-1-i][j]+" ");
//            }
//            System.out.println();
//        }
        int[][] a = new int[3][3];
        for (int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                a[i][j] = b[3-1-i][j];
            }
        }
        Board aboard = new Board(a);
        aboard.printBoard();



    }
}
