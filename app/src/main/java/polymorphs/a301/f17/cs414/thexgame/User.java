package polymorphs.a301.f17.cs414.thexgame;

import java.util.ArrayList;

/**
 * Created by Miles on 10/14/2017.
 * TODO: @TEAM, I think we should remove this class and use only the one User class in the AppBackend.
 */

public class User {
    private String username;
    private int knownInvites;

    /**
     * For Firebase loading ONLY. DO NOT USE!!
     */
    public User(){}

    /**
     * Primary constructor for User
     * @param username - the users name
     */
    public User(String username) {
        this.username = username;
        knownInvites = 0;
    }

    public String getUsername() {
        return username;
    }

    public int getKnownInvites() {
        return knownInvites;
    }

}
