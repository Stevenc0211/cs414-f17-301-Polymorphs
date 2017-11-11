package polymorphs.a301.f17.cs414.thexgame.AppBackend;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.*;

/**
 * Created by athai on 10/29/17.
 */

public class TestRook {
    @Test
        public void testInstanceCreation(){
            try{
                Rook rook = new Rook(3,4,true,Color.BLACK);
            }
            catch (Exception e){
                fail("ERROR: Rook failed to instantiate");
            }
    }

    @Test
    public void testIsValidMoveFalse(){
        Rook r = new Rook(4,5,true,Color.WHITE);
        assertFalse("Rook should not be able to move diagonally",r.isValidMove(5,6));
    }

    @Test
    public void testIsValidMoveTrueVertically(){
        Rook r = new Rook(4,5,true,Color.WHITE);
        assertTrue("Rook should be able to move vertically",r.isValidMove(11,5));
    }

    @Test
    public void testIsValidMoveTrueHorizontally(){
        Rook r = new Rook(4,5,true,Color.WHITE);
        assertTrue("Rook should be able to move horizontally",r.isValidMove(4,7));
    }

    @Test
    public void testGetMovePathInvalid(){
        Board b = new Board();
        Rook r = new Rook(4,5,true,Color.WHITE);
        ArrayList<Tile> temp = r.getMovePath(b,7,0);
        assertNull(temp);
    }

    @Test
    public void testToString(){
        Rook rook = new Rook(4,3,true,Color.WHITE);
        assertEquals("Rook,4,3,true,WHITE", rook.toString());
    }

    @Test
    public void testEqualsMethodTrue(){
        Rook rook = new Rook(3,3,true,Color.BLACK);
        Rook other = new Rook(3,3,true,Color.BLACK);
        assertTrue("Rooks should be equal",rook.equals(other));
    }

    @Test
    public void testEqualsMethodFalse(){
        Rook rook = new Rook(3,3,true,Color.BLACK);
        Rook other = new Rook(2,1,true,Color.BLACK);
        assertFalse("Rooks should not be equal",rook.equals(other));
    }
}
