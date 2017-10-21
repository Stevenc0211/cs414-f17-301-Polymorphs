package polymorphs.a301.f17.cs414.thexgame.persistence;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import polymorphs.a301.f17.cs414.thexgame.Invitation;

import static android.content.ContentValues.TAG;

/**
 * Created by Miles on 10/18/2017.
 * This is used by the DBIOCore to organize all observers to the Invitation list in the database.
 */


class InviteListListener extends ListenerBase<InviteListObserver> implements ChildEventListener{

    /**
     * Triggered when Firebase detects a new addition to the Invitation list
     * @param dataSnapshot - the data for the new Invitation
     * @param s - the key of the preceding invite in the list
     */
    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        for (InviteListObserver observer : observers) {
            observer.inviteAdded(dataSnapshot.getValue(Invitation.class), s);
        }
    }

    /**
     * Triggered when Firebase detects change in one of the Invitations in the list
     * @param dataSnapshot - the data for the changed Invitation
     * @param s - the key of the preceding invite in the list
     */
    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        for (InviteListObserver observer : observers) {
            observer.inviteChanged(dataSnapshot.getValue(Invitation.class), s);
        }
    }

    /**
     * Triggered when Firebase detects one of the Invitations in the list is removed
     * @param dataSnapshot - the data for the removed Invitation
     */
    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        for (InviteListObserver observer : observers) {
            observer.inviteRemoved(dataSnapshot.getValue(Invitation.class));
        }
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