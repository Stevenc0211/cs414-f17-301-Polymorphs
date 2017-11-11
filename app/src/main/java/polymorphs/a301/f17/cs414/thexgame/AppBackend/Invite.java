package polymorphs.a301.f17.cs414.thexgame.AppBackend;

/**
 * Created by Miles on 10/12/2017. Stub of invitation class for testing
 *
 * The class is serializable to allow for passing of this data across activities.
 * TODO: @TEAM, I think we should remove this class and use only the one Invite class in the AppBackend.
 */

public class Invite implements Invitation {
    private String invitingUser;
    private String invitedUser;
    private String dbKey;


    public String getInvitingUser() {
        return invitingUser;
    }

    public String getDbKey() {
        return dbKey;
    }


    public void setDbKey(String key) {
        dbKey = key;
    }

    public void setInvitedUser(String username) {
        invitedUser = username;
    }

    public String getInvitedUser() {
        return invitedUser;
    }

    public Invite(String invitingUser, String invitedUser) {
        this.invitedUser = invitedUser;
        this.invitingUser = invitingUser;
    }

    /**
     * For Firebase loading ONLY. DO NOT USE!!
     */
    public Invite() {
        invitedUser = "";
        invitedUser = "";
    }

    public boolean equals(Object o) {
        if (!(o instanceof Invite)) return false;
        Invite otherInvite = (Invite)o;
        return (invitedUser.equals(otherInvite.invitedUser) && invitingUser.equals(otherInvite.invitingUser));
    }
}
