package polytech.vladislava.sudoku;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class GameActivity extends AppCompatActivity {

    private DBConnector connector;
    private boolean loaded = false;
    private Chronometer chronometer;
    private List<SudokuButton> gameButtons;
    private Sudoku game;
    private Button check;
    private Button help;
    private int tips = 0;
    private GridLayout gameGrid;
    private static final int FIELD_SIZE = 9;
    private static final int MARGINS = 20;
    private static final int DURATION = 1000;
    private final static int BLOCK_SIZE1 = 3;
    private final static int BLOCK_SIZE2 = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameGrid = findViewById(R.id.a_game_sudokuGrid);
        chronometer = findViewById(R.id.a_game_chrono);
        chronometer.stop();
        connector = new DBConnector(getApplicationContext());

        LinearLayout loading = findViewById(R.id.a_game_loadingScreen);

        check = findViewById(R.id.a_game_check);
        check.setOnClickListener(v -> checkGame());

        help = findViewById(R.id.a_game_help);
        help.setOnClickListener(v -> help());

        if (savedInstanceState == null || !savedInstanceState.containsKey("sudokuGame")) {
            loading.setAlpha(1f);
            generateNewStuff();
        } else {
            loaded = true;
            loading.setAlpha(0f);
            loadSavedGame(savedInstanceState);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle saveState) {
        super.onSaveInstanceState(saveState);

        if (!loaded)
            return;

        saveState.putSerializable("sudokuGame", game);
        saveState.putLong("chronoBase", chronometer.getBase());
        saveState.putInt("tipsCounter", tips);

    }

    private void loadSavedGame(Bundle savedInstanceState) {
        game = (Sudoku) savedInstanceState.getSerializable("sudokuGame");
        tips = savedInstanceState.getInt("tipsCounter");
        chronometer.setBase(savedInstanceState.getLong("chronoBase"));
        fillGrid();
        chronometer.start();
    }

    private void help() {
        List<Integer> notFinished = new ArrayList<>();
        for (int i = 0; i < FIELD_SIZE * FIELD_SIZE; i++) {
            if (game.getNumber(i % FIELD_SIZE, i / FIELD_SIZE) == 0) {
                notFinished.add(i);
            }
        }
        if (notFinished.isEmpty())
            return;
        Collections.shuffle(notFinished);
        int helpingIndex = notFinished.get(0);
        int solution = game.getSolutionForHelp(helpingIndex % FIELD_SIZE, helpingIndex / FIELD_SIZE);
        gameButtons.get(helpingIndex).setHepled(solution);
        tips++;
    }

    private void generateNewStuff() {
        AsyncTask generatingMap = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                generateAsync();
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                notifyGameCreated();
            }
        };
        generatingMap.execute();
    }

    private void generateAsync() {
        game = new Sudoku();
        SudokuButton.game = game;
    }

    private void checkGame() {
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (!checkPoint(i, j))
                    return;
            }
        }
        win();
    }

    private void win() {
        chronometer.stop();
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(R.string.Congrats);
        alertDialog.setMessage(R.string.NameEnter);
        final EditText input = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        lp.setMargins(MARGINS, MARGINS, MARGINS, MARGINS);
        input.setLayoutParams(lp);
        alertDialog.setView(input);
        alertDialog.setPositiveButton(R.string.Ready, (dialog, which) -> {
            int tips = this.tips;
            String name = input.getText().length() == 0 ? getString(R.string.Player) : String.valueOf(input.getText());
            String time = String.valueOf(chronometer.getText());
            Record record = new Record(name, time, tips);
            connector.addRecord(record);
            finish();
        });
        alertDialog.show();
    }

    private boolean checkPoint(int x, int y) {
        if (game.getNumber(x, y) == 0) {
            makeToast(getString(R.string.EmptyCells));
            return false;
        }
        if (!game.isCheckValid(x, y)) {
            makeToast(getString(R.string.WrongAnswer));
            return false;
        }
        return true;
    }

    private void makeToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void fillGrid() {

        gameButtons = new ArrayList<>();
        gameGrid.setColumnCount(FIELD_SIZE + 2);

        int counter = 0;
        int second_counter = 0;

        for (int i = 0; i < FIELD_SIZE * FIELD_SIZE; i++) {
            SudokuButton button = new SudokuButton(this, i, 0);
            int actualy = game.getNumber(i % FIELD_SIZE, i / FIELD_SIZE);
            if (actualy != 0) {
                if (game.isHelped(i % FIELD_SIZE, i / FIELD_SIZE))
                    button.setHepled(actualy);
                else if (game.isInitial(i % FIELD_SIZE, i / FIELD_SIZE)) {
                    button.setContent(actualy);
                    button.makeStatic();
                } else
                    button.setContent(actualy);
            }
            gameButtons.add(button);
            gameGrid.addView(button);
            counter++;
            second_counter++;
            if (second_counter == FIELD_SIZE * BLOCK_SIZE1 && !(i > 60)) {
                for (int j = 0; j < FIELD_SIZE + 2; j++) {
                    Space space = new Space(this);
                    GridLayout.LayoutParams doubleLayoutParams = new GridLayout.LayoutParams();
                    doubleLayoutParams.width = 0;
                    doubleLayoutParams.height = 8;
                    doubleLayoutParams.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
                    doubleLayoutParams.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
                    space.setLayoutParams(doubleLayoutParams);
                    gameGrid.addView(space);
                }
                counter = 0;
                second_counter = 0;
                continue;
            }
            if (counter == BLOCK_SIZE1 || counter == BLOCK_SIZE2) {
                Space space = new Space(this);
                GridLayout.LayoutParams doubleLayoutParams = new GridLayout.LayoutParams();
                doubleLayoutParams.width = 0;
                doubleLayoutParams.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
                doubleLayoutParams.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
                space.setLayoutParams(doubleLayoutParams);
                gameGrid.addView(space);
            }
            if (counter == FIELD_SIZE)
                counter = 0;
        }
    }

    private void notifyGameCreated() {
        loaded = true;
        fillGrid();
        LinearLayout loading = findViewById(R.id.a_game_loadingScreen);
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setDuration(DURATION);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //nothing
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                //nothing
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                loading.setAlpha(0f);
            }
        });
        loading.setAnimation(fadeOut);
        fadeOut.start();
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }

    @Override
    protected void onDestroy() {
        connector.close();
        super.onDestroy();
    }
}
