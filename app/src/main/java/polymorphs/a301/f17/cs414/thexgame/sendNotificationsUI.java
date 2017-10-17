package polymorphs.a301.f17.cs414.thexgame;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by steve-0 on 10/17/17.
 */

public class sendNotificationsUI extends Fragment {

    // TODO: miles you will want to send the data base stuff here you will want to send in the list from the data base
    ArrayList<String> dataBase = new ArrayList<String>();
    InviteListAdapter inviteListAdapter; // the adapter that will populate the invite list.

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments(); // get the arguments sent in rom the list.

        dataBase = args.getStringArrayList("dataBase"); // the items that were sent in from the list.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View sendNotificationsUI = inflater.inflate(R.layout.notifications, container, false);

        // the send button that we need to be working with.
        Button send = (Button) sendNotificationsUI.findViewById(R.id.sendButton);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO: Sends invitaions to users selected from the list
            }
        });


        ListView inviteList = (ListView) sendNotificationsUI.findViewById(R.id.inviteList);
        // TODO: Miles you want to send in the arraylist of strings here which populates the invite list.
        inviteListAdapter = new InviteListAdapter(sendNotificationsUI.getContext(), R.layout.item, dataBase);
        inviteList.setAdapter(inviteListAdapter);
        inviteListAdapter.notifyDataSetChanged(); // tell the list that the items have updated.



        return sendNotificationsUI;
    }
}





