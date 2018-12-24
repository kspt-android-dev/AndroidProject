package ru.gdcn.beastmaster64revelations.GameActivities.CharacterCreation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import ru.gdcn.beastmaster64revelations.GameActivities.InLocation.InLocationActivity;
import ru.gdcn.beastmaster64revelations.GameClass.Characters.PlayerClass;
import ru.gdcn.beastmaster64revelations.GameInterface.Character.NPC.Player;
import ru.gdcn.beastmaster64revelations.GameLogger;
import ru.gdcn.beastmaster64revelations.R;

public class CharacterCreationActivity extends AppCompatActivity {

    //Игрок и его пол
    Player player;
    Gender gender;

    //Аватарки персонажей
    ImageView manFace;
    ImageView womanFace;

    //Переменные для отслеживания очков
    Integer points = 15;
    Integer stren = 5;
    Integer agili = 5;
    Integer intel = 5;

    //Кнопочки для распределения очков
    Button strMin;
    Button agiMin;
    Button intMin;
    Button strPlus;
    Button agiPlus;
    Button intPlus;

    //Кнопка "продолжить"
    Button readyButton;

    //Тексты которые нужно обновлять в соответствии кол-ву очков
    TextView strText;
    TextView agiText;
    TextView intText;
    TextView pointsText;
    TextView inputName;
    TextView textName;

    //Ключи для сохранения в Bundle
    static final String CREATION_STR_ID = "strength";
    static final String CREATION_AGI_ID = "agility";
    static final String CREATION_INTELLECT_ID = "intellect";
    static final String CREATION_PTS_ID = "points";
    static final String CREATION_GENDER_ID = "gender";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_creation);

        //Достаём все нужные нам вьюшки

        strMin = findViewById(R.id.button_minus_str);
        agiMin = findViewById(R.id.button_minus_agi);
        intMin = findViewById(R.id.button_minus_int);
        strPlus = findViewById(R.id.button_plus_str);
        agiPlus = findViewById(R.id.button_plus_agi);
        intPlus = findViewById(R.id.button_plus_int);

        strText = findViewById(R.id.activity_character_creation_strengthText);
        agiText = findViewById(R.id.activity_character_creation_agilityText);
        intText = findViewById(R.id.activity_character_creation_intellectText);
        pointsText = findViewById(R.id.activity_character_creation_points);
        textName = findViewById(R.id.activity_character_creation_char_name);
        inputName = findViewById(R.id.activity_character_creation_edit_char_name);
        manFace = findViewById(R.id.activity_character_creation_char_face_man);
        womanFace = findViewById(R.id.activity_character_creation_char_face_woman);

        gender = Gender.FEMALE;

        //Задаём события на кнопки

        readyButton = findViewById(R.id.activity_character_creation_proceedButton);
        readyButton.setOnClickListener(v -> {
            player = new PlayerClass(inputName.getText().toString(), null, stren, agili, intel, 10, gender);
            Intent intent = new Intent(getApplicationContext(), InLocationActivity.class);
            intent.putExtra("player", player);
            startActivity(intent);
        });

        strMin.setOnClickListener(v -> {
            points++;
            stren--;
            updateContent();
        });
        agiMin.setOnClickListener(v -> {
            points++;
            agili--;
            updateContent();
        });
        intMin.setOnClickListener(v -> {
            points++;
            intel--;
            updateContent();
        });

        strPlus.setOnClickListener(v -> {
            points--;
            stren++;
            updateContent();
        });
        agiPlus.setOnClickListener(v -> {
            points--;
            agili++;
            updateContent();
        });
        intPlus.setOnClickListener(v -> {
            points--;
            intel++;
            updateContent();
        });


        inputName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0)
                    disableButton(readyButton);
                else
                    enableButton(readyButton);
                textName.setText(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        manFace.setOnClickListener(v -> {
            gender = Gender.MALE;
            updateContent();
        });
        womanFace.setOnClickListener(v -> {
            gender = Gender.FEMALE;
            updateContent();
        });

        //Обновляем контент активити в соответствии с внутренней логикой
        updateContent();

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(CREATION_PTS_ID, points);
        savedInstanceState.putSerializable(CREATION_GENDER_ID, gender);
        savedInstanceState.putInt(CREATION_STR_ID, stren);
        savedInstanceState.putInt(CREATION_AGI_ID, agili);
        savedInstanceState.putInt(CREATION_INTELLECT_ID, intel);
        GameLogger.log("GDCN", "Character creation instance Saved");
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        points = savedInstanceState.getInt(CREATION_PTS_ID);
        stren = savedInstanceState.getInt(CREATION_STR_ID);
        agili = savedInstanceState.getInt(CREATION_AGI_ID);
        intel = savedInstanceState.getInt(CREATION_INTELLECT_ID);
        gender = (Gender) savedInstanceState.getSerializable(CREATION_GENDER_ID);
        GameLogger.log("GDCN", "Character creation instance Restored");
    }

    private void updateContent() {

        //Вызываем это каждый раз когда чё-то меняем

        if (points > 0) {
            enableButton(strPlus);
            enableButton(agiPlus);
            enableButton(intPlus);
        } else {
            disableButton(agiPlus);
            disableButton(strPlus);
            disableButton(intPlus);
        }

        if (stren > 0)
            enableButton(strMin);
        else
            disableButton(strMin);

        if (stren > 0)
            enableButton(strMin);
        else
            disableButton(strMin);

        if (agili > 0)
            enableButton(agiMin);
        else
            disableButton(agiMin);

        if (intel > 0)
            enableButton(intMin);
        else
            disableButton(intMin);

        if (gender == Gender.MALE) {
            manFace.setAlpha(1.0f);
            womanFace.setAlpha(0.5f);
        } else {
            manFace.setAlpha(0.5f);
            womanFace.setAlpha(1.0f);
        }

        pointsText.setText(String.valueOf(points));
        String stats = getResources().getString(R.string.character_stats_text_agility) + agili;
        agiText.setText(stats);
        stats = getResources().getString(R.string.character_stats_text_strength) + stren;
        strText.setText(stats);
        stats = getResources().getString(R.string.character_stats_text_intellect) + intel;
        intText.setText(stats);

    }

    //небольшой шорткат для удобства
    private void disableButton(Button button) {
        button.setEnabled(false);
    }

    //это тоже
    private void enableButton(Button button) {
        button.setEnabled(true);
    }


}
