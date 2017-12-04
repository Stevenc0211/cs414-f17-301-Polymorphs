package polymorphs.a301.f17.cs414.thexgame.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import polymorphs.a301.f17.cs414.thexgame.AppBackend.GameRecord;
import polymorphs.a301.f17.cs414.thexgame.R;
import polymorphs.a301.f17.cs414.thexgame.persistence.DBIOCore;
import polymorphs.a301.f17.cs414.thexgame.persistence.GameRecordListObserver;
import polymorphs.a301.f17.cs414.thexgame.ui.activities.HomescreenActivity;
import polymorphs.a301.f17.cs414.thexgame.ui.adapters.HistoryListAdapter;

/**
 * Created by thenotoriousrog on 11/28/17.
 * Displays the history items in the fragment.
 */
public class HistoryFragment extends Fragment {


    private ListView historyList; // holds the history list view that we want to be working with.
    private HistoryListAdapter historyAdapter; // holds the history adapter.

    private ArrayList<GameRecord> gameRecords; // holds the list of gamerecords for the list. Will be automatically populated by Firebase database.

    // This is the first method that is called when the fragment is initialized.
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        HomescreenActivity h = (HomescreenActivity) getActivity();
        gameRecords = h.getGameRecords();
    }

    // sets up the history view.
    private void setupHistoryListView(View historyView)
    {
        historyList = (ListView) historyView.findViewById(R.id.historyListView);
        historyAdapter = new HistoryListAdapter(historyView.getContext(), R.layout.history_item, gameRecords);
        historyList.setAdapter(historyAdapter);
        historyAdapter.notifyDataSetChanged();
        historyList.setDividerHeight(10);
    }

    // This is what populates the actual fragment layout.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View historyView = inflater.inflate(R.layout.history, container, false); // setup the actual game history.
        setupHistoryListView(historyView); // sets up the history view of the fragment.

        return historyView; // return the view to be used.
    }
}
