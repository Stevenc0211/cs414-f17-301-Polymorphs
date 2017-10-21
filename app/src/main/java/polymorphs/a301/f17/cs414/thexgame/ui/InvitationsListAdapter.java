package polymorphs.a301.f17.cs414.thexgame.ui;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import polymorphs.a301.f17.cs414.thexgame.Invitation;
import polymorphs.a301.f17.cs414.thexgame.R;
import polymorphs.a301.f17.cs414.thexgame.persistence.DBIOCore;

/**
 * Created by thenotoriousrog on 10/13/17.
 *
 * WARNING: this adapter is not referring to any of the adapters used in the Invitation class nor the adapters in the inviteusers UI.
 */

public class InvitationsListAdapter extends ArrayAdapter {

    private MainGameUI primaryActivity;

    public InvitationsListAdapter(Context context, int resource, ArrayList<Invitation> invitations, MainGameUI primaryActivity)
    {
        super(context, resource, invitations); // todo: again we would likely want to create our own Inviations class
        this.primaryActivity = primaryActivity;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        View v = convertView; // view that we are working with.

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.invitation_item, null); // this is what expands the items in the list
        }

        TextView text = v.findViewById(R.id.invitationMessage);
        String invitingUser = ((Invitation)getItem(position)).getInvitingUser();
        text.setText(invitingUser +  " invites you to a game of Chad");
        text.setTextSize(20);

        Button acceptButton = (Button) v.findViewById(R.id.acceptButton); // get the accept button that we want to work with!
        acceptButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                // TODO: after clicking accept, this item will be removed from the list and will also take the user into another game! Important to get that thing working correctly!!!
                DBIOCore.removeInvite((Invitation)getItem(position));
                primaryActivity.openCurrentGamesFragment();
            }
        });

        Button declineButton = (Button) v.findViewById(R.id.declineButton); // get the decline button.
        declineButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                DBIOCore.removeInvite((Invitation)getItem(position));

            }
        });

        return v;
    }
}
