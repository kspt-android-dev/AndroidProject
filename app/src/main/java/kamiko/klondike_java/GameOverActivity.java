package kamiko.klondike_java;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class GameOverActivity extends AppCompatActivity {

    long time;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button replay = findViewById(R.id.newGame);
        Button quit = findViewById(R.id.quit);
        Button menu = findViewById(R.id.menu);

        TextView chronometer = findViewById(R.id.chronometer);
        TextView count = findViewById(R.id.counter);
        String counter = getIntent().getStringExtra("counter");

        if (savedInstanceState == null) time = getIntent().getLongExtra("elapsedMilliSeconds", 0);
        else time = savedInstanceState.getLong("time");

        long minutes = TimeUnit.SECONDS.convert(time, TimeUnit.NANOSECONDS) / 60;
        long seconds = TimeUnit.SECONDS.convert(time, TimeUnit.NANOSECONDS) % 60;

        chronometer.setText(String.format("%02d:%02d", minutes, seconds));
        count.setText(counter);

        replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.newGame) {
                    Intent intent = new Intent(GameOverActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.menu) {
                    Intent intent = new Intent(GameOverActivity.this, StartActivity.class);
                    startActivity(intent);
                }
            }
        });

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.quit) {
                    Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                    homeIntent.addCategory( Intent.CATEGORY_HOME );
                    homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(homeIntent);
                    System.exit(1);
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("time", time);
    }

}
