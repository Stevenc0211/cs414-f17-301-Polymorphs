package polymorphs.a301.f17.cs414.thexgame.AppBackend;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by athai on 10/23/17. Edited and modified by Steven.
 */

public class TestBoard {

    @Test
    public void testInstanceCreation(){
        try{
            Board b = new Board();
        }
        catch (Exception e){
            fail("ERROR: Board failed to instantiate");
        }
    }

    private Board b;
    private Player white;

    @Before
    public void setup() {
        b = new Board();
        white = new Player(new User("test", "test", "user1"), Color.WHITE);
        for (Piece piece : white.getPieces()) {
            if (piece instanceof King) continue;
            piece.setAvailable(false);
        }
        b.getTile(white.getKing().getRow(), white.getKing().getCol()).occupyTile(white.getKing());
    }


    @Test
    public void testBoardSize(){
        assertEquals(144,b.getRows() * b.getColumns());
    }

    @Test
    public void testGetTile(){
        Tile t = b.getTile(3,5);
        assertTrue("Retrieved tile should have the same coordinates", t.getRow() == 3 && t.getCol() == 5);
    }

    @Test
    public void testGetTileOutOfBounds1(){
        Tile t = b.getTile(-1,5);
        assertNull("Should return a null if requested tile is out of bounds", t);
    }

    @Test
    public void testGetTileOutOfBounds2(){
        Tile t = b.getTile(6,12);
        assertNull("Should return a null if requested tile is out of bounds", t);
    }

    // ------------------ isValidMove Checks ------------------ //

    @Test
    public void testIsValidMoveTrue() {
        b.getTile(7,3).occupyTile(new Rook(7,3,true, Color.WHITE));
        assertTrue("Rook should be able to move up", b.isValidMove(white,7,3,5,3));
    }

    @Test
    public void testIsValidMoveNoPiece() {
        b.getTile(7,3).occupyTile(new Rook(7,3,true, Color.WHITE));
        assertFalse("Should not be able to move from 0,0 since tile does not contain a piece", b.isValidMove(white,0,0,0,3));
    }

    @Test
    public void testIsValidMoveWrongColor() {
        b.getTile(7,3).occupyTile(new Rook(7,3,true, Color.BLACK));
        assertFalse("Should not be able to move piece of a different color", b.isValidMove(white,7,3,5,3));
    }

    @Test
    public void testIsValidMoveInvalidPieceMove() {
        b.getTile(7,3).occupyTile(new Rook(7,3,true, Color.WHITE));
        assertFalse("Should not be able to piece move invalidly (Rook diagonal)", b.isValidMove(white,7,3,5,4));
    }

    @Test
    public void testIsValidMoveKingOutsideCastle() {
        b.getTile(7,3).occupyTile(white.getKing());
        assertFalse("King should not be able to move outside of castle", b.isValidMove(white,7,3,6,3));
    }

    @Test
    public void testIsValidMoveKingMoveIntoCheck() {
        b.getTile(7,7).occupyTile(new Rook(7,7,true,Color.BLACK));
        b.getTile(8,3).occupyTile(white.getKing());
        assertFalse("King should not be able to move into check", b.isValidMove(white,8,3,7,3));
    }

    @Test
    public void testIsValidMoveKingMoveIntoBlockedCheck() {
        b.getTile(7,7).occupyTile(new Rook(7,7,true,Color.BLACK));
        b.getTile(7,5).occupyTile(new Rook(7,5,true,Color.WHITE));
        b.getTile(8,3).occupyTile(white.getKing());
        assertTrue("King should be able to move since white rook is blocking check", b.isValidMove(white,8,3,7,3));
    }

    @Test
    public void testIsValidMoveKingMovesOutCheck() {
        b.getTile(7,7).occupyTile(new Rook(7,7,true,Color.BLACK));
        b.getTile(7,3).occupyTile(white.getKing());
        assertTrue("King should be able to move out of check", b.isValidMove(white,7,3,8,4));
    }

    @Test
    public void testIsValidMoveCheckNotRemoved() {
        b.getTile(7,7).occupyTile(new Rook(7,7,true,Color.BLACK));
        b.getTile(7,5).occupyTile(new Rook(7,5,true,Color.WHITE));
        b.getTile(7,3).occupyTile(white.getKing());
        assertFalse("Move should not be allowed since King remains in check", b.isValidMove(white,7,5,8,5));
    }

