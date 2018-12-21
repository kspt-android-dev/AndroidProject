package ru.polytech.course.pashnik.lines.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ru.polytech.course.pashnik.lines.R;

import static java.lang.System.exit;


public class MainActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void onClickStartGame(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void onClickRecords(View view) {
        Intent intent = new Intent(this, RecordsActivity.class);
        startActivity(intent);
    }

    public void onClickExit(View view) {
        exit(1);
    }
}
