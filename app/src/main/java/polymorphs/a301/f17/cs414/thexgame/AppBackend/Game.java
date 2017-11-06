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
    // not sure these are needed. A game record object should be created when the game ends that will capture this info - Miles
    private Player winner; // holds the winner of the game.
    private Player loser; //

    public Game(User user1,User user2){
        this.user1 = user1;
        this.user2 = user2;
        p1 = new Player(user1,Color.WHITE);
        currentPlayer = p1;
        p2 = new Player(user2,Color.BLACK);
        this.board = new Board();
    }

    public User getUser1(){
        return user1;
    }

    public User getUser2(){
        return user2;
    }

    public Player getP1(){
        return p1;
    }

    public Player getP2(){
        return p2;
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
        Player activePlayer;
        if (user.equals(user1)) {
            activePlayer = p1;
        } else if (user.equals(user2)) {
            activePlayer = p2;
        } else {
            return -1;
        }


        if (!this.currentPlayer.equals(activePlayer)) return -1;

        if (board.isValidMove(activePlayer , fromRow, fromCol, toRow, toCol))
        {

            Tile from = board.getTile(fromRow, fromCol);
            Tile to = board.getTile(toRow, toCol);
            to.occupyTile(from.getPiece()); // this will also update the coordinates of the piece
            from.occupyTile(null);
            if (currentPlayer == p1) {
                currentPlayer = p2;
            } else {
                currentPlayer = p1;
            }
            if (board.kingInCheckmate(currentPlayer.getKing())) {
                return 0;
            }
            return 1;
        } else {
            return -1;
        }
    }

    // checks if player one is in check.
    public boolean isPlayerOneInCheck()
    {
        if(board.kingInCheck(p1.getKing())) // check if player two is in check.
        {
            return true;
        }
        else {
            return false; // player one is not in check.
        }
    }

    // checks if player one is in check.
    public boolean isPlayerTwoInCheck()
    {
        if(board.kingInCheck(p2.getKing())) // check if player two is in check.
        {
            return true;
        }
        else {
            return false; // player two is not in check.
        }
    }

    // check is player one is in checkmate
    public boolean isPlayerOneInCheckmate()
    {
        if(board.kingInCheckmate(p1.getKing()))
        {
            winner = p2; // set player 2 as the winner of the game.
            loser = p1; // set player 1 as the loser of the game.
            return true;
        }
        else {
            return false;
        }
    }

    // check is player one is in checkmate
    public boolean isPlayerTwoInCheckmate()
    {
        if(board.kingInCheckmate(p2.getKing()))
        {
            winner = p1; // set player 1 as the winner of the game.
            loser = p2; // set player 2 as the loser of the game.
            return true;
        }
        else {
            return false;
        }
    }

    // goes through all pieces and looks for rooks on the castle wall. i.e. needs to be converted into a queen.
    public ArrayList<Rook> getP1RooksOnCastle()
    {
        ArrayList<Rook> rooksOnCastle = new ArrayList<>(); // list of rooks that need to be turned in to queens for this player.
        // TODO: @Miles, @Andy should we make this immediately convert all of the Rooks into Queens for this player?

        // iterate through each piece and check if any rooks are on the castle walls.
        for(int i = 0; i < p1.getPieces().size(); i++)
        {
            Piece piece = p1.getPieces().get(i); // grabs a piece from player 1's list of pieces.
            // check if the piece is a rook.

            if(piece instanceof Rook) // check to see if a piece is an instance of rook.
            {
                // Note: this rook is coming from player should be white based off of the rook.
                Rook rook = (Rook) piece; // cast the piece to a rook.

                if(board.withinCastle(rook.getRow(), rook.getCol()))
                {
                    rooksOnCastle.add(rook); // add the rook that is one the castle.
                }
            }
        }

        return rooksOnCastle; // return player one's rooks on the castle.
    }

    // goes through all pieces and looks for rooks on the castle wall. i.e. needs to be converted into a queen.
    public ArrayList<Rook> getP2RooksOnCastle()
    {
        ArrayList<Rook> rooksOnCastle = new ArrayList<>(); // list of rooks that need to be turned in to queens for this player.
        // TODO: @Miles, @Andy should we make this immediately convert all of the Rooks into Queens for this player?

        // iterate through each piece and check if any rooks are on the castle walls.
        for(int i = 0; i < p2.getPieces().size(); i++)
        {
            Piece piece = p2.getPieces().get(i); // grabs a piece from player 2's list of pieces.
            // check if the piece is a rook.

            if(piece instanceof Rook) // check to see if a piece is an instance of rook.
            {
                // Note: this rook is coming from player should be white based off of the rook.
                Rook rook = (Rook) piece; // cast the piece to a rook.

                if(board.withinCastle(rook.getRow(), rook.getCol()))
                {
                    rooksOnCastle.add(rook); // add the rook that is one the castle.
                }
            }
        }

        return rooksOnCastle; // return player one's rooks on the castle.
    }

    public String toString(){
        return p1.toString() + ", " + p2.toString();
    }
}
