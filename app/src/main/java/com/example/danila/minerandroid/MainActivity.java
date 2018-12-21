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
        int minesDigit = 1;//TODO take from menu

        Logic logic = new Logic(fieldWidth, fieldHeight, minesDigit);


        GridLayout gameField = findViewById(R.id.game_field);
        Button reloadButton = findViewById(R.id.reload_button);
        Button reloadLastButton = findViewById(R.id.reloadlast_button);
        TextView minesNumberView = findViewById(R.id.mines_number);



        Graphic graphic = new Graphic(this, gameField, minesNumberView,  logic);


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
