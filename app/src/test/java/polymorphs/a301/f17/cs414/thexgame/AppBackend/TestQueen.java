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
        Queen q = new Queen(4,5,true,Color.WHITE);
        ArrayList<Tile> temp = q.getMovePath(2,3);
        assertEquals(2,temp.size());
    }

    @Test
    public void testGetMovePathRow(){
        Queen q = new Queen(4,5,true,Color.WHITE);
        ArrayList<Tile> temp = q.getMovePath(4,0);
        assertEquals(5,temp.size());
    }

    @Test
    public void testGetMovePathNull(){
        Queen q = new Queen(4,5,true,Color.WHITE);
        ArrayList<Tile> temp = q.getMovePath(7,0);
        assertNull(temp);
    }
}
