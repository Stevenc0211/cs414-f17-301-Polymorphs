package polymorphs.a301.f17.cs414.thexgame.AppBackend;

import java.lang.reflect.Array;

/**
 * Created by athai on 10/18/17.
 */

public class Game {
    private User player1;
    private User player2;
    private Player p1;
    private Player p2;
    private Board board;

    public Game(User player1,User player2){
        this.player1 = player1;
        this.player2 = player2;
        p1 = new Player(player1.getNickname());
        p2 = new Player(player2.getNickname());
        this.board = new Board();
    }
}
