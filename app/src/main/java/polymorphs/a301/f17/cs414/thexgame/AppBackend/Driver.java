package polymorphs.a301.f17.cs414.thexgame.AppBackend;

import java.util.ArrayList;
import java.util.HashMap;

import polymorphs.a301.f17.cs414.thexgame.persistence.DBIOCore;
import polymorphs.a301.f17.cs414.thexgame.persistence.UsernameListObserver;

/**
 * Created by athai on 10/18/17. Edited and modified by Roger and Miles
 * Controls all of the features needed for the backend to work with the UI elements.
 */

public final class Driver implements UsernameListObserver { // will implement GameListObserver
    private HashMap<String, String> usernames;    // the master list of registered users usernames
    private ArrayList<Game> games;
    private int currentGameIndex = -1;

    public Driver(){
        this.usernames = new HashMap<>();
        this.games = new ArrayList<>();
    }

    public ArrayList<Game> getGames(){
        return games;
    }

    /**
     * Builds a new game and adds it to the set of current games.
     * @param userWhite - the inviting user for this game
     * @param userBlack - the invited user for this game
     * @throws IllegalArgumentException - if either passed user are not registered with the system
     */
    public void createGame(User userWhite,User userBlack) throws IllegalArgumentException{
        createGame(userWhite.getNickname(), userBlack.getNickname());
    }

    /**
     * Builds a new game and adds it to the set of current games.
     * @param nicknameWhite - the inviting users nickname for this game
     * @param nicknameBlack - the invited users nickname for this game
     * @throws IllegalArgumentException - if either passed user are not registered with the system
     */
    public void createGame(String nicknameWhite,String nicknameBlack) throws IllegalArgumentException{
        //must check if both players are registered
        if(isRegistered(nicknameWhite) && isRegistered(nicknameBlack)) {
            Game game = new Game(nicknameWhite, nicknameBlack);
            games.add(game);
        }
        else{

            // MANUALLY ADDING THIS CODE WHERE WE BYPASS THE REGISTRATION TO GET USERS INTO THE GAME.
            Game game = new Game(nicknameWhite, nicknameBlack);
            games.add(game);

            //throw new IllegalArgumentException("ERROR: both passed users must be registered");
        }
    }



    /**
     * Sets the active game of the set of active games.
     * @param index - the index of the game to set as active
     * @throws IllegalArgumentException - if the passed index is out of bounds, i.e. greater or equal to the number of active games
     */
    public void setCurrentGameIndex(int index) throws IllegalArgumentException {
        if (index >= games.size()) {
            throw new IllegalArgumentException("ERROR: given index is out of bounds");
        }
        currentGameIndex = index;
    }

    /**
     * Checks if the passed user has it's nickname in the system, i.e. has registered to the system
     * @param userNickname - the nickname of the user to check
     * @return true if the user is registered to the system, false if not
     */
    private boolean isRegistered(String userNickname){
        if (usernames.containsValue(userNickname)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This is the method the UI will use to validate moves the player is attempting. This method will
     * decide if the move is valid and if so will update the game state and return true.
     * @param user - the current user making the move
     * @param fromRow - the x coordinate of the moves starting tile
     * @param fromCol- the y coordinate of the moves starting tile
     * @param toRow- the x coordinate of the moves ending tile
     * @param toCol- the y coordinate of the moves ending tile
     * @return -1 if the move was invalid, 1 if the move was successful, 0 if the move ended the game
     */
    public int makeMove(User user, int fromRow, int fromCol, int toRow,int toCol) {
        int result = games.get(currentGameIndex).makeMove(user,fromRow,fromCol,toRow,toCol);
        if (result == 0) {
            // book keeping for a finished game should go here. Will need to wait until the DB is set up to handle games and game results
        } else if (result > 1) {
            // book keeping to sync the opponents board with the new game state. Will need to wait until the DB is set up to handle games
        }

        System.out.println("THE RESULT RETURNED FROM DRIVER WAS == " + result);

        return result;
    }

    // returns the name of the player who won the game!
    public String getCurrentPlayerNickname()
    {
        return games.get(currentGameIndex).getCurrentPlayer().getNickname();
    }

    // tells the calling method if the user we are looking for is in check
    public int[] isInCheck()
    {
        King king = games.get(currentGameIndex).getCurrentPlayer().getKing(); // get the king for the current player.

        if(games.get(currentGameIndex).getBoard().kingInCheck(king)) // if the king is in check.
        {
            int[] coords = new int[2];
            coords[0] = king.getRow();
            coords[1] = king.getCol();

            return coords; // returning this will tell the UI that the game is in not in check.
        }

        return null; // return null saying that this king is not in check.
    }

    /*
        This method is used to grab all of the available moves for a piece that the user has clicked.
    */
    public ArrayList<int[]> getAvailableMoves(int row, int col)
    {
        Tile from = games.get(currentGameIndex).getBoard().getTile(row, col);
        if (!from.isOccupied()) return new ArrayList<>();
        Color currentPlayerColor =  games.get(currentGameIndex).getCurrentPlayer().getColor();
        Color movedPieceColor = from.getPiece().getColor();
        if (movedPieceColor != currentPlayerColor) return new ArrayList<>();
        return games.get(currentGameIndex).getBoard().getAvailableMoves(row, col, games.get(currentGameIndex).getCurrentPlayer());
    }


    /**
     * This method should be used if a user wishes to forfeit a game in progress. That user will be marked as the looser
     * if successful and the opponent will be marked as the winner.
     * @param user - the user that wises to quit
     * @return - true if the user is part of the current game and the quit operation was successful, false if otherwise
     */
    public boolean quitGame(User user) {
        if (games.get(currentGameIndex).getNicknameWhite().equals(user.getNickname()) || games.get(currentGameIndex).getNicknameBlack().equals(user.getNickname())) {
            // preform quit operation, will need to wait until DB is caught up
            return true;
        }
        return false;
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
        if (rmKey != "") {
            usernames.remove(rmKey);
        }
    }
}
