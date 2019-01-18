package com.shminesweeper.shminesweeper;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

import static android.view.MotionEvent.*;

public class PlayingFieldListener implements View.OnTouchListener {

    private static final int MAX_SCROLL_BY_X = 100;
    private static final int MAX_SCROLL_BY_Y = 100;
    private static final int MIN_RANGE_FOR_MOVEMENT = 50;
    // начальные координаты нажатия
    private float startX = 0;
    private float startY = 0;

    private PlayingField playingField;

    PlayingFieldListener(PlayingField playingField){
        this.playingField = playingField;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {


        switch (motionEvent.getAction()){
            case ACTION_DOWN:
                startX = motionEvent.getX();
                startY = motionEvent.getY();
                break;
            case ACTION_MOVE:


                // ------------------- moving -------------------

                if (motionEvent.getHistorySize() != 0) {
                    int xShift = (int) (motionEvent.getHistoricalX(0) - motionEvent.getX());
                    int yShift = (int) (motionEvent.getHistoricalY(0) - motionEvent.getY());
                    int maxScrollX = playingField.widthInPixel() >= playingField.getWidth() ?
                            playingField.widthInPixel() + MAX_SCROLL_BY_X - playingField.getWidth() : 0;
                    int maxScrollY = playingField.heightInPixel() >= playingField.getHeight() ?
                            playingField.heightInPixel() + MAX_SCROLL_BY_Y - playingField.getHeight() : 0;

                    if (view.getScrollX() + xShift > -MAX_SCROLL_BY_X &&
                            view.getScrollY() + yShift > -MAX_SCROLL_BY_Y &&
                            view.getScrollX() + xShift < maxScrollX &&
                            view.getScrollY() + yShift < maxScrollY) {
                        view.scrollBy(xShift, yShift);
                    }
                }
                //---------------------------------------------------------

                break;
            case ACTION_UP:
            case ACTION_CANCEL:

                if (Math.abs(motionEvent.getX()+view.getScrollX() - (startX+view.getScrollX())) < MIN_RANGE_FOR_MOVEMENT &&
                        Math.abs(motionEvent.getY()+view.getScrollY() - (startY+view.getScrollY())) < MIN_RANGE_FOR_MOVEMENT) {
                    playingField.checkCell(new Point((int) motionEvent.getX() + view.getScrollX(),
                            (int) motionEvent.getY() + view.getScrollY()));
                }
                break;
        }

        return false;
    }

}
