package polymorphs.a301.f17.cs414.thexgame.AppBackend;

import org.junit.Test;
import static org.junit.Assert.*;
import polymorphs.a301.f17.cs414.thexgame.AppBackend.Board;
import polymorphs.a301.f17.cs414.thexgame.AppBackend.Tile;

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

    @Test
    public void testBoardArrayList(){
        Board b = new Board();
        assertEquals(144,b.getBoard().length * b.getBoard()[0].length);
    }

    @Test
    public void testGetTile(){
        Board b = new Board();
        Tile t = b.getTile(4,4);
        assertEquals(t,b.getTile(4,4));
    }

    @Test
    public void testGetBoard(){
        // @Miles, @Andy, still not sure on this one, will implement if I have time to, but if you can get it done that would be great.
    }

    @Test
    public void testKingCheck()
    {
        Board board = new Board();
        Rook rookz = new Rook(0,0,true,Color.WHITE);
        King kingz = new King(0,1,true,Color.BLACK);
        boolean check = board.kingInCheck(kingz);
        assertEquals(true, check); // check to see if the board is in check


    }

    @Test
    public void testKingInCheckmate()
    {
        Board board = new Board();
        Rook rookz = new Rook(0,0,true,Color.WHITE);
        King kingz = new King(0,1,true,Color.BLACK);
        boolean check = board.kingInCheckmate(kingz);
        assertEquals(true, check); // check to see if the board is in check


    }

    @Test
    public void testWithinCastle()
    {
        Board board = new Board();
        boolean check = board.withinCastle(1,7);
        assertEquals(true, check);
    }



}