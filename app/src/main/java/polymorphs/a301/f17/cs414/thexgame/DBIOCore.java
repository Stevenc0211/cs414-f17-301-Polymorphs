package polymorphs.a301.f17.cs414.thexgame;


import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;

/**
 * Created by Miles on 10/12/2017. To preform basic IO operations for the database
 */


public class DBIOCore {
    private static DatabaseReference baseReference = FirebaseDatabase.getInstance().getReference();
    private static String currentUser;

    /**
     * Sets the current user, i.e. the user who in running the app. Should only be used in the StartupScreen
     * @param userName - a username, if this is a first time user a new user object will be added to the database
     */
    public static void setCurrentUser(String userName) {
        currentUser = userName;
        DatabaseReference tmp = getUserReference();
        tmp.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User testUser = dataSnapshot.getValue(User.class);
                if (testUser == null) {
                    getUserReference().setValue(new User(currentUser));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

    /**
     * This returns a reference to the current users user object in the database.
     * This is a checked reference, i.e. the user object is ensured to be there
     * NOTE: if I understand this correctly the listener added in setCurrentUser should trigger and
     * make the user after the StartupScreen and perhaps the MainGameUI have finished their onCreate methods
     * @return
     */
    public static DatabaseReference getUserReference() {
        return baseReference.child("users").child(currentUser);
    }

    /**
     * This returns a reference to the current users invitations list (inbound invitations).
     * Since this is a list the proper way to add is as follows.
     * key = ref.push().getKey()
     * ref.child(key).setValue(new Invitation())
     * NOTE: the above code ensures that even if it is a first time user the list is created properly
     * @return - the reference to the invitations list
     */
    public static DatabaseReference getInvitationsReference() {
        return baseReference.child("invitations").child(currentUser);
    }

    /**
     * This returns a reference to any user via their username. This is an UNCHECKED reference meaning
     * the user object may not exist if the passed username is not a current user.
     * NOTE: if the user does not exist when you do 'User testUser = dataSnapshot.getValue(User.class);'
     * or similar in the onChange method of a listener at this reference testUser will be null
     * @param username - a username for an active user in the system
     * @return the reference to that user, UNCHECKED
     */
    public static DatabaseReference getUserReferenceFor(String username) {
        return baseReference.child("users").child(username);
    }

//    ValueEventListener inviteListener =  new ValueEventListener() {
//        @Override
//        public void onDataChange(DataSnapshot dataSnapshot) {
//            // Get Post object and use the values to update the UI
//            outputInvite = dataSnapshot.getValue(Invitation.class);
//        }
//
//        @Override
//        public void onCancelled(DatabaseError databaseError) {
//            // Getting Post failed, log a message
//            Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
//        }
//    };
}
