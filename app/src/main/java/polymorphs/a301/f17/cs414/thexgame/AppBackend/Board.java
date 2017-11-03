package polymorphs.a301.f17.cs414.thexgame.AppBackend;

import java.util.ArrayList;

/**
 * Created by athai on 10/18/17.
 */

class Board {
    private Tile[][] board;
    private ArrayList <Piece> pieces;

    public Board(){
        super();
        board = new Tile[12][12];

        for(int i = 0; i < 12; i++){
            for(int j = 0; j < 12; j++){
                this.board[i][j] = new Tile(i,j);
            }
        }
        inititalizeCastleWall();
        initializeInsideCastle();
    }

    private void inititalizeCastleWall(){
        getTile(1,7).setTileStatus(Status.CASTLE);
        getTile(1,8).setTileStatus(Status.CASTLE);
        getTile(1,9).setTileStatus(Status.CASTLE);
        getTile(2,6).setTileStatus(Status.CASTLE);
        getTile(2,10).setTileStatus(Status.CASTLE);
        getTile(3,6).setTileStatus(Status.CASTLE);
        getTile(3,10).setTileStatus(Status.CASTLE);
        getTile(4,6).setTileStatus(Status.CASTLE);
        getTile(4,10).setTileStatus(Status.CASTLE);
        getTile(5,7).setTileStatus(Status.CASTLE);
        getTile(5,8).setTileStatus(Status.CASTLE);
        getTile(5,9).setTileStatus(Status.CASTLE);

        getTile(6,2).setTileStatus(Status.CASTLE);
        getTile(6,3).setTileStatus(Status.CASTLE);
        getTile(6,4).setTileStatus(Status.CASTLE);
        getTile(7,1).setTileStatus(Status.CASTLE);
        getTile(7,5).setTileStatus(Status.CASTLE);
        getTile(8,1).setTileStatus(Status.CASTLE);
        getTile(8,5).setTileStatus(Status.CASTLE);
        getTile(9,1).setTileStatus(Status.CASTLE);
        getTile(9,5).setTileStatus(Status.CASTLE);
        getTile(10,2).setTileStatus(Status.CASTLE);
        getTile(10,3).setTileStatus(Status.CASTLE);
        getTile(10,4).setTileStatus(Status.CASTLE);
    }

    private void initializeInsideCastle(){
        getTile(2,7).setTileStatus(Status.INSIDE);
        getTile(2,8).setTileStatus(Status.INSIDE);
        getTile(2,9).setTileStatus(Status.INSIDE);
        getTile(3,7).setTileStatus(Status.INSIDE);
        getTile(3,8).setTileStatus(Status.INSIDE);
        getTile(3,9).setTileStatus(Status.INSIDE);
        getTile(4,7).setTileStatus(Status.INSIDE);
        getTile(4,8).setTileStatus(Status.INSIDE);
        getTile(4,9).setTileStatus(Status.INSIDE);

        getTile(7,2).setTileStatus(Status.INSIDE);
        getTile(7,3).setTileStatus(Status.INSIDE);
        getTile(7,4).setTileStatus(Status.INSIDE);
        getTile(8,2).setTileStatus(Status.INSIDE);
        getTile(8,3).setTileStatus(Status.INSIDE);
        getTile(8,4).setTileStatus(Status.INSIDE);
        getTile(9,2).setTileStatus(Status.INSIDE);
        getTile(9,3).setTileStatus(Status.INSIDE);
        getTile(9,4).setTileStatus(Status.INSIDE);
    }

    public Tile getTile(int x,int y){
        return board[x][y];
    }

    public Tile[][] getBoard(){
        return board;
    }

    /**
     * This will validate a move based on board logic, e.g. does it run into another piece etc.
     * @param player - the player attempting the move
     * @param fromX - the x coordinate of the moves starting tile
     * @param fromY - the y coordinate of the moves starting tile
     * @param toX - the x coordinate of the moves ending tile
     * @param toY - the y coordinate of the moves ending tile
     * @return true if the move is valid. false if not
     */
    boolean isValidMove(Player player, int fromX, int fromY, int toX, int toY) {
        // sanity checks should include, from coord contains a piece, that piece is the same color
        //  as the player, that players king is not in check or the move puts the king out of check
        Piece p = getTile(fromX,fromY).getPiece();
        //check if there is a piece on the tile
        if(p == null){
            return false;
        }
        //check if piece moving is same color as player
        if(p.getColor() != player.getColor()){
            return false;
        }

        // Logic check should follow the pattern, get starting tile, if piece is on tile check if to coordinate
        //  is valid with piece.isValidMove(), then ask for the pieces move path and validate that the
        //  path is correct, e.g. doesn't move through pieces and ends on an empty tile or opposing players piece

        // NOTE: for kings the move must additionally be validated by isValidKingMove
        return false;
    }

    /**
     * Given a king piece decides if the king is in check.
     * @param king - the king to check
     * @return true if the king is in check, false if otherwise
     */
    private boolean kingInCheck(King king) {
        if (kingInCheckmate(king)) return true;
        return false;
    }

    /**
     * Given a king piece decides if the king is in checkmate.
     * @param king - the king to check
     * @return true if the king is in checkmate, false if otherwise
     */
    private boolean kingInCheckmate(King king) {
        // for moves in king.getAllMoves() if move is valid (standard valid + not in check) return true, return false on done
        return false;
    }

    /**
     * This is used to check for additional restraints for kings. This will handle moving into check,
     * moving out of the castle and other king only logic.
     * @return true if the kings restraints are not being violated
     */
    private boolean isValidKingMove(int fromX, int fromY, int toX, int toY) {
        return false;
    }
}
