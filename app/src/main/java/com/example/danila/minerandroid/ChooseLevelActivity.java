package com.example.danila.minerandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseLevelActivity extends Activity {
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
                goToGameActivity(6);
            }
        });

        medium_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToGameActivity(9);
            }
        });

        hard_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToGameActivity(12);
            }
        });


    }

    void goToGameActivity(int minesNumber) {
        GameActivity.setMinesNumber(minesNumber);
        startActivity(new Intent(this, GameActivity.class));
    }
}
