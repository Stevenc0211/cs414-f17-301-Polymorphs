package polymorphs.a301.f17.cs414.thexgame.persistence;


import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import polymorphs.a301.f17.cs414.thexgame.Invitation;
import polymorphs.a301.f17.cs414.thexgame.AppBackend.User;

import static android.content.ContentValues.TAG;

/**
 * Created by Miles on 10/12/2017. To preform basic IO operations for the database
 */


public class DBIOCore {
    private static DatabaseReference baseReference = FirebaseDatabase.getInstance().getReference();
    private static String currentUser;
    private static User thisUser; // this is exactly the same idea as the above currentUser, but with the User Object.




    /** TODO: @Miles I removed this because I think that we should be making use of the objects themselves in the database.
     * Sets the current user, i.e. the user who in running the app. Should only be used in the StartupScreen
     * @param userName - a username, if this is a first time user a new user object will be added to the database
    */
    public static void setCurrentUser(final String name, final String email, final String userName) {
        currentUser = userName;
        DatabaseReference tmp = getUserReference();

        System.out.println("name of person we are adding: " + name);
        System.out.println("email of person we are adding: " + email);
        System.out.println("username of person we are adding: " + userName);
        tmp.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User testUser = dataSnapshot.getValue(User.class);
                if (testUser == null) {
                    System.out.println("We are adding the user now!");
                    getUserReference().setValue(new User(name, email, currentUser));
                    String key = baseReference.child("usernameList").push().getKey();
                    baseReference.child("usernameList").child(key).setValue(currentUser);
                }
                else
                {
                    System.out.println("Nothing was added to the database!");
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
     * @return DatabaseReference - the reference to the current users data
     */
    private static DatabaseReference getUserReference() {
        return baseReference.child("users").child(currentUser);
    }

    /**
     * This method registers the passed UserListener to the current users data. The UserListener will be kept
     * up to data with any changes to the current users data
     * @param listener - the listener to register
     */
    public static void registerToCurrentUser(UserListener listener) {
        getUserReference().addValueEventListener(listener.listener);
    }

    /**
     * This method registers the passed InviteListListener to the current users invitation list.
     * The InviteListListener will be kept up to data with any changes to the current users invitation list
     * @param listener - the listener to register
     */
    public static void registerToCurrentUserInviteList(InviteListListener listener) {
        baseReference.child("invites").child(currentUser).addChildEventListener(listener.listener);
    }

    /**
     * This takes the passed invitation and adds it to the passed users invitation list.
     * NOTE: the passed user is not ensured to exist, i.e. a random username may be passed without
     * error (please don't)
     * @param invite - an invitation object, this should be created with both a invited and inviting user
     */
    public static void sendInvite(Invitation invite) {
        String key = baseReference.child("invites").child(invite.getInvitedUser()).push().getKey();
        baseReference.child("invites").child(invite.getInvitedUser()).child(key).setValue(invite);
    }

    /**
     * This method registers the passed UsernameListListener to the master list of usernames.
     * The UsernameListListener will be kept up to data with any changes master list of usernames.
     * @param listener - the listener to register
     */
    public static void registerToUsernameList(UsernameListListener listener) {
        baseReference.child("usernameList").addChildEventListener(listener.listener);
    }
}
