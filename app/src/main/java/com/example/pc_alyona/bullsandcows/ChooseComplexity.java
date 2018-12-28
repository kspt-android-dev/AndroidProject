package com.example.pc_alyona.bullsandcows;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class ChooseComplexity extends AppCompatActivity implements View.OnClickListener {

    Button backButton;
    Button playButton;
    SeekBar chooseBar;
    TextView sizeText;
    int numberSize;

    MediaPlayer song;

    int volumeClick;
    int volumeMusic;

    //воспроизведение звука "клика" при нажатии на кнопки
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
        setContentView(R.layout.activity_choose_complexity);
        //запрет поворота экрана
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //получение параметров из MainActivity
        Bundle args = getIntent().getExtras();
        volumeClick = (int) args.get("volumeClick");
        volumeMusic = (int) args.get("volumeMusic");

        //воспроизведение музыки в режиме игры
        song = MediaPlayer.create(this, R.raw.audio);
        song.setAudioStreamType(AudioManager.STREAM_MUSIC);
        song.setVolume((float)volumeMusic / 100, (float)volumeMusic / 100);
        song.setLooping(true); //зацикливание музыки
        song.start();

        sizeText = findViewById(R.id.chooseNumb);
        backButton = findViewById(R.id.backButtonChoose);
        playButton = findViewById(R.id.playButtonChoose);
        chooseBar = findViewById(R.id.chooseSeek);

        //прослушивание seekbar. в seekbar выбирается размер числа.
        //при изменениях в seekbar соответственно меняется текст ниже
        SeekBar.OnSeekBarChangeListener seekListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                numberSize = progress + 1;
                sizeText.setText(Integer.toString(numberSize));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };

        backButton.setOnClickListener(this);
        playButton.setOnClickListener(this);
        chooseBar.setOnSeekBarChangeListener(seekListener);

        //по умолчанию сложность 5
        numberSize = 5;
        sizeText.setText("5");
        chooseBar.setProgress(4);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case(R.id.backButtonChoose):
                clickSound();
                //при возврате назад (в меню) выключение музыки
                song.reset();
                finish();
                break;
            case(R.id.playButtonChoose):
                clickSound();
                /* переход в активити Game. передача параметров сложности и звука.
                   активити ничего не возвращает.
                   переход переходом выключается музыка этого активити и включается в следующем */
                song.reset();
                Intent intentGame = new Intent(ChooseComplexity.this, Game.class);
                intentGame.putExtra("numberSize", numberSize);
                intentGame.putExtra("volumeClick", volumeClick);
                intentGame.putExtra("volumeMusic", volumeMusic);
                startActivity(intentGame);
                break;
        }
    }
}
