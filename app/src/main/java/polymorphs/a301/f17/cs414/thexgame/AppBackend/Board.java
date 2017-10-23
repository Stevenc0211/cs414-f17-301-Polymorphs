package polymorphs.a301.f17.cs414.thexgame.AppBackend;

import java.util.ArrayList;

/**
 * Created by athai on 10/18/17.
 */

public class Board {
    private Tile[][] board;
    private ArrayList <Piece> pieces;

    public Board(){
        super();
        board = new Tile[12][12];

        for(int i = 0; i < 12; i++){
            for(int j = 0; j < 12; j++){
                this.board[i][j] = new Tile(i,j);
            }
        }
    }

    public Tile getTile(int x,int y){
        return board[x][y];
    }
}
