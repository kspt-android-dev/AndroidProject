package com.example.eugenia.game_2048;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startButton = findViewById(R.id.startButton);
        Button rulesButton = findViewById(R.id.rulesButton);
        Button exitButton = findViewById(R.id.exitButton);
        startButton.setOnClickListener(this);
        rulesButton.setOnClickListener(this);
        exitButton.setOnClickListener(this);

    }

    public void onClick(View view){
        switch (view.getId()) {
            case R.id.startButton: {
                Intent gameIntent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(gameIntent);
                break;
            }
            case R.id.rulesButton: {
                Intent rulesIntent = new Intent(MainActivity.this, RulesActivity.class);
                startActivity(rulesIntent);
                break;
            }
            case R.id.exitButton: {
                finish();
                break;
            }
        }

    }


}
