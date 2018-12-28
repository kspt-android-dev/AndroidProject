package com.logiccombine.artmate.logiccombine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;

public class InGame extends AppCompatActivity {
    @Override
    public void onBackPressed(){
        try {
            Intent intent = new Intent(InGame.this, MainActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private AdditionalFinding finder = new AdditionalFinding(this);
    private Calculations calculations = new Calculations();

    private arithmeticOperations currentOperation;
    private double result;
    private double currentNumber;
    private arithmeticOperations[] operations = {arithmeticOperations.NOTHING, arithmeticOperations.NOTHING, arithmeticOperations.NOTHING, arithmeticOperations.NOTHING,
                                                arithmeticOperations.NOTHING, arithmeticOperations.NOTHING, arithmeticOperations.NOTHING, arithmeticOperations.NOTHING};

    private void startSound(){
        startService(new Intent(this, ServiceForMusic.class));
    }
    private void  stopSound(){
        stopService(new Intent(this, ServiceForMusic.class));
    }

    private void updateArithmeticButtons(){
        findViewById(R.id.plusButton).setBackgroundResource(R.drawable.explus);
        findViewById(R.id.minusButton).setBackgroundResource(R.drawable.exminus);
        findViewById(R.id.multiplyButton).setBackgroundResource(R.drawable.exmultiply);
        findViewById(R.id.shareButton).setBackgroundResource(R.drawable.exshare);
        findViewById(R.id.powerButton).setBackgroundResource(R.drawable.expower);
        findViewById(R.id.connectButton).setBackgroundResource(R.drawable.excombine);
        if (currentOperation != arithmeticOperations.NOTHING) {
            findViewById(finder.findArithmeticButtonIdByOperation(currentOperation)).setBackgroundResource(getResources().getIdentifier(finder.findImgByArithmeticOperation(currentOperation) + "pressed", "drawable", getPackageName()));
        }
    }

    private void updateSignButtons(int position){
        operations[position] = currentOperation;
        findViewById(finder.findSignIdByPosition(position)).setBackgroundResource(getResources().getIdentifier(finder.findImgByArithmeticOperation(currentOperation), "drawable", getPackageName()));
        result = calculations.countResult(operations);
        ((TextView)findViewById(R.id.currentResult)).setText(String.valueOf(result));
        winCheck();
    }

    public static final String APP_PREFERENCES = "mastering";
    public static final String APP_PREFERENCES_SOUND = "sound";
    public static final String APP_PREFERENCES_SCORE = "score";
    private SharedPreferences mySettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mySettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.in_game);
        final SharedPreferences.Editor editor = mySettings.edit();
        //region Back Pressed
        final ImageButton settings = (ImageButton)findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    onBackPressed();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //endregion

        //region Sound upgrade

        final ImageButton soundButton = (ImageButton)findViewById(R.id.titleLeft);
        soundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mySettings.getInt(APP_PREFERENCES_SOUND, 0) == 1) {
                    soundButton.setBackgroundResource(R.drawable.soundonsecond);
                    editor.putInt(APP_PREFERENCES_SOUND, 0);
                    editor.apply();
                    startSound();
                }
                else {
                    soundButton.setBackgroundResource(R.drawable.soundoffsecond);
                    editor.putInt(APP_PREFERENCES_SOUND, 1);
                    editor.apply();
                    stopSound();
                }
            }
        });

        if (mySettings.getInt(APP_PREFERENCES_SOUND, 0) == 1) {
            soundButton.setBackgroundResource(R.drawable.soundoffsecond);
        }
        else {
            soundButton.setBackgroundResource(R.drawable.soundonsecond);
        }
        //endregion

        TextView scoreNumber = (TextView)findViewById(R.id.scoreInfo);
        scoreNumber.setText(String.valueOf(readScore()));

        //region Initialise arithmetic buttons
        ImageButton plusButton = findViewById(R.id.plusButton);
        ImageButton minusButton = findViewById(R.id.minusButton);
        ImageButton multiplyButton = findViewById(R.id.multiplyButton);
        ImageButton divideButton = findViewById(R.id.shareButton);
        ImageButton powerButton = findViewById(R.id.powerButton);
        ImageButton connectButton = findViewById(R.id.connectButton);
        //set handler
        plusButton.setOnClickListener(onClickArithmeticOperations);
        minusButton.setOnClickListener(onClickArithmeticOperations);
        multiplyButton.setOnClickListener(onClickArithmeticOperations);
        divideButton.setOnClickListener(onClickArithmeticOperations);
        powerButton.setOnClickListener(onClickArithmeticOperations);
        connectButton.setOnClickListener(onClickArithmeticOperations);
        //endregion

        //region Set game number
        if (savedInstanceState == null) {
            Random randomNumber = new Random();
            currentNumber = randomNumber.nextInt(11111);
            ((TextView)findViewById(R.id.mainNumber)).setText(String.valueOf(currentNumber));
        }
        //endregion

        //region Initialise sign buttons
        ImageButton sign1Button = findViewById(R.id.forSign1);
        ImageButton sign2Button = findViewById(R.id.forSign2);
        ImageButton sign3Button = findViewById(R.id.forSign3);
        ImageButton sign4Button = findViewById(R.id.forSign4);
        ImageButton sign5Button = findViewById(R.id.forSign5);
        ImageButton sign6Button = findViewById(R.id.forSign6);
        ImageButton sign7Button = findViewById(R.id.forSign7);
        ImageButton sign8Button = findViewById(R.id.forSign8);

        sign1Button.setOnClickListener(onClickSignCell);
        sign2Button.setOnClickListener(onClickSignCell);
        sign3Button.setOnClickListener(onClickSignCell);
        sign4Button.setOnClickListener(onClickSignCell);
        sign5Button.setOnClickListener(onClickSignCell);
        sign6Button.setOnClickListener(onClickSignCell);
        sign7Button.setOnClickListener(onClickSignCell);
        sign8Button.setOnClickListener(onClickSignCell);

        final ImageButton [] allSignButtons = {sign1Button, sign2Button, sign3Button, sign4Button, sign5Button, sign6Button, sign7Button, sign8Button};
        //endregion

        //region Restore Instance State
        if (savedInstanceState != null) {
            String [] operationsInStrings;
            result = savedInstanceState.getDouble("result");
            currentNumber = savedInstanceState.getDouble("currentNumber");
            operationsInStrings = savedInstanceState.getStringArray("operationsInStrings");
            for (int p = 0; p<8; p++){
                operations[p] = arithmeticOperations.valueOf((operationsInStrings != null) ? operationsInStrings[p] : null);
            }
            ((TextView)findViewById(R.id.mainNumber)).setText(String.valueOf(currentNumber));
            ((TextView)findViewById(R.id.currentResult)).setText(String.valueOf(result));
            for (int i = 0; i<8; i++) {
                currentOperation = operations[i];
                updateSignButtons(i);
            }
            String currentOperationString = savedInstanceState.getString("currentOperation");
            if (currentOperationString!= null) {
                currentOperation = arithmeticOperations.valueOf(currentOperationString);
            }
            updateArithmeticButtons();
        }
        //endregion

    }

    public void updateScore(){
        mySettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySettings.edit();
        editor.putInt(APP_PREFERENCES_SCORE, (mySettings.getInt(APP_PREFERENCES_SCORE, 0) + 1));
        editor.apply();
    }

    public int readScore(){
        mySettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        return (mySettings.getInt(APP_PREFERENCES_SCORE, 0));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        String [] operationsInStrings = new String[8];
        for (int p = 0; p<8; p++){
            operationsInStrings[p] = String.valueOf(operations[p]);
        }
        super.onSaveInstanceState(outState);
        outState.putDouble("result", result);
        outState.putDouble("currentNumber", currentNumber);
        outState.putStringArray("operationsInStrings", operationsInStrings);
        outState.putString("currentOperation", String.valueOf(currentOperation));
    }


    private View.OnClickListener onClickArithmeticOperations = new View.OnClickListener() {
        arithmeticOperations expectedOperation;
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.plusButton:
                    expectedOperation = arithmeticOperations.PLUS;
                    break;
                case R.id.minusButton:
                    expectedOperation = arithmeticOperations.MINUS;
                    break;
                case R.id.multiplyButton:
                    expectedOperation = arithmeticOperations.MULTIPLY;
                    break;
                case R.id.shareButton:
                    expectedOperation = arithmeticOperations.DIVIDE;
                    break;
                case R.id.powerButton:
                    expectedOperation = arithmeticOperations.POWER;
                    break;
                case R.id.connectButton:
                    expectedOperation = arithmeticOperations.CONNECT;
                    break;
            }
            currentOperation = (currentOperation != expectedOperation)? expectedOperation : arithmeticOperations.NOTHING;
            updateArithmeticButtons();
        }
    };

    private View.OnClickListener onClickSignCell = new View.OnClickListener() {
        int position;
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.forSign1:
                    position = 0;
                    break;
                case R.id.forSign2:
                    position = 1;
                    break;
                case R.id.forSign3:
                    position = 2;
                    break;
                case R.id.forSign4:
                    position = 3;
                    break;
                case R.id.forSign5:
                    position = 4;
                    break;
                case R.id.forSign6:
                    position = 5;
                    break;
                case R.id.forSign7:
                    position = 6;
                    break;
                case R.id.forSign8:
                    position = 7;
                    break;
            }
            updateSignButtons(position);
        }
    };

    private void winCheck(){
        if (result == currentNumber){
            updateScore();
            result = 0;
            currentOperation = arithmeticOperations.NOTHING;
            for (int j = 0; j<8; j++){
                operations[j] = arithmeticOperations.NOTHING;
            }
            Random randomNumber = new Random();
            currentNumber = randomNumber.nextInt(11111);
            recreate();
        }
    }
}
