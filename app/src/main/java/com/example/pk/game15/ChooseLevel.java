package com.example.pk.game15;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
public class ChooseLevel extends AppCompatActivity {

    private int width;
    private int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_level);

        Button normal = findViewById(R.id.normal);
        Button easy = findViewById(R.id.easy);
        Button hard = findViewById(R.id.hard);
        Display display = getWindowManager().getDefaultDisplay();
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            width = display.getWidth() / 3;
            height = display.getWidth() / 7;
        }
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            width = display.getHeight() / 3;
            height = display.getHeight() / 7;
        }
        ViewGroup.LayoutParams params = easy.getLayoutParams();
        params.width = width;
        params.height = height;
        easy.setLayoutParams(params);
        normal.setLayoutParams(params);
        hard.setLayoutParams(params);


        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseLevel.this, Game.class);
                intent.putExtra("side", 4);
                startActivity(intent);
            }
        });

        normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseLevel.this, Game.class);
                intent.putExtra("side", 5);
                startActivity(intent);
            }
        });

        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseLevel.this, Game.class);
                intent.putExtra("side", 6);
                startActivity(intent);
            }
        });
    }
}
