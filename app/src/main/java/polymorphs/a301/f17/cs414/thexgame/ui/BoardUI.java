package polymorphs.a301.f17.cs414.thexgame.ui;

/**
 * Found on https://gist.github.com/Oshuma/3352280 updated and modified for our implementation. ~Roger
 * This was greatly worked on and modified by Roger and Miles to work with the AppBackend and ensures that as little code goes into the actual UI elements as possible
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.service.quicksettings.Tile;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import polymorphs.a301.f17.cs414.thexgame.AppBackend.Driver;
import polymorphs.a301.f17.cs414.thexgame.AppBackend.GameRecord;
import polymorphs.a301.f17.cs414.thexgame.AppBackend.GameSnapshot;
import polymorphs.a301.f17.cs414.thexgame.R;
import polymorphs.a301.f17.cs414.thexgame.persistence.DBIOCore;
import polymorphs.a301.f17.cs414.thexgame.persistence.GameSnapshotObserver;
import polymorphs.a301.f17.cs414.thexgame.ui.activities.HomescreenActivity;


public final class BoardUI extends View implements GameSnapshotObserver {
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

    private int gameState = 0; // 0 = in progress, 1 = game won, 2 = game tied

    private String gameID = ""; // holds the gameID for this game, needed in order to keep track of the game and what it can do.
    private Driver driver; // the driver object that we are going to be using to communicate with the UI (this)
    private HomescreenActivity activity; // a copy of the homescreen activity so that we can display the proper winning screen for this game if a player sees it in real time.
    private String whitePlayer = ""; // holds the name of the white player
    private String blackPlayer = ""; // holds the name of the black player.

    public BoardUI(final Context context, final AttributeSet attrs ) {
        super(context, attrs);

        this.tileUIs = new TileUI[ROWS][COLS];
        setFocusable(true);
        movePieceActionListener = new MovePieceActionListener(this); // create a new MovePieceActionListener that will be in charge of setting everything else up.
        buildTiles();
        driver = Driver.getInstance();
    }

    public void registerToSnapshot(String snapshotKey) {

        System.out.println("The game is being registered with this snapshotkey ->" + snapshotKey);

        DBIOCore.getInstance().registerToGameSnapshot(this, snapshotKey);
        gameID = snapshotKey;
    }

    public void setWhitePlayer(String name)
    {
        whitePlayer = name;
    }

    public void setBlackPlayer(String name)
    {
        blackPlayer = name;
    }

    public String getWhiteplayer()
    {
        return whitePlayer;
    }

    public String getBlackPlayer()
    {
        return blackPlayer;
    }

    // gets the game ID, mainly used for removing games out of the view pager.
    public String getGameID()
    {
        return gameID;
    }

    public int getGameState() {
        return gameState;
    }

    // important for displaying the winner of the game.
    public void setHomescreenActivity(HomescreenActivity activity)
    {
        this.activity = activity;
    }

    // returns a copy of the homescreen activity.
    public HomescreenActivity getHomescreenActivity()
    {
        return activity;
    }

    // display the winner text if someone wins a game.
    public void displayWinnerCaption()
    {
        String display = driver.getCurrentPlayerNickname() + " Wins!";
        Snackbar.make(this, display, Snackbar.LENGTH_INDEFINITE).show(); // show the snackbar of the player!
    }

    // display the tie text if the game is a tie.
    public void displayTieCaption()
    {
        String p1 = getBlackPlayer();
        String p2 = getWhiteplayer();
        GameRecord record = new GameRecord(p1, p2, driver.getGameState());

        String display = "This Game is a Tie!";
        Snackbar.make(this, display, Snackbar.LENGTH_INDEFINITE).show(); // show the snackbar to alert the user.
    }

    public void displayInProgressCaption()
    {
        String vsTitle = getWhiteplayer() +  " vs " + getBlackPlayer(); // the title of the snackbar itself.
        Snackbar.make(this, vsTitle, Snackbar.LENGTH_LONG).show(); // show the snackbar plus the game for the users to see, this is actually pretty cool!!! You'll see
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
    }

    // Checks if the tile needs to be drawn again to when the board is rewritten.
    public boolean toBeHighlighted(int row, int col)
    {
        for(int i = 0; i < highlightedSquares.size(); i++)
        {
            int rowToCheck = highlightedSquares.get(i)[0]; // get the row.
            int colToCheck = highlightedSquares.get(i)[1]; // get the col.

            if(rowToCheck == row && colToCheck == col)
            {
                return true; // this tile needs to be highlighted.
            }
        }

        return false; // no square was found that was in need to be highlighted.
    }

    // checks to see if the coordinates of the king, null means that the king is not in check.
    public int[] highlightKingInCheck()
    {
        int[] kingCoords = driver.isInCheck(); // returns the set of coords for king and checks if it is in check..
        if(kingCoords != null) // that means there are some coordinates that we need to be working with.
        {
            return kingCoords;
        }
        else
        {
            return null;
        }
    }

    // Makes calls to tileUIs and tells it to draw the pieces and will eventually tell it what to do in terms of what to build and what not.
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
                final int xCoord = getXCoord(cols);
                final int yCoord = getYCoord(rows);

                final Rect tileRect = new Rect(
                        xCoord,               // left
                        yCoord,               // top
                        xCoord + squareSize,  // right
                        yCoord + squareSize   // bottom
                );

                final Rect highlightRect = new Rect(
                        xCoord,               // left
                        yCoord,               // top
                        xCoord + squareSize,  // right
                        yCoord + squareSize   // bottom
                );

                final Rect kingHightlightRect = new Rect(
                        xCoord,               // left
                        yCoord,               // top
                        xCoord + squareSize,  // right
                        yCoord + squareSize   // bottom
                );


                tileUIs[rows][cols].setTileRect(tileRect); // set the tileRect which controls coloring of the tiles.

                if(toBeHighlighted(rows, cols)) // check if the tile needs to be highlighted when the board is drawn again.
                {
                    System.out.println("tile needs to be highlighted right now ");
                    tileUIs[rows][cols].setHighlightRect(highlightRect);
                    tileUIs[rows][cols].setKingHighlightRect(null); // we are not using the king highlight rect here.
                }
                else if(highlightKingInCheck() != null)
                {
                    int row = highlightKingInCheck()[0];
                    int col = highlightKingInCheck()[1];

                    tileUIs[row][col].setKingHighlightRect(kingHightlightRect); // set the king highlight rect.
                    tileUIs[rows][cols].setHighlightRect(null); // set null so that the original board colors are written.
                }
                else
                {
                    tileUIs[rows][cols].setHighlightRect(null); // set null so that the original board colors are written.
                    tileUIs[rows][cols].setKingHighlightRect(null); // we are not using the king highlight rect here.
                }


                if(newlyStarted == true) // generate the castle walls.
                {
                    // look for all of the pieces and ensure that the pieces have a place to end up on the castle wall.
                    if(tileUIs[rows][cols].isOpponentKingLoc()) {
                        tileUIs[rows][cols].draw(canvas, "bking", getContext()); // set black king inside opponent's castle.
                    }
                    else if(tileUIs[rows][cols].isOpponentCastle()) {
                        tileUIs[rows][cols].draw(canvas, "brook", getContext()); // set black rooks on opponent's castle.
                    }
                    else if(tileUIs[rows][cols].isPlayerKingLoc()) {
                        tileUIs[rows][cols].draw(canvas, "wking", getContext()); // set white king inside opponent's castle.
                    }
                    else if(tileUIs[rows][cols].isPlayerCastle()){
                        tileUIs[rows][cols].draw(canvas, "wrook", getContext()); // set white rook inside opponent's castle.
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
                  //  getHomescreenActivity().switchToGameAt(0);
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
        return x0 + squareSize * x;
    }

    private int getYCoord(final int y) {
        return y0 + squareSize * y;
    }

    private void computeOrigins(final int width, final int height) {
        this.x0 = 0;
        this.y0 = 0;
    }

    // Updates the game based on the snapshot of the game sent in, used when a game is updated.
    @Override
    public void snapshotUpdated(GameSnapshot gs) {

        System.out.println("Is the gamesnapshot null? " + gs);

        newlyStarted = false;
        String tempGame = gs.getGameString();
        String [] playerPieces = tempGame.split("-")[1].split("\\|");
        String [] pieces;
        String [] pieceParts;
        HashMap<String, String[]> piecePartsByLocation = new HashMap<>();
        for (int playerIdx = 0; playerIdx < 2; playerIdx++) {
            pieces = playerPieces[playerIdx].split("\\*");
            for (String piece : pieces) {
                pieceParts = piece.split(",");
                if (!pieceParts[3].equals("true")) continue; // if graveyard needs to be done it should be added here
                piecePartsByLocation.put(pieceParts[1] + "," + pieceParts[2], new String[]{pieceParts[0],pieceParts[4]});
            }
        }
        String pieceName;
        for (int row = 0; row < tileUIs.length; row++) {
            for (int col = 0; col < tileUIs[row].length; col++) {
                if (piecePartsByLocation.get(row + "," + col) != null) {
                    pieceName = piecePartsByLocation.get(row + "," + col)[0].toLowerCase();
                    if (piecePartsByLocation.get(row + "," + col)[1].equalsIgnoreCase("white")) {
                        pieceName = "w" + pieceName;
                    } else {
                        pieceName = "b" + pieceName;
                    }
                    tileUIs[row][col].setPieceName(pieceName);
                } else {
                    tileUIs[row][col].setPieceName(" ");
                }
            }
        }
        gameState = gs.getGameState();
        if (canvas != null) {
            if (gameState == 1) {
                displayWinnerCaption();
            } else if (gameState == 2) {
                displayTieCaption();
            }
        }
        invalidate();
    }
}