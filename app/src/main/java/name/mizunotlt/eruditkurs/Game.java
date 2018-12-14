package name.mizunotlt.eruditkurs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.concurrent.CountDownLatch;

public class Game extends Activity {
    Player player1;
    Player player2;
    GameField gameField;
    TextView tt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        FrameLayout frameLayout;
        Intent initIntent = getIntent();
        tt = (TextView) findViewById(R.id.namePlayer);
        frameLayout = (FrameLayout) findViewById(R.id.frameGame);
        player1 = new Player(initIntent.getStringExtra("firstPlayer"));
        player2 = new Player(initIntent.getStringExtra("secondPlayer"));
        gameField = new GameField(this, player1,player2);
        frameLayout.addView(gameField);

    }

    public void onClickNextTurn(View view) {

        if (!gameField.getTurn()) {
            StringBuilder str = new StringBuilder();
            str.append(gameField.getSecondPlayer().getName());
            str.append("\n");
            str.append(gameField.getSecondPlayer().getScore());
            tt.setText(str.toString());
            gameField.changeTurn();
            gameField.resetTurn();
        } else {
            StringBuilder str = new StringBuilder();
            str.append(gameField.getFirstPlayer().getName());
            str.append("\n");
            str.append(gameField.getFirstPlayer().getScore());
            tt.setText(str.toString());
            gameField.changeTurn();
            gameField.resetTurn();
        }
    }
    public void onClickRecycle(View view) {
        if (!gameField.getTurn()) {
            StringBuilder str = new StringBuilder();
            str.append(gameField.getSecondPlayer().getName());
            str.append("\n");
            str.append(gameField.getSecondPlayer().getScore());
            tt.setText(str.toString());
            gameField.changeTurnResetLetter();
            gameField.resetTurn();
        } else {
            StringBuilder str = new StringBuilder();
            str.append(gameField.getFirstPlayer().getName());
            str.append("\n");
            str.append(gameField.getFirstPlayer().getScore());
            tt.setText(str.toString());
            gameField.changeTurnResetLetter();
            gameField.resetTurn();
        }
    }

    public void onClickNewWord(View view) {
        gameField.newWord();
        tt.setText(gameField.tc);
    }

    public void onClickStartGame(View view) {
        gameField.start();
        StringBuilder str = new StringBuilder();
        str.append(gameField.getFirstPlayer().getName());
        str.append("\n");
        str.append(gameField.getFirstPlayer().getScore());
        tt.setText(str.toString());
    }
}
