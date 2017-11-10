package polymorphs.a301.f17.cs414.thexgame.ui.activities;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import polymorphs.a301.f17.cs414.thexgame.AppBackend.Driver;
import polymorphs.a301.f17.cs414.thexgame.AppBackend.User;
import polymorphs.a301.f17.cs414.thexgame.ui.BoardUI;
import polymorphs.a301.f17.cs414.thexgame.R;
import polymorphs.a301.f17.cs414.thexgame.persistence.DBIOCore;
import polymorphs.a301.f17.cs414.thexgame.persistence.UserObserver;
import polymorphs.a301.f17.cs414.thexgame.persistence.UsernameListObserver;
import polymorphs.a301.f17.cs414.thexgame.ui.adapters.ActivityListAdapter;
import polymorphs.a301.f17.cs414.thexgame.ui.adapters.GamePagerAdapter;
import polymorphs.a301.f17.cs414.thexgame.ui.adapters.SquareAdapter;
import polymorphs.a301.f17.cs414.thexgame.ui.fragments.NotificationsFragment;
import polymorphs.a301.f17.cs414.thexgame.ui.fragments.SettingsFragment;
import polymorphs.a301.f17.cs414.thexgame.ui.listeners.CreateNewGameButtonListener;
import polymorphs.a301.f17.cs414.thexgame.ui.listeners.GamePageChangeListener;
import polymorphs.a301.f17.cs414.thexgame.ui.listeners.SubmitButtonClickListener;

public class HomescreenActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, UsernameListObserver, UserObserver {

    private NotificationsFragment notificationsFragment = new NotificationsFragment(); // a copy of the notifications UI that should be built for the user.
    private ArrayList<String> currentGames; // the list of current games.
    private ViewPager gamePager; // this holds the game view pager which essentially is a list of horizontal list items of the game, which is pretty awesome!
    private GamePagerAdapter gamePagerAdapter; // holds the gamePagerAdapter that we need to be working on here.
    private BoardUI boardUI; // holds the chess board that we are going to be working on.
    private ArrayList<BoardUI> games = new ArrayList<>(); // holds all of our games, mainly there boards, hence why we are containing GridViews
    private ArrayList<String> events = new ArrayList<>(); // todo: this should filled out after a call to our database. This will allow us to be able to get events about the game for the user to look at.
    private ActivityListAdapter eventsListAdapter; // the events adapter here for us to update, very important!
    private SquareAdapter squareAdapter; // holds our square adapter which will allow us to be able to work on our lists and update the information for the players to be able to play a game!
    private View inGameUI; // holds the in game ui view which is needed to create another board for users to be able to play games on.
    private boolean startNewGame = false; // tells the inGameUI that we want to start a new game, which involves sending a list of invite(s) to other player(s).
    private boolean openCurrentGames = false; // tells inGameUI to just open the current list of games.
    private SubmitButtonClickListener submitClickListener;
    private SettingsFragment settingsUI = new SettingsFragment(); // holds a copy of our settingsUI.
    private final int SET_USERNAME = 9001; // details what we are doing for the username.


    private Driver driver = new Driver(); // the driver that we will be working with within homescreen activity.

    // These will be populated by the shared preferences.
    private String email; // email of the user.
    private String name; // name of the user.
    private String username; // username of the user

    HashMap<String, String> usernames; // holds the list of people to invite keyed by the previous usernames database key
    polymorphs.a301.f17.cs414.thexgame.AppBackend.User currentUser;


    // adds a game to the game pager and also shows the person we are playing the game with.
    public void addGameToPager(BoardUI boardToAdd, String opponent)
    {
        games.add(boardToAdd);
        gamePagerAdapter.notifyDataSetChanged();
    }

