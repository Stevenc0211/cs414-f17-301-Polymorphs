package polymorphs.a301.f17.cs414.thexgame.persistence;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import polymorphs.a301.f17.cs414.thexgame.User;

import static android.content.ContentValues.TAG;

/**
 * Created by Miles on 10/18/2017. This is a wrapper to handle data retrieval for a user.
 * It is used in conjunction with the DBIOCore when a register method calls for UserListener.
 * The user object may be retrieved by getUser();
 * Synchronicity Notes:
 * 1. The user retrieved by getUser() will be up to data when the call is run if it is not run in the same method as the UserListener was registered
 * (the method that registers the UserListener must finish so the UserListener may update)
 * 2. If the return of getUser() is saved to another variable that variable is NOT ensured to be up to data. The recommendation is to retrive data directly from the
 * getUser call, i.e. (UserListener).getUser().getUsername()
 */

public class UserListener {
    private User user;

    /**
     * Retrieves the user being listened to.
     * @return the user object
     */
    public User getUser() {
        return user;
    }

    // Package private listener to be used by DBIOCore
    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            user = dataSnapshot.getValue(User.class);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
        }
    };
}