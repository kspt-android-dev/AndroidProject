package com.shminesweeper.shminesweeper;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import static android.view.MotionEvent.*;

public class PlayingFieldListner implements View.OnTouchListener {

    // начальные координаты нажатия
    float startX = 0;
    float startY = 0;

    private PlayingField playingField;

    PlayingFieldListner(PlayingField playingField){
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
                            playingField.widthInPixel() + 100 - playingField.getWidth() : 0;
                    int maxScrollY = playingField.heightInPixel() >= playingField.getHeight() ?
                            playingField.heightInPixel() + 100 - playingField.getHeight() : 0;

                    if (view.getScrollX() + xShift > -100 &&
                            view.getScrollY() + yShift > -100 &&
                            view.getScrollX() + xShift < maxScrollX &&
                            view.getScrollY() + yShift < maxScrollY) {
                        view.scrollBy(xShift, yShift);
                    }
                }
                //---------------------------------------------------------

                break;
            case ACTION_UP:
            case ACTION_CANCEL:

                // изменение настроек при несовпедении их новых параметров с предыдущими
                if (Math.abs(motionEvent.getX()+view.getScrollX() - (startX+view.getScrollX())) < 50 &&
                        Math.abs(motionEvent.getY()+view.getScrollY() - (startY+view.getScrollY())) < 50) {
                    playingField.checkCell(new Point((int) motionEvent.getX() + view.getScrollX(),
                            (int) motionEvent.getY() + view.getScrollY()));
                }
                break;
        }

        return false;
    }

}
