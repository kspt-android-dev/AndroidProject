package com.example.danila.minerandroid;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

public class MainActivity extends Activity {


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


        GridLayout gameField = findViewById(R.id.game_field);
        Button helpMeBotButton = findViewById(R.id.helpbot_button);
        Button reloadButton = findViewById(R.id.reload_button);
        Button reloadLastButton = findViewById(R.id.reloadlast_button);
        TextView minesNumberView = findViewById(R.id.mines_number);
        TextView timerView = findViewById(R.id.timer);


        Graphic graphic = new Graphic(this, gameField, minesNumberView, timerView, logic, bot);

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
                graphic.reload();
                bot.reload();

            }
        });

    }


}
