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

import de.hdodenhof.circleimageview.CircleImageView;
import polymorphs.a301.f17.cs414.thexgame.AppBackend.Driver;
import polymorphs.a301.f17.cs414.thexgame.AppBackend.GameSnapshot;
import polymorphs.a301.f17.cs414.thexgame.AppBackend.User;
import polymorphs.a301.f17.cs414.thexgame.persistence.GameSnapshotListObserver;
import polymorphs.a301.f17.cs414.thexgame.persistence.GameSnapshotObserver;
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
        implements NavigationView.OnNavigationItemSelectedListener, UsernameListObserver, UserObserver, GameSnapshotListObserver {

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
    private String gameID = ""; // the game id used for each game.

    private Driver driver; // the driver that we will be working with within homescreen activity.

    // These will be populated by the shared preferences.
    private String email; // email of the user.
    private String name; // name of the user.
    private String username; // username of the user

    HashMap<String, String> usernames; // holds the list of people to invite keyed by the previous usernames database key
    polymorphs.a301.f17.cs414.thexgame.AppBackend.User currentUser;

    private NavigationView navigationView; // a copy of the navigation view to populate our board layout.

    // adds a game to the game pager and also shows the person we are playing the game with.
    public void addGameToPager(BoardUI boardToAdd, String whitePlayer)
    {
        boardToAdd.setWhitePlayer(whitePlayer);
        boardToAdd.setBlackPlayer(currentUser.getNickname());
        games.add(boardToAdd);
        gamePagerAdapter.notifyDataSetChanged();
    }

    // gets the game ID primarily used by InvitationsListAdapter.
    public String getGameID()
    {
        return gameID;
    }

    public User getCurrentUser()
    {
        return currentUser;
    }

    // Simply updates the view pager that we are working with.
    public void updateViewPager()
    {
        gamePager.invalidate();
    }

    // creates a new game for us to be able to work with.
    public BoardUI createNewGame(String whitePlayerNickname, String blackPlayerNickname)
    {
        System.out.println("new game created with white player: " + whitePlayerNickname + " and black player: " + blackPlayerNickname);
        String newGameKey = driver.createGame(whitePlayerNickname, blackPlayerNickname);

        System.out.println("The new game key created for the game is: " + newGameKey);

        driver.setCurrentGameKey(newGameKey); // this may be causing the problems with not moving pieces since we are setting the current game key but no game shows up.
        BoardUI newGame = new BoardUI(getBaseContext(), null);
        newGame.setHomescreenActivity(this);
        newGame.registerToSnapshot(newGameKey); // TODO: @Miles for whatever reason the board ui is not adding games to the UI like it should, only after we swipe the game pager for some reason, then it works.

       // Driver.getInstance().setCurrentGameKey( games.get(gamePager.getCurrentItem()).getGameID()); // sets the game snapshot of whatever board the pager is on.

        return newGame; // send back the board UI to work with something.
    }

    // sets the game that was switched within the view pager.
    public void switchToGameAt(int position) {

        Driver.getInstance().setCurrentGameKey( games.get(position).getGameID() );
    }

    // gets a game a specific position
    public BoardUI getGameAt(int position)
    {
        return games.get(position);
    }

    // should show games if the app is up and running.
    public void showGames()
    {
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.mainContentScreen);
        TextView noGame = (TextView) relativeLayout.findViewById(R.id.noGamesText);
        noGame.setVisibility(View.GONE); // make the text field do away

        gamePager.setVisibility(View.VISIBLE); // have the game(s) appear.
    }

    // this method sets up our game pager.
    protected void setupGamePager()
    {
       // boardUI = (BoardUI) findViewById(R.id.chessboard);
        gamePager = (ViewPager) findViewById(R.id.gamesListPager); // get the game pager that will basically fill out the games!
        boardUI = (BoardUI) findViewById(R.id.chessboard);
        boardUI.setHomescreenActivity(this); // send a copy of the homescreen activity to allow for certain displaying of certain UI elements.

        if(games.size() > 0) // make the game pager visible and remove the no games text view.
        {
            System.out.println("Showing games now!");
            RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.mainContentScreen);
            TextView noGame = (TextView) relativeLayout.findViewById(R.id.noGamesText);
            noGame.setVisibility(View.GONE); // make the text field do away

            gamePager.setVisibility(View.VISIBLE); // have the game(s) appear.
            gamePager.invalidate();
        }
        else
        {
            System.out.println("SETTING THE DRIVER FOR BOARDUI");

            // todo: we should have a list of our boards pulled from our database with the information about the piece places. This is pretty important!
            // games.add(boardUI); // add razor game (with Miles).
            //games.add(newBoard); // add corey game

            gamePagerAdapter = new GamePagerAdapter(games, inGameUI, getBaseContext()); // send in the games that we want to work with that will allow us to send our games to the adapter to update the ViewPager (to swipe horizontally)
            // TODO: create the Gamepage listener that will be in charge of getting this thing working correctly.
            GamePageChangeListener gpcl = new GamePageChangeListener(this); // holds the game as well as a copy of the InGameUI that will allow us to see a snack bar for the users to be able to see their game number.

            gamePager.setAdapter(gamePagerAdapter);
            gamePagerAdapter.notifyDataSetChanged(); // update the number of games in the list view pretty important!
            gamePager.addOnPageChangeListener(gpcl);
            gamePager.invalidate();

        }

    }

    // checks the SharedPreferences to see if the username has correctly been set. If so, proceed to maingameui, otherwise show newusername layout.
    protected boolean isUsernameSet()
    {
        if (DBIOCore.getInstance().getCurrentUserUsername() == null) return true;
        return false;
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
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Molto Importante: This needs to be displayed at the very beginning before we do any work on the app otherwise our UI elements will not be displayed properly this is very important!
        // (cont): we need to display this first and then update it the app loads, putting this at the end caused the app to break which is not good!
        setContentView(R.layout.homescreen);
        usernames = new HashMap<>();

        driver = Driver.getInstance();

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        if(isUsernameSet())
        {
            System.out.println("username was not set!");

            preferences.edit().putBoolean("usernameCreated", true).apply(); // sets this in main memory in a background thread.
            preferences.edit().putBoolean("userCreated", true).apply(); // sets this in main memory in a background thread.

            // Start the activity for setting a username.
            // todo: removed because we want this to be asking to create username in startupscreen.
            //Intent setUsernameIntent = new Intent(this, SetUsernameActivity.class);
            //startActivityForResult(setUsernameIntent, SET_USERNAME); // start the activity for intent!
        }
        else
        {
            displayHomescreen(); // setup the familiar homescreen layout that we are used to seeing.
            System.out.println("Home screen is being displayed!!");
        }

        System.out.println("The number of games that we have == " + 0);
        if(games.size() != 0)
        {
            System.out.println("we have more than 0 games!! Adding database items now");

        }
        DBIOCore.getInstance().registerToGameSnapshotList(this);
        DBIOCore.getInstance().registerToUsernameList(this);
        DBIOCore.getInstance().registerToCurrentUser(this);

    }

    // This method sets up the header for the navigation view which will show the user's nickname and email so they know that they are logged in.
    private void setupHeader()
    {
        LinearLayout navHeaderView = (LinearLayout) navigationView.getHeaderView(0); // gets the header view of the nav_header_homescreen.
        TextView nameText = (TextView) navHeaderView.findViewById(R.id.nameText);
        nameText.setText(username); // display the user's nickname in the navheader.

        TextView textView = (TextView) navHeaderView.findViewById(R.id.textView); // get the text view out of our header.
        textView.setText(email);

        CircleImageView navProfilePic = (CircleImageView) navHeaderView.findViewById(R.id.navProfilePicture); // get the profile picture for the navHeaderView this is very important!
        navProfilePic.setOnClickListener(new View.OnClickListener() {

            // When clicked, simply open the current user's profile fragment.
            @Override
            public void onClick(View view)
            {
                openCurrentUserProfileFragment();
            }
        });

    }

    // this reads the number of notifications from main memory for this user and updats the text for the notifications.
    public void updateNotificationsCount()
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        int notsCount = preferences.getInt("NotificationsCount", 0); // 0 is default if nothing is there.

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu navMenu = navigationView.getMenu();

        MenuItem notificationsText = navMenu.findItem(R.id.notifications);

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

        CreateNewGameButtonListener newGameButtonListener = new CreateNewGameButtonListener(HomescreenActivity.this);
        createNewGameButton.setOnClickListener(newGameButtonListener);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setupHeader(); // setup the header of the navigation view.
        updateNotificationsCount(); // update the count of notifications.

        usernames = new HashMap<>();
        setupGamePager(); // setup the game pager here, this is what allows our game to be completed.
    }


    // -------------------------------------------------- Observer and Listener code START ----------------------------------------------------------------------------------------

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

    // this method no longer seems to keep track of the user that was updated
    @Override
    public void userUpdated(User u) {
        System.out.println("User in HomescreenActivity is " + u.getNickname());
        currentUser = u;
        name = u.getName();
        email = u.getEmail();
        username = u.getNickname();
        setupHeader(); // reset up the header so that we can see our credentials in the drawer layout.
    }

    // tracks when a snapshot is added
    @Override
    public void snapshotAdded(GameSnapshot addedSnapshot, String precedingSnapshotKey)
    {
        System.out.println("In snapshotAdded, Adding a game to the pager now!");
        BoardUI newGame = new BoardUI(getBaseContext(), null);
        newGame.registerToSnapshot(addedSnapshot.getDbKey()); // register this new game to the snapshot.
        newGame.setHomescreenActivity(this);

        newGame.setWhitePlayer(addedSnapshot.getNicknameWhite()); // set the white player nickname
        newGame.setBlackPlayer(addedSnapshot.getNicknameBlack()); // set the black player nickname.

        driver.setCurrentGameKey(addedSnapshot.getDbKey());
        games.add(newGame);
        gamePagerAdapter.notifyDataSetChanged();
        //updateViewPager(); this was causing problems
    }

    @Override
    public void snapshotChanged(GameSnapshot changedSnapshot, String precedingSnapshotKey)
    {
        System.out.println("There was a change in the gamesnapshot with players" + changedSnapshot.getNicknameWhite() + " and " + changedSnapshot.getNicknameBlack());

    }

    @Override
    public void snapshotRemoved(GameSnapshot removedSnapshot)
    {
        // todo: remove from the view pager.
    }

    // -------------------------------------------------- Observer and Listener code END ----------------------------------------------------------------------------------------

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

        // TODO: add the code that will simply close our fragments instead of causing the app to restart each time which is a huge pain.
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

    // opens the current user's profile fragment which should also show the user's current information which is very important.
    protected void openCurrentUserProfileFragment()
    {
        RelativeLayout homescreenLayout = (RelativeLayout) findViewById(R.id.mainContentScreen); // get the relative layout of the homescreen.
        homescreenLayout.removeAllViews();
        homescreenLayout.setBackground(null); // this should remove all views from the main view to allow us to show the fragment properly.

        // todo: create the profile Class and also open up the profile class which is very very very important!

        FragmentTransaction transaction = getFragmentManager().beginTransaction(); // get the Fragment transaction to allow us to display the fragment properly
        transaction.replace(R.id.mainContentScreen, settingsUI); // todo: replace the current fragment with the profile fragment which requires a copy of the profile class to be implemented.
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