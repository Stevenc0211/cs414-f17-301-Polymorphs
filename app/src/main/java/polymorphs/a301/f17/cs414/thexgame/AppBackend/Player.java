package polymorphs.a301.f17.cs414.thexgame.AppBackend;

import java.util.ArrayList;
import java.util.Date;
/**
 * Created by athai on 10/18/17.
 */

class Player {
    private User user; // saving the user instead to match between UI and backend
    private Color color;
    private ArrayList<Piece> pieces = new ArrayList<Piece>();

    public Player(User user,Color color){
        this.user = user;
        this.color = color;
        initializePieces();
    }

    public String getNickname(){
        return user.getNickname();
    }

    public Color getColor(){
        return color;
    }

    public boolean equals(Object o) {
        if (!(o instanceof Player)) return false;
        Player otherPlayer = (Player) o;
        return (user.equals(otherPlayer.user) && color == otherPlayer.color);
    }

    public ArrayList<Piece> getPieces(){
        return pieces;
    }

    public void initializePieces(){
        if(color.equals(Color.WHITE)){
            pieces.add(new King(8,3,true,Color.WHITE));
            pieces.add(new Rook(7,2,true,Color.WHITE));
            pieces.add(new Rook(8,2,true,Color.WHITE));
            pieces.add(new Rook(9,2,true,Color.WHITE));
            pieces.add(new Rook(7,3,true,Color.WHITE));
            pieces.add(new Rook(9,3,true,Color.WHITE));
            pieces.add(new Rook(7,4,true,Color.WHITE));
            pieces.add(new Rook(8,4,true,Color.WHITE));
            pieces.add(new Rook(9,4,true,Color.WHITE));
        }
        //else BLACK
        else{
            pieces.add(new King(3,8,true,Color.BLACK));
            pieces.add(new Rook(2,7,true,Color.BLACK));
            pieces.add(new Rook(3,7,true,Color.BLACK));
            pieces.add(new Rook(4,7,true,Color.BLACK));
            pieces.add(new Rook(2,8,true,Color.BLACK));
            pieces.add(new Rook(4,8,true,Color.BLACK));
            pieces.add(new Rook(2,9,true,Color.BLACK));
            pieces.add(new Rook(3,9,true,Color.BLACK));
            pieces.add(new Rook(4,9,true,Color.BLACK));
        }
    }

    /**
     * This is used by the board to determine if the players king is in check.
     * @return the players king, null if the king is not in pieces (SHOULD NOT HAPPEN)
     */
    public King getKing() {
        for (Piece piece : pieces) {
            if (piece instanceof King) {
                return (King) piece;
            }
        }
        return null;
    }

    /**
     * Takes in a Rook and returns a Queen with all piece parameters the same as the passed Rook.
     * Also removes the old Rook from the players pieces and adds the new queen
     * @param rook - the rook to premote
     * @return  the new Queen if the passed rook existed in the players pieces, null if otherwise
     */
    public Queen promoteRook(Rook rook) {
        int idx = pieces.indexOf(rook);
        if (idx == -1) return null;
        Queen newQueen = new Queen(rook.getRow(), rook.getCol(), rook.isAvailable(), rook.getColor());
        pieces.set(idx, newQueen);
        return newQueen;
    }

    public String toString() {
        String temp = "";
        for (int i = 0; i < pieces.size() - 1; i++) {
            temp += pieces.get(i).toString();
            temp += ", ";
        }
        temp += pieces.get(pieces.size() - 1).toString();
        return temp;
    }
}
