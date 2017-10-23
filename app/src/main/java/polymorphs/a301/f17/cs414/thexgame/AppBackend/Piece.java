package polymorphs.a301.f17.cs414.thexgame.AppBackend;

/**
 * Created by athai on 10/18/17.
 */

public class Piece {
    private int x;
    private int y;
    private boolean available;
    private Enum color;

    public Piece(int x,int y,boolean available,Enum color){
        this.x = x;
        this.y = y;
        this.available = available;
        this.color = color;
    }

    public int getX(){
        return x;
    }

    public void setX(int x){
        this.x = x;
    }

    public int getY(){
        return y;
    }

    public void setY(int y){
        this.y = y;
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

    public boolean isValid(Board board,int fromX,int fromY,int toX,int toY){
        if(fromX == toX && fromY == toY){
            return false;
        }
        if(fromX < 0 || fromX > 11 || toX < 0 || toX > 11 || fromY < 0 || fromY > 11 || toY < 0 || toY > 11){
            return false;
        }
        return true;
    }

}
