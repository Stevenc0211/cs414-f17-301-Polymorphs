package polymorphs.a301.f17.cs414.thexgame;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.widget.GridView;

/**
 * Created by thenotoriousrog on 10/13/17.
 *
 * This will control what happens when the user decides to swipe horizontally on one of the games. The reason for the class is because we are now able to update some of the items in the InGameFragment such as
 * activities as well as being able to update the items on the board and keeping the code a little cleaner. Look in the methods and the comments below to understand what is happening here.
 */

public class GamePageChangeListener implements ViewPager.OnPageChangeListener {

    private InGameUI inGameUI; // holds a copy of the ingameui to allow the snackbar to have a view of what game they are on for the user to be able to work on which will be awesome looking!!
    private GridView game; // holds a copy of the game that the user is working on which is pretty important!!!

    public GamePageChangeListener(InGameUI inGame)
    {
        inGameUI = inGame; // sets a copy of the game that we are working with. Mainly used for the Snackbar!
        game = inGame.getActivity().findViewById(R.id.chessboard); // grabs the chessboard that we are working with.
    }

    // do not pay attention to this.
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // not implemented, this is listening for the pager to be started to be changed.
    }


    // This is the real important thing here. This method will listen for when the user switches a page. When they do, we will display a little snack bar showing the game number to the user!
    @Override
    public void onPageSelected(int position)
    {
        Snackbar.make(inGameUI.getView(), "GAME " + (position+1), Snackbar.LENGTH_SHORT).show(); // show the snackbar plus the game for the users to see, this is actually pretty cool!!! You'll see
    }


    // This tells the android ui when the state of thew view pager changes. Not useful for us likely at all for our app.
    @Override
    public void onPageScrollStateChanged(int state) {} // nothing for this user.
}
