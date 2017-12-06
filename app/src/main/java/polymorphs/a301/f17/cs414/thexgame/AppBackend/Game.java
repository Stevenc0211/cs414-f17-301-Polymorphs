package polymorphs.a301.f17.cs414.thexgame.AppBackend;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by athai on 10/18/17, edited, modified, and implemented by Roger, Miles, and Andy.
 *
 * Speaks and works with the driver file class.
 */

class Game {
    private Player p1;
    private Player p2;
    private Player currentPlayer;
    private Board board;
    private int gameState; // 0 = in progress, 1 = won, 2 = tie
    private String winner;
    private String loser;
    public Game(String nickname1,String nickname2){
        gameState = 0;
        p1 = new Player(nickname1,Color.WHITE);
        currentPlayer = p1;
        p2 = new Player(nickname2,Color.BLACK);
        this.board = new Board(p1,p2);
    }

    String getCurrentPlayerNickname(){
        return currentPlayer.getNickname();
    }

    Player getCurrentPlayer() {
        return currentPlayer;
    }

    String getP1Nickname() { return p1.getNickname();}

    String getP2Nickname() { return p2.getNickname();}

    String getWinnerNickname() {
        return winner;
    }

    String getLoserNickname() {
        return loser;
    }

    /**
     * Returns the games state.
     * @return 0 if game is in progress, 1 if the game has been won, 2 if the game resulted in a tie
     */
    int getGameState() {
        return gameState;
    }

    /**
     * Sets the game as won by the winnerNickname.
     * @param winnerNickname
     * @return false if winnerNickname is not a player nickname
     */
    boolean setGameWon(String winnerNickname)
    {

        if (winnerNickname.equals(getP1Nickname())) {
            winner = getP1Nickname();
            loser = getP2Nickname();
            currentPlayer = p1;

        } else if (winnerNickname.equals(getP2Nickname())) {
            winner = getP2Nickname();
            loser = getP1Nickname();
            currentPlayer = p2;
        } else {
            return false;
        }
        gameState = 1;
        return true;
    }

    public Board getBoard(){
        return board;
    }

    /**
     * This is the method the driver will use to validate moves the player is attempting. This method will
     * decide if the move is valid and if so will update the game state and return 1.
     * @param nickname - nickname of the current user making the move
     * @param fromRow - the x coordinate of the moves starting tile
     * @param fromCol- the y coordinate of the moves starting tile
     * @param toRow- the x coordinate of the moves ending tile
     * @param toCol- the y coordinate of the moves ending tile
     * @return -1 if the move was invalid, 0 if the move was successful, 1 if the move resulted in promotion
     */
    int makeMove(String nickname, int fromRow, int fromCol, int toRow,int toCol)
    {
        if (gameState != 0) return -1;
        Player activePlayer = getPlayerForNickname(nickname);
        if (activePlayer == null) return -1;

        if (!this.currentPlayer.equals(activePlayer)) return -1;

        if (board.isValidMove(activePlayer , fromRow, fromCol, toRow, toCol))
        {
            boolean promotion = handleMoveResult(fromRow, fromCol, toRow, toCol);
            if (promotion) return 1;
            return 0;
        } else {
            return -1;
        }
    }

    /** Helper for makeMove
     * After a move has been validated this method preforms the move then determines the result of making the move.
     * @return true if the move results in promotion, false if not
     */
    private boolean handleMoveResult(int fromRow, int fromCol, int toRow,int toCol) {
        boolean promotionOccurred = movePiece(fromRow, fromCol, toRow, toCol);
        Player opponent;
        if (currentPlayer == p1) {
            currentPlayer = p2;
            opponent = p1;
        } else {
            currentPlayer = p1;
            opponent = p2;
        }
        int playerStatus = board.getPlayerStatus(currentPlayer, opponent);
        if (playerStatus == 1) {
            if (currentPlayer == p1) {
                loser = p1.getNickname();
                winner = p2.getNickname();
                currentPlayer = p2;
            } else {
                loser = p2.getNickname();
                winner = p1.getNickname();
                currentPlayer = p1;
            }
            gameState = 1;
        } else if (playerStatus == 2) {
            gameState = 2;
        }
        return promotionOccurred;
    }

    /** Helper for makeMove
     * Responsible for moving and possibly promoting the piece after the move is verified as valid
     * @param fromRow - the row where the move starts
     * @param fromCol - the column where the move starts
     * @param toRow - the row where the move ends
     * @param toCol - the column where the move ends
     */
    private boolean movePiece(int fromRow, int fromCol, int toRow, int toCol) {
        Tile from = board.getTile(fromRow, fromCol);
        Tile to = board.getTile(toRow, toCol);
        int pieceIndex = currentPlayer.getIndexOf(from.getPiece());
        to.occupyTile(from.getPiece()); // this will also update the coordinates of the piece
        from.occupyTile(null);
        if (pieceIndex != -1) {
            currentPlayer.setPieceAt(pieceIndex, to.getPiece());
        }
        if (to.getPiece() instanceof Rook) {
            if (to.getPiece().getColor() == Color.WHITE) {
                if (to.getTileStatus() == Status.INSIDE_BLACK) {
                    Queen newQueen = currentPlayer.promoteRook((Rook)to.getPiece());
                    to.occupyTile(null);
                    to.occupyTile(newQueen);
                    return true; // tell make move to return 2.
                }
            } else {
                if (to.getTileStatus() == Status.INSIDE_WHITE) {
                    Queen newQueen = currentPlayer.promoteRook((Rook)to.getPiece());
                    to.occupyTile(null);
                    to.occupyTile(newQueen);
                    return true; // tell make move to return 2.
                }
            }
        }
        return false; // if no checks are hit to return true, then return false, no promotion occurred.
    }

    /**
     * Returns the player for the passed user. If the user is not a player in the game null will be returned.
     * @param nickname - the nickname to retrieve the player for
     * @return player if the user is part of the game, null if not
     */
    private Player getPlayerForNickname(String nickname) {
        Player activePlayer;
        if (nickname.equals(p1.getNickname())) {
            activePlayer = p1;
        } else if (nickname.equals(p2.getNickname())) {
            activePlayer = p2;
        } else {
            return null;
        }
        return activePlayer;
    }

    /**
     * Updates the game with a snap shot
     */
    void updateFromSnapshot(GameSnapshot snapshot){
        //set nickname of players
        p1.setNickname(snapshot.getNicknameWhite());
        p2.setNickname(snapshot.getNicknameBlack());

        gameState = snapshot.getGameState();
        winner = snapshot.getWinner();
        loser = snapshot.getLoser();

        //split game string
        String tempGame = snapshot.getGameString();
        String [] part = tempGame.split("-");
        String currPlayer = part[0];
        String [] playerPieces = part[1].split("\\|");
        String [] player1Pieces = playerPieces[0].split("\\*");
        String [] player2Pieces = playerPieces[1].split("\\*");

        //clear out both list of pieces for Player1 and Player 2
        p1.clearPieces();
        p2.clearPieces();

        //update player1 pieces
        for(int i = 0; i < player1Pieces.length; i++){
            String [] piece = player1Pieces[i].split(",");
            p1.addPieces(piece[0],Integer.parseInt(piece[1]),Integer.parseInt(piece[2]),Boolean.valueOf(piece[3]));
        }

        //update player2 pieces
        for(int i = 0; i < player2Pieces.length; i++){
            String [] piece = player2Pieces[i].split(",");
            p2.addPieces(piece[0],Integer.parseInt(piece[1]),Integer.parseInt(piece[2]),Boolean.valueOf(piece[3]));
        }

        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getColumns(); col++) {
                board.getTile(row, col).occupyTile(null);
            }
        }
        board.addPlayerPieces(p1);
        board.addPlayerPieces(p2);
        if(currPlayer.equals(p1.getNickname())){
            currentPlayer = p1;
        }
        else{
            currentPlayer = p2;
        }
    }

    public String toString(){
        return currentPlayer.getNickname() + "-" + p1.toString() + "|" + p2.toString();
    }

}
