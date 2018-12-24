package ru.gdcn.beastmaster64revelations.UIElements;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ru.gdcn.beastmaster64revelations.GameActivities.CharacterCreation.Gender;
import ru.gdcn.beastmaster64revelations.GameClass.Characters.PlayerClass;
import ru.gdcn.beastmaster64revelations.GameClass.Characters.TestCharacters.DummyEnemy;
import ru.gdcn.beastmaster64revelations.GameInterface.Character.Character;
import ru.gdcn.beastmaster64revelations.R;


public class CharacterCard extends LinearLayout {

    private Character character;
    private TextView name;
    private ImageView face;
    private TextView HP;
    //TextView stats;

    public CharacterCard(final Context context, @Nullable AttributeSet attrs, Character character) {
        super(context, attrs);
        this.character = character;

        face = new ImageView(context);
        if (character instanceof PlayerClass) {
            if (((PlayerClass) (character)).getGender() == Gender.MALE)
                face.setImageDrawable(face.getResources().getDrawable(R.drawable.char_face_man));
            else
                face.setImageDrawable(face.getResources().getDrawable(R.drawable.char_face_woman));
        } else {
            switch (((DummyEnemy) (character)).getType()) {
                case DRUNK_PIRATE:
                    face.setImageDrawable(face.getResources().getDrawable(R.drawable.enemy_pirate));
                    break;
                case DARK_TWIN:
                    face.setImageDrawable(face.getResources().getDrawable(R.drawable.enemy_dark_twin));
                    break;
                case CULTIST:
                    face.setImageDrawable(face.getResources().getDrawable(R.drawable.enemy_cultist));
                    break;
                default:
                    face.setImageDrawable(face.getResources().getDrawable(R.drawable.orc_face));
                    break;
            }
        }
        LayoutParams layoutParams = new LayoutParams(300, 300);
        face.setLayoutParams(layoutParams);

        name = new TextView(context);
        name.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        name.setText(character.getName());
        name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        name.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        name.setTextColor(Color.WHITE);

        HP = new TextView(context);
        String text = character.getHP() + getResources().getString(R.string.text_slash) + character.getMaxHP();
        HP.setText(text);
        HP.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        HP.setTextColor(Color.RED);
        HP.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        HP.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);

//        Typeface tf = Typeface.createFromFile("fonts/straight");
//        HP.setTypeface(tf);

        this.setOrientation(LinearLayout.VERTICAL);
        this.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        this.setGravity(Gravity.CENTER);

        this.addView(name);
        this.addView(face);
        this.addView(HP);
        //this.addView(stats);

    }

    public void updateContent() {
        String text = character.getHP() + getResources().getString(R.string.text_slash) + character.getMaxHP();
        HP.setText(text);
    }

    public ImageView getFace() {
        return face;
    }

}