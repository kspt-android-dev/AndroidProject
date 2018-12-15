package ru.gdcn.beastmaster64revelations.CharacterCreation;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AttributeChanger extends LinearLayout {

    private String attrName;
    private AttributeCounter counter;
    private Button plus;
    private Button minus;
    private TextView textValue;
    private Integer currentPoints = 0;

    public AttributeChanger(final Context context, @Nullable AttributeSet attrs, String atributeName, final AttributeCounter counter) {
        super(context, attrs);
        //AppCompatActivity mainActivity = (AppCompatActivity) context;
        this.attrName = atributeName;
        this.counter = counter;

        minus = new Button(context);
        minus.setText("-");
        TextView nameOfAtr = new TextView(context);
        nameOfAtr.setText(attrName + ": ");
        textValue = new TextView(context);
        textValue.setText("0");
        plus = new Button(context);
        plus.setText("+");

        minus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPoints < 0){
                    counter.notifyChangers();
                    return;
                }
                if (!counter.add())
                    return;
                currentPoints--;
                updateText();
                counter.notifyChangers();
            }
        });

        plus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!counter.hasPoints()){
                    counter.notifyChangers();
                    return;
                }
                if (!counter.substract())
                    return;
                currentPoints++;
                updateText();
                counter.notifyChangers();
            }
        });

        this.setOrientation(LinearLayout.HORIZONTAL);
        this.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        this.addView(minus);
        this.addView(nameOfAtr);
        this.addView(textValue);
        this.addView(plus);
    }

    private void updateText() {
        this.textValue.setText(String.valueOf(currentPoints));
    }

    public void updateButtons(){
        if (counter.hasPoints())
            plus.setEnabled(true);
        else
            plus.setEnabled(false);
        if (currentPoints > 0)
            minus.setEnabled(true);
        else
            minus.setEnabled(false);
    }

    public Integer getPoints() {
        return currentPoints;
    }
}
