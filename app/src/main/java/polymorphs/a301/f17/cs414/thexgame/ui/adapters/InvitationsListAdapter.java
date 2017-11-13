package polymorphs.a301.f17.cs414.thexgame.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import polymorphs.a301.f17.cs414.thexgame.AppBackend.Invite;
import polymorphs.a301.f17.cs414.thexgame.R;
import polymorphs.a301.f17.cs414.thexgame.persistence.DBIOCore;
import polymorphs.a301.f17.cs414.thexgame.ui.activities.HomescreenActivity;

/**
 * Created by thenotoriousrog on 10/13/17.
 *
 * WARNING: this adapter is not referring to any of the adapters used in the Invite class nor the adapters in the inviteusers UI.
 */

public class InvitationsListAdapter extends ArrayAdapter {

    private HomescreenActivity primaryActivity;

    public InvitationsListAdapter(Context context, int resource, ArrayList<Invite> invitations, HomescreenActivity primaryActivity)
    {
        super(context, resource, invitations);
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
        String invitingUser = ((Invite)getItem(position)).getInvitingUser();
        text.setText(invitingUser +  " invites you to a game of Chad");
        text.setTextSize(20);

        Button acceptButton = (Button) v.findViewById(R.id.acceptButton); // get the accept button that we want to work with!
        acceptButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                DBIOCore.getInstance().removeInvite((Invite)getItem(position));
                primaryActivity.createNewGame((Invite)getItem(position));
            }
        });

        Button declineButton = (Button) v.findViewById(R.id.declineButton); // get the decline button.
        declineButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                DBIOCore.getInstance().removeInvite((Invite)getItem(position));

            }
        });

        return v;
    }
}
