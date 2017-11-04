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
        d.createGame(new User("test1", "test1", "not"), new User("test2", "test2", "not"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOnlyUser1Registered() {
        Driver d = new Driver();
        d.usernameAdded(username1, user1Key);
        d.createGame(new User("test1", "test1", "username1"), new User("test2", "test2", "not"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOnlyUser2Registered() {
        Driver d = new Driver();
        d.usernameAdded(username2, user2Key);
        d.createGame(new User("test1", "test1", "not"), new User("test2", "test2", username2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUserChangedFailure() {
        Driver d = new Driver();
        d.usernameAdded(username1, user1Key);
        d.usernameAdded(username2, user2Key);
        d.usernameChanged(username3, user1Key);
        d.createGame(new User("test1", "test1", username1), new User("test2", "test2", username2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUserRemovedFailure() {
        Driver d = new Driver();
        d.usernameAdded(username1, user1Key);
        d.usernameAdded(username2, user2Key);
        d.usernameRemoved(username1);
        d.createGame(new User("test1", "test1", username1), new User("test2", "test2", username2));
    }

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

    // --- Testing makeMove permutations --- //

    Driver testDriver;
    User user1 = new User("user1Key", "test", "user1");
    User user2 = new User("user2Key", "test", "user2");

    private void setupDriver() {
        testDriver = new Driver();
        testDriver.usernameAdded(user1.getNickname(), user1.getName());
        testDriver.usernameAdded(user2.getNickname(), user2.getName());
        testDriver.createGame(user1,user2);
        testDriver.setCurrentGameIndex(0);
    }
    // NOTE: user.getName() is used for the registration key
    private void addGameToDriverWithRegistration(User user1, User user2) throws IllegalArgumentException {
        testDriver.usernameAdded(user1.getNickname(), user1.getName());
        testDriver.usernameAdded(user2.getNickname(), user2.getName());
        testDriver.createGame(user1, user2);
    }

    @Test
    public void testMakeValidMove() {
        setupDriver();
        int result = testDriver.makeMove(user1, 7, 3, 4, 3);
        assertTrue("ERROR: first player should be able to move top center white rook up 3 tiles", result == 1);
    }
}
