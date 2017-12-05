package polymorphs.a301.f17.cs414.thexgame.AppBackend;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by athai on 10/23/17. Updated and modified by Roger.
 */

public class TestGame {
    @Test
    public void testInstanceCreation(){
        String name1 = "john";
        String name2 = "Jack";
        try{
            new Game(name1,name2);
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
        game = new Game(white.getNickname(),black.getNickname());
    }

    @Test
    public void testMakeMoveNotPlayersTurn() { // should be whites turn thus black cannot move
        setupNewGame();
        assertEquals("Black should not be able to move on whites turn", -1, game.makeMove(black.getNickname(),2,8, 0, 8 ));
    }


    @Test
    public void testMakeMoveTurnOrder() { // both players make a valid move with no chained moves
        setupNewGame();
        game.makeMove(white.getNickname(),7,3,5,3);
        assertEquals("Bottom center black rook should be able to move down 2",0 ,game.makeMove(black.getNickname(),4,8,6,8));
    }

    @Test
    public void testMakeMoveChain0() { // white 7,2 -> 2,2
        setupNewGame();
        assertEquals("Move should be valid, white 7,2 -> 2,2",0 ,game.makeMove(white.getNickname(),7,2,2,2));
    }

    @Test
    public void testMakeMoveChain1() { // black 4,7 -> 11,7
        setupNewGame();
        game.makeMove(white.getNickname(),7,2,2,2);
        assertEquals("Move should be valid, black 4,7 -> 11,7",0 ,game.makeMove(black.getNickname(),4,7,11,7));
    }

    // TODO: 11/10/2017 FIX TO STRING
    @Test
    public void testToString(){
        Game game = new Game("name1","name2");
        assertEquals("name1-King,8,3,true,WHITE*Rook,7,2,true,WHITE*Rook,8,2,true,WHITE*Rook,9,2,true,WHITE*Rook,7,3,true,WHITE*Rook,9,3,true,WHITE*Rook,7,4,true,WHITE*Rook,8,4,true,WHITE*Rook,9,4,true,WHITE|King,3,8,true,BLACK*Rook,2,7,true,BLACK*Rook,3,7,true,BLACK*Rook,4,7,true,BLACK*Rook,2,8,true,BLACK*Rook,4,8,true,BLACK*Rook,2,9,true,BLACK*Rook,3,9,true,BLACK*Rook,4,9,true,BLACK",game.toString());
    }

    @Test
    public void testMakeMoveChain2() { // white 7,3 -> 4,3
        setupNewGame();
        game.makeMove(white.getNickname(),7,2,2,2);
        game.makeMove(black.getNickname(),4,7,11,7);
        assertEquals("Move should be valid, white 7,3 -> 4,3",0 ,game.makeMove(white.getNickname(),7,3,4,3));
    }

    @Test
    public void testMakeMoveChain3() { // black 3,7 -> 10,7
        setupNewGame();
        game.makeMove(white.getNickname(),7,2,2,2);
        game.makeMove(black.getNickname(),4,7,11,7);
        game.makeMove(white.getNickname(),7,3,4,3);
        assertEquals("Move should be valid, white 7,3 -> 4,3",0 ,game.makeMove(black.getNickname(),3,7,10,7));
    }

    @Test
    public void testMakeMoveChain4() { // white 7,4 -> 7,5
        setupNewGame();
        game.makeMove(white.getNickname(),7,2,2,2);
        game.makeMove(black.getNickname(),4,7,11,7);
        game.makeMove(white.getNickname(),7,3,4,3);
        game.makeMove(black.getNickname(),3,7,10,7);
        assertEquals("Move should be valid, white 7,4 -> 8,4",0 ,game.makeMove(white.getNickname(),7,4,7,5));
    }

    @Test
    public void testMakeMoveChain5() { // black 2,7 -> 9,7
        setupNewGame();
        game.makeMove(white.getNickname(),7,2,2,2);
        game.makeMove(black.getNickname(),4,7,11,7);
        game.makeMove(white.getNickname(),7,3,4,3);
        game.makeMove(black.getNickname(),3,7,10,7);
        game.makeMove(white.getNickname(),7,4,7,5);
        assertEquals("Move should be valid, black 2,7 -> 9,7",0 ,game.makeMove(black.getNickname(), 2,7,9,7));
    }

    @Test
    public void testMakeMoveChain6() { // white 7,5 -> 7,7
        setupNewGame();
        game.makeMove(white.getNickname(),7,2,2,2);
        game.makeMove(black.getNickname(),4,7,11,7);
        game.makeMove(white.getNickname(),7,3,4,3);
        game.makeMove(black.getNickname(),3,7,10,7);
        game.makeMove(white.getNickname(),7,4,7,5);
        game.makeMove(black.getNickname(), 2,7,9,7);
        assertEquals("Move should be valid, white 7,5 -> 7,7",0 ,game.makeMove(white.getNickname(),7,5,7,7));
    }

    @Test
    public void testMakeMoveChain7() { // black 9,7 -> 9,8
        setupNewGame();
        game.makeMove(white.getNickname(),7,2,2,2);
        game.makeMove(black.getNickname(),4,7,11,7);
        game.makeMove(white.getNickname(),7,3,4,3);
        game.makeMove(black.getNickname(),3,7,10,7);
        game.makeMove(white.getNickname(),7,4,7,5);
        game.makeMove(black.getNickname(), 2,7,9,7);
        game.makeMove(white.getNickname(),7,5,7,7);
        assertEquals("Move should be valid, black 9,7 -> 9,8",0 ,game.makeMove(black.getNickname(), 9,7, 9,8));
    }

    @Test
    public void testMakeMoveCheckmate() {
        setupNewGame();
        game.makeMove(white.getNickname(),7,2,2,2);
        game.makeMove(black.getNickname(),4,7,11,7);
        game.makeMove(white.getNickname(),7,3,4,3);
        game.makeMove(black.getNickname(),3,7,10,7);
        game.makeMove(white.getNickname(),7,4,7,5);
        game.makeMove(black.getNickname(), 2,7,9,7);
        game.makeMove(white.getNickname(),7,5,7,7);
        game.makeMove(black.getNickname(), 9,7, 9,8);
        assertEquals("Move should result in checkmate, white 8,4 -> 3,4",0 ,game.makeMove(white.getNickname(), 8,4, 3,4));
    }

    @Test
    public void testUpdateFromSnapshot(){
        setupNewGame();
        game.makeMove(white.getNickname(),7,2,5,2);
        GameSnapshot snapshot = new GameSnapshot(game);
        Game newGame = new Game(white.getNickname(),black.getNickname());

        newGame.updateFromSnapshot(snapshot);
        assertEquals("Snap shot and game should be the same",game.toString(),newGame.toString());
    }
}
