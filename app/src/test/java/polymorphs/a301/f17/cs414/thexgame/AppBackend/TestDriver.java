package polymorphs.a301.f17.cs414.thexgame.AppBackend;

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

   // --- Testing User registration checks and createGame() --- //

    String username1 = "user1";
    String username2 = "user2";
    String username3 = "user3";
    String user1Key = "user1Key";
    String user2Key = "user2Key";

    @Test(expected = IllegalArgumentException.class)
    public void testNeitherUserRegistered() {
        Driver d = new Driver();
        d.createGame("name", "other");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOnlyUser1Registered() {
        Driver d = new Driver();
        d.usernameAdded(username1, user1Key);
        d.createGame("name", "other");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOnlyUser2Registered() {
        Driver d = new Driver();
        d.usernameAdded(username2, user2Key);
        d.createGame("name", "other");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUserChangedFailure() {
        Driver d = new Driver();
        d.usernameAdded(username1, user1Key);
        d.usernameAdded(username2, user2Key);
        d.usernameChanged(username3, user1Key);
        d.createGame("name","other");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUserRemovedFailure() {
        Driver d = new Driver();
        d.usernameAdded(username1, user1Key);
        d.usernameAdded(username2, user2Key);
        d.usernameRemoved(username1);
        d.createGame("name","other");
    }
    /*
    @Test
    public void testCreateGameSuccess() {
        Driver d = new Driver();
        d.usernameAdded(username1, user1Key);
        d.usernameAdded(username2, user2Key);
        try{
            d.createGame(new User("test1", "test1", username1), new User("test2", "test2", username2));
        } catch (IllegalArgumentException e) {
            fail("Game should be created when both users are registered");
        }
    }
    */
/*
    @Test
    public void testCreateGameSuccessWithUserChanged() {
        Driver d = new Driver();
        d.usernameAdded(username1, user1Key);
        d.usernameAdded(username2, user2Key);
        d.usernameChanged(username3, user1Key);
        try{
            d.createGame(new User("test1", "test1", username3), new User("test2", "test2", username2));
        } catch (IllegalArgumentException e) {
            fail("Game should be created when both users are registered");
        }
    }
    */
    // --- Testing makeMove permutations --- //

    Driver testDriver;
    User user1 = new User("user1Key", "test", "user1");
    User user2 = new User("user2Key", "test", "user2");

    private void setupDriver() {
        testDriver = new Driver();
        testDriver.usernameAdded(user1.getNickname(), user1.getName());
        testDriver.usernameAdded(user2.getNickname(), user2.getName());
        testDriver.createGame(user1.getNickname(),user2.getNickname());
        testDriver.setCurrentGameKey("key");
    }
    /*
    @Test
    public void testMakeValidMove() {
        setupDriver();
        int result = testDriver.makeMove(user1, 7, 3, 4, 3);
        assertTrue("first player should be able to move top center white rook up 3 tiles", result == 1);
    }

    @Test
    public void testMakeInvalidMove() {
        setupDriver();
        int result = testDriver.makeMove(user2, 7, 3, 4, 3);
        assertTrue("Black should not be able to move on whites turn", result == -1);
    }
    */
    /*
    @Test
    public void testMakeMoveCheckmate() {
        setupDriver();
        testDriver.makeMove(user2, 7, 3, 4, 3);
        testDriver.makeMove(user1,7,2,2,2);
        testDriver.makeMove(user2,4,7,11,7);
        testDriver.makeMove(user1,7,3,4,3);
        testDriver.makeMove(user2,3,7,10,7);
        testDriver.makeMove(user1,7,4,7,5);
        testDriver.makeMove(user2, 2,7,9,7);
        testDriver.makeMove(user1,7,5,7,7);
        testDriver.makeMove(user2, 9,7, 9,8);
        assertEquals("Move should result in checkmate, white 8,4 -> 3,4",0 ,testDriver.makeMove(user1, 8,4, 3,4));
    }
    */
    /*
    @Test
    public void testGameSeparationRepeatMove() {
        setupDriver();
        testDriver.createGame(user1,user2);
        testDriver.setCurrentGameKey("key");
        testDriver.makeMove(user1, 7, 3, 4, 3);
        testDriver.setCurrentGameKey("key");
        assertEquals("User should be able to make the same starting move in two different games", 1, testDriver.makeMove(user1, 7, 3, 4, 3));
    }
    */
    /*
    @Test
    public void testGameSeparationInverseColor() {
        setupDriver();
        testDriver.createGame(user2,user1);
        testDriver.setCurrentGameKey("key");
        testDriver.makeMove(user1, 7, 3, 4, 3);
        testDriver.setCurrentGameKey("key");
        assertEquals("User is black in game 2, should not be able to make move", -1, testDriver.makeMove(user1, 7, 3, 4, 3));
    }
    */
    /*@Test (expected = IllegalArgumentException.class)
    public void testGameIndexOutOfBounds() {
        setupDriver();
        testDriver.setCurrentGameKey("KDKDK");
    }*/
}
