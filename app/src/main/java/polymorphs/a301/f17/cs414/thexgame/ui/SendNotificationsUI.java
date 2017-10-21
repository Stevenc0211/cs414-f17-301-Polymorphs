package polymorphs.a301.f17.cs414.thexgame.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import polymorphs.a301.f17.cs414.thexgame.Invitation;
import polymorphs.a301.f17.cs414.thexgame.R;
import polymorphs.a301.f17.cs414.thexgame.persistence.DBIOCore;

/**
 * Created by steve-0 on 10/17/17. Updated and commented by Roger.
 * This class is likely to remain but the code in it is going to change when Roger finishes the new way of sending invites out to users.
 * Todo: Miles, Andy, Badr the code you add in here will not be removed. It's crucial that we get the database in here and populating a list of users. This class sends the invites to users.
 */

public class SendNotificationsUI extends Activity  {

    // TODO: miles you will want to send the data base stuff here you will want to send in the list from the data base, i.e. an ArrayList of whatever object. It does have to be an arraylist.
    private ArrayList<Invitation> allInvites = new ArrayList<>(); // list of database items that we are working with.
    private InviteListAdapter inviteListAdapter; // the adapter that will populate the invite list.
    // removed for now --> private final int SEND_INVITES = 4000; // this is the request code for sending invites to players very important.

    // this method simply generates the UI that we created for the SendNotificationsUI.
    protected void setupUI(final ArrayList<Invitation> usersToSendto)
    {
        // the send button that we need to be working with.
        Button send = (Button) findViewById(R.id.sendButton);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int idx = 0; idx < allInvites.size(); idx++) {
                    if (inviteListAdapter.getItem(idx).isSelected()) {
                        DBIOCore.sendInvite(allInvites.get(idx));
                    }
                }

                finish(); // end the Activity.
            }
        });

        // Setup the listview.
        ListView inviteList = (ListView) findViewById(R.id.inviteList);
        inviteListAdapter = new InviteListAdapter(getApplicationContext(), R.layout.item, usersToSendto);
        inviteList.setAdapter(inviteListAdapter);
        inviteListAdapter.notifyDataSetChanged(); // tell the list that the items have updated.
    }

    /*
        This method is called as soon as the activity is started. All variables should be initialized and populated and then the UI elements will be built. See the setupUI method for more.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
       // /Bundle args = getArguments(); // get the arguments sent in rom the list.

        //ArrayList<String> dataBase = new ArrayList<>(); // todo: we will want to get the items from the base
        setContentView(R.layout.send_invitations); // this will display our UI

        Intent sendInvitesIntent = getIntent();
        Bundle args = sendInvitesIntent.getBundleExtra("args");
        String currentUser = args.getString("currentUser");
        ArrayList<String> people = args.getStringArrayList("usernames"); // grab the set of usernames from InGameUI


        for(int i = 0; i < people.size(); i++)
        {
            if (people.get(i).equals(currentUser)) continue;
            Invitation invite = new Invitation(currentUser, people.get(i));
            allInvites.add(invite);
        }

        setupUI(allInvites);
    }
}
