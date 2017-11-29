package polymorphs.a301.f17.cs414.thexgame.ui.activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import java.util.logging.Handler;

import de.hdodenhof.circleimageview.CircleImageView;
import polymorphs.a301.f17.cs414.thexgame.AppBackend.Driver;
import polymorphs.a301.f17.cs414.thexgame.AppBackend.GameSnapshot;
import polymorphs.a301.f17.cs414.thexgame.AppBackend.User;
import polymorphs.a301.f17.cs414.thexgame.persistence.GameSnapshotListObserver;
import polymorphs.a301.f17.cs414.thexgame.ui.BoardUI;
import polymorphs.a301.f17.cs414.thexgame.R;
import polymorphs.a301.f17.cs414.thexgame.persistence.DBIOCore;
import polymorphs.a301.f17.cs414.thexgame.persistence.UserObserver;
import polymorphs.a301.f17.cs414.thexgame.persistence.UsernameListObserver;
import polymorphs.a301.f17.cs414.thexgame.ui.adapters.ActivityListAdapter;
import polymorphs.a301.f17.cs414.thexgame.ui.adapters.GamePagerAdapter;
import polymorphs.a301.f17.cs414.thexgame.ui.adapters.SquareAdapter;
import polymorphs.a301.f17.cs414.thexgame.ui.fragments.CurrentUserProfileFragment;
import polymorphs.a301.f17.cs414.thexgame.ui.fragments.HelpFragment;
import polymorphs.a301.f17.cs414.thexgame.ui.fragments.HistoryFragment;
import polymorphs.a301.f17.cs414.thexgame.ui.fragments.NotificationsFragment;
import polymorphs.a301.f17.cs414.thexgame.ui.fragments.SettingsFragment;
import polymorphs.a301.f17.cs414.thexgame.ui.listeners.CreateNewGameButtonListener;
import polymorphs.a301.f17.cs414.thexgame.ui.listeners.GamePageChangeListener;
import polymorphs.a301.f17.cs414.thexgame.ui.listeners.SubmitButtonClickListener;

public class HomescreenActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, UsernameListObserver, UserObserver, GameSnapshotListObserver {

    private NotificationsFragment notificationsFragment = new NotificationsFragment(); // a copy of the notifications UI that should be built for the user.
    private HistoryFragment historyFragment = new HistoryFragment(); // a copy of the history fragment here.
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

    private final int SET_USERNAME = 9001; // details what we are doing for the username.
    private String gameID = ""; // the game id used for each game.

    private Driver driver; // the driver that we will be working with within homescreen activity.
    private boolean fragmentOpen = false; // tells the app if a fragment is open, if it is, then we need to ensure that we are fixing things
    private Fragment currentFragment; // holds the current fragment thats open.
    private FloatingActionButton createNewGameButton; // a copy of the create new game button that will allow us to

    // These will be populated by the shared preferences.
    private String email; // email of the user.
    private String name; // name of the user.
    private String username; // username of the user
    private Bitmap userProfilePic; // holds a copy of a user's profile picture.

    HashMap<String, String> usernames; // holds the list of people to invite keyed by the previous usernames database key
    polymorphs.a301.f17.cs414.thexgame.AppBackend.User currentUser;

    private NavigationView navigationView; // a copy of the navigation view to populate our board layout.

    // TODO: this needs to save the profile picture to the database so that when a user changes their profile picture it is not lost!
    public void saveAndChangeProfilePic(Bitmap newProfilePic)
    {
        userProfilePic = newProfilePic; // set the new profile picture for the user.
        setupHeader(); // have the header refresh with the new profile picture now set.

        // TODO: @Miles, @Andy, @Anyone, in here is where we need to save that profile picture to the database. I will take care of saving the picture to header view, but we need the DB for it to save permanently
    }

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
        String newGameKey = driver.createGame(whitePlayerNickname, blackPlayerNickname);

        driver.setCurrentGameKey(newGameKey); // this may be causing the problems with not moving pieces since we are setting the current game key but no game shows up.
        BoardUI newGame = new BoardUI(getBaseContext(), null);
        newGame.setHomescreenActivity(this);
        newGame.registerToSnapshot(newGameKey);

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

    // returns the number of games.
    public int getNumOfGames()
    {
        return games.size();
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

            gamePagerAdapter = new GamePagerAdapter(games, inGameUI, getBaseContext()); // send in the games that we want to work with that will allow us to send our games to the adapter to update the ViewPager (to swipe horizontally)
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

    // set's the title of the player who's turn we are trying to alert.
    public void changeTurnText(String playerTurn)
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(playerTurn);
        setSupportActionBar(toolbar);
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

        displayHomescreen(); // setup the familiar homescreen layout that we are used to seeing.


        DBIOCore.getInstance().registerToGameSnapshotList(this);
        DBIOCore.getInstance().registerToUsernameList(this);
        DBIOCore.getInstance().registerToCurrentUser(this);


    }

