package com.btn.thuynhung.puzzlegame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by ThuyNhung on 10/28/2018.
 */
public class howToPlayAct extends AppCompatActivity {

    @Override
    protected void onCreate (final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.how_to_play);

        getSupportActionBar().setTitle("Help");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
