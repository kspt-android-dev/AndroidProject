package com.logiccombine.artmate.logiccombine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class InStats extends AppCompatActivity {

    @Override
    public void onBackPressed(){
        try {
            Intent intent = new Intent(InStats.this, MainActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.in_stats);

        final TextView statTitle = (TextView)findViewById(R.id.statTitle);
        statTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    onBackPressed();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        final String APP_PREFERENCES = "mysettings";
        final String APP_PREFERENCES_SCORE = "score";
        final SharedPreferences mySettings;
        mySettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        final TextView statNumber = (TextView)findViewById(R.id.statNumber);
        statNumber.setText(String.valueOf(mySettings.getInt(APP_PREFERENCES_SCORE, 0)));
    }
}
