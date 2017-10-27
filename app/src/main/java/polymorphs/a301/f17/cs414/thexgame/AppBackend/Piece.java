package polymorphs.a301.f17.cs414.thexgame.AppBackend;

import java.util.ArrayList;

/**
 * Created by athai on 10/18/17.
 */
abstract class Piece {
    private int myX;
    private int myY;
    private boolean available;
    private Enum color;

    public Piece(int x,int y,boolean available,Enum color){
        myX = x;
        myY = y;
        this.available = available;
        this.color = color;
    }

    public int getX(){
        return myX;
    }

    public void setX(int x){
        myX = x;
    }

    public int getY(){
        return myY;
    }

    public void setY(int y){
        myY = y;
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

    /**
     * Checks that the move will stay inside the board and will not land on the same square
     * @param toX the x coordinate of the move
     * @param toY the y coordinate of the move
     * @return true if the move stays inbounds and does not end on the start square
     */
    public boolean isValidMove(int toX,int toY){
        //Stayed in the same spot
        if(myX == toX && myY == toY){
            return false;
        }
        //if the xy coordinate we are moving to is outside the board
        if(toX < 0 || toX > 11 || toY < 0 || toY > 11){
            return false;
        }
        return true;
    }

    /**
     * This is used to derive all the tiles the piece will travel over to get to the to position.
     * Used by Board to decide if a move is valid
     * @param toX the x coordinate of the move
     * @param toY the y cooordinate of the move
     * @return a list of Tiles the piece would move over to acomplish the move
     */
    public abstract ArrayList<Tile> getMovePath(int toX, int toY);

}