    // this method sets up our game pager.
    protected void setupGamePager()  {

        boardUI = (BoardUI) findViewById(R.id.chessboard);

        // TODO: @Roger remove this because we are hard creating a game and this should not happen! Very important!
        User user1 = new User("tmp", "tmp", "white"); // BreadCrumb: turn order hack
        User user2 = new User("tmp", "tmp", "black");// BreadCrumb: turn order hack
        driver.createGame(user1, user2); // create a game with two random players, pretty important.
        driver.setCurrentGameIndex(0); // TODO: @Team, remove this because it setting the game index to always be 0 and this will not be allowed for our game.

        System.out.println("SETTING THE DRIVER FOR BOARDUI");
        boardUI.setDriver(driver); // set the driver for the boardUI to be working with.
        boardUI.setHomescreenActivity(this); // send a copy of the homescreen activity to allow for certain displaying of certain UI elements.

        gamePager = (ViewPager) findViewById(R.id.gamesListPager); // get the game pager that will basically fill out the games!

        // todo: we should have a list of our boards pulled from our database with the information about the piece places. This is pretty important!
        games.add(boardUI); // add once.
       // games.add(boardUI); // add twice.

        gamePagerAdapter = new GamePagerAdapter(games, inGameUI); // send in the games that we want to work with that will allow us to send our games to the adapter to update the ViewPager (to swipe horizontally)
        // TODO: create the Gamepage listener that will be in charge of getting this thing working correctly.
        GamePageChangeListener gpcl = new GamePageChangeListener(this); // holds the game as well as a copy of the InGameUI that will allow us to see a snack bar for the users to be able to see their game number.

        gamePager.setAdapter(gamePagerAdapter);
        gamePagerAdapter.notifyDataSetChanged(); // update the number of games in the list view pretty important!
        gamePager.addOnPageChangeListener(gpcl);

    }

    // checks the SharedPreferences to see if the username has correctly been set. If so, proceed to maingameui, otherwise show newusername layout.
    protected boolean isUsernameSet()
    {
        // We are reading from main memory here. This is where we will have to have read/write permissions setup on the phone. The app will always ask for a username unless we have this here.
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean usernameCreated = preferences.getBoolean("usernameCreated", false); // grabs the boolean named usernameCreated, if the boolean does not exist, the default boolean is false.
        return usernameCreated;
    }



    /*
        This reads what was written from SharedPreferences and restores it into our global variables.
     */
    private void resetBasicInfoFromMainMemory()
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        name = prefs.getString("name", "");
        email = prefs.getString("email", "");
        username = prefs.getString("username", "");

        /*
            NOTE: If we really wanted to we could reset the current user's info doing so below
            currentUser.setName(name);
            currentUser.setEmail(email);
            currentUser.setUsername(username);
            But the idea is that is should be read from the database, and if not, we could definitely reset it from the main memory so that we could have it because current user gone after app is killed.
          */

    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // TODO: @Roger, the app is getting to the point where it is lagging now. When we pull games from the database we need to do an AsynTask to load them up so the users can look at a loading screen while it loads.

        setContentView(R.layout.homescreen);
        usernames = new HashMap<>();
        setupGamePager(); // setup our game pager, pretty important.

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        if(isUsernameSet() == false)
        {
            preferences.edit().putBoolean("usernameCreated", true).apply(); // sets this in main memory in a background thread.
            preferences.edit().putBoolean("userCreated", true).apply(); // sets this in main memory in a background thread.

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

    // This method sets up the header for the navigation view which will show the user's nickname and email so they know that they are logged in.
    private void setupHeader(NavigationView navigationView)
    {
        LinearLayout navHeaderView = (LinearLayout) navigationView.getHeaderView(0); // gets the header view of the nav_header_homescreen.
        TextView nameText = (TextView) navHeaderView.findViewById(R.id.nameText);
        nameText.setText(username); // display the user's nickname in the navheader.

        TextView textView = (TextView) navHeaderView.findViewById(R.id.textView); // get the text view out of our header.
        textView.setText(email);
    }

    // this reads the number of notifications from main memory for this user and updats the text for the notifications.
    public void updateNotificationsCount()
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        int notsCount = preferences.getInt("NotificationsCount", 0); // 0 is default if nothing is there.

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu navMenu = navigationView.getMenu();

        MenuItem notificationsText = navMenu.findItem(R.id.notifications);

        // TODO: the noficiations counter is not perfect but it works in every case except for on app startup and notifications already exist. The reason is because we need the notificationFragment to be
        // todo (cont): started and we can't start it until we touch the notifications, however, removal of a notification does work.
        // TODO: @Miles, is there a way we can read invites for the currentUser from the database (assuming that we get that null currentuser situation handled)?
        if(notsCount == 0) {
            notificationsText.setTitle("Notifications"); // don't have a count if notifications show nothing.
        }
        else {
            notificationsText.setTitle("Notifications (" + notsCount + ")"); // have a counter if there are notifications.
        }

    }

