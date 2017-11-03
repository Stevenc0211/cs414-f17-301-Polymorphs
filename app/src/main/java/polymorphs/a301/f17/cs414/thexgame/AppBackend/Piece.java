package polymorphs.a301.f17.cs414.thexgame.AppBackend;

import java.util.ArrayList;

/**
 * Created by athai on 10/18/17.
 */
abstract class Piece {
    private int myRow;
    private int myCol;
    private boolean available;
    private Enum color;

    public Piece(int row,int col,boolean available,Enum color){
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

    public Enum getColor(){
        return color;
    }

    public double distance(int fromRow,int fromCol,int toRow,int toCol){
        return Math.hypot((double)fromRow - (double)toRow,(double)fromCol - (double)toCol);
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
     * Used by Board to decide if a move is valid
     * @param toRow the x coordinate of the move
     * @param toCol the y cooordinate of the move
     * @return a list of Tiles the piece would move over to acomplish the move
     */
    public abstract ArrayList<Tile> getMovePath(Board board,int toRow, int toCol);

}
