
import java.io.IOException;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Random;


public class Zobrist {
    private BitSet table[][];
    public Zobrist(int row, int col)throws IOException {
        int board_size = row*col;
        Random r = new Random();

        table = new BitSet[16][2];
        for (int i=0; i<board_size; i++){
            for(int j=0; j<2; j++){


                table[i][j]  = new BitSet(64);
                for(int k=0; k<64;k++){
                    if(r.nextBoolean()){
                        table[i][j].set(k);
                    }
                }

//                System.out.println(table[i][j]);
            }
        }

    }
    public BitSet hash(Board board){

        int[] boardArr=new int[16];
        int c=0;
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                boardArr[c]=board.getBoard()[i][j];
                c++;
            }
        }
        BitSet h = new BitSet(64);
        System.out.println(h);

        for (int i=0;i<boardArr.length;i++){
            int j=-1;
            if(boardArr[i]!=0){

                if(boardArr[i]==1){
                    j=0;
                }
                else if (boardArr[i] == -1) {
                    j=1;
                }
                h.xor(table[i][j]);
            }

//            System.out.println(table[i][j].length());

        }

        return h;
    }
    public static int indexCalc(int result, int depth){

        String biResult = String.format("%2s", Integer.toBinaryString(result)).replace(' ', '0');
        System.out.println(biResult);
        String biDepth = String.format("%8s", Integer.toBinaryString(depth)).replace(' ', '0');
        System.out.println(biDepth);
        String concat = biDepth+biResult;
        return Integer.parseInt(concat,2);
    }
//    public String randomBitString(){
//        Random random = ThreadLocalRandom.current();
//        byte[] r = new byte[8];
//        random.nextBytes(r);
//        String s = new String(r);
//        return s;
//    }



    public static void main(String[] args)throws IOException{
        Zobrist z = new Zobrist(4,4);
        int[][] b = {{-1,1,-1,1},
                {1,0,0,0},
                {0,0,1,0},
                {-1,0,1,-1}};
        z.hash(new Board(b));
//        System.out.println("011110001100111011011010001100100110010101110111101011".length());
//        long f =Long.parseLong("011110001100111011011010001100100110010101110111101011", 2);
//        System.out.println(f);
//        int r = indexCalc(2,16);
//        System.out.println(r);


    }
}
