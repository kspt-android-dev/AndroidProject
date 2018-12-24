package ru.gdcn.beastmaster64revelations.GameClass.Characters.NPC;

import ru.gdcn.beastmaster64revelations.GameClass.Characters.CharacterClass;
import ru.gdcn.beastmaster64revelations.GameInterface.Action.Action;
import ru.gdcn.beastmaster64revelations.GameInterface.Character.Interactions.Trade;
import ru.gdcn.beastmaster64revelations.GameInterface.Character.NPC.Trader;
import ru.gdcn.beastmaster64revelations.GameInterface.Items.ItemContainer;
import ru.gdcn.beastmaster64revelations.GameInterface.World.Location.Location;

import java.util.ArrayList;
import java.util.List;

//TODO Трейдер должен расширять NPC а не Character
public class TraderClass extends NPCclass implements Trader {

    //TODO Пока не очень представляю как будет работать трейдер в игре, поэтому нужно будет доделать его попозже
    //Когда будет понятно что от него нужно

    private ArrayList<String> phrases;
    private Trade trade;

    public TraderClass(String name,
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
    public ItemContainer getGoods() {
        return null;
    }

    @Override
    public Trade getTrade() {
        return trade;
    }

}
