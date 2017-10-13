package polymorphs.a301.f17.cs414.thexgame;


import android.util.Log;

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

    Invitation outputInvite;

    public DBIOCore() {

    }

    public void runExperimentSetWatch() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("testInvite1");

        ValueEventListener inviteListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                outputInvite = dataSnapshot.getValue(Invitation.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };

        myRef.addValueEventListener(inviteListener);
    }

    public void runExperimentSendData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("testInvite1");
        Invitation invite = new Invitation("Miles", "Andy");
        myRef.setValue(invite);
    }

    public Invitation runExperimentGetData() {
        return outputInvite;
    }
}
