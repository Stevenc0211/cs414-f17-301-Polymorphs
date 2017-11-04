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
        // TODO: 10/23/17 implement
    }

    @Test
    public void testKingCheck()  // test kingCheck with a rook piece
    {
        // test kingCheck with a rook piece
        Board brook = new Board();
        Rook rookz = new Rook(0,5,true,Color.WHITE);
        King kingz = new King(0,1,true,Color.BLACK);
        brook.getTile(0,5).occupyTile(rookz);
        brook.getTile(0,1).occupyTile(kingz);
        boolean check = brook.kingInCheck(kingz);
        assertEquals(true, check); // check to see if the board is in check


    }
    @Test
    public void testKingInCheckqueen() {
        System.out.println("testing with a queen kingincheck");
        Board bqueen = new Board();
        Queen queenz = new Queen(2, 2, true, Color.WHITE);
        King kingzq = new King(1, 1, true, Color.BLACK);
        bqueen.getTile(2, 2).occupyTile(queenz);
        bqueen.getTile(1, 1).occupyTile(kingzq);
         boolean check1 = bqueen.kingInCheck(kingzq);
        assertEquals(true, check1);

    }

    @Test
    public void testKingInCheckmatefalse()
    {
        Board board = new Board();
        System.out.println("testing kingCheckmatefalse");
        Rook rookz = new Rook(0,1,true,Color.WHITE);
        King kingz = new King(0,0,true,Color.BLACK);
        board.getTile(0,1).occupyTile(rookz);
        board.getTile(0,0).occupyTile(kingz);
        boolean check = board.kingInCheckmate(kingz);
        assertEquals(false, check); // check to see if the board is in check


    }
    @Test
    public void testKingInCheckmatetrue()
    {
        Board board = new Board();
        System.out.println("testing kingCheckmatetrue");
        King kingz = new King(3,8,true,Color.BLACK);
        Rook rookz = new Rook(2,7,true,Color.WHITE);
       //Rook rook1 = new Rook(2,8,true,Color.WHITE);
       board.getTile(2,7).occupyTile(rookz);
        //board.getTile(2,8).occupyTile(rook1);
        board.getTile(3,8).occupyTile(kingz);
        boolean check = board.kingInCheckmate(kingz);
        assertEquals(false, check); // check to see if the board is in check


    }

    @Test
    public void testWithinCastle()
    {
        Board board = new Board();
     //   boolean check = board.withinCastle(1,7);
      //  assertEquals(true, check);
    }



}