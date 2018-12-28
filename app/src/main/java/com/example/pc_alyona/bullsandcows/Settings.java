package com.example.pc_alyona.bullsandcows;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class Settings extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    Button backButton;
    SeekBar onClick;
    SeekBar onMusic;
    int volumeClick;
    int volumeMusic;

    private void clickSound() {
        MediaPlayer click;
        click = MediaPlayer.create(this, R.raw.click);
        click.setAudioStreamType(AudioManager.STREAM_MUSIC);
        click.setVolume((float)volumeClick / 100, (float)volumeClick / 100);
        click.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //получение аргументов из MainActivity
        Bundle args = getIntent().getExtras();
        volumeClick = (int) args.get("volumeClick");
        volumeMusic = (int) args.get("volumeMusic");

        backButton = findViewById(R.id.backSetButton);

        //обработчик нажатия на кнопку "назад"
        View.OnClickListener backListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case(R.id.backSetButton):
                        clickSound();
                        //возврат измененных параметров через intent
                        Intent intent = new Intent();
                        intent.putExtra("volume", volumeMusic * 1000 + volumeClick);
                        setResult(RESULT_OK, intent);
                        finish();
                        break;
                }
            }
        };
        backButton.setOnClickListener(backListener);

        onClick = findViewById(R.id.seekBarClick);
        onMusic = findViewById(R.id.seekBarMusic);

        onClick.setOnSeekBarChangeListener(this);
        onMusic.setOnSeekBarChangeListener(this);

        onClick.setProgress(volumeClick);
        onMusic.setProgress(volumeMusic);

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    //обработчик изменения параметров звука
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        switch(seekBar.getId()) {
            case(R.id.seekBarClick):
                volumeClick = seekBar.getProgress();
                clickSound();
                break;
            case(R.id.seekBarMusic):
                volumeMusic = seekBar.getProgress();
                clickSound();
                break;
        }
    }
}
