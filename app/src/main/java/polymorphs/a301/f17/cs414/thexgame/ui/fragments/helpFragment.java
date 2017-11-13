package polymorphs.a301.f17.cs414.thexgame.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import polymorphs.a301.f17.cs414.thexgame.R;

/**
 * Created by steve-0 on 11/12/17.
 */

public class helpFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View helpUI = inflater.inflate(R.layout.help, container, false);
        TextView textView = (TextView) helpUI.findViewById(R.id.helpText);

        String helpText = "text that is helpful";
        textView.setText(helpText);

        return helpUI;
    }

}
