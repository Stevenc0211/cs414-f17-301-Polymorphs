package polymorphs.a301.f17.cs414.thexgame.persistence;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import static android.content.ContentValues.TAG;

/**
 * Created by Miles on 10/18/2017. This is a wrapper to handle data retrieval for a list of usernames.
 * It is used in conjunction with the DBIOCore when a register method calls for UsernameListListener.
 * The list of invitations may be retrieved using getInviteList()
 * Synchronicity Notes:
 * 1. The invites retrieved by getUsernameList() will be up to data when the call is run if it is not run in the same method as the UsernameListListener was registered
 * (the method that registers the UsernameListListener must finish so the UsernameListListener may update)
 * 2. If the return of getUsernameList() is saved to another variable that variable is NOT ensured to be up to data.
 * The recommendation is to use the data once then discard the data or refresh the data every time it is used.
 */

public class UsernameListListener {
    private HashMap<String, String> inviteList;

    public ArrayList<String> getUsernameList() {
        ArrayList<String> list = new ArrayList<>();
        Set<String> keys = inviteList.keySet();
        for (String key : keys) {
            list.add(inviteList.get(key));
        }
        return list;
    }

    // Package private listener to be used by DBIOCore
    ChildEventListener listener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            inviteList.put(s, dataSnapshot.getValue(String.class));
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            inviteList.put(s, dataSnapshot.getValue(String.class));
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            String removedUsername = dataSnapshot.getValue(String.class);
            Set<String> keys = inviteList.keySet();
            String rmKey = "";
            for (String key : keys) {
                if (inviteList.get(key).equals(removedUsername)) {
                    rmKey = key;
                }
            }
            if (rmKey != "") {
                inviteList.remove(rmKey);
            }
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            inviteList.put(dataSnapshot.getKey(), dataSnapshot.getValue(String.class));
            inviteList.remove(s);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
        }
    };
}
