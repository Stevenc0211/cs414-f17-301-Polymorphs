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
            Tile tile = new Tile(0,4);
        }
        catch (Exception e){
            fail("ERROR: Tile failed to instantiate");
        }
    }

    @Test
    public void testIsOccupiedFalse(){
        Tile tile = new Tile(5,6);
        assertFalse("Should be false",tile.isOccupied());
    }

    @Test
    public void testOccupyTile(){
        Tile tile = new Tile(5,6);
        Rook rook = new Rook(4,5,true, Color.WHITE);
        tile.occupyTile(rook);
        assertTrue("Should be true",tile.isOccupied());
    }

    @Test
    public void testOccupyTileCapture(){
        Tile tile = new Tile(5,6);
        Queen queen1 = new Queen(4,4,true,Color.BLACK);
        Queen queen2 = new Queen(4,5,true,Color.WHITE);
        tile.occupyTile(queen1);
        tile.occupyTile(queen2);
        assertEquals(queen2,tile.getPiece());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOccupyTileSameColor(){
        Tile tile = new Tile(5,6);
        Queen queen1 = new Queen(4,4,true,Color.BLACK);
        Queen queen2 = new Queen(4,5,true,Color.BLACK);
        tile.occupyTile(queen1);
        tile.occupyTile(queen2);
    }

    @Test
    public void testPieceLocationUpdateOnOccupy() {
        Tile tile = new Tile(5,6);
        Queen queen = new Queen(4,4,true,Color.BLACK);
        tile.occupyTile(queen);
        assertTrue("ERROR: piece coordinates should update when occupying a tile", queen.getCol() == tile.getCol() && queen.getRow() == tile.getRow());
    }
}
