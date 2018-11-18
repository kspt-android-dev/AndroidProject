package com.shminesweeper.shminesweeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    View.OnClickListener buttonsonClickListner;

    SeekBar customWidthSB;
    SeekBar customHeightSB;
    SeekBar customMinesSB;

    TextView widthSeekBarIndex;
    TextView heightSeekBarIndex;
    TextView minesSeekBarIndex;
    TextView watchInstruction;

    RadioButton hexagonalRadioButton;
    RadioButton squareRadioButton;

    SeekBar.OnSeekBarChangeListener seekBarChangeListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setLayoutComponents();

        setButtonsonClickListner();
        addButtonsClickListenerToButtons();

        setProgressForSeekBars();

        setTextForSekkBars();

        setActiveRadioButton();

        setSeekBarChangeListener();
        addSeekBarChangeLictenerToSeekBars();

        addListenerToWatchInstructionText();
    }

    // определение компонентов layout'a из ресурсов
    private  void setLayoutComponents() {
        widthSeekBarIndex = findViewById(R.id.width_seek_bar_index);
        heightSeekBarIndex = findViewById(R.id.height_seek_bar_index);
        minesSeekBarIndex = findViewById(R.id.mines_seek_bar_index);

        watchInstruction = findViewById(R.id.watch_instruction_text_view);

        customWidthSB = findViewById(R.id.custom_width_seek_bar);
        customHeightSB = findViewById(R.id.custom_height_seek_bar);
        customMinesSB = findViewById(R.id.custom_mines_seek_bar);

        hexagonalRadioButton = findViewById(R.id.hex_radio_button);
        squareRadioButton = findViewById(R.id.square_radio_button);
    }

    // ппределение listener'a для кнопок save и cancel
    private void setButtonsonClickListner(){
        buttonsonClickListner = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setResult(RESULT_CANCELED);

                switch (view.getId()){
                    case R.id.cancel_button:
                        break;
                    case R.id.save_button:
                        int newWidth = (customWidthSB.getProgress() < 2) ? 2 : customWidthSB.getProgress();
                        int newHeight = (customHeightSB.getProgress() < 2) ? 2 : customHeightSB.getProgress();
                        int newNumberOfMines =
                                (customMinesSB.getProgress() >= newWidth * newHeight) ? newHeight * newWidth - 1 :
                                        customMinesSB.getProgress() == 0 ? 2 : customMinesSB.getProgress();
                        Settings.FieldMode newFieldMode = (hexagonalRadioButton.isChecked()) ? Settings.FieldMode.HEXAGONAL : Settings.FieldMode.SQUARE;

                        if (newFieldMode != Settings.fieldMode || newHeight != Settings.cellsInHeight ||
                                newWidth != Settings.cellsInWidth || newNumberOfMines != Settings.numberOfMines){
                            Settings.cellsInWidth = newWidth;
                            Settings.cellsInHeight = newHeight;
                            Settings.numberOfMines = newNumberOfMines;
                            Settings.fieldMode = newFieldMode;
                            setResult(RESULT_OK);
                        }

                        break;
                }

                finish();
            }
        };
    }

    // добавление listener'a к кнопкам save и cancel
    private void addButtonsClickListenerToButtons(){
        findViewById(R.id.save_button).setOnClickListener(buttonsonClickListner);
        findViewById(R.id.cancel_button).setOnClickListener(buttonsonClickListner);
    }

    // установление прогресса seekBar'ам в соответствии с текущими настройками
    private void setProgressForSeekBars() {
        customHeightSB.setProgress(Settings.cellsInHeight);
        customWidthSB.setProgress(Settings.cellsInWidth);
        customMinesSB.setProgress(Settings.numberOfMines);
    }

    // установление прогресса в числовом эквиваленте в соответствии с текущими настройками
    private void setTextForSekkBars() {
        widthSeekBarIndex.setText("" + Settings.cellsInWidth);
        heightSeekBarIndex.setText("" + Settings.cellsInHeight);
        minesSeekBarIndex.setText("" + Settings.numberOfMines);
    }

    // установление активного radioButton'a в соответствии с настройками
    private void setActiveRadioButton() {
        if (Settings.fieldMode.equals(Settings.FieldMode.HEXAGONAL)) hexagonalRadioButton.setChecked(true);
        else squareRadioButton.setChecked(true);
    }

    private void setSeekBarChangeListener(){
        seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (seekBar.equals(customWidthSB)) widthSeekBarIndex.setText("" + customWidthSB.getProgress());
                if (seekBar.equals(customHeightSB)) heightSeekBarIndex.setText("" + customHeightSB.getProgress());
                if (seekBar.equals(customMinesSB)) minesSeekBarIndex.setText("" + customMinesSB.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
    }

    // добавление seekBar'ам listener'a
    private void addSeekBarChangeLictenerToSeekBars() {
        customWidthSB.setOnSeekBarChangeListener(seekBarChangeListener);
        customHeightSB.setOnSeekBarChangeListener(seekBarChangeListener);
        customMinesSB.setOnSeekBarChangeListener(seekBarChangeListener);
    }

    private void addListenerToWatchInstructionText() {
        watchInstruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, InstructionActivity.class);
                startActivity(intent);
            }
        });
    }
}
