package polymorphs.a301.f17.cs414.thexgame.AppBackend;

import java.util.ArrayList;

/**
 * Created by athai on 10/18/17. Edited and Modified by Budhr and Steven.
 */

class Board {
    private Tile[][] boardTiles;
    private ArrayList <Piece> pieces;

    public Board(){
        super();
        boardTiles = new Tile[12][12];

        for(int i = 0; i < 12; i++){
            for(int j = 0; j < 12; j++){
                this.boardTiles[i][j] = new Tile(i,j);
            }
        }
        inititalizeCastleWall();
        initializeInsideCastle();
    }
    //todo @Andy, do we still need to initialize the castle wall from within Board? -Steven

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

    public Tile getTile(int fromRow,int fromCol){
        return boardTiles[fromRow][fromCol];
    }

    public Tile[][] getBoard(){
        return boardTiles;
    }

    /**
     * This will validate a move based on board logic, e.g. does it run into another piece etc.
     * @param player - the player attempting the move
     * @param fromRow - moves from the row essentially acts as the x coordinate of the moves starting/current tile
     * @param fromCol - moves from the row essentially acts as the y coordinate of the moves starting/current tile
     * @param toRow - the x coordinate of the moves ending tile
     * @param toCol - the y coordinate of the moves ending tile
     * @return true if the move is valid. false if not
     */
    public boolean isValidMove(Player player, int fromRow,int fromCol,int toRow,int toCol) {
        Piece p = getTile(fromRow, fromCol).getPiece();
        // check if there is a piece on the tile
        if(p == null) return false;

        // check if piece moving is same color as player
        if(p.getColor() != player.getColor()) return false;

        // check if the piece can make the move given an empty board
        if (!p.isValidMove(toRow, toCol)) return false;

        if ((p instanceof King) && !isValidKingMove(fromRow,fromCol,toRow,toCol)) return false;

        if (kingInCheck(player.getKing()))  { // move must remove check as well as move path being valid
            if (moveResultsInCheck(player.getKing(),fromRow,fromCol,toRow,toCol)) return false;
        }

        //return validateMovePath(p.getMovePath(this,toRow,toCol), p.getColor()); // TODO: error in p.getColor()
        return false;
    }

    /**
     * Checks a given movePath to see if the move is valid. A move path is invalid if it moves through a piece or if the ending
     * tile is occupied by a friendly piece
     * @param movePath - a move path from a call to Piece.getMovePath
     * @return true if the move path is valid
     */
    private boolean validateMovePath(ArrayList<Tile> movePath, Color friendlyColor) {
        for (int idx = 0; idx < movePath.size()-1; idx++) { // for all save last tile
            if (movePath.get(idx).isOccupied()) return false;
        }
        if (movePath.get(movePath.size()-1).getPiece().getColor() == friendlyColor) return false;
        return true;
    }

    /** BASE METHOD, does not rely on other methods
     * Given a king piece decides if the king is in check.
     * @param king - the king to check
     * @return true if the king is in check, false if otherwise
     */
    public boolean kingInCheck(King king) { // TODO: miles this should be public , I cant test it
        if (checkEngine(king,1,0)) return true; // check for threat below king (col +)
        if (checkEngine(king,-1,0)) return true; // check for threat above king (col -)
        if (checkEngine(king,0,1)) return true; // check for threat right of king (row +)
        if (checkEngine(king,0,-1)) return true; // check for threat left of king (row -)
        if (checkEngine(king,1,1)) return true; // check for threat down right diagonal (col +, row +)
        if (checkEngine(king,-1,-1)) return true; // check for threat up left diagonal (col -, row -)
        if (checkEngine(king,1,1)) return true; // check for threat down right diagonal (col +, row +)
        if (checkEngine(king,1,-1)) return true; // check for threat up right diagonal (col +, row -)
        if (checkEngine(king,-1,1)) return true; // check for threat down left diagonal (col -, row +)
        return false;
    }

    /**
     * Backend for kingInCheck, starts at the kings row and column and moves out the tile space by
     * adding colInc to column and rowInc to row.
     * @param king - the king to check
     * @param colInc - the amount to increment the column by, should be -1, 0 or 1
     * @param rowInc - the amount to increment the row by, should be -1, 0 or 1
     * @return true if there is a piece placing the king in check in the direction specified, false if not
     */
    private boolean checkEngine(King king, int colInc, int rowInc) {
        Tile currentTile;
        for (int col = king.getCol()+colInc, row = king.getRow()+rowInc;
             col >=0 && col < 12 && row >=0 && row < 12;
             col+=colInc, row+=rowInc)
        {
            currentTile = boardTiles[row][col];
            if (!currentTile.isOccupied()) continue;
            if (currentTile.getPiece().getColor() == king.getColor()) {
                break;
            } else {
                if (currentTile.getPiece() instanceof King) break; // kings can't check each other
                return true;
            }
        }
        return false;
    }

    /**
     * Temporarily makes the proposed move and checks if the move resulted in the king being in check.
     * NOTE: this does not check that the move is valid only that the result is not check.
     * @param king - the active players king
     * @param fromRow - the row where the move starts
     * @param fromCol - the column where the move starts
     * @param toRow - the row where the move ends
     * @param toCol - the column where the move ends
     * @return true if after the move the players king is in check, false if otherwise
     */
    private boolean moveResultsInCheck(King king, int fromRow, int fromCol, int toRow, int toCol) {
        Tile from = boardTiles[fromRow][fromCol];
        Tile to = boardTiles[toRow][toCol];
        Piece savedFromPiece = from.getPiece();
        Piece savedToPiece = to.getPiece();

        boolean result;

        to.occupyTile(from.getPiece());
        from.occupyTile(null);
        if (kingInCheck(king)) {
            result = false;
        } else {
            result = true;
        }

        from.occupyTile(savedFromPiece);
        to.occupyTile(savedToPiece);
        if (savedToPiece != null) {
            savedToPiece.setAvailable(true);
        }
        return result;
    }

    /**
     * Given a king piece decides if the king is in checkmate.
     * @param king - the king to check
     * @return true if the king is in checkmate, false if otherwise
     */
    public boolean kingInCheckmate(King king) {
        if (!kingInCheck(king)) return false;

        ArrayList<Tile> allMoves = king.getAllMoves(this); // get all moves the king can make

        for (Tile tile : allMoves) {
            if (isValidKingMove(king.getRow(),king.getCol(),tile.getRow(),tile.getCol())) {
                if (!moveResultsInCheck(king, king.getRow(), king.getCol(), tile.getRow(), tile.getCol())) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * This is used to check for additional restraints for kings. This will handle moving into check,
     * moving out of the castle and other king only logic.
     * NOTE: does NOT check standard isValidMove for the king piece, this should be used in addition to such a call
     * @return true if the kings restraints are not being violated
     */
    private boolean isValidKingMove(int fromRow,int fromCol,int toRow,int toCol) {
        Tile to = boardTiles[toRow][toCol];
        if (to.getTileStatus() != Status.INSIDE) return false;
        Piece p = getTile(fromRow, fromCol).getPiece();
        if(p instanceof King)
        {
            King king = (King) p;
            if (!king.isValidMove(toRow,toCol)) return false;
            if (moveResultsInCheck(king,fromRow,fromCol,toRow,toCol)) return false;
            return true;
        }
        return false;
    }

//    public boolean withinCastle(int fromRow, int fromCol)
//    {
//
//        if(getTile(fromRow, fromCol).getTileStatus()== Status.CASTLE)
//        {
//            return true;
//        }
//        else
//            return false;
//
//    }


}