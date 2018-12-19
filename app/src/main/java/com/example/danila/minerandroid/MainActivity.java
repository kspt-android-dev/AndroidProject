package com.example.danila.minerandroid;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
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
        Button helpMeBotButton = findViewById(R.id.help_me_bot);

        Graphic graphic = new Graphic(this, gameField, helpMeBotButton, SCREEN_WIDTH, SCREEN_HEIGHT, logic, bot);
        bot.setGraphic(graphic);
    }


}
