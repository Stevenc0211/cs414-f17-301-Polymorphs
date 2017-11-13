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

    @Override
    public void onClick(View view)
    {
        Intent sendInvitesIntent = new Intent(homescreenActivity, SendInvitationsActivity.class);
        homescreenActivity.startActivity(sendInvitesIntent); // start the activity.
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
