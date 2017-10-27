package polymorphs.a301.f17.cs414.thexgame.AppBackend;

/**
 * Created by athai on 10/18/17.
 */

class Tile {
    private int x;
    private int y;
    private Piece piece;

    public Tile(int x,int y){
        this.x = x;
        this.y = y;
        piece = null;
    }

    public Piece getPiece(){
        return piece;
    }

    public boolean isOccupied(){
        if(piece == null){
            return false;
        }
        else{
            return true;
        }
    }

    public void occupyTile(Piece piece){
        //if tile is currently occupied with another piece and is not the same color
        if(this.piece != null && this.piece.getColor() != piece.getColor()){
            this.piece.setAvailable(false);
        }
        this.piece = piece;
    }

}
