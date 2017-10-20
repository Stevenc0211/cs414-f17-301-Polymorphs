package polymorphs.a301.f17.cs414.thexgame.ui;



import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

import polymorphs.a301.f17.cs414.thexgame.R;
import polymorphs.a301.f17.cs414.thexgame.AppBackend.User;
import polymorphs.a301.f17.cs414.thexgame.persistence.DBIOCore;
import polymorphs.a301.f17.cs414.thexgame.persistence.UserListener;
import polymorphs.a301.f17.cs414.thexgame.persistence.UsernameListListener;

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
    private SettingsUI settingsUI = new SettingsUI(); // holds a copy of our settingsUI.
    private User currentUser; // this is our main user which is created as soon as they start the app.
    private Button submitButton; // holds a copy of the submit button so we can terminate the button once the view is gone.
    UsernameListListener usernameListener;

    private SubmitButtonClickListener submitClickListener;

    // These will be populated by the shared preferences.
    private String email; // email of the user.
    private String name; // name of the user.
    private String username; // username of the user

    // this method is in charge of starting the fragment for the user to be in charge of setting everything up correctly!!!
    protected void createInGameUIFragment() {
        Bundle fragmentArgs = new Bundle(); // the Bundle here allows us to send arguments to our fragment!
        // we should pull items from the data base including all of the users current games.
        // we should then send the information to the games here by attaching the games to the user which is pretty important!

        ArrayList<String> people = usernameListener.getUsernameList(); // get the list of usernames

        currentGames.add("Game " + currentGames.size() + 1); // creates a new game with the name i.e. Game 2 (where the 2 will increase each time!)
        fragmentArgs.putStringArrayList("currentGames", currentGames); // send in the list of current games under the name of "currentGames"
        fragmentArgs.putBoolean("Start new game", true); // tells the InGameUI that the user wants to create a new game
        fragmentArgs.putBoolean("Open current games", false); // tells the InGameUI that the user just wants to open the list of current games.
        fragmentArgs.putStringArrayList("usernames", people); // send this in for the ingame ui to be populated with the list of users.

        inGameUI.setArguments(fragmentArgs); // set the arguments of the fragment.

        RelativeLayout homescreenLayout = (RelativeLayout) findViewById(R.id.homescreenLayout); // get the relative layout of the homescreen.
        homescreenLayout.removeAllViews();
        homescreenLayout.setBackground(null); // this should remove all views from the main view to allow us to show the fragment properly.

        FragmentTransaction transaction = getFragmentManager().beginTransaction(); // get the Fragment transaction to allow us to display the fragment properly
        transaction.replace(R.id.homescreenLayout, inGameUI); // replace the current fragment with our games
        transaction.commit(); // commit the fragment to be loaded.
    }

    // This method has code very similar to createInGameUIFragment, this one is not creating a new game however, this will simply pull from the database and show the current games of the user.
    protected void openCurrentGamesFragment() {
        Bundle fragmentArgs = new Bundle(); // the Bundle here allows us to send arguments to our fragment!
        // we should pull items from the data base including all of the users current games.
        // we should then send the information to the games here by attaching the games to the user which is pretty important!

        // TODO: we should pull from the database again here and update the arraylist currentGames so we are making sure that the games we are updating is accurate. Very important!

        fragmentArgs.putStringArrayList("currentGames", currentGames); // send in the list of current games under the name of "currentGames"
        fragmentArgs.putBoolean("Start new game", false); // tells the InGameUI that the user wants to create a new game
        fragmentArgs.putBoolean("Open current games", true); // tells the InGameUI that the user just wants to open the list of current games.

        inGameUI.setArguments(fragmentArgs); // set the arguments of the fragment.

        RelativeLayout homescreenLayout = (RelativeLayout) findViewById(R.id.homescreenLayout); // get the relative layout of the homescreen.
        homescreenLayout.removeAllViews();
        homescreenLayout.setBackground(null); // this should remove all views from the main view to allow us to show the fragment properly.

        FragmentTransaction transaction = getFragmentManager().beginTransaction(); // get the Fragment transaction to allow us to display the fragment properly
        transaction.replace(R.id.homescreenLayout, inGameUI); // replace the current fragment with our games
        transaction.commit(); // commit the fragment to be loaded.
    }

    // This method opens up the notifications fragment
    protected void openNotificationsFragment() {
        Bundle fragmentArgs = new Bundle(); // the Bundle here allows us to send arguments to our fragment!
        // we should pull items from the data base including all of the users current games.
        // we should then send the information to the games here by attaching the games to the user which is pretty important!

        // TODO: we should pull data from the database to get the users notifications. We also need to update the notifications counter as well!!

        inGameUI.setArguments(fragmentArgs); // set the arguments of the fragment.

        RelativeLayout homescreenLayout = (RelativeLayout) findViewById(R.id.homescreenLayout); // get the relative layout of the homescreen.
        homescreenLayout.removeAllViews();
        homescreenLayout.setBackground(null); // this should remove all views from the main view to allow us to show the fragment properly.

        FragmentTransaction transaction = getFragmentManager().beginTransaction(); // get the Fragment transaction to allow us to display the fragment properly
        transaction.replace(R.id.homescreenLayout, notificationsUI); // replace the current fragment with our games
        transaction.commit(); // commit the fragment to be loaded.
    }

    // This method opens the settings fragment.
    protected void openSettingsFragment() {
        Bundle fragmentArgs = new Bundle(); // the Bundle here allows us to send arguments to our fragment!

        inGameUI.setArguments(fragmentArgs); // set the arguments of the fragment.

        RelativeLayout homescreenLayout = (RelativeLayout) findViewById(R.id.homescreenLayout); // get the relative layout of the homescreen.
        homescreenLayout.removeAllViews();
        homescreenLayout.setBackground(null); // this should remove all views from the main view to allow us to show the fragment properly.

        FragmentTransaction transaction = getFragmentManager().beginTransaction(); // get the Fragment transaction to allow us to display the fragment properly
        transaction.replace(R.id.homescreenLayout, settingsUI); // replace the current fragment with our games
        transaction.commit(); // commit the fragment to be loaded.
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
    protected void createMainGameUI()
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

            // TODO: @Miles, right here we are able to set the information into the database FINALLY! I don't know why it's so hard to move data out of listeners lol.
            DBIOCore.setCurrentUser(name, email, username); // adds a user to the database @Miles, if you haven't already, look in the DBIOcore to see the changes and comments I have made.

        }
        else
        {
            preferences.edit().putBoolean("userCreated", true).commit(); // IGNORE THIS!
        }

        history = (ListView) findViewById(R.id.HistoryList); // holds the list of the history for the users to be able to work with!!

        histAdapter = new HistoryListAdapter(this, R.layout.game_item, gameHistory); // send in the game history for the game adapter!!

        // todo: be sure to remove this, this is just populating some items for us to test and see how the game is working
        for (int i = 0; i < 15; i++) {
            gameHistory.add("Game " + i); // simply add 15 game items for the users to be able to see
        }

        history.setAdapter(histAdapter); // set the adapter that we are working with for the history of the app itself.
        history.setDivider(null); // says that we don't want any divisors.
        history.setDividerHeight(10); // set's our divisor (granted invisible ones) to be about 10 spaces apart (roughly 10 pixels)
        histAdapter.notifyDataSetChanged(); // tell the adapter that the data has changed in the listview.

        Button createGameButton = (Button) findViewById(R.id.createGameButton); // grab the button we are working with.
        createGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createInGameUIFragment(); // have the InGameUI created in here!!
            }
        });


        Button currentGameButton = (Button) findViewById(R.id.currentGamesButton); // grab our button here.
        currentGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openCurrentGamesFragment(); // have the fragment open and show the current games to the user! Very important!

                // TODO: we can also pull from the database here and update current games if we wanted.
            }
        });


        Button notificationsButton = (Button) findViewById(R.id.NotificationsButton); // grab our notifications button.
        notificationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openNotificationsFragment(); // open up the notifications fragment!
            }
        });

        Button settingsButton = (Button) findViewById(R.id.SettingsButton); // grab our settings button.
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openSettingsFragment(); // opens the settings fragment!

                // some of the features inside the settings fragment do not work yet and will be updated when some more of the main features of the game have been implemented.
            }
        });
    }

    // This is the first thing called when this class is called and will create the startup screen UI.
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        // we are WRITING to main memory here. This is putting out boolean in so that the user never has to create a new username again.
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        //preferences.edit().putBoolean("usernameCreated", true).commit();

        super.onCreate(savedInstanceState);

        usernameListener = new UsernameListListener();
        DBIOCore.registerToUsernameList(usernameListener);

        Intent thisIntent = getIntent(); // grab the intent sent from the StartupScreen class.
        Bundle args = thisIntent.getBundleExtra("args");
        name = args.getString("GoogleDisplayName");
        email = args.getString("email");


        if(isUsernameSet() == false)
        {
            preferences.edit().putBoolean("usernameCreated", true).commit();
            preferences.edit().putBoolean("userCreated", true).commit();

            setContentView(R.layout.setusername); // set's the username layout for us to be able to use.
            final EditText textField = (EditText) findViewById(R.id.usernameField); // grab the edit text username field.
            final Button submitButton = (Button) findViewById(R.id.submitButton); // grab the submit button.


            submitClickListener = new SubmitButtonClickListener(name, email, textField, MainGameUI.this);
            submitButton.setOnClickListener(submitClickListener);

            System.out.println("name from button: " + submitClickListener.getName());
            System.out.println("name from button: " + submitClickListener.getEmail());
            System.out.println("name from button: " + submitClickListener.getUsername());

            /*
            // Hitting submit will send the username off into the data base for the users to be able to get things. Pretty cool
            submitButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view) {

                    if(textField.getText().length() == 0)
                    {
                        Toast.makeText(getApplicationContext(), "You must enter a username " + ("\ud83d\ude1c"), Toast.LENGTH_SHORT).show(); // display a little message (with silly face) to the user.
                    }
                    else // save the username write it to the DataBase.
                    {
                        username = textField.getText().toString(); // grab the username.

                        //UserOld thisUser = new UserOld(googleDisplayName, email, username);
                        preferences.edit().putString("name", name);
                        preferences.edit().putString("email", email);
                        preferences.edit().putString("username", username);
                        createMainGameUI(); // create the main game ui now.
                    }
                }
            });
            */

        }
        else
        {
            createMainGameUI(); // setup the familiar homescreen layout that we are used to seeing.
        }



    }
}
