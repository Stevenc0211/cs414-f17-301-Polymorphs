package polymorphs.a301.f17.cs414.thexgame.AppBackend;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by athai on 11/3/17.
 */

public class GameRecord {
    private String player;
    private String opponent;
    private String startDate;
    private String endDate;
    private boolean won;
    private String dbKey;

    public GameRecord(String player,String opponent,boolean won){
        this.player = player;
        this.opponent = opponent;
        endDate = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        this.won = won;
    }

    public void setPlayer(String player){
        this.player = player;
    }

    public String getPlayer(){
        return player;
    }

    public void setOpponent(String opponent){
        this.opponent = opponent;
    }

    public String getOpponent(){
        return opponent;
    }

    public void setEndDate(String endDate){
        this.endDate = endDate;
    }

    public String getEndDate(){
        return endDate;
    }

    public void setWon(boolean won){
        this.won = won;
    }

    public boolean getWon(){
        return won;
    }

    public void setDbKeyKey(String key) {
        dbKey = key;
    }

    public String getDbKey() {
        return dbKey;
    }

    public boolean equals(Object o) {
        if (!(o instanceof GameRecord)) return false;
        GameRecord otherRecord = (GameRecord) o;
        return (player.equals(otherRecord.player) && opponent.equals(otherRecord.opponent) && startDate.equals(otherRecord.startDate) && endDate.equals(otherRecord.endDate));
    }
}
