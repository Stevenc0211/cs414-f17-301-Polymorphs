package polymorphs.a301.f17.cs414.thexgame;

import java.io.Serializable;

/**
 * Created by Miles on 10/12/2017. Stub of invitation class for testing
 *
 * The class is serializable to allow for passing of this data across activities.
 */

public class Invitation implements Serializable {
    private String invitingUser;
    private String invitedUser;

    public String getInvitingUser() {
        return invitingUser;
    }

    public String getInvitedUser() {
        return invitedUser;
    }

    public Invitation(String invitingUser, String invitedUser) {
        this.invitedUser = invitedUser;
        this.invitingUser = invitingUser;
    }

    /**
     * For Firebase loading ONLY. DO NOT USE!!
     */
    public Invitation() {
        invitedUser = "";
        invitedUser = "";
    }

    public boolean equals(Object o) {
        if (!(o instanceof Invitation)) return false;
        Invitation otherInvite = (Invitation)o;
        return (invitedUser.equals(otherInvite.invitedUser) && invitingUser.equals(otherInvite.invitingUser));
    }

}
