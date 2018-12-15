package ru.polytech.course.pashnik.lines;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import ru.polytech.course.pashnik.lines.Core.Cell;
import ru.polytech.course.pashnik.lines.Core.ColorType;
import ru.polytech.course.pashnik.lines.Graphics.Ball;
import ru.polytech.course.pashnik.lines.Graphics.GameView;
import ru.polytech.course.pashnik.lines.Graphics.ScoreView;

public class MainActivity extends AppCompatActivity implements MainContract.ViewInterface {

    private MainContract.Presenter presenter;
    private static Canvas gameViewCanvas;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this);

        GameView gameView = new GameView(this, presenter);
        // ScoreView scoreView = new ScoreView(this);

        /*Если я определяю обработчик здесь, то он вообще не работает.
        Хотелось бы конечно его определять здесь, а не в GameView как сейчас*/

//        gameView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        presenter.onCellWasClicked(v.getX(), v.getY());
//                        break;
//                }
//                return false;
//            }
//        });
//
    }

    @Override
    public void drawBallOnBoard(Cell cell, ColorType color) {
        new Ball(cell, color).drawBall(gameViewCanvas);
    }

    @Override
    public void clearBallOnBoard(Cell cell) {
        new Ball(cell).clearBall(gameViewCanvas);
    }

    public static void setCanvas(Canvas canvas) {
        gameViewCanvas = canvas;
    }
}
