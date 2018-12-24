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
    private TextView textView;
    private FrameLayout frameLayout;
    private boolean orientation = false;
    private Point size;
    private int sizeCell;
    private final int CELLS_IN_ROW = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        textView = (TextView) findViewById(R.id.namePlayer);
        initGameField();
        resetGameField(savedInstanceState);
    }

    private void initGameField() {
        Intent initIntent = getIntent();
        player1 = new Player(initIntent.getStringExtra("firstPlayer"));
        player2 = new Player(initIntent.getStringExtra("secondPlayer"));
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            size = new Point();
            Display display = getWindowManager().getDefaultDisplay();
            display.getSize(size);
            //Округление в сторону нуля
            sizeCell = ((size.y / CELLS_IN_ROW) / 10) * 10;
            orientation = true;
        }
        else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            size = new Point();
            Display display = getWindowManager().getDefaultDisplay();
            display.getSize(size);
            //Округление в сторону 0
            sizeCell = ((size.x / CELLS_IN_ROW) / 10) * 10;
            orientation = false;
        }

        gameField = new GameField(this, player1, player2, sizeCell);
        frameLayout = (FrameLayout) findViewById(R.id.frameGame);
        gameField.setOrientation(orientation);
        gameField.setSizeScreen(size);
        gameField.setStartPoint();
        frameLayout.addView(gameField);
    }
    private void resetGameField(Bundle savedInstanceState){
        if (savedInstanceState != null){
            gameField.resetGameField(savedInstanceState);
            if(gameField.getGames().getFirstTurn()) {
                if (!gameField.getGames().getTurn()) {
                    String str = gameField.getGames().getFirstPlayer().getName() +
                            "\n" +
                            gameField.getGames().getFirstPlayer().getScore();
                    textView.setText(str);
                } else {
                    String str = gameField.getGames().getSecondPlayer().getName() +
                            "\n" +
                            gameField.getGames().getSecondPlayer().getScore();
                    textView.setText(str);
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
            String str = gameField.getGames().getSecondPlayer().getName() +
                    "\n" +
                    gameField.getGames().getSecondPlayer().getScore();
            textView.setText(str);
            gameField.changeTurn();
            gameField.getGames().resetTurn();
        } else {
            String str = gameField.getGames().getFirstPlayer().getName() +
                    "\n" +
                    gameField.getGames().getFirstPlayer().getScore();
            textView.setText(str);
            gameField.changeTurn();
            gameField.getGames().resetTurn();
        }
    }

    public void onClickRecycle(View view) {
        if (gameField.isTempListNumCells()) {
            if (!gameField.getGames().getTurn()) {
                String str = gameField.getGames().getSecondPlayer().getName() +
                        "\n" +
                        gameField.getGames().getSecondPlayer().getScore();
                textView.setText(str);
                gameField.changeTurnResetLetter();
                gameField.getGames().resetTurn();
            } else {
                String str = gameField.getGames().getFirstPlayer().getName() +
                        "\n" +
                        gameField.getGames().getFirstPlayer().getScore();
                textView.setText(str);
                gameField.changeTurnResetLetter();
                gameField.getGames().resetTurn();
            }
        }
    }

    public void onClickNewWord(View view) {
        gameField.newWord();
        textView.setText(gameField.getGames().tc);
    }

    public void onClickStartGame(View view) {
        StringBuilder str = new StringBuilder();
        if (!gameField.getGames().getFirstTurn()){
            gameField.start();
            str.append(gameField.getGames().getFirstPlayer().getName());
            str.append("\n");
            str.append(gameField.getGames().getFirstPlayer().getScore());
            textView.setText(str.toString());
        }
    }
}
