package com.logiccombine.artmate.logiccombine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
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

    private int checker;

    private double result;

    private double currentNumber;

    private int[] operations = new int[8];

    private void startSound(){
        startService(new Intent(this, ServiceForMusic.class));
    }
    private void  stopSound(){
        stopService(new Intent(this, ServiceForMusic.class));
    }

    private void updateButtons(ImageButton [] allButtons){
        allButtons[0].setBackgroundResource(R.drawable.explus);
        allButtons[1].setBackgroundResource(R.drawable.exminus);
        allButtons[2].setBackgroundResource(R.drawable.exmultiply);
        allButtons[3].setBackgroundResource(R.drawable.exshare);
        allButtons[4].setBackgroundResource(R.drawable.expower);
        allButtons[5].setBackgroundResource(R.drawable.excombine);
        switch (checker){
            case 1:
                allButtons[0].setBackgroundResource(R.drawable.expluspressed);
                break;
            case 2:
                allButtons[1].setBackgroundResource(R.drawable.exminuspressed);
                break;
            case 3:
                allButtons[2].setBackgroundResource(R.drawable.exmultiplypressed);
                break;
            case 4:
                allButtons[3].setBackgroundResource(R.drawable.exsharepressed);
                break;
            case 5:
                allButtons[4].setBackgroundResource(R.drawable.expowerpressed);
                break;
            case 6:
                allButtons[5].setBackgroundResource(R.drawable.excombinepressed);
                break;
            default:
                break;
        }
    }

    private void updateSignButtons(ImageButton signButton, int position){
        switch (checker){
            case 0:
                signButton.setBackgroundResource(R.drawable.exemptycell);
                operations[position - 1] = 0;
                break;
            case 1:
                signButton.setBackgroundResource(R.drawable.explus);
                operations[position - 1] = 1;
                break;
            case 2:
                signButton.setBackgroundResource(R.drawable.exminus);
                operations[position - 1] = 2;
                break;
            case 3:
                signButton.setBackgroundResource(R.drawable.exmultiply);
                operations[position - 1] = 3;
                break;
            case 4:
                signButton.setBackgroundResource(R.drawable.exshare);
                operations[position - 1] = 4;
                break;
            case 5:
                signButton.setBackgroundResource(R.drawable.expower);
                operations[position - 1] = 5;
                break;
            case 6:
                signButton.setBackgroundResource(R.drawable.excombine);
                operations[position - 1] = 6;
                break;
            default:
                break;
        }
        result = countResult(operations);
        TextView currentResultText = (TextView)findViewById(R.id.currentResult);
        currentResultText.setText(String.valueOf(result));
        //region Equal Result
        if (result == currentNumber){
            updateScore();
            result = 0;
            checker = 0;
            for (int j = 0; j<8; j++){
                operations[j] =0;
            }
            Random randomNumber = new Random();
            currentNumber = randomNumber.nextInt(11111);
            recreate();
        }
        //endregion
    }

    public double countResult(int [] operations){
        double countResult = 1;
        double[] neighbours = new double[8];
        double leftN;
        double rightN;
        for (int i = 7; i>=0; i--) {
            if (operations[i] == 6) {
                if (i != 0 && neighbours[i - 1] != 0) {
                    leftN = neighbours[i - 1];
                } else {
                    leftN = i + 1;
                }
                if (i != 7 && neighbours[i + 1] != 0) {
                    rightN = neighbours[i + 1];
                } else {
                    rightN = i + 2;
                }
                countResult = rightN;
                while (countResult!=0){
                    leftN = leftN * 10;
                    countResult = Math.floor(countResult/10);
                }
                countResult = leftN + rightN;
                for (int k = 0; k<8; k++){
                    if (neighbours[k] == rightN || neighbours[k]== leftN){
                        neighbours[k] = countResult;
                    }
                }
                neighbours[i] = countResult;
            }
        }
        for (int i = 7; i>=0; i--) {
            if (operations[i] == 5) {
                if (i != 0 && neighbours[i - 1] != 0) {
                    leftN = neighbours[i - 1];
                } else {
                    leftN = i + 1;
                }
                if (i != 7 && neighbours[i + 1] != 0) {
                    rightN = neighbours[i + 1];
                } else {
                    rightN = i + 2;
                }
                countResult = Math.pow(leftN, rightN);
                for (int k = 0; k<8; k++){
                    if (neighbours[k] == rightN || neighbours[k]== leftN){
                        neighbours[k] = countResult;
                    }
                }
                neighbours[i] = countResult;
            }
        }
        for (int i = 7; i>=0; i--) {
            if (operations[i] == 4) {
                if (i != 0 && neighbours[i - 1] != 0) {
                    leftN = neighbours[i - 1];
                } else {
                    leftN = i + 1;
                }
                if (i != 7 && neighbours[i + 1] != 0) {
                    rightN = neighbours[i + 1];
                } else {
                    rightN = i + 2;
                }
                countResult = leftN / rightN;
                for (int k = 0; k<8; k++){
                    if (neighbours[k] == rightN || neighbours[k]== leftN){
                        neighbours[k] = countResult;
                    }
                }
                neighbours[i] = countResult;
            }
        }
        for (int i = 7; i>=0; i--) {
            if (operations[i] == 3) {
                if (i != 0 && neighbours[i - 1] != 0) {
                    leftN = neighbours[i - 1];
                } else {
                    leftN = i + 1;
                }
                if (i != 7 && neighbours[i + 1] != 0) {
                    rightN = neighbours[i + 1];
                } else {
                    rightN = i + 2;
                }
                countResult = leftN * rightN;
                for (int k = 0; k<8; k++){
                    if (neighbours[k] == rightN || neighbours[k]== leftN){
                        neighbours[k] = countResult;
                    }
                }
                neighbours[i] = countResult;
            }
        }
        for (int i = 0; i<8; i++) {
            if (operations[i] == 2) {
                if (i != 0 && neighbours[i - 1] != 0) {
                    leftN = neighbours[i - 1];
                } else {
                    leftN = i + 1;
                }
                if (i != 7 && neighbours[i + 1] != 0) {
                    rightN = neighbours[i + 1];
                } else {
                    rightN = i + 2;
                }
                if (rightN < 0){
                    rightN = Math.abs(rightN);
                }
                countResult = leftN - rightN;
                for (int k = 0; k<8; k++){
                    if (neighbours[k] == rightN || neighbours[k]== leftN){
                        neighbours[k] = countResult;
                    }
                }
                neighbours[i] = countResult;
            }
        }
        for (int i = 7; i>=0; i--) {
            if (operations[i]==1){
                if (i!= 0 && neighbours[i-1]!= 0){
                    leftN = neighbours[i-1];
                }
                else {
                    leftN = i+1;
                }
                if (i!= 7 && neighbours[i+1]!= 0){
                    rightN = neighbours[i+1];
                }
                else {
                    rightN = i+2;
                }
                countResult = leftN + rightN;
                for (int k = 0; k<8; k++){
                    if (neighbours[k] == rightN || neighbours[k]== leftN){
                        neighbours[k] = countResult;
                    }
                }
                neighbours[i] = countResult;
            }
        }
        return countResult;
    }

    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_SOUND = "sound";
    public static final String APP_PREFERENCES_SCORE = "score";
    private SharedPreferences mySettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mySettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.in_game);
        final SharedPreferences.Editor editor = mySettings.edit();

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
        final ImageButton plusButton = (ImageButton)findViewById(R.id.plusButton);
        final ImageButton minusButton = (ImageButton)findViewById(R.id.minusButton);
        final ImageButton multiplyButton = (ImageButton)findViewById(R.id.multiplyButton);
        final ImageButton shareButton = (ImageButton)findViewById(R.id.shareButton);
        final ImageButton powerButton = (ImageButton)findViewById(R.id.powerButton);
        final ImageButton connectButton = (ImageButton)findViewById(R.id.connectButton);
        final ImageButton [] allButtons = {plusButton, minusButton, multiplyButton, shareButton, powerButton, connectButton};
        //endregion

        //region Set game number

        final TextView mainNumber = (TextView) findViewById(R.id.mainNumber);
        if (savedInstanceState == null) {
            Random randomNumber = new Random();
            currentNumber = randomNumber.nextInt(11111);
            mainNumber.setText(String.valueOf(currentNumber));
        }
        //endregion

        //region Listeners arithmetic buttons
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (checker != 1) {
                        checker = 1;
                    }
                    else {
                        checker = 0;
                    }
                    updateButtons(allButtons);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (checker != 2) {
                        checker = 2;
                    }
                    else {
                        checker = 0;
                    }
                    updateButtons(allButtons);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        multiplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (checker != 3) {
                        checker = 3;
                    }
                    else {
                        checker = 0;
                    }
                    updateButtons(allButtons);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (checker != 4) {
                        checker = 4;
                    }
                    else {
                        checker = 0;
                    }
                    updateButtons(allButtons);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        powerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (checker != 5) {
                        checker = 5;
                    }
                    else {
                        checker = 0;
                    }
                    updateButtons(allButtons);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (checker != 6) {
                        checker = 6;
                    }
                    else {
                        checker = 0;
                    }
                    updateButtons(allButtons);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //endregion

        //region Initialise sign buttons
        final ImageButton sign1Button = (ImageButton)findViewById(R.id.forSign1);
        final ImageButton sign2Button = (ImageButton)findViewById(R.id.forSign2);
        final ImageButton sign3Button = (ImageButton)findViewById(R.id.forSign3);
        final ImageButton sign4Button = (ImageButton)findViewById(R.id.forSign4);
        final ImageButton sign5Button = (ImageButton)findViewById(R.id.forSign5);
        final ImageButton sign6Button = (ImageButton)findViewById(R.id.forSign6);
        final ImageButton sign7Button = (ImageButton)findViewById(R.id.forSign7);
        final ImageButton sign8Button = (ImageButton)findViewById(R.id.forSign8);

        final ImageButton [] allSignButtons = {sign1Button, sign2Button, sign3Button, sign4Button, sign5Button, sign6Button, sign7Button, sign8Button};
        //endregion

        //region Listeners sign buttons
        sign1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    updateSignButtons(sign1Button, 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        sign2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    updateSignButtons(sign2Button, 2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        sign3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    updateSignButtons(sign3Button,3);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        sign4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    updateSignButtons(sign4Button,4);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        sign5Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    updateSignButtons(sign5Button,5);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        sign6Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    updateSignButtons(sign6Button,6);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        sign7Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    updateSignButtons(sign7Button,7);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        sign8Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    updateSignButtons(sign8Button,8);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //endregion

        //region Restore Instance State
        if (savedInstanceState != null) {
            result = savedInstanceState.getDouble("result");
            currentNumber = savedInstanceState.getDouble("currentNumber");
            operations = savedInstanceState.getIntArray("operations");
            mainNumber.setText(String.valueOf(currentNumber));
            TextView currentResultText = (TextView)findViewById(R.id.currentResult);
            currentResultText.setText(String.valueOf(result));
            for (int i = 0; i<8; i++) {
                checker = operations[i];
                updateSignButtons(allSignButtons[i], i+1);
            }
            checker = savedInstanceState.getInt("checker");
            updateButtons(allButtons);
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
        super.onSaveInstanceState(outState);
        outState.putDouble("result", result);
        outState.putDouble("currentNumber", currentNumber);
        outState.putIntArray("operations", operations);
        outState.putInt("checker", checker);
    }


    /*@Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        result = savedInstanceState.getDouble("result");
        currentNumber = savedInstanceState.getDouble("currentNumber");
        operations = savedInstanceState.getIntArray("operations");
        checker = savedInstanceState.getInt("checker");
    }*/
}
