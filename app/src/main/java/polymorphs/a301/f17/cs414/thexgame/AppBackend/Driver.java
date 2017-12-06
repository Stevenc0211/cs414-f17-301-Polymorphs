package polymorphs.a301.f17.cs414.thexgame.AppBackend;

import java.util.ArrayList;
import java.util.HashMap;

import polymorphs.a301.f17.cs414.thexgame.persistence.DBIOCore;
import polymorphs.a301.f17.cs414.thexgame.persistence.GameSnapshotListObserver;
import polymorphs.a301.f17.cs414.thexgame.persistence.UsernameListObserver;

/**
 * Created by athai on 10/18/17.
 */

public final class Driver implements UsernameListObserver,GameSnapshotListObserver  { // will implement GameListObserver
    private HashMap<String, String> usernames;    // the master list of registered users usernames
    private HashMap<String,Game> games;
    private String currentGameKey = "";

    private static Driver instance = null;

    private Driver(){
        this.usernames = new HashMap<>();
        this.games = new HashMap<>();
        DBIOCore.getInstance().registerToUsernameList(this);
        DBIOCore.getInstance().registerToGameSnapshotList(this);
    }

    public static Driver getInstance() {
        if (instance == null) instance = new Driver();
        return instance;
    }

    public boolean isSetup() {
        return usernames.size() != 0;
    }

    public HashMap<String, Game> getGames(){
        return games;
    }

    /**
     * Builds a new game and adds it to the set of current games.
     * @param nickname1 - the inviting user for this game
     * @param nickname2 - the invited user for this game
     * @return the key for the newly created game
     * @throws IllegalArgumentException - if either passed user are not registered with the system
     */
    public String createGame(String nickname1,String nickname2) throws IllegalArgumentException{
        //must check if both players are registered
        if(isRegistered(nickname1) && isRegistered(nickname2)) {
            Game game = new Game(nickname1, nickname2);
            GameSnapshot snapshot = new GameSnapshot(game);
            String key = DBIOCore.getInstance().addGameSnapshot(snapshot);
            games.put(key,game);
            return key;
        }
        else{
            throw new IllegalArgumentException("ERROR: both passed users must be registered");
        }
    }


    /**
     * Sets the active game of the set of active games.
     * @param key - the key of the game to set as active
     * @throws IllegalArgumentException - if the passed index is out of bounds, i.e. greater or equal to the number of active games
     */
    public void setCurrentGameKey(String key) throws IllegalArgumentException {
        if (!games.containsKey(key)) {
            throw new IllegalArgumentException("ERROR: given index is out of bounds");
        }
        currentGameKey = key;
    }

    /**
     * Removes the current game from the database. Will set the currentGameKey to null!!! Make sure
     * to use setCurrentGameKey directly after this call
     */
    public void removeCurrentGame() {
        GameSnapshot rmSnap = new GameSnapshot(games.get(currentGameKey));
        rmSnap.setDbKey(currentGameKey);
        DBIOCore.getInstance().removeGameSnapshot(rmSnap);
        currentGameKey = null;
    }

    /**
     * Checks if the passed user has it's nickname in the system, i.e. has registered to the system
     * @param nickname - the nickname to check
     * @return true if the user is registered to the system, false if not
     */
    private boolean isRegistered(String nickname){
        if (usernames.containsValue(nickname)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This is the method the UI will use to validate moves the player is attempting. This method will
     * decide if the move is valid and if so will update the game state and return true.
     * @param nickname - the current user making the move
     * @param fromRow - the x coordinate of the moves starting tile
     * @param fromCol- the y coordinate of the moves starting tile
     * @param toRow- the x coordinate of the moves ending tile
     * @param toCol- the y coordinate of the moves ending tile
     * @return -1 if the move was invalid, 1 if the move was successful, 0 if the move ended the game
     */
    public int makeMove(String nickname, int fromRow, int fromCol, int toRow,int toCol) {
        Game currentGame = games.get(currentGameKey);
        int result = currentGame.makeMove(nickname,fromRow,fromCol,toRow,toCol);
        GameSnapshot snapshot = new GameSnapshot(currentGame);
        snapshot.setDbKey(currentGameKey);
        DBIOCore.getInstance().updateGameSnapshot(snapshot);
        if (currentGame.getGameState() > 0) {
            if (currentGame.getGameState() == 1) { // game won
                GameRecord record1 = new GameRecord(currentGame.getWinnerNickname(),currentGame.getLoserNickname(),1);
                GameRecord record2 = new GameRecord(currentGame.getLoserNickname(),currentGame.getWinnerNickname(),-1);
                DBIOCore.getInstance().addGameRecord(record1);
                DBIOCore.getInstance().addGameRecord(record2);
            } else { // game tied
                GameRecord record1 = new GameRecord(currentGame.getWinnerNickname(),currentGame.getLoserNickname(),0);
                GameRecord record2 = new GameRecord(currentGame.getLoserNickname(),currentGame.getWinnerNickname(),0);
                DBIOCore.getInstance().addGameRecord(record1);
                DBIOCore.getInstance().addGameRecord(record2);
            }
        }
        return result;
    }

    /**
     * Returns the game state of the current game.
     * @return 0 if the game is in progress, 1 if the game was won, 2 if the game was a tie
     */
    public int getGameState() {
        return games.get(currentGameKey).getGameState();
    }

    // returns the name of the player who won the game!
    public String getCurrentPlayerNickname()
    {
        return games.get(currentGameKey).getCurrentPlayerNickname();
    }

    // tells the calling method if the user we are looking for is in check
    public int[] isInCheck()
    {
       // System.out.println("the number of games in Driver == " + games.size());
        //System.out.println("What is the current game key? " + currentGameKey);
        if(currentGameKey.equals("")) // this should not happen!
        {
            return null;
        }

        King king = games.get(currentGameKey).getCurrentPlayer().getKing(); // get the king for the current player.
        if(games.get(currentGameKey).getBoard().kingInCheck(king)) // if the king is in check.
        {
            int[] coords = new int[2];
            coords[0] = king.getRow();
            coords[1] = king.getCol();
            return coords; // returning this will tell the UI that the game is in not in check.
        }
        return null; // return null saying that this king is not in check.
    }


    public ArrayList<int[]> getAvailableMoves(int row, int col)
    {
        System.out.println("The currentGameKey for touching the tile is " + currentGameKey);

        Tile from = games.get(currentGameKey).getBoard().getTile(row, col);
        if (!from.isOccupied()) return new ArrayList<>();
        Color currentPlayerColor =  games.get(currentGameKey).getCurrentPlayer().getColor();
        Color movedPieceColor = from.getPiece().getColor();
        if (movedPieceColor != currentPlayerColor) return new ArrayList<>();
        return games.get(currentGameKey).getBoard().getAvailableMoves(row, col, games.get(currentGameKey).getCurrentPlayer());
    }

    /**
     * This method should be used if a user wishes to forfeit a game in progress. That user will be marked as the looser
     * if successful and the opponent will be marked as the winner.
     * @param nickname - the user that wishes to quit
     * @return - true if the user is part of the current game and the quit operation was successful, false if otherwise
     */
    public boolean quitGame(String nickname) {
        String opponent;
        Game currentGame = games.get(currentGameKey);
        if (currentGame.getP1Nickname().equals(nickname)) {
            opponent = currentGame.getP2Nickname();
        } else if (currentGame.getP2Nickname().equals(nickname)) {
            opponent = currentGame.getP1Nickname();
        } else {
            return false;
        }

        currentGame.setGameWon(opponent);
        GameSnapshot gs = new GameSnapshot(currentGame);
        gs.setDbKey(currentGameKey);
        DBIOCore.getInstance().updateGameSnapshot(gs);

        //todo:  need to send in the game snapshot to both players for the quit
        GameRecord record1 = new GameRecord(nickname,opponent,-1); // current player loses
        GameRecord record2 = new GameRecord(opponent,nickname,1); // opponent wins
        DBIOCore.getInstance().addGameRecord(record1);
        DBIOCore.getInstance().addGameRecord(record2);
        return true;
    }

    // --------------- Data retrieval/Observer methods --------------- //

    @Override
    public void usernameAdded(String addedUsername, String precedingUsernameKey) {
        usernames.put(precedingUsernameKey, addedUsername);
    }

    @Override
    public void usernameChanged(String changedUsername, String precedingUsernameKey) {
        usernames.put(precedingUsernameKey, changedUsername);
    }

    @Override
    public void usernameRemoved(String removedUsername) {
        String rmKey = "";
        for (String key : usernames.keySet()) {
            if (usernames.get(key).equals(removedUsername)) {
                rmKey = key;
                break;
            }
        }
        if (!rmKey.equals("")) {
            usernames.remove(rmKey);
        }
    }

    /**
     * @param addedSnapshot - snapshot to convert to a game
     * @param precedingSnapshotKey - snapshot's key from database
     */
    @Override
    public void snapshotAdded(GameSnapshot addedSnapshot, String precedingSnapshotKey) {
        Game tempGame = new Game(addedSnapshot.getNicknameWhite(),addedSnapshot.getNicknameBlack());
        games.put(addedSnapshot.getDbKey(),tempGame);
        //update that game with snapshot
        tempGame.updateFromSnapshot(addedSnapshot);
    }

    @Override
    public void snapshotChanged(GameSnapshot changedSnapshot, String precedingSnapshotKey) {
        //update game with new snapshot
        Game tempGame = new Game(changedSnapshot.getNicknameWhite(),changedSnapshot.getNicknameBlack());
        games.put(changedSnapshot.getDbKey(),tempGame);
        //Update game with snapshot
        tempGame.updateFromSnapshot(changedSnapshot);
    }

    @Override
    public void snapshotRemoved(GameSnapshot removedSnapshot) {
        games.remove(removedSnapshot.getDbKey());
    }
}
