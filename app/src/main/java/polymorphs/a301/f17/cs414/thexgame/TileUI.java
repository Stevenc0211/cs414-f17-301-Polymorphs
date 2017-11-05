package polymorphs.a301.f17.cs414.thexgame;

/**
 * * Found on https://gist.github.com/Oshuma/3352280 updated and modified for our implementation. ~ Roger
 */

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

public final class TileUI implements View.OnClickListener {
    private static final String TAG = TileUI.class.getSimpleName();

    private final int col;
    private final int row;

    private final Paint squareColor; // color of the square itself.
    private final Paint squareBorder; // color of the boarder of each square (black)
    private Rect tileRect;

    // TODO: @miles, @Andy should we allow the Piece class from backend be in here? It sure will make things easier for me to store certain pieces so I can see if this board has a piece or not.
    private boolean hasRook = false;
    private boolean hasKing = false;
    private boolean hasQueen = false;


    public boolean hasPiece()
    {
        if(hasRook == false && hasKing == false && hasQueen == false)
        {
            return false;
        }

        return true; // this tile has a piece on it.
    }

    // tells us if a tile is on the opponent castle wall.
    public boolean isOpponentCastle()
    {
        if(col == 7 && row == 10 ) {
            return true;
        }
        if(col == 8 && row == 10 ) {
            return true;
        }
        if(col == 9 && row == 10 ) {
            return true;
        }

        if(col == 6 && row == 9 ) {
            return true;
        }
        if(col == 6 && row == 8 ) {
            return true;
        }
        if(col == 6 && row == 7 ) {
            return true;
        }

        if(col == 10 && row == 9 ) {
            return true;
        }
        if(col == 10 && row == 9 ) {
            return true;
        }
        if(col ==10 && row == 8 ) {
            return true;
        }

        if(col == 10 && row == 7 ) {
            return true;
        }
        if(col == 9 && row == 6 ) {
            return true;
        }
        if(col == 8 && row == 6 ) {
            return true;
        }
        if(col == 7 && row == 6 ) {
            return true;
        }

        return false;
    }

    // tells the BoardUI if the current tile we are looking at is in the center of the player's castle (white)
    public boolean isPlayerKingLoc()
    {
        if(row == 3 && col == 3)
        {
            return true; // we are at the center of player's castle.
        }

        return false; // not at the center of the player's castle.
    }

    // tells the BoardUI if the current tile we are looking at is in the center of the player's castle (white)
    public boolean isOpponentKingLoc()
    {
        if(row == 8 && col == 8)
        {
            return true; // we are at the center of player's castle.
        }

        return false; // not at the center of the player's castle.
    }

    // returns true if tiles are within the player's castle.
    public boolean isPlayerCastle()
    {
        if(col == 5 && row == 4 ) {
            return true;
        }
        if(col == 5 && row == 3 ) {
            return true;
        }
        if(col == 5 && row == 2 ) {
            return true;
        }

        if(col == 4 && row == 5 ) {
            return true;
        }
        if(col == 3 && row == 5 ) {
            return true;
        }
        if(col == 4 && row == 5 ) {
            return true;
        }

        if(col == 2 && row == 5 ) {
            return true;
        }
        if(col == 1 && row == 4 ) {
            return true;
        }
        if(col ==1 && row == 3 ) {
            return true;
        }

        if(col == 1 && row == 2 ) {
            return true;
        }

        if(col == 4 && row == 1 ) {
            return true;
        }
        if(col == 3 && row ==  1) {
            return true;
        }
        if(col == 2 && row ==  1) {
            return true;
        }

        return false;
    }



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
        if(col == 5 && row == 4 ) {
            squareColor.setColor(Color.YELLOW);
        }
        if(col == 5 && row == 3 ) {
            squareColor.setColor(Color.YELLOW);
        }
        if(col == 5 && row == 2 ) {
            squareColor.setColor(Color.YELLOW);
        }

        if(col == 4 && row == 5 ) {
            squareColor.setColor(Color.YELLOW);
        }
        if(col == 3 && row == 5 ) {
            squareColor.setColor(Color.YELLOW);
        }
        if(col == 4 && row == 5 ) {
            squareColor.setColor(Color.YELLOW);
        }

        if(col == 2 && row == 5 ) {
            squareColor.setColor(Color.YELLOW);
        }
        if(col == 1 && row == 4 ) {
            squareColor.setColor(Color.YELLOW);
        }
        if(col ==1 && row == 3 ) {
            squareColor.setColor(Color.YELLOW);
        }

        if(col == 1 && row == 2 ) {
            squareColor.setColor(Color.YELLOW);
        }



