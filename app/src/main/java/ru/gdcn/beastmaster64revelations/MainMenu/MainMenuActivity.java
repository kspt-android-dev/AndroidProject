package ru.gdcn.beastmaster64revelations.MainMenu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ru.gdcn.beastmaster64revelations.CharacterCreation.CharacterCreationActivity;
import ru.gdcn.beastmaster64revelations.GameLogger;
import ru.gdcn.beastmaster64revelations.R;
import ru.gdcn.beastmaster64revelations.Settings.SettingsActivity;


public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GameLogger.log("Инициализация главного меню.");
        setContentView(R.layout.activity_main_menu);

        GameLogger.log("Задаём кнопкам события");
        initButtonEvents();
    }

    private void initButtonEvents() {
        GameLogger.log("Вытаскиваем все кнопки по id");
        Button playButton = findViewById(R.id.main_menu_button_play);
        Button settingsButton = findViewById(R.id.main_menu_button_settings);
        Button exitButton = findViewById(R.id.main_menu_button_exit);

        GameLogger.log("Добавляем событие входа в игру");
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToGameIntent = new Intent(getApplicationContext(), CharacterCreationActivity.class);
                startActivity(goToGameIntent);
            }
        });

        GameLogger.log("Добавляем событие входа в настройки");
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToSettingsIntent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(goToSettingsIntent);
            }
        });

        GameLogger.log("Добавляем событие выхода из игры");
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO нормальный выход (диалоговое окно)
                finish();
            }
        });

    }

}
