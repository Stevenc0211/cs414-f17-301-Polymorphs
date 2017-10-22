package polymorphs.a301.f17.cs414.thexgame.persistence;

/**
 * Created by Miles on 10/20/2017.
 * This is the interface to implement if the implementing class needs to listen to username list in the database.
 * To be used in conjunction with DBIOCore.registerToUsernameList()
 */

public interface UsernameListObserver {
    /**
    * Triggered when a new username is added to the list
    * @param addedUsername - the new username
    * @param precedingUsernameKey - the database key of the preceding element in the username list
    */
    void usernameAdded(String addedUsername, String precedingUsernameKey);

    /**
     * Triggered when a username is changed in the database
     * @param changedUsername - the changed username
     * @param precedingUsernameKey - the database key of the preceding element in the username list
     */
    void usernameChanged(String changedUsername, String precedingUsernameKey);

    /**
     * Triggered when a username is removed from the database
     * @param removedUsername - the username that was removed
     */
    void usernameRemoved(String removedUsername);

}
