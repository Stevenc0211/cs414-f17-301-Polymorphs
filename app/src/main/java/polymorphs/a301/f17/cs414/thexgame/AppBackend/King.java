package polymorphs.a301.f17.cs414.thexgame.AppBackend;

import java.util.ArrayList;

/**
 * Created by athai on 10/22/17.
 */

class King extends Piece {

    public King(int myX,int myY,boolean available,Enum color){
        super(myX,myY,available,color);
    }

    @Override
    public ArrayList<Tile> getMovePath(Board board,int toX, int toY) {
        // implement should be only two tiles since knights move does not touch tiles other then start and end
        return null;
    }

    @Override
    public boolean isValidMove(int toX,int toY){
        if(!super.isValidMove(toX,toY)){
            return false;
        }
        //check for valid King move
        if(Math.sqrt(Math.pow(Math.abs((toX - super.getX())),2)) + Math.pow(Math.abs((toY - super.getY())), 2) != Math.sqrt(2)){
            return false;
        }

        //Check for valid Knight move
        if(toX != super.getX() - 1 && toX != super.getX() + 1 && toX != super.getX() + 2 && toX != super.getX() - 2)
            return false;
        if(toY != super.getY() - 2 && toY != super.getY() + 2 && toY != super.getY() - 1 && toY != super.getY() + 1)
            return false;
        return false;
    }

    /**
     * This is used by board to find if the king is in check/checkmate
     * @return a array of to coordinates, i.e. [[toX1, toY1], [toX2, toY2], etc.]
     */
    public double[][] getAllMoves() {
        return new double[0][0];
    }
}