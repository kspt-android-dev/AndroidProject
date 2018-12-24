package com.logiccombine.artmate.logiccombine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_SOUND = "sound";
    private SharedPreferences mySettings;

    private void startSound(){
        startService(new Intent(this, ServiceForMusic.class));
    }
    private void  stopSound(){
        stopService(new Intent(this, ServiceForMusic.class));
    }

    @Override
    public void onBackPressed(){
        try {
            stopSound();
            finish();
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mySettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageButton playButton = (ImageButton)findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playButton.setBackgroundResource(R.drawable.pressedplay);
                try {
                    Intent intent = new Intent(MainActivity.this, InGame.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        final ImageButton helpButton = (ImageButton)findViewById(R.id.helpButton);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpButton.setBackgroundResource(R.drawable.pressedhelp);
                try {
                    Intent intent = new Intent(MainActivity.this, InHelp.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        final ImageButton statsButton = (ImageButton)findViewById(R.id.statsButton);
        statsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statsButton.setBackgroundResource(R.drawable.pressedstats);
                try {
                    Intent intent = new Intent(MainActivity.this, InStats.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        final ImageButton quitButton = (ImageButton)findViewById(R.id.quitButton);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quitButton.setBackgroundResource(R.drawable.pressedquit);
                try {
                    stopSound();
                    finish();
                    System.exit(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        final SharedPreferences.Editor editor = mySettings.edit();

        final ImageButton soundButton = (ImageButton)findViewById(R.id.soundButton);
        soundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mySettings.getInt(APP_PREFERENCES_SOUND, 0) == 1) {
                    soundButton.setBackgroundResource(R.drawable.soundon);
                    editor.putInt(APP_PREFERENCES_SOUND, 0);
                    editor.apply();
                    startSound();
                }
                else {
                    soundButton.setBackgroundResource(R.drawable.soundoff);
                    editor.putInt(APP_PREFERENCES_SOUND, 1);
                    editor.apply();
                    stopSound();
                }
            }
        });

        if (mySettings.getInt(APP_PREFERENCES_SOUND, 0) == 1) {
            soundButton.setBackgroundResource(R.drawable.soundoff);
            stopSound();
        }
        else {
            soundButton.setBackgroundResource(R.drawable.soundon);
            startSound();
        }
    }
}