    // calculates the size of the optimum image
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)
    {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    // decodes the bitmap with the correct size so that it's not too large.
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight)
    {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
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

        if(userProfilePic != null) // if not null, set the user's profile picture on the navbar layout!
        {
            navProfilePic.setImageBitmap(userProfilePic);
        }
        else // if there is no profile picture, then resize the default image to ensure that it is not too large.
        {
            Bitmap resizedDefaultPic = decodeSampledBitmapFromResource(getResources(), R.drawable.blank_profile_image, 100,100); // resize the image to a 100 by 100 image.
            navProfilePic.setImageBitmap(resizedDefaultPic); // set the newly resized bitmap.
        }

        navProfilePic.setOnClickListener(null); // set the click listener to null to remove any old listeners that were previously here.
        navProfilePic.setOnClickListener(new View.OnClickListener() { // set the new click listener we want for the imageview.

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
        This method set's up our homescreen which is pretty important!
     */
    protected void displayHomescreen()
    {
        setContentView(R.layout.homescreen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        createNewGameButton = (FloatingActionButton) findViewById(R.id.createNewGameButton);

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

    // simply automatically closes the drawer.
    private void closeDrawer()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    // Controls the actions when the back button is pressed, in this case, we make it so that the sliding drawer closes
    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if(fragmentOpen == true && currentFragment != null) // close the fragment, set fragment open to false.
        {
            System.out.println("trying to end the fragment that is currently open");
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.animator.slide_up_out, R.animator.slide_up_out); // set the slide down animation for when a user hits the back button.
            transaction.remove(currentFragment); // remove the current fragment
            transaction.commit(); // make the changes to remove the fragment
            createNewGameButton.setVisibility(View.VISIBLE); // make the createNewGameButton reappear.


        }
        else {
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


        if (id == R.id.quit)
        {
            // todo: when this button is pushed the current user should quit this game generating a win for the other player.

            RelativeLayout homescreenLayout = (RelativeLayout) findViewById(R.id.mainContentScreen); // get the relative layout of the homescreen.
            Snackbar.make(homescreenLayout, "This feature isn't available yet ¯\\_(ツ)_/¯ ", Snackbar.LENGTH_LONG).show();
            return true;
        }
        else if(id == R.id.sendMessage)
        {
            // todo: this should open up the send message fragment!

            RelativeLayout homescreenLayout = (RelativeLayout) findViewById(R.id.mainContentScreen); // get the relative layout of the homescreen.
            Snackbar.make(homescreenLayout, "This feature isn't available yet ¯\\_(ツ)_/¯ ", Snackbar.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    // opens up the settings menu
    protected void openNotificationsFragment()
    {
        RelativeLayout homescreenLayout = (RelativeLayout) findViewById(R.id.mainContentScreen); // get the relative layout of the homescreen.
        homescreenLayout.setBackground(null); // this should remove all views from the main view to allow us to show the fragment properly.

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.slide_up_in, R.animator.slide_up_out); // set custom animations for this fragment
        fragmentTransaction.replace(R.id.mainContentScreen, notificationsFragment); // replace the current fragment with our games
        fragmentTransaction.commit(); // commit the fragment to be loaded.
        closeDrawer(); // close the drawer automatically.
        fragmentOpen = true; // set the boolean to be true that a fragment is indeed open.
        currentFragment = notificationsFragment; // set the current fragment.
        createNewGameButton.setVisibility(View.GONE);
    }

    // opens up the history menu
    protected void openHistoryFragment()
    {
        RelativeLayout homescreenLayout = (RelativeLayout) findViewById(R.id.mainContentScreen); // get the relative layout of the homescreen.
        homescreenLayout.setBackground(null); // this should remove all views from the main view to allow us to show the fragment properly.

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.slide_up_in, R.animator.slide_up_out); // set custom animations for this fragment
        fragmentTransaction.replace(R.id.mainContentScreen, historyFragment); // replace the current fragment with our games
        fragmentTransaction.commit(); // commit the fragment to be loaded.
        closeDrawer(); // close the drawer automatically.
        fragmentOpen = true; // set the boolean to be true that a fragment is indeed open.
        currentFragment = historyFragment; // set the current fragment
        createNewGameButton.setVisibility(View.GONE); // make the new game button go away.
    }

    protected void openMessagesFragment()
    {
        // todo: needs to be implemented.
        closeDrawer(); // close the drawer automatically.
        fragmentOpen = true; // set the boolean to be true that a fragment is indeed open.
        // todo: set current fragment.
    }

    // opens up the settings fragment
    protected void openSettingsFragment()
    {
        RelativeLayout homescreenLayout = (RelativeLayout) findViewById(R.id.mainContentScreen); // get the relative layout of the homescreen.
        homescreenLayout.setBackground(null); // this should remove all views from the main view to allow us to show the fragment properly.

        SettingsFragment settingsUI = new SettingsFragment(); // holds a copy of our settingsUI.
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.slide_up_in, R.animator.slide_up_out); // set custom animations for this fragment
        fragmentTransaction.replace(R.id.mainContentScreen, settingsUI); // replace the current fragment with our games
        fragmentTransaction.commit(); // commit the fragment to be loaded.
        updateNotificationsCount(); // update the notifications as soon as something is pressed.
        closeDrawer(); // close the drawer automatically.
        fragmentOpen = true; // set the boolean to be true that a fragment is indeed open.
        currentFragment = settingsUI; // set the current fragment.
        createNewGameButton.setVisibility(View.GONE);
    }

    // opens the help fragment.
    protected void openHelpFragment()
    {
        RelativeLayout homescreenLayout = (RelativeLayout) findViewById(R.id.mainContentScreen); // get the relative layout of the homescreen.
        homescreenLayout.setBackground(null); // this should remove all views from the main view to allow us to show the fragment properly.

        HelpFragment helpUI = new HelpFragment();

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.slide_up_in, R.animator.slide_up_out); // set custom animations for this fragment
        fragmentTransaction.replace(R.id.mainContentScreen, helpUI); // replace the current fragment with our games
        fragmentTransaction.commit(); // commit the fragment to be loaded.
        updateNotificationsCount(); // update the notifications as soon as something is pressed.
        closeDrawer(); // close the drawer automatically.
        fragmentOpen = true; // set the boolean to be true that a fragment is indeed open.
        currentFragment = helpUI;
        createNewGameButton.setVisibility(View.GONE);
    }

    // opens the current user's profile fragment which should also show the user's current information which is very important.
    protected void openCurrentUserProfileFragment()
    {
        RelativeLayout homescreenLayout = (RelativeLayout) findViewById(R.id.mainContentScreen); // get the relative layout of the homescreen.
        homescreenLayout.setBackground(null); // this should remove all views from the main view to allow us to show the fragment properly.

        CurrentUserProfileFragment currUserProfile = new CurrentUserProfileFragment(); // a copy of the CurrentUserProfileFragment
        currUserProfile.setHomescreenActivity(this); // set the homescreen activity for the fragment to use.
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.slide_up_in, R.animator.slide_up_out); // set custom animations for this fragment
        fragmentTransaction.replace(R.id.mainContentScreen, currUserProfile); // replace the homescreenActivity with the CurrentUserProfile
        fragmentTransaction.commit(); // commit the fragment to be loaded.
        closeDrawer(); // close the drawer automatically.
        fragmentOpen = true; // set the boolean to be true that a fragment is indeed open.
        currentFragment = currUserProfile;
        createNewGameButton.setVisibility(View.GONE);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        RelativeLayout homescreenLayout = (RelativeLayout) findViewById(R.id.mainContentScreen);

        int id = item.getItemId();

        if(id == R.id.homescreenGames)
        {
            onBackPressed(); // simulate a back button press
            // BreadCrumb: this is a hack that just closes the current fragment showing the mainscreen. A little slight of hand if you will.
            if(fragmentOpen == true && currentFragment != null) // close the fragment, set fragment open to false.
            {
                System.out.println("trying to end the fragment that is currently open");
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.animator.slide_up_out, R.animator.slide_up_out); // set the slide down animation for when a user hits the back button.
                transaction.remove(currentFragment); // remove the current fragment
                transaction.commit(); // make the changes to remove the fragment
                createNewGameButton.setVisibility(View.VISIBLE);
            }

        }
        else if (id == R.id.notifications)
        {
            openNotificationsFragment();
        }
        else if (id == R.id.History) // todo: call the history fragment once we have history to be displayed.
        {
            openHistoryFragment(); // opens the history fragment.
        }
        else if (id == R.id.messages) // todo: call the messages fragment if we decide to do a chat feature.
        {
            Snackbar.make(homescreenLayout, "This feature isn't available yet ¯\\_(ツ)_/¯ ", Snackbar.LENGTH_LONG).show();
        }
        else if (id == R.id.settings)
        {
            openSettingsFragment();
        }
        else if(id == R.id.help)
        {
            openHelpFragment();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}