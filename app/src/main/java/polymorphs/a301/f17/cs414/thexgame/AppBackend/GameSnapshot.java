package polymorphs.a301.f17.cs414.thexgame.AppBackend;

/**
 * Created by athai on 11/3/17.
 */

public class GameSnapshot {
    private String gameString;
    private String nicknameWhite;
    private String nicknameBlack;

    public GameSnapshot(Game game){
        gameString = game.toString();
        nicknameWhite = game.getNicknameWhite();
        nicknameBlack = game.getNicknameBlack();
    }

    public GameSnapshot() {} // for Firebase loads

    public String getGameString() {
        return gameString;
    }

    public String getNicknameBlack() {
        return nicknameBlack;
    }

    public String getNicknameWhite() {
        return  nicknameWhite;
    }
}
