package polymorphs.a301.f17.cs414.thexgame.AppBackend;

import java.util.ArrayList;
import java.util.Date;
/**
 * Created by athai on 10/18/17.
 */

public class Player {
    private String nickname;
    private Enum color;
    private ArrayList<Piece> pieces = new ArrayList<Piece>();
    public int King = 1;
    public int Rook = 8;
    public String timestamp = "";

    public Player(User user,Enum color){
        this.nickname = user.getNickname();
        this.color = color;
        initializePieces();
    }

    public String getNickname(){
        return nickname;
    }

    public Enum getColor(){
        return color;
    }

    public void setColor(Enum color){
        this.color = color;
    }

    public String getTimestamp(){
        //Use java.sql.Timestamp
        return timestamp;
    }

    public boolean equals(Object o) {
        if (!(o instanceof User)) return false;
        Player otherPlayer = (Player) o;
        return (nickname.equals(otherPlayer.nickname));
    }

    public ArrayList<Piece> getPieces(){
        return pieces;
    }

    public void initializePieces(){
        if(color.equals(Color.WHITE)){
            pieces.add(new King(3,8,true,Color.WHITE));
            pieces.add(new Rook(2,7,true,Color.WHITE));
            pieces.add(new Rook(3,7,true,Color.WHITE));
            pieces.add(new Rook(4,7,true,Color.WHITE));
            pieces.add(new Rook(2,8,true,Color.WHITE));
            pieces.add(new Rook(4,8,true,Color.WHITE));
            pieces.add(new Rook(2,9,true,Color.WHITE));
            pieces.add(new Rook(3,9,true,Color.WHITE));
            pieces.add(new Rook(4,9,true,Color.WHITE));
        }
        //else BLACK
        else{
            pieces.add(new King(8,3,true,Color.BLACK));
            pieces.add(new Rook(7,2,true,Color.BLACK));
            pieces.add(new Rook(8,2,true,Color.BLACK));
            pieces.add(new Rook(9,2,true,Color.BLACK));
            pieces.add(new Rook(7,3,true,Color.BLACK));
            pieces.add(new Rook(9,3,true,Color.BLACK));
            pieces.add(new Rook(7,4,true,Color.BLACK));
            pieces.add(new Rook(8,4,true,Color.BLACK));
            pieces.add(new Rook(9,4,true,Color.BLACK));
        }
    }
}
