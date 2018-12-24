package name.mizunotlt.eruditkurs;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Game extends Activity {

    public Player player1;
    public Player player2;
    public GameField gameField;
    private TextView tt;
    private FrameLayout frameLayout;
    private boolean flag = false;
    private Point size = new Point();
    private int sizeCell;
    private final int CELLS_IN_ROW = 15;

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
            Point size = new Point();
            Display display = getWindowManager().getDefaultDisplay();
            display.getSize(size);
            sizeCell = ((size.y / CELLS_IN_ROW) / 10) * 10;
            flag = true;
        }
        else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_game);
            frameLayout = (FrameLayout) findViewById(R.id.frameGame);
            tt = (TextView) findViewById(R.id.namePlayer);
            Display display = getWindowManager().getDefaultDisplay();
            display.getSize(size);
            sizeCell = ((size.x / CELLS_IN_ROW) / 10) * 10;
            flag = false;
        }

    }

    private void initGameField() {
        Intent initIntent = getIntent();
        player1 = new Player(initIntent.getStringExtra("firstPlayer"));
        player2 = new Player(initIntent.getStringExtra("secondPlayer"));
        gameField = new GameField(this, player1, player2, sizeCell);
        gameField.setFlag(flag);
        gameField.setSizeScreen(size);
        gameField.setStartPoint();
        frameLayout.addView(gameField);
    }
    private void resetGameField(Bundle savedInstanceState){
        if (savedInstanceState != null){
            gameField.resetGameField(savedInstanceState);
            if(gameField.getGames().getFirstTurn()) {
                if (!gameField.getGames().getTurn()) {
                    StringBuilder str = new StringBuilder();
                    str.append(gameField.getGames().getFirstPlayer().getName());
                    str.append("\n");
                    str.append(gameField.getGames().getFirstPlayer().getScore());
                    tt.setText(str.toString());
                } else {
                    StringBuilder str = new StringBuilder();
                    str.append(gameField.getGames().getSecondPlayer().getName());
                    str.append("\n");
                    str.append(gameField.getGames().getSecondPlayer().getScore());
                    tt.setText(str.toString());
                }
            }
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle saveState) {
        super.onSaveInstanceState(saveState);
        //Сохранение игроков
        saveState.putString("firstPlayerName", gameField.getGames().getFirstPlayer().getName());
        saveState.putString("secondPlayerName", gameField.getGames().getSecondPlayer().getName());
        saveState.putInt("secondPlayerScore", gameField.getGames().getSecondPlayer().getScore());
        saveState.putInt("firstPlayerScore", gameField.getGames().getFirstPlayer().getScore());
        if(gameField.getGames().getFirstTurn()){
            saveState.putCharArray("firstPlayerLetter",gameField.getGames().getFirstPlayer().getLetter());
            saveState.putCharArray("secondPlayerLetter",gameField.getGames().getSecondPlayer().getLetter());
        }
        //сохрание переменных поля
        saveState.putBoolean("firstTurn", gameField.getGames().getFirstTurn());
        saveState.putBoolean("flag", gameField.getFlag());
        saveState.putBoolean("turn", gameField.getGames().getTurn());
        saveState.putBoolean("nextTurn", gameField.getGames().getNextTurn());

        //Сохранение поля
        saveState.putCharArray("letterInBoard", gameField.getGames().getLetterInBoard());
        saveState.putIntegerArrayList("indexLetterInBoard", gameField.getGames().getIndexLetterInBoadr());

        //Сохранение размеров ячейки
        saveState.putInt("sizeCell", gameField.getSizeCell());
        saveState.putInt("sizeX", gameField.getSizeScreen().x);
        saveState.putInt("sizeY", gameField.getSizeScreen().y);

        //Сохранение оставшихся букв
        saveState.putIntegerArrayList("freeLetter", gameField.getGames().getGameRule().getCountFreeLetter());
    }

    public void onClickNextTurn(View view) {

        if (!gameField.getGames().getTurn()) {
            StringBuilder str = new StringBuilder();
            str.append(gameField.getGames().getSecondPlayer().getName());
            str.append("\n");
            str.append(gameField.getGames().getSecondPlayer().getScore());
            tt.setText(str.toString());
            gameField.changeTurn();
            gameField.getGames().resetTurn();
        } else {
            StringBuilder str = new StringBuilder();
            str.append(gameField.getGames().getFirstPlayer().getName());
            str.append("\n");
            str.append(gameField.getGames().getFirstPlayer().getScore());
            tt.setText(str.toString());
            gameField.changeTurn();
            gameField.getGames().resetTurn();
        }
    }

    public void onClickRecycle(View view) {
        if (gameField.isTempListNumCells()) {
            if (!gameField.getGames().getTurn()) {
                StringBuilder str = new StringBuilder();
                str.append(gameField.getGames().getSecondPlayer().getName());
                str.append("\n");
                str.append(gameField.getGames().getSecondPlayer().getScore());
                tt.setText(str.toString());
                gameField.changeTurnResetLetter();
                gameField.getGames().resetTurn();
            } else {
                StringBuilder str = new StringBuilder();
                str.append(gameField.getGames().getFirstPlayer().getName());
                str.append("\n");
                str.append(gameField.getGames().getFirstPlayer().getScore());
                tt.setText(str.toString());
                gameField.changeTurnResetLetter();
                gameField.getGames().resetTurn();
            }
        }
    }

    public void onClickNewWord(View view) {
        gameField.newWord();
        tt.setText(gameField.getGames().tc);
    }

    public void onClickStartGame(View view) {
        StringBuilder str = new StringBuilder();
        if (!gameField.getGames().getFirstTurn()){
            gameField.start();
            str.append(gameField.getGames().getFirstPlayer().getName());
            str.append("\n");
            str.append(gameField.getGames().getFirstPlayer().getScore());
            tt.setText(str.toString());
        }
    }
}
