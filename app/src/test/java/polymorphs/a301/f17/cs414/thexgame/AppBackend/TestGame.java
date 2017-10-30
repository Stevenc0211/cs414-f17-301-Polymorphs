package polymorphs.a301.f17.cs414.thexgame.AppBackend;

import org.junit.Test;
import static org.junit.Assert.*;

import polymorphs.a301.f17.cs414.thexgame.AppBackend.Color;
import polymorphs.a301.f17.cs414.thexgame.AppBackend.Game;
import polymorphs.a301.f17.cs414.thexgame.AppBackend.User;
import polymorphs.a301.f17.cs414.thexgame.AppBackend.Player;
/**
 * Created by athai on 10/23/17.
 */

public class TestGame {
    @Test
    public void testInstanceCreation(){
        User u = new User("a","b","c");
        User u1 = new User("e","f","g");
        try{
            Game game = new Game(u,u1);
        }
        catch (Exception e){
            fail("ERROR: Game failed to instantiate");
        }
    }

    @Test
    public void testGetUser1(){
        User user1 = new User("a","email","nickname");
        User user2 = new User("b","test","ddd");
        Game game = new Game(user1,user2);
        assertEquals(user1,game.getUser1());
    }

//    @Test
//    public void testGetP1(){
//        User user1 = new User("a","email","nickname");
//        User user2 = new User("b","test","ddd");
//        Game game = new Game(user1,user2);
//        Player p1 = new Player(user1, Color.WHITE);
//        assertEquals(p1,game.getP1());
//    }


}
