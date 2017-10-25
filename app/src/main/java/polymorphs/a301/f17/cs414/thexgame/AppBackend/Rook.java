package polymorphs.a301.f17.cs414.thexgame.AppBackend;

/**
 * Created by athai on 10/22/17.
 */

public class Rook extends Piece {

    public Rook(int myX,int myY,boolean available,Enum color){
        super(myX,myY,available,color);
    }

    @Override
    public boolean isValidMove(Board board,int toX,int toY){
        if(!super.isValidMove(board,toX,toY)){
            return false;
        }

        if(super.getX() == toX){
            return true;
        }
        if(super.getY() == toY){
            return true;
        }

        return false;
    }
}
