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
    private Player black;

    @Before
    public void setup() {
        b = new Board();
        white = new Player("name", Color.WHITE);
        black = new Player("name", Color.BLACK);
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
        b.getTile(9,9).occupyTile(new Rook(9,9,true, Color.BLACK));
        assertFalse("Should not be able to move piece of a different color", b.isValidMove(white,9,9,9,10));
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
        b.getTile(9,9).occupyTile(new Rook(9,9,true,Color.WHITE));
        b.getTile(9,10).occupyTile(new Rook(9,10,true,Color.WHITE));
        assertFalse("ERROR: white should not be able to capture a white piece", b.isValidMove(white,9,9,9,10));
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

    // ---------- Checkmate tests ---------- //
    private Rook[] getBlackRooks(int number) {
        int idx = 0;
        Rook[] blackRooks = new Rook[number];
        for (Piece piece : black.getPieces()) {
            if (piece instanceof Rook) {
                if (idx < number && blackRooks[idx] == null) {
                    blackRooks[idx] = (Rook)piece;
                    idx++;
                    continue;
                }
            }
            piece.setAvailable(false);
        }
        return blackRooks;
    }

    @Test
    public void testKingInCheckmateTrue() {
        for (Piece piece : white.getPieces()) if (!(piece instanceof King)) piece.setAvailable(false);
        Rook[] blackRooks = getBlackRooks(3);
        b.getTile(9,3).occupyTile(white.getKing());
        b.getTile(0,2).occupyTile(blackRooks[0]);
        b.getTile(0,3).occupyTile(blackRooks[1]);
        b.getTile(0,4).occupyTile(blackRooks[2]);
        assertTrue("King should be in checkmate", b.getPlayerStatus(white, black) == 1);
    }

    @Test
    public void testKingInCheckmateFalse1() { // not in check
        for (Piece piece : white.getPieces()) if (!(piece instanceof King)) piece.setAvailable(false);
        Rook[] blackRooks = getBlackRooks(3);
        b.getTile(9,3).occupyTile(white.getKing());
        b.getTile(0,2).occupyTile(blackRooks[0]);
        b.getTile(0,4).occupyTile(blackRooks[1]);
        b.getTile(11,11).occupyTile(blackRooks[2]); // will return stalemate without extra rook
        assertTrue("King should not be in checkmate", b.getPlayerStatus(white, black) == 0);
    }

    @Test
    public void testKingInCheckmateFalse2() { // can move out of check
        for (Piece piece : white.getPieces()) if (!(piece instanceof King)) piece.setAvailable(false);
        Rook[] blackRooks = getBlackRooks(3);
        b.getTile(9,3).occupyTile(white.getKing());
        b.getTile(0,3).occupyTile(blackRooks[0]);
        b.getTile(10,11).occupyTile(blackRooks[1]); // will return stalemate without extra rooks
        b.getTile(11,11).occupyTile(blackRooks[2]); // will return stalemate without extra rooks
        assertTrue("King should not be in checkmate", b.getPlayerStatus(white, black) == 0);
    }

    @Test
    public void testKingInCheckmateFalse3() { // another piece can block check
        for (Piece piece : white.getPieces()) if (!(piece instanceof King)) piece.setAvailable(false);
        Rook[] blackRooks = getBlackRooks(3);
        b.getTile(9,3).occupyTile(white.getKing());
        b.getTile(0,2).occupyTile(blackRooks[0]);
        b.getTile(0,3).occupyTile(blackRooks[1]);
        b.getTile(0,4).occupyTile(blackRooks[2]);
        Piece tmp = white.getPieces().get(0);
        if (!(tmp instanceof Rook)) {
            tmp = white.getPieces().get(1);
        }
        Rook whiteRook = (Rook) tmp;
        whiteRook.setAvailable(true);
        b.getTile(1,5).occupyTile(whiteRook);
        assertTrue("King should not be in checkmate", b.getPlayerStatus(white, black) == 0);
    }

    @Test
    public void testKingInCheckmateFalse4() { // another piece can capture checking piece
        for (Piece piece : white.getPieces()) if (!(piece instanceof King)) piece.setAvailable(false);
        Rook[] blackRooks = getBlackRooks(3);
        b.getTile(7,3).occupyTile(white.getKing());
        b.getTile(6,2).occupyTile(blackRooks[0]);
        b.getTile(6,3).occupyTile(blackRooks[1]);
        b.getTile(6,4).occupyTile(blackRooks[2]);
        Piece tmp = white.getPieces().get(0);
        if (!(tmp instanceof Rook)) {
            tmp = white.getPieces().get(1);
        }
        Rook whiteRook = (Rook) tmp;
        whiteRook.setAvailable(true);
        b.getTile(7,2).occupyTile(white.promoteRook(whiteRook));
        assertTrue("King should not be in checkmate", b.getPlayerStatus(white, black) == 0);
    }

    // ---------- Stalemate tests ---------- //

    @Test
    public void testStalemateFalse1() {
        b.addPlayerPieces(white);
        b.addPlayerPieces(black);
        assertFalse("ERROR: starting board must not be stalemate", b.getPlayerStatus(white, black) == 2);
    }

    @Test
    public void testStalemateFalse2() {
        for (Piece piece : white.getPieces()) if (!(piece instanceof King)) piece.setAvailable(false);
        b.getTile(7,3).occupyTile(white.getKing());
        Rook[] blackRooks = getBlackRooks(3);
        b.getTile(6,11).occupyTile(blackRooks[0]);
        b.getTile(6,3).occupyTile(blackRooks[1]); // will return stalemate without extra rooks
        b.getTile(6,10).occupyTile(blackRooks[2]); // will return stalemate without extra rooks
        assertFalse("ERROR: king can move, should not be stalemate", b.getPlayerStatus(white, black) == 2);
    }

    @Test
    public void testStalemateFalse3() {
        for (Piece piece : white.getPieces()) if (!(piece instanceof King)) piece.setAvailable(false);
        b.getTile(8,3).occupyTile(white.getKing());
        Rook[] blackRooks = getBlackRooks(4);
        b.getTile(6,2).occupyTile(blackRooks[0]);
        b.getTile(6,4).occupyTile(blackRooks[1]);
        b.getTile(7,7).occupyTile(blackRooks[2]);
        b.getTile(9,9).occupyTile(blackRooks[3]);
        Piece tmp = white.getPieces().get(0);
        if (!(tmp instanceof Rook)) {
            tmp = white.getPieces().get(1);
        }
        Rook whiteRook = (Rook) tmp;
        whiteRook.setAvailable(true);
        b.getTile(0,0).occupyTile(white.promoteRook(whiteRook));
        assertFalse("ERROR: the rook can move, should not be stalemate", b.getPlayerStatus(white, black) == 2);
    }

    @Test
    public void testStalemateTrue1() {
        for (Piece piece : white.getPieces()) if (!(piece instanceof King)) piece.setAvailable(false);
        b.getTile(8,3).occupyTile(white.getKing());
        getBlackRooks(1);
        b.addPlayerPieces(black);
        assertTrue("ERROR: neither player has enough piece to preform checkmate", b.getPlayerStatus(white, black) == 2);
    }

    @Test
    public void testStalemateTrue2() {
        for (Piece piece : white.getPieces()) if (!(piece instanceof King)) piece.setAvailable(false);
        b.getTile(8,3).occupyTile(white.getKing());
        Rook[] blackRooks = getBlackRooks(4);
        b.getTile(6,2).occupyTile(blackRooks[0]);
        b.getTile(6,4).occupyTile(blackRooks[1]);
        b.getTile(7,7).occupyTile(blackRooks[2]);
        b.getTile(9,9).occupyTile(blackRooks[3]);
        assertTrue("ERROR: white has no moves but is not in check, should be stalemate", b.getPlayerStatus(white, black) == 2);
    }
    // ---------- Board init w/Player pieces tests ---------- //
    @Test
    public void testAddPlayerPieces() {
        try{
            b = new Board();
            b.addPlayerPieces(white);
        } catch (IllegalArgumentException e) {
            fail("White pieces should be added to an empty board without error");
        }
    }

    @Test
    public void testAddPlayerPiecesConsistency() {
        b = new Board();
        b.addPlayerPieces(white);
        for (Piece piece : white.getPieces()) {
            assertTrue("All player pieces should exist on the board", b.getTile(piece.getRow(), piece.getCol()).getPiece().equals(piece));
        }
    }

    @Test
    public void testInitWhiteRookPlacement() {
        Player black = new Player("name", Color.BLACK);
        b = new Board(white,black);
        int[][] coordinates = {{7,2},{7,3},{7,4},{8,2},{8,4},{9,2},{9,3},{9,4}};
        boolean test;
        for (int[] coord: coordinates) {
            test = b.getTile(coord[0],coord[1]).isOccupied();
            test = test & b.getTile(coord[0],coord[1]).getPiece().getColor() == Color.WHITE;
            test = test & b.getTile(coord[0],coord[1]).getPiece() instanceof Rook;
            assertTrue("All specified tiles should contain a white rook", test);
        }
    }

    @Test
    public void testInitWhiteKingPlacement() {
        b = new Board(white,black);
        boolean test = b.getTile(8,3).isOccupied();
        test = test & b.getTile(8,3).getPiece().getColor() == Color.WHITE;
        test = test & b.getTile(8,3).getPiece() instanceof King;
        assertTrue("Tile at 8,3 should contain the white king", test);
    }

    @Test
    public void testInitBlackRookPlacement() {
        Player black = new Player("name", Color.BLACK);
        b = new Board(white,black);
        int[][] coordinates = {{2,7},{2,8},{2,9},{3,7},{3,9},{4,7},{4,8},{4,9}};
        boolean test;
        for (int[] coord: coordinates) {
            test = b.getTile(coord[0],coord[1]).isOccupied();
            test = test & b.getTile(coord[0],coord[1]).getPiece().getColor() == Color.BLACK;
            test = test & b.getTile(coord[0],coord[1]).getPiece() instanceof Rook;
            assertTrue("All specified tiles should contain a black rook", test);
        }
    }

    @Test
    public void testInitBlackKingPlacement() {
        Player black = new Player("name", Color.BLACK);
        b = new Board(white,black);
        boolean test = b.getTile(3,8).isOccupied();
        test = test & b.getTile(3,8).getPiece().getColor() == Color.BLACK;
        test = test & b.getTile(3,8).getPiece() instanceof King;
        assertTrue("Tile at 3,8 should contain the black king", test);
    }

}