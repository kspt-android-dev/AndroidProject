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
    ImageButton b1, b2, b3, b4, b5, b6, b7, b8, b9, b0, del, ok;
    String text;
    String text2;
    String numerals = "";
    String myNumb = getRandNumber();
    TextView txt;
    TextView txt2;
    TextView txt3;
    AlertDialog alert;
    int win;
    final String LOG_TAG = "myLogs";

    public String getRandNumber(){
        ArrayList<Character> numbers = new ArrayList<>();
        Collections.addAll(numbers, '1', '2', '3', '4', '5', '6', '7', '8', '9', '0');
        int index = (int)(Math.random()*10);
        Character a1 =(Character) numbers.get(index);
        numbers.remove(index);

        index = (int)(Math.random()*9);
        Character a2 = numbers.get(index);
        numbers.remove(index);

        index = (int)(Math.random()*8);
        Character a3 = numbers.get(index);
        numbers.remove(index);

        index = (int)(Math.random()*7);
        Character a4 = numbers.get(index);
        numbers.remove(index);

        String s = a1.toString() + a2.toString() + a3.toString() + a4.toString();
        return s;
    }
    public String countOfCB(String num, String myNumb){
        int cows = 0, bulls = 0;
        for (int i = 0; i < 4; i++){
            if (myNumb.contains(num.substring(i, i+1))) cows++;
        }
        for (int i = 0; i < 4; i++){
            if (myNumb.substring(i, i+1).equals(num.substring(i, i+1))) {
                bulls++;
                cows--;
            }
        }
        return "bulls: " + Integer.toString(bulls) + " " + "cows: " + Integer.toString(cows);
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
        builder.setTitle("Attention!").setMessage("YOU WIN").setCancelable(false).setNegativeButton("Ok, thanks", new DialogInterface.OnClickListener() {
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
    private void closeActivity(){
        this.finish();
    }
    @Override
    public void onClick(View v) {
        txt2.setMovementMethod(new ScrollingMovementMethod());
       // txt3.setText(myNumb);
        switch (v.getId()) {
            case R.id.button1:
                plusButtonValue(4, "1");
                break;
            case R.id.button2:
                plusButtonValue(4, "2");
                break;
            case R.id.button3:
                plusButtonValue(4, "3");
                break;
            case R.id.button4:
                plusButtonValue(4, "4");
                break;
            case R.id.button5:
                plusButtonValue(4, "5");
                break;
            case R.id.button6:
                plusButtonValue(4, "6");
                break;
            case R.id.button7:
                plusButtonValue(4, "7");
                break;
            case R.id.button8:
                plusButtonValue(4, "8");
                break;
            case R.id.button9:
                plusButtonValue(4, "9");
                break;
            case R.id.button0:
                plusButtonValue(4, "0");
                break;
            case R.id.del:
                if (text.length() > 0){
                text = text.substring(0, text.length()-1); }
                break;
            case R.id.ok:
                if (text.length() == 4) {
                numerals = numerals + text + " " + countOfCB(text, myNumb)+ "\n";
                //txt2.setText(numerals);
                String str1 = countOfCB(text, myNumb);
                String str2 = "bulls: 4 cows: 0";
                    if (str1.equals(str2)) {
                        numerals = numerals + "\n" + "YOU ARE WIN";
                        win = 1;
                        alert.show();
                        txt3.setText(myNumb);
                    }
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
