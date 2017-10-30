package polymorphs.a301.f17.cs414.thexgame.AppBackend;

import org.junit.Test;
import static org.junit.Assert.*;

import polymorphs.a301.f17.cs414.thexgame.AppBackend.Color;
import polymorphs.a301.f17.cs414.thexgame.AppBackend.Piece;
import polymorphs.a301.f17.cs414.thexgame.AppBackend.Board;
/**
 * Created by athai on 10/23/17.
 */

public class TestPiece {
    @Test
    public void testInstanceCreation(){
        try{
            //Piece p = new Piece();
        }
        catch (Exception e){
            fail("ERROR: Piece failed to instantiate");
        }
    }

    @Test
    public void testIsValid1(){
        Board b = new Board();
        //Piece p = new Piece(5,5,true, Color.WHITE);
        //assertFalse("Should be false",p.isValidMove(b,5,5));
    }

    @Test
    public void testIsValid2(){
        Board b = new Board();
        //Piece p = new Piece(5,5,true, Color.WHITE);
        //assertTrue("Should be true",p.isValidMove(b,2,10));
    }
}
