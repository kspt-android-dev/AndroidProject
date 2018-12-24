package com.example.myapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;


public class MainActivity extends AppCompatActivity {
    private boolean flagOnSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addListenerOnButton();
        addListenerOnSwitch();
    }


    private void addListenerOnSwitch() {
        Switch aSwitch = findViewById(R.id.switch2);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                flagOnSwitch = isChecked;
            }
        });
    }

    private void addListenerOnButton() {
        Button button3x3 = findViewById(R.id.play);
        Button button5x5 = findViewById(R.id.play5x5);

        button3x3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (flagOnSwitch) {
                            Intent intent3x3_bot = new Intent(getApplicationContext(), Main2Activity_bot.class);
                            startActivity(intent3x3_bot);
                        } else {
                            Intent intent3x3 = new Intent(getApplicationContext(), Main2Activity.class);
                            startActivity(intent3x3);
                        }
                    }
                }
        );

        button5x5.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (flagOnSwitch) {
                            Intent intent5x5 = new Intent(getApplicationContext(), Main3Activity_bot.class);
                            startActivity(intent5x5);
                        } else {
                            Intent intent5x5 = new Intent(getApplicationContext(), Main3Activity.class);
                            startActivity(intent5x5);
                        }
                    }
                }
        );


    }
}

