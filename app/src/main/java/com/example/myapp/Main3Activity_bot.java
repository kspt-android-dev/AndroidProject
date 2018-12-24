package com.example.myapp;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;


public class Main3Activity_bot extends AppCompatActivity {
    private PlayField playField;
    private ConstraintLayout layout;
    private FrameLayout frame;

    private Button button;

    private TextView cX;
    private TextView cO;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main3_bot);

        playField = new PlayField(this, 6); // vs bot (5x5)

        layout = findViewById(R.id.q);


        frame = findViewById(R.id.frameLayout);

        frame.setBackgroundResource(R.drawable.grid_fives);

        frame.addView(playField);

        layout.setBackgroundResource(R.drawable.back_notebook2);
        addListenerOnButton();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            playField.exitGame();

        }
        return super.onKeyDown(keyCode, event);
    }


    private void addListenerOnButton() {
        button = findViewById(R.id.button);

        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        playField.setClickable(true);
                        Logic.countStep = 0;
                        playField.restartGame();
                    }
                }
        );
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) { // смена ориентации
        super.onSaveInstanceState(outState);

        if (Logic.flagTeamWin == 1) Logic.winX--;
        if (Logic.flagTeamWin == 2) Logic.winO--;

        Logic.countStep--;
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        cX = findViewById(R.id.countWinX);
        cO = findViewById(R.id.countWinO);

        cX.setText(String.valueOf(Logic.winX));
        cO.setText(String.valueOf(Logic.winO));
    }
}