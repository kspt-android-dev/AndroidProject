package com.example.pc_alyona.bullsandcows;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button startGame; //кнопка "начать игру"
    private Button showRules; //кнопка "показать правила"
    private Button showSettings; //кнопка настроек
    private Button rageQuit; //кнопка выхода

    private int volumeClick;
    private int volumeMusic;

    //воспроизведение звука "клика" при нажатии на кнопки
    private void clickSound() {
        MediaPlayer click;
        click = MediaPlayer.create(this, R.raw.click);
        //значение громкости хранится в int а не float, так легче работать с переменными
        click.setVolume((float)volumeClick / 100, (float)volumeClick / 100);
        click.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        volumeClick = 50;
        volumeMusic = 50;

        startGame = findViewById(R.id.startButton);
        showRules = findViewById(R.id.rulesButton);
        showSettings = findViewById(R.id.settingsButton);
        rageQuit = findViewById(R.id.quitButton);

        startGame.setOnClickListener(this);
        showRules.setOnClickListener(this);
        showSettings.setOnClickListener(this);
        rageQuit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startButton:
                clickSound();
                //загрузка активити ChooseComplexity. передача параметров звука. активити ничего не должен возвращать
                Intent intentStart = new Intent(MainActivity.this, ChooseComplexity.class);
                intentStart.putExtra("volumeClick", volumeClick);
                intentStart.putExtra("volumeMusic", volumeMusic);
                startActivity(intentStart);
                break;
            case R.id.rulesButton:
                clickSound();
                //загрузка активити Rules. передача громкости клика. активити ничего не должен возвращать
                Intent intentRules = new Intent(MainActivity.this, Rules.class);
                intentRules.putExtra("volume", volumeClick);
                startActivity(intentRules);
                break;
            case R.id.settingsButton:
                clickSound();
                //загрузка активити Settings. передача значений громкости.
                //в активити изменяются параметры, поэтому он возвращает новые значения громкости
                Intent intentSettings = new Intent(MainActivity.this, Settings.class);
                intentSettings.putExtra("volumeClick", volumeClick);
                intentSettings.putExtra("volumeMusic", volumeMusic);
                startActivityForResult(intentSettings, 1);
                break;
            case R.id.quitButton:
                //выход из приложения
                finish();
                System.exit(0);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //вызывается при возврате данных из Settings.
        if(data != null) {
            /*так как с помощью startactivityforresult можно вернуть только одно значение,
              а здесь необходимо два, в возвращаемом значении "закодировано" два параметра
              (первые три разряда числа - громкость музыки, последние три - клика)
             */
            int volumes = data.getIntExtra("volume", 1);
            volumeClick = volumes % 1000;
            volumeMusic = volumes / 1000;
        }
    }

}
