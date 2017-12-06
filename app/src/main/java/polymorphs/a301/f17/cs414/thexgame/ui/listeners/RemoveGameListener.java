package polymorphs.a301.f17.cs414.thexgame.ui.listeners;

import android.provider.Settings;
import android.view.View;

import polymorphs.a301.f17.cs414.thexgame.ui.activities.HomescreenActivity;

/**
 * Created by thenotoriousrog on 12/5/17.
 * This class is in charge of taking in and listening for the user to click the remove game button within the snackbar itself. This is will let the user remove the game if they so choose.
 * Note: swiping away the snackbar will not remove the game, only if the user actually hits this button.
 */

public class RemoveGameListener implements View.OnClickListener {

    private HomescreenActivity homescreenActivity; // a copy of the homescreen activity.

    // constructor takes in a copy of the game listener and allows the game to be removed from the view pager
    public RemoveGameListener(HomescreenActivity homescreenActivity)
    {
        this.homescreenActivity = homescreenActivity;
    }

    // when clicked calls the homescreen activity to remove the current game out of the view pager and refreshes the UI.
    @Override
    public void onClick(View view)
    {
        System.out.println("remove was clicked");
        homescreenActivity.removeCurrentGame(); // removes the current game.
    }
}
