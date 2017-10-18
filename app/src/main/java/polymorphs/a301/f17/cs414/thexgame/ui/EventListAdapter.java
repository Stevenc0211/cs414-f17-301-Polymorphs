package polymorphs.a301.f17.cs414.thexgame.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import polymorphs.a301.f17.cs414.thexgame.R;

/**
 * Created by thenotoriousrog on 10/13/17.
 *
 * This is the for the EventList which simply holds strings, so not too difficult.
 */

public class EventListAdapter extends ArrayAdapter {

    public EventListAdapter(Context context, int resource, ArrayList<String> events)
    {
        super(context, resource, events);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v = convertView; // view that we are working with.

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.event_item, null); // this is what expands the items in the list
        }

        return v;
    }

}
