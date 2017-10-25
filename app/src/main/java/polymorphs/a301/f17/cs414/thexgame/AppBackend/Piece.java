package polymorphs.a301.f17.cs414.thexgame.AppBackend;

/**
 * Created by athai on 10/18/17.
 */

public class Piece {
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

    public boolean isValidMove(Board board,int toX,int toY){
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

}
