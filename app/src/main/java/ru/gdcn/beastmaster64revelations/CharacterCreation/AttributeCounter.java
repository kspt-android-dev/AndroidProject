package ru.gdcn.beastmaster64revelations.CharacterCreation;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AttributeCounter {

    private int value;
    private List<AttributeChanger> changers = new ArrayList<>();
    private TextView visibleCounter;

    public AttributeCounter(int totalPoints, TextView visibleCounter) {
        this.value = totalPoints;
        this.visibleCounter = visibleCounter;
    }

    public void notifyChangers(){
        for (AttributeChanger changer: changers) {
            changer.updateButtons();
        }
    }

    public boolean hasPoints(){
        return value > 0;
    }

    public int getPoints(){
        return value;
    }

    public boolean substract(){
        if (value <= 0){
            value = 0;
            return false;
        }
        value--;
        notifyChangers();
        updateText();
        return true;
    }

    public boolean add(){
        value++;
        notifyChangers();
        updateText();
        return true;
    }

    public void addChanger(AttributeChanger changer){
        this.changers.add(changer);
    }


    private void updateText() {
        visibleCounter.setText(String.valueOf(value));
    }

}
