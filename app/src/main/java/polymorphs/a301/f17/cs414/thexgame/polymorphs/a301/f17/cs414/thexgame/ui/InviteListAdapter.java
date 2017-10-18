package polymorphs.a301.f17.cs414.thexgame.polymorphs.a301.f17.cs414.thexgame.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import polymorphs.a301.f17.cs414.thexgame.Invitation;
import polymorphs.a301.f17.cs414.thexgame.R;

/**
 * Created by steve-0 on 10/17/17. Updated and commented by Roger.
 */

public class InviteListAdapter extends ArrayAdapter<Invitation> {

    public InviteListAdapter(Context context, int resource, List<Invitation> invites){
        super(context, resource, invites);
    }

    // This populates each of the items in the ListView and controls things such as click actions and what not.
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v = convertView; // view that we are working with.

        if(v == null)
        {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.item, null); // inflating the xml file called item, which is strictly just a name
        }

        Invitation invite = getItem(position); // grab the game string that we are working with.

        // todo: Team, watch what is happening here, this is how we get the populated list to show the name of the people we want to invite to show up in the list view.
        TextView userToInvite = (TextView) v.findViewById(R.id.item); // notice that the name of the TextView in the .xml file is also called item. Bad practice, but this file is going to be removed anyways.
        userToInvite.setText(invite.getInvitedUser()); // set the text to match that of the users that we want to invite, <-- this is the real important part, we are setting the text to show a list of users.

        // if we really wanted we could do things with this here. We could also send in list of objects not just string. Right we now we are not going to do anything.

        return v; // return the view to the ListView.
    }

}