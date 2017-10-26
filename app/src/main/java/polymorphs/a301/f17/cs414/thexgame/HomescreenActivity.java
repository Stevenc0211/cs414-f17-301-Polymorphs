package polymorphs.a301.f17.cs414.thexgame;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import polymorphs.a301.f17.cs414.thexgame.AppBackend.User;
import polymorphs.a301.f17.cs414.thexgame.persistence.DBIOCore;
import polymorphs.a301.f17.cs414.thexgame.persistence.UserObserver;
import polymorphs.a301.f17.cs414.thexgame.persistence.UsernameListObserver;
import polymorphs.a301.f17.cs414.thexgame.ui.ActivityListAdapter;
import polymorphs.a301.f17.cs414.thexgame.ui.CreateNewGameButtonListener;
import polymorphs.a301.f17.cs414.thexgame.ui.GamePageChangeListener;
import polymorphs.a301.f17.cs414.thexgame.ui.GamePagerAdapter;
import polymorphs.a301.f17.cs414.thexgame.ui.NotificationsUI;
import polymorphs.a301.f17.cs414.thexgame.ui.SettingsUI;
import polymorphs.a301.f17.cs414.thexgame.ui.SquareAdapter;
import polymorphs.a301.f17.cs414.thexgame.ui.SubmitButtonClickListener;

public class HomescreenActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, UsernameListObserver, UserObserver {

    private NotificationsUI  notificationsUI = new NotificationsUI(); // a copy of the notifications UI that should be built for the user.
    private ArrayList<String> currentGames; // the list of current games.
    private ViewPager gamePager; // this holds the game view pager which essentially is a list of horizontal list items of the game, which is pretty awesome!
    private GamePagerAdapter gamePagerAdapter; // holds the gamePagerAdapter that we need to be working on here.
    private Chessboard chessboard; // holds the chess board that we are going to be working on.
    private ArrayList<Chessboard> games = new ArrayList<>(); // holds all of our games, mainly there boards, hence why we are containing GridViews
    private ArrayList<String> events = new ArrayList<>(); // todo: this should filled out after a call to our database. This will allow us to be able to get events about the game for the user to look at.
    private ActivityListAdapter eventsListAdapter; // the events adapter here for us to update, very important!
    private SquareAdapter squareAdapter; // holds our square adapter which will allow us to be able to work on our lists and update the information for the players to be able to play a game!
    private View inGameUI; // holds the in game ui view which is needed to create another board for users to be able to play games on.
    private boolean startNewGame = false; // tells the inGameUI that we want to start a new game, which involves sending a list of invite(s) to other player(s).
    private boolean openCurrentGames = false; // tells inGameUI to just open the current list of games.
    private SubmitButtonClickListener submitClickListener;
    private SettingsUI settingsUI = new SettingsUI(); // holds a copy of our settingsUI.
    private final int SET_USERNAME = 9001; // details what we are doing for the username.


    // These will be populated by the shared preferences.
    private String email; // email of the user.
    private String name; // name of the user.
    private String username; // username of the user

    HashMap<String, String> usernames; // holds the list of people to invite keyed by the previous usernames database key
    polymorphs.a301.f17.cs414.thexgame.AppBackend.User currentUser;


    // adds a game to the game pager and also shows the person we are playing the game with.
    public void addGameToPager(Chessboard boardToAdd, String opponent)
    {
        games.add(boardToAdd);
        gamePagerAdapter.notifyDataSetChanged();
    }

    // this method sets up our game pager.
    protected void setupGamePager()  {

        chessboard  = (Chessboard) findViewById(R.id.chessboard);
        gamePager = (ViewPager) findViewById(R.id.gamesListPager); // get the game pager that will basically fill out the games!

        // todo: we should have a list of our boards pulled from our database with the information about the piece places. This is pretty important!
        games.add(chessboard); // add once.
        games.add(chessboard); // add twice.

        gamePagerAdapter = new GamePagerAdapter(games, inGameUI); // send in the games that we want to work with that will allow us to send our games to the adapter to update the ViewPager (to swipe horizontally)
        // TODO: create the Gamepage listener that will be in charge of getting this thing working correctly.
        GamePageChangeListener gpcl = new GamePageChangeListener(this); // holds the game as well as a copy of the InGameUI that will allow us to see a snack bar for the users to be able to see their game number.

        gamePager.setAdapter(gamePagerAdapter);
        gamePagerAdapter.notifyDataSetChanged(); // update the number of games in the list view pretty important!
        gamePager.addOnPageChangeListener(gpcl);

    }

