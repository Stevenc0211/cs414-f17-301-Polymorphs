package polymorphs.a301.f17.cs414.thexgame.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import polymorphs.a301.f17.cs414.thexgame.R;

/**
 * Created by thenotoriousrog on 10/13/17.
 *
 * This class is in charge of starting the fragment of current games for the user to be able to show the games to the lists which is pretty important. This is a fragment because fragments can be built and
 * destroyed relatively easily and we want this to be built and destroyed relativly easy which is very important for us to do correctly!
 */

public class InGameUI extends Fragment {

    private ArrayList<String> currentGames; // the list of current games.
    private ViewPager gamePager; // this holds the game view pager which essentially is a list of horizontal list items of the game, which is pretty awesome!
    private GamePagerAdapter gamePagerAdapter; // holds the gamePagerAdapter that we need to be working on here.
    private GridView chessboard; // holds the chess board that we are going to be working on.
    private ArrayList<GridView> games = new ArrayList<>(); // holds all of our games, mainly there boards, hence why we are containing GridViews
    private ArrayList<String> events = new ArrayList<>(); // todo: this should filled out after a call to our database. This will allow us to be able to get events about the game for the user to look at.
    private ActivityListAdapter eventsListAdapter; // the events adapter here for us to update, very important!
    private SquareAdapter squareAdapter; // holds our square adapter which will allow us to be able to work on our lists and update the information for the players to be able to play a game!
    private View inGameUI; // holds the in game ui view which is needed to create another board for users to be able to play games on.
    private boolean startNewGame = false; // tells the inGameUI that we want to start a new game, which involves sending a list of invite(s) to other player(s).
    private boolean openCurrentGames = false; // tells inGameUI to just open the current list of games.

    ArrayList<String> usernames; // holds the list of people to invite.

    // this method sets up our game pager.
    protected void setupGamePager(View gameUIView) {
        // Need to setup the view pager now!
        gamePager = (ViewPager) gameUIView.findViewById(R.id.gamesListPager); // get the game pager that will basically fill out the games!

        // todo: we should have a list of our boards pulled from our database with the information about the piece places. This is pretty important!
        games.add(chessboard); // add once.
        games.add(chessboard); // add twice.

        gamePagerAdapter = new GamePagerAdapter(games, inGameUI); // send in the games that we want to work with that will allow us to send our games to the adapter to update the ViewPager (to swipe horizontally)
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

    // this is the first method reached when the fragment is started. This method should only be used to setting of variables really. onCreateView is used for the more UI element creation and setting.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Team, see how this works? We send in to the fragment and they are grabbed in the onCreate method. Look in MainGameUI for how we make a call to InGameUI and how we send those fragments, pretty crucial to see.
        Bundle args = getArguments(); // get the arguments sent in for the fragment.
        currentGames = args.getStringArrayList("currentGames"); // grab the ArrayList of current games. // todo: perhaps grabbed from database? If not, remove this line.
        startNewGame = args.getBoolean("Start new game"); // grab the boolean argument.
        openCurrentGames = args.getBoolean("Open current games"); // grab the boolean argument.
        usernames = args.getStringArrayList("usernames");
    }

    // Pops up a dialog box and asks if users want to send invites, upon yes, inflate the send_invitations.xml
    protected void askToSendInvites(View gameUIView)
    {
        //final SendNotificationsUI sendNotificationsUI = new SendNotificationsUI(); // a copy of the SendNotificationsUI we want to inflate.
        final Dialog invitePlayersDialog = new Dialog(gameUIView.getContext()); // creates the dialog asking users if they want to invite other users to the game or not.
        invitePlayersDialog.setContentView(R.layout.invite_players_dialog); // set the dialog that we want the users to see.
        invitePlayersDialog.setTitle("Do you want to invite players?");
        invitePlayersDialog.show(); // show the dialog asking if players want to send invite to players or not.

        Button yesButton = (Button) invitePlayersDialog.findViewById(R.id.dialogYesButton); // get the yes button for the dialog
        yesButton.setOnClickListener(new View.OnClickListener()
        {
            // Upon click, the dialog will close and the SendNotificationsUI is inflated.
            @Override
            public void onClick(View view) {

                invitePlayersDialog.dismiss(); // close the invitePlayersDialog box.

                Intent sendInvitesIntent = new Intent(getActivity(), SendNotificationsUI.class);
                Bundle args = new Bundle(); // bundle to send to SendNotificationsUI
                args.putStringArrayList("usernames", usernames);
                sendInvitesIntent.putExtra("args", args); // put the bundle into the intent to be grabbed.


                startActivity(sendInvitesIntent); // start the activity.
            }
        });

        Button noButton = (Button) invitePlayersDialog.findViewById(R.id.dialogNoButton); // get the no button for the dialog.
        noButton.setOnClickListener(new View.OnClickListener()
        {
            // Upon click, simply close the dialog box.
            @Override
            public void onClick(View view) {
                invitePlayersDialog.dismiss(); // close the invitePlayersDialog box.
            }
        });

    }

    // This method is in charge of starting up everything including the main view which is extremely important!
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View gameUIView = inflater.inflate(R.layout.in_game_ui, container, false); // inflate our view of our main game.

        inGameUI = gameUIView; // set out global value to be GameUIView <-- yes this is horrible coding, I will fix it later, I'm tired at the moment haha

        // Create our chessboard which is very important!
        chessboard = (GridView) gameUIView.findViewById(R.id.chessboard); // find the chess board that we want to be working with.
        squareAdapter = new SquareAdapter(getActivity().getApplicationContext());
        chessboard.setAdapter(squareAdapter);
        squareAdapter.notifyDataSetChanged(); // tell the square adapter to update the dataset to show the correct items in the gridview.

        setupGamePager(gameUIView); // setup our game pager, pretty important.

        // TODO: remove this once we have events coming from the database. This is just to fill the event listview with some arbitrary data for now!
        for (int i = 0; i < 15; i++) {
            events.add("Event to notify...");
        }

        setupActivityListView(gameUIView); // setup the activity list for users to get news on what is happening with the game! TODO: this also should be shown in real time!

        TextView userVSOpponent = (TextView) gameUIView.findViewById(R.id.playersField); // get the text with the user and the player.
        // TODO: We should pull the data from the data base and update the userVSOpponent text field which is very, very important so the user knows who the person they are fighting is. It is crucial.!

        Button quitButton = gameUIView.findViewById(R.id.quitButton); // grab our quit button
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // The user should be presented a dialog box asking if the user wants to quit.

                // after saying yet, the user will receive a loss for the game.

                // the game will then be removed from the GamePager (may need to actually send this in as an argument for us to be able to remove this from an option as needed)

                Toast.makeText(inGameUI.getContext(), "This feature is not ready yet!", Toast.LENGTH_SHORT).show(); // to show the user that this feature is not ready yet.
            }
        });

        if(startNewGame == true)
        {
            askToSendInvites(gameUIView); // make a call that will ask if a users wants to send invites and then will inflate the new fragment
        }
        else {} // do nothing, simply open up the current games.

        return gameUIView; // return the view that we are working with.
    }

}
