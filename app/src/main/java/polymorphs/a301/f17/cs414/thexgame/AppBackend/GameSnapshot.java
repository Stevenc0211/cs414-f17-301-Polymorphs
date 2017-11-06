package polymorphs.a301.f17.cs414.thexgame.AppBackend;

/**
 * Created by athai on 11/3/17.
 */

public class GameSnapshot {
    private Game game;

    public GameSnapshot(Game game){
        this.game = game;
    }

    public String toString(){
        return game.toString();
    }

    public Game getGame(){
        return game;
    }
}
