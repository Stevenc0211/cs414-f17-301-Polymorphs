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
        // sanity checks should include, from coord contains a piece, that piece is the same color
        //  as the player, that players king is not in check or the move puts the king out of check
        Piece p = getTile(fromRow, fromCol).getPiece();
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
    public boolean kingInCheck(King king) {
        //king.getColor();
        Color  opponentColor;
        if (king.getColor()==Color.WHITE ) {
            opponentColor = Color.BLACK;
        }
        else {
            opponentColor = Color.WHITE;
        }

        for(int i = 0; i < 12; i++){  // iterate through every tile in board
            for(int j = 0; j < 12; j++) {
                Piece p = getTile(i, j).getPiece();  // get a piece from a tile at a time
                if (p != null){
                    if (p.getColor().equals(opponentColor)) {// if current piece is not the opponent piece
                        if (p.isValidMove(king.getRow(), king.getCol())) { //check if current piece can reach the king's tile, if so its in check
                            return true;
                        }
                    }
            }
            }
        }
        return false;
    }

    /**
     * Given a king piece decides if the king is in checkmate.
     * @param king - the king to check
     * @return true if the king is in checkmate, false if otherwise
     */
    public boolean kingInCheckmate(King king) {
        // for moves in king.getAllMoves() if move is valid (standard valid + not in check) return true, return false on done


        int counter = 0; // counter to keep a track of moves that result in check
        ArrayList<Tile> allMoves = king.getAllMoves(this); // get all moves the king can // make
        Color  opponentColor;     // checks for opponent's piece and get a the opponent color
        if (king.getColor()==Color.WHITE ) {
            opponentColor = Color.BLACK;
        }
        else {
            opponentColor = Color.WHITE;
        }
        //System.out.println("kingallmoveslength: "+allMoves.size());

        for(int i = 0; i <boardTiles.length; i++){    //king.getallmoves return x,y coordinates
            for(int j = 0; j < boardTiles.length; j++) {
                Piece p = getTile(i, j).getPiece();  //   problem here I don't understand what to do?
                if (p != null){
                    if (p.getColor().equals(opponentColor)) {
                        if ((p.isValidMove(allMoves.get(i).getPiece().getRow(), allMoves.get(i).getPiece().getCol())) || (kingInCheck(king))) {
                            counter = counter + 1;
                            if (counter == allMoves.size()) {
                                return true;
                            }

                        }
                    }
            }
            }
        }
        // getallmoves
        // have no valid moves and in check, return false;

        return false;
    }

    /**
     * This is used to check for additional restraints for kings. This will handle moving into check,
     * moving out of the castle and other king only logic.
     * @return true if the kings restraints are not being violated
     */
    private boolean isValidKingMove(int fromRow,int fromCol,int toRow,int toCol) {

        Piece p = getTile(fromRow, fromCol).getPiece();
        if(p instanceof King)
        {
            King king = (King) p;
            if(king == null)
                System.out.println("King is null and typecasting to king in isValidKingMove failed");
            if(kingInCheck(king) == false || kingInCheckmate(king) == false)
            {
                if(king.isValidMove(toRow,toCol))
                    king.setRow(toRow);
                king.setCol(toCol);
            }
        }
        return false;
    }

    public boolean withinCastle(int fromRow, int fromCol)
    {

        if(getTile(fromRow, fromCol).getTileStatus()== Status.CASTLE)
        {
            return true;
        }
        else
            return false;

    }


}