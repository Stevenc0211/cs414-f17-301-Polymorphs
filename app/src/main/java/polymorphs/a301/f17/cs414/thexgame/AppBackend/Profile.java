package polymorphs.a301.f17.cs414.thexgame.AppBackend;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by athai on 10/18/17. edited and modified by Steven.
 */

public class Profile {

    // arraylist holding all games a player has played, will be returned to be viewed in a profile
    ArrayList<String> gamesHistory = new ArrayList<String>();
    private String nickname;
    double winRatio =0.0;


    public Profile(String nickname){
        this.nickname = nickname;
    }

    public String getNickname()
    {
        return nickname;
    }

    public double getWinRatio()
    {
        return winRatio;
    }

    public String getWinRatioUI()
    {
        return " "+winRatio+" ";
    }

    public ArrayList<String> getGamesHistory()
    {
        return gamesHistory;
    }

    public String getGamesHistoryUI()
    {
        String gameHistoryList="";

        for(String h : gamesHistory)
        {
            gameHistoryList+=h;
            gameHistoryList+="\n";
        }
        return gameHistoryList;
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

    public void setGamesHistory(List<String> history)
    {
        for (String h : history) {
            gamesHistory.add(h);
        }

    }






}