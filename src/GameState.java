import java.util.ArrayList;

public class GameState {
    private int currentPlayer;
    public ArrayList availableMoves;
    private Board board;
    public GameState(Board board){
        this.board = board;

    }

}
