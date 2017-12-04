package polymorphs.a301.f17.cs414.thexgame.ui.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import polymorphs.a301.f17.cs414.thexgame.ui.BoardUI;

/**
 * Created by thenotoriousrog on 10/13/17.
 *
 * This controls the setup for the pager adapter for the game. This will allows us to swipe horizontally and switch between games for the users to be able to play.
 */

public class GamePagerAdapter extends PagerAdapter {

    private ArrayList<BoardUI> games; // this arraylist holds the GridView of the games that we are working with here.
    private View inGameUI;

    private ArrayList<View> views = new ArrayList<View>(); // views to be added to the GamePager
    private Context fromContext;

    public GamePagerAdapter(ArrayList<BoardUI> gamesList, View ui, Context context) {
        games = gamesList;
        inGameUI = ui;
        fromContext = context;
    }


    // returns the number of games.
    @Override
    public int getCount() {
        return games.size();
    }

    // Note: that "Object" refers to the page.
    @Override
    public int getItemPosition(Object object)
    {
        int index = views.indexOf(object);

        if(index == -1) {
            return POSITION_NONE;
        }
        else {
            return index;
        }
    }

    // This method sets up the GridView for the game for when the user swipes to the next game.
    // All of the user's game info should be created at a time for the user to be able to switch between games effectively.
    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        BoardUI game = (BoardUI) games.get(position);
        container.addView(game);
        return game; // return the instantiated game.
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return "Game " + position; // should return something like "Game 12"
    }

    // this view will let us destroy the view. This is what we need to use when a game is finished. This is how you remove from the ViewPager. You cannot delete the game without this method!
    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        container.removeView((BoardUI) object); // remove this view completely.
    }
}