    @Test
    public void testIsValidMoveMoveCausesCheck() {
        b.getTile(7,7).occupyTile(new Rook(7,7,true,Color.BLACK));
        b.getTile(7,5).occupyTile(new Rook(7,5,true,Color.WHITE));
        b.getTile(7,3).occupyTile(white.getKing());
        assertFalse("Move should not be allowed since it places King in check", b.isValidMove(white,7,5,5,5));
    }

    @Test
    public void testIsValidMovePathThroughOtherPiece() {
        b.getTile(7,7).occupyTile(new Rook(7,7,true,Color.BLACK));
        b.getTile(7,5).occupyTile(new Rook(7,5,true,Color.WHITE));
        assertFalse("Move should not pass through other pieces", b.isValidMove(white,7,5,7,10));
    }

    // ---------- All capture tests ---------- //

    @Test
    public void testIsValidMoveFriendlyCapture() {
        b.getTile(7,7).occupyTile(new Rook(7,7,true,Color.WHITE));
        b.getTile(7,5).occupyTile(new Rook(7,5,true,Color.WHITE));
        assertFalse("Move should not end on a same colored piece", b.isValidMove(white,7,5,7,7));
    }

    @Test
    public void testIsValidMoveInvalidCapture1() { // outside -> outside
        b.getTile(7,8).occupyTile(new Rook(7,8,true,Color.BLACK));
        b.getTile(7,6).occupyTile(new Rook(7,6,true,Color.WHITE));
        assertFalse("Cannot capture unless one piece is on the enemies castle wall and other is inside it's castle",
                b.isValidMove(white,7,8,7,6));
    }

    @Test
    public void testIsValidMoveInvalidCapture2() { // wall -> outside
        b.getTile(7,8).occupyTile(new Rook(7,8,true,Color.BLACK));
        b.getTile(7,5).occupyTile(new Rook(7,5,true,Color.WHITE));
        assertFalse("Cannot capture unless one piece is on the enemies castle wall and other is inside it's castle",
                b.isValidMove(white,7,5,7,8));
    }

    @Test
    public void testIsValidMoveInvalidCapture3() { // wall -> outside
        b.getTile(7,8).occupyTile(new Rook(7,8,true,Color.BLACK));
        b.getTile(7,5).occupyTile(new Rook(7,5,true,Color.WHITE));
        assertFalse("Cannot capture unless one piece is on the enemies castle wall and other is inside it's castle",
                b.isValidMove(white,7,8,7,5));
    }

    @Test
    public void testIsValidMoveInvalidCapture4() { // wall -> wall
        b.getTile(6,3).occupyTile(new Rook(6,3,true,Color.BLACK));
        b.getTile(6,2).occupyTile(new Rook(6,2,true,Color.WHITE));
        assertFalse("Cannot capture unless one piece is on the enemies castle wall and other is inside it's castle",
                b.isValidMove(white,6,3,6,2));
    }

    @Test
    public void testIsValidMoveInvalidCapture5() { // castle -> castle
        b.getTile(7,3).occupyTile(new Rook(7,3,true,Color.BLACK));
        b.getTile(7,2).occupyTile(new Rook(7,2,true,Color.WHITE));
        assertFalse("Cannot capture unless one piece is on the enemies castle wall and other is inside it's castle",
                b.isValidMove(white,7,3,7,2));
    }

    @Test
    public void testIsValidMoveInvalidCapture6() { // your wall -> your castle
        b.getTile(7,5).occupyTile(new Rook(7,5,true,Color.WHITE));
        b.getTile(7,3).occupyTile(new Rook(7,3,true,Color.BLACK));
        assertFalse("Cannot capture unless one piece is on the enemies castle wall and other is inside it's castle",
                b.isValidMove(white,7,5,7,2));
    }

    @Test
    public void testIsValidMoveInvalidCapture7() { // your wall -> their castle
        b.getTile(7,5).occupyTile(new Queen(7,5,true,Color.WHITE));
        b.getTile(4,8).occupyTile(new Queen(4,8,true,Color.BLACK));
        assertFalse("Cannot capture unless one piece is on the enemies castle wall and other is inside it's castle",
                b.isValidMove(white,7,5,4,8));
    }

    @Test
    public void testIsValidMoveInvalidCapture8() { // your castle -> their wall
        b.getTile(8,4).occupyTile(new Queen(8,4,true,Color.WHITE));
        b.getTile(5,7).occupyTile(new Queen(5,7,true,Color.BLACK));
        assertFalse("Cannot capture unless one piece is on the enemies castle wall and other is inside it's castle",
                b.isValidMove(white,8,4,5,7));
    }

