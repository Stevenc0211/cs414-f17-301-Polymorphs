package polymorphs.a301.f17.cs414.thexgame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by thenotoriousrog on 10/15/17.
 *
 * Allows us to use the ArrayAdapter so that we can correctly show the information of
 * */

public class AvailableUsersListAdapter extends ArrayAdapter<String> {

    // TODO: decide if we need to add other user to the list or not, this is crucial for us to be able to generate the code for this thing to work correctly.
    // TODO: add a copy of our SendInvites class so we can update the usersToAdd driver in or not, or also add in the usersToAdd adapter as we need.

    ArrayList<String> availableUsers; // holds the list of available users
    ArrayList<String> usersToAdd; // hols the list of users to add. Very important.

    public AvailableUsersListAdapter(Context context, int resource, ArrayList<String> availableUsers, ArrayList<String> usersToAdd)
    {
        super(context, resource, availableUsers); // todo: again we would likely want to create our own Inviations class

        // populate the arraylist for us.
        this.availableUsers = availableUsers;
        this.usersToAdd = usersToAdd;

        // TODO: pull in the fragment arguments for us to be able to get the arraylist of users to add so we can show that the item that the user's items can work correctly.
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v = convertView; // view that we are working with.

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.invitation_item, null); // change this, we need to have a custom item that shows user's username only!
        }

        // TODO: add touch events here that will listen for whenever an item is touched, it will populate over to the other side. Really important!

        // todo: add the code that will allow users to search the database (for now just a string array) and will populate the left list that users can click

        return v;
    }
}
