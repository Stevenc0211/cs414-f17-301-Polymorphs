package polymorphs.a301.f17.cs414.thexgame.ui;

/**
 This is the TileUI that is in control of each tile within BoardUI. Written and worked on by Roger and Miles.
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

import polymorphs.a301.f17.cs414.thexgame.R;

public final class TileUI  {
    private static final String TAG = TileUI.class.getSimpleName();

    private final int col;
    private final int row;

    private final Paint squareColor; // color of the square itself.
    private final Paint squareBorder; // color of the boarder of each square (black)
    private final Paint highlightColor; // color of the highlight that we are working with (blue)
    private final Paint kingHighlightColor; // holds the color of the king hightlight (Red)
    private Rect tileRect; // holds the rectangle of the tile itself where pieces move to.
    private Rect highlightRect;  // holds the rectangle that will highlight the board.
    private Rect kingHighlightRect; // holds the rectangle that the king is on.

    private boolean hasRook = false;
    private boolean hasKing = false;
    private boolean hasQueen = false;
    private String pieceName = " "; // holds the name of the piece whether it be a rook, queen, or king, whether black or white.

    public boolean hasPiece()
    {
        if(hasRook == false && hasKing == false && hasQueen == false)
        {
            return false;
        }

        return true; // this tile has a piece on it.
    }

    // set the piecename for this tile.
    public void setPieceName(String name)
    {
        pieceName = name;
    }

    // returns the name of the piece that this tile contains.
    public String getPieceName()
    {
        return pieceName;
    }

    // tells us if a tile is on the opponent castle wall.
    public boolean isOpponentCastle()
    {

        if ((col == 7 || col == 8 || col == 9) && (row == 2 || row == 3 || row == 4)) {
            return true;
        }
        return false;
    }

    // tells the BoardUI if the current tile we are looking at is in the center of the player's castle (white)
    public boolean isPlayerKingLoc()
    {
        if(row == 8 && col == 3)
        {
            return true; // we are at the center of player's castle.
        }

        return false; // not at the center of the player's castle.
    }

    // tells the BoardUI if the current tile we are looking at is in the center of the player's castle (white)
    public boolean isOpponentKingLoc()
    {
        if(row == 3 && col == 8)
        {
            return true; // we are at the center of player's castle.
        }

        return false; // not at the center of the player's castle.
    }

    // returns true if tiles are within the player's castle.
    public boolean isPlayerCastle()
    {
        if ((row == 7 || row == 8 || row == 9) && (col == 2 || col == 3 || col == 4)) {
            return true;
        }

        return false;
    }



    // sets the bounds of the opponent
    protected void setOpponentBounds()
    {
        if ((row == 1 || row == 5) && (col == 7 || col == 8 || col == 9)) {
            squareColor.setColor(Color.YELLOW);
        }
        if ((col == 6 || col == 10) && (row == 2 || row == 3 || row == 4)) {
            squareColor.setColor(Color.YELLOW);
        }
    }

    // sets the player bounds.
    protected void setPlayerBounds()
    {
        if ((col == 1 || col == 5) && (row == 7 || row == 8 || row == 9)) {
            squareColor.setColor(Color.YELLOW);
        }
        if ((row == 6 || row == 10) && (col == 2 || col == 3 || col == 4)) {
            squareColor.setColor(Color.YELLOW);
        }

    }

    // constructor for tile
    public TileUI(final int row, final int col, Context context) {
        this.col = col;
        this.row = row;

        this.squareColor = new Paint();
        this.squareBorder = new Paint();
        this.highlightColor = new Paint();
        this.kingHighlightColor = new Paint();

        squareColor.setStyle(Paint.Style.FILL);
        squareColor.setColor(ContextCompat.getColor(context, R.color.Green)); // set the color to be green.

        squareBorder.setStyle(Paint.Style.STROKE); // the black lines of the board.
        squareBorder.setColor(Color.BLACK); // color of the stroke itself.
        squareBorder.setStrokeWidth(3);


        highlightColor.setStyle(Paint.Style.FILL);
        highlightColor.setColor(Color.BLUE); // color to tell the player what moves that they are able to work with!

        kingHighlightColor.setStyle(Paint.Style.FILL);
        kingHighlightColor.setColor(Color.RED); // color to tell the player who is currently in check.

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
    public void draw(Canvas canvas, String pieceName, Context c)
    {
        if(highlightRect != null) // draw the highlighted squares instead of the normal board sqaures.
        {
            canvas.drawRect(highlightRect, highlightColor);
            canvas.drawRect(highlightRect, squareBorder);
        }
        else if(kingHighlightRect != null) // draw the color for the king if it is in check.
        {
            canvas.drawRect(kingHighlightRect, kingHighlightColor);
            canvas.drawRect(kingHighlightRect, squareBorder);
        }
        else // color the board in the regular color style.
        {
            canvas.drawRect(tileRect, squareColor); // canvas requires these two elements here.
            canvas.drawRect(tileRect, squareBorder); // set the boarder of the squares themselves
        }


        Bitmap piece = null; // holds the piece that we want to draw.
        if(!pieceName.equals(" ")) // make sure that the name is not a space, it is a space, then we must not put a piece there, show only the board.
        {
            if(pieceName.equals("wrook")){ // set white rook

                //System.out.println("TEST DRAWING OVER THE NEW ROOK NOW!!");
                piece = decodeSampledBitmapFromResource(c.getResources(), R.drawable.wrook, 50, 50);
                hasRook = true;
                hasKing = false;
                hasQueen = false;
                this.pieceName = pieceName;
            }
            else if(pieceName.equals("wking")){ // set white king
                piece = decodeSampledBitmapFromResource(c.getResources(), R.drawable.wking, 50, 50);
                hasRook = false;
                hasKing = true;
                hasQueen = false;
                this.pieceName = pieceName;
            }
            else if(pieceName.equals("wqueen")){ // set white queen
                piece = decodeSampledBitmapFromResource(c.getResources(), R.drawable.wqueen, 50, 50);
                hasRook = false;
                hasKing = false;
                hasQueen = true;
                this.pieceName = pieceName;
            }
            else if(pieceName.equals("brook")){ // set black rook
                piece = decodeSampledBitmapFromResource(c.getResources(), R.drawable.brook, 50, 50);
                hasRook = true;
                hasKing = false;
                hasQueen = false;
                this.pieceName = pieceName;
            }
            else if(pieceName.equals("bking")){ // set black king
                piece = decodeSampledBitmapFromResource(c.getResources(), R.drawable.bking, 50, 50);
                hasRook = false;
                hasKing = true;
                hasQueen = false;
                this.pieceName = pieceName;
            }
            else if(pieceName.equals("bqueen")){ // set black queen
                piece = decodeSampledBitmapFromResource(c.getResources(), R.drawable.bqueen, 50, 50);
                hasRook = false;
                hasKing = false;
                hasQueen = true;
                this.pieceName = pieceName;
            }

            canvas.drawBitmap(piece, null, tileRect, null); // draw our bitmap image.
            hasRook = false;
            hasKing = false;
            hasQueen = false;
        }
        else // no piece therefore draw an empty piece.
        {
            this.pieceName = " "; // set the piece name to be nothing to stand for an empty tile.
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

    public void setHighlightRect(final Rect highlightRect)
    {
        this.highlightRect = highlightRect;
    }

    public void setKingHighlightRect(final Rect kingHighlightRect)
    {
        this.kingHighlightRect = kingHighlightRect;
    }


}