package polymorphs.a301.f17.cs414.thexgame.AppBackend;

/**
 * Created by athai on 10/18/17, edited, modified, and implemented by Roger.
 *
 * Speaks and works with the driver file class.
 * TODO: needs the code written in the Board class from Steven and Badr so that we the game will work as expected!
 */

class Game {
    private String nicknameW;
    private String nicknameB;
    private Player white;
    private Player black;
    private Player currentPlayer;
    private Board board;
    private Player winner;
    private Player loser;


    public Game(String nickname1, String nickname2){
        nicknameW = nickname1;
        nicknameB = nickname2;
        white = new Player(nicknameW,Color.WHITE);
        currentPlayer = white;
        black = new Player(nicknameB,Color.BLACK);
        this.board = new Board(white, black);
    }

    public String getNicknameWhite(){
        return nicknameW;
    }

    public String getNicknameBlack(){
        return nicknameB;
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
        Player activePlayer = getPlayerForUser(user);
        if (activePlayer == null) return -1;

        if (!this.currentPlayer.equals(activePlayer)) return -1;

        if (board.isValidMove(activePlayer , fromRow, fromCol, toRow, toCol))
        {

            movePiece(fromRow, fromCol, toRow, toCol);
            if (currentPlayer == white) {
                currentPlayer = black;
            } else {
                currentPlayer = white;
            }
            if (board.inCheckmate(currentPlayer)) {
                return 0;
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
    private void movePiece(int fromRow, int fromCol, int toRow, int toCol) {
        Tile from = board.getTile(fromRow, fromCol);
        Tile to = board.getTile(toRow, toCol);
        to.occupyTile(from.getPiece()); // this will also update the coordinates of the piece
        from.occupyTile(null);
        if (from.getPiece() instanceof Rook) {
            if (from.getPiece().getColor() == Color.WHITE) {
                if (from.getTileStatus() == Status.INSIDE_BLACK) {
                    from.occupyTile(currentPlayer.promoteRook((Rook)from.getPiece()));
                }
            } else {
                if (from.getTileStatus() == Status.INSIDE_WHITE) {
                    from.occupyTile(currentPlayer.promoteRook((Rook)from.getPiece()));
                }
            }
        }
    }

    /**
     * Returns the player for the passed user. If the user is not a player in the game null will be returned.
     * @param user - the user to retrieve the player for
     * @return player if the user is part of the game, null if not
     */
    private Player getPlayerForUser(User user) {
        Player activePlayer;
        if (user.getNickname().equals(nicknameW)) {
            activePlayer = white;
        } else if (user.getNickname().equals(nicknameB)) {
            activePlayer = black;
        } else {
            return null;
        }
        return activePlayer;
    }

    public String toString(){
        return white.toString() + ", " + black.toString();
    }

}
