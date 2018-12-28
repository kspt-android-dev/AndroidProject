package com.example.pc_alyona.bullsandcows;

import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;

public class Game extends AppCompatActivity implements View.OnClickListener {

    Button gameBack;
    Button tryAgain;
    Button enterNumber;

    TextView history; //текстовое поле с историей игры
    TextInputLayout userNumber; //текстовое поле ввода числа

    int numberSize;
    int volumeMusic;
    int volumeClick;

    int stepsCounter; //счетчик "шагов" игрока

    GameState gameActions; //класс, в котором обрабатываются игровые события

    MediaPlayer song;

    //воспроизведение клика при нажатии на кнопку
    private void clickSound() {
        MediaPlayer click;
        click = MediaPlayer.create(this, R.raw.click);
        click.setVolume((float)volumeClick / 100, (float)volumeClick / 100);
        click.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //запрет смены ориентации экрана
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        gameBack = findViewById(R.id.gameBack);
        tryAgain = findViewById(R.id.tryAgainGame);
        history = findViewById(R.id.gameHistory);
        userNumber = findViewById(R.id.inputGame);
        enterNumber = findViewById(R.id.enterNumber);

        //установка прокрутки текстовой области с историей
        history.setMovementMethod(new ScrollingMovementMethod());

        Bundle args = getIntent().getExtras();
        numberSize = (int) args.get("numberSize");
        volumeClick = (int) args.get("volumeClick");
        volumeMusic = (int) args.get("volumeMusic");

        //включение песни
        song = MediaPlayer.create(this, R.raw.audio);
        song.setAudioStreamType(AudioManager.STREAM_MUSIC);
        song.setVolume((float)volumeMusic / 100, (float)volumeMusic / 100);
        song.setLooping(true);
        song.start();

        gameActions = new GameState(numberSize);

        gameActions.generateNumber();

        //можно раскомментировать чтобы выводилось загаданное число
        //history.append(Arrays.toString(gameActions.getNumber().toArray()) + "\n");

        gameBack.setOnClickListener(this);
        tryAgain.setOnClickListener(this);
        enterNumber.setOnClickListener(this);

        stepsCounter = 1;

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.gameBack:
                clickSound();
                //выключение песни переход выходом. возврат в активити ChooseComplexity
                song.reset();
                finish();
                break;
            case R.id.tryAgainGame:
                clickSound();
                //генерирование нового числа
                gameActions.generateNumber();
                //можно убрать комментарий, чтобы выводилось сгенерированное число
                //history.append(Arrays.toString(gameActions.getNumber().toArray()) + "\n");
                break;
            case R.id.enterNumber:
                clickSound();
                //ввод числа. подсчет количество быков и коров в gameActions
                String numberString = userNumber.getEditText().getText().toString();
                if(numberString.length() != numberSize) {
                    history.append("Ошибка: размер числа должен быть равен " + numberSize + "\n");
                }
                else {
                    Pair<Integer, Integer> bullsNCows =
                            gameActions.checkNumber(numberString);
                    if(bullsNCows != null) {
                        if(bullsNCows.first == numberSize) {
                            history.append("Правильно, вы угадали! Загаданное число: " + numberString + "\n");
                        }
                        else {
                            history.append(stepsCounter + ". Ваше число: " + numberString + ". ");
                            history.append(bullsNCows.first + " быков, " + bullsNCows.second + " коров.\n");
                        }
                    }
                }
                break;
        }
    }
}
