package kamiko.klondike_java;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class RulesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button newGame = findViewById(R.id.newGame);
        Button back = findViewById(R.id.back);

        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.newGame) {
                    Intent intent = new Intent(RulesActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.back) {
                    Intent intent = new Intent(RulesActivity.this,StartActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}
