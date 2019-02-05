package max.myminesweeper;


import android.annotation.SuppressLint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

import static android.view.MotionEvent.*;

public class PlayingFieldListener implements View.OnTouchListener {

    private final static int MAX_SCROLL = 100;
    private final static int MIN_MOVE = 50;

    float startX = 0;
    float startY = 0;

    private PlayingField playingField;

    public PlayingFieldListener(PlayingField playingField) {
        this.playingField = playingField;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case ACTION_DOWN:
                startX = motionEvent.getX();
                startY = motionEvent.getY();
                break;
            case ACTION_MOVE:
                if (motionEvent.getHistorySize() != 0) {
                    int xShift = (int) (motionEvent.getHistoricalX(0) - motionEvent.getX());
                    int yShift = (int) (motionEvent.getHistoricalY(0) - motionEvent.getY());
                    int maxScrollX = playingField.widthInPixel() >= playingField.getWidth() ?
                            playingField.widthInPixel() + MAX_SCROLL - playingField.getWidth() : 0;
                    int maxScrollY = playingField.heightInPixel() >= playingField.getHeight() ?
                            playingField.heightInPixel() + MAX_SCROLL - playingField.getHeight() : 0;

                    if (view.getScrollX() + xShift > -MAX_SCROLL &&
                            view.getScrollY() + yShift > -MAX_SCROLL &&
                            view.getScrollX() + xShift < maxScrollX &&
                            view.getScrollY() + yShift < maxScrollY) {
                        view.scrollBy(xShift, yShift);
                    }
                }
                break;
            case ACTION_UP:
            case ACTION_CANCEL:
                if (Math.abs(motionEvent.getX() + view.getScrollX() - (startX + view.getScrollX())) < MIN_MOVE &&
                        Math.abs(motionEvent.getY() + view.getScrollY() - (startY + view.getScrollY())) < MIN_MOVE) {
                    playingField.checkCell(new Point((int) motionEvent.getX() + view.getScrollX(),
                            (int) motionEvent.getY() + view.getScrollY()));
                }
                break;

        }
        return false;
    }
}
