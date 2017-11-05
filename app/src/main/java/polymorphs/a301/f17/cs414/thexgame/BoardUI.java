package polymorphs.a301.f17.cs414.thexgame;

/**
 * Found on https://gist.github.com/Oshuma/3352280 updated and modified for our implementation. ~Roger
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


public final class BoardUI extends View {
    private static final String TAG = BoardUI.class.getSimpleName();

    private static final int ROWS = 12;
    private static final int COLS = 12;

    private final TileUI[][] tileUIs;

    private int x0 = 0;
    private int y0 = 0;
    private int squareSize = 0;

    /** 'true' if black is facing player. */
    private boolean flipped = false;

    public BoardUI(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        this.tileUIs = new TileUI[ROWS][COLS];

        setFocusable(true);

        buildTiles();
    }

    private void buildTiles() {
        for (int rows = 0; rows < ROWS; rows++) {
            for (int cols = 0; cols < COLS; cols++) {
                tileUIs[rows][cols] = new TileUI(rows, cols, getContext());

            }
        }
    }

    // Makes calls to tileUIs and tells it to graw the pieces and will eventually tell it what to do in terms of what to build and what not.
    @Override
    protected void onDraw(final Canvas canvas) {
        final int width = getWidth();
        final int height = getHeight();

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
        }
    }


    // Listens for touches on the board, once x and y are satisfied, we want to trigger the click listener on the TileUI if it is a valid tild.
    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        final int x = (int) event.getX();
        final int y = (int) event.getY();

        TileUI tileUI;
        for (int rows = 0; rows < ROWS; rows++) {
            for (int cols = 0; cols < COLS; cols++) {
                tileUI = tileUIs[rows][cols];
                if (tileUI.isTouched(x, y))
                {
                    tileUI.handleTouch();
                    // todo: the click event is triggered properly, but the click lasts way way too long, need to get that fixed up.
                    tileUI.onClick(this); // trigger the click event
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