package com.example.danila.minerandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {


    LinearLayout gameField;
    Logic logic;
    Graphic graphic;
    Bot bot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO take from menu
        int width = 9;
        int hight = 9;
        int minesDigit = 9;


        logic = new Logic(width, hight, minesDigit);

        bot = new Bot(logic);

        graphic = new Graphic(this, logic, bot);
        gameField = (LinearLayout) findViewById(R.id.gameField);
    }


}
