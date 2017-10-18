package polymorphs.a301.f17.cs414.thexgame;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

/**
 * Created by steve-0 on 10/15/17.
 */

public class SettingsUI extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View settingUI = inflater.inflate(R.layout.notifications, container, false);

        Button signout = (Button) settingUI.findViewById(R.id.signOut);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO: signout button Roger
            }
        });


        Button deleteUnregister = (Button) settingUI.findViewById(R.id.delUnreg);
        deleteUnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO: unregister the user from the database
            }
        });

        Switch invertColors = (Switch) settingUI.findViewById(R.id.invertC);
        deleteUnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO: Invert the colors
            }
        });

        Switch winLossRatio = (Switch) settingUI.findViewById(R.id.winLossR);
            winLossRatio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO: This will either turn on or off the win Ratio on the screen
            }
        });




    return settingUI;
    }






}
