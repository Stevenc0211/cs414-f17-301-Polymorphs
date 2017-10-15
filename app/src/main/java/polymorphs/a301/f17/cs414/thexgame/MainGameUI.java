package polymorphs.a301.f17.cs414.thexgame;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

import static android.content.ContentValues.TAG;

/**
 * Created by Roger Hannagan on 9/19/17.
 *
 * This class is responsible for the main game UI. This is what we are going to update and mess with the most until we have a nice UI to work with. Updates will be applied here first.
 */

public class MainGameUI extends Activity {

    private ListView history; // holds the history of the game for us to use.
    private HistoryListAdapter histAdapter; // list adapter that shows the history of the apps themselves.
    private ArrayList<String> gameHistory = new ArrayList<>(); // todo: pull from the database and get the user's game history!
    private ArrayList<String> currentGames = new ArrayList<>(); //todo: this arraylist should be a list of likely Game objects, but for now it's just a string. Also new games are added to this ArrayList!
    private ArrayList<GridView> games = new ArrayList<>(); // todo: this will be updated through our database, this holds all of the games that we are working with. Right now I will just have test code!
    private InGameUI inGameUI = new InGameUI(); // this is the copy of the in game ui that we will be able to control from outside the class if we really need to!
    private NotificationsUI notificationsUI = new NotificationsUI(); // a copy of the notifications UI that should be built for the user.

