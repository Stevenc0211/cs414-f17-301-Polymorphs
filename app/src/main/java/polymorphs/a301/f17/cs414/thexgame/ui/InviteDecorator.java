package polymorphs.a301.f17.cs414.thexgame.ui;

import polymorphs.a301.f17.cs414.thexgame.AppBackend.Invitation;
import polymorphs.a301.f17.cs414.thexgame.AppBackend.Invite;

/**
 * Created by Miles on 11/10/2017.
 */

public class InviteDecorator implements Invitation {
    private Invitation invite;
    private boolean isSelected;

    public InviteDecorator(Invitation invite) {
        this.invite = invite;
        isSelected = false;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public Invitation unDecorate() {
        return invite;
    }

    @Override
    public String getInvitingUser() {
        return invite.getInvitingUser();
    }

    @Override
    public void setInvitedUser(String username) {
        invite.setInvitedUser(username);
    }

    @Override
    public String getInvitedUser() {
        return invite.getInvitedUser();
    }

    @Override
    public String getDbKey() {
        return invite.getDbKey();
    }

    @Override
    public void setDbKey(String key) {
        invite.setDbKey(key);
    }
}
