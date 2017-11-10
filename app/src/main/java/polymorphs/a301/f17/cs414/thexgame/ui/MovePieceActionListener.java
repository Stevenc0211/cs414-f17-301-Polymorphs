package polymorphs.a301.f17.cs414.thexgame.ui;

import android.graphics.Rect;

import java.util.ArrayList;

import polymorphs.a301.f17.cs414.thexgame.AppBackend.Driver;
import polymorphs.a301.f17.cs414.thexgame.AppBackend.User;
import polymorphs.a301.f17.cs414.thexgame.persistence.DBIOCore;

/**
 * Created by thenotoriousrog on 11/5/17.
 * This is a custom listener that doesn't really behave in the true listener sense of the word. What this does is takes in two parameters a row and a col. It then checks if a piece was selected using Tile to report that.
 * If a piece was selected then the class sets a boolean that tells the class the next row and col received will mean that that is the place that the user would like to move the piece to. If the move is valid,
 * Then the class will reset the piece on the newly selected tile, and remove the piece from the old tile.
 */
public class MovePieceActionListener {

    private boolean moveActionStarted = false; // set initially to false.
    private BoardUI boardUI; // copy of the boardUI to return values and control other UI elements in the UI such as highlighting tiles when a click is performed.
    private int fromRow = 0; // the row that was selected when the moveAction has started.
    private int fromCol = 0; // the col that was selected when teh moveActions has started.
    private TileUI fromTile; // the tile that was selected when the moveAction has started.
    private String pieceName = " "; // holds the name of the piece and what it contains and what not.
    private Driver driver; // holds a copy of the driver object that we are using.

    Rect tileRect; // copy

    private ArrayList<int[]> availableMoves = new ArrayList<>(); // this is the moves that we are trying to grab right here.

    MovePieceActionListener(BoardUI boardUI)
    {
        this.boardUI = boardUI;
        System.out.println("The driver in MovePieceActionListener = " + driver);
    }


    // resets all of the fields for the action click listener so that we can listen for another click event.
    private void reset()
    {
        moveActionStarted = false;
        fromRow = 0;
        fromCol = 0;
        fromTile = null;
        pieceName = " ";
    }

    // takes in the same list of moves and replaces the tile to be that of the normal green square.
    private void unhighlightSquares(ArrayList<int[]> availableMoves)
    {
        for(int i = 0; i < availableMoves.size(); i++)
        {
            int row = availableMoves.get(i)[0];
            int col = availableMoves.get(i)[1];
            TileUI tile = new TileUI(row, col, boardUI.getContext()); // create the new tile that we need to work with.
            boardUI.replaceAndUpdateTile(tile, row, col, " "); // replace this tile and update it show to that of the tiles.
        }
        // tell the board to hightlight the squares.
        // boardUI.setHighlightedSquares(availableMoves); // set's the available squares for the board to be able to redraw and highlight the squares as needed.
    }

    // This method takes in a list of available moves and tells the boardUI to highlight the board correctly.
    private void highlightSquares(ArrayList<int[]> availableMoves)
    {
        for(int i = 0; i < availableMoves.size(); i++)
        {
            int row = availableMoves.get(i)[0];
            int col = availableMoves.get(i)[1];
            TileUI tile = new TileUI(row, col, boardUI.getContext()); // create the new tile that we need to work with.
            boardUI.replaceAndUpdateTile(tile, row, col, "highlight"); // replace this tile and update it show to that of the tiles.
        }
        // tell the board to hightlight the squares.
        // boardUI.setHighlightedSquares(availableMoves); // set's the available squares for the board to be able to redraw and highlight the squares as needed.
    }

    // this is the method that is called whenever a tile is clicked.
    public void click(TileUI clickedTile, int row, int col, String pieceName)
    {
        // hardcoded this for now.
        if(moveActionStarted == false && !pieceName.equals(" ")) // no moveAction is currently in progress and the tile that was clicked has a piece on it if so, begin moving action.
        {
            //System.out.println("A click action has started!");

            // TODO: @Team, when a click action starts, we know that they clicked a piece, tell the board to highlight all of the available moves that this piece can make (highlighting not implemented yet).

            // set all of the items that we need to perform the move from the tile which is pretty important.
            fromTile = clickedTile;
            fromRow = row;
            fromCol = col;
            this.pieceName = pieceName;
            moveActionStarted = true; // a move action started has started.
            availableMoves = boardUI.getDriver().getAvailableMoves(row, col);


            if(availableMoves.isEmpty())
            {
                System.out.println("The moves available for this piece are null");
            }
            else
            {
                System.out.println("The piece that was clicked has name: " + pieceName);
                System.out.println("The number of available moves for the piece clicked is " + availableMoves.size());
                highlightSquares(availableMoves); // highlight the squares and wait for the next move to take place.
                boardUI.invalidate(); // force the UI to draw again.
            }


        }
        else if(moveActionStarted == true && !clickedTile.hasPiece()) // check to make sure that the clicked tile does not have a piece and a click action has already started.
        {
            //System.out.println("attempting to now move a piece!");
            // TODO: need to add a check inside tileUI to make sure that it is not a friendly tile that we are working with i.e. make sure we can remove pieces if a player attacks their opponents piece. This skips that for now.


            // TODO: @Team, we need to make sure that the pieces that clicks is not one of the his own. Basically ensure that the location that the player wants to move is a valid move.



            // TODO: need to check that the move that the user wants to do is a valid move.

            // TODO: REMOVE, this is a hack to test the UI
            String currentColor = boardUI.getDriver().getCurrentPlayerColor(fromRow,fromCol);
            User tmpUser = new User("null", "null", currentColor);
            if(boardUI.getDriver().makeMove(tmpUser, fromRow, fromCol, row, col) == 1)
            {
                // do the code below
                // move the piece and unhighlight the board.
                // create the updated tiles.
                TileUI updatedFromTile = new TileUI(fromRow, fromCol, boardUI.getContext()); // update the fromTile using the coordinates.
                TileUI updatedToTile = new TileUI(row, col, boardUI.getContext()); // create a new tile that will place the new tile into the board moving the pieces.

                // replace the tiles in the correct array.
                boardUI.replaceAndUpdateTile(updatedFromTile, fromRow, fromCol, " "); // update the fromTile to show no pieces.
                boardUI.replaceAndUpdateTile(updatedToTile, row, col, this.pieceName); // update the toTile which will then place the pieces where they need to be.
                unhighlightSquares(availableMoves);

                boardUI.invalidate(); // refresh the layout.
                reset(); // reset the move action listener.
            }
            else {
                unhighlightSquares(availableMoves); // takes the moves that were created and removes the blue highlights.
                boardUI.invalidate(); // refresh the layout.
            }



        }
    }
}
