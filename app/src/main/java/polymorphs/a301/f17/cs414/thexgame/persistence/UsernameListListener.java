package polymorphs.a301.f17.cs414.thexgame.persistence;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import polymorphs.a301.f17.cs414.thexgame.Invitation;

import static android.content.ContentValues.TAG;

/**
 * Created by Miles on 10/18/2017.
 * This is used by the DBIOCore to organize all observers to the Username list in the database.
 */

class UsernameListListener implements ChildEventListener {

    private UsernameListObserver observer;

    public UsernameListListener(UsernameListObserver observer) {
        this.observer = observer;
    }

    /**
     * Triggered when Firebase detects a new addition to the username list
     * @param dataSnapshot - the data for the new username
     * @param s - the key of the preceding username in the list
     */
    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        observer.usernameAdded(dataSnapshot.getValue(String.class), s);
    }

    /**
     * Triggered when Firebase detects change in one of the usernames in the list
     * @param dataSnapshot - the data for the changed username
     * @param s - the key of the preceding username in the list
     */
    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        observer.usernameChanged(dataSnapshot.getValue(String.class), s);
    }

    /**
     * Triggered when Firebase detects one of the usernames in the list is removed
     * @param dataSnapshot - the data for the removed username
     */
    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        observer.usernameRemoved(dataSnapshot.getValue(String.class));
    }

    /**
     * Not used for this Listener
     */
    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        // usernames are non ordered data so this method is not used
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
