package polymorphs.a301.f17.cs414.thexgame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by steve-0 on 10/17/17.
 */

public class InviteListAdapter extends ArrayAdapter<String> {

    public InviteListAdapter(Context context, int resource, List<String> games){
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

        String item = getItem(position); // grab the game string that we are working with.

        // if we really wanted we could do things with this here. We could also send in list of objects not just string. Right we now we are not going to do anything.

        return v; // return the view to the ListView.
    }

}
