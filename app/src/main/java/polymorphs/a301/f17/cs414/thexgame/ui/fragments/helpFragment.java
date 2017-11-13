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

        String helpText = "hello!"+"\n"+"To play chad chess there are a couple of things you want to know:"+"\n\n"+"1) the area surrounding the pieces are the castle walls, your piece must"+"\n"+"make it into the opponents castle to be able to attack their pieces.\nApart from this situation pieces simply block one another."
                +"\n\n2) The King is confined to his 3x3 area within the castle walls.\nHe may go and capture enemy pieces inside his castle using either \nthe King's move or the Knight's move in traditional chess."
                +"\n\n3) A Rook moves just like in traditional chess. If it makes it in\nthe enemies castle wall, the Rook is then promoted to Queen.\nThe Queen moves like the Queen in Chess, moves unhindered by castles and walls."
                +"\n\n4) Lastly, just like in traditional chess there is check and checkmate, check\nmeaning the king must be moved to keep from being captured and has the ability\nto move.Checkmate is if a king has been put into check and he can't move because\nopponent pieces can move directly to him to capture him, the Game is over";
        textView.setText(helpText);

        return helpUI;
    }

}
