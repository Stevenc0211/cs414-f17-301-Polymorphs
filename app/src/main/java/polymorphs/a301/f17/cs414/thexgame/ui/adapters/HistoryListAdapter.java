package polymorphs.a301.f17.cs414.thexgame.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import polymorphs.a301.f17.cs414.thexgame.AppBackend.GameRecord;
import polymorphs.a301.f17.cs414.thexgame.R;

/**
 * Created by thenotoriousrog on 10/12/17.
 *
 * This is the adapter for the history adapter. It basically holds items and reports what is in it to the ListView
 */

public class HistoryListAdapter extends ArrayAdapter<GameRecord> {

    public HistoryListAdapter(Context context, int resource, List<GameRecord> games){
        super(context, resource, games);
    }

    // NOTE: if you really wanted to, we can also send in a copy of the homescreen activity if we need to. Miles may not like it, but I want it working first and foremost.

    // This populates each of the items in the ListView and controls things such as click actions and what not.
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v = convertView; // view that we are working with.

        if(v == null)
        {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.game_item, null);
        }

        GameRecord gameItem = getItem(position); // grab the game record that we want to work with right here.

        TextView gameTitle = (TextView) v.findViewById(R.id.gameID); // the game title we want to work with
        // todo: @Andy set the title, if you can reliably, set the game number as well to match exactly as I have it in history_item.xml
        // andy, you will do gameTitle.setText to change the text.

        TextView gameDate = (TextView) v.findViewById(R.id.gameDate); // the game title we want to work with
        // TODO: @Andy you will want to set the game date here, you may configure the date any way that you wish.

        TextView gameWinner = (TextView) v.findViewById(R.id.gameWinner); // the game title we want to work with
        // TODO: @Andy, here is where you will set the winner of the game.

        TextView gameLoser = (TextView) v.findViewById(R.id.gameLoser); // the game title we want to work with
        // TODO: @Andy, here is where you will set the game loser.

        return v; // return the view to the ListView.
    }
}
