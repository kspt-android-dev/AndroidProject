package ru.polytech.course.pashnik.lines.Activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import ru.polytech.course.pashnik.lines.Core.Cell;
import ru.polytech.course.pashnik.lines.Core.ColorType;
import ru.polytech.course.pashnik.lines.DataBase.Contact;
import ru.polytech.course.pashnik.lines.DataBase.DataBaseHandler;
import ru.polytech.course.pashnik.lines.Graphics.Ball;
import ru.polytech.course.pashnik.lines.Graphics.GameView;
import ru.polytech.course.pashnik.lines.Graphics.ScoreView;
import ru.polytech.course.pashnik.lines.Presentation.MainContract;
import ru.polytech.course.pashnik.lines.Presentation.MainPresenter;
import ru.polytech.course.pashnik.lines.R;

public class GameActivity extends AppCompatActivity implements MainContract.ViewInterface {

    private static final int TEXT_SIZE = 25;

    private static Canvas gameViewCanvas;
    private static Canvas scoreViewCanvas;
    private MainContract.Presenter presenter;
    private DataBaseHandler dataBaseHandler = new DataBaseHandler(this);
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        LinearLayout linearLayout = findViewById(R.id.main_layout);

        ScoreView scoreView = new ScoreView(this);
        linearLayout.addView(scoreView);

        presenter = new MainPresenter(this);

        GameView gameView = new GameView(this);
        linearLayout.addView(gameView);
        gameView.setPresenter(presenter);

        LinearLayout.LayoutParams lpView =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);

        textView = new TextView(this);
        textView.setTextColor(Color.RED);
        textView.setTextSize(TEXT_SIZE);
        textView.setLayoutParams(lpView);
        linearLayout.addView(textView);
    }

    @SuppressLint("InflateParams")
    @Override
    public void createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog, null);
        final EditText valueKey = view.findViewById(R.id.edit_text);
        builder.setTitle(R.string.enter_name)
                .setView(view)
                .setCancelable(false)
                .setNegativeButton(R.string.save,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                final String name = String.valueOf(valueKey.getText());
                                dataBaseHandler.addContact(new Contact(name, presenter.getScore()));
                                dialog.dismiss();
                            }
                        });
        builder.show();
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
    public void clearBallOnBoard(Cell cell) {
        new Ball(cell).clearBall(gameViewCanvas);
    }

    @Override
    public void setScore(String score) {
        textView.setText(score);
    }


    public static void setGameViewCanvas(Canvas canvas) {
        gameViewCanvas = canvas;
    }

    public static void setScoreViewCanvas(Canvas canvas) {
        scoreViewCanvas = canvas;
    }
}