    /*
       This writes the user's information to main memory. It's a bit of a hack, but for some reason when the app is killed, we can no longer get the current user, it's not updated from the database.
       This will work for now because each person who has a phone will be logged in with one account and this will show the name of that person in local memory and what we are saving is small so it works.
    */
    private void writeBasicInfoToMemory(String name, String email, String username)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        preferences.edit().putString("name", name).commit();
        preferences.edit().putString("email", email).commit();
        preferences.edit().putString("username", username).commit();
    }

    /*
        This method set's up our homescreen which is pretty important!
     */
    protected void displayHomescreen()
    {
        setContentView(R.layout.homescreen);
        resetBasicInfoFromMainMemory(); // resets all of the basic info from main memory.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton createNewGameButton = (FloatingActionButton) findViewById(R.id.createNewGameButton);

        // TODO: @Miles for some weird reason, we are getting a null reference for the current user. This likely means the same for usernames. This is why sendInvites Don't work, try to get that fixed and we are golden.
        CreateNewGameButtonListener newGameButtonListener = new CreateNewGameButtonListener(HomescreenActivity.this);
        createNewGameButton.setOnClickListener(newGameButtonListener);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setupHeader(navigationView); // setup the header of the navigation view.
        updateNotificationsCount(); // update the count of notifications.

        usernames = new HashMap<>();
        setupGamePager(); // setup our game pager, pretty important.

    }

    /*
        This method is called when the user set's up their username. The information is passed back from the SetUsernameActivity which is sent here.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intentResult)
    {
        super.onActivityResult(requestCode, resultCode, intentResult);

        if(requestCode == SET_USERNAME) // if the request code came from SetUsernameActivity.
        {
            Bundle data = intentResult.getBundleExtra("results");
            name = data.getString("name"); // this comes back as null
            email = data.getString("email"); // this comes back as null.
            username = data.getString("username"); // this comes back fine.
            // note: in the very first run, we have our current user and we can grab their name and email, but when the app is closed we lose our current user.
            writeBasicInfoToMemory(currentUser.getName(), currentUser.getEmail(), username); // write the user's basic info to main memory.
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

    // Controls the actions when the back button is pressed, in this case, we make it so that the sliding drawer closes
    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            updateNotificationsCount();
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

        // TODO: we should pull data from the database to get the users notifications. We also need to update the notifications counter as well!!

        RelativeLayout homescreenLayout = (RelativeLayout) findViewById(R.id.mainContentScreen); // get the relative layout of the homescreen.
        homescreenLayout.removeAllViews();
        homescreenLayout.setBackground(null); // this should remove all views from the main view to allow us to show the fragment properly.

        FragmentTransaction transaction = getFragmentManager().beginTransaction(); // get the Fragment transaction to allow us to display the fragment properly
        transaction.replace(R.id.mainContentScreen, notificationsFragment); // replace the current fragment with our games
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
        updateNotificationsCount(); // update the notifications as soon as something is pressed.
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
