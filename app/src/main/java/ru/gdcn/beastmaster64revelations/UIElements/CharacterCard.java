package ru.gdcn.beastmaster64revelations.UIElements;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ru.gdcn.beastmaster64revelations.CharacterCreation.AttributeCounter;
import ru.gdcn.beastmaster64revelations.GameClass.Characters.PlayerClass;
import ru.gdcn.beastmaster64revelations.GameInterface.Character.Character;
import ru.gdcn.beastmaster64revelations.GameInterface.Character.NPC.Player;
import ru.gdcn.beastmaster64revelations.R;


public class CharacterCard extends LinearLayout {

    Character character;

    TextView name;
    ImageView face;
    TextView HP;
    //TextView stats;

    public CharacterCard(final Context context, @Nullable AttributeSet attrs, Character character) {
        super(context, attrs);
        this.character = character;

        face = new ImageView(context);
        if (character instanceof PlayerClass)
            face.setImageDrawable(face.getResources().getDrawable(R.drawable.hero_face));
        else
            face.setImageDrawable(face.getResources().getDrawable(R.drawable.orc_face));
        LayoutParams layoutParams = new LayoutParams(300, 300);
        face.setLayoutParams(layoutParams);

        name = new TextView(context);
        name.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        name.setText(character.getName());
        name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        name.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        name.setTextColor(Color.WHITE);

        HP = new TextView(context);
        HP.setText(character.getHP() + "/" + character.getMaxHP());
        HP.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        HP.setTextColor(Color.RED);
        HP.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        HP.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);

//        stats = new TextView(context);
//        stats.setText("-----\n" +
//                "СИЛ:" + character.getStrength() + "\n" +
//                "ЛОВ:" + character.getAgility() + "\n" +
//                "ИНТ:" + character.getIntellect() + "\n" +
//                "-----");
//        stats.setTextAlignment(TEXT_ALIGNMENT_CENTER);
//        stats.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
//        stats.setTextColor(Color.WHITE);

        this.setOrientation(LinearLayout.VERTICAL);
        this.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        this.setGravity(Gravity.CENTER);

        this.addView(name);
        this.addView(face);
        this.addView(HP);
        //this.addView(stats);

    }

    public void updateContent(){
        HP.setText(character.getHP() + "/" + character.getMaxHP());
    }

    public ImageView getFace() {
        return face;
    }
}