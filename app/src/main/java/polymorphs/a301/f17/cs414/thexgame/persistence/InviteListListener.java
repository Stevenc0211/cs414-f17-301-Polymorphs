package polymorphs.a301.f17.cs414.thexgame.persistence;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import polymorphs.a301.f17.cs414.thexgame.Invitation;

import static android.content.ContentValues.TAG;

/**
 * Created by Miles on 10/18/2017. This is a wrapper to handle data retrieval for a users invitation list.
 * It is used in conjunction with the DBIOCore when a register method calls for InviteListListener.
 * The list of invitations may be retrived using getInviteList()
 * Synchronicity Notes:
 * 1. The invites retrieved by getInviteList() will be up to data when the call is run if it is not run in the same method as the InviteListListener was registered
 * (the method that registers the InviteListListener must finish so the InviteListListener may update)
 * 2. If the return of getInviteList() is saved to another variable that variable is NOT ensured to be up to data.
 * The recommendation is to use the data once then discard the data or refresh the data every time it is used.
 */


public class InviteListListener {
    private HashMap<String, Invitation> inviteList;

    public ArrayList<Invitation> getInviteList() {
        ArrayList<Invitation> list = new ArrayList<>();
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
            inviteList.put(s, dataSnapshot.getValue(Invitation.class));
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            inviteList.put(s, dataSnapshot.getValue(Invitation.class));
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            Invitation removedInvite = dataSnapshot.getValue(Invitation.class);
            Set<String> keys = inviteList.keySet();
            String rmKey = "";
            for (String key : keys) {
                if (inviteList.get(key).equals(removedInvite)) {
                    rmKey = key;
                }
            }
            if (rmKey != "") {
                inviteList.remove(rmKey);
            }
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            inviteList.put(dataSnapshot.getKey(), dataSnapshot.getValue(Invitation.class));
            inviteList.remove(s);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
        }
    };
}