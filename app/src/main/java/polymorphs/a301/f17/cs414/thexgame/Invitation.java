package polymorphs.a301.f17.cs414.thexgame;

import java.io.Serializable;

/**
 * Created by Miles on 10/12/2017. Stub of invitation class for testing
 *
 * The class is serializable to allow for passing of this data across activities.
 * TODO: @TEAM, I think we should remove this class and use only the one Invitation class in the AppBackend.
 */

public class Invitation implements Serializable {
    private String invitingUser;
    private String invitedUser;
    private boolean selected = false;
    private String dbKey;


    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public String getInvitingUser() {
        return invitingUser;
    }

    public void setDbKeyKey(String key) {
        dbKey = key;
    }

    public String getDbKey() {
        return dbKey;
    }

    public void setInvitedUser(String username) {
        invitedUser = username;
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
