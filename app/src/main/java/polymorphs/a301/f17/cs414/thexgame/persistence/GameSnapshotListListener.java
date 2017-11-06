package polymorphs.a301.f17.cs414.thexgame.persistence;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import polymorphs.a301.f17.cs414.thexgame.AppBackend.GameSnapshot;

import static android.content.ContentValues.TAG;

/**
 * Created by athai on 11/3/17.
 */

class GameSnapshotListListener implements ChildEventListener {
    private GameSnapshotListObserver observer;

    public GameSnapshotListListener(GameSnapshotListObserver observer){
        this.observer = observer;
    }
    /**
     * Triggered when Firebase detects a new addition to the game snapshot list
     * @param dataSnapshot - the data for the new Game Snapshot
     * @param s - the key of the preceding snapshot in the list
     */
    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        observer.snapshotAdded(dataSnapshot.getValue(GameSnapshot.class), s);
    }

    /**
     * Triggered when Firebase detects change in one of the game snapshot in the list
     * @param dataSnapshot - the data for the changed Game Snapshot
     * @param s - the key of the preceding snapshot in the list
     */
    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        observer.snapshotChanged(dataSnapshot.getValue(GameSnapshot.class), s);
    }

    /**
     * Triggered when Firebase detects one of the Game Snapshot in the list is removed
     * @param dataSnapshot - the data for the removed Game Snapshot
     */
    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        observer.snapshotRemoved(dataSnapshot.getValue(GameSnapshot.class));
    }

    /**
     * Not used for this Listener
     */
    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        // Game Snapshot are non ordered data so this method is not used
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
