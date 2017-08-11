package com.jollyottsel.android2048;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by jackk on 07/08/2017.
 */

public class TileViewLayout extends LinearLayout {

    TileView tileView;

    public TileViewLayout(Context context) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        init(inflater);
    }


    public TileViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = LayoutInflater.from(context);
        init(inflater);
    }

    public void init(LayoutInflater inflater){
        View view = inflater.inflate(R.layout.tile, this);
        this.tileView = (TileView) view.findViewById(R.id.textView);
        this.setBackgroundColor(ColorHelper.V0);
    }

    public TileView getTileView() {
        return tileView;
    }

    public void setTileView(TileView tileView) {
        this.tileView = tileView;
    }


}