    private User currentUser;
    private ValueEventListener userListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            currentUser = dataSnapshot.getValue(User.class);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
        }
    };

    private ArrayList<Invitation> invitations = new ArrayList<>();
    private ChildEventListener inviteListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            invitations.add(dataSnapshot.getValue(Invitation.class));
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            int stall = 1; // Temporary until I can figure out what to do here
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            int stall = 1; // Temporary until I can figure out what to do here
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            int stall = 1; // Temporary until I can figure out what to do here
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            int stall = 1; // Temporary until I can figure out what to do here
        }
    };

    // this method is in charge of starting the fragment for the user to be in charge of setting everything up correctly!!!
    protected void createInGameUIFragment()
    {
        Bundle fragmentArgs = new Bundle(); // the Bundle here allows us to send arguments to our fragment!
        // we should pull items from the data base including all of the users current games.
        // we should then send the information to the games here by attaching the games to the user which is pretty important!

        currentGames.add("Game " + currentGames.size() + 1); // creates a new game with the name i.e. Game 2 (where the 2 will increase each time!)
        fragmentArgs.putStringArrayList("currentGames", currentGames); // send in the list of current games under the name of "currentGames"

        inGameUI.setArguments(fragmentArgs); // set the arguments of the fragment.

        RelativeLayout homescreenLayout = (RelativeLayout) findViewById(R.id.homescreenLayout); // get the relative layout of the homescreen.
        homescreenLayout.removeAllViews();
        homescreenLayout.setBackground(null); // this should remove all views from the main view to allow us to show the fragment properly.

        FragmentTransaction transaction = getFragmentManager().beginTransaction(); // get the Fragment transaction to allow us to display the fragment properly
        transaction.replace(R.id.homescreenLayout, inGameUI); // replace the current fragment with our games
        transaction.commit(); // commit the fragment to be loaded.
    }

    // This method has code very similar to createInGameUIFragment, this one is not creating a new game however, this will simply pull from the database and show the current games of the user.
    protected void openCurrentGamesFragment()
    {
        // Highjacked by Miles for testing
        int stall = 1;

//        Bundle fragmentArgs = new Bundle(); // the Bundle here allows us to send arguments to our fragment!
//        // we should pull items from the data base including all of the users current games.
//        // we should then send the information to the games here by attaching the games to the user which is pretty important!
//
//        // TODO: we should pull from the database again here and update the arraylist currentGames so we are making sure that the games we are updating is accurate. Very important!
//
//        fragmentArgs.putStringArrayList("currentGames", currentGames); // send in the list of current games under the name of "currentGames"
//        inGameUI.setArguments(fragmentArgs); // set the arguments of the fragment.
//
//        RelativeLayout homescreenLayout = (RelativeLayout) findViewById(R.id.homescreenLayout); // get the relative layout of the homescreen.
//        homescreenLayout.removeAllViews();
//        homescreenLayout.setBackground(null); // this should remove all views from the main view to allow us to show the fragment properly.
//
//        FragmentTransaction transaction = getFragmentManager().beginTransaction(); // get the Fragment transaction to allow us to display the fragment properly
//        transaction.replace(R.id.homescreenLayout, inGameUI); // replace the current fragment with our games
//        transaction.commit(); // commit the fragment to be loaded.
    }

    // This method opens up the notifications fragment
    protected void openNotificationsFragment()
    {
        // Highjacked by Miles for testing
        String key = DBIOCore.getInvitationsReference().push().getKey();
        DBIOCore.getInvitationsReference().child(key).setValue(new Invitation("Miles", "Danny"));
//
//        Bundle fragmentArgs = new Bundle(); // the Bundle here allows us to send arguments to our fragment!
//        // we should pull items from the data base including all of the users current games.
//        // we should then send the information to the games here by attaching the games to the user which is pretty important!
//
//        // TODO: we should pull data from the database to get the users notifications. We also need to update the notifications counter as well!!
//
//        inGameUI.setArguments(fragmentArgs); // set the arguments of the fragment.
//
//        RelativeLayout homescreenLayout = (RelativeLayout) findViewById(R.id.homescreenLayout); // get the relative layout of the homescreen.
//        homescreenLayout.removeAllViews();
//        homescreenLayout.setBackground(null); // this should remove all views from the main view to allow us to show the fragment properly.
//
//        FragmentTransaction transaction = getFragmentManager().beginTransaction(); // get the Fragment transaction to allow us to display the fragment properly
//        transaction.replace(R.id.homescreenLayout, notificationsUI); // replace the current fragment with our games
//        transaction.commit(); // commit the fragment to be loaded.
    }

    // This is the first thing called when this class is called and will create the startup screen UI.
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen); // set the homescreen layout.

        history = (ListView) findViewById(R.id.HistoryList); // holds the list of the history for the users to be able to work with!!

        histAdapter = new HistoryListAdapter(this, R.layout.game_item,  gameHistory); // send in the game history for the game adapter!!

        // todo: be sure to remove this, this is just populating some items for us to test and see how the game is working
        for(int i = 0; i < 15; i++)
        {
            gameHistory.add("Game " + i); // simply add 15 game items for the users to be able to see
        }

        history.setAdapter(histAdapter); // set the adapter that we are working with for the history of the app itself.
        history.setDivider(null); // says that we don't want any divisors.
        history.setDividerHeight(10); // set's our divisor (granted invisible ones) to be about 10 spaces apart (roughly 10 pixels)
        histAdapter.notifyDataSetChanged(); // tell the adapter that the data has changed in the listview.

        Button createGameButton = (Button) findViewById(R.id.createGameButton); // grab the button we are working with.
        createGameButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                createInGameUIFragment(); // have the InGameUI created in here!!


                // open up the in game fragment here! Very important! If the game closes we should go back to this home menu!

                // When a new game is created we need to ensure that this game is started and added first the list of the of games in the game pager.

                // The board should be generated and the player should be notified if they want to add users or not!
            }
        });


        Button currentGameButton = (Button) findViewById(R.id.currentGamesButton); // grab our button here.
        currentGameButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {


                openCurrentGamesFragment(); // have the fragment open and show the current games to the user! Very important!

                // TODO: we can also pull from the database here and update current games if we wanted.

                // open up the current games for the user.

                // we really should have the games list the games for the user's turn is next first (I know that was worded horribly)

                // again if the user hits the back button the users should see the homescreen again.

            }
        });


        Button notificationsButton = (Button) findViewById(R.id.NotificationsButton); // grab our notifications button.
        notificationsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                openNotificationsFragment(); // open up the notifications fragment!

                // This should open up our notifications fragment which will show the user's information including events as well as invitations!

                // Again, hitting back button in this fragment should show the homescreen again, very important
            }
        });

        Button settingsButton = (Button) findViewById(R.id.SettingsButton); // grab our settings button.
        settingsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                // todo: @Steven you will implement the fragment manager here to get your UI to appear when the clicks the Settings button. Look and see how I do it.

                Toast.makeText(getApplicationContext(), "This feature is not ready yet!", Toast.LENGTH_SHORT).show();
            }
        });

        /* keep this code, this will start up our game chess board and allow users to be able to work on this thing here!!
        chessboard = (GridView) findViewById(R.id.chessboard); // find the chess board that we want to be working with.
        SquareAdapter squareAdapter = new SquareAdapter(getApplicationContext());
        chessboard.setAdapter(squareAdapter);
        squareAdapter.notifyDataSetChanged(); // tell the square adapter to update the dataset to show the correct items in the gridview.
        */

        DBIOCore.getUserReference().addValueEventListener(userListener);
        DBIOCore.getInvitationsReference().addChildEventListener(inviteListener);

    }

}
