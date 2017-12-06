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
import android.util.Base64;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import polymorphs.a301.f17.cs414.thexgame.AppBackend.Driver;
import polymorphs.a301.f17.cs414.thexgame.AppBackend.GameRecord;
import polymorphs.a301.f17.cs414.thexgame.AppBackend.GameSnapshot;
import polymorphs.a301.f17.cs414.thexgame.AppBackend.Profile;
import polymorphs.a301.f17.cs414.thexgame.AppBackend.ProfileSnapshot;
import polymorphs.a301.f17.cs414.thexgame.AppBackend.User;
import polymorphs.a301.f17.cs414.thexgame.persistence.GameRecordListObserver;
import polymorphs.a301.f17.cs414.thexgame.persistence.GameSnapshotListObserver;
import polymorphs.a301.f17.cs414.thexgame.persistence.ProfileSnapshotObserver;
import polymorphs.a301.f17.cs414.thexgame.ui.BoardUI;
import polymorphs.a301.f17.cs414.thexgame.R;
import polymorphs.a301.f17.cs414.thexgame.persistence.DBIOCore;
import polymorphs.a301.f17.cs414.thexgame.persistence.UserObserver;
import polymorphs.a301.f17.cs414.thexgame.persistence.UsernameListObserver;
import polymorphs.a301.f17.cs414.thexgame.ui.adapters.GamePagerAdapter;
import polymorphs.a301.f17.cs414.thexgame.ui.fragments.CurrentUserProfileFragment;
import polymorphs.a301.f17.cs414.thexgame.ui.fragments.HelpFragment;
import polymorphs.a301.f17.cs414.thexgame.ui.fragments.HistoryFragment;
import polymorphs.a301.f17.cs414.thexgame.ui.fragments.NotificationsFragment;
import polymorphs.a301.f17.cs414.thexgame.ui.fragments.SettingsFragment;
import polymorphs.a301.f17.cs414.thexgame.ui.listeners.CreateNewGameButtonListener;
import polymorphs.a301.f17.cs414.thexgame.ui.listeners.GamePageChangeListener;

public class HomescreenActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, UsernameListObserver, UserObserver, GameSnapshotListObserver, GameRecordListObserver, ProfileSnapshotObserver {

    private NotificationsFragment notificationsFragment = new NotificationsFragment(); // a copy of the notifications UI that should be built for the user.
    private HistoryFragment historyFragment = new HistoryFragment(); // a copy of the history fragment here.
    private ViewPager gamePager; // this holds the game view pager which essentially is a list of horizontal list items of the game, which is pretty awesome!
    private GamePagerAdapter gamePagerAdapter; // holds the gamePagerAdapter that we need to be working on here.
    private BoardUI boardUI; // holds the chess board that we are going to be working on.
    private ArrayList<BoardUI> games = new ArrayList<>(); // holds all of our games, mainly there boards, hence why we are containing GridViews
    private View inGameUI; // holds the in game ui view which is needed to create another board for users to be able to play games on.

    ArrayList<GameRecord> gameRecords = new ArrayList<>(); // holds all of the game records to be populated from the database.
    private String gameID = ""; // the game id used for each game.

    private Driver driver; // the driver that we will be working with within homescreen activity.
    private boolean fragmentOpen = false; // tells the app if a fragment is open, if it is, then we need to ensure that we are fixing things
    private Fragment currentFragment; // holds the current fragment thats open.
    private FloatingActionButton createNewGameButton; // a copy of the create new game button that will allow us to
    private TextView playerTurnText; // holds the text of the current player's turn.
    private ImageView playerTurnProfPic; // holds the profile picture of the turn of the current player.

    private String email; // email of the user.
    private String name; // name of the user.
    private String username; // username of the user
    private Bitmap userProfilePic; // holds a copy of a user's profile picture.
    private Profile userProfile; // the profile of the current user.

    HashMap<String, String> usernames; // holds the list of people to invite keyed by the previous usernames database key
    polymorphs.a301.f17.cs414.thexgame.AppBackend.User currentUser;

    private NavigationView navigationView; // a copy of the navigation view to populate our board layout.

    // decodes a base64string into a bitmap to allow for the user to get their own profile.
    public Bitmap decodeBase64String(Profile profile)
    {
        Bitmap pic = null; // returns the bitmap of the picture that we want to be using.
        byte[] picDecod = Base64.decode(profile.getPicString(), Base64.DEFAULT);
        pic = BitmapFactory.decodeByteArray(picDecod,0,picDecod.length); // convert the base64 into a string.
        return pic; // return the bitmap to the calling function.
    }

