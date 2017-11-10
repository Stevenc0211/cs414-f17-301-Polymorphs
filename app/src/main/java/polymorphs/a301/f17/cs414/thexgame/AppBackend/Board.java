package polymorphs.a301.f17.cs414.thexgame.AppBackend;

import java.util.ArrayList;

/**
 * Created by athai on 10/18/17. Edited and Modified by Budhr and Steven.
 */

class Board {
    private Tile[][] boardTiles;

    /**
     * Creates an empty Chad board
     */
    public Board(){
        super();
        boardTiles = new Tile[12][12];

        for(int i = 0; i < getRows(); i++){
            for(int j = 0; j < getColumns(); j++){
                this.boardTiles[i][j] = new Tile(i,j);
            }
        }
        initializeCastleWall();
        initializeInsideCastle();
    }

    /**
     * Creates and empty Chad board then adds all the piece from both player1 and player2 to the board.
     * @param player1 - the first player with pieces to add
     * @param player2 - the second player with pieces to add
     */
    public Board(Player player1, Player player2) throws IllegalArgumentException {
        this();
        addPlayerPieces(player1);
        addPlayerPieces(player2);
    }

    /**
     * Adds all of the available pieces the player owns to the board.
     * @param player - the player with pieces to add
     * @throws IllegalArgumentException - if any of the pieces from the player would be added to an occupied tile or tile outside the board bounds
     */
    public void addPlayerPieces(Player player) throws IllegalArgumentException {
        for (Piece piece : player.getPieces()) {
            if (!piece.isAvailable()) continue;
            addPiece(piece);
        }
    }

    /**
     * Adds the passed piece to the board and the coordinates specified by the piece
     * @param piece - the piece to add
     * @throws IllegalArgumentException - if the piece would be added to an occupied tile
     */
    private void addPiece(Piece piece) throws IllegalArgumentException {
        try {
            if (boardTiles[piece.getRow()][piece.getCol()].isOccupied()) {
                throw new IllegalArgumentException("ERROR: piece would be added to an occupied tile");
            }
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("ERROR: piece specified a tile that was out of bounds");
        } catch (NullPointerException e) {
            if (piece == null) throw new IllegalArgumentException("ERROR: passed piece was null");
            else throw new IllegalArgumentException("ERROR: board has null tiles");
        }
        boardTiles[piece.getRow()][piece.getCol()].occupyTile(piece);
    }

    private void initializeCastleWall(){
        getTile(1,7).setTileStatus(Status.WALL_BLACK);
        getTile(1,8).setTileStatus(Status.WALL_BLACK);
        getTile(1,9).setTileStatus(Status.WALL_BLACK);
        getTile(2,6).setTileStatus(Status.WALL_BLACK);
        getTile(2,10).setTileStatus(Status.WALL_BLACK);
        getTile(3,6).setTileStatus(Status.WALL_BLACK);
        getTile(3,10).setTileStatus(Status.WALL_BLACK);
        getTile(4,6).setTileStatus(Status.WALL_BLACK);
        getTile(4,10).setTileStatus(Status.WALL_BLACK);
        getTile(5,7).setTileStatus(Status.WALL_BLACK);
        getTile(5,8).setTileStatus(Status.WALL_BLACK);
        getTile(5,9).setTileStatus(Status.WALL_BLACK);

        getTile(6,2).setTileStatus(Status.WALL_WHITE);
        getTile(6,3).setTileStatus(Status.WALL_WHITE);
        getTile(6,4).setTileStatus(Status.WALL_WHITE);
        getTile(7,1).setTileStatus(Status.WALL_WHITE);
        getTile(7,5).setTileStatus(Status.WALL_WHITE);
        getTile(8,1).setTileStatus(Status.WALL_WHITE);
        getTile(8,5).setTileStatus(Status.WALL_WHITE);
        getTile(9,1).setTileStatus(Status.WALL_WHITE);
        getTile(9,5).setTileStatus(Status.WALL_WHITE);
        getTile(10,2).setTileStatus(Status.WALL_WHITE);
        getTile(10,3).setTileStatus(Status.WALL_WHITE);
        getTile(10,4).setTileStatus(Status.WALL_WHITE);
    }

    private void initializeInsideCastle(){
        getTile(2,7).setTileStatus(Status.INSIDE_BLACK);
        getTile(2,8).setTileStatus(Status.INSIDE_BLACK);
        getTile(2,9).setTileStatus(Status.INSIDE_BLACK);
        getTile(3,7).setTileStatus(Status.INSIDE_BLACK);
        getTile(3,8).setTileStatus(Status.INSIDE_BLACK);
        getTile(3,9).setTileStatus(Status.INSIDE_BLACK);
        getTile(4,7).setTileStatus(Status.INSIDE_BLACK);
        getTile(4,8).setTileStatus(Status.INSIDE_BLACK);
        getTile(4,9).setTileStatus(Status.INSIDE_BLACK);

        getTile(7,2).setTileStatus(Status.INSIDE_WHITE);
        getTile(7,3).setTileStatus(Status.INSIDE_WHITE);
        getTile(7,4).setTileStatus(Status.INSIDE_WHITE);
        getTile(8,2).setTileStatus(Status.INSIDE_WHITE);
        getTile(8,3).setTileStatus(Status.INSIDE_WHITE);
        getTile(8,4).setTileStatus(Status.INSIDE_WHITE);
        getTile(9,2).setTileStatus(Status.INSIDE_WHITE);
        getTile(9,3).setTileStatus(Status.INSIDE_WHITE);
        getTile(9,4).setTileStatus(Status.INSIDE_WHITE);
    }

    /**
     * Returns the tile at the given coordinates.
     * @param row - the row of the desired tile
     * @param col- the column of the desired tile
     * @return the tile if row and col are in the bounds of the board, null if not
     */
    public Tile getTile(int row,int col){
        if (row < 0 || row > 11 || col < 0 || col > 11) return null;
        return boardTiles[row][col];
    }

    public int getRows() {
        return 12;
    }

    public int getColumns(){
        return 12;
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

        if (moveResultsInCheck(player.getKing(),fromRow,fromCol,toRow,toCol)) return false;

        return validateMovePath(p.getMovePath(this,toRow,toCol), player);
    }

    /**
     * Checks a given movePath to see if the move is valid. A move path is invalid if it moves through a piece, if the ending
     * tile is occupied by a friendly piece, if the move results in check or if a king is moving and the move fails isValidKingMove
     * @param movePath - a move path from a call to Piece.getMovePath
     * @return true if the move path is valid
     */
    private boolean validateMovePath(ArrayList<Tile> movePath, Player movingPlayer) {
        if (movePath == null) return false;
        Tile firstTile = movePath.get(0);
        if (!firstTile.isOccupied()) return false;
        Tile lastTile = movePath.get(movePath.size()-1);
        if (firstTile.getPiece() instanceof King) {
            if (!isValidKingMove(firstTile.getRow(), firstTile.getCol(), lastTile.getRow(), lastTile.getCol())){
                return false;
            }
        }
        if (moveResultsInCheck(movingPlayer.getKing(), firstTile.getRow(), firstTile.getCol(), lastTile.getRow(), lastTile.getCol())) return false;
        for (int idx = 1; idx < movePath.size()-1; idx++) { // for all save first and last tile
            if (movePath.get(idx).isOccupied()) return false;
        }
        return validCapture(firstTile,lastTile,movingPlayer.getColor());
    }

    /** Helper method for validateMovePath
     * Checks if the move results in a valid capture. From our rules source (en.wikipedia.org/wiki/Chad_(chess_variant)):
     * "A rook or queen may capture an opponent's rook or queen only when one of these pieces is on the enemy's wall,
     *      and the other piece is in its own castle. Then either player having the turn to move may capture.
     *      (In other situations rooks and queens cannot capture, and simply block one another's movement.
     *      For example, a queen in an enemy castle is capturable only by the king.)"
     * @param firstTile - the first tile of a move path
     * @param lastTile - the last tile of a move path
     * @param friendlyColor - the color of friendly pieces
     * @return true if the capture is valid.
     * NOTE: capturing a tile with no other piece is always valid
     */
    private boolean validCapture(Tile firstTile, Tile lastTile, Color friendlyColor) {
        if (!lastTile.isOccupied()) return true;
        if (!firstTile.isOccupied()) return false;
        if (lastTile.getPiece().getColor() == friendlyColor) return false;
        if (firstTile.getPiece() instanceof King) {
            if (friendlyColor == Color.WHITE) {
                if (lastTile.getTileStatus() == Status.INSIDE_WHITE) return true;
            } else {
                if (lastTile.getTileStatus() == Status.INSIDE_BLACK) return true;
            }
        } else {
            if (friendlyColor == Color.WHITE) {
                if (firstTile.getTileStatus() == Status.WALL_BLACK && lastTile.getTileStatus() == Status.INSIDE_BLACK) return true;
                if (firstTile.getTileStatus() == Status.INSIDE_WHITE && lastTile.getTileStatus() == Status.WALL_WHITE) return true;
            } else {
                if (firstTile.getTileStatus() == Status.WALL_WHITE && lastTile.getTileStatus() == Status.INSIDE_WHITE) return true;
                if (firstTile.getTileStatus() == Status.INSIDE_BLACK && lastTile.getTileStatus() == Status.WALL_BLACK) return true;
            }
        }
        return false;
    }

    /** BASE METHOD, does not rely on other methods
     * Given a king piece decides if the king is in check.
     * @param king - the king to check
     * @return true if the king is in check, false if otherwise
     */
    private boolean kingInCheck(King king) {
        if (checkEngine(king,1,0)) return true; // check for threat below king (row +)
        if (checkEngine(king,-1,0)) return true; // check for threat above king (row -)
        if (checkEngine(king,0,1)) return true; // check for threat right of king (col +)
        if (checkEngine(king,0,-1)) return true; // check for threat left of king (col -)
        if (checkEngine(king,1,1)) return true; // check for threat down right diagonal (col +, row +)
        if (checkEngine(king,-1,-1)) return true; // check for threat up left diagonal (col -, row -)
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
                if (colInc != 0 && rowInc != 0 && currentTile.getPiece() instanceof Rook) continue; // rooks can't check diagonaly
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
        if (!from.isOccupied()) return kingInCheck(king);

        boolean result;
        Piece savedFromPiece = from.getPiece();
        Piece savedToPiece = to.getPiece();

        try {
            to.occupyTile(from.getPiece());
        } catch (IllegalArgumentException e) {
            return false;
        }
        from.occupyTile(null);
        if (kingInCheck(king)) {
            result = true;
        } else {
            result = false;
        }

        from.occupyTile(savedFromPiece);
        to.occupyTile(savedToPiece);
        if (savedToPiece != null) {
            savedToPiece.setAvailable(true);
        }
        return result;
    }

    /**
     * Given a player determines if that player is in checkmate
     * @param player - the player to check
     * @return true if the player is in checkmate, false if otherwise
     */
    public boolean inCheckmate(Player player) {
        King king = player.getKing();
        if (!kingInCheck(king)) return false;
        Tile lastTile;
        ArrayList<ArrayList<Tile>> allMovePaths;
        for (Piece piece : player.getPieces()) {
            if (!piece.isAvailable()) continue;;
            allMovePaths = piece.getAllMovePaths(this);
            for (ArrayList<Tile> movePath : allMovePaths) {
                if (!validateMovePath(movePath, player)) continue;
                lastTile = movePath.get(movePath.size()-1);
                if (piece instanceof King) {
                    if(!isValidKingMove(piece.getRow(),piece.getCol(),lastTile.getRow(),lastTile.getCol())) continue;
                }
                if (!moveResultsInCheck(king,piece.getRow(),piece.getCol(),lastTile.getRow(),lastTile.getCol())) {
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
        Piece p = getTile(fromRow, fromCol).getPiece();
        if(p instanceof King)
        {
            King king = (King) p;
            if (king.getColor() == Color.WHITE) {
                if (to.getTileStatus() != Status.INSIDE_WHITE) return false;
            } else {
                if (to.getTileStatus() != Status.INSIDE_BLACK) return false;
            }
            if (!king.isValidMove(toRow,toCol)) return false;
            if (moveResultsInCheck(king,fromRow,fromCol,toRow,toCol)) return false;
            return true;
        }
        return false;
    }

    /**
     * Returns the valid move coordinates for the piece at the passed row and col.
     * @param row - the row to calculate moves from
     * @param col - the col to calculate moves from
     * @return array list of coordinates [row,col], empty list if the tile was empty
     */
    ArrayList<int[]> getAvailableMoves(int row, int col, Player playerMoving)
    {
        ArrayList<int[]> result = new ArrayList<>();
        boolean firstTileOfPath;
        if (boardTiles[row][col].isOccupied()) {
            ArrayList<ArrayList<Tile>> allMovePaths = boardTiles[row][col].getPiece().getAllMovePaths(this);
            for(ArrayList<Tile> movePath : allMovePaths) {
                if (validateMovePath(movePath, playerMoving)) {
                    firstTileOfPath = true;
                    for (Tile tile : movePath) {
                        if (firstTileOfPath) {
                            firstTileOfPath = false;
                            continue;
                        }
                        result.add(new int[]{tile.getRow(), tile.getCol()});
                    }
                }
            }
        }
        return result;
    }

}
