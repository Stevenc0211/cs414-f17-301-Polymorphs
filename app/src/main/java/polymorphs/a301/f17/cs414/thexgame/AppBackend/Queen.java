package polymorphs.a301.f17.cs414.thexgame.AppBackend;

import java.util.ArrayList;

/**
 * Created by athai on 10/22/17.
 */

class Queen extends Piece {

    public Queen(int myRow,int myCol,boolean available,Color color){
        super(myRow,myCol,available,color);
    }

    @Override
    /**
     * @return true if valid Queen move, false otherwise
     */
    public boolean isValidMove(int toRow,int toCol){
        if(!super.isValidMove(toRow,toCol)){
            return false;
        }

        if(super.getRow() == toRow){
            return true;
        }
        if(super.getCol() == toCol){
            return true;
        }
        //check if move is diagonal
        if(Math.abs(super.getRow() - toRow) == Math.abs(super.getCol() - toCol)){
            return true;
        }
        return false;
    }

    public String toString(){
        return super.toString();
    }
}
