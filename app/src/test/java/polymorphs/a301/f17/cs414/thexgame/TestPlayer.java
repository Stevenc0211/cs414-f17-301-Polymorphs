package polymorphs.a301.f17.cs414.thexgame;

import org.junit.Test;

import polymorphs.a301.f17.cs414.thexgame.AppBackend.Color;
import polymorphs.a301.f17.cs414.thexgame.AppBackend.Player;
import polymorphs.a301.f17.cs414.thexgame.AppBackend.User;
import static org.junit.Assert.*;
import static org.junit.Assert.fail;

/**
 * Created by athai on 10/24/17.
 */

public class TestPlayer {
    @Test
    public void testInstanceCreation(){
        User u = new User("a","b","c");
        try{
            Player p = new Player(u, Color.WHITE);
        }
        catch (Exception e){
            fail("ERROR: Board failed to instantiate");
        }
    }

    @Test
    public void testInitializePieces(){
        User u = new User("a","b","c");
        Player p = new Player(u, Color.WHITE);
        assertFalse("Should be false",p.getPieces().isEmpty());
    }

    @Test
    public void testInitializePiecesSize(){
        User u = new User("a","b","c");
        Player p = new Player(u, Color.WHITE);
        assertEquals(9,p.getPieces().size());
    }
}
