package com.jollyottsel.android2048;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GameRecyclerViewAdapter.ItemClickListener {

    GameRecyclerViewAdapter adapter;

    RecyclerView recyclerView;

    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<GameType> gameTypes = new ArrayList<>();
        gameTypes.add(new GameType(GameTypeID.SMALL_3X3, "Small 3x3", 1, 3, 3));
        gameTypes.add(new GameType(GameTypeID.CLASSIC_4X4, "Classic 4x4", 2, 4, 4));
        gameTypes.add(new GameType(GameTypeID.LARGE_5X5, "Large 5x5", 3, 5, 5));
        gameTypes.add(new GameType(GameTypeID.LARGER_6X6, "Larger 6x6", 4, 6, 6));
        gameTypes.add(new GameType(GameTypeID.BIG_7X7, "Big 7x7", 5, 7, 7));
        gameTypes.add(new GameType(GameTypeID.MASSIVE_8X8, "Massive 8x8", 6, 8, 8));

        // set up the RecyclerView
        this.recyclerView = (RecyclerView) findViewById(R.id.rvGameTypes);
        this.linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        this.linearLayoutManager.setReverseLayout(false);
        this.recyclerView.setLayoutManager(this.linearLayoutManager);
        adapter = new GameRecyclerViewAdapter(this, gameTypes);
        adapter.setClickListener(this);
        this.recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

    public void scrollLeft(View view) {


        if (linearLayoutManager.findFirstCompletelyVisibleItemPosition() > 0)
            recyclerView.smoothScrollToPosition(linearLayoutManager.findLastVisibleItemPosition() - 1);

    }

    public void scrollRight(View view) {

        if (linearLayoutManager.findFirstCompletelyVisibleItemPosition() < adapter.getItemCount())
            recyclerView.smoothScrollToPosition(linearLayoutManager.findLastVisibleItemPosition() + 1);

    }

    public void startGame(View view) {

        int position = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
        GameType gameType = adapter.getItem(position);
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        intent.putExtra(Constants.INTENT_DATA_KEY_GAME_ID, gameType.getGameTypeID().toString());
        intent.putExtra(Constants.INTENT_DATA_KEY_ROWS, gameType.getRows());
        intent.putExtra(Constants.INTENT_DATA_KEY_COLS, gameType.getColumns());

        logger.info("Starting game activity. GameTypeID: {}", gameType.getGameTypeID().toString());
        startActivity(intent);
    }

    final static Logger logger = LoggerFactory.getLogger(MainActivity.class);

}