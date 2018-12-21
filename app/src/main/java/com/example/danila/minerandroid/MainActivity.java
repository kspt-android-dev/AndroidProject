package com.example.danila.minerandroid;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.GridLayout;
import android.widget.TextView;

public class MainActivity extends Activity {


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settings();


    }


    void settings() {
        setContentView(R.layout.choose_level_activity);


        Button easy_button = findViewById(R.id.easy);
        Button medium_button = findViewById(R.id.medium);
        Button hard_button = findViewById(R.id.hard);

        easy_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game(6);
            }
        });

        medium_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game(9);
            }
        });
        hard_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game(12);
            }
        });

    }

    @SuppressLint("SetTextI18n")
    void game(int minesNumber) {
        setContentView(R.layout.game_activity);


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
                settings();
            }
        });

        reloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logic.reload();
                graphic.reload();
            }
        });


        reloadLastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logic.reloadLast();
                graphic.reload();
            }
        });

    }


}
