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
    public ArrayList<Tile> getMovePath(Board board,int toX, int toY) {
        // implement
        ArrayList<Tile> validTiles = new ArrayList<Tile>();
        int myX = super.getX();
        int myY = super.getY();

        //check if valid move first
        if(!isValidMove(toX,toY)){
            return null;
        }

        for(int i = 0; i < board.getBoard().length; i++){
            for(int j = 0; j < board.getBoard()[i].length; j++){
                if(isValidMove(i,j)){
                    //check if tile[i,j] in between two points
                    if(distance(myX,myY,i,j) + distance(toX,toY,i,j) == distance(myX,myY,toX,toY)){
                        validTiles.add(board.getTile(i,j));
                    }
                }
            }
        }

        return validTiles;
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
