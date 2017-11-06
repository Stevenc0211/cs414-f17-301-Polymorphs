package polymorphs.a301.f17.cs414.thexgame.persistence;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import polymorphs.a301.f17.cs414.thexgame.AppBackend.GameRecord;

import static android.content.ContentValues.TAG;

/**
 * Created by athai on 11/3/17.
 */

class GameRecordListListener implements ChildEventListener {
    private GameRecordListObserver observer;

    public GameRecordListListener(GameRecordListObserver observer){
        this.observer = observer;
    }
    /**
     * Triggered when Firebase detects a new addition to the game record list
     * @param dataSnapshot - the data for the new Game Record
     * @param s - the key of the preceding record in the list
     */
    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        observer.recordAdded(dataSnapshot.getValue(GameRecord.class), s);
    }

    /**
     * Triggered when Firebase detects change in one of the records in the list
     * @param dataSnapshot - the data for the changed Game Record
     * @param s - the key of the preceding record in the list
     */
    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        observer.recordChanged(dataSnapshot.getValue(GameRecord.class), s);
    }

    /**
     * Triggered when Firebase detects one of the Game Record in the list is removed
     * @param dataSnapshot - the data for the removed Game Record
     */
    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        observer.recordRemoved(dataSnapshot.getValue(GameRecord.class));
    }

    /**
     * Not used for this Listener
     */
    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        // Game record are non ordered data so this method is not used
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
