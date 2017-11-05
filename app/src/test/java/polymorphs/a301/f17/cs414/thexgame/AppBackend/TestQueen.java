package polymorphs.a301.f17.cs414.thexgame.AppBackend;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

/**
 * Created by athai on 10/27/17.
 */

public class TestQueen {
    @Test
    public void testInstanceCreation(){
        try{
            Queen queen = new Queen(3,4,true,Color.BLACK);
        }
        catch (Exception e){
            fail("ERROR: Queen failed to instantiate");
        }
    }

    @Test
    public void testIsValidMoveFalse(){
        Queen q = new Queen(4,5,true,Color.WHITE);
        assertFalse("Should be false",q.isValidMove(4,96));
    }

    @Test
    public void testIsValidMoveTrue(){
        Queen q = new Queen(4,5,true,Color.WHITE);
        assertTrue("Should be true",q.isValidMove(2,3));
    }

    @Test
    public void testGetMovePathDiagonal(){
        Board b = new Board();
        Queen q = new Queen(4,5,true,Color.WHITE);
        ArrayList<Tile> temp = q.getMovePath(b,2,3);
        assertEquals(2,temp.size());
    }

    @Test
    public void testGetMovePathRow(){
        Board b = new Board();
        Queen q = new Queen(4,5,true,Color.WHITE);
        ArrayList<Tile> temp = q.getMovePath(b,4,0);
        assertEquals(5,temp.size());
    }

    @Test
    public void testGetMovePathNull(){
        Board b = new Board();
        Queen q = new Queen(4,5,true,Color.WHITE);
        ArrayList<Tile> temp = q.getMovePath(b,7,0);
        assertNull(temp);
    }

    @Test
    public void testToString(){
        Queen queen = new Queen(4,5,true,Color.WHITE);
        assertEquals("4, 5, true, WHITE",queen.toString());
    }
}
