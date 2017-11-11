package polymorphs.a301.f17.cs414.thexgame.AppBackend;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

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
    public void testDistance(){
        Queen q = new Queen(4,5,true,Color.WHITE);
        double temp = q.distance(4,5,4,10);
        assertEquals(5,temp,0);
    }

    private class TestablePiece extends Piece {

        public TestablePiece(int row, int col, boolean available, Color color) {
            super(row, col, available, color);
        }
    }

    @Test
    public void testIsValidMoveTrue() {
        TestablePiece piece = new TestablePiece(0,0,true,Color.BLACK);
        assertTrue("Move should be valid since it remains in bounds", piece.isValidMove(6,6));
    }

    @Test
    public void testIsValidMoveRowInvalid1() {
        TestablePiece piece = new TestablePiece(0,0,true,Color.BLACK);
        assertFalse("Move should be invalid since row < 0", piece.isValidMove(-1,6));
    }

    @Test
    public void testIsValidMoveRowInvalid2() {
        TestablePiece piece = new TestablePiece(0,0,true,Color.BLACK);
        assertFalse("Move should be invalid since row > 11", piece.isValidMove(12,6));
    }

    @Test
    public void testIsValidMoveColInvalid1() {
        TestablePiece piece = new TestablePiece(0,0,true,Color.BLACK);
        assertFalse("Move should be invalid since col < 0", piece.isValidMove(6,-1));
    }

    @Test
    public void testIsValidMoveColInvalid2() {
        TestablePiece piece = new TestablePiece(0,0,true,Color.BLACK);
        assertFalse("Move should be invalid since col > 11", piece.isValidMove(6,12));
    }

    // -------------- Move Path Tests -------------- //
    // Note: this uses Queen so all cardinal and diagonal directions may be tested

    @Test
    public void testGetMovePathUpSize(){
        Board b = new Board();
        Queen q = new Queen(4,5,true,Color.WHITE);
        ArrayList<Tile> movePath = q.getMovePath(b,1,5);
        assertTrue("Move path is the wrong size", 4 == movePath.size());
    }

    @Test
    public void testGetMovePathUpConsistency(){
        Board b = new Board();
        int row = 4;
        int col = 5;
        Queen q = new Queen(row,col,true,Color.WHITE);
        ArrayList<Tile> movePath = q.getMovePath(b,1,col);
        for(Tile tile : movePath) {
            assertTrue("Move path did not return the correct set of tiles", tile.getRow() == row && tile.getCol() == col);
            row--;
        }
    }

    @Test
    public void testGetMovePathUpRightSize(){
        Board b = new Board();
        Queen q = new Queen(4,5,true,Color.WHITE);
        ArrayList<Tile> movePath = q.getMovePath(b,1,8);
        assertTrue("Move path is the wrong size", 4 == movePath.size());
    }

    @Test
    public void testGetMovePathUpRightConsistency(){
        Board b = new Board();
        int row = 4;
        int col = 5;
        Queen q = new Queen(row,col,true,Color.WHITE);
        ArrayList<Tile> movePath = q.getMovePath(b,1,8);
        for(Tile tile : movePath) {
            assertTrue("Move path did not return the correct set of tiles", tile.getRow() == row && tile.getCol() == col);
            row--;
            col++;
        }
    }

    @Test
    public void testGetMovePathRightSize(){
        Board b = new Board();
        Queen q = new Queen(4,5,true,Color.WHITE);
        ArrayList<Tile> movePath = q.getMovePath(b,4,10);
        assertTrue("Move path is the wrong size", 6 == movePath.size());
    }

    @Test
    public void testGetMovePathRightConsistency(){
        Board b = new Board();
        int col = 5;
        int row = 4;
        Queen q = new Queen(row,col,true,Color.WHITE);
        ArrayList<Tile> movePath = q.getMovePath(b,4,10);
        for(Tile tile : movePath) {
            assertTrue("Move path did not return the correct set of tiles", tile.getRow() == row && tile.getCol() == col);
            col++;
        }
    }

    @Test
    public void testGetMovePathRightDownSize(){
        Board b = new Board();
        Queen q = new Queen(4,5,true,Color.WHITE);
        ArrayList<Tile> movePath = q.getMovePath(b,6,7);
        assertTrue("Move path is the wrong size", 3 == movePath.size());
    }

    @Test
    public void testGetMovePathRightDownConsistency(){
        Board b = new Board();
        int col = 5;
        int row = 4;
        Queen q = new Queen(row,col,true,Color.WHITE);
        ArrayList<Tile> movePath = q.getMovePath(b,6,7);
        for(Tile tile : movePath) {
            assertTrue("Move path did not return the correct set of tiles", tile.getRow() == row && tile.getCol() == col);
            col++;
            row++;
        }
    }

    @Test
    public void testGetMovePathDownSize(){
        Board b = new Board();
        Queen q = new Queen(4,5,true,Color.WHITE);
        ArrayList<Tile> movePath = q.getMovePath(b,10,5);
        assertTrue("Move path is the wrong size", 7 == movePath.size());
    }

    @Test
    public void testGetMovePathDownConsistency(){
        Board b = new Board();
        int row = 4;
        int col = 5;
        Queen q = new Queen(row,col,true,Color.WHITE);
        ArrayList<Tile> movePath = q.getMovePath(b,10,col);
        for(Tile tile : movePath) {
            assertTrue("Move path did not return the correct set of tiles", tile.getRow() == row && tile.getCol() == col);
            row++;
        }
    }

    @Test
    public void testGetMovePathLeftDownSize(){
        Board b = new Board();
        Queen q = new Queen(4,5,true,Color.WHITE);
        ArrayList<Tile> movePath = q.getMovePath(b,7,2);
        assertTrue("Move path is the wrong size", 4 == movePath.size());
    }

    @Test
    public void testGetMovePathLeftDownConsistency(){
        Board b = new Board();
        int row = 4;
        int col = 5;
        Queen q = new Queen(row,col,true,Color.WHITE);
        ArrayList<Tile> movePath = q.getMovePath(b,7,2);
        for(Tile tile : movePath) {
            assertTrue("Move path did not return the correct set of tiles", tile.getRow() == row && tile.getCol() == col);
            row++;
            col--;
        }
    }

    @Test
    public void testGetMovePathLeftSize(){
        Board b = new Board();
        Queen q = new Queen(4,5,true,Color.WHITE);
        ArrayList<Tile> movePath = q.getMovePath(b,4,0);
        assertTrue("Move path is the wrong size", 6 == movePath.size());
    }

    @Test
    public void testGetMovePathLeftConsistency(){
        Board b = new Board();
        int col = 5;
        int row = 4;
        Queen q = new Queen(row,col,true,Color.WHITE);
        ArrayList<Tile> movePath = q.getMovePath(b,4,0);
        for(Tile tile : movePath) {
            assertTrue("Move path did not return the correct set of tiles", tile.getRow() == row && tile.getCol() == col);
            col--;
        }
    }

    @Test
    public void testGetMovePathLeftUpSize(){
        Board b = new Board();
        Queen q = new Queen(4,5,true,Color.WHITE);
        ArrayList<Tile> movePath = q.getMovePath(b,0,1);
        assertTrue("Move path is the wrong size", 5 == movePath.size());
    }

    @Test
    public void testGetMovePathLeftUpConsistency(){
        Board b = new Board();
        int col = 5;
        int row = 4;
        Queen q = new Queen(row,col,true,Color.WHITE);
        ArrayList<Tile> movePath = q.getMovePath(b,0,1);
        for(Tile tile : movePath) {
            assertTrue("Move path did not return the correct set of tiles", tile.getRow() == row && tile.getCol() == col);
            col--;
            row--;
        }
    }

    @Test
    public void testToString(){
        King king = new King(4,4,true,Color.BLACK);
        assertEquals("King,4,4,true,BLACK",king.toString());
    }

    @Test
    public void testToStringWhite(){
        Queen queen = new Queen(1,6,false,Color.WHITE);
        assertEquals("Queen,1,6,false,WHITE",queen.toString());
    }
}
