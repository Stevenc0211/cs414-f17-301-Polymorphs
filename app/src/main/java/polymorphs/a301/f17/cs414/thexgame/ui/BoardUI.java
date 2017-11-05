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


public final class BoardUI extends View {
    private static final String TAG = BoardUI.class.getSimpleName();

    private static final int ROWS = 12;
    private static final int COLS = 12;
    private Canvas canvas; // holds the canvas for this class in charge of drawing everything, very important that it is done correctly.

    private boolean newlyStarted = true; // tells the board UI that this game is newly started and needs to generate the castle walls and what not.
    private final TileUI[][] tileUIs;
    private MovePieceActionListener movePieceActionListener; // the move piece action listener that starts the moving of pieces.

    private int x0 = 0;
    private int y0 = 0;
    private int squareSize = 0;

    /** 'true' if black is facing player. */
    private boolean flipped = false;

    public BoardUI(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        this.tileUIs = new TileUI[ROWS][COLS];

        setFocusable(true);

        movePieceActionListener = new MovePieceActionListener(this);

        buildTiles();
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

                tileUIs[rows][cols].setTileRect(tileRect); // set the color of the board tile here.

                // TODO: add the code here to check if this board ui class has just been started, if so, then we want to setup the game, otherwise we do no want to reset the pieces.

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
                else {
                    tileUIs[rows][cols].draw(canvas, " ", getContext()); // set no pieces replace with a space to tell the tile to just draw the board.
                }

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