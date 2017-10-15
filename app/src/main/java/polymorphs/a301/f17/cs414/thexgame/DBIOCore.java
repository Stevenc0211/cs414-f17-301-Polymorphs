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
    private static Boolean newUser = false;

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


    public static DatabaseReference getUserReference() {
        return baseReference.child("users").child(currentUser);
    }

    public static DatabaseReference getInvitationsReference() {
        return baseReference.child("invitations").child(currentUser);
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
