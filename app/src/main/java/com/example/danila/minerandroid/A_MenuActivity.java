package com.example.danila.minerandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class A_MenuActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);
        Button play_button = findViewById(R.id.play_button);
        Button records_button = findViewById(R.id.records_button);
        Button exit_button = findViewById(R.id.exit_button);

        play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToChooseLevelActivity();
            }
        });

        records_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRecordsActivity();
            }
        });

        exit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToExit();
            }
        });


    }

    private void goToChooseLevelActivity() {
        startActivity(new Intent(this, A_ChooseLevelActivity.class));
    }

    private void goToRecordsActivity() {
        startActivity(new Intent(this, A_RecordsTableActivity.class));
    }

    private void goToExit() {
        System.exit(0);
    }

}

