package polymorphs.a301.f17.cs414.thexgame.AppBackend;

import java.util.HashMap;

import polymorphs.a301.f17.cs414.thexgame.persistence.DBIOCore;
import polymorphs.a301.f17.cs414.thexgame.persistence.GameRecordListObserver;

/**
 * Created by athai on 10/18/17. edited and modified by Steven.
 */

public class Profile implements GameRecordListObserver {

    // arraylist holding all games a player has played, will be returned to be viewed in a profile
    private HashMap<String,GameRecord> gamesHistory;
    private String nickname;
    double winRatio =0.0;
    private String picString = "";


    public Profile() {} // empty constructor needed by database.

    public Profile(String nickname){
        this.nickname = nickname;
        gamesHistory = new HashMap<>();
        DBIOCore.getInstance().registerToGameRecordList(this);
    }

    public void setPicString(String pic) {
        picString = pic;
    }

    public String getPicString() {
        return picString;
    }

    public String getNickname(){
        return nickname;
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    public double getWinRatio()
    {
        return winRatio;
    }


    HashMap<String, GameRecord> getGamesHistory()
    {
        return gamesHistory;
    }


    public void setWinRatio()
    {

        int totalWins=0;
        int totalGames= gamesHistory.size();
        for (GameRecord record : gamesHistory.values()) {
            if (record.getWon() == 1) {
                totalWins++;
            } else if (record.getWon() == 0) {
                totalGames--; // discount ties
            }
        }
        if(totalGames == 0) // the player has no wins, therefore has no win percentage.
        {
            winRatio = 0;
        }
        else {
            winRatio = (double)totalWins/totalGames; // calculate win percentage.
        }



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
            if (gamesHistory.get(key).equals(removedRecord)) {
                rmKey = key;
                break;
            }
        }
        if (!rmKey.equals("")) {
            gamesHistory.remove(rmKey);
        }
    }


    public String toString(){
        return nickname + "-" + winRatio + "-" + gamesHistory.toString();
    }

    //update Profile from Snapshot--------------need to do profile picture
    public void updateFromSnapshot(ProfileSnapshot snapshot){

        //Set the nickname
        nickname = snapshot.getNickname();
        //Set the win ratio
        winRatio = snapshot.getWinRatio();

        picString = snapshot.getPicString();

        //Split profile string
        String temp = snapshot.getHistString();
        //remove brackets from first and last index
        String record = temp.substring(1,temp.length()-1);
        //remove white spaces
        record = record.replaceAll("\\s","");
        String [] part = record.split(",");
        if (!record.equals("")) {
            for(int i = 0; i < part.length; i++){
                String [] keyValue = part[i].split("=");
                String key = keyValue[0];
                String [] rec = keyValue[1].split("!");
                //Create Game Record
                GameRecord game = new GameRecord(rec[0],rec[1],Integer.parseInt(rec[3]));
                //Update the with correct timestamp
                game.setEndDate(rec[2]);
                //Store game record in gamesHistory
                gamesHistory.put(key,game);
            }
        }
    }

    public boolean equals(Object o) {
        if (!(o instanceof Profile)) return false;
        Profile otherProfile = (Profile) o;
        return (nickname == otherProfile.getNickname() && winRatio == otherProfile.getWinRatio() && gamesHistory.size() == otherProfile.getGamesHistory().size());
    }
}