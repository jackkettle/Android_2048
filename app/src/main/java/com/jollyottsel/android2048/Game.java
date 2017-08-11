package com.jollyottsel.android2048;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Build;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by jackk on 03/08/2017.
 */

public class Game {

    private GameGrid previousGameGrid;

    private GameGrid gameGrid;

    List<Animator> animatorList;

    Context context;

    int rows;

    int cols;

    public Game(Context context, int rows, int cols) {
        this.cols = cols;
        this.rows = rows;
        this.context = context;
        this.gameGrid = new GameGrid(this, context, rows, cols);
        this.previousGameGrid = new GameGrid(this, context, rows, cols);
        this.animatorList = new ArrayList<>();
    }

    public boolean pull(Direction direction) {

        boolean tilesMoved = false;

        for (int i = 0; i < this.getGameGrid().length(); i++) {
            String value = this.getGameGrid().getPosition(i).getTileView().getText().toString();
            previousGameGrid.getPosition(i).getTileView().setText(value);
        }

        animatorList = new ArrayList<>();

        switch (direction) {
            case UP:
                tilesMoved = pullUp();
                break;
            case DOWN:
                tilesMoved = pullDown();
                break;
            case LEFT:
                tilesMoved = pullLeft();
                break;
            case RIGHT:
                tilesMoved = pullRight();
                break;
        }

        if (tilesMoved) {
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(this.animatorList);
            animatorSet.setDuration(Constants.ANIMATION_TIME);
            animatorSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    logger.info("Update");
                    ((GameActivity) context).update();
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            animatorSet.start();
            return true;
        } else {
            logger.info("No tiles moved in pull action.");
            return false;
        }
    }

    public void addAnimator(Animator animator) {
        this.animatorList.add(animator);
    }


    public void reset() {
        this.gameGrid = new GameGrid(this, context, rows, cols);
        this.previousGameGrid = new GameGrid(this, context, rows, cols);
    }

    public void undo() {
        for (int i = 0; i < this.getGameGrid().length(); i++) {
            String value = this.getPreviousGameGrid().getPosition(i).getTileView().getText().toString();
            gameGrid.getPosition(i).getTileView().setText(value);
            ColorHelper.changeColor(gameGrid.getPosition(i).getTileView());
        }
    }

    public GameGrid getGameGrid() {
        return gameGrid;
    }


    public GameGrid getPreviousGameGrid() {
        return previousGameGrid;
    }

    public boolean pullUp() {
        logger.info("Pulling up");
        return this.getGameGrid().moveAllTilesUp();
    }

    public boolean pullDown() {
        logger.info("Pulling down");
        return this.getGameGrid().moveAllTilesDown();
    }

    public boolean pullLeft() {
        logger.info("Pulling left");
        return this.getGameGrid().moveAllTilesLeft();
    }

    public boolean pullRight() {
        logger.info("Pulling right");
        return this.getGameGrid().moveAllTilesRight();

    }

    public static AnimatorSet reverseSequentialAnimatorSet(AnimatorSet animatorSet) {
        ArrayList<Animator> animators = animatorSet.getChildAnimations();
        Collections.reverse(animators);

        AnimatorSet reversedAnimatorSet = new AnimatorSet();
        reversedAnimatorSet.playSequentially(animators);
        reversedAnimatorSet.setDuration(animatorSet.getDuration());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            reversedAnimatorSet.setInterpolator(animatorSet.getInterpolator());
        }
        return reversedAnimatorSet;
    }

    final static Logger logger = LoggerFactory.getLogger(Game.class);

}
