import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Zobrist {
    private String table[][];
    public Zobrist(int row, int col){
        int board_size = row*col;
        table = new String[16][2];
        for (int i=0; i<board_size; i++){
            for(int j=0; j<2; j++){
                table[i][j]  = randomBitString();
            }
        }

    }
    public String hash(Board board){

        int[] boardArr=new int[16];
        int c=0;
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                boardArr[c]=board.getBoard()[i][j];
                c++;
            }
        }
        String h="0000000000000000000000000000000000000000000000000000000000000000";
        char[] charray = h.toCharArray();
        for (int i=0;i<boardArr.length;i++){
            if(boardArr[i]!=0){
                int j=0;
                if(boardArr[i]==1){
                   j=0;
                }
                else if (boardArr[i] == -1) {
                    j=1;
                }
               for (int t=0; t<64;t++){
                   charray[t] =(char)((int)(charray[t])^(int)(table[i][j].charAt(i)) ) ;
               }
            }

        }
        return String.valueOf(charray);
    }
    public String randomBitString(){
        Random random = ThreadLocalRandom.current();
        byte[] r = new byte[8];
        random.nextBytes(r);
        String s = new String(r);
        return s;
    }
}
