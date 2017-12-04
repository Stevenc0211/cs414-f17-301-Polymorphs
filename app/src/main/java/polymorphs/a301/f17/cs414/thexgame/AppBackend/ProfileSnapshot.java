package polymorphs.a301.f17.cs414.thexgame.AppBackend;

/**
 * Created by Andy on 12/2/2017.
 */

public class ProfileSnapshot {

    private String histString; // this is the image of the snapshot to be converted into a base64 object again.
    private String nickname;
    private double winRatio;
    private String dbKey;
    private String picString;

    /**
     * For Firebase Load ONLY
     */
    public ProfileSnapshot() {}

    public ProfileSnapshot(Profile profile){
        histString = profile.getGamesHistory().toString();
        nickname = profile.getNickname();
        winRatio = profile.getWinRatio();
        picString = profile.getPicString();
    }

    public void setDbKey(String dbKey) {
        this.dbKey = dbKey;
    }

    public String getNickname(){
        return nickname;
    }

    public double getWinRatio(){
        return winRatio;
    }

    public String getHistString(){
        return histString;
    }

    public String getPicString(){
        return picString;
    }

    public String getDbKey(){
        return dbKey;
    }
}
