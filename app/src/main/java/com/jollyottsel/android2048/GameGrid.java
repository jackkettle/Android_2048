package com.jollyottsel.android2048;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import android.widget.GridView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by jackk on 03/08/2017.
 */

public class GameGrid {

    private Game game;
    private Context context;
    private List<TileViewLayout> grid;
    private Set<Integer> availablePositions;
    private Set<Integer> takenPositions;
    private List<Animator> animatorList;
    private int rowLength;
    private int score;

    private final static Integer STARTING_TILES = 2;

    public GameGrid(Game game, Context context, int rows, int cols) {

        this.game = game;
        this.context = context;
        this.rowLength = rows;
        this.score = 0;
        this.grid = new ArrayList<>();
        this.availablePositions = new HashSet<>();
        this.takenPositions = new HashSet<>();

        for (int i = 0; i < rows * cols; i++) {
            TileViewLayout tileViewLayout = createEmptyTileLayout(context, i, rows);
            availablePositions.add(i);
            grid.add(tileViewLayout);
        }

        for (int i = 0; i < STARTING_TILES; i++) {
            addStartingTile();
        }

    }


    public TileViewLayout createEmptyTileLayout(Context context, int position, int rows) {
        TileViewLayout tileViewLayout = new TileViewLayout(context);
        tileViewLayout.setClipChildren(false);
        tileViewLayout.setClipToPadding(false);
        initEmptyTile(tileViewLayout.getTileView(), position, rows);
        return tileViewLayout;
    }


    public void initEmptyTile(TileView tileView, int position, int rows) {
        tileView.setTag(position);
        tileView.setText("");
        tileView.setTag("");
        setTextSizeBasedOnGrid(tileView, rows);
        ColorHelper.changeColor(tileView);
    }

    public void unsetChangedTiles() {

        for (TileViewLayout view : grid) {
            view.getTileView().setTempEmpty(false);
            view.getTileView().setChanged(false);
            view.getTileView().setTag("");

        }

    }

    public void setTiles() {

        for (TileViewLayout view : grid) {
            view.getTileView().setTempEmpty(false);
            view.getTileView().setChanged(false);
            view.getTileView().setTag(view.getTileView().getText());

        }

    }

    public boolean moveAllTilesUp() {
        setTiles();

        int N = length();
        boolean tilesMoved = false;

        for (int j = 0; j < (N + 1) - rowLength; j += rowLength) {
            for (int i = 0; i < rowLength; i++) {
                int index = i + j;
                TileViewLayout tileView = this.grid.get(index);
                boolean hasMoved = processTile(tileView, index, rowLength, Direction.UP);
                if (hasMoved) {
                    tilesMoved = true;
                }
            }
        }

        unsetChangedTiles();
        return tilesMoved;

    }

    public boolean moveAllTilesDown() {
        setTiles();

        int N = length();
        boolean tilesMoved = false;

        for (int j = N - rowLength; j >= 0; j -= rowLength) {
            for (int i = 0; i < rowLength; i++) {
                int index = i + j;
                TileViewLayout tileView = this.grid.get(index);
                boolean hasMoved = processTile(tileView, index, rowLength, Direction.DOWN);
                if (hasMoved) {
                    tilesMoved = true;
                }
            }
        }

        unsetChangedTiles();
        return tilesMoved;


    }

    public boolean moveAllTilesLeft() {
        setTiles();

        int N = length();
        boolean tilesMoved = false;

        for (int i = 0; i < rowLength; i++) {
            for (int j = 0; j < (N + 1) - rowLength; j += rowLength) {
                int index = i + j;
                TileViewLayout tileView = this.grid.get(index);
                boolean hasMoved = processTile(tileView, index, rowLength, Direction.LEFT);
                if (hasMoved) {
                    tilesMoved = true;
                }
            }
        }

        unsetChangedTiles();
        return tilesMoved;


    }

    public boolean moveAllTilesRight() {
        setTiles();
        int N = length();
        boolean tilesMoved = false;

        for (int i = rowLength - 1; i >= 0; i--) {
            for (int j = 0; j < (N + 1) - rowLength; j += rowLength) {
                int index = i + j;
                TileViewLayout tileView = this.grid.get(index);
                boolean hasMoved = processTile(tileView, index, rowLength, Direction.RIGHT);
                if (hasMoved) {
                    tilesMoved = true;
                }
            }
        }

        unsetChangedTiles();
        return tilesMoved;

    }

    public boolean processTile(TileViewLayout tileView, int index, int row, Direction direction) {

        int positionToMoveTo = -1;
        boolean increment = false;

        if (tileView.getTileView().isEmpty())
            return false;

        switch (direction) {
            case UP:
                logger.info("Up index: {}", index);
                for (int i = index - row; i >= 0; i = i - row) {

                    logger.info("Up i: {}", i);


                    TileView lookAhead = grid.get(i).getTileView();

                    logger.info("Lookahead.getTag: {}, i: {}", lookAhead.getTag(), i);

                    if (lookAhead.isChanged()) {
                        logger.info("1");
                        continue;
                    }

                    if (lookAhead.getText().toString().equals("")) {
                        logger.info("2");
                        positionToMoveTo = i;
                        continue;
                    }

                    if (lookAhead.getText().toString().equals(tileView.getTileView().getText())) {
                        logger.info("3");
                        positionToMoveTo = i;
                        increment = true;
                        break;
                    } else {
                        break;
                    }

                }
                break;
            case DOWN:

                for (int i = index + row; i <= length() - 1; i = i + row) {

                    TileView lookAhead = grid.get(i).getTileView();

                    logger.info("Lookahead.getTag: {}, i: {}", lookAhead.getTag(), i);

                    if (lookAhead.isChanged()) {
                        logger.info("1");
                        continue;
                    }

                    if (lookAhead.getText().toString().equals("")) {
                        logger.info("2");
                        positionToMoveTo = i;
                        continue;
                    }

                    if (lookAhead.getText().toString().equals(tileView.getTileView().getText())) {
                        logger.info("3");
                        positionToMoveTo = i;
                        increment = true;
                        break;
                    } else {
                        break;
                    }

                }
                break;

            case LEFT:
                for (int i = index - 1; ((i + 1) % rowLength != 0) && i >= 0; i--) {

                    TileView lookAhead = grid.get(i).getTileView();

                    logger.info("Lookahead.getTag: {}, i: {}", lookAhead.getTag(), i);

                    if (lookAhead.isChanged()) {
                        logger.info("1");
                        continue;
                    }

                    if (lookAhead.getText().toString().equals("")) {
                        logger.info("2");
                        positionToMoveTo = i;
                        continue;
                    }

                    if (lookAhead.getText().toString().equals(tileView.getTileView().getText())) {
                        logger.info("3");
                        positionToMoveTo = i;
                        increment = true;
                        break;
                    } else {
                        break;
                    }

                }

                break;
            case RIGHT:

                for (int i = index + 1; (i % rowLength != 0); i++) {

                    TileView lookAhead = grid.get(i).getTileView();

                    logger.info("Lookahead.getTag: {}, i: {}", lookAhead.getTag(), i);


                    if (lookAhead.isChanged()) {
                        logger.info("1");
                        continue;
                    }

                    if (lookAhead.getText().toString().equals("")) {
                        logger.info("2");
                        positionToMoveTo = i;
                        continue;
                    }

                    if (lookAhead.getText().toString().equals(tileView.getTileView().getText())) {
                        logger.info("3");
                        positionToMoveTo = i;
                        increment = true;
                        break;
                    } else {
                        break;
                    }

                }
                break;
        }


        if (positionToMoveTo >= 0 && (index != positionToMoveTo)) {

            if (increment)
                grid.get(positionToMoveTo).getTileView().setChanged(true);

            logger.info("Moving tile to position {} from position {}", positionToMoveTo, index);
            Animator animator = animateMove(grid.get(index), grid.get(positionToMoveTo), increment, index, positionToMoveTo);
            game.addAnimator(animator);

//            grid.get(index).getTileView().setTag("");
//            grid.get(positionToMoveTo).getTileView().setTag(grid.get(index).getTileView().getText());

            handleMove(grid.get(index), increment, index, positionToMoveTo);


            return true;
        }

        return false;
    }

//    private void animateMove(final TileViewLayout fromView, TileViewLayout toView, final boolean increment,  final int currentPosition, final int positionToMoveTo, final int offset) {
//
//        GridView parent = (GridView) fromView.getParent();
//
//        int x1 = fromView.getLeft();
//        int y1 = fromView.getTop();
//
//        int x2 = toView.getLeft();
//        int y2 = toView.getTop();
//
//        int spacing = parent.getHorizontalSpacing();
//
//        logger.debug("x1: {}, y1: {}", x1, y1);
//        logger.debug("x2: {}, y2: {}", x2, y2);
//        logger.debug("x2 - x1: {}, y2 - y1: {}", x2 - x1, y2 - y1);
//
//        TranslateAnimation translateAnimation = null;
//        if (x1 == x2) {
//            y2 = y2 - (y1);
//            logger.debug("Change to y2: {}", y2);
//            translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.ABSOLUTE, y2);
//        } else {
//            x2 = x2 - (x1);
//            logger.debug("Change to x2: {}", x2);
//            translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.ABSOLUTE, x2, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
//        }
//
//        translateAnimation.initialize(fromView.getWidth(), fromView.getHeight(), parent.getWidth(), parent.getHeight());
//        translateAnimation.setDuration(Constants.ANIMATION_TIME);
//        translateAnimation.setFillAfter(false);
//        translateAnimation.setZAdjustment(1);
//        translateAnimation.setStartOffset(offset);
//        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
//
//            @Override
//            public void onAnimationStart(Animation animation) {
//                fromView.setZ(2);
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                fromView.setZ(1);
//                handleMove(grid.get(currentPosition), increment, currentPosition, positionToMoveTo);
//                ((GameActivity) context).update();
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//            }
//
//        });
//        fromView.getTileView().startAnimation(translateAnimation);
//
//    }

