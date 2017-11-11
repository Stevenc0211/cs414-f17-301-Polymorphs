package polymorphs.a301.f17.cs414.thexgame.AppBackend;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by athai on 10/18/17.
 */
abstract class Piece {
    private int myRow;
    private int myCol;
    private boolean available;
    private Color color;

    public Piece(int row,int col,boolean available,Color color){
        myRow = row;
        myCol = col;
        this.available = available;
        this.color = color;
    }

    public int getRow(){
        return myRow;
    }

    public void setRow(int row){
        myRow = row;
    }

    public int getCol(){
        return myCol;
    }

    public void setCol(int col){
        myCol = col;
    }

    public boolean isAvailable(){
        return available;
    }

    public void setAvailable(boolean available){
        this.available = available;
    }

    public Color getColor(){
        return color;
    }

    public double distance(int fromRow,int fromCol,int toRow,int toCol){
        return Math.hypot((double)fromRow - (double)toRow,(double)fromCol - (double)toCol);
    }

    /**
     * Used to setup increments for various traversals of the board. Will return -1, 0 or 1 based on the diference
     * of from and to.
     * @param from - a from row or column
     * @param to - a to row or column
     * @return -1 if from > to, 0 if from == to, 1 if from < to
     */
    private int getIncrement(int from, int to) {
        if (from < to) {
            return 1;
        } else if (from > to) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * Checks that the move will stay inside the board and will not land on the same square
     * @param toRow the x coordinate of the move
     * @param toCol the y coordinate of the move
     * @return true if the move stays inbounds and does not end on the start square
     */
    public boolean isValidMove(int toRow,int toCol){
        //Stayed in the same spot
        if(myRow == toRow && myCol == toCol){
            return false;
        }
        //if the xy coordinate we are moving to is outside the board
        if(toRow < 0 || toRow > 11 || toCol < 0 || toCol > 11){
            return false;
        }
        return true;
    }

    /**
     * This is used to derive all the tiles the piece will travel over to get to the to position.
     * Used by Board to decide if a move is valid. Tiles are inorder of the move starting with the first tile
     * the piece will pass through
     * @param toRow the x coordinate of the move
     * @param toCol the y coordinate of the move
     * @return a list of Tiles the piece would move over to acomplish the move
     */
    public ArrayList<Tile> getMovePath(Board board,int toRow, int toCol) {
        // implement
        ArrayList<Tile> validTiles = new ArrayList<>();
        int myRow = getRow();
        int myCol = getCol();

        //check if valid move first
        if(!isValidMove(toRow,toCol)){
            return null;
        }

        int rowInc = getIncrement(myRow, toRow);
        int colInc = getIncrement(myCol, toCol);
        // Because of the check in the loop the toRow/toCol need to be pushed out one further
        toRow += rowInc;
        toCol += colInc;

        for (int row = myRow, col = myCol; row != toRow || col != toCol; row += rowInc, col += colInc) {
            validTiles.add(board.getTile(row,col));
        }

        return validTiles;
    }

    /**
     * Returns the move paths for all reachable tiles. Use when checking for checkmate.
     * @return - arraylist of move paths
     */
    public ArrayList<ArrayList<Tile>> getAllMovePaths(Board board) {
        ArrayList<ArrayList<Tile>> result = new ArrayList<>();
        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getColumns(); col++) {
                if(isValidMove(row,col)){
                    result.add(getMovePath(board, row, col));
                }
            }
        }
        return result;
    }

    private String getType(Piece piece){
        if(piece instanceof Rook){
            return "Rook";
        }
        else if(piece instanceof Queen){
            return "Queen";
        }
        else if(piece instanceof King){
            return "King";
        }
        else{
            return null;
        }
    }

    public String toString(){
        return getType(this) + "," + myRow + "," + myCol + "," + available + "," + color;
    }

}