    // This method will update the events adapter to show the updated information which is helps when showing the information in real time! Can be used inside and outside the class!
    public void updateEvents() {
        eventsListAdapter.notifyDataSetChanged();
    }

    // sets up the ActivityListView. TODO: This data should be pulled from the database
    protected void setupActivityListView(View gameUIView) {
        ListView activities = (ListView) gameUIView.findViewById(R.id.EventsList); // grabs the events that we are working with.
        eventsListAdapter = new ActivityListAdapter(gameUIView.getContext(), R.layout.activity_item, events); // holds the game Event lists which is pretty important!

        activities.setAdapter(eventsListAdapter);
        eventsListAdapter.notifyDataSetChanged(); // let the adapter know we have new items to update.
        activities.setDivider(null); // set no dividers.
        activities.setDividerHeight(10); // set the spacing between our invisible dividers.
    }

    // checks the SharedPreferences to see if the username has correctly been set. If so, proceed to maingameui, otherwise show newusername layout.
    protected boolean isUsernameSet()
    {
        // We are reading from main memory here. This is where we will have to have read/write permissions setup on the phone. The app will always ask for a username unless we have this here.
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean usernameCreated = preferences.getBoolean("usernameCreated", false); // grabs the boolean named usernameCreated, if the boolean does not exist, the default boolean is false.
        return usernameCreated;
    }

    protected boolean isUserCreated()
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean userCreated = preferences.getBoolean("userCreated", false); // grabs the boolean named usernameCreated, if the boolean does not exist, the default boolean is false.
        return userCreated;
    }

    // When this method is called, the mainGameUI is created along with all of the adapters and button listeners with the homescreenlayout created.
    public void createMainGameUI()
    {
        setContentView(R.layout.homescreen); // set the homescreen layout.
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        if(isUserCreated() == false)
        {
            name = submitClickListener.getName();
            email = submitClickListener.getEmail();
            username = submitClickListener.getUsername();

            System.out.println("name we want to send = " + name);
            System.out.println("email we want to send = " + email);
            System.out.println("username we want to send = " + username);

        }
        else
        {
            preferences.edit().putBoolean("userCreated", true).commit(); // IGNORE THIS!
        }


        Button createGameButton = (Button) findViewById(R.id.createGameButton); // grab the button we are working with.
        createGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // createInGameUIFragment(); // have the InGameUI created in here!!
            }
        });


        Button currentGameButton = (Button) findViewById(R.id.currentGamesButton); // grab our button here.
        currentGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //openCurrentGamesFragment(); // have the fragment open and show the current games to the user! Very important!

                // TODO: we can also pull from the database here and update current games if we wanted.
            }
        });


        Button notificationsButton = (Button) findViewById(R.id.NotificationsButton); // grab our notifications button.
        notificationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // openNotificationsFragment(); // open up the notifications fragment!
            }
        });

        Button settingsButton = (Button) findViewById(R.id.SettingsButton); // grab our settings button.
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //openSettingsFragment(); // opens the settings fragment!

                // some of the features inside the settings fragment do not work yet and will be updated when some more of the main features of the game have been implemented.
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        DBIOCore.registerToCurrentUser(this);

        setContentView(R.layout.homescreen2);
        usernames = new HashMap<>();
        // Create our chessboard which is very important!
        //chessboard = (GridView) findViewById(R.id.chessboard); // find the chess board that we want to be working with.
        //squareAdapter = new SquareAdapter(getApplicationContext());
        //chessboard.setAdapter(squareAdapter);
        //squareAdapter.notifyDataSetChanged(); // tell the square adapter to update the dataset to show the correct items in the gridview.
        setupGamePager(); // setup our game pager, pretty important.

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        if(isUsernameSet() == false)
        {
            preferences.edit().putBoolean("usernameCreated", true).commit();
            preferences.edit().putBoolean("userCreated", true).commit();

            // Start the activity for setting a username.
            Intent setUsernameIntent = new Intent(this, SetUsernameActivity.class);
            startActivityForResult(setUsernameIntent, SET_USERNAME); // start the activity for intent!
        }
        else
        {
            displayHomescreen(); // setup the familiar homescreen layout that we are used to seeing.
        }

        DBIOCore.registerToUsernameList(this);
        DBIOCore.registerToCurrentUser(this);

    }

    /*
        This method set's up our homescreen which is pretty important!
     */
    protected void displayHomescreen()
    {
        setContentView(R.layout.homescreen2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton createNewGameButton = (FloatingActionButton) findViewById(R.id.createNewGameButton);

        // TODO: @Miles for some weird reason, we are getting a null reference for the current user. This likely means the same for usernames. This is why sendInvites Don't work, try to get that fixed and we are golden.
        CreateNewGameButtonListener newGameButtonListener = new CreateNewGameButtonListener(HomescreenActivity.this, usernames, currentUser);
        createNewGameButton.setOnClickListener(newGameButtonListener);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // TODO: once we have the ability to send invites, and get the user able to do things, we need to be able to change the name for the user to see their name while playing.

        usernames = new HashMap<>();
        setupGamePager(); // setup our game pager, pretty important.
    }

    /*
        This method is called when the user set's up their username.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intentResult)
    {
        super.onActivityResult(requestCode, resultCode, intentResult);

        if(requestCode == SET_USERNAME) // if the request code came from SetUsernameActivity.
        {
            Bundle data = intentResult.getBundleExtra("results");
            name = data.getString("name");
            email = data.getString("email");
            username = data.getString("username");
            displayHomescreen();
        }

    }


    @Override
    public void usernameAdded(String addedUsername, String precedingUsernameKey) {
        usernames.put(precedingUsernameKey, addedUsername);
    }

    @Override
    public void usernameChanged(String changedUsername, String precedingUsernameKey) {
        usernames.put(precedingUsernameKey, changedUsername);
    }

    @Override
    public void usernameRemoved(String removedUsername) {
        String rmKey = "";
        for (String key : usernames.keySet()) {
            if (usernames.get(key).equals(removedUsername)) {
                rmKey = key;
            }
        }
        if (rmKey != "") {
            usernames.remove(rmKey);
        }
    }

    @Override
    public void userUpdated(User u) {
        System.out.println("User in HomescreenActivity is " + u.getNickname());
        currentUser = u;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.homescreen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        item.setIcon(R.drawable.quit);

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            RelativeLayout homescreenLayout = (RelativeLayout) findViewById(R.id.mainContentScreen); // get the relative layout of the homescreen.
            Snackbar.make(homescreenLayout, "This feature isn't available yet ¯\\_(ツ)_/¯ ", Snackbar.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    // opens up the settings menu
    protected void openNotificationsFragment()
    {
        Bundle fragmentArgs = new Bundle(); // the Bundle here allows us to send arguments to our fragment!
        // we should pull items from the data base including all of the users current games.
        // we should then send the information to the games here by attaching the games to the user which is pretty important!

        // TODO: we should pull data from the database to get the users notifications. We also need to update the notifications counter as well!!

        //inGameUI.setArguments(fragmentArgs); // set the arguments of the fragment.

        RelativeLayout homescreenLayout = (RelativeLayout) findViewById(R.id.mainContentScreen); // get the relative layout of the homescreen.
        homescreenLayout.removeAllViews();
        homescreenLayout.setBackground(null); // this should remove all views from the main view to allow us to show the fragment properly.

        FragmentTransaction transaction = getFragmentManager().beginTransaction(); // get the Fragment transaction to allow us to display the fragment properly
        transaction.replace(R.id.mainContentScreen, notificationsUI); // replace the current fragment with our games
        transaction.commit(); // commit the fragment to be loaded.
    }

    // opens up the history menu
    protected void openHistoryFragment()
    {
        // todo: needs to be implemented.
    }

    protected void openMessagesFragment()
    {
        // todo: needs to be implemented.
    }

    // opens up the settings fragment
    protected void openSettingsFragment()
    {
        Bundle fragmentArgs = new Bundle(); // the Bundle here allows us to send arguments to our fragment!

        RelativeLayout homescreenLayout = (RelativeLayout) findViewById(R.id.mainContentScreen); // get the relative layout of the homescreen.
        homescreenLayout.removeAllViews();
        homescreenLayout.setBackground(null); // this should remove all views from the main view to allow us to show the fragment properly.

        FragmentTransaction transaction = getFragmentManager().beginTransaction(); // get the Fragment transaction to allow us to display the fragment properly
        transaction.replace(R.id.mainContentScreen, settingsUI); // replace the current fragment with our games
        transaction.commit(); // commit the fragment to be loaded.
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        RelativeLayout homescreenLayout = (RelativeLayout) findViewById(R.id.mainContentScreen);

        int id = item.getItemId();

        if (id == R.id.notifications)
        {
            openNotificationsFragment();
        }
        else if (id == R.id.History)
        {
            Snackbar.make(homescreenLayout, "This feature isn't available yet ¯\\_(ツ)_/¯ ", Snackbar.LENGTH_LONG).show();
        }
        else if (id == R.id.messages)
        {
            Snackbar.make(homescreenLayout, "This feature isn't available yet ¯\\_(ツ)_/¯ ", Snackbar.LENGTH_LONG).show();

        } else if (id == R.id.settings)
        {
            openSettingsFragment();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