    @Test
    public void testIsValidMoveValidCapture1() { // your castle -> your wall
        b.getTile(8,4).occupyTile(new Queen(8,4,true,Color.WHITE));
        b.getTile(7,5).occupyTile(new Queen(7,5,true,Color.BLACK));
        assertTrue("Capture should be valid",b.isValidMove(white,8,4,7,5));
    }

    @Test
    public void testIsValidMoveValidCapture2() { // their wall -> their castle
        b.getTile(3,6).occupyTile(new Queen(3,6,true,Color.WHITE));
        b.getTile(3,7).occupyTile(new Queen(37,5,true,Color.BLACK));
        assertTrue("Capture should be valid",b.isValidMove(white,3,6,3,7));
    }

    @Test
    public void testIsValidMoveValidCapture3() { // king captures in castle (king move)
        b.getTile(8,4).occupyTile(new Queen(8,4,true,Color.BLACK));
        b.getTile(8,3).occupyTile(white.getKing());
        assertTrue("Capture should be valid",b.isValidMove(white,8,3,8,4));
    }

    @Test
    public void testIsValidMoveValidCapture4() { // king captures in castle (knights move)
        b.getTile(7,2).occupyTile(new Queen(7,2,true,Color.BLACK));
        b.getTile(9,3).occupyTile(white.getKing());
        assertTrue("Capture should be valid",b.isValidMove(white,9,3,7,2));
    }

    @Test
    public void testKingInCheckmateTrue() {
        b.getTile(9,3).occupyTile(white.getKing());
        b.getTile(0,2).occupyTile(new Rook(0,2,true,Color.BLACK));
        b.getTile(0,3).occupyTile(new Rook(0,3,true,Color.BLACK));
        b.getTile(0,4).occupyTile(new Rook(0,4,true,Color.BLACK));
        assertTrue("King should be in checkmate", b.inCheckmate(white));
    }

    @Test
    public void testKingInCheckmateFalse1() { // no in check
        b.getTile(9,3).occupyTile(white.getKing());
        b.getTile(0,2).occupyTile(new Rook(0,2,true,Color.BLACK));
        b.getTile(0,4).occupyTile(new Rook(0,4,true,Color.BLACK));
        assertFalse("King should not be in checkmate", b.inCheckmate(white));
    }

    @Test
    public void testKingInCheckmateFalse2() { // can move out of check
        b.getTile(9,3).occupyTile(white.getKing());
        b.getTile(0,3).occupyTile(new Rook(0,3,true,Color.BLACK));
        assertFalse("King should not be in checkmate", b.inCheckmate(white));
    }

    @Test
    public void testKingInCheckmateFalse3() { // another piece can block check
        b.getTile(9,3).occupyTile(white.getKing());
        b.getTile(0,2).occupyTile(new Rook(0,2,true,Color.BLACK));
        b.getTile(0,3).occupyTile(new Rook(0,3,true,Color.BLACK));
        b.getTile(0,4).occupyTile(new Rook(0,4,true,Color.BLACK));
        Piece tmp = white.getPieces().get(0);
        if (!(tmp instanceof Rook)) {
            tmp = white.getPieces().get(1);
        }
        Rook whiteRook = (Rook) tmp;
        whiteRook.setAvailable(true);
        b.getTile(1,5).occupyTile(whiteRook);
        assertFalse("King should not be in checkmate", b.inCheckmate(white));
    }

    @Test
    public void testKingInCheckmateFalse4() { // another piece can capture checking piece
        b.getTile(7,3).occupyTile(white.getKing());
        b.getTile(6,2).occupyTile(new Rook(0,2,true,Color.BLACK));
        b.getTile(6,3).occupyTile(new Rook(0,3,true,Color.BLACK));
        b.getTile(6,4).occupyTile(new Rook(0,4,true,Color.BLACK));
        Piece tmp = white.getPieces().get(0);
        if (!(tmp instanceof Rook)) {
            tmp = white.getPieces().get(1);
        }
        Rook whiteRook = (Rook) tmp;
        whiteRook.setAvailable(true);
        b.getTile(7,2).occupyTile(white.promoteRook(whiteRook));
        assertFalse("King should not be in checkmate", b.inCheckmate(white));
    }

}