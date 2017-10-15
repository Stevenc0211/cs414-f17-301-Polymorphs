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
 *
 */

public class UsersToAddAdapterAdapter extends ArrayAdapter<String> {

    public UsersToAddAdapterAdapter(Context context, int resource, ArrayList<String> usersToAdd)
    {
        super(context, resource, usersToAdd); // todo: again we would likely want to create our own Inviations class
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v = convertView; // view that we are working with.

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.invitation_item, null); // this is what expands the items in the list
        }

        // when a name is clicked in this list, we have to remove them from the list. Pretty important!

        // if the send button is hit we need to send in the list of code that will send off the invites!

        return v;
    }

}
