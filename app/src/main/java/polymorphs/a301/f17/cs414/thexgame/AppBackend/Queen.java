package polymorphs.a301.f17.cs414.thexgame.AppBackend;

/**
 * Created by athai on 10/22/17.
 */

public class Queen extends Piece {

    public Queen(int myX,int myY,boolean available,Enum color){
        super(myX,myY,available,color);
    }

    @Override
    public boolean isValidMove(Board board,int toX,int toY){        //return Tile arraylist
        if(!super.isValidMove(board,toX,toY)){
            return false;
        }

        if(super.getX() == toX){
            return true;
        }
        if(super.getY() == toY){
            return true;
        }
        //check if move is diagonal
        if(super.getX() - toX == super.getY() - toY){
            return true;
        }
        return false;
    }
}
