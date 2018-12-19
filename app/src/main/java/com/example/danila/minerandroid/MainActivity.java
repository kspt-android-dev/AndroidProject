package com.example.danila.minerandroid;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int fieldWidth = 9;
        int fieldHeight = 12;
        int minesDigit = 9;//TODO take from menu

        Logic logic = new Logic(fieldWidth, fieldHeight, minesDigit);

        Bot bot = new Bot(logic);


        //For graphic
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        final int SCREEN_WIDTH = dm.widthPixels;
        final int SCREEN_HEIGHT = dm.heightPixels;

        FrameLayout gameField = (FrameLayout) findViewById(R.id.game_field);
        Button helpMeBotButton = findViewById(R.id.helpbot_button);
        Button reloadButton = findViewById(R.id.reload_button);
        Button reloadLastButton = findViewById(R.id.reloadlast_button);


        Graphic graphic = new Graphic(this, gameField, SCREEN_WIDTH, SCREEN_HEIGHT, logic, bot);

        bot.setGraphic(graphic);


        helpMeBotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bot.helpMeBot();
            }
        });


        reloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logic.reload();
                graphic.reload();
                bot.reload();
            }
        });


        reloadLastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logic.reloadLast();
                graphic.reloadLast();
                bot.reload();
            }
        });


    }


}
