package polymorphs.a301.f17.cs414.thexgame.AppBackend;

import java.util.ArrayList;

/**
 * Created by athai on 10/18/17.
 */

public class Board {
    private Tile[][] Location;
    private ArrayList <Piece> user1Pieces;
    private ArrayList <Piece> user2Pieces;

    public Board(){
        Location = new Tile[12][12];
    }
}
