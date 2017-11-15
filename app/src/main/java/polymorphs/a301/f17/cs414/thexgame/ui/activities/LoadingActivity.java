package polymorphs.a301.f17.cs414.thexgame.ui.activities;

import android.app.Activity;
import android.os.Bundle;

import polymorphs.a301.f17.cs414.thexgame.R;

/**
 * Created by thenotoriousrog on 11/14/17.
 * All this class does is display a blank screen to load up a blank activity before that of homescreen, which is very important.
 */

public class LoadingActivity extends Activity {

    // onCreate simply makes a call to a blank activity which will allow us to show something while the app is loading but that is all that I have for now until something better is up.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_screen);
    }

}
