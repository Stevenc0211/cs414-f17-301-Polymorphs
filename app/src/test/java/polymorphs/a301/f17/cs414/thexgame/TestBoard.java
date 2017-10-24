package polymorphs.a301.f17.cs414.thexgame;

import org.junit.Test;
import static org.junit.Assert.*;
import polymorphs.a301.f17.cs414.thexgame.AppBackend.Board;
import polymorphs.a301.f17.cs414.thexgame.AppBackend.Tile;

/**
 * Created by athai on 10/23/17.
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
}
