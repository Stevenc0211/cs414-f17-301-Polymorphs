package polymorphs.a301.f17.cs414.thexgame.AppBackend;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import polymorphs.a301.f17.cs414.thexgame.persistence.GameRecordListObserver;

/**
 * Created by athai on 10/18/17. edited and modified by Steven.
 */

public class Profile implements GameRecordListObserver {

    // arraylist holding all games a player has played, will be returned to be viewed in a profile
    private HashMap<String,GameRecord> gamesHistory;
    private String nickname;
    double winRatio =0.0;



    public Profile(String nickname){
        this.nickname = nickname;
    }


    public double getWinRatio()
    {
        return winRatio;
    }

    public String getWinRatioUI()
    {
        return " "+winRatio+" ";
    }

    public HashMap<String, GameRecord> getGamesHistory()
    {
        return gamesHistory;
    }

    public HashMap<String,GameRecord> getGamesHistoryUI()
    {
       return gamesHistory;
    }

    public void setWinRatio(List<Integer>wins, List<Integer>losses )
    {
        //todo @Miles need to return the wins and losses for a player from the database
        int totalWins=0;
        int totalLosses=0;
        int totalGames=0;

        for (int w : wins) {
            totalWins+=w;
        }

        for (int l : losses) {
            totalLosses+=l;
        }
        totalGames = totalWins+totalLosses;
        winRatio = totalWins/totalGames;


    }

    @Override
    public void recordAdded(GameRecord addedRecord, String precedingRecordKey) {
        gamesHistory.put(precedingRecordKey,addedRecord);
    }

    @Override
    public void recordChanged(GameRecord changedRecord, String precedingRecordKey) {
        gamesHistory.put(precedingRecordKey,changedRecord);
    }

    @Override
    public void recordRemoved(GameRecord removedRecord) {
        String rmKey = "";
        for (String key : gamesHistory.keySet()) {
            if (gamesHistory.get(key).equals(gamesHistory)) {
                rmKey = key;
                break;
            }
        }
        if (rmKey != "") {
            gamesHistory.remove(rmKey);
        }
    }

    public void setGamesHistory(HashMap<String,GameRecord> history)
    {
        gamesHistory.putAll(history);
    }






}