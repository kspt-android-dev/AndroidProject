package com.example.checkers;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.util.Log;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import static android.view.DragEvent.ACTION_DRAG_ENDED;
import static android.view.DragEvent.ACTION_DRAG_ENTERED;
import static android.view.MotionEvent.*;

public class FieldListener implements View.OnTouchListener {

    private float touchX = 0;
    private float touchY = 0;

    private PlayingField field;

    FieldListener(PlayingField field){
        this.field = field;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case ACTION_MOVE:

                //field.setGrabbedChecker(new Point( (int)event.getX(), (int)event.getY()) );
                Log.i("X", "1");
                //field.invalidate();

                break;
            case ACTION_DOWN:
                touchX = event.getX();
                touchY = event.getY();

                //field.setGrabbedChecker(new Point((int)touchX,(int)touchY));
                //field.invalidate();
                Log.i("Touch", "down");

                break;
            case ACTION_UP:
            case ACTION_CANCEL:
                Log.i("Touch", "up");
                //field.setGrabbedChecker(new Point(0,0));

                break;
        }
        return false;
    }
}
