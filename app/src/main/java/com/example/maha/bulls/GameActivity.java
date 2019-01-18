package com.example.maha.bulls;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GameActivity extends Activity implements View.OnClickListener{
    final int COUNT_OF_NUMB = 4;
    private int cows, bulls;
    private ImageButton b1, b2, b3, b4, b5, b6, b7, b8, b9, b0, del, ok;
    private String text;
    private String text2;
    private String numerals = "";
    private String myNumb = getRandNumber();

    TextView txt;
    TextView txt2;
    TextView txt3;
    AlertDialog alert;
    int win;
    final String LOG_TAG = "myLogs";

    public String getRandNumber(){
        ArrayList<Character> numbers = new ArrayList<>();
        Collections.addAll(numbers, '1', '2', '3', '4', '5', '6', '7', '8', '9', '0');
        int index;
        char a;
        char num[] = new char[COUNT_OF_NUMB];;
        for (int i = 0; i < COUNT_OF_NUMB; i++){
            index =(int) (Math.random()*(10-i));
            a = numbers.get(index);
            numbers.remove(index);
            num[i] = a;
        }
        return String.copyValueOf(num);
    }
    public int countC(String num, String myNumb){
        cows = 0;
        for (int i = 0; i < COUNT_OF_NUMB; i++){
            if (myNumb.contains(num.substring(i, i + 1))
                    && !myNumb.substring(i, i + 1).equals(num.substring(i, i + 1))) cows++;
        }
        return cows;
    }
    public int countB(String num, String myNumb){
        bulls = 0;
        for (int i = 0; i < COUNT_OF_NUMB; i++){
            if (myNumb.substring(i, i + 1).equals(num.substring(i, i + 1))) bulls++;
        }
        return bulls;
    }
    public String countOfCB(String num, String myNumb){
        cows = 0;
        bulls = 0;
        for (int i = 0; i < COUNT_OF_NUMB; i++){
            if (myNumb.contains(num.substring(i, i + 1))) cows++;
        }
        for (int i = 0; i < COUNT_OF_NUMB; i++){
            if (myNumb.substring(i, i + 1).equals(num.substring(i, i + 1))) {
                bulls++;
                cows--;
            }
        }
        return getResources().getString(R.string.bulls)
                + Integer.toString(bulls) + " "
                + getResources().getString(R.string.cows) + Integer.toString(cows);
    }
    public void plusButtonValue(int countOfDig, String buttonValue){
        if (text.length() < countOfDig && (!text.contains(buttonValue))){
           text = text + buttonValue;
        }
   }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        txt = findViewById(R.id.textView);
        txt2 = findViewById(R.id.textView2);
        txt3 = findViewById(R.id.textView3);
        txt.setText(text);
        txt2.setText(numerals);
        text = "";
        b1 = (ImageButton) findViewById(R.id.button1);
        b1.setOnClickListener(this);
        b2 = (ImageButton) findViewById(R.id.button2);
        b2.setOnClickListener(this);
        b3 = (ImageButton) findViewById(R.id.button3);
        b3.setOnClickListener(this);
        b4 = (ImageButton) findViewById(R.id.button4);
        b4.setOnClickListener(this);
        b5 = (ImageButton) findViewById(R.id.button5);
        b5.setOnClickListener(this);
        b6 = (ImageButton) findViewById(R.id.button6);
        b6.setOnClickListener(this);
        b7 = (ImageButton) findViewById(R.id.button7);
        b7.setOnClickListener(this);
        b8 = (ImageButton) findViewById(R.id.button8);
        b8.setOnClickListener(this);
        b9 = (ImageButton) findViewById(R.id.button9);
        b9.setOnClickListener(this);
        b0 = (ImageButton) findViewById(R.id.button0);
        b0.setOnClickListener(this);
        del = (ImageButton) findViewById(R.id.del);
        del.setOnClickListener(this);
        ok = (ImageButton) findViewById(R.id.ok);
        ok.setOnClickListener(this);
        win = 0;

        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
        builder.setTitle(getResources().getString(R.string.dialog_title))
                .setMessage(getResources().getString(R.string.winner_text))
                .setCancelable(false)
                .setNegativeButton(getResources().getString(R.string.dialog_agreement),
                        new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                closeActivity();
            }
        });
        alert = builder.create();
        Log.d(LOG_TAG, "onCreate");

    }
   @Override
    protected void onSaveInstanceState(Bundle outState){
        outState.putString("myNumb", myNumb);
        outState.putString("text", text);
        outState.putString("numerals", numerals);
        outState.putInt("win", win);
        super.onSaveInstanceState(outState);
        Log.d(LOG_TAG, "onSaveInstanceState");
    }
    @Override
    protected void onRestoreInstanceState(Bundle outState){
        super.onRestoreInstanceState(outState);
        myNumb = outState.getString("myNumb");
        text = outState.getString("text");
        numerals = outState.getString("numerals");
        win = outState.getInt("win");
        txt.setText(text);
        txt2.setText(numerals);
        if (win == 1) alert.show();
        Log.d(LOG_TAG, "onRestoreInstanceState");
    }
    // закрытие GameActivity
    private void closeActivity(){
        this.finish();
    }
    @Override
    public void onClick(View v) {
        txt2.setMovementMethod(new ScrollingMovementMethod());
        switch (v.getId()) {
            case R.id.button1:
                plusButtonValue(COUNT_OF_NUMB, getResources().getString(R.string.buttonVal1));
                break;
            case R.id.button2:
                plusButtonValue(COUNT_OF_NUMB, getResources().getString(R.string.buttonVal2));
                break;
            case R.id.button3:
                plusButtonValue(COUNT_OF_NUMB, getResources().getString(R.string.buttonVal3));
                break;
            case R.id.button4:
                plusButtonValue(COUNT_OF_NUMB, getResources().getString(R.string.buttonVal4));
                break;
            case R.id.button5:
                plusButtonValue(COUNT_OF_NUMB, getResources().getString(R.string.buttonVal5));
                break;
            case R.id.button6:
                plusButtonValue(COUNT_OF_NUMB, getResources().getString(R.string.buttonVal6));
                break;
            case R.id.button7:
                plusButtonValue(COUNT_OF_NUMB, getResources().getString(R.string.buttonVal7));
                break;
            case R.id.button8:
                plusButtonValue(COUNT_OF_NUMB, getResources().getString(R.string.buttonVal8));
                break;
            case R.id.button9:
                plusButtonValue(COUNT_OF_NUMB, getResources().getString(R.string.buttonVal9));
                break;
            case R.id.button0:
                plusButtonValue(COUNT_OF_NUMB, getResources().getString(R.string.buttonVal0));
                break;
            case R.id.del:
                if (text.length() > 0){
                text = text.substring(0, text.length() - 1); }
                Log.d(LOG_TAG, "delOneDig");
                break;
            case R.id.ok:
                if (text.length() == COUNT_OF_NUMB) {
                numerals = numerals + text + " " + countOfCB(text, myNumb)+ "\n";
                    if (bulls == COUNT_OF_NUMB){
                        numerals = numerals + "\n" + getResources().getString(R.string.winner_text);
                        win = 1;
                        alert.show();
                       // MainActivity.createSound();
                    }
                Log.d(LOG_TAG, "numberEntered");
                text = "";
                }
                break;
            default:
                break;
        }
        txt.setText(text);
        txt2.setText(numerals);
    }
}
