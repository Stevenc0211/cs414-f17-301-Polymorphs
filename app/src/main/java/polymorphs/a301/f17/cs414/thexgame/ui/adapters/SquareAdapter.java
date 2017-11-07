package polymorphs.a301.f17.cs414.thexgame.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import polymorphs.a301.f17.cs414.thexgame.R;

/**
 * Created by Roger Hannagan on 9/20/17.
 *
 * This class builds and sets the adapter needed in order to generate and create the main chess board as well as be able to interact with those items on the chess board.
 */

public class SquareAdapter extends BaseAdapter {

    private Context context; // context of the app
    private LayoutInflater layoutInflater; // to inflate our layout

    FrameLayout frameLayout;
    ImageView imageView = null;

    public SquareAdapter(Context c)
    {
        context = c;
        Context sqrContext = c.getApplicationContext();
        layoutInflater = LayoutInflater.from(sqrContext);
    }

    // The chessboard
    private Integer[] chessboardSquares = {
            R.drawable.whitesquare, R.drawable.blacksquare, R.drawable.whitesquare, R.drawable.blacksquare,
            R.drawable.whitesquare, R.drawable.blacksquare, R.drawable.whitesquare, R.drawable.blacksquare,
            R.drawable.blacksquare, R.drawable.whitesquare, R.drawable.blacksquare, R.drawable.whitesquare,
            R.drawable.blacksquare, R.drawable.whitesquare, R.drawable.blacksquare, R.drawable.whitesquare,
            R.drawable.whitesquare, R.drawable.blacksquare, R.drawable.whitesquare, R.drawable.blacksquare,
            R.drawable.whitesquare, R.drawable.blacksquare, R.drawable.whitesquare, R.drawable.blacksquare,
            R.drawable.blacksquare, R.drawable.whitesquare, R.drawable.blacksquare, R.drawable.whitesquare,
            R.drawable.blacksquare, R.drawable.whitesquare, R.drawable.blacksquare, R.drawable.whitesquare,
            R.drawable.whitesquare, R.drawable.blacksquare, R.drawable.whitesquare, R.drawable.blacksquare,
            R.drawable.whitesquare, R.drawable.blacksquare, R.drawable.whitesquare, R.drawable.blacksquare,
            R.drawable.blacksquare, R.drawable.whitesquare, R.drawable.blacksquare, R.drawable.whitesquare,
            R.drawable.blacksquare, R.drawable.whitesquare, R.drawable.blacksquare, R.drawable.whitesquare,
            R.drawable.whitesquare, R.drawable.blacksquare, R.drawable.whitesquare, R.drawable.blacksquare,
            R.drawable.whitesquare, R.drawable.blacksquare, R.drawable.whitesquare, R.drawable.blacksquare,
            R.drawable.blacksquare, R.drawable.whitesquare, R.drawable.blacksquare, R.drawable.whitesquare,
            R.drawable.blacksquare, R.drawable.whitesquare, R.drawable.blacksquare, R.drawable.whitesquare,
    };

    static class ViewHolder
    {
        public ImageView square;
        public ImageView piece;
    }

    // count of the chessboard.
    @Override
    public int getCount()
    {
        return chessboardSquares.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View rowView = convertView;

        if(rowView == null) // if not recycled, initialize attributes.
        {
            rowView = layoutInflater.inflate(R.layout.tile, null); // call the tile and initialize the design on it

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.square = (ImageView) rowView.findViewById(R.id.squareBackground);
            viewHolder.square.setImageResource(R.drawable.goldsquare); // set the image to be whatever position is called on getView.
            viewHolder.piece = (ImageView) rowView.findViewById(R.id.chessPiece); // set the ImageView for the chess piece, however we do not have a chess piece yet.

            /*
            if(position == 8 || position == 9 || position == 10
                    || position == 19 || position == 23 || position == 31 || position == 35 || position == 43 || position == 47 ||
                    position == 56 || position == 57 || position == 58)
            {
                viewHolder.square.setImageResource(R.drawable.greensquare);
            }
            */

            final int pos = position;
            viewHolder.square.setOnClickListener(new View.OnClickListener() {

                // behavior for when a particular square is clicked.
                @Override
                public void onClick(View view)
                {
                    // TODO: We will likely want to add a custom listener that will let us see what we would like to do for this particular square so that users can select moves.
                    System.out.println("The item at position " + pos + " was clicked!");
                    Toast.makeText(context, "TileUI at position " + pos + " was clicked!", Toast.LENGTH_SHORT).show();
                }
            });
            rowView.setTag(viewHolder);
        }

        return rowView; // return the view that we have so far. Pretty important
    }
}
