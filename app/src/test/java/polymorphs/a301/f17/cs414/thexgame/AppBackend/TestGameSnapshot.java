package polymorphs.a301.f17.cs414.thexgame.AppBackend;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by Andy on 12/5/2017.
 */

public class TestGameSnapshot {
    @Test
    public void testInstanceCreation(){
        Game game = new Game("Andy","John");
        try{
            GameSnapshot snapshot = new GameSnapshot(game);
        }
        catch (Exception e){
            fail("ERROR: Game snapshot failed to instantiate");
        }
    }
    @Test
    public void testUserNameEquals(){
        Game game = new Game("Andy","John");
        GameSnapshot snapshot = new GameSnapshot(game);
        assertEquals(game.getP1Nickname(),snapshot.getNicknameWhite());
    }
    @Test
    public void testGameToStringEquals(){
        Game game = new Game("Andy","John");
        GameSnapshot snapshot = new GameSnapshot(game);
        assertEquals(game.toString(),snapshot.getGameString());
    }
    @Test
    public void testGameStateEquals(){
        Game game = new Game("Andy","John");
        GameSnapshot snapshot = new GameSnapshot(game);
        assertEquals(game.getGameState(),snapshot.getGameState());
    }
}
