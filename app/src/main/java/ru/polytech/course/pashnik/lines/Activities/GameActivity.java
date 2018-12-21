package ru.polytech.course.pashnik.lines.Activities;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import ru.polytech.course.pashnik.lines.Core.Cell;
import ru.polytech.course.pashnik.lines.Core.ColorType;
import ru.polytech.course.pashnik.lines.Graphics.Ball;
import ru.polytech.course.pashnik.lines.Graphics.GameView;
import ru.polytech.course.pashnik.lines.Graphics.ScoreView;
import ru.polytech.course.pashnik.lines.Presentation.MainContract;
import ru.polytech.course.pashnik.lines.Presentation.MainPresenter;
import ru.polytech.course.pashnik.lines.R;

public class GameActivity extends AppCompatActivity implements MainContract.ViewInterface {

    private static Canvas gameViewCanvas;
    private static Canvas scoreViewCanvas;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        LinearLayout linearLayout = findViewById(R.id.main_layout);

        ScoreView scoreView = new ScoreView(this);
        linearLayout.addView(scoreView);

        MainContract.Presenter presenter = new MainPresenter(this);

        GameView gameView = new GameView(this);
        linearLayout.addView(gameView);
        gameView.setPresenter(presenter);
    }

    @Override
    public void drawBallOnBoard(Cell cell, ColorType color) {
        new Ball(cell, color).drawBall(gameViewCanvas);
    }

    @Override
    public void drawBallOnScoreView(Cell cell, ColorType color) {
        new Ball(cell, color).drawBall(scoreViewCanvas);
    }

    @Override
    public void clearBallOnScoreView(Cell cell) {
        new Ball(cell).clearBall(scoreViewCanvas);
    }

    @Override
    public void clearBallOnBoard(Cell cell) {
        new Ball(cell).clearBall(gameViewCanvas);
    }

    public static void setGameViewCanvas(Canvas canvas) {
        gameViewCanvas = canvas;
    }

    public static void setScoreViewCanvas(Canvas canvas) {
        scoreViewCanvas = canvas;
    }
}
