package com.julia.tag;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.julia.tag.game.GameActivity;
import com.julia.tag.records.RecordsActivity;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        findViewById(R.id.activity_menu_play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, GameActivity.class));
                    overridePendingTransition(R.anim.pull_enter, R.anim.pull_exit);
            }
        });

        findViewById(R.id.activity_menu_records).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, RecordsActivity.class));
                overridePendingTransition(R.anim.pull_enter, R.anim.pull_exit);
            }
        });

        findViewById(R.id.activity_menu_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
