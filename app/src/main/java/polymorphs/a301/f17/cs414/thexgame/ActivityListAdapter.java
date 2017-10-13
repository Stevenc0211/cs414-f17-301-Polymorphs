package polymorphs.a301.f17.cs414.thexgame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by thenotoriousrog on 10/13/17.
 *
 * This class is another adapter to allow us to see games in the lists. Nothing special needs to happen in each of the items. They should just be strings.
 */

public class ActivityListAdapter extends ArrayAdapter<String> {

    public ActivityListAdapter(Context context, int resource, List<String> events)
    {
        super(context, resource, events);
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
            v = vi.inflate(R.layout.activity_item, null);
        }

        String eventItem = getItem(position); // grab the game string that we are working with.

        // if we really wanted we could do things with this here. We could also send in list of objects not just string. Right we now we are not going to do anything.

        return v; // return the view to the ListView.
    }

}
