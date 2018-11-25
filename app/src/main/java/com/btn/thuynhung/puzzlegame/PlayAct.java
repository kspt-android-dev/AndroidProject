package com.btn.thuynhung.puzzlegame;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class PlayAct extends AppCompatActivity {
    private final long START_TIME_IN_MILLIS = 300000;

    private Button buttonReset;
    private TextView moveNumbItem;
    private int moveNumb = 0;

    private TextView countdownText;
    private CountDownTimer countDownTimer;
    private long timeLeftInMilliseconds = START_TIME_IN_MILLIS;
    private long endTime;
    private boolean timerRunning;
    private boolean checkEndTime = false;
    private String timeLeftText;

    private GridView gridView;
    private NumbsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Play game");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mapping();
        startTimer();
        setData();
        setEvent();
        resetClick();
    }

    private void mapping() {
        gridView = findViewById(R.id.mainGrid);
        countdownText = findViewById(R.id.countdown);
        buttonReset = findViewById(R.id.resetButton);
        moveNumbItem = findViewById(R.id.moveNumb);
    }

    private void setData() {
        DataGame.getDatagame().setNumbs();
        adapter = new NumbsAdapter(PlayAct.this, 0, DataGame.getDatagame().getArrNumbs());
        gridView.setAdapter(adapter);
    }

    private void setEvent() {

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!checkEndTime) {
                    DataGame.getDatagame().moveItems(position);
                    adapter.notifyDataSetChanged();
                    moveNumb = DataGame.getDatagame().getMove();
                    moveNumbItem.setText("" + moveNumb);
                    if (DataGame.getDatagame().checkForWin()) {
                        Intent intent = new Intent(getApplicationContext(), EndGameAct.class);
                        intent.putExtra("MOVE", moveNumb);
                        intent.putExtra("TIME", timeLeftText);
                        startActivity(intent);
                    }
                }
                else {
                    startActivity(new Intent(PlayAct.this, EndGameAct.class));
                }
            }

        });
    }

    //save state of game after rotating screen

    /*when our activity in portrait mode is destroyed,
     *this method will be called and we'll safely save the values
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("mililsLeft", timeLeftInMilliseconds);
        outState.putBoolean("timerRunning", timerRunning);
        outState.putLong("endTime", endTime);
        outState.putInt("move", moveNumb);
        outState.putBoolean("checkEndTime", checkEndTime);

    }

    //this method is used to retrieve values back when the landscape activity is created
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        timeLeftInMilliseconds = savedInstanceState.getLong("millisLeft");
        timerRunning = savedInstanceState.getBoolean("timerRunning");
        updateTimer();

        if (timerRunning) {
            endTime = savedInstanceState.getLong("endTime");
            timeLeftInMilliseconds = endTime - System.currentTimeMillis();
            startTimer();
        }

        moveNumb = savedInstanceState.getInt("move");
        checkEndTime = savedInstanceState.getBoolean("checkEndTime");
    }

    //work with button reset
    public void resetClick() {
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTimer();
                AlertDialog.Builder builder = new AlertDialog.Builder(PlayAct.this);

                builder.setTitle("Reset game");
                builder.setMessage("Are you sure?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataGame.getDatagame().clearArrNumbs();
                        DataGame.getDatagame().resetMove();
                        startActivity(new Intent(PlayAct.this, PlayAct.class));
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startStop();
                    }
                });

                builder.setCancelable(false); //prevent dialog from geting dismissed on outside touch
                AlertDialog dialog = builder.show();
            }
        });
    }

    private boolean twice = false;

    @Override
    public void onBackPressed() {
        if (twice) {
            startActivity(new Intent(PlayAct.this, MainActivity.class));
        }
        twice = true;
        Toast.makeText(PlayAct.this, "Please press BACK again to go back to Main Menu",
                Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                twice = false;
            }
        }, 3000);
    }

    //For countdown timer
    public void startStop() {
        if (timerRunning) stopTimer();
        else startTimer();
    }

    public void startTimer() {
        endTime = System.currentTimeMillis() + timeLeftInMilliseconds;

        countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMilliseconds = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                checkEndTime = true;
            }
        }.start();

        timerRunning = true;
    }

    public void stopTimer() {
        countDownTimer.cancel();
        timerRunning = false;
    }

    public void updateTimer() {
        int minutes = (int) timeLeftInMilliseconds / 60000;
        int seconds = (int) timeLeftInMilliseconds % 60000 / 1000;

        timeLeftText = "" + minutes;
        timeLeftText += ":";
        if (seconds < 10) timeLeftText += 0;
        timeLeftText += seconds;

        countdownText.setText(timeLeftText);
    }


}
