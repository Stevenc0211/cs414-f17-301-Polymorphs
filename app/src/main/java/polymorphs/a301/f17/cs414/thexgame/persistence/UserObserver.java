package polymorphs.a301.f17.cs414.thexgame.persistence;

import polymorphs.a301.f17.cs414.thexgame.AppBackend.User;

/**
 * Created by Miles on 10/20/2017.
 * This is the interface to implement if the implementing class needs to listen to a User object in the database.
 * To be used in conjunction with DBIOCore.registerToCurrentUser()
 */

public interface UserObserver {
    /**
     * Called when the observed user is updated in the database
     * @param u - a user object containing up to date data for the observed user
     */
    void userUpdated(User u);
}
