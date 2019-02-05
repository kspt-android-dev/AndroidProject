package com.julia.tag.game;

import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.julia.tag.R;
import com.julia.tag.records.AppDataBase;
import com.julia.tag.records.Record;

public class GameActivity extends AppCompatActivity implements TagAdapter.AdapterListener, SaveDialog.OnSaveListener {

    public static final String SAVE_GAME_FILE = "saves";
    public static final String DB_NAME = "BB_records";
    public static final String TAG_DIALOG_SAVES = "SaveDialog";
    public static final int NUM_COLUMN_IN_P = 4;
    public static final int NUM_COLUMN_IN_LAND = 5; //чтоб не вылазило за границы, пришлось увеличить на один

    private TagAdapter adapter;

    private AppDataBase dataBase;
    private SharedPreferences sharedPreferences;

    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        final Animation animClick = AnimationUtils.loadAnimation(this, R.anim.click);

        btnSave = findViewById(R.id.activity_game_btn_save); //Сохранить рекорд

        sharedPreferences = getSharedPreferences(SAVE_GAME_FILE, MODE_PRIVATE);

        GridView gridView = findViewById(R.id.activity_game_play_field);
        adapter = new TagAdapter(this);
        gridView.setAdapter(adapter);

        findViewById(R.id.activity_game_counter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.setWin();
            }
        });

        //задаем слушатели кнопкам Меню, Заново, Сохранить результат и Правила
        Button btn = findViewById(R.id.activity_game_btn_menu); //Меню
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animClick);
                onBackPressed();
            }
        });
        btn = findViewById(R.id.activity_game_btn_restart); //Рестарт
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animClick);
                //диалог начала новой игры
                AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                builder.setMessage(getResources().getString(R.string.dialog_restart_message))
                        .setPositiveButton(getResources().getString(R.string.dialog_yes),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        adapter.setPlayField(new PlayField());
                                    }
                                })
                        .setNegativeButton(getResources().getString(R.string.dialog_no),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                }).create();
                builder.show();
            }
        });
        btn = findViewById(R.id.activity_game_btn_info); //Правила
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animClick);
                AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                builder.setTitle(getResources().getString(R.string.game_dialog_info_title))
                        .setMessage(getResources().getString(R.string.info))
                        .setPositiveButton(android.R.string.ok,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                })
                        .create();
                builder.show();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (adapter.getPlayField().isWin()) {
                    v.startAnimation(animClick);
                    new SaveDialog().show(getSupportFragmentManager(), TAG_DIALOG_SAVES);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                    builder.setMessage(getResources().getString(R.string.game_dialog_save_no_active))
                            .setPositiveButton(android.R.string.ok,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    })
                            .create();
                    builder.show();
                }
            }
        });

        //Узнаем ширину экрана, чтобы задать подходящую ширину столбцам
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metricsB = new DisplayMetrics();
        display.getMetrics(metricsB);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            gridView.setColumnWidth(metricsB.widthPixels / NUM_COLUMN_IN_P); //узнаем ширину столбцов
        } else {
            gridView.setColumnWidth(metricsB.heightPixels / NUM_COLUMN_IN_LAND);
        }

        dataBase = Room.databaseBuilder(this, AppDataBase.class, DB_NAME)
                .allowMainThreadQueries()
                .build();
    }

    @Override
    protected void onStart() {
        adapter.load(sharedPreferences); //загрузка сохранения
        if (adapter.getPlayField().isWin())
            btnSave.setBackground(getResources().getDrawable(R.drawable.ic_save_active));
        super.onStart();
    }

    @Override
    public void onChangedMoves(int moves) {
        TextView counter = findViewById(R.id.activity_game_counter);
        counter.setText(String.valueOf(moves));
        if (counter.getText().equals("0"))
            btnSave.setBackground(getResources().getDrawable(R.drawable.ic_save_no_active));

    }

    @Override
    public void onWin() {
        new SaveDialog().show(getSupportFragmentManager(), TAG_DIALOG_SAVES);
    }

    @Override
    protected void onStop() {
        adapter.saveGame(sharedPreferences); //сохранение состояния
        super.onStop();
    }

    @Override
    public void onSave(String name) {
        Record record = new Record();
        record.name = name;
        record.moves = adapter.getPlayField().getMoves();
        dataBase.recordDao().insert(record);
        adapter.setPlayField(new PlayField());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pop_enter, R.anim.pop_exit);
    }

    @Override
    protected void onDestroy() {
        dataBase.close();
        super.onDestroy();
    }
}