        if(col == 4 && row == 1 ) {
            squareColor.setColor(Color.YELLOW);
        }
        if(col == 3 && row ==  1) {
            squareColor.setColor(Color.YELLOW);
        }
        if(col == 2 && row ==  1) {
            squareColor.setColor(Color.YELLOW);
        }
    }

    // constructor for tile
    public TileUI(final int row, final int col, Context context) {
        this.col = col;
        this.row = row;

        this.squareColor = new Paint();
        this.squareBorder = new Paint();


        //squareColor.setColor(isDark() ? ContextCompat.getColor(context, R.color.Green) : ContextCompat.getColor(context, R.color.Green));
        squareColor.setStyle(Paint.Style.FILL);
        squareColor.setColor(ContextCompat.getColor(context, R.color.Green)); // set the color to be green.

        squareBorder.setStyle(Paint.Style.STROKE); // the black lines of the board.
        squareBorder.setColor(Color.BLACK); // color of the stroke itself.
        squareBorder.setStrokeWidth(3);

        setOpponentBounds(); // set opponent bounds
        setPlayerBounds(); // set player bounds.

    }

    // calculate the size of each of the piece bitmaps. This is needed to properly set the resolution of the bitmap so that it does not cause memory problems in our application (App was becoming incredibly slow)
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)
    {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth)
            {
                inSampleSize *= 2;
            }
        }

        return inSampleSize; //size of the image that we should use
    }

    // This takes in a bitmap image from our resources, calculates the appropriate size of the bitmap, and returns the resolution optimum bitmap based on the height and width passed into the method.
    // Note: the reqWidth, and reqHeight, determine how high of resolution we want our bitmap to be, setting the correct sizes will determine the performance of our app.
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight)
    {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    // draws our board including tiles and pieces.
    public void draw(Canvas canvas, String pieceName, Context c) // TODO: decide if I need to send in the piece her when I call tile within chessboard so that we can be able to get this working properly.
    {
        canvas.drawRect(tileRect, squareColor); // canvas requires these two elements here.
        canvas.drawRect(tileRect, squareBorder); // set the boarder of the squares themselves

        Bitmap piece = null; // holds the piece that we want to draw.

        // TODO: need to efficiently draw the bitmaps here to ensure that the UI does not lag or get super super super slow.
        if(!pieceName.equals(" ")) // make sure that the name is not a space, it is a space, then we must not put a piece there, show only the board.
        {
            if(pieceName.equals("wrook")){ // set white rook
                piece = decodeSampledBitmapFromResource(c.getResources(), R.drawable.wrook, 100, 100);
                hasRook = true;
                hasKing = false;
                hasQueen = false;
            }
            else if(pieceName.equals("wking")){ // set white king
                piece = decodeSampledBitmapFromResource(c.getResources(), R.drawable.wking, 100, 100);
                hasRook = false;
                hasKing = true;
                hasQueen = false;
            }
            else if(pieceName.equals("wqueen")){ // set white queen
                piece = decodeSampledBitmapFromResource(c.getResources(), R.drawable.wqueen, 100, 100);
                hasRook = false;
                hasKing = false;
                hasQueen = true;
            }
            else if(pieceName.equals("brook")){ // set black rook
                piece = decodeSampledBitmapFromResource(c.getResources(), R.drawable.brook, 100, 100);
                hasRook = true;
                hasKing = false;
                hasQueen = false;
            }
            else if(pieceName.equals("bking")){ // set black king
                piece = decodeSampledBitmapFromResource(c.getResources(), R.drawable.bking, 100, 100);
                hasRook = false;
                hasKing = true;
                hasQueen = false;
            }
            else if(pieceName.equals("bqueen")){ // set black queen
                piece = decodeSampledBitmapFromResource(c.getResources(), R.drawable.bqueen, 100, 100);
                hasRook = false;
                hasKing = false;
                hasQueen = true;
            }

            canvas.drawBitmap(piece, null, tileRect, null); // draw our bitmap image.
            hasRook = false;
            hasKing = false;
            hasQueen = false;
        }
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

    // In here is where we will handle the controls for when a tile is touched. We will have to look to see if there is a piece set on that tile.
    public void handleTouch()
    {
        if(hasPiece() == true)
        {
            // TODO: if the piece was touched has a piece on it, we need to highlight all of the squares that this piece can go to and then listen for the next click and decide if the piece can in fact go there.
            // this is likely going to be done in BoardUI, however, I have not finished the algorithm for it quite yet.
            //
        }
        else {} // ignore the touch, we do not need to do anything with this right now.
    }


    // checks if a particular tile is touched and if so returns true telling the board to trigger the highlighting of tiles if necessary.
    public boolean isTouched(final int x, final int y) {
        return tileRect.contains(x, y);
    }

    public void setTileRect(final Rect tileRect) {
        this.tileRect = tileRect;
    }

    public String toString() {
        final String column = getColumnString();
        final String row    = getRowString();
        return "<TileUI " + column + row + ">";
    }

    // This click listener is a little bit different
    @Override
    public void onClick(View view)
    {
        // this is just a test to see if a click listener would work in this class or not. If it does work, take the logic and the to do out of the handle touch and place it in here.
        Toast.makeText(view.getContext(), "TileUI at row = " + row + " col = " + col + " was clicked", Toast.LENGTH_SHORT).show();
    }
}