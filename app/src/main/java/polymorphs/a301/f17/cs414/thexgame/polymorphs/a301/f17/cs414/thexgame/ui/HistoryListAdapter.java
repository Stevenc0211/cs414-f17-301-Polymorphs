package polymorphs.a301.f17.cs414.thexgame.polymorphs.a301.f17.cs414.thexgame.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import polymorphs.a301.f17.cs414.thexgame.R;

/**
 * Created by thenotoriousrog on 10/12/17.
 *
 * This is the adapter for the history adapter. It basically holds items and reports what is in it to the ListView
 */

public class HistoryListAdapter extends ArrayAdapter<String> {

    public HistoryListAdapter(Context context, int resource, List<String> games){
        super(context, resource, games);
    }

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

        String gameItem = getItem(position); // grab the game string that we are working with.

        // if we really wanted we could do things with this here. We could also send in list of objects not just string. Right we now we are not going to do anything.

        return v; // return the view to the ListView.
    }
}
