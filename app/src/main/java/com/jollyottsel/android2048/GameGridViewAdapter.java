package com.jollyottsel.android2048;

import android.content.Context;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jackk on 02/08/2017.
 */

public class GameGridViewAdapter extends BaseAdapter {

    private Context context;
    private GridView gridView;
    private Game game;
    private float x, y, xStart, yStart, xMove, yMove;

    public GameGridViewAdapter(Context context, GridView gridView, Game game) {
        this.context = context;
        this.gridView = gridView;
        this.game = game;
    }

    public void setData(Game game){
        this.game = game;
    }

    public Game getGame(){
        return this.game;
    }

    @Override
    public int getCount() {
        return this.game.getGameGrid().length();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int gridViewHeightDP = 300;
        int spacingDP = 10;

        float gridViewHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, gridViewHeightDP, context.getResources().getDisplayMetrics());
        float spacing = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, spacingDP, context.getResources().getDisplayMetrics());

        int toRemove = ((int) spacing + (13 - game.getGameGrid().getRowLength()));

        int tileHeight = (int) gridViewHeight / gridView.getNumColumns();
        tileHeight = tileHeight - toRemove;

        this.game.getGameGrid().getPosition(position).getTileView().setHeight(tileHeight);
        return this.game.getGameGrid().getPosition(position);

    }

    final static Logger logger = LoggerFactory.getLogger(GameGridViewAdapter.class);

}

