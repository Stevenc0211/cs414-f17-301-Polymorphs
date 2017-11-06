package polymorphs.a301.f17.cs414.thexgame.AppBackend;

import java.util.ArrayList;

/**
 * Created by athai on 10/22/17.
 */

class King extends Piece {

    public King(int myRow, int myCol, boolean available, Enum color) {
        super(myRow, myCol, available, color);
    }

    @Override
    /**
     * @return a list of tiles on the path to destination tile
     */
    public ArrayList<Tile> getMovePath(Board board, int toRow, int toCol) {
        // implement should be only two tiles since knights move does not touch tiles other then start and end
        ArrayList<Tile> validTiles = new ArrayList<Tile>();
        int myRow = super.getRow();
        int myCol = super.getCol();

        //check if valid move first
        if (!isValidMove(toRow, toCol)) {
            return null;
        }

        for (int row = 0; row < board.getBoard().length; row++) {
            for (int col = 0; col < board.getBoard()[row].length; col++) {
                if (isValidMove(row, col)) {
                    //check if tile[i,j] in between two points
                    if (distance(myRow, myCol, row, col) + distance(toRow, toCol, row, col) == distance(myRow, myCol, toRow, toCol)) {
                        validTiles.add(board.getTile(row, col));
                    }
                }
            }
        }

        return validTiles;
    }

    @Override
    /**
     *
     * @return true if valid King move, false otherwise
     */
    public boolean isValidMove(int toRow, int toCol) {

        if (!super.isValidMove(toRow, toCol)) {
            return false;
        }
        //check if single tile move
        if ((Math.abs(toRow - super.getRow()) <= 1 && Math.abs(toCol - super.getCol()) <= 1)) {
            //isValidKingMove = true;
            return true;

        }
        //Check for valid Knight move
        int rowDist = Math.abs(toRow - super.getRow());
        int colDist = Math.abs(toCol - super.getCol());

        if ((rowDist == 2 && colDist == 1) || (rowDist == 1 && colDist == 2)) {
            return true;
        }

        return false;
    }

    /**
     * This is used by board to find if the king is in check/checkmate
     *
     * @return a list of available tiles to move to given an empty board
     */
    public ArrayList<Tile> getAllMoves(Board board) {
        ArrayList<Tile> validTiles = new ArrayList<Tile>();

        for (int row = 0; row < board.getBoard().length; row++) {
            for (int col = 0; col < board.getBoard()[row].length; col++) {
                if(isValidMove(row,col)){
                    validTiles.add(board.getTile(row, col));
                }
            }
        }

        return validTiles;
    }

    public String toString(){
        return super.toString();
    }

}