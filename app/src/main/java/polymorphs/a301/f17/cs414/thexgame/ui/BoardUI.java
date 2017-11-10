package polymorphs.a301.f17.cs414.thexgame.ui;

/**
 * Found on https://gist.github.com/Oshuma/3352280 updated and modified for our implementation. ~Roger
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.service.quicksettings.Tile;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import polymorphs.a301.f17.cs414.thexgame.AppBackend.Driver;


public final class BoardUI extends View {
    private static final String TAG = BoardUI.class.getSimpleName();

    private static final int ROWS = 12;
    private static final int COLS = 12;
    private Canvas canvas; // holds the canvas for this class in charge of drawing everything, very important that it is done correctly.

    private boolean newlyStarted = true; // tells the board UI that this game is newly started and needs to generate the castle walls and what not.
    private final TileUI[][] tileUIs;
    private MovePieceActionListener movePieceActionListener; // the move piece action listener that starts the moving of pieces.
    private ArrayList<int[]> highlightedSquares = new ArrayList<>(); // these are the squares that need to be highlighted. Very, very important.

    private int x0 = 0;
    private int y0 = 0;
    private int squareSize = 0;



    private Driver driver; // the driver object that we are going to be using to communicate with the UI (this)

    /** 'true' if black is facing player. */
    private boolean flipped = false;

    // todo: have the board UI take in a Driver object so that we can correctly be able to communicate with the board and update tiles as needed. Very important
    public BoardUI(final Context context, final AttributeSet attrs ) {
        super(context, attrs);

        this.tileUIs = new TileUI[ROWS][COLS];
        setFocusable(true);
        movePieceActionListener = new MovePieceActionListener(this); // create a new MovePieceActionListener that will be in charge of setting everything else up.
        buildTiles();
    }

    public void setDriver(Driver driver)
    {
        System.out.println("setting the driver now!");
        System.out.println("The driver = " + driver);
        this.driver = driver;
    }

    // gets the driver for the MovePieceActionListener.
    public Driver getDriver()
    {
        return driver;
    }

    private void setCanvas(Canvas c)
    {
        canvas = c;
    }

    // returns the canvas for this board. Mainly used for MovePieceActionListener.
    public Canvas getCanvas()
    {
        return canvas;
    }

    // sets the squares that need to be highlighted, when onDraw is called after invalidate!
    public void setHighlightedSquares(ArrayList<int[]> squares)
    {
        highlightedSquares = squares;
    }

    private void buildTiles() {
        for (int rows = 0; rows < ROWS; rows++) {
            for (int cols = 0; cols < COLS; cols++) {
                final int r = rows;
                final int c = cols;
                tileUIs[rows][cols] = new TileUI(rows, cols, getContext());
            }
        }
    }

    // forces the UI to update.
    public void forceUIToRefresh()
    {
        invalidate();
    }

    // replace a tile with an updated tile.
    public void replaceAndUpdateTile(TileUI updatedTile, int row, int col, String pieceName)
    {
        Rect tileRect = new Rect(
                row,
                col,
                row + squareSize,
                col + squareSize
        );

        updatedTile.setTileRect(tileRect);
        updatedTile.setPieceName(pieceName);

        tileUIs[row][col] = updatedTile; // this is the tile we wish to create.

        //tileUIs[row][col].setTileRect(tileRect);
        //tileUIs[row][col].setPieceName(pieceName); // set the piece name for this tile.
    }

    // this method is called when onDraw is called and goes through each of the elements in the arrayList of squares and checks to see if there is a square that needs to be highlighted.
    private void highlightTiles(Canvas canvas, int rowToCheck, int colToCheck)
    {
        // iterate through each of the highlighted squares and if spots match, then we must color that square, very important.
        for(int i = 0; i < highlightedSquares.size(); i++)
        {
            int row = highlightedSquares.get(i)[0]; // the row of this highlighted square.
            int col = highlightedSquares.get(i)[1]; // the col of this highlighted square.

            if(row == rowToCheck && col == colToCheck) // we must highlight this square.
            {
                System.out.println("We have found a highlight in row = " + rowToCheck + " and col = " + colToCheck);
                tileUIs[rowToCheck][colToCheck].draw(canvas, "highlight", getContext()); // highlight this square, very important.
            }
        }
    }

    // Makes calls to tileUIs and tells it to graw the pieces and will eventually tell it what to do in terms of what to build and what not.
    @Override
    protected void onDraw(final Canvas canvas) {
        final int width = getWidth();
        final int height = getHeight();
        setCanvas(canvas); // set the canvas for the boardUI

        this.squareSize = Math.min(
                getSquareSizeWidth(width),
                getSquareSizeHeight(height)
        );

        computeOrigins(width, height);

        for (int rows = 0; rows < ROWS; rows++) {
            for (int cols = 0; cols < COLS; cols++) {
                final int xCoord = getXCoord(rows);
                final int yCoord = getYCoord(cols);

                final Rect tileRect = new Rect(
                        xCoord,               // left
                        yCoord,               // top
                        xCoord + squareSize,  // right
                        yCoord + squareSize   // bottom
                );

                tileUIs[rows][cols].setTileRect(tileRect); // set the tileRect which controls coloring of the tiles.
                // TODO: add the code here to check if this board ui class has just been started, if so, then we want to setup the game, otherwise we do no want to reset the pieces.


                // TODO: Need to make use of the Driver objec that will be sent in to help us generate the UI with the correct UI elements to ensure that everything is working out the way it should!

                if(newlyStarted == true) // generate the castle walls.
                {
                    // look for all of the pieces and ensure that the pieces have a place to end up on the castle wall.
                    if(tileUIs[rows][cols].isOpponentCastle() == true) {
                        tileUIs[rows][cols].draw(canvas, "brook", getContext()); // set black rooks on opponent's castle.
                    }
                    else if(tileUIs[rows][cols].isOpponentKingLoc() == true) {
                        tileUIs[rows][cols].draw(canvas, "bking", getContext()); // set black king inside opponent's castle.
                    }
                    else if(tileUIs[rows][cols].isPlayerCastle() == true) {
                        tileUIs[rows][cols].draw(canvas, "wrook", getContext()); // set white rook inside opponent's castle.
                    }
                    else if(tileUIs[rows][cols].isPlayerKingLoc() == true){
                        tileUIs[rows][cols].draw(canvas, "wking", getContext()); // set white king inside opponent's castle.
                    }
                    else {
                        tileUIs[rows][cols].draw(canvas, " ", getContext()); // set no pieces replace with a space to tell the tile to just draw the board.
                    }
                }

                // construct all of the pieces according to the tile that they are on.
                if(tileUIs[rows][cols].getPieceName().equals("wrook")) // if the tile has a white rook, draw it!
                {
                    tileUIs[rows][cols].draw(canvas, "wrook", getContext()); // set the rook, very important!
                }
                else if(tileUIs[rows][cols].getPieceName().equals("wqueen")) // if the tile has a white rook, draw it!
                {
                    tileUIs[rows][cols].draw(canvas, "wqueen", getContext()); // set the rook, very important!
                }
                else if(tileUIs[rows][cols].getPieceName().equals("wking")) // if the tile has a white rook, draw it!
                {
                    tileUIs[rows][cols].draw(canvas, "wking", getContext()); // set the rook, very important!
                }
                else if(tileUIs[rows][cols].getPieceName().equals("brook")) // if the tile has a white rook, draw it!
                {
                    tileUIs[rows][cols].draw(canvas, "brook", getContext()); // set the rook, very important!
                }
                else if(tileUIs[rows][cols].getPieceName().equals("bqueen")) // if the tile has a white rook, draw it!
                {
                    tileUIs[rows][cols].draw(canvas, "bqueen", getContext()); // set the rook, very important!
                }
                else if(tileUIs[rows][cols].getPieceName().equals("bking")) // if the tile has a white rook, draw it!
                {
                    tileUIs[rows][cols].draw(canvas, "bking", getContext()); // set the rook, very important!
                }
                else if(tileUIs[rows][cols].getPieceName().equals("highlight") ) // we have squares that we need to highlight.
                {
                    tileUIs[rows][cols].draw(canvas, "highlight", getContext());
                    //System.out.println("we are highlighting squares is row = " + rows);
                    // this causes my thing to be incorrect and this is a problem.!
                   // highlightTiles(canvas, rows, cols); // highlight the squares.
                }
                else // nothing to highlight, we need to write the green squares.
                {
                    tileUIs[rows][cols].draw(canvas, " ", getContext()); // set no pieces replace with a space to tell the tile to just draw the board.
                }

                // check for the board to be apart of the highlighting of the squares! Very important!


            }
        }
        newlyStarted = false; // tell the board that the game has been started and do not reset the castle borders.
    }

    // Listens for touches on the board, once x and y are satisfied, we want to trigger the click listener on the TileUI if it is a valid tild.
    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        final int x = (int) event.getX(); // get the x coordinate of the tile that was touched.
        final int y = (int) event.getY(); // get the y coordinate of the tile that was touched.

        TileUI tileUI; // holds the tile that was touched.

        // iterate through all of the tiles looking for the tile that was touched.
        for (int rows = 0; rows < ROWS; rows++)
        {
            for (int cols = 0; cols < COLS; cols++)
            {
                tileUI = tileUIs[rows][cols];

                // This will look for when a user releases their touch which let's us know that it is a click.
                if (tileUI.isTouched(x, y) && event.getAction() == MotionEvent.ACTION_UP)
                {
                    Toast.makeText(getContext(), "[row][col] = " + rows + ", " + cols, Toast.LENGTH_SHORT).show();
                    movePieceActionListener.click(tileUI, rows, cols, tileUI.getPieceName()); // determine a move click action listener.

                }
            }
        }

        return true;
    }

    private int getSquareSizeWidth(final int width) {
        return width / 12;
    }

    private int getSquareSizeHeight(final int height) {
        return height / 12;
    }

    private int getXCoord(final int x) {
        return x0 + squareSize * (flipped ? 12 - x : x);
    }

    private int getYCoord(final int y) {
        return y0 + squareSize * (flipped ? 12 : 7 - y);
    }

    private void computeOrigins(final int width, final int height) {
        this.x0 = (width  - squareSize * 12) / 2;
        this.y0 = (height - squareSize * 12) / 2;
    }

}