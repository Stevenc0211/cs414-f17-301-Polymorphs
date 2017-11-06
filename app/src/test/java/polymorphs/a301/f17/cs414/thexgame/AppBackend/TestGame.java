package polymorphs.a301.f17.cs414.thexgame.AppBackend;

import org.junit.Test;
import static org.junit.Assert.*;

import polymorphs.a301.f17.cs414.thexgame.AppBackend.Color;
import polymorphs.a301.f17.cs414.thexgame.AppBackend.Game;
import polymorphs.a301.f17.cs414.thexgame.AppBackend.User;
import polymorphs.a301.f17.cs414.thexgame.AppBackend.Player;
/**
 * Created by athai on 10/23/17. Updated and modified by Roger.
 */

public class TestGame {
    @Test
    public void testInstanceCreation(){
        User u = new User("a","b","c");
        User u1 = new User("e","f","g");
        try{
            Game game = new Game(u,u1);
        }
        catch (Exception e){
            fail("ERROR: Game failed to instantiate");
        }
    }

    @Test
    public void testGetUser1(){
        User user1 = new User("a","email","nickname");
        User user2 = new User("b","test","ddd");
        Game game = new Game(user1,user2);
        assertEquals(user1,game.getUser1());
    }

    @Test
    public void testGetUser2(){
        User user1 = new User("a","email","nickname");
        User user2 = new User("b","test","ddd");
        Game game = new Game(user1,user2);
        assertEquals(user2,game.getUser2());
    }

    @Test
    public void testMakeMoveValid()
    {
        User user1 = new User("a","email","nickname");
        User user2 = new User("b","test","ddd");
        Game game = new Game(user1,user2);

        Board board = new Board(); // make a new board.
        Rook rook = new Rook(0, 0, true, Color.WHITE); // create a new Rook

        assertEquals(true, game.makeMove(user1, 0,0, 10, 0 )); // move the rook from spot 0,0 to 0,10

        int newRowPos = rook.getRow(); // this is the row of the new move for the ray which should be 10.
        int newColPos = rook.getCol(); // this is the col of the new move for the rook which should be 0.

        assertEquals(newRowPos, 10); // should be true.
        assertEquals(newColPos, 0); // should be true.
      }

    @Test
    public void testMakeMoveInvalid()
    {
        User user1 = new User("a","email","nickname");
        User user2 = new User("b","test","ddd");
        Game game = new Game(user1,user2);

        Board board = new Board(); // make a new board.
        Rook rook = new Rook(0, 0, true, Color.WHITE); // create a new Rook

        assertEquals(false, game.makeMove(user1, 0,0, 1, 1 )); // should be false because rooks can only go horizontally and vertically.
    }

    @Test
    public void testToString(){
        User user1 = new User("a","email","nickname");
        User user2 = new User("b","test","ddd");
        Game game = new Game(user1,user2);
        assertEquals("3, 8, true, WHITE, 2, 7, true, WHITE, 3, 7, true, WHITE, 4, 7, true, WHITE, 2, 8, true, WHITE, 4, 8, true, WHITE, 2, 9, true, WHITE, 3, 9, true, WHITE, 4, 9, true, WHITE, 8, 3, true, BLACK, 7, 2, true, BLACK, 8, 2, true, BLACK, 9, 2, true, BLACK, 7, 3, true, BLACK, 9, 3, true, BLACK, 7, 4, true, BLACK, 8, 4, true, BLACK, 9, 4, true, BLACK",game.toString());
    }
}
