package ru.gdcn.beastmaster64revelations.MainMenu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import ru.gdcn.beastmaster64revelations.CharacterCreation.CharacterCreationActivity;
import ru.gdcn.beastmaster64revelations.GameLogger;
import ru.gdcn.beastmaster64revelations.R;
import ru.gdcn.beastmaster64revelations.Settings.SettingsActivity;
import ru.gdcn.beastmaster64revelations.UIElements.ProportionalImageView;


public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        GameLogger.log(getString(R.string.log_initialize));
        setContentView(R.layout.activity_main_menu);

        GameLogger.log(getString(R.string.log_events));
        initButtonEvents();

        ImageView imageView = new ProportionalImageView(this);
        imageView.setAlpha(0.9f);
        imageView.setImageDrawable(imageView.getResources().getDrawable(R.drawable.main_menu_background));
        FrameLayout mainFrame = findViewById(R.id.activity_main_menu_mainFrame);
        mainFrame.addView(imageView, 0);

    }

    private void initButtonEvents() {
        GameLogger.log(getString(R.string.log_buttons));
        Button playButton = findViewById(R.id.main_menu_button_play);
        Button settingsButton = findViewById(R.id.main_menu_button_settings);
        Button exitButton = findViewById(R.id.main_menu_button_exit);

        GameLogger.log(getString(R.string.log_game_entry));
        playButton.setOnClickListener(v -> {
            Intent goToGameIntent = new Intent(getApplicationContext(), CharacterCreationActivity.class);
            startActivity(goToGameIntent);
        });

        GameLogger.log(getString(R.string.log_settings_entry));
        settingsButton.setOnClickListener(v -> {
            Intent goToSettingsIntent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(goToSettingsIntent);
        });
        settingsButton.setEnabled(false);

        GameLogger.log(getString(R.string.log_quit));
        exitButton.setOnClickListener(v -> finish());

    }

}
