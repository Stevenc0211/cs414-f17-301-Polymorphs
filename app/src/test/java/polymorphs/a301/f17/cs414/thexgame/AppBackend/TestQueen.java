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
    public void testIsValidMoveUp(){
        Queen q = new Queen(4,5,true,Color.WHITE);
        assertTrue("Queen should be able to move up",q.isValidMove(0,5));
    }

    @Test
    public void testIsValidMoveUpRight(){
        Queen q = new Queen(4,5,true,Color.WHITE);
        assertTrue("Queen should be able to move up right diagonal",q.isValidMove(2,7));
    }

    @Test
    public void testIsValidMoveRight(){
        Queen q = new Queen(4,5,true,Color.WHITE);
        assertTrue("Queen should be able to move right",q.isValidMove(4,7));
    }

    @Test
    public void testIsValidMoveDownRight(){
        Queen q = new Queen(4,5,true,Color.WHITE);
        assertTrue("Queen should be able to move down right diagonal",q.isValidMove(7,8));
    }

    @Test
    public void testIsValidMoveDown(){
        Queen q = new Queen(4,5,true,Color.WHITE);
        assertTrue("Queen should be able to move down",q.isValidMove(8,5));
    }

    @Test
    public void testIsValidMoveDownLeft(){
        Queen q = new Queen(4,5,true,Color.WHITE);
        assertTrue("Queen should be able to move down left diagonal",q.isValidMove(6,3));
    }

    @Test
    public void testIsValidMoveLeft(){
        Queen q = new Queen(4,5,true,Color.WHITE);
        assertTrue("Queen should be able to move left",q.isValidMove(4,3));
    }

    @Test
    public void testIsValidMoveUpLeft(){
        Queen q = new Queen(4,5,true,Color.WHITE);
        assertTrue("Queen should be able to move up left diagonal",q.isValidMove(1,2));
    }

    @Test
    public void testIsValidMoveFailure(){
        Queen q = new Queen(4,5,true,Color.WHITE);
        assertFalse("Queen should not be able to make a knights move",q.isValidMove(6,6));
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
