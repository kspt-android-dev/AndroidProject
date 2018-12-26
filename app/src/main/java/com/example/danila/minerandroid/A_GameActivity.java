package com.example.danila.minerandroid;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;

public class A_GameActivity extends Activity {
    private final int FIELD_WIDTH = 6;
    private final int FIELD_HEIGHT = 8;

    private static int minesNumber;

    private Logic logic;

    private GraphicCell graphicCells[];
    private Chronometer chronometer;
    TextView minesNumberView;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);

        chronometer = findViewById(R.id.timer_view);
        chronometer.start();

        GridLayout gameField = findViewById(R.id.game_field);

        Button menuButton = findViewById(R.id.menu_button);
        Button reloadButton = findViewById(R.id.reload_button);
        Button reloadLastButton = findViewById(R.id.reloadlast_button);

        minesNumberView = findViewById(R.id.mines_number);


        if ((savedInstanceState == null) || !(savedInstanceState.containsKey("logic"))) {
            logic = new Logic(FIELD_WIDTH, FIELD_HEIGHT, minesNumber);
            initGameField(gameField, minesNumberView, logic);
            chronometer.setBase(SystemClock.elapsedRealtime());
        } else {
            logic = (Logic) savedInstanceState.getSerializable("logic");
            initGameField(gameField, minesNumberView, logic);
            updateAllCells();
            chronometer.setBase(savedInstanceState.getLong("chronometer"));
        }

        minesNumberView.setText("" + (logic.getMinesDigit() - logic.getFindedMinesDigit()));


        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMenuActivity();
            }
        });

        reloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logic.reload();
                updateAllCells();
                minesNumberView.setText("" + (logic.getMinesDigit() - logic.getFindedMinesDigit()));
                chronometer.setBase(SystemClock.elapsedRealtime());

            }
        });


        reloadLastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logic.reloadLast();
                updateAllCells();
                minesNumberView.setText("" + (logic.getMinesDigit() - logic.getFindedMinesDigit()));
                chronometer.setBase(SystemClock.elapsedRealtime());

            }
        });


    }

    @Override
    protected void onSaveInstanceState(Bundle saveState) {
        super.onSaveInstanceState(saveState);
        saveState.putSerializable("logic", logic);
        saveState.putLong("chronometer", chronometer.getBase());
    }


    @SuppressLint("SetTextI18n")
    void initGameField(GridLayout gameField, TextView minesNumberView, Logic logic) {


        minesNumberView.setText("" + (logic.getMinesDigit() - logic.getFindedMinesDigit()));

        /*init gameField*/
        gameField.setColumnCount(logic.getLevelWidth());

        /*init buttons*/
        graphicCells = new GraphicCell[logic.getLevelWidth() * logic.getLevelHight()];

        for (int y = 0; y < logic.getLevelHight(); y++)
            for (int x = 0; x < logic.getLevelWidth(); x++) {

                graphicCells[y * logic.getLevelWidth() + x] =
                        new GraphicCell(this, logic.getLogicCells()[y * logic.getLevelWidth() + x]);


                gameField.addView(graphicCells[y * logic.getLevelWidth() + x]);

            }

        /*set listeners*/
        for (GraphicCell gc : graphicCells) {
            gc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (logic.checkGameCondition()) {
                        case Logic.GAME_CONTINUES:
                            logic.checkCell(gc.getLogicCell().getNumberInArray());
                            updateAllCells();
                            minesNumberView.setText("" + (logic.getMinesDigit() - logic.getFindedMinesDigit()));
                            break;
                        case Logic.GAME_LOSSED:
                            logic.checkAll();
                            checkAllCells();
                            break;
                        case Logic.GAME_WIN:
                            logic.checkAll();
                            checkAllCells();
                            gameWin();
                            break;

                    }

                }
            });
            gc.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    switch (logic.checkGameCondition()) {
                        case Logic.GAME_CONTINUES:
                            logic.changeFlag(gc.getLogicCell().getNumberInArray());
                            updateAllCells();
                            minesNumberView.setText("" + (logic.getMinesDigit() - logic.getFindedMinesDigit()));
                            break;
                        case Logic.GAME_LOSSED:
                            logic.checkAll();
                            checkAllCells();
                            break;
                        case Logic.GAME_WIN:
                            logic.checkAll();
                            checkAllCells();
                            gameWin();
                            break;

                    }

                    return true;
                }
            });

        }

    }


    //Показывает изначальные условия
    @SuppressLint("SetTextI18n")
    void checkAllCells() {
        for (GraphicCell graphicCell : graphicCells)
            if (!graphicCell.getLogicCell().isFlag())
                if (graphicCell.getLogicCell().getConditon() == 9)
                    graphicCell.setBackgroundColor(GraphicCell.MINE_COLOR);
                else {
                    graphicCell.setBackgroundColor(GraphicCell.EMPTY_COLOR);
                    graphicCell.setText("" + graphicCell.getLogicCell().getConditon());
                }


    }

    void updateAllCells() {
        for (GraphicCell graphicCell : graphicCells)
            graphicCell.update();

    }

    public static void setMinesNumber(int minesNumber) {
        A_GameActivity.minesNumber = minesNumber;
    }


    private void gameWin() {
        chronometer.stop();
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("You won!");
        alertDialog.setMessage("Print name:");
        final EditText input = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        lp.setMargins(20, 20, 20, 20);
        input.setLayoutParams(lp);
        alertDialog.setView(input);
        alertDialog.setPositiveButton("OK", (dialog, which) -> {
            String name = input.getText().length() == 0 ? "[Игрок]" : String.valueOf(input.getText());
            String time = String.valueOf(chronometer.getText());
            Record record = new Record(name, time);
            /*TODO*/
            //DBConnector.addRecord(getApplicationContext(), record);
            finish();
        });
        alertDialog.show();
    }


    private void goToMenuActivity() {
        startActivity(new Intent(this, A_MenuActivity.class));
    }


}
