package polymorphs.a301.f17.cs414.thexgame;

import java.util.ArrayList;

/**
 * Created by Miles on 10/14/2017.
 */

public class User {
    private String username;
    private int knownInvites;

    public User(){} // For Firebase load

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
