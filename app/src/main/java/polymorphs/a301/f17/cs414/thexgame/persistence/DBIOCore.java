package polymorphs.a301.f17.cs414.thexgame.persistence;


import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import polymorphs.a301.f17.cs414.thexgame.AppBackend.GameRecord;
import polymorphs.a301.f17.cs414.thexgame.AppBackend.GameSnapshot;
import polymorphs.a301.f17.cs414.thexgame.Invitation;
import polymorphs.a301.f17.cs414.thexgame.AppBackend.User;


import static android.content.ContentValues.TAG;

/**
 * Created by Miles on 10/12/2017. To preform basic IO operations for the database
 */


public class DBIOCore {
    private static DatabaseReference baseReference = FirebaseDatabase.getInstance().getReference();
    private static String currentUser;
    private static String userEmail;
    private static String userNickname;


    /**
     * Sets up the database to begin serving data for the current user. Should be called from the
     * start screen only. If the user is new a new user object will be added to the database with a black username (nickname)
     * @param name - the google display name for the user, will be the primary key of the current user
     * @param email - the users email
     */
    public static void setCurrentUser(String name, String email) {
        currentUser = name;
        userEmail = email;
        DatabaseReference tmp = getUserReference();

        System.out.println("name of person we are adding: " + name);
        System.out.println("email of person we are adding: " + email);
        tmp.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User testUser = dataSnapshot.getValue(User.class);
                if (testUser == null) {
                    System.out.println("We are adding the user now!");
                    getUserReference().setValue(new User(currentUser, userEmail, ""));
                }
                else
                {
                    userNickname = testUser.getNickname();
                    System.out.println("Nothing was added to the database!");
                }

            }

            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

    /**
     * This should be used when the username (nickname) of the current user needs to be set.
     * @param username - the new username (nickname)
     */
    public static void setCurrentUserUsername(String username) {
        getUserReference().child("nickname").setValue(username);
        baseReference.child("usernameList").push();
        String key = baseReference.child("usernameList").push().getKey();
        baseReference.child("usernameList").child(key).setValue(username);
    }

    // TODO: we need to sort out the no check for existing nickname and implement a check for an existing user on start. Also ensure the username gets set either way - Miles <- I believe I got it, ~ Roger

    /**
     * This can be used to retrieve the current users username (nickname).
     * @return the current users username (nickname)
     */
    public static String getCurrentUserUsername()  {
        return userNickname;
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
     * This method registers the passed Observer to the current users data. The Observers update methods
     * will be called if there is any change in the user data
     * @param observer - the observer to register
     */
    public static void registerToCurrentUser(UserObserver observer) {
        getUserReference().addValueEventListener(new UserListener(observer));
    }

    /**
     * This method registers the passed Observer to the current users invitation list.
     * The Observers update methods will be called if there is any change in the list data
     * @param observer - the observer to register
     */
    public static void registerToCurrentUserInviteList(InviteListObserver observer) {
        baseReference.child("invites").child(userNickname).addChildEventListener(new InviteListListener(observer));
    }

    /**
     * This method registers the passed Observer to the master username list.
     * The Observers update methods will be called if there is any change in the list data
     * @param observer - the observer to register
     */
    public static void registerToUsernameList(UsernameListObserver observer) {
        baseReference.child("usernameList").addChildEventListener(new UsernameListListener(observer));
    }

    /**
     * This method registers the passed Observer to the master game record list.
     * The Observers update methods will be called if there is any change in the list data
     * @param observer - the observer to register
     */
    public static void registerToGameRecordList(GameRecordListObserver observer) {
        baseReference.child("gamerecordList").child(currentUser).addChildEventListener(new GameRecordListListener(observer));
    }

    /**
     * This method registers the passed Observer to the game snapshot list.
     * The Observers update methods will be called if there is any change in the list data
     * @param observer - the observer to register
     */
    public static void registerToGameSnapshotList(GameSnapshotListObserver observer){
        baseReference.child("gamesnapshotList").child(currentUser).addChildEventListener(new GameSnapshotListListener(observer));
    }

    /**
     * This takes the passed invitation and adds it to the passed users invitation list.
     * NOTE: the passed user is not ensured to exist, i.e. a random username may be passed without
     * error (please don't)
     * @param invite - an invitation object, this should be created with both a invited and inviting user
     */
    public static void sendInvite(Invitation invite) {
        String key = baseReference.child("invites").child(invite.getInvitedUser()).push().getKey();
        invite.setDbKeyKey(key);
        baseReference.child("invites").child(invite.getInvitedUser()).child(key).setValue(invite);
    }

    public static void removeInvite(Invitation invite) {
        baseReference.child("invites").child(invite.getInvitedUser()).child(invite.getDbKey()).removeValue();
    }

    public static void addGameRecord(GameRecord record){
        String key = baseReference.child("gamerecordList").child(currentUser).push().getKey();
        baseReference.child("gamerecordList").child(key).setValue(record);
    }

    public static String addGameSnapshot(GameSnapshot snapshot){
        String key = baseReference.child("gamesnapshotList").child(currentUser).push().getKey();
        baseReference.child("gamesnapshotList").child(currentUser).child(key).setValue(snapshot);
        baseReference.child("gamesnapshotList").child(snapshot.getNicknameBlack()).child(key).setValue(snapshot);

        //set key for snapshot
        snapshot.setDbKey(key);

        return key;
    }

    /**
     * ONLY for junit tests to bypass errors with firebase not initializing
     * @param database
     */
    static void setDatabase(FirebaseDatabase database) {
        baseReference = database.getReference();
    }
}
