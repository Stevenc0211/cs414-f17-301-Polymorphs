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

    public String parseDateAndTime(String date)
    {
        boolean inPM = false; // tells the method that we are in PM and should append the string with PM
        date = date.replace(".", "/"); // replace all of the periods with slashes.
        String[] parsed = date.split("/"); // split by slashes.

        String toDate = parsed[1] + "/" + parsed[2] + "/" +parsed[0]; // i.e. 12/5/2017

        int hour = Integer.parseInt(parsed[3]); // gets the hour.

        if(hour == 12)
        {
            inPM = true; // tell the app that we are in the PM
        }
        else if(hour > 12)
        {
            inPM = true; // tell the app that we are in the PM
            hour -= 12; // subtract by 12, we are in the PM
        }

        String hourString = String.valueOf(hour); // convert the correctly switched hour back to a string.

        String toReturn = "";
        if(inPM == false)
        {
            toReturn = toDate + " " + hourString + ":" + parsed[4] + ":" + parsed[5] + " AM"; // string to return set in AM
        }
        else {
            toReturn = toDate + " " + hourString + ":" + parsed[4] + ":" + parsed[5] + " PM"; // string to return set in PM
        }

        return toReturn;
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
            v = vi.inflate(R.layout.history_item, null);
        }

        GameRecord gameItem = getItem(position); // grab the game record that we want to work with right here.

        TextView gameTitle = (TextView) v.findViewById(R.id.gameID); // the game title we want to work with
        gameTitle.setText(gameItem.getPlayer() + " vs " + gameItem.getOpponent());

        TextView gameDate = (TextView) v.findViewById(R.id.gameDate); // the game title we want to work with
        String date = parseDateAndTime(gameItem.getEndDate()); // parse the date that the game ended fixing up the string to be more user friendly.
        gameDate.setText(date); // set the date.

        String winner = "";
        String loser = "";
        boolean tie = false; // tells the adapter if the game resulted in a tie or not.

        if(gameItem.getWon() == 1) // player won
        {
            winner = gameItem.getPlayer();
            loser = gameItem.getOpponent();
        }
        else if(gameItem.getWon() == -1) // player lost.
        {
            winner = gameItem.getOpponent();
            loser = gameItem.getPlayer();
        }
        else // game was a tie.
        {
            tie = true;
        }


        TextView gameWinnerText = (TextView) v.findViewById(R.id.gameWinnerText); // the game winner text, text view.
        TextView gameWinner = (TextView) v.findViewById(R.id.gameWinner); // the game winner text view.
        TextView gameLoser = (TextView) v.findViewById(R.id.gameLoser); // the game loser text view
        TextView gameLoserText = (TextView) v.findViewById(R.id.gameLoserText); // the game loser text, text view.
        TextView gameTie = (TextView) v.findViewById(R.id.tie); // the game tie text view.

        if(tie == false)
        {
            gameTie.setVisibility(View.GONE); // make the tie text invisible.

            // make all winner/loser text views visible.
            gameWinnerText.setVisibility(View.VISIBLE);
            gameWinner.setVisibility(View.VISIBLE);
            gameLoserText.setVisibility(View.VISIBLE);
            gameLoser.setVisibility(View.VISIBLE);

            gameWinner.setText(winner); // set winner.
            gameLoser.setText(loser); // set loser.
        }
        else { // a game resulted in a tie, we should only display the tie text.

            gameTie.setVisibility(View.VISIBLE); // make only the tie text appear.

            // set all winner/loser text views gone.
            gameWinnerText.setVisibility(View.GONE);
            gameWinner.setVisibility(View.GONE);
            gameLoser.setVisibility(View.GONE);
            gameLoserText.setVisibility(View.GONE);
        }


        return v; // return the view to the ListView.
    }
}
