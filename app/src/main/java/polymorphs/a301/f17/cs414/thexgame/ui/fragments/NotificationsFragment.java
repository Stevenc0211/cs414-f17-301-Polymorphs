package polymorphs.a301.f17.cs414.thexgame.ui.fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

import polymorphs.a301.f17.cs414.thexgame.Invitation;
import polymorphs.a301.f17.cs414.thexgame.R;
import polymorphs.a301.f17.cs414.thexgame.persistence.DBIOCore;
import polymorphs.a301.f17.cs414.thexgame.persistence.InviteListObserver;
import polymorphs.a301.f17.cs414.thexgame.ui.activities.HomescreenActivity;
import polymorphs.a301.f17.cs414.thexgame.ui.adapters.EventListAdapter;
import polymorphs.a301.f17.cs414.thexgame.ui.adapters.InvitationsListAdapter;

/**
 * Created by thenotoriousrog on 10/13/17.
 *
 * This class is in charge of setting up the UI for Notifications when the user clicks the button!
 */

public class NotificationsFragment extends Fragment implements InviteListObserver{

    private HashMap<String, Invitation> invitationsData = new HashMap<>(); // todo: we will likely want to change this to an invitations class. For now I am going to use strings just for us to have a UI up and running!
    private ArrayList<String> eventsData = new ArrayList<>(); // TODO: This needs to be pulled from the database so that users can be able to get information which is pretty important for us to get this working!

    private ListView invitationsList;  // holds the invitations list.
    private ListView eventsList; // holds the events list.
    private EventListAdapter eventListAdapter; // holds the EventListAdapter
    private InvitationsListAdapter invitationsListAdapter; // holds the invitations list adapter.
    private SharedPreferences preferences;
    private HomescreenActivity homescreenActivity;

    // The first method reached when the method is called to be created!
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        homescreenActivity = (HomescreenActivity) getActivity(); // cast a copy of our homescreen activity to be used by this fragment.

        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
        Bundle args = getArguments(); // get the arguments for the fragment activity.
        // TODO: we should grab any arguments that we have sent to the Notifications UI pretty important!!
        DBIOCore.getInstance().registerToCurrentUserInviteList(this);
    }


    // set up the invitations list view.
    protected void setupInvitationsListView(View notificationsView)
    {
        invitationsList = (ListView) notificationsView.findViewById(R.id.invitationsListView); // grab the inviations list.
        invitationsListAdapter = new InvitationsListAdapter(notificationsView.getContext(), R.layout.invitation_item,new ArrayList<>(invitationsData.values()) , (HomescreenActivity) getActivity()); // create the invitations adapter that we need to use.
        invitationsList.setAdapter(invitationsListAdapter);
        invitationsListAdapter.notifyDataSetChanged();
        invitationsList.setDivider(null);
        invitationsList.setDividerHeight(10);
    }

    // This the next method which also creates all of the UI elements.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View notificationsView = inflater.inflate(R.layout.notifications, container, false); // inflate our view of our main game.

        setupInvitationsListView(notificationsView); // setup the inviatations list view.

        return notificationsView; // display the fragment for the users to see.
    }

    /*
        This will write the notifications to main memory and the idea is when the app starts up that person should see a count of there notifications if any exist.
        TODO: @Miles, this is something I'd like to be able to grab out of the database if possible, but I'm doing it this way for us to be able to have something that counts notifications for us.
     */
    private void writeNotificationsCountToMainMemory(int count)
    {
        preferences.edit().putInt("NotificationsCount", count).commit(); // put the notifications count into main memory.
        homescreenActivity.updateNotificationsCount();
    }

    @Override
    public void inviteAdded(Invitation addedInvite, String precedingInviteKey) {
        invitationsListAdapter.add(addedInvite);
        invitationsData.put(precedingInviteKey, addedInvite);
        invitationsListAdapter.notifyDataSetChanged();

        writeNotificationsCountToMainMemory(invitationsListAdapter.getCount()); // write notification count to main memory.
        System.out.println("Notification count = " + invitationsListAdapter.getCount());
    }

    @Override
    public void inviteChanged(Invitation changedInvite, String precedingInviteKey) {
        invitationsListAdapter.remove(invitationsData.get(precedingInviteKey));
        invitationsData.put(precedingInviteKey, changedInvite);
        invitationsListAdapter.add(changedInvite);
        invitationsListAdapter.notifyDataSetChanged();

        writeNotificationsCountToMainMemory(invitationsListAdapter.getCount()); // write notification count to main memory.
        System.out.println("Notification count = " + invitationsListAdapter.getCount());
    }

    @Override
    public void inviteRemoved(Invitation removedInvite) {
        invitationsListAdapter.remove(removedInvite);
        String rmKey = "";
        for (String key : invitationsData.keySet()) {
            if (invitationsData.get(key).equals(removedInvite)) {
                rmKey = key;
                break;
            }
        }
        invitationsData.remove(rmKey);
        invitationsListAdapter.notifyDataSetChanged();

        writeNotificationsCountToMainMemory(invitationsListAdapter.getCount()); // write notification count to main memory
    }
}
