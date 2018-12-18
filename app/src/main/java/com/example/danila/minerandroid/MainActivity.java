package com.example.danila.minerandroid;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {


    FrameLayout gameField;
    Logic logic;
    Graphic graphic;
    Bot bot;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO take from menu
        int width = 9;
        int hight = 9;
        int minesDigit = 9;

        logic = new Logic(width, hight, minesDigit);

        bot = new Bot(logic);


        gameField = (FrameLayout) findViewById(R.id.game_field);

        graphic = new Graphic(this, gameField, logic, bot);
        bot.setGraphic(graphic);
    }


}
