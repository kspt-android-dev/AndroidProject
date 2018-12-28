package com.example.pc_alyona.bullsandcows;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Rules extends Activity implements View.OnClickListener {

    //здесь только вывод правил и кнока "назад"
    Button backButton;
    int volume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
        backButton = findViewById(R.id.backRulButton);
        backButton.setOnClickListener(this);
        Bundle args = getIntent().getExtras();
        volume = (int) args.get("volume");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backRulButton:
                //обработка нажатия на кнопку "назад". возврат в MainActivity
                MediaPlayer click;
                click = MediaPlayer.create(this, R.raw.click);
                click.setAudioStreamType(AudioManager.STREAM_MUSIC);
                click.setVolume((float)volume / 100, (float)volume / 100);
                click.start();
                finish();
                break;
        }
    }
}
