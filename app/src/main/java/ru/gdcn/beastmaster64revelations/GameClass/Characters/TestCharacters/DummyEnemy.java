package ru.gdcn.beastmaster64revelations.GameClass.Characters.TestCharacters;

import java.util.List;
import java.util.Random;

import ru.gdcn.beastmaster64revelations.GameClass.Actions.BasicAttack;
import ru.gdcn.beastmaster64revelations.GameClass.Actions.BasicHeal;
import ru.gdcn.beastmaster64revelations.GameClass.Characters.CharacterClass;
import ru.gdcn.beastmaster64revelations.GameInterface.Action.Action;
import ru.gdcn.beastmaster64revelations.GameInterface.Character.Character;
import ru.gdcn.beastmaster64revelations.GameInterface.Character.NPC.Opponent;
import ru.gdcn.beastmaster64revelations.GameInterface.Character.NPC.OpponentType;
import ru.gdcn.beastmaster64revelations.GameInterface.Items.ItemContainer;

public class DummyEnemy extends CharacterClass implements Opponent {

    public DummyEnemy() {
        super(generateName(), null, 10, 10, 10, 10);
    }


    public static String generateName() {
        int number = new Random().nextInt(4);
        String name = "Чёрт";
        switch (number){
            case 0:
                name = "Бешеный пёс";
                break;
            case 1:
                name = "Нетрезвый пират";
                break;
            case 2:
                name = "Злой двойник";
                break;
        }
        return name;
    }

    @Override
    public Boolean isAttackable() {
        return true;
    }

    @Override
    public Boolean isTalkable() {
        return false;
    }

    @Override
    public Boolean isAvoidable() {
        return true;
    }

    @Override
    public List<String> getWarPhrases() {
        return null;
    }

    @Override
    public ItemContainer getLoot() {
        return null;
    }

    @Override
    public Action makeNextFightTurn(Character enemy) {
        Action action;
        if (HP/maxHP * 1.0 < 0.2){
            action = new BasicHeal("Пуф!", 4);
        } else {
            action = new BasicAttack("Бац", 0.6);
        }
        return action;
    }

    @Override
    public Boolean isValuableForLore() {
        return false;
    }

    @Override
    public OpponentType getType() {
        return OpponentType.BANDIT;
    }

    @Override
    public Boolean isAgressive() {
        return true;
    }

}
