package polymorphs.a301.f17.cs414.thexgame;

import org.junit.Test;
import polymorphs.a301.f17.cs414.thexgame.AppBackend.*;
import polymorphs.a301.f17.cs414.thexgame.AppBackend.User;
import static org.junit.Assert.*;

/**
 * Created by athai on 10/23/17.
 */

public class TestDriver {
    @Test
    public void testInstanceCreation(){
        try{
            Driver d = new Driver();
        }
        catch (Exception e){
            fail("ERROR: Driver failed to instantiate");
        }
    }

    @Test
    public void testRegisterUser(){
        Driver d = new Driver();
        d.registerUser("Andy","andy@gmail.com","andy");
        assertFalse("Should be false",d.getUsers().isEmpty());
    }

    @Test
    public void testIsRegistered(){
        Driver d = new Driver();
        User u = new User("andy","andy@gmail.com","andy");
        d.getUsers().add(u);
        assertTrue("Should be true",d.isRegistered(u));
    }

    @Test
    public void testCreateGame(){
        Driver d = new Driver();
        User user1 = new User("andy","andy@gmail.com","andy");
        User user2 = new User("Miles","Miles@gmail.com","miles");
        d.getUsers().add(user1);
        d.getUsers().add(user2);
        d.createGame(user1,user2);
        assertFalse("Should be false",d.getGames().isEmpty());
    }
}
