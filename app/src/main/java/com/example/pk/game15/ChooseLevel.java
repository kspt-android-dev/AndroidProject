package com.example.pk.game15;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.GridLayout;

public class ChooseLevel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_level);

        Button button = findViewById(R.id.easy);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game(4);
            }
        });

        Button button1 = findViewById(R.id.normal);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game(5);
            }
        });

        Button button2 = findViewById(R.id.hard);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game(6);
            }
        });
    }

    void game(int side) {
        setContentView(R.layout.activity_game);

        Display display = getWindowManager().getDefaultDisplay();
        GridLayout field = findViewById(R.id.field);
        ViewGroup.LayoutParams params = field.getLayoutParams();
        params.width = display.getWidth() - 150;
        params.height = display.getWidth() - 150;
        field.setLayoutParams(params);

        field.setColumnCount(side);
        field.setRowCount(side);

        Logic logic = new Logic(side);

        Graphic graphic = new Graphic(this, field, logic, side);

        final Chronometer chronometer = findViewById(R.id.chronometer);
        chronometer.start();
        Button pause = findViewById(R.id.pause_button);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.stop();
                AlertDialog.Builder builder = new AlertDialog.Builder(ChooseLevel.this);
                builder.setMessage("Paused")
                        .setCancelable(false)
                        .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                chronometer.start();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        Button menu = findViewById(R.id.menu_button);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseLevel.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
