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
            new Game(u,u1);
        }
        catch (Exception e){
            fail("ERROR: Game failed to instantiate");
        }
    }

    User white;
    User black;
    Game game;

    private void setupNewGame() {
        white = new User("a","email","nickname");
        black = new User("b","test","ddd");
        game = new Game(white,black);
    }

    @Test
    public void testGetUser1(){
        setupNewGame();
        assertEquals(white,game.getUser1());
    }

    @Test
    public void testGetUser2(){
        setupNewGame();
        assertEquals(black,game.getUser2());
    }

    @Test
    public void testMakeMoveNotPlayersTurn() { // should be whites turn thus black cannot move
        setupNewGame();
        assertEquals("Black should not be able to move on whites turn", -1, game.makeMove(black,2,8, 0, 8 ));
    }


    @Test
    public void testMakeMoveTurnOrder() { // both players make a valid move with no chained moves
        setupNewGame();
        game.makeMove(white,7,3,5,3);
        assertEquals("Bottom center black rook should be able to move down 2",1 ,game.makeMove(black,4,8,6,8));
    }

    @Test
    public void testMakeMoveChain0() { // white 7,2 -> 2,2
        setupNewGame();
        assertEquals("Move should be valid, white 7,2 -> 2,2",1 ,game.makeMove(white,7,2,2,2));
    }

    @Test
    public void testMakeMoveChain1() { // black 4,7 -> 11,7
        setupNewGame();
        game.makeMove(white,7,2,2,2);
        assertEquals("Move should be valid, black 4,7 -> 11,7",1 ,game.makeMove(black,4,7,11,7));
    }

    @Test
    public void testToString(){
        User user1 = new User("a","email","nickname");
        User user2 = new User("b","test","ddd");
        Game game = new Game(user1,user2);
        assertEquals("3, 8, true, WHITE, 2, 7, true, WHITE, 3, 7, true, WHITE, 4, 7, true, WHITE, 2, 8, true, WHITE, 4, 8, true, WHITE, 2, 9, true, WHITE, 3, 9, true, WHITE, 4, 9, true, WHITE, 8, 3, true, BLACK, 7, 2, true, BLACK, 8, 2, true, BLACK, 9, 2, true, BLACK, 7, 3, true, BLACK, 9, 3, true, BLACK, 7, 4, true, BLACK, 8, 4, true, BLACK, 9, 4, true, BLACK",game.toString());
    }

    @Test
    public void testMakeMoveChain2() { // white 7,3 -> 4,3
        setupNewGame();
        game.makeMove(white,7,2,2,2);
        game.makeMove(black,4,7,11,7);
        assertEquals("Move should be valid, white 7,3 -> 4,3",1 ,game.makeMove(white,7,3,4,3));
    }

    @Test
    public void testMakeMoveChain3() { // black 3,7 -> 10,7
        setupNewGame();
        game.makeMove(white,7,2,2,2);
        game.makeMove(black,4,7,11,7);
        game.makeMove(white,7,3,4,3);
        assertEquals("Move should be valid, white 7,3 -> 4,3",1 ,game.makeMove(black,3,7,10,7));
    }

    @Test
    public void testMakeMoveChain4() { // white 7,4 -> 7,5
        setupNewGame();
        game.makeMove(white,7,2,2,2);
        game.makeMove(black,4,7,11,7);
        game.makeMove(white,7,3,4,3);
        game.makeMove(black,3,7,10,7);
        assertEquals("Move should be valid, white 7,4 -> 8,4",1 ,game.makeMove(white,7,4,7,5));
    }

    @Test
    public void testMakeMoveChain5() { // black 2,7 -> 9,7
        setupNewGame();
        game.makeMove(white,7,2,2,2);
        game.makeMove(black,4,7,11,7);
        game.makeMove(white,7,3,4,3);
        game.makeMove(black,3,7,10,7);
        game.makeMove(white,7,4,7,5);
        assertEquals("Move should be valid, black 2,7 -> 9,7",1 ,game.makeMove(black, 2,7,9,7));
    }

    @Test
    public void testMakeMoveChain6() { // white 7,5 -> 7,7
        setupNewGame();
        game.makeMove(white,7,2,2,2);
        game.makeMove(black,4,7,11,7);
        game.makeMove(white,7,3,4,3);
        game.makeMove(black,3,7,10,7);
        game.makeMove(white,7,4,7,5);
        game.makeMove(black, 2,7,9,7);
        assertEquals("Move should be valid, white 7,5 -> 7,7",1 ,game.makeMove(white,7,5,7,7));
    }

    @Test
    public void testMakeMoveChain7() { // black 9,7 -> 9,8
        setupNewGame();
        game.makeMove(white,7,2,2,2);
        game.makeMove(black,4,7,11,7);
        game.makeMove(white,7,3,4,3);
        game.makeMove(black,3,7,10,7);
        game.makeMove(white,7,4,7,5);
        game.makeMove(black, 2,7,9,7);
        game.makeMove(white,7,5,7,7);
        assertEquals("Move should be valid, black 9,7 -> 9,8",1 ,game.makeMove(black, 9,7, 9,8));
    }

    @Test
    public void testMakeMoveCheckmate() {
        setupNewGame();
        game.makeMove(white,7,2,2,2);
        game.makeMove(black,4,7,11,7);
        game.makeMove(white,7,3,4,3);
        game.makeMove(black,3,7,10,7);
        game.makeMove(white,7,4,7,5);
        game.makeMove(black, 2,7,9,7);
        game.makeMove(white,7,5,7,7);
        game.makeMove(black, 9,7, 9,8);
        assertEquals("Move should result in checkmate, white 8,4 -> 3,4",0 ,game.makeMove(white, 8,4, 3,4));
    }

}
