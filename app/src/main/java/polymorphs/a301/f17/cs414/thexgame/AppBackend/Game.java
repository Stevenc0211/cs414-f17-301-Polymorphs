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
    private Board board;

    public Game(User user1,User user2){
        this.user1 = user1;
        this.user2 = user2;
        p1 = new Player(user1,Color.WHITE);
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

    //public void move(board,player,piece,int toX,int toY);
}