    private ObjectAnimator animateMove(final TileViewLayout fromView, TileViewLayout toView, final boolean increment, final int currentPosition, final int positionToMoveTo) {

        GridView parent = (GridView) fromView.getParent();

        int x1 = fromView.getLeft();
        int y1 = fromView.getTop();

        int x2 = toView.getLeft();
        int y2 = toView.getTop();

        int spacing = parent.getHorizontalSpacing();

        logger.debug("x1: {}, y1: {}", x1, y1);
        logger.debug("x2: {}, y2: {}", x2, y2);
        logger.debug("x2 - x1: {}, y2 - y1: {}", x2 - x1, y2 - y1);


        ObjectAnimator objectAnimator = null;
        if (x1 == x2) {
            y2 = y2 - (y1);
            logger.debug("Change to y2: {}", y2);

            objectAnimator = ObjectAnimator.ofFloat(fromView.getTileView(), "translationY", y2);

            //translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.ABSOLUTE, y2);
        } else {
            x2 = x2 - (x1);
            logger.debug("Change to x2: {}", x2);

            objectAnimator = ObjectAnimator.ofFloat(fromView.getTileView(), "translationX", x2);

            //translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.ABSOLUTE, x2, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        }

        objectAnimator.addListener(new AnimatorListenerAdapter() {

            int x1, y1;

            @Override
            public void onAnimationStart(Animator animation) {
                ObjectAnimator animator = (ObjectAnimator) animation;
                TileView tileView = ((TileView) animator.getTarget());
                tileView.getParent().bringChildToFront(tileView);
                tileView.setZ(1);


                x1 = tileView.getLeft();
                y1 = tileView.getTop();

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animation.removeListener(this);
                ObjectAnimator animator = (ObjectAnimator) animation;
                TileView tileView = ((TileView) animator.getTarget());
                tileView.setZ(0);

                tileView.setX(x1);
                tileView.setY(y1);
            }

        });
        return objectAnimator;
    }


    public void handleMove(TileViewLayout fromView, boolean increment, int currentPosition, int positionToMoveTo) {

        if (increment) {
            fromView.getTileView().incrementTile();
            fromView.getTileView().setChanged(true);
            this.score += Integer.valueOf(fromView.getTileView().getText().toString());
        }

        logger.info("Setting positionToMoveTo: {}, from currentPosition: {}", positionToMoveTo, currentPosition);
        grid.set(positionToMoveTo, fromView);
        grid.set(currentPosition, createEmptyTileLayout(this.context, currentPosition, this.rowLength));

        availablePositions.add(currentPosition);
        availablePositions.remove(positionToMoveTo);
        takenPositions.add(positionToMoveTo);
        takenPositions.remove(currentPosition);

    }

    public void setTextSizeBasedOnGrid(TileView tileView, int rows) {

        switch (rows) {

            case 3:
                tileView.setTextSize(context.getResources().getDimension(R.dimen.textsize_3x3));
                break;

            case 4:
                tileView.setTextSize(context.getResources().getDimension(R.dimen.textsize_4x4));
                break;

            case 5:
                tileView.setTextSize(context.getResources().getDimension(R.dimen.textsize_5x5));
                break;

            case 6:
                tileView.setTextSize(context.getResources().getDimension(R.dimen.textsize_6x6));
                break;

            case 7:
                tileView.setTextSize(context.getResources().getDimension(R.dimen.textsize_7x7));
                break;

            case 8:
                tileView.setTextSize(context.getResources().getDimension(R.dimen.textsize_8x8));
                break;

        }
    }

    public boolean addStartingTile() {

        if (availablePositions.size() == 0) {
            return false;
        }

        int randomPositionIndex = new Random().nextInt(availablePositions.size());
        Integer randomPosition = (Integer) availablePositions.toArray()[randomPositionIndex];

        grid.get(randomPosition).getTileView().incrementTile();
        takenPositions.add(randomPosition);
        availablePositions.remove(randomPosition);

        ScaleAnimation fade_in = new ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        fade_in.setDuration(200);
        fade_in.setFillAfter(true);

        logger.info("Adding new tile to position: {}", randomPosition);
        grid.get(randomPosition).getTileView().startAnimation(fade_in);

        return true;
    }

    public int getRowLength() {
        return this.rowLength;
    }

    public int getScore() {
        return this.score;
    }

    public int length() {
        return grid.size();
    }

    public TileViewLayout getPosition(int position) {
        return this.grid.get(position);
    }

    final static Logger logger = LoggerFactory.getLogger(GameGrid.class);

}
