package com.shminesweeper.shminesweeper;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    public static final int SETTINGS_ACTIVITY_REQUEST_CODE = 1;
    PlayingField playingField;
    FrameLayout frameLayout;

    ImageButton flagButton;
    ImageButton questionButton;
    ImageButton touchButton;

    View.OnClickListener buttonsOnClickListner;

    Intent startServiceIntent ;

    FieldLogic fieldLogic;

    SharedPreferences preferences;

    SharedViewModel viewModel;

    enum ShownDialog {GREETING, DEFEAT, NONE}
    private ShownDialog shownDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // останавливаем сервис уведомлений
        startServiceIntent = new Intent(this,NotificationService.class);
        stopService(startServiceIntent);

        setLayoutComponents();

        setSharedPreferences();

        viewModel = ViewModelProviders.of(this).get(SharedViewModel.class);
        setCellSizeInViewModel();

        setFieldLogic();

        addPlayingFieldToLayout();

        setButtonsOnClickListener();

        addOnClickListenerToButtons();

        restoreCheckedButton();
        restoreDialog();
    }

    private void setCellSizeInViewModel() {
        viewModel.setCellHeight(
                (viewModel.getFieldMode().equals(FieldLogic.FieldMode.HEXAGONAL) ?
                        (int) getResources().getDimension(R.dimen.hexagonal_cell_height) :
                        (int) getResources().getDimension(R.dimen.square_cell_size)
        ));

        viewModel.setCellWidth(
                (viewModel.getFieldMode().equals(FieldLogic.FieldMode.HEXAGONAL) ?
                        (int) getResources().getDimension(R.dimen.hexagonal_cell_width) :
                        (int) getResources().getDimension(R.dimen.square_cell_size)
        ));
    }

    private void setFieldLogic() {
        fieldLogic = new FieldLogic(this, preferences);
        fieldLogic.update(viewModel);
    }


    private void setSharedPreferences() {
        Context context = getApplicationContext();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // запускаем сервис уведомлений
        startService(startServiceIntent);

        updateViewModel();

        Log.i("MainActivity.onDestroy", "Activity destroyed");
    }

    private void updateViewModel() {
        viewModel.setGameCondition(fieldLogic.getGameCondition());
        viewModel.setTotalOpenedCells(fieldLogic.getTotalOpenCells());
        viewModel.setFieldMode(fieldLogic.getFieldMode());
        viewModel.setCellsWithMine(fieldLogic.getCellsWithMine());
        viewModel.setCellsWithFlag(fieldLogic.getCellsWithFlag());
        viewModel.setCellsWithQuestion(fieldLogic.getCellsWithQuestion());
        viewModel.setOpenCells(fieldLogic.getOpenCells());
        viewModel.setCheckedButton(playingField.getCheckedButton());
        viewModel.setShownDialog(shownDialog);
    }

    // определение всех компонентов layout'a из ресурсов
    private void setLayoutComponents(){
        frameLayout = findViewById(R.id.frameLayout);
        flagButton = findViewById(R.id.flag_button);
        questionButton = findViewById(R.id.question_button);
        touchButton = findViewById(R.id.touch_button);
    }

    // добавление игрового поля на frameLayout
    private void addPlayingFieldToLayout(){
        playingField = new PlayingField(this);
        playingField.setFieldLogic(fieldLogic);
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
                        Intent openSettings = new Intent(MainActivity.this, PrefSettings.class);
                        startActivityForResult(openSettings, SETTINGS_ACTIVITY_REQUEST_CODE);
                        break;
                    case R.id.reload_button:
                        fieldLogic.startNewGame();
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

    private void restoreDialog() {
            shownDialog = viewModel.getShownDialog();
            if (shownDialog.equals(ShownDialog.DEFEAT)) showDefeatDialog();
            else if (shownDialog.equals(ShownDialog.GREETING)) showGreetingDialog();
    }

    public void showDefeatDialog() {
        shownDialog = ShownDialog.DEFEAT;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.you_lose)
                .setPositiveButton(R.string.new_game, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        shownDialog = ShownDialog.NONE;
                        fieldLogic.startNewGame();
                        playingField.startNewGame();
                    }
                })
                .setNegativeButton(R.string.overview, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        shownDialog = ShownDialog.NONE;
                        fieldLogic.setGameConditionOverview();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showGreetingDialog() {
        shownDialog = ShownDialog.GREETING;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.you_won)
                .setPositiveButton(R.string.new_game, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        shownDialog = ShownDialog.NONE;
                        fieldLogic.startNewGame();
                        playingField.startNewGame();
                    }
                })
                .setNegativeButton(R.string.overview, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                      shownDialog = ShownDialog.NONE;
                      fieldLogic.setGameConditionOverview();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    // восстановление активной кнопки (flag | touch | question) после поворота экрана
    private void restoreCheckedButton() {

        if (viewModel.getCheckedButton().equals(PlayingField.CheckedButton.TOUCH)) {
            playingField.setCheckedButton(PlayingField.CheckedButton.TOUCH);
            setActiveButtonColor(touchButton, flagButton, questionButton);
        }
        else if (viewModel.getCheckedButton().equals(PlayingField.CheckedButton.FLAG)) {
            playingField.setCheckedButton(PlayingField.CheckedButton.FLAG);
            setActiveButtonColor(flagButton, touchButton, questionButton);

        } else if (viewModel.getCheckedButton().equals(PlayingField.CheckedButton.QUESTION)) {
            playingField.setCheckedButton(PlayingField.CheckedButton.QUESTION);
            setActiveButtonColor(questionButton, flagButton, touchButton);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        switch (requestCode) {
            case SETTINGS_ACTIVITY_REQUEST_CODE:
                // если пользователь ихменил настройки, то создаётся новое поле
                if (resultCode == RESULT_OK ) {
                    Log.i("Setting result code", "OK");

                    frameLayout.removeAllViews();
                    playingField = new PlayingField(this);
                    playingField.setFieldLogic(fieldLogic);
                    frameLayout.addView(playingField);
                    fieldLogic.startNewGame();
                    playingField.startNewGame();

                    setActiveButtonColor(touchButton, flagButton, questionButton);
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
