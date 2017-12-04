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
    private String endDate;
    private int won; // 1 if won, -1 if lost, 0 if tied

    public GameRecord(String player,String opponent,int won){
        this.player = player;
        this.opponent = opponent;
        endDate = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        setWon(won);
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

    public void setWon(int won){
        if (won > 0) {
            this.won = 1;
        } else if (won < 0) {
            this.won = -1;
        } else {
            this.won = 0;
        }
    }

    public int getWon(){
        return won;
    }

    public boolean equals(Object o) {
        if (!(o instanceof GameRecord)) return false;
        GameRecord otherRecord = (GameRecord) o;
        return (player.equals(otherRecord.player) && opponent.equals(otherRecord.opponent) && endDate.equals(otherRecord.endDate));
    }

    public String toString(){
        return player + "-" + opponent + "-" + endDate + "-" + won;
    }
}
