package com.example.checkers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;


public class MainActivity extends Activity {

    public static final int SETTINGS_ACTIVITY_RESULT = 1;
    private PlayingField field;
    private FrameLayout frameLayout;
    //private Context context;

    private boolean checkerGrabbed = false;
    Point downFingerPoint = new Point(0,0);

    GestureDetector gd;

    GameLogic gameLogic;

    Intent startServiceIntent ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  context = this;

        gameLogic = new GameLogic();

        // останавливаем сервис уведомлений
        startServiceIntent = new Intent(this,NotificationService.class);
        stopService(startServiceIntent);

        frameLayout = findViewById(R.id.frameLayout);
        field = new PlayingField(this);
        frameLayout.addView(field);
        field.setLogic(gameLogic);


        findViewById(R.id.menu_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openSettings = new Intent(MainActivity.this, SettingsActivity.class );
                startActivityForResult(openSettings, SETTINGS_ACTIVITY_RESULT);
            }
        });

        // восстанавливаем поле после поворота
        if (savedInstanceState != null){
            gameLogic.recoverParams(savedInstanceState);
        } else {
            gameLogic.startNewGame();
        }

        gd = new GestureDetector(this, new MyGestureListener());
        field.setOnTouchListener(touchListener);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // запускаем сервис уведомлений
        startService(startServiceIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SETTINGS_ACTIVITY_RESULT:
                if (resultCode == RESULT_OK) {
                    Log.i("Setting Activity", "result ok");
                    gameLogic.startNewGame();
                    field.invalidate();
                }
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // клетки с шашками
        outState.putIntegerArrayList("whiteCheckers", gameLogic.getWiteCheckersPlaces());
        outState.putIntegerArrayList("blackCheckers", gameLogic.getBlackCheckersPlecas());
        //клетки с дамками
        outState.putIntegerArrayList("whiteCrowns", gameLogic.getWhiteCrownPlaces());
        outState.putIntegerArrayList("blackCrowns", gameLogic.getBlackCrownsPlaces());
        // количество битых
        outState.putInt("numbOfBlackDead", gameLogic.getNumbOfBlackDead());
        outState.putInt("numbOfWiteDead", gameLogic.getNumbOfWiteDead());
        // цвета игроков
        outState.putInt("upperPlayerColor", gameLogic.getUpperPlayerColor());
        outState.putInt("bottomPlayerColor", gameLogic.getBottomPlayerColor());

        // чей ход
        outState.putString("turnColor", gameLogic.getTurnColor().equals(GameLogic.TurnColor.WHITE) ? "WHITE" : "BLACK");
        outState.putString("turnSide", gameLogic.getTurnSide().equals(GameLogic.TurnSide.TOP) ? "TOP" : "BOTTOM");

        outState.putBoolean("someOneKilled", gameLogic.isSomeoneKilledJustNow() );
    }


    View.OnTouchListener touchListener = new View.OnTouchListener() {
        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:

                    downFingerPoint = new Point( (int) event.getX(), (int) event.getY());

                    if (gameLogic.cellSuitableForGrab( downFingerPoint)) {
                        checkerGrabbed = true;
                        gameLogic.hideChecker( downFingerPoint);
                        field.setGrabbedChecker(downFingerPoint);
                        field.invalidate();
                    }
                    break;

                case MotionEvent.ACTION_MOVE :
                    if (checkerGrabbed) field.setGrabbedChecker(new Point( (int) event.getX(), (int) event.getY()));
                    break;

                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:

                    Point upFingerPoint = new Point( (int) event.getX(), (int) event.getY());

                    if (checkerGrabbed) {
                        gameLogic.placeChecker(downFingerPoint, upFingerPoint);

                        field.setGrabbedChecker(new Point(0,0));
                        checkerGrabbed = false;

                    }
                    field.invalidate();
                    break;
            }


            return gd.onTouchEvent(event);
        }
    };

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent event) {
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            return true;
        }
    }

}
