package polymorphs.a301.f17.cs414.thexgame.AppBackend;

import java.util.ArrayList;

/**
 * Created by athai on 10/22/17.
 */

class Rook extends Piece {

    public Rook(int myX,int myY,boolean available,Enum color){
        super(myX,myY,available,color);
    }

    @Override
    public ArrayList<Tile> getMovePath(int toX, int toY) {
        // implement
        return null;
    }

    @Override
    public boolean isValidMove(int toX,int toY){
        if(!super.isValidMove(toX,toY)){
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