    public void saveAndChangeProfilePic(Bitmap newProfilePic)
    {
        userProfilePic = newProfilePic; // set the new profile picture for the user.
        setupHeader(); // have the header refresh with the new profile picture now set.

        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        newProfilePic.compress(Bitmap.CompressFormat.PNG, 100, byteArray); // compress the bitmap to a png form.
        byte[] bArray = byteArray.toByteArray();

        String profPicEncoded = Base64.encodeToString(bArray, Base64.DEFAULT); // convert the profile to a Base64 string. This is what needs to be saved into database.
        userProfile.setPicString(profPicEncoded);
        DBIOCore.getInstance().updateProfileSnapshot(new ProfileSnapshot(userProfile));
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

    // returns the user's profile.
    public Profile getCurrentUserProfile()
    {
        return userProfile;
    }

    // Simply updates the view pager that we are working with.
    public void updateViewPager()
    {
        gamePager.invalidate();
    }


    // When this is called it will remove the game at whatever position the view pager is on
    public void removeCurrentGame()
    {
        int p = gamePager.getCurrentItem();
        driver.removeCurrentGame(); // This triggers snapshotRemoved below
        //games.get(p).invalidate(); // invalidate the board.
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

    public void showIndicators()
    {
        playerTurnText.setVisibility(View.VISIBLE);
        playerTurnProfPic.setVisibility(View.VISIBLE);
    }

    public void removeIndicators()
    {
        playerTurnText.setVisibility(View.GONE); // make the player turn text gone.
        playerTurnProfPic.setVisibility(View.GONE); // make the profile picture gone.
    }

    // sets the game that was switched within the view pager.
    public void switchToGameAt(int position) {

        try { // attempt to remove the game.

            Driver.getInstance().setCurrentGameKey( games.get(position).getGameID() );
            games.get(position).setTurnIndicator();

        }catch (IllegalArgumentException ex) { // if this exception occurs. We need to not do anything and just remove the games.

            removeIndicators(); // removes all of the indicators hiding them from the view.

        }

    }

    // gets a game a specific position
    public BoardUI getGameAt(int position)
    {
        return games.get(position);
    }

    // returns the number of games.
    public int getNumOfGames()
    {
        return games.size();
    }

    // this resets the game pager with a new set of elements. This is called whenever a game is removed from the view pager.
    private void resetGamePager()
    {
        gamePagerAdapter = new GamePagerAdapter(games, inGameUI, getBaseContext()); // send in the games that we want to work with that will allow us to send our games to the adapter to update the ViewPager (to swipe horizontally)
        GamePageChangeListener gpcl = new GamePageChangeListener(this); // holds the game as well as a copy of the InGameUI that will allow us to see a snack bar for the users to be able to see their game number.

        gamePager.setAdapter(gamePagerAdapter);
        gamePagerAdapter.notifyDataSetChanged(); // update the number of games in the list view pretty important!
        gamePager.addOnPageChangeListener(gpcl);
        gamePager.invalidate();
        if (games.size() > 0) {
            switchToGameAt(0); // the UI switches back to the first game, tell the DBIOcore to do the same.
        } else {
            RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.mainContentScreen);
            TextView noGame = relativeLayout.findViewById(R.id.noGamesText);
            noGame.setVisibility(View.VISIBLE); // make the text field appear
            gamePager.setVisibility(View.GONE); // have the games disappear.
            gamePager.invalidate();
        }

    }

    // this method sets up our game pager.
    protected void setupGamePager()
    {
//        createNewGame("razor", "black");
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


    // displays the profile picture and the text of the person's turn
    public void changeTurnIndicator(Profile playerTurnProfile, Bitmap profilePic)
    {

        if(currentUser.getNickname().equals(playerTurnProfile.getNickname())) // if its the user's turn tell them its their turn.
        {
            // tell them its their turn.
            playerTurnText.setText("It's YOUR turn!");
            playerTurnProfPic.setImageBitmap(userProfilePic); // the current user's profile picture.
        }
        else // it's not their turn tell them that it's the other player's turn.
        {
            playerTurnText.setText("It's " + playerTurnProfile.getNickname() + "'s" + " turn!");
            if (profilePic != null) {
                playerTurnProfPic.setImageBitmap(profilePic); // set the default pic for the other player.
            }
        }
    }

    public void changeTurnIndicator(String nickname)
    {
        if(currentUser.getNickname().equals(nickname)) {
            playerTurnText.setText("It's YOUR turn!");
        } else {
            playerTurnText.setText("It's " + nickname + "'s" + " turn!");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);
        usernames = new HashMap<>();

        driver = Driver.getInstance();

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        displayHomescreen(); // setup the familiar homescreen layout that we are used to seeing.

        // add items into the database.
        DBIOCore.getInstance().registerToGameSnapshotList(this);
        DBIOCore.getInstance().registerToUsernameList(this);
        DBIOCore.getInstance().registerToCurrentUser(this);
        DBIOCore.getInstance().registerToGameRecordList(this);
        DBIOCore.getInstance().registerCurrentUserProfileSnapshot(this); // this doesn't work I cannot get it to work like I wanted it to.

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

        // setup the text views for the games.
        playerTurnText = (TextView) findViewById(R.id.playerTurnText); // textview for displaying the user's current turn.
        playerTurnProfPic = (ImageView) findViewById(R.id.turnProfilePic); // the imageview for the player's current turn.

        // setup floating action button.
        createNewGameButton = (FloatingActionButton) findViewById(R.id.createNewGameButton);
        CreateNewGameButtonListener newGameButtonListener = new CreateNewGameButtonListener(HomescreenActivity.this);
        createNewGameButton.setOnClickListener(newGameButtonListener);

        // setup the drawer layout.
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
        showIndicators(); // display the indicators on the application
        gamePagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void snapshotChanged(GameSnapshot changedSnapshot, String precedingSnapshotKey)
    {
        System.out.println("There was a change in the gamesnapshot with players" + changedSnapshot.getNicknameWhite() + " and " + changedSnapshot.getNicknameBlack());
    }

    @Override
    public void snapshotRemoved(GameSnapshot removedSnapshot) {
        int currPos = gamePager.getCurrentItem(); // get the item for the view of the position
        games.remove(currPos); // remove the game from the list of games.
        resetGamePager();  // resets the game game pager with the new information.
    }

    @Override
    public void recordAdded(GameRecord addedRecord, String precedingRecordKey) {
        System.out.println("This game record was added to the list! -> " + addedRecord);
        gameRecords.add(addedRecord); // add the game to the game record.
    }

    @Override
    public void recordChanged(GameRecord changedRecord, String precedingRecordKey) {}

    @Override
    public void recordRemoved(GameRecord removedRecord) {}

    // for profile snapshot
    @Override
    public void snapshotUpdated(ProfileSnapshot u) {

        if (userProfile == null) {
            this.userProfile = new Profile(DBIOCore.getInstance().getCurrentUserUsername());
            userProfile.setWinRatio();
        }
        this.userProfile.updateFromSnapshot(u);
        if (!userProfile.getPicString().equals("")) {
            byte[] picDecod = Base64.decode(userProfile.getPicString(), Base64.DEFAULT);
            userProfilePic = BitmapFactory.decodeByteArray(picDecod,0,picDecod.length);
            setupHeader();
            userProfile.setWinRatio();
        }
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

        if (id == R.id.quit) // if user presses the quit button.
        {
            // TODO: the code to have the game quit is just below. It is removed because we couldn't get it working properly before the presentation.
            //driver.quitGame(currentUser.getNickname()); // call quit on this game. Have this user quit.

            RelativeLayout homescreenLayout = (RelativeLayout) findViewById(R.id.mainContentScreen); // get the relative layout of the homescreen.
            Snackbar.make(homescreenLayout, "This feature isn't available yet ¯\\_(ツ)_/¯ ", Snackbar.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public ArrayList<GameRecord> getGameRecords()
    {
        return gameRecords;
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

        Bundle args = new Bundle(); // holds the arguments that we want to send to the fragment.
        args.putSerializable("gameRecords", gameRecords); // sends all of the game records to the history fragment.
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.slide_up_in, R.animator.slide_up_out); // set custom animations for this fragment
        fragmentTransaction.replace(R.id.mainContentScreen, historyFragment); // replace the current fragment with our games
        fragmentTransaction.commit(); // commit the fragment to be loaded.
        closeDrawer(); // close the drawer automatically.
        fragmentOpen = true; // set the boolean to be true that a fragment is indeed open.
        currentFragment = historyFragment; // set the current fragment
        createNewGameButton.setVisibility(View.GONE); // make the new game button go away.
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
        currUserProfile.setCurrUserProfilePic(userProfilePic); // set the profile picture for the fragment.
        currUserProfile.setCurrUserProfile(userProfile); // set the profile for the fragment.
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
        else if (id == R.id.History)
        {
            openHistoryFragment(); // opens the history fragment.
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