package ru.gdcn.beastmaster64revelations.GameActivities.MainMenu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import ru.gdcn.beastmaster64revelations.GameActivities.CharacterCreation.CharacterCreationActivity;
import ru.gdcn.beastmaster64revelations.GameLogger;
import ru.gdcn.beastmaster64revelations.R;
import ru.gdcn.beastmaster64revelations.GameActivities.Settings.SettingsActivity;
import ru.gdcn.beastmaster64revelations.UIElements.ProportionalImageView;


public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //Задаём события кнопкам в менюшке
        initButtonEvents();

        //Задаём картинку, которая растягивается по ширине экрана
        ImageView imageView = new ProportionalImageView(this);
        imageView.setAlpha(0.9f);
        imageView.setImageDrawable(imageView.getResources().getDrawable(R.drawable.main_menu_background));
        FrameLayout mainFrame = findViewById(R.id.activity_main_menu_mainFrame);
        mainFrame.addView(imageView, 0);

    }

    private void initButtonEvents() {
        //Достаём кнопки из вьюшки
        Button playButton = findViewById(R.id.main_menu_button_play);
        Button settingsButton = findViewById(R.id.main_menu_button_settings);
        Button exitButton = findViewById(R.id.main_menu_button_exit);

        playButton.setOnClickListener(v -> {
            Intent goToGameIntent = new Intent(getApplicationContext(), CharacterCreationActivity.class);
            startActivity(goToGameIntent);
        });

        settingsButton.setOnClickListener(v -> {
            Intent goToSettingsIntent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(goToSettingsIntent);
        });
        settingsButton.setEnabled(false);

        exitButton.setOnClickListener(v -> finish());

    }

}
