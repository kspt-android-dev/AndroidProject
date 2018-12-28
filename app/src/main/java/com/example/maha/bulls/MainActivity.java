package com.example.maha.bulls;

import android.content.Intent;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton button_start;
    private ImageButton button_settings;
    private ImageButton button_score;
    private SoundPool soundPool;
    private int sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_start = (ImageButton) findViewById(R.id.imageStart);
        button_start.setOnClickListener(this);

        button_settings = (ImageButton) findViewById(R.id.imageSettings);
        button_settings.setOnClickListener(this);

        button_score = (ImageButton) findViewById(R.id.imageScore);
        button_score.setOnClickListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder()
                .setMaxStreams(1)
                .setAudioAttributes(audioAttributes)
                .build();
        } else soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);

        sound = soundPool.load(this, R.raw.cowstart, 1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageStart:
                Intent intent1 = new Intent(this, GameActivity.class);
                soundPool.play(sound, 0,1,1, 0, 1);
                startActivity(intent1);
                break;
            case R.id.imageSettings:
                Intent intent2 = new Intent(this, SettingsActivity.class);
                startActivity(intent2);
                break;
            case R.id.imageScore:
                Intent intent3 = new Intent(this, ScoreActivity.class);
                startActivity(intent3);
                break;
            default:
                break;
        }

    }
@Override
    protected void onDestroy(){
        super.onDestroy();
        soundPool.release();
        soundPool = null;
}
}
