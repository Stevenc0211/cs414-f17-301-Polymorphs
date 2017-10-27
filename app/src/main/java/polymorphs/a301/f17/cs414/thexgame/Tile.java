package polymorphs.a301.f17.cs414.thexgame;

/**
 * * Found on https://gist.github.com/Oshuma/3352280 updated and modified for our implementation. ~ Roger
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public final class Tile {
    private static final String TAG = Tile.class.getSimpleName();

    private final int col;
    private final int row;

    private final Paint squareColor;
    private Rect tileRect;

    // sets the bounds of the opponent
    protected void setOpponentBounds()
    {
        if(col == 7 && row == 10 ) {
            squareColor.setColor(Color.YELLOW);
        }
        if(col == 8 && row == 10 ) {
            squareColor.setColor(Color.YELLOW);
        }
        if(col == 9 && row == 10 ) {
            squareColor.setColor(Color.YELLOW);
        }

        if(col == 6 && row == 9 ) {
            squareColor.setColor(Color.YELLOW);
        }
        if(col == 6 && row == 8 ) {
            squareColor.setColor(Color.YELLOW);
        }
        if(col == 6 && row == 7 ) {
            squareColor.setColor(Color.YELLOW);
        }

        if(col == 10 && row == 9 ) {
            squareColor.setColor(Color.YELLOW);
        }
        if(col == 10 && row == 9 ) {
            squareColor.setColor(Color.YELLOW);
        }
        if(col ==10 && row == 8 ) {
            squareColor.setColor(Color.YELLOW);
        }

        if(col == 10 && row == 7 ) {
            squareColor.setColor(Color.YELLOW);
        }
        if(col == 9 && row == 6 ) {
            squareColor.setColor(Color.YELLOW);
        }
        if(col == 8 && row == 6 ) {
            squareColor.setColor(Color.YELLOW);
        }
        if(col == 7 && row == 6 ) {
            squareColor.setColor(Color.YELLOW);
        }
    }

    // sets the player bounds.
    protected void setPlayerBounds()
    {
        if(col == 4 && row == 3 ) {
            squareColor.setColor(Color.YELLOW);
        }
        if(col == 4 && row == 2 ) {
            squareColor.setColor(Color.YELLOW);
        }
        if(col == 4 && row == 1 ) {
            squareColor.setColor(Color.YELLOW);
        }

        if(col == 3 && row == 4 ) {
            squareColor.setColor(Color.YELLOW);
        }
        if(col == 2 && row == 4 ) {
            squareColor.setColor(Color.YELLOW);
        }
        if(col == 3 && row == 4 ) {
            squareColor.setColor(Color.YELLOW);
        }

        if(col == 1 && row == 4 ) {
            squareColor.setColor(Color.YELLOW);
        }
        if(col == 0 && row == 3 ) {
            squareColor.setColor(Color.YELLOW);
        }
        if(col ==0 && row == 2 ) {
            squareColor.setColor(Color.YELLOW);
        }

        if(col == 0 && row == 1 ) {
            squareColor.setColor(Color.YELLOW);
        }
        if(col == 9 && row == 6 ) {
            squareColor.setColor(Color.YELLOW);
        }
        if(col == 8 && row == 6 ) {
            squareColor.setColor(Color.YELLOW);
        }


        if(col == 3 && row == 0 ) {
            squareColor.setColor(Color.YELLOW);
        }
        if(col == 2 && row ==  0) {
            squareColor.setColor(Color.YELLOW);
        }
        if(col == 1 && row ==  0) {
            squareColor.setColor(Color.YELLOW);
        }
    }

    public Tile(final int col, final int row) {
        this.col = col;
        this.row = row;

        this.squareColor = new Paint();
        squareColor.setColor(isDark() ? Color.GREEN : Color.GREEN);

        setOpponentBounds(); // set opponent bounds
        setPlayerBounds(); // set player bounds.

    }

    public void draw(final Canvas canvas) {
        canvas.drawRect(tileRect, squareColor);
    }

    public String getColumnString() {
        switch (col) {
            case 0: return "A";
            case 1: return "B";
            case 2: return "C";
            case 3: return "D";
            case 4: return "E";
            case 5: return "F";
            case 6: return "G";
            case 7: return "H";
            default: return null;
        }
    }

    public String getRowString() {
        // To get the actual row, add 1 since 'row' is 0 indexed.
        return String.valueOf(row + 1);
    }

    public void handleTouch() {
        Log.d(TAG, "handleTouch(): col: " + col);
        Log.d(TAG, "handleTouch(): row: " + row);
    }

    public boolean isDark() {
        return (col + row) % 2 == 0;
    }

    public boolean isTouched(final int x, final int y) {
        return tileRect.contains(x, y);
    }

    public void setTileRect(final Rect tileRect) {
        this.tileRect = tileRect;
    }

    public String toString() {
        final String column = getColumnString();
        final String row    = getRowString();
        return "<Tile " + column + row + ">";
    }

}