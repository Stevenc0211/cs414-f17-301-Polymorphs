package polymorphs.a301.f17.cs414.thexgame;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by thenotoriousrog on 10/15/17.
 *
 * This fragment will be in control of adding, removing, and sending invites to users that the player wants to use.
 */

public class SendInvitesUI extends Fragment {

    // TODO: we need to have the correct variables here. I am only using simple ones for testing. We want an actual instance of our database object for us to be able to grab data easily.
    // TODO: remember that whatever the user decides to send as the list of users also has to be sent to the InGameUI for us to be able to create the games with the accurate users names and whatnot.

    // these are the junk arraylists here.
    ArrayList<String> totalUsers = new ArrayList<String>(); // holds all of the available list of users.
    ArrayList<String> usersToInvite = new ArrayList<String>(); // this needs to hold a whole bunch of users to be able to add for us to be able to work on.

    ListView availableUsersList; // holds the list of the available players that the user could invite.
    ListView usersToAddList; // holds the list of the players that the user wants to invite.
    FloatingActionButton searchUsersButton; // this is the button used to search and add users to the system.

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // TODO: @Roger need to pull in the arguments here from the list. Part of the arguments is that we need to be able to have an instance of the database so that we can make pulls of the database for the user.

        // TODO: for right now, we want to populate the totalUsers arraylist so that the users can properly get the things that we want to work on. This will allow me to be able to correctly test if things are working will.
    }

    // this method sets up the searchUsersButton and let's us be able to make changes to the system correctly.
    protected void setupSearchUsersButton(View inviteUsersView)
    {
        searchUsersButton = (FloatingActionButton) inviteUsersView.findViewById(R.id.searchUsersButton); // get the floating action button that we are working on.

        // TODO: setup a click listener that will listen for when the item is clicked to search for specific users or search the entire database.

        // NOTE: when searching the database should look for strings of the name itself, that way the user can have a list of users to choose from. If only one matches the name, then just add the name automatically.
    }

    // creates and generates our views.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // TODO: this is required to return a view which is our generated fragment.
        View inviteUsersView = inflater.inflate(R.layout.inviteuser, container, false); // inflate our view of the invite user fragment.

        availableUsersList = (ListView) inviteUsersView.findViewById(R.id.availableUsers); // get the list of available user to add.
        usersToAddList = (ListView) inviteUsersView.findViewById(R.id.addedUsers); // get the listView of usrs to add to. Pretty important!

        // THIS IS NOT COMPLETED A LOT MORE HAS TO BE MADE FOR THIS!!!

        return inviteUsersView; // the fragment that will be inflated.
    }
}
