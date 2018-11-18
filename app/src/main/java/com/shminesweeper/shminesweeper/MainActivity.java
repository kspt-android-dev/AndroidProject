package com.shminesweeper.shminesweeper;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    PlayingField playingField;
    FrameLayout frameLayout;

    ImageButton flagButton;
    ImageButton questionButton;
    ImageButton touchButton;

    View.OnClickListener buttonsOnClickListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setLayoutComponents();

        addPlayingFieldToLayout();

        setButtonsOnClickListener();

        addOnClickListenerToButtons();

        checkSavedInstanceState(savedInstanceState);

    }

    // определение всех компонентов layout'a из ресурсов
    private void setLayoutComponents(){
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        flagButton = findViewById(R.id.flag_button);
        questionButton = findViewById(R.id.question_button);
        touchButton = findViewById(R.id.touch_button);
    }

    // добавление игрового поля на frameLayout
    private void addPlayingFieldToLayout(){
        playingField = (Settings.fieldMode == Settings.FieldMode.HEXAGONAL) ?
                new PlayingField(this) : new SquarePlayingField(this);
        frameLayout.addView(playingField);
    }

    // определение onClickListener'a для кнопок touch, flag, question
    private void setButtonsOnClickListener(){
        buttonsOnClickListner = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.flag_button:
                        playingField.setCheckedButton(PlayingField.CheckedButton.FLAG);
                        setActiveButtonColor(flagButton, touchButton, questionButton);
                        break;
                    case R.id.question_button:
                        playingField.setCheckedButton(PlayingField.CheckedButton.QUESTION);
                        setActiveButtonColor(questionButton, flagButton, touchButton);
                        break;
                    case R.id.touch_button:
                        playingField.setCheckedButton(PlayingField.CheckedButton.TOUCH);
                        setActiveButtonColor(touchButton, flagButton, questionButton);
                        break;
                    case R.id.settings_button:
                        Intent openSettings = new Intent(MainActivity.this, SettingsActivity.class);
                        startActivityForResult(openSettings, 1);
                        break;
                    case R.id.reload_button:
                        playingField.startNewGame();
                        setActiveButtonColor(touchButton, flagButton, questionButton);
                        break;
                }
            }
        };
    }

    // добавление onClickListener'a кнопкам touch, question, flag
    private void addOnClickListenerToButtons() {
        flagButton.setOnClickListener(buttonsOnClickListner);
        questionButton.setOnClickListener(buttonsOnClickListner);
        touchButton.setOnClickListener(buttonsOnClickListner);
        findViewById(R.id.settings_button).setOnClickListener(buttonsOnClickListner);
        findViewById(R.id.reload_button).setOnClickListener(buttonsOnClickListner);
    }

    // проверка сохранённого состояния для воссоздания поля после поворота экрана
    private void checkSavedInstanceState(Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            playingField.restoreField(savedInstanceState);

            restoreCheckedButton();
        }
        else {
            playingField.startNewGame();
        }
    }

    // восстановление активной кнопки (flag | touch | question) после поворота экрана
    private void restoreCheckedButton() {

        if (playingField.getCheckedButton().equals(PlayingField.CheckedButton.TOUCH)) {
            playingField.setCheckedButton(PlayingField.CheckedButton.TOUCH);
            touchButton.setColorFilter(Color.WHITE);
            flagButton.setColorFilter(Color.BLACK);
            questionButton.setColorFilter(Color.BLACK);
        }
        else if (playingField.getCheckedButton().equals(PlayingField.CheckedButton.FLAG)) {
            playingField.setCheckedButton(PlayingField.CheckedButton.FLAG);
            flagButton.setColorFilter(Color.WHITE);
            touchButton.setColorFilter(Color.BLACK);
            questionButton.setColorFilter(Color.BLACK);
        } else if (playingField.getCheckedButton().equals(PlayingField.CheckedButton.QUESTION)) {
            playingField.setCheckedButton(PlayingField.CheckedButton.QUESTION);
            questionButton.setColorFilter(Color.WHITE);
            flagButton.setColorFilter(Color.BLACK);
            touchButton.setColorFilter(Color.BLACK);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // cсохранение состояний ячеек поля
        outState.putIntegerArrayList("cellsWithMine", playingField.getCellsWithMine());
        outState.putIntegerArrayList("cellsWithFlag", playingField.getCellsWithFlag());
        outState.putIntegerArrayList("cellsWithQuestion", playingField.getCellsWithQuestion());
        outState.putIntegerArrayList("openCells", playingField.getOpenCells());

        // сохранение переменных состояния поля
        outState.putBoolean("gameStoped", playingField.isGameStoped());
        outState.putBoolean("firstOpening", playingField.isFirstOpening());
        outState.putInt("totalOpenCells", playingField.getTotalOpenCells());
        outState.putString("checkedButton",
                playingField.getCheckedButton().equals(PlayingField.CheckedButton.FLAG) ? "flag" :
                        playingField.getCheckedButton().equals(PlayingField.CheckedButton.TOUCH) ? "touch" :
                                "question");
        outState.putString("shownDialog", playingField.getShownDialogString());

        // сохранение размеров для дальнейшего смещения поля TODO delete
        outState.putInt("screenSizeX", playingField.getScrollX() + (getScreenSize().x / 2));
        outState.putInt("screenSizeY", playingField.getScrollY() + (getScreenSize().y / 2));
    }

    private Point getScreenSize(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // 1 - settingsActivity
        switch (requestCode) {
            case 1:
                // если пользователь ихменил настройки, то создаётся новое поле
                if (resultCode == RESULT_OK ) {
                    frameLayout.removeAllViews();
                    playingField = (Settings.fieldMode == Settings.FieldMode.HEXAGONAL) ?
                            new PlayingField(this) : new SquarePlayingField(this);
                    frameLayout.addView(playingField);
                    playingField.startNewGame();

                    touchButton.setColorFilter(Color.WHITE);
                    flagButton.setColorFilter(Color.BLACK);
                    questionButton.setColorFilter(Color.BLACK);
                }
                break;
        }
    }

    // для активной кнопки устанавливается белый цвет, для двух остальных - чёрный
    private void setActiveButtonColor(ImageButton activeButton, ImageButton nonActiveButton1, ImageButton nonActiveButton2) {
        activeButton.setColorFilter(Color.WHITE);
        nonActiveButton1.setColorFilter(Color.BLACK);
        nonActiveButton2.setColorFilter(Color.BLACK);
    }

}
