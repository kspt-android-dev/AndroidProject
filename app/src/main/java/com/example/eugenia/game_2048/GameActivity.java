package com.example.eugenia.game_2048;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

public class GameActivity extends Activity {
    Matrix matr;
    GridView gridView = null;
    TextView scoreView ;
    public Integer score;
    private final int CELLS_NUMBER = 4;
    private final int INIT_CELLS = 2;
    private static final String KEY_COUNT = "COUNT";
    private static final String KEY_ARRAY = "ARRAY";
    private static final String KEY_VICTORY = "VICTORY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent serviceIntent = new Intent(GameActivity.this, MusicService.class);
        startService(serviceIntent);
        gridView = (GridView) findViewById(R.id.gridview);
        scoreView = (TextView) findViewById(R.id.scoreView);
        score = 0;
        matr = new Matrix();
        for (int i = 0; i < INIT_CELLS; i++) {        //Создаем 2 начальные ячейки
            matr.newStep();
        }
        score = matr.score;
        scoreView.setText(getString(R.string.result, score));
        drawOnView(fillTab());
        gridView.setOnTouchListener(new OnSwipeTouchListener(GameActivity.this){
            @Override
            public void onSwipeTop() {
                if(matr.matrixUp()) {
                    drawOnView(fillTab());
                    score = matr.score;
                   scoreView.setText(getString(R.string.result, score));
                   if (matr.victory) showEndGame();
                }
                else showEndGame();
            }

            @Override
            public void onSwipeBottom() {
                if(matr.matrixDown()) {
                    drawOnView(fillTab());
                    score = matr.score;
                    scoreView.setText(getString(R.string.result, score));
                    if (matr.victory) showEndGame();
                }
                else showEndGame();
            }

            @Override
            public void onSwipeLeft() {
                if(matr.matrixLeft()) {
                    drawOnView(fillTab());
                    score = matr.score;
                    scoreView.setText(getString(R.string.result, score));
                    if (matr.victory) showEndGame();
                }
                else showEndGame();
            }

            @Override
            public void onSwipeRight() {
                if(matr.matrixRight()) {
                    drawOnView(fillTab());
                    score = matr.score;
                    scoreView.setText(getString(R.string.result, score));
                    if (matr.victory) showEndGame();
                }
                else showEndGame();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        startService(new Intent(GameActivity.this, MusicService.class));
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopService(new Intent(GameActivity.this, MusicService.class));
    }

    void drawOnView(Integer[] picsArray) {
        gridView.setAdapter(new ImageAdapter(this, picsArray ));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    //поиск нужной картинки
    Integer findPic(int cellVal) {
        int picId = 0;
        switch (cellVal) {
            case 2:
                picId = R.drawable.pic2;
                break;
            case 4:
                picId = R.drawable.pic4;
                break;
            case 8:
                picId = R.drawable.pic8;
                break;
            case 16:
                picId = R.drawable.pic16;
                break;
            case 32:
                picId = R.drawable.pic32;
                break;
            case 64:
                picId = R.drawable.pic64;
                break;
            case 128:
                picId = R.drawable.pic128;
                break;
            case 256:
                picId = R.drawable.pic256;
                break;
            case 512:
                picId = R.drawable.pic512;
                break;
            case 1024:
                picId = R.drawable.pic1024;
                break;
            case 2048:
                picId = R.drawable.pic2048;
                break;
            default:
                picId = R.drawable.pic0;     // Свободна
        }
        return picId;
    }

    //заполняем массив id картинок для передачи в адптер
    Integer[] fillTab() {
        Integer[] picsId = new Integer[CELLS_NUMBER * CELLS_NUMBER];
        int c = 0;
        for (int i = 0; i < CELLS_NUMBER; i++) {
            for (int j = 0; j < CELLS_NUMBER; j++) {
                int cellValue = matr.get(i, j);
                picsId[c] = findPic(cellValue);
                c++;
            }

        }
        return picsId;
    }
   //создание диалогового окна
    void showEndGame() {
        AlertDialog.Builder endDialog = new AlertDialog.Builder(GameActivity.this);
        endDialog.setTitle(R.string.dialogTitle);
        if(matr.victory)
        endDialog.setMessage(getString(R.string.victoryMessage,getString(R.string.result, score)));
        else endDialog.setMessage(getString(R.string.failMessage,getString(R.string.result, score)));
        endDialog.setPositiveButton(R.string.newGame, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                Intent gameIntent = new Intent(GameActivity.this, GameActivity.class);
                startActivity(gameIntent);
            }
        });
        endDialog.setNegativeButton(R.string.backToMenu, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int arg1) {
                Intent menuIntent = new Intent(GameActivity.this, MainActivity.class);
                startActivity(menuIntent);
            }
        });
        endDialog.setCancelable(false);
        endDialog.show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_VICTORY, matr.victory);
        outState.putInt(KEY_COUNT, score);
        outState.putIntegerArrayList(KEY_ARRAY,matr.allList);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        matr.victory = savedInstanceState.getBoolean(KEY_VICTORY);
        score = savedInstanceState.getInt(KEY_COUNT);
        matr.score = savedInstanceState.getInt(KEY_COUNT);
        scoreView.setText(getString(R.string.result, score));
        matr.allList = savedInstanceState.getIntegerArrayList(KEY_ARRAY);
        int c =0;
        for (int i = 0; i < CELLS_NUMBER; i++) {
            for (int j = 0; j < CELLS_NUMBER; j++) {
                matr.m[i][j]=matr.allList.get(c);
                c++;
            }
            drawOnView(fillTab());

        }
    }
}
