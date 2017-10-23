package polymorphs.a301.f17.cs414.thexgame.AppBackend;

/**
 * Created by athai on 10/22/17.
 */

public class Rook extends Piece {

    public Rook(int x,int y,boolean available,Enum color){
        super(x,y,available,color);
    }

    @Override
    public boolean isValid(Board board,int fromX,int fromY,int toX,int toY){
        if(!super.isValid(board,fromX,fromY,toX,toY)){
            return false;
        }

        if(fromX == toX){
            return true;
        }
        if(fromY == toY){
            return true;
        }

        return false;
    }
}
