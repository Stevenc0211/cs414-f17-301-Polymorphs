package polymorphs.a301.f17.cs414.thexgame.AppBackend;

import java.util.ArrayList;

/**
 * Created by athai on 10/22/17.
 */

class King extends Piece {

    public King(int myRow, int myCol, boolean available, Color color) {
        super(myRow, myCol, available, color);
    }

    @Override
    /**
     * @return a list of tiles on the path to destination tile
     */
    public ArrayList<Tile> getMovePath(Board board, int toRow, int toCol) {
        ArrayList<Tile> validTiles = new ArrayList<Tile>();

        //check if valid move first
        if (!isValidMove(toRow, toCol)) {
            return null;
        }
        validTiles.add(board.getTile(getRow(), getCol()));
        validTiles.add(board.getTile(toRow,toCol));
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


}