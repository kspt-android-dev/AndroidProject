package com.example.danila.minerandroid;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.GridLayout;
import android.widget.TextView;

public class A_GameActivity extends Activity {
    static int minesNumber;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);


        Logic logic = new Logic(6, 8, minesNumber);


        GridLayout gameField = findViewById(R.id.game_field);
        Button settingsButton = findViewById(R.id.settings_button);
        Button reloadButton = findViewById(R.id.reload_button);
        Button reloadLastButton = findViewById(R.id.reloadlast_button);
        TextView minesNumberView = findViewById(R.id.mines_number);
        Chronometer chronometer = findViewById(R.id.timer_view);
        chronometer.start();


        Graphic graphic = new Graphic(this, gameField, minesNumberView, logic);
        minesNumberView.setText("" + (logic.getMinesDigit() - logic.getFindedMinesDigit()));

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSettingsActivity();
            }
        });

        reloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logic.reload();
                graphic.reload();
                minesNumberView.setText("" + (logic.getMinesDigit() - logic.getFindedMinesDigit()));
                chronometer.setBase(SystemClock.elapsedRealtime());

            }
        });


        reloadLastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logic.reloadLast();
                graphic.reload();
                minesNumberView.setText("" + (logic.getMinesDigit() - logic.getFindedMinesDigit()));
                chronometer.setBase(SystemClock.elapsedRealtime());

            }
        });


    }

    private void goToSettingsActivity() {
        startActivity(new Intent(this, A_ChooseLevelActivity.class));
    }

    public static void setMinesNumber(int minesNumber) {
        A_GameActivity.minesNumber = minesNumber;
    }
}
