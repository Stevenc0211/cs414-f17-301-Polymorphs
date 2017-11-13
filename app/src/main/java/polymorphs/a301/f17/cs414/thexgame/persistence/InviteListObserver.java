package polymorphs.a301.f17.cs414.thexgame.persistence;

import polymorphs.a301.f17.cs414.thexgame.AppBackend.Invite;

/**
 * Created by Miles on 10/20/2017.
 * This is the interface to implement if the implementing class needs to listen to invitation list in the database.
 * To be used in conjunction with DBIOCore.registerToCurrentUserInviteList()
 */

public interface InviteListObserver {
    /**
     * Triggered when a new invite is added to the list
     * @param addedInvite - the new invitation
     * @param precedingInviteKey - the database key of the preceding element in the invite list
     */
    void inviteAdded(Invite addedInvite, String precedingInviteKey);

    /**
     * Triggered when a invite is changed in the database
     * @param changedInvite - the changed invitation with updated data
     * @param precedingInviteKey - the database key of the preceding element in the invite list
     */
    void inviteChanged(Invite changedInvite, String precedingInviteKey);

    /**
     * Triggered when a invite is removed from the database
     * @param removedInvite - the invitation that was removed
     */
    void inviteRemoved(Invite removedInvite);
}
