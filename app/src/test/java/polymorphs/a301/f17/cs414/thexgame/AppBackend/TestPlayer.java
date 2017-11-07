package polymorphs.a301.f17.cs414.thexgame.AppBackend;

import org.junit.Test;

import polymorphs.a301.f17.cs414.thexgame.AppBackend.Color;
import polymorphs.a301.f17.cs414.thexgame.AppBackend.Player;
import polymorphs.a301.f17.cs414.thexgame.AppBackend.User;
import static org.junit.Assert.*;
import static org.junit.Assert.fail;

/**
 * Created by athai on 10/24/17.
 */

public class TestPlayer {
    @Test
    public void testInstanceCreation(){
        User u = new User("a","b","c");
        try{
            Player p = new Player(u, Color.WHITE);
        }
        catch (Exception e){
            fail("ERROR: Board failed to instantiate");
        }
    }

    @Test
    public void testInitializePieces(){
        User u = new User("a","b","c");
        Player p = new Player(u, Color.WHITE);
        assertFalse("Should be false",p.getPieces().isEmpty());
    }

    @Test
    public void testInitializePiecesSize(){
        User u = new User("a","b","c");
        Player p = new Player(u, Color.WHITE);
        assertEquals(9,p.getPieces().size());
    }

    @Test
    public void testToString(){
        User u = new User("a","b","c");
        Player p = new Player(u, Color.WHITE);
        assertEquals("8, 3, true, WHITE, 7, 2, true, WHITE, 8, 2, true, WHITE, 9, 2, true, WHITE, 7, 3, true, WHITE, 9, 3, true, WHITE, 7, 4, true, WHITE, 8, 4, true, WHITE, 9, 4, true, WHITE", p.toString());
    }

    @Test
    public void testInitializedKing() {
        User u = new User("a","b","c");
        Player p = new Player(u, Color.WHITE);
        King k = p.getKing();
        assertNotNull("Player should have one King after initialization", k);
    }

    /**
     * Returns the count of all piece of the same type as the passed piece in the players pieces
     * @param testPiece
     * @return
     */
    private int countPieceInstances(Piece testPiece, Player p) {
        int count = 0;
        for (Piece piece : p.getPieces()) {
            if (piece.getClass() == testPiece.getClass()) {
                count++;
            }
        }
        return count;
    }

    @Test
    public void testInitializeRookCount() {
        User u = new User("a","b","c");
        Player p = new Player(u, Color.WHITE);
        Rook r = new Rook(0,0,true,Color.BLACK);
        assertTrue("Player should have 8 Rooks after initialization", 8 == countPieceInstances(r, p));
    }

    @Test
    public void testPromoteCopiesValues() {
        User u = new User("a","b","c");
        Player p = new Player(u, Color.WHITE);
        Piece test = p.getPieces().get(0);
        if (test instanceof King) {
            test = p.getPieces().get(1);
        }
        Rook r = (Rook) test;
        Queen q = p.promoteRook(r);
        assertTrue("Queen returned from premote should have the same values as the original rook",
                r.getCol() == q.getCol() && r.getRow() == q.getRow() && r.getColor() == q.getColor());
    }

    @Test
    public void testPromoteDecreasesRookCount() {
        User u = new User("a","b","c");
        Player p = new Player(u, Color.WHITE);
        Piece test = p.getPieces().get(0);
        if (test instanceof King) {
            test = p.getPieces().get(1);
        }
        Rook r = (Rook) test;
        int originalRookCount = countPieceInstances(r, p);
        Queen q = p.promoteRook(r);
        assertTrue("Players number of rooks should decrease by one", (originalRookCount-1) == countPieceInstances(r, p));
    }

    @Test
    public void testPromoteIncreasesQueenCount() {
        User u = new User("a","b","c");
        Player p = new Player(u, Color.WHITE);
        Piece test = p.getPieces().get(0);
        if (test instanceof King) {
            test = p.getPieces().get(1);
        }
        Rook r = (Rook) test;
        int originalQueenCount = countPieceInstances(new Queen(0,0,true,Color.WHITE), p);
        Queen q = p.promoteRook(r);
        assertTrue("Players number of rooks should decrease by one", (originalQueenCount+1) == countPieceInstances(q, p));
    }

    @Test
    public void testPromoteFailure() {
        User u = new User("a","b","c");
        Player p = new Player(u, Color.WHITE);
        Rook r = new Rook(0,0,true,Color.BLACK);
        Queen q = p.promoteRook(r);
        assertNull("The passed rook should not be in the players pieces list",q);
    }
}
