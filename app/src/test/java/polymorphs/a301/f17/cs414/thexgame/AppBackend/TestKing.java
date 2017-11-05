package polymorphs.a301.f17.cs414.thexgame.AppBackend;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.fail;
import static org.junit.Assert.*;
/**
 * Created by athai on 11/1/17.
 */

public class TestKing {
    @Test
    public void testInstanceCreation(){
        try{
            King king = new King(4,5,true,Color.WHITE);
        }
        catch (Exception e){
            fail("ERROR: King failed to instantiate");
        }
    }

    @Test
    public void testIsValidMoveKnight(){
        King king = new King(3,8,true,Color.WHITE);
        assertTrue("Should be true",king.isValidMove(1,9));
    }

    @Test
    public void testIsValidMoveKing(){
        King king = new King(3,8,true,Color.WHITE);
        assertTrue("Should be true",king.isValidMove(3,9));
    }

    @Test
    public void testIsValidMoveFalse(){
        King king = new King(3,8,true,Color.WHITE);
        assertFalse("Should be false",king.isValidMove(5,6));
    }

    @Test
    public void testIsValidMoveKnight2(){
        King king = new King(3,8,true,Color.WHITE);
        assertTrue("Should be true",king.isValidMove(5,7));
    }

    @Test
    public void testIsValidMoveOutOfBound(){
        King king = new King(3,8,true,Color.WHITE);
        assertFalse("Should be false",king.isValidMove(16,7));
    }

    @Test
    public void testGetMovePathSize1(){
        Board board = new Board();
        King king = new King(3,8,true,Color.WHITE);
        ArrayList<Tile> temp = king.getMovePath(board,4,8);
        assertEquals(1,temp.size());
    }

    @Test
    public void testGetMovePathSize2(){
        Board board = new Board();
        King king = new King(3,8,true,Color.WHITE);
        ArrayList<Tile> temp = king.getMovePath(board,1,9);
        assertEquals(1,temp.size());
    }

    @Test
    public void testGetMovePathNull(){
        Board board = new Board();
        King king = new King(3,8,true,Color.WHITE);
        ArrayList<Tile> temp = king.getMovePath(board,3,6);
        assertNull(temp);
    }

    @Test
    public void testGetAllMovePathsSize(){
        Board board = new Board();
        King king = new King(3,8,true,Color.WHITE);
        ArrayList< ArrayList<Tile> > temp = king.getAllMovePaths(board);
        assertEquals(16,temp.size());
    }

    @Test
    public void testGetAllMovesValid(){
        Board board = new Board();
        King king = new King(3,8,true,Color.WHITE);
        ArrayList< ArrayList<Tile> > temp = king.getAllMovePaths(board);
        for (ArrayList<Tile> movePath : temp) {
            assertTrue("getAllMoves returned and invalid tile", king.isValidMove(movePath.get(movePath.size()-1).getRow(),movePath.get(movePath.size()-1).getCol()));
        }
    }
}
