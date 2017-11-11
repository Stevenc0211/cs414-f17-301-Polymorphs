package polymorphs.a301.f17.cs414.thexgame.AppBackend;

/**
 * Created by Miles on 11/10/2017.
 */

public interface Invitation {
    String getInvitingUser();
    void setInvitedUser(String username);
    String getInvitedUser();
    String getDbKey();
    void setDbKey(String key);
}
