package com.jollyottsel.android2048;

/**
 * Created by jackk on 01/08/2017.
 */

public class GameType {

    GameTypeID gameTypeID;

    String name;

    int thumbnail;

    int columns;

    int rows;

    public GameType(){}

    public GameType(GameTypeID gameTypeID, String name, int thumbnail, int columns, int rows) {
        this.gameTypeID = gameTypeID;
        this.name = name;
        this.thumbnail = thumbnail;
        this.columns = columns;
        this.rows = rows;
    }


    public GameTypeID getGameTypeID() {
        return gameTypeID;
    }

    public void setGameTypeID(GameTypeID gameTypeID) {
        this.gameTypeID = gameTypeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
}
