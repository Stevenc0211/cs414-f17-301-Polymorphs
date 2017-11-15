package polymorphs.a301.f17.cs414.thexgame.persistence;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import polymorphs.a301.f17.cs414.thexgame.AppBackend.GameSnapshot;

import static android.content.ContentValues.TAG;

/**
 * Created by Miles on 11/14/2017.
 */

class GameSnapshotListener implements ValueEventListener {
    private GameSnapshotObserver observer;

    public GameSnapshotListener(GameSnapshotObserver observer) {
        this.observer = observer;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        observer.snapshotUpdated(dataSnapshot.getValue(GameSnapshot.class));
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
    }
}
