package polymorphs.a301.f17.cs414.thexgame.persistence;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import polymorphs.a301.f17.cs414.thexgame.AppBackend.Invite;

import static android.content.ContentValues.TAG;

/**
 * Created by Miles on 10/18/2017.
 * This is used by the DBIOCore to organize all observers to the Invite list in the database.
 */


class InviteListListener implements ChildEventListener{

    private InviteListObserver observer;

    public InviteListListener(InviteListObserver observer) {
        this.observer = observer;
    }

    /**
     * Triggered when Firebase detects a new addition to the Invite list
     * @param dataSnapshot - the data for the new Invite
     * @param s - the key of the preceding invite in the list
     */
    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        observer.inviteAdded(dataSnapshot.getValue(Invite.class), s);
    }

    /**
     * Triggered when Firebase detects change in one of the Invitations in the list
     * @param dataSnapshot - the data for the changed Invite
     * @param s - the key of the preceding invite in the list
     */
    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        observer.inviteChanged(dataSnapshot.getValue(Invite.class), s);
    }

    /**
     * Triggered when Firebase detects one of the Invitations in the list is removed
     * @param dataSnapshot - the data for the removed Invite
     */
    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        observer.inviteRemoved(dataSnapshot.getValue(Invite.class));
    }

    /**
     * Not used for this Listener
     */
    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        // Invites are non ordered data so this method is not used
    }

    /**
     * Called when a Firebase data update is interrupted
     * @param databaseError - the error that caused the cancel
     */
    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
    }

}