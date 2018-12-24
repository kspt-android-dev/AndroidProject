package polytech.vladislava.sudoku;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button startButton = findViewById(R.id.a_menu_PLAY);
        startButton.setOnClickListener(v -> goToGame());

        Button recButton = findViewById(R.id.a_menu_RECORDS);
        recButton.setOnClickListener(v -> goToRecords());

        Button aboutButton = findViewById(R.id.a_menu_ABOUT);
        aboutButton.setOnClickListener(v -> goToAbout());

        Button exitButton = findViewById(R.id.a_menu_EXIT);
        exitButton.setOnClickListener(v -> exit());

    }

    private void exit() {
        finish();
    }

    private void goToAbout() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    private void goToRecords() {
        Intent intent = new Intent(this, RecordsActivity.class);
        startActivity(intent);
    }

    private void goToGame() {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

}
