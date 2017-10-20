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

    /*
        todo: @Miles I added this method to help me grab a list of registered users, if you do not like please let me know and I can remove it ~Roger.

        Retrieves the list of usernames from within the database.
     */
    public static ArrayList<String> getUsernames()
    {
        final ArrayList<String> usernames = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot userSnapshot: dataSnapshot.getChildren())
                {
                    // TODO: @Miles, I'm struggling to read the data, I get an error when I try to create game and grab the list of users.
                    // **WEIRD** another weird thing is when I print out str right below, it shows for whatever reason, my email address. Not my name or my random nickname.

                    String str = (String) userSnapshot.getValue(String.class).toString();
                    System.out.println("The string equivalent of what we are trying to grab = " + str);
                    User user = userSnapshot.getValue(User.class);
                    if(user != null)
                    {
                        usernames.add(user.getNickname()); // add this to the list of usernames we want to return.
                    }
                    else
                    {
                        System.out.println("found null child!!");
                    }
                }

                /*
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator(); // iterator to iterate through list of users.
                while(iterator.hasNext())
                {
                    DataSnapshot snapshot = iterator.next();
                    User user = snapshot.child("users").getValue(User.class);
                    if(user != null)
                    {
                        usernames.add(user.getNickname()); // add this to the list of usernames we want to return.
                    }
                    else
                    {
                        System.out.println("found null child!!");
                    }

                }
                */

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });

        return usernames;
    }

    /** TODO: @Miles I added this because I think we should be making use of objects in our database.
     * Adds a user to the database. This is only called once (besides for testing purposes) in MainGameUI *AFTER* a user selects their username (nickname) for the very first (and only) time.
     * @param userToAdd - this is a user that we are adding to the database. This only happens once they have logged in using oAuth and have set a nickname.
     *                    This is started in StartupScreen and finishes in MainGameUI.
     */
    public static void addUser(final User userToAdd) {
        //DatabaseReference tmp = getUserReference();
       //final DatabaseReference usersRef = baseReference.getRef().child("users");
        final DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");

        baseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // TODO: @Miles I'm trying to add a user to the database here, but I want to add the User object and I guess I don't know how to add User to the child "users" how do I do that?

                //User testUser = dataSnapshot.child("users").getValue(User.class);
                //Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();

                System.out.println("user we want to have has name: " + userToAdd.getName() + " email: " + userToAdd.getEmail() + " nickname: " + userToAdd.getNickname());
                //baseReference.child("users").child(userToAdd.getNickname()).setValue(userToAdd); // add the user to the database of user objects.
                //baseReference.child("usernameList").child(userToAdd.getNickname()).setValue(userToAdd); // add the user's nickname to the list of nicknames in the database.

                usersRef.child(userToAdd.getNickname()).setValue(userToAdd); // add the user to the database of user objects.
                usersRef.child(userToAdd.getNickname()).setValue(userToAdd); // add the user's nickname to the list of nicknames in the database.
                //usersRef.setValue(userToAdd);

                /*
                if (testUser == null) // if user is not in the database add them.
                {
                    baseReference.child("users").setValue(userToAdd); // add the user to the database of user objects.
                    baseReference.child("usernameList").child(userToAdd.getNickname()).setValue(userToAdd); // add the user's nickname to the list of nicknames in the database.
                    thisUser = testUser; // set the actual object for us to use.
                }
                */

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });
    }


    /** TODO: @Miles I removed this because I think that we should be making use of the objects themselves in the database.
     * Sets the current user, i.e. the user who in running the app. Should only be used in the StartupScreen
     * @param userName - a username, if this is a first time user a new user object will be added to the database

    public static void setCurrentUser(String userName) {
        currentUser = userName;
        DatabaseReference tmp = getUserReference();
        tmp.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User testUser = dataSnapshot.getValue(User.class);
                if (testUser == null) {
                    getUserReference().setValue(new User(currentUser));
                    String key = baseReference.child("usernameList").push().getKey();
                    baseReference.child("usernameList").child(key).setValue(currentUser);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        })
    */

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
