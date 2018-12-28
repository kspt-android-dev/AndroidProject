package com.logiccombine.artmate.logiccombine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class InHelp extends AppCompatActivity {

    @Override
    public void onBackPressed(){
        try {
            Intent intent = new Intent(InHelp.this, MainActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.in_help);

        final ImageButton helpTitleButton = (ImageButton)findViewById(R.id.helpTitleButton);
        helpTitleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    onBackPressed();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
