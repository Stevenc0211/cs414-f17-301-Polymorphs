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
 * Created by thenotoriousrog on 10/13/17.
 *
 * This class is another adapter like the others.
 */

public class InvitationsListAdapter extends ArrayAdapter {

    public InvitationsListAdapter(Context context, int resource, ArrayList<String> invitations)
    {
        super(context, resource, invitations); // todo: again we would likely want to create our own Inviations class
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

        Button acceptButton = (Button) v.findViewById(R.id.acceptButton); // get the accept button that we want to work with!
        acceptButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                // TODO: after clicking accept, this item will be removed from the list and will also take the user into another game! Important to get that thing working correctly!!!

                Toast.makeText(getContext(), "This feature is not ready yet!", Toast.LENGTH_SHORT).show();
            }
        });

        Button declineButton = (Button) v.findViewById(R.id.declineButton); // get the decline button.
        declineButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                // TODO: we want to remove the item from the list after clicking this button!

                Toast.makeText(getContext(), "This feature is not ready yet!", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }
}
