package com.btn.thuynhung.puzzlegame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class EndGameAct extends AppCompatActivity {
    private TextView timeLable, moveLable, loseOrWin, newGame, scoreText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_game);

        mapping();

        if (DataGame.getDatagame().checkForWin()) {

            int moveScore = getIntent().getIntExtra("MOVE", 0);
            String timeScore = getIntent().getStringExtra("TIME");

            moveLable.setText("MOVE\n" + moveScore);
            timeLable.setText("TIME LEFT\n" + timeScore);

            loseOrWin.setText("YOU WIN!");
        } else {
            moveLable.setText("");
            timeLable.setText("");
            loseOrWin.setText("Time's up!");
            scoreText.setText("YOU LOSE");
        }

        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataGame.getDatagame().clearArrNumbs();
                DataGame.getDatagame().resetMove();
                startActivity(new Intent(EndGameAct.this, PlayAct.class));
                finish();
            }
        });
    }

    private void mapping() {
        scoreText = (TextView) findViewById(R.id.scoreText);
        timeLable = (TextView) findViewById(R.id.timeLable);
        moveLable = (TextView) findViewById(R.id.moveLable);
        loseOrWin = (TextView) findViewById(R.id.lose_or_win);
        newGame = (TextView) findViewById(R.id.newGame);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

}
