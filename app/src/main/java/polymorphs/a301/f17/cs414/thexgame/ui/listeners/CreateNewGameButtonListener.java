package polymorphs.a301.f17.cs414.thexgame.ui.listeners;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;

import polymorphs.a301.f17.cs414.thexgame.AppBackend.User;
import polymorphs.a301.f17.cs414.thexgame.R;
import polymorphs.a301.f17.cs414.thexgame.persistence.UserObserver;
import polymorphs.a301.f17.cs414.thexgame.persistence.UsernameListObserver;
import polymorphs.a301.f17.cs414.thexgame.ui.activities.HomescreenActivity;
import polymorphs.a301.f17.cs414.thexgame.ui.activities.SendInvitationsActivity;

/**
 * Created by thenotoriousrog on 10/26/17.
 */

public class CreateNewGameButtonListener implements View.OnClickListener, UserObserver, UsernameListObserver {

    private HomescreenActivity homescreenActivity;
    HashMap<String, String> usernames;
    User currentUser;

    public CreateNewGameButtonListener(HomescreenActivity homescreenActivity)
    {
        this.homescreenActivity = homescreenActivity;
    }

    // Pops up a dialog box and asks if users want to send invites, upon yes, inflate the send_invitations.xml
    protected void askToSendInvites()
    {
        //final SendInvitationsActivity sendNotificationsUI = new SendInvitationsActivity(); // a copy of the SendInvitationsActivity we want to inflate.
        final Dialog invitePlayersDialog = new Dialog(homescreenActivity); // creates the dialog asking users if they want to invite other users to the game or not.
        invitePlayersDialog.setContentView(R.layout.invite_players_dialog); // set the dialog that we want the users to see.
        invitePlayersDialog.setTitle("Do you want to invite players?");
        invitePlayersDialog.show(); // show the dialog asking if players want to send invite to players or not.
        Button yesButton = (Button) invitePlayersDialog.findViewById(R.id.dialogYesButton); // get the yes button for the dialog
        yesButton.setOnClickListener(new View.OnClickListener()
        {
            // Upon click, the dialog will close and the SendInvitationsActivity is inflated.
            @Override
            public void onClick(View view) {

                invitePlayersDialog.dismiss(); // close the invitePlayersDialog box.

                Intent sendInvitesIntent = new Intent(homescreenActivity, SendInvitationsActivity.class);
//                Bundle args = new Bundle(); // bundle to send to SendInvitationsActivity
//                ArrayList<String> users = new ArrayList<>( usernames.values() );
//                args.putStringArrayList("usernames", users);
//                args.putString("currentUser", currentUser.getNickname());
//                sendInvitesIntent.putExtra("args", args); // put the bundle into the intent to be grabbed.


                homescreenActivity.startActivity(sendInvitesIntent); // start the activity.
            }
        });

        Button noButton = (Button) invitePlayersDialog.findViewById(R.id.dialogNoButton); // get the no button for the dialog.
        noButton.setOnClickListener(new View.OnClickListener()
        {
            // Upon click, simply close the dialog box.
            @Override
            public void onClick(View view) {
                invitePlayersDialog.dismiss(); // close the invitePlayersDialog box.
            }
        });

    }

    @Override
    public void onClick(View view)
    {
        askToSendInvites();
    }

    @Override
    public void userUpdated(User u) {
        currentUser = u;
    }

    @Override
    public void usernameAdded(String addedUsername, String precedingUsernameKey) {
        usernames.put(precedingUsernameKey, addedUsername);
    }

    @Override
    public void usernameChanged(String changedUsername, String precedingUsernameKey) {
        usernames.put(precedingUsernameKey, changedUsername);
    }

    @Override
    public void usernameRemoved(String removedUsername) {
        String rmKey = "";
        for (String key : usernames.keySet()) {
            if (usernames.get(key).equals(removedUsername)) {
                rmKey = key;
                break;
            }
        }
        usernames.remove(rmKey);
    }
}
