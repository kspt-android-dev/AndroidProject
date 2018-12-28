package com.example.pk.game15;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.GridLayout;

public class Game extends AppCompatActivity {
    private Chronometer chronometer;
    private boolean running = false;
    private long milliseconds;
    private Logic logic;
    private Graphic graphic;
    private int width;
    private int height;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        int side = intent.getIntExtra("side", 4);
        Display display = getWindowManager().getDefaultDisplay();
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            width = display.getWidth() - 150;
            height = display.getWidth() - 150;
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            width = display.getHeight() - 150;
            height = display.getHeight() - 150;
        }
        GridLayout field = findViewById(R.id.field);
        ViewGroup.LayoutParams params = field.getLayoutParams();
        params.width = width;
        params.height = height;
        field.setLayoutParams(params);
        field.setColumnCount(side);
        field.setRowCount(side);

        chronometer = findViewById(R.id.chronometer);
        chronometer.setFormat("Time: %s");
        chronometer.setBase(SystemClock.elapsedRealtime());
        milliseconds = 0;
        startCh();

        logic = new Logic(side);

        if (savedInstanceState != null) {
            logic = savedInstanceState.getParcelable("logic");
            chronometer.setBase(savedInstanceState.getLong("milliseconds"));
        }

        graphic = new Graphic(this, this, field, logic, side);

        Button pause = findViewById(R.id.pause_button);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseCh();
                AlertDialog.Builder builder = new AlertDialog.Builder(Game.this);
                builder.setMessage("Paused")
                        .setCancelable(false)
                        .setNegativeButton("Restart", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                logic.newGame();
                                graphic.update(logic);
                                resetCh();
                                startCh();
                            }
                        })
                        .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                startCh();
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
                Intent intent = new Intent(Game.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void startCh() {
        if (!running) {
            chronometer.setBase(SystemClock.elapsedRealtime() - milliseconds);
            chronometer.start();
            running = true;
        }
    }

    private void pauseCh() {
        if (running) {
            chronometer.stop();
            milliseconds = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        }
    }

    private void resetCh() {
        chronometer.setBase(SystemClock.elapsedRealtime());
        milliseconds = 0;
    }

    //диалоговое окно приокончании игры
    public void endGame() {
        pauseCh();
        AlertDialog.Builder builder = new AlertDialog.Builder(Game.this);
        builder.setMessage("You win!")
                .setMessage("Your time: " + milliseconds / 60000 + " minutes " + (milliseconds - milliseconds / 60) / 1000 + " seconds")
                .setCancelable(false)
                .setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logic.newGame();
                        graphic.update(logic);
                    }
                })
                .setNegativeButton("Back to menu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Game.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
        resetCh();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("logic", logic);
        outState.putLong("milliseconds", chronometer.getBase());
    }
}
