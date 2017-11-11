package polymorphs.a301.f17.cs414.thexgame.AppBackend;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by athai on 10/18/17, edited, modified, and implemented by Roger.
 *
 * Speaks and works with the driver file class.
 * TODO: needs the code written in the Board class from Steven and Badr so that we the game will work as expected!
 */

class Game {
    private User user1;
    private User user2;
    private Player p1;
    private Player p2;
    private Player currentPlayer;
    private Board board;
    private Player winner;
    private Player loser;

    public Game(User user1,User user2){
        this.user1 = user1;
        this.user2 = user2;
        p1 = new Player(user1,Color.WHITE);
        currentPlayer = p1;
        p2 = new Player(user2,Color.BLACK);
        this.board = new Board(p1,p2);
    }

    public Player getCurrentPlayer()
    {
        return currentPlayer;
    }

    public User getUser1(){
        return user1;
    }

    public User getUser2(){
        return user2;
    }

    public Board getBoard(){
        return board;
    }

    /**
     * This is the method the driver will use to validate moves the player is attempting. This method will
     * decide if the move is valid and if so will update the game state and return 1.
     * @param user - the current user making the move
     * @param fromRow - the x coordinate of the moves starting tile
     * @param fromCol- the y coordinate of the moves starting tile
     * @param toRow- the x coordinate of the moves ending tile
     * @param toCol- the y coordinate of the moves ending tile
     * @return -1 if the move was invalid, 1 if the move was successful, 0 if the move ended the game
     */
    public int makeMove(User user, int fromRow, int fromCol, int toRow,int toCol)
    {
        // TODO: UNCOMMENT!! this is what restricts turn order
        Player activePlayer = getPlayerForUser(user);
        if (activePlayer == null) return -1;
        if (!this.currentPlayer.equals(activePlayer)) return -1;

        boolean promotionOccurred = false; // default to no promotion.
        if (board.isValidMove(activePlayer , fromRow, fromCol, toRow, toCol))
        {
            Tile to = board.getTile(toRow, toCol);
            Piece toPiece = to.getPiece();
            promotionOccurred = movePiece(fromRow, fromCol, toRow, toCol);
            if (currentPlayer == p1) {
                currentPlayer =  p2;
            } else {
                currentPlayer = p1;
            }
            if (board.inCheckmate(currentPlayer)) {
                return 0;
            }
            if (toPiece != null) {

                Player pieceCapturedPlayer = currentPlayer;
                playerGraveyard(pieceCapturedPlayer, toPiece);
            }
            if(promotionOccurred) // a promotion has occured.
            {
                return 2;
            }

            return 1;
        } else {

            return -1;
        }
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
        to.occupyTile(from.getPiece()); // this will also update the coordinates of the piece

        from.occupyTile(null);
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

    /** updates the arraylist for a player by adding the piece that was captured by the opponent
     * @param player - the player whose piece was captured by the opponent
     * @param piece - piece that was captured
     * @return void
     */
     public void playerGraveyard(Player player,Piece piece){
         if (player.equals(p1)){

             p1.getPlayer1graveyard().add(piece);
         }
         else {

             p2.getPlayer2graveyard().add(piece);
         }
     }
    /**
     * Returns the player for the passed user. If the user is not a player in the game null will be returned.
     * @param user - the user to retrieve the player for
     * @return player if the user is part of the game, null if not
     */
    private Player getPlayerForUser(User user) {
        Player activePlayer;
        if (user.equals(user1)) {
            activePlayer = p1;
        } else if (user.equals(user2)) {
            activePlayer = p2;
        } else {
            return null;
        }
        return activePlayer;
    }

    public String toString(){
        return p1.toString() + ", " + p2.toString();
    }

}
