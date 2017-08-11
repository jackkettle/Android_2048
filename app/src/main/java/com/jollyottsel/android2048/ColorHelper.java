package com.jollyottsel.android2048;

import android.graphics.Color;

/**
 * Created by jackk on 03/08/2017.
 */

public class ColorHelper {

    static int lightText = Color.parseColor("#f9f6f2");
    static int darkText = Color.parseColor("#776e65");

    static int V0 = Color.parseColor("#cec3b6");
    static int V2 = Color.parseColor("#eee4da");
    static int V4 = Color.parseColor("#ede0c8");
    static int V8 = Color.parseColor("#f2b179");
    static int V16 = Color.parseColor("#f59563");
    static int V32 = Color.parseColor("#f67c5f");
    static int V64 = Color.parseColor("#f65e3b");
    static int V128 = Color.parseColor("#edcf72");
    static int V256 = Color.parseColor("#edcc61");
    static int V512 = Color.parseColor("#edc850");
    static int V1024 = Color.parseColor("#edc53f");
    static int V2048 = Color.parseColor("#edc22e");
    static int V4096 = Color.parseColor("#3c3a32");

    public static void changeColor(TileView tileView) {

        String value = tileView.getText().toString();

        switch (value) {
            case "":
                tileView.setBackgroundColor(ColorHelper.V0);
                return;
            case "0":
                tileView.setBackgroundColor(ColorHelper.V0);
                return;
            case "2":
                tileView.setBackgroundColor(ColorHelper.V2);
                tileView.setTextColor(ColorHelper.darkText);
                break;
            case "4":
                tileView.setBackgroundColor(ColorHelper.V4);
                tileView.setTextColor(ColorHelper.darkText);
                break;
            case "8":
                tileView.setBackgroundColor(ColorHelper.V8);
                tileView.setTextColor(ColorHelper.lightText);
                break;
            case "16":
                tileView.setBackgroundColor(ColorHelper.V16);
                tileView.setTextColor(ColorHelper.darkText);
                tileView.setTextColor(ColorHelper.lightText);
                break;
            case "32":
                tileView.setBackgroundColor(ColorHelper.V32);
                tileView.setTextColor(ColorHelper.lightText);
                break;
            case "64":
                tileView.setBackgroundColor(ColorHelper.V64);
                tileView.setTextColor(ColorHelper.lightText);
                break;
            case "128":
                tileView.setBackgroundColor(ColorHelper.V128);
                tileView.setTextColor(ColorHelper.lightText);
                break;
            case "256":
                tileView.setBackgroundColor(ColorHelper.V256);
                tileView.setTextColor(ColorHelper.lightText);
                break;
            case "512":
                tileView.setBackgroundColor(ColorHelper.V512);
                tileView.setTextColor(ColorHelper.lightText);
                break;
            case "1024":
                tileView.setBackgroundColor(ColorHelper.V1024);
                tileView.setTextColor(ColorHelper.lightText);
                break;
            case "2048":
                tileView.setBackgroundColor(ColorHelper.V2048);
                tileView.setTextColor(ColorHelper.lightText);
                break;
            case "4096":
                tileView.setBackgroundColor(ColorHelper.V4096);
                tileView.setTextColor(ColorHelper.lightText);
                break;
        }
    }

}
