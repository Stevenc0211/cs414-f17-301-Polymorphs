package polymorphs.a301.f17.cs414.thexgame;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

/**
 * Created by Roger Hannagan on 9/19/17.
 *
 * This class is responsible for the main game UI. This is what we are going to update and mess with the most until we have a nice UI to work with. Updates will be applied here first.
 */

public class MainGameUI extends Activity {

    private GridView chessboard; // holds the chess board that we are going to be working on.

    // This is the first thing called when this class is called and will create the startup screen UI.
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_game_ui);

        chessboard = (GridView) findViewById(R.id.chessboard); // find the chess board that we want to be working with.
        SquareAdapter squareAdapter = new SquareAdapter(getApplicationContext());
        chessboard.setAdapter(squareAdapter);
        squareAdapter.notifyDataSetChanged(); // tell the square adapter to update the dataset to show the correct items in the gridview.
    }

}
