package com.example.danila.minerandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class A_ChooseLevelActivity extends Activity {
    final int EASY_MINES_NUMBER = 6;
    final int MEDIUM_MINES_NUMBER = 9;
    final int HARD_MINES_NUMBER = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_level_layout);


        Button easy_button = findViewById(R.id.easy);
        Button medium_button = findViewById(R.id.medium);
        Button hard_button = findViewById(R.id.hard);

        easy_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToGameActivity(EASY_MINES_NUMBER);
            }
        });

        medium_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToGameActivity(MEDIUM_MINES_NUMBER);
            }
        });

        hard_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToGameActivity(HARD_MINES_NUMBER);
            }
        });


    }

    private void goToGameActivity(int minesNumber) {
        A_GameActivity.setMinesNumber(minesNumber);
        startActivity(new Intent(this, A_GameActivity.class));
    }
}
