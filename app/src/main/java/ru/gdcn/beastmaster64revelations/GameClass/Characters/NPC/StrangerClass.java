package ru.gdcn.beastmaster64revelations.GameClass.Characters.NPC;

import ru.gdcn.beastmaster64revelations.GameClass.Characters.CharacterClass;
import ru.gdcn.beastmaster64revelations.GameInterface.Character.NPC.Stranger;
import ru.gdcn.beastmaster64revelations.GameInterface.World.Location.Location;

import java.util.ArrayList;
import java.util.List;

//TODO Почему интерфейс Stranger не расширяет интерфейс NPC?
public class StrangerClass extends NPCclass implements Stranger {

    /*Todo Должен содержать не строки, а варианты диалогов.
    Советую пока оставить этот класс, пока не решим как его лучше сделать*/

    private ArrayList<String> phrases;
    private ArrayList<String> stories;

    public StrangerClass(String name,
                         Location location,
                         Integer strength,
                         Integer agility,
                         Integer intellect,
                         Integer luck,
                         Boolean isTalkable,
                         Boolean isAvoidable,
                         Boolean isAttackable) {
        super(name, location, strength, agility, intellect, luck, isTalkable, isAvoidable, isAttackable);
    }


    @Override
    public List<String> getPhrases() {
        return phrases;
    }

    @Override
    public List<String> getStories() {
        return stories;
    }
}
