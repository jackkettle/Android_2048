package com.jollyottsel.android2048;

/**
 * Created by jackk on 02/08/2017.
 */

import android.support.v7.widget.AppCompatTextView;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Gravity;

public class TileView extends AppCompatTextView {

    boolean changed;

    boolean tempEmpty;

    public TileView(Context context) {
        super(context);
        setGravity(Gravity.CENTER);
        tempEmpty = false;
    }

    public TileView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TileView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void incrementTile() {

        if (isEmpty()) {
            this.setText("2");
        } else {
            int value = Integer.valueOf(this.getText().toString());
            this.setText((value + value) + "");
        }

        ColorHelper.changeColor(this);

    }

    public boolean isTempEmpty() {
        return tempEmpty;
    }

    public void setTempEmpty(boolean tempEmpty) {
        this.tempEmpty = tempEmpty;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public boolean isEmpty() {
        return this.getText().equals("") || this.getText().equals("0");
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

}