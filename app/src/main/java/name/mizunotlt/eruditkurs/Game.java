package name.mizunotlt.eruditkurs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.concurrent.CountDownLatch;

public class Game extends Activity {
    private Player player1;
    private Player player2;
    private GameField gameField;
    private TextView tt;
    private FrameLayout frameLayout;
    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setOrientationLayout();
        initGameField();
        resetGameField(savedInstanceState);
    }

    private void setOrientationLayout() {
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.land_scape_orientation);
            frameLayout = (FrameLayout) findViewById(R.id.landFrameLayout);
            tt = (TextView) findViewById(R.id.namePlayerLand);
            flag = true;
        } else {
            if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                setContentView(R.layout.activity_game);
                frameLayout = (FrameLayout) findViewById(R.id.frameGame);
                tt = (TextView) findViewById(R.id.namePlayer);
                flag = false;
            }
        }
    }

    private void initGameField() {
        Intent initIntent = getIntent();
        player1 = new Player(initIntent.getStringExtra("firstPlayer"));
        player2 = new Player(initIntent.getStringExtra("secondPlayer"));
        gameField = new GameField(this, player1, player2);
        gameField.setFlag(flag);
        gameField.setStartPoint();
        frameLayout.addView(gameField);
    }
    private void resetGameField(Bundle savedInstanceState){
        if (savedInstanceState != null){
            gameField.resetGameField(savedInstanceState);
            if(!gameField.getTurn()){
                StringBuilder str = new StringBuilder();
                str.append(gameField.getFirstPlayer().getName());
                str.append("\n");
                str.append(gameField.getFirstPlayer().getScore());
                tt.setText(str.toString());
            }
            else{
                StringBuilder str = new StringBuilder();
                str.append(gameField.getSecondPlayer().getName());
                str.append("\n");
                str.append(gameField.getSecondPlayer().getScore());
                tt.setText(str.toString());
            }
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle saveState) {
        super.onSaveInstanceState(saveState);
        //Сохранение игроков
        saveState.putString("firstPlayerName", gameField.getFirstPlayer().getName());
        saveState.putString("secondPlayerName", gameField.getSecondPlayer().getName());
        saveState.putInt("secondPlayerScore", gameField.getSecondPlayer().getScore());
        saveState.putInt("firstPlayerScore", gameField.getFirstPlayer().getScore());
        saveState.putCharArray("firstPlayerLetter",gameField.getFirstPlayer().getLetter());
        saveState.putCharArray("secondPlayerLetter",gameField.getSecondPlayer().getLetter());

        //сохрание переменных поля
        saveState.putBoolean("firstTurn", gameField.getFirstTurn());
        saveState.putBoolean("flag", gameField.getFlag());
        saveState.putBoolean("turn", gameField.getTurn());
        saveState.putBoolean("nextTurn", gameField.getNextTurn());

        //Сохранение поля
        saveState.putCharArray("letterInBoard", gameField.getLetterInBoard());
        saveState.putIntegerArrayList("indexLetterInBoard", gameField.getIndexLetterInBoadr());

        //Сохранение оставшихся букв
        saveState.putIntegerArrayList("freeLetter", gameField.getGameRule().getCountFreeLetter());
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
        StringBuilder str = new StringBuilder();
        if (!gameField.getFirstTurn()){
            gameField.start();
            str.append(gameField.getFirstPlayer().getName());
            str.append("\n");
            str.append(gameField.getFirstPlayer().getScore());
            tt.setText(str.toString());
        }
    }
}
