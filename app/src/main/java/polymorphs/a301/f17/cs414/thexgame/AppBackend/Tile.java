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


    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
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


    /**
     * This method takes the given piece and adds it to the tile, if there is another piece of the opposing color on this tile
     * that piece will be set to unavailable and will be replaced with the passed piece.
     * NOTE: the piece row and column will be updated with this tiles row and column
     * @param piece - the piece to place on the tile
     * @throws IllegalArgumentException - if a piece of the same color as the passed piece is allready on the tile
     */
    public void occupyTile(Piece piece) throws IllegalArgumentException {
        if (piece != null) {
            if(this.piece != null && this.piece.getColor() != piece.getColor()){
                this.piece.setAvailable(false);
            } else if(this.piece != null && this.piece.getColor() == piece.getColor()){
                throw new IllegalArgumentException("ERROR: this tile contained a piece of the same color");
            }
            piece.setRow(row);
            piece.setCol(col);
        }
        this.piece = piece;
    }


}
