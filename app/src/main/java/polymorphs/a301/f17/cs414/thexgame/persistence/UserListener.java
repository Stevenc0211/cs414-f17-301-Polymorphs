package polymorphs.a301.f17.cs414.thexgame.persistence;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import polymorphs.a301.f17.cs414.thexgame.AppBackend.User;

import static android.content.ContentValues.TAG;

/**
 * Created by Miles on 10/18/2017.
 * This is used by the DBIOCore to organize all observers to a User object in the database.
 */

class UserListener extends ListenerBase<UserObserver> implements ValueEventListener {

    /**
     * Called when Firebase detects a update to the data.
     * @param dataSnapshot - the data that has been updated
     */
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        User user = dataSnapshot.getValue(User.class);
        for (UserObserver observer : observers){
            observer.userUpdated(user);
        }
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