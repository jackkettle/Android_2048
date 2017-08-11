package com.jollyottsel.android2048;

import android.animation.AnimatorSet;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationSet;
import android.widget.GridView;
import android.widget.TextView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameActivity extends AppCompatActivity {

    GridView gridView;

    TextView scoreView;

    TextView highScoreView;

    GameGridViewAdapter gameGridViewAdapter;

    Game game;

    boolean tileAdded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        this.scoreView = (TextView) findViewById(R.id.scoreText);
        this.scoreView.setText("0");

        this.highScoreView = (TextView) findViewById(R.id.highScoreText);
        this.highScoreView.setText(getHighScore() + "");

        int rows = getIntent().getIntExtra(Constants.INTENT_DATA_KEY_ROWS, 4);
        int cols = getIntent().getIntExtra(Constants.INTENT_DATA_KEY_COLS, 4);
        this.game = new Game(this, rows, cols);

        gridView = (GridView) findViewById(R.id.gridViewId);
        gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        gridView.setNumColumns(cols);

        gridView.setAdapter(new GameGridViewAdapter(this, gridView, this.game));
        gameGridViewAdapter = (GameGridViewAdapter) gridView.getAdapter();


        gridView.setOnTouchListener(new View.OnTouchListener() {

            private float x, y, xStart, yStart, xMove, yMove;

            boolean movedTiles;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                tileAdded = false;

                x = event.getX();
                y = event.getY();

                movedTiles = false;

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        xStart = x;
                        yStart = y;
                        break;

                    case MotionEvent.ACTION_MOVE:
                        break;

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:

                        xMove = x - xStart;
                        yMove = y - yStart;

                        String value = String.valueOf(event.getX()) + "x" + String.valueOf(event.getY());
                        logger.info("value: {}", value);

                        if ((xMove > 0) && (Math.abs(xMove) > Math.abs(yMove)))
                            movedTiles = game.pull(Direction.RIGHT);
                        if ((xMove < 0) && (Math.abs(xMove) > Math.abs(yMove)))
                            movedTiles = game.pull(Direction.LEFT);
                        if ((yMove < 0) && (Math.abs(xMove) < Math.abs(yMove)))
                            movedTiles = game.pull(Direction.UP);
                        if ((yMove > 0) && (Math.abs(xMove) < Math.abs(yMove)))
                            movedTiles = game.pull(Direction.DOWN);

                        break;
                }

                return true;

            }
        });

    }

    public int getHighScore() {
        SharedPreferences prefs = this.getSharedPreferences(Constants.GAME_KEY, Context.MODE_PRIVATE);
        return prefs.getInt(Constants.GAME_KEY_HIGHSCORE, 0);
    }

    public void setHighScore(int score) {
        SharedPreferences prefs = this.getSharedPreferences(Constants.GAME_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(Constants.GAME_KEY_HIGHSCORE, score);
        editor.commit();

    }

    public void update() {
        if (!tileAdded) {
            this.game.getGameGrid().addStartingTile();
            tileAdded = true;
        }
        updateScoreText();
        this.gameGridViewAdapter.notifyDataSetChanged();
        this.gridView.invalidateViews();
    }

    public void undoLast(View view) {

        logger.info("Undo called");
        this.game.undo();
        this.gridView.invalidateViews();

    }

    public void resetGame(View view) {

        logger.info("reset called");
        this.game.reset();
        this.gridView.invalidateViews();
        this.updateScoreText();

    }

    public void updateScoreText() {
        this.scoreView.setText(String.valueOf(this.game.getGameGrid().getScore()));

        if (this.game.getGameGrid().getScore() > getHighScore()) {
            this.setHighScore(this.game.getGameGrid().getScore());
            this.highScoreView.setText(String.valueOf(this.game.getGameGrid().getScore()));
        }
    }


    final static Logger logger = LoggerFactory.getLogger(GameActivity.class);

}
