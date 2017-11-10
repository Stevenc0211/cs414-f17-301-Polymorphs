package polymorphs.a301.f17.cs414.thexgame.AppBackend;

import java.util.ArrayList;
import java.util.HashMap;

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
     * @param player1 - the inviting user for this game
     * @param player2 - the invited user for this game
     * @throws IllegalArgumentException - if either passed user are not registered with the system
     */
    public void createGame(User player1,User player2) throws IllegalArgumentException{
        //must check if both players are registered
        if(isRegistered(player1) && isRegistered(player2)) {
            Game game = new Game(player1, player2);
            games.add(game);
        }
        else{

            // MANUALLY ADDING THIS CODE WHERE WE BYPASS THE REGISTRATION TO GET USERS INTO THE GAME.
            Game game = new Game(player1, player2);
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
     * @param user - the user to check
     * @return true if the user is registered to the system, false if not
     */
    private boolean isRegistered(User user){
        if (usernames.containsValue(user.getNickname())) {
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
        return result;
    }

    // return the color of the current player for the UI to keep track with who is moving.
    public String getCurrentPlayerColor()
    {
        Color color = games.get(currentGameIndex).getCurrentPlayer().getColor(); // gets the color for the user that we are working with.

        if(color == Color.BLACK)
        {
            return "black";
        }
        else
        {
            return "white";
        }

    }

    /**
        Grabs all of the available moves for a rook sent in by the getAvailableMoves method.
        @param rook - the rook that is sent in.
        @param selectedTile - the tile that was selected by the user on the UI that contains the rook.
     */
    private ArrayList<int[]> getRookMoves(Rook rook, Tile selectedTile)
    {

        ArrayList<int[]> availableMoves = new ArrayList<>();

        // go through every row and col and check if it is a valid move, if so, add to our list of available moves.
        for(int row = 0; row < 12; row++)
        {
            for(int col = 0; col < 12; col++)
            {
                if(rook.isValidMove(row, col) == true)
                {
                    int moves[] = new int[2];
                    moves[0] = row;
                    moves[1] = col;
                    availableMoves.add(moves); // add the tile to the list of valid tiles.
                }
            }
        }

        return availableMoves;
    }

    /**
     Grabs all of the available moves for a rook sent in by the getAvailableMoves method.
     @param king - the king that is sent in.
     @param selectedTile - the tile that was selected by the user on the UI that contains the king.
     */
    private ArrayList<int[]> getKingMoves(King king, Tile selectedTile)
    {

        ArrayList<int[]> availableMoves = new ArrayList<>();

        // go through every row and col and check if it is a valid move, if so, add to our list of available moves.
        for(int row = 0; row < 12; row++)
        {
            for(int col = 0; col < 12; col++)
            {
                if(king.isValidMove(row, col) == true)
                {
                    int moves[] = new int[2];
                    moves[0] = row;
                    moves[1] = col;
                    availableMoves.add(moves); // add the tile to the list of valid tiles.
                }
            }
        }

        return availableMoves;
    }

    /**
     Grabs all of the available moves for a rook sent in by the getAvailableMoves method.
     @param queen - the rook that is sent in.
     @param selectedTile - the tile that was selected by the user on the UI that contains the rook.
     */
    private ArrayList<int[]> getQueenMoves(Queen queen, Tile selectedTile)
    {

        ArrayList<int[]> availableMoves = new ArrayList<>();

        // go through every row and col and check if it is a valid move, if so, add to our list of available moves.
        for(int row = 0; row < 12; row++)
        {
            for(int col = 0; col < 12; col++)
            {
                if(queen.isValidMove(row, col) == true)
                {
                    int moves[] = new int[2];
                    moves[0] = row;
                    moves[1] = col;
                    availableMoves.add(moves); // add the tile to the list of valid tiles.
                }
            }
        }

        return availableMoves;
    }

    /*
        This method is used to grab all of the available moves for a piece that the user has clicked.
    */
    public ArrayList<int[]> getAvailableMoves(int row, int col)
    {
        ArrayList<int[]> availableMoves = new ArrayList<>(); // holds the list of moves that the player is able to make.
        Tile selectedTile = games.get(currentGameIndex).getBoard().getTile(row, col); // get the tile that was selected by the player.

        if(selectedTile.isOccupied() == true) // tile has a piece.
        {
            Piece piece = selectedTile.getPiece(); // grab the piece that this tile has and see what piece it is.
            if(piece.isAvailable())
            {
                // figure out what piece this actually is and find the moves for it.
                if(piece instanceof Rook)
                {
                    Rook rook = (Rook) piece; // piece is a rook
                    availableMoves = getRookMoves(rook, selectedTile); // grabs the selected tile from the board. Pretty important.
                    return availableMoves; // return the moves to the UI
                }
                else if(piece instanceof Queen)
                {
                    Queen queen = (Queen) piece; // piece is a queen.
                    availableMoves = getQueenMoves(queen, selectedTile); // grab moves for this piece, then return the arraylist.
                    return availableMoves; // return the moves to the UI.
                }
                else if(piece instanceof King)
                {
                    King king = (King) piece; // piece is a king.
                    availableMoves = getKingMoves(king, selectedTile); // grab moves for this piece, then return the arraylist.
                    return availableMoves;
                }
            }
        }
        else {
            System.out.println("The tile does not have a piece on it!");
            return null; // return a null set in which the UI will not highlight anything.
        }

        return null; // this should not be reached, but if it is, the UI will not display highlighted tiles.

    }


    /**
     * This method should be used if a user wishes to forfeit a game in progress. That user will be marked as the looser
     * if successful and the opponent will be marked as the winner.
     * @param user - the user that wises to quit
     * @return - true if the user is part of the current game and the quit operation was successful, false if otherwise
     */
    public boolean quitGame(User user) {
        if (games.get(currentGameIndex).getUser1().equals(user) || games.get(currentGameIndex).getUser2().equals(user)) {
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
