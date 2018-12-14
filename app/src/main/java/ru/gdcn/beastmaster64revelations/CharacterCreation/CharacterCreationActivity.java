package ru.gdcn.beastmaster64revelations.CharacterCreation;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import ru.gdcn.beastmaster64revelations.GameActivities.InLocation.InLocationActivity;
import ru.gdcn.beastmaster64revelations.GameClass.Characters.PlayerClass;
import ru.gdcn.beastmaster64revelations.GameInterface.Character.Character;
import ru.gdcn.beastmaster64revelations.GameInterface.Character.NPC.Player;
import ru.gdcn.beastmaster64revelations.R;

public class CharacterCreationActivity extends AppCompatActivity {

    CharacterCreationFragment creationFragment;
    Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_creation);
        String[] arr = {"СИЛ", "ЛОВ", "ИНТ"};
        Integer points = 3;
        creationFragment = CharacterCreationFragment.newInstance(arr, points);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .add(R.id.activity_character_creation_fragHolder, creationFragment)
                .commit();

        Button readyButton = findViewById(R.id.activity_character_creation_proceedButton);
        readyButton.setOnClickListener(v -> {

            player = new PlayerClass("Герой", null, 60,30,25,10);
            Intent intent = new Intent(getApplicationContext(), InLocationActivity.class);
            intent.putExtra("player", player);
            startActivity(intent);

//            if (!creationFragment.hasPoints() && creationFragment.hasName()){
//                player = creationFragment.getCharacterFromData();
//                Intent intent = new Intent(getApplicationContext(), InLocationActivity.class);
//                intent.putExtra("player", player);
//                startActivity(intent);
//            }

        });

    }

}
