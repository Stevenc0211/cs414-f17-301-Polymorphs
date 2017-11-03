package polymorphs.a301.f17.cs414.thexgame.AppBackend;

/**
 * Created by athai on 10/18/17.
 */

class Tile {
    private int row;
    private int col;
    private Piece piece;
    private Status status;

    public Tile(int row,int col){
        this.row = row;
        this.col = col;
        piece = null;
        status = Status.OUTSIDE;
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

    public void setTileStatus(Status status){
        this.status = status;
    }

    public Status getTileStatus(){
        return status;
    }

    public void occupyTile(Piece piece){
        //if tile is currently occupied with another piece and is not the same color
        if(this.piece != null && this.piece.getColor() != piece.getColor()){
            this.piece.setAvailable(false);
            this.piece = piece;
        }

        else if(this.piece != null && this.piece.getColor() == piece.getColor()){
            //do nothing, same color pieces cannnot occupy same tile
        }

        else{
            this.piece = piece;
        }
    }

}
