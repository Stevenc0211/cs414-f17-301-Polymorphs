package polymorphs.a301.f17.cs414.thexgame.persistence;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import polymorphs.a301.f17.cs414.thexgame.AppBackend.ProfileSnapshot;

import static android.content.ContentValues.TAG;

/**
 * Created by Andy on 12/2/2017.
 */

class ProfileSnapshotListener implements ValueEventListener {
    private ProfileSnapshotObserver observer;

    public ProfileSnapshotListener(ProfileSnapshotObserver observer) {
        this.observer = observer;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        observer.snapshotUpdated(dataSnapshot.getValue(ProfileSnapshot.class));
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
    }
}
