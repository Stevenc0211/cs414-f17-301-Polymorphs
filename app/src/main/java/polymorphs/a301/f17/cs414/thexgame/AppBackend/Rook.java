package polymorphs.a301.f17.cs414.thexgame.AppBackend;

import java.util.ArrayList;

/**
 * Created by athai on 10/22/17.
 */

class Rook extends Piece {

    public Rook(int myRow,int myCol,boolean available,Enum color){
        super(myRow,myCol,available,color);
    }

    @Override
    /**
     * @return a list of tiles on the path to destination tile
     */
    public ArrayList<Tile> getMovePath(Board board,int toRow, int toCol) {
        // implement
        ArrayList<Tile> validTiles = new ArrayList<Tile>();
        int myRow = super.getRow();
        int myCol = super.getCol();

        //check if valid move first
        if(!isValidMove(toRow,toCol)){
            return null;
        }

        for(int row = 0; row < board.getBoard().length; row++){
            for(int col = 0; col < board.getBoard()[row].length; col++){
                if(isValidMove(row,col)){
                    //check if tile[i,j] in between two points
                    if(distance(myRow,myCol,row,col) + distance(toRow,toCol,row,col) == distance(myRow,myCol,toRow,toCol)){
                        validTiles.add(board.getTile(row,col));
                    }
                }
            }
        }

        return validTiles;
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

        return false;
    }

    public String toString(){
        return super.toString();
    }
}
