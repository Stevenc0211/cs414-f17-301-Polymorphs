package polymorphs.a301.f17.cs414.thexgame.AppBackend;

import org.junit.Test;

import polymorphs.a301.f17.cs414.thexgame.AppBackend.Color;
import polymorphs.a301.f17.cs414.thexgame.AppBackend.Piece;
import polymorphs.a301.f17.cs414.thexgame.AppBackend.Tile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by athai on 10/24/17.
 */

public class TestTile {
    @Test
    public void testInstanceCreation(){
        try{
            polymorphs.a301.f17.cs414.thexgame.Tile tile = new polymorphs.a301.f17.cs414.thexgame.Tile(0,4);
        }
        catch (Exception e){
            fail("ERROR: Tile failed to instantiate");
        }
    }

    @Test
    public void testIsOccupiedFalse(){
        polymorphs.a301.f17.cs414.thexgame.Tile tile = new polymorphs.a301.f17.cs414.thexgame.Tile(5,6);
        //assertFalse("Should be false",tile.isOccupied());
    }

    @Test
    public void testOccupyTile(){
        polymorphs.a301.f17.cs414.thexgame.Tile tile = new polymorphs.a301.f17.cs414.thexgame.Tile(5,6);
        //Piece piece = new Piece(4,5,true, Color.WHITE);
        //tile.occupyTile(piece);
        //assertTrue("Should be true",tile.isOccupied());
    }

    @Test
    public void testOccupyTileCapture(){
        polymorphs.a301.f17.cs414.thexgame.Tile tile = new polymorphs.a301.f17.cs414.thexgame.Tile(5,6);
        //Piece piece = new Piece(4,5,true, Color.WHITE);
        //Piece piece1 = new Piece(4,4,true,Color.BLACK);
        //tile.occupyTile(piece);
        //tile.occupyTile(piece1);
        //assertEquals(piece1,tile.getPiece());
    }
}
