package polymorphs.a301.f17.cs414.thexgame.AppBackend;

/**
 * Created by athai on 11/3/17.
 */

public class GameSnapshot {
    private String gameString;
    private String nicknameWhite;
    private String nicknameBlack;
    private String dbKey;

    public GameSnapshot(Game game) {
        gameString = game.toString();
        nicknameWhite = game.getP1Nickname();
        nicknameBlack = game.getP2Nickname();
    }

    public GameSnapshot(){}

    public String getDbKey(){ return dbKey;}

    public void setDbKey(String dbKey) {
        this.dbKey = dbKey;
    }

    public String getGameString() {
        return gameString;
    }

    public String getNicknameBlack(){
        return nicknameBlack;
    }

    public String getNicknameWhite(){
        return nicknameWhite;
    }
}
