package polymorphs.a301.f17.cs414.thexgame.AppBackend;

import java.lang.reflect.Array;

/**
 * Created by athai on 10/18/17.
 */

public class Game {
    private User user1;
    private User user2;
    private Player p1;
    private Player p2;
    private Player currentPlayer;
    private Board board;

    public Game(User user1,User user2){
        this.user1 = user1;
        this.user2 = user2;
        p1 = new Player(user1,Color.WHITE);
        currentPlayer = p1;
        p2 = new Player(user2,Color.BLACK);
        this.board = new Board();
    }

    public User getUser1(){
        return user1;
    }

    public User getUser2(){
        return user2;
    }

    public Player getP1(){
        return p1;
    }

    public Player getP2(){
        return p2;
    }

    public Board getBoard(){
        return board;
    }

    /**
     * This is the method the UI will use to validate moves the player is attempting. This method will
     * decide if the move is valid and if so will update the game state and return true.
     * @param user - the current user making the move
     * @param fromX - the x coordinate of the moves starting tile
     * @param fromY- the y coordinate of the moves starting tile
     * @param toX- the x coordinate of the moves ending tile
     * @param toY- the y coordinate of the moves ending tile
     * @return true if the move is valid, false if not
     */
    public boolean makeMove(User user, int fromX, int fromY, int toX,int toY) {
        Player currentPlayer;
        if (user.equals(user1)) {
            currentPlayer = p1;
        } else if (user.equals(user2)) {
            currentPlayer = p2;
        } else {
            return false;
        }
        if (!this.currentPlayer.equals(currentPlayer)) return false;
        if (board.isValidMove(currentPlayer , fromX, fromY, toX, toY)) {
            // make move
            return true;
        } else {
            return false;
        }
    }
}
