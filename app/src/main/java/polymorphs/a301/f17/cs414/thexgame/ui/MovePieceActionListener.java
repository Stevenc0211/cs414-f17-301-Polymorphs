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
        availableMoves.clear(); // remove all of the moves from the available moves in order to reset everything that it should be reset to.
        boardUI.setHighlightedSquares(availableMoves);

        /* removed this. Again this is part of the old system.
        for(int i = 0; i < availableMoves.size(); i++)
        {
            int row = availableMoves.get(i)[0];
            int col = availableMoves.get(i)[1];
            TileUI tile = new TileUI(row, col, boardUI.getContext()); // create the new tile that we need to work with.
            boardUI.replaceAndUpdateTile(tile, row, col, " "); // replace this tile and update it show to that of the tiles.
        }
        */
        // tell the board to hightlight the squares.
        // boardUI.setHighlightedSquares(availableMoves); // set's the available squares for the board to be able to redraw and highlight the squares as needed.
    }

    // This method takes in a list of available moves and tells the boardUI to highlight the board correctly.
    private void highlightSquares(ArrayList<int[]> availableMoves)
    {

        boardUI.setHighlightedSquares(availableMoves); // set the available moves that will tell the board which ui elements should be used to generate the highlighted squares.
        /* removed for now to test the new square highlights. Just uncomment this to get back to the old system.
        for(int i = 0; i < availableMoves.size(); i++)
        {
            int row = availableMoves.get(i)[0];
            int col = availableMoves.get(i)[1];
            TileUI tile = new TileUI(row, col, boardUI.getContext()); // create the new tile that we need to work with.
            boardUI.replaceAndUpdateTile(tile, row, col, "highlight"); // replace this tile and update it show to that of the tiles.
        }
        */
        // tell the board to hightlight the squares.
        // boardUI.setHighlightedSquares(availableMoves); // set's the available squares for the board to be able to redraw and highlight the squares as needed.
    }

    User user1 = new User("tmp", "tmp", "white"); // BreadCrumb: turn order hack
    User user2 = new User("tmp", "tmp", "black"); // BreadCrumb: turn order hack
    User currentUser = user1; // BreadCrumb: turn order hack

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
            availableMoves = boardUI.getDriver().getAvailableMoves(row, col);

            if(availableMoves.isEmpty())
            {
                System.out.println("The moves available for this piece are null");
            }
            else
            {
                System.out.println("The piece that was clicked has name: " + pieceName);
                System.out.println("The number of available moves for the piece clicked is " + availableMoves.size());
                moveActionStarted = true; // a move action started has started.
                highlightSquares(availableMoves); // highlight the squares and wait for the next move to take place.
                boardUI.invalidate(); // force the UI to draw again.
            }


        }
        else if(moveActionStarted == true && !clickedTile.hasPiece()) // check to make sure that the clicked tile does not have a piece and a click action has already started.
        {
            // TODO: get user into here so we can verify turn order
            int moveResult = boardUI.getDriver().makeMove(currentUser, fromRow, fromCol, row, col);
            if(moveResult == 1)
            {
                if (currentUser.equals(user1)) {    // BreadCrumb: turn order hack
                    currentUser = user2;            // BreadCrumb: turn order hack
                } else {                            // BreadCrumb: turn order hack
                    currentUser = user1;            // BreadCrumb: turn order hack
                }                                   // BreadCrumb: turn order hack
                unhighlightSquares(availableMoves);
                // do the code below
                // move the piece and unhighlight the board.
                // create the updated tiles.
                TileUI updatedFromTile = new TileUI(fromRow, fromCol, boardUI.getContext()); // update the fromTile using the coordinates.
                TileUI updatedToTile = new TileUI(row, col, boardUI.getContext()); // create a new tile that will place the new tile into the board moving the pieces.

                // replace the tiles in the correct array.
                boardUI.replaceAndUpdateTile(updatedFromTile, fromRow, fromCol, " "); // update the fromTile to show no pieces.
                boardUI.replaceAndUpdateTile(updatedToTile, row, col, this.pieceName); // update the toTile which will then place the pieces where they need to be.

                boardUI.invalidate(); // refresh the layout.
                reset(); // reset the move action listener.
            }
            else if (moveResult == 0) {
                System.out.println("Move resulted in checkmate!");
                // TODO: still needs to be handled.
            }
            else if( moveResult == -1) // move was invalid
            {
                // remove the highlights but don't do anything else.
                unhighlightSquares(availableMoves);
                boardUI.invalidate();
                reset(); // reset the click action listener to allow for the rest of the board to behave in the way that it should
            }
            else if( moveResult == 2) // promote black rook to a queen.
            {
                TileUI updatedFromTile = new TileUI(fromRow, fromCol, boardUI.getContext()); // update the fromTile using the coordinates.
                TileUI updatedToTile = new TileUI(row, col, boardUI.getContext()); // create a new tile that will place the new tile into the board moving the pieces.

                System.out.println("A promotion has occurred!");

                if(this.pieceName.equals("wrook")) // check if a white queen had entered into a castle
                {
                    System.out.println("White rook was promoted!");
                    boardUI.replaceAndUpdateTile(updatedFromTile, fromRow, fromCol, " "); // update the fromTile to show no pieces.
                    boardUI.replaceAndUpdateTile(updatedToTile, row, col, "wqueen"); // update the toTile which will then place the pieces where they need to be.
                }
                else if(this.pieceName.equals("brook")) // check if a black queen has entered into a castle.
                {
                    System.out.println("Black rook was promoted!");
                    boardUI.replaceAndUpdateTile(updatedFromTile, fromRow, fromCol, " "); // update the fromTile to show no pieces.
                    boardUI.replaceAndUpdateTile(updatedToTile, row, col, "bqueen"); // update the toTile which will then place the pieces where they need to be.
                }

                unhighlightSquares(availableMoves); // remove the highlights.
                boardUI.invalidate(); // refresh the board layout.
                reset(); // reset the move action listener so that we are able to get things working.
            }
            else {
                unhighlightSquares(availableMoves); // takes the moves that were created and removes the blue highlights.
                boardUI.invalidate(); // refresh the layout.
                reset(); // reset the move action listener.
            }

        }
    }
}
