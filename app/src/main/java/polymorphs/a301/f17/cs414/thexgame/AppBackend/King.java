package polymorphs.a301.f17.cs414.thexgame.AppBackend;

/**
 * Created by athai on 10/22/17.
 */

public class King extends Piece {

    public King(int myX,int myY,boolean available,Enum color){
        super(myX,myY,available,color);
    }

    @Override
    public boolean isValidMove(Board board,int toX,int toY){    //Verify!!!!!!!!!!!!!!!
        if(!super.isValidMove(board,toX,toY)){
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
}