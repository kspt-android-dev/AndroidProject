package com.example.checkers;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

public class SettingsActivity extends Activity {

    RadioGroup firstMove;
    int oldFirstMove;
    TextView instructionTW;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        firstMove = findViewById(R.id.first_move_group);
        instructionTW = findViewById(R.id.open_instruction);
        instructionTW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openInstruction = new Intent(SettingsActivity.this, InstructionActivity.class);
                startActivity(openInstruction);
            }
        });

        findViewById(R.id.save_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);

                // если при закрытии окна настройки изменившиеся
                if (firstMove.getCheckedRadioButtonId() != oldFirstMove) {
                    // установка цветов игроков
                    if (firstMove.getCheckedRadioButtonId() == R.id.upper_player_first_move) {
                        GameLogic.upperPlayerColor = Color.WHITE;
                        GameLogic.bottomPlayerColor = Color.BLACK;
                    } else {
                        GameLogic.upperPlayerColor = Color.BLACK;
                        GameLogic.bottomPlayerColor = Color.WHITE;
                    }
                    setResult(RESULT_OK);
                }
                    finish();
            }
        });

        setParams();
    }

    // устанавливаем параметры в соответствии с текущими настройками поля
    public void setParams() {
        firstMove.check( (GameLogic.upperPlayerColor == Color.WHITE) ? R.id.upper_player_first_move : R.id.bottom_player_first_move);
        oldFirstMove = firstMove.getCheckedRadioButtonId();
    }

}
