package polymorphs.a301.f17.cs414.thexgame.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import polymorphs.a301.f17.cs414.thexgame.AppBackend.GameRecord;
import polymorphs.a301.f17.cs414.thexgame.R;
import polymorphs.a301.f17.cs414.thexgame.persistence.DBIOCore;
import polymorphs.a301.f17.cs414.thexgame.persistence.GameRecordListObserver;
import polymorphs.a301.f17.cs414.thexgame.ui.adapters.HistoryListAdapter;

/**
 * Created by thenotoriousrog on 11/28/17.
 * Displays the history items in the fragment.
 */
public class HistoryFragment extends Fragment implements GameRecordListObserver {


    private ListView historyList; // holds the history list view that we want to be working with.
    private HistoryListAdapter historyAdapter; // holds the history adapter.


    // This is the first method that is called when the fragment is initialized.
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        DBIOCore.getInstance().registerToGameRecordList(this); // register this to the game record list. chea.
    }

    // sets up the history view.
    private void setupHistoryListView(View historyView)
    {
        historyList = (ListView) historyView.findViewById(R.id.historyListView);

        // tODO: @Andy look at notificationsfragment you need to create a new arraylist and send in the gamerecord list just like invitations list needs to and it should work.
        // also andy, you may want to send in a copy of the history fragment in here, so yeah haha. May want to do that.
        historyAdapter = new HistoryListAdapter(historyView.getContext(), R.layout.history_item, insertHere)
    }

    // This is what populates the actual fragment layout.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View historyView = inflater.inflate(R.layout.history, container, false); // setup the actual game history.
        setupHistoryListView(historyView); // sets up the history view of the fragment.

        return historyView; // return the view to be used.
    }


    @Override
    public void recordAdded(GameRecord addedRecord, String precedingRecordKey) {
        // todo: @Andy, implement this per favore.
    }

    @Override
    public void recordChanged(GameRecord changedRecord, String precedingRecordKey) {
        // todo: @Andy, implement this per favore.
    }

    @Override
    public void recordRemoved(GameRecord removedRecord) {
        // todo: @Andy, implement this per favore.
    }
}
