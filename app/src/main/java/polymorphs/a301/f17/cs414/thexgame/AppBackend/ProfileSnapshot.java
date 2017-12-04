package polymorphs.a301.f17.cs414.thexgame.AppBackend;

/**
 * Created by Andy on 12/2/2017.
 */

public class ProfileSnapshot {

    private String profileString;
    private String nickname;
    private double winRatio;
    private String dbKey;

    public ProfileSnapshot(Profile profile){
        profileString = profile.getGamesHistory().toString();
        nickname = profile.getNickname();
        winRatio = profile.getWinRatio();
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

    public String getProfileString(){
        return profileString;
    }

    public String getDbKey(){
        return dbKey;
    }
}
