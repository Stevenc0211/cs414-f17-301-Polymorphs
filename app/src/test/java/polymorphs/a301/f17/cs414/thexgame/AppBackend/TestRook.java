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
        assertFalse("Should be false",r.isValidMove(4,96));
    }

    @Test
    public void testIsValidMoveTrue(){
        Rook r = new Rook(4,5,true,Color.WHITE);
        assertTrue("Should be true",r.isValidMove(11,5));
    }

    @Test
    public void testGetMovePathColumn(){
        Board b = new Board();
        Rook r = new Rook(4,5,true,Color.WHITE);
        ArrayList<Tile> temp = r.getMovePath(b,4,10);
        assertEquals(5,temp.size());
    }

    @Test
    public void testGetMovePathRow(){
        Board b = new Board();
        Rook r = new Rook(4,5,true,Color.WHITE);
        ArrayList<Tile> temp = r.getMovePath(b,1,5);
        assertEquals(3,temp.size());
    }

    @Test
    public void testGetMovePathNull(){
        Board b = new Board();
        Rook r = new Rook(4,5,true,Color.WHITE);
        ArrayList<Tile> temp = r.getMovePath(b,7,0);
        assertNull(temp);
    }

    @Test
    public void testToString(){
        Rook rook = new Rook(4,3,true,Color.WHITE);
        assertEquals("4, 3, true, WHITE", rook.toString());
    }
}
