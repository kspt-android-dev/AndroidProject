package ru.gdcn.beastmaster64revelations.GameClass.Characters.TestCharacters;

import java.util.List;
import java.util.Random;

import ru.gdcn.beastmaster64revelations.GameClass.Actions.BasicAttack;
import ru.gdcn.beastmaster64revelations.GameClass.Actions.BasicHeal;
import ru.gdcn.beastmaster64revelations.GameClass.Characters.CharacterClass;
import ru.gdcn.beastmaster64revelations.GameInterface.Action.Action;
import ru.gdcn.beastmaster64revelations.GameInterface.Character.Character;
import ru.gdcn.beastmaster64revelations.GameInterface.Character.NPC.NPC;
import ru.gdcn.beastmaster64revelations.GameInterface.Character.NPC.Opponent;
import ru.gdcn.beastmaster64revelations.GameInterface.Character.NPC.OpponentType;
import ru.gdcn.beastmaster64revelations.GameInterface.Character.NPC.Player;
import ru.gdcn.beastmaster64revelations.GameInterface.Items.ItemContainer;

public class DummyEnemy extends CharacterClass implements Opponent {

    private OpponentType type;

    public DummyEnemy() {
        super(generateName(), null, 10, 10, 10, 10);
    }

    private DummyEnemy(String name, Integer randomBase) {
        super(name, null,
                randomBase - randomBase/4 + new Random().nextInt(randomBase/2),
                randomBase - randomBase/4 + new Random().nextInt(randomBase/2),
                randomBase - randomBase/4 + new Random().nextInt(randomBase/2),
                10);
    }

    private DummyEnemy(String name, OpponentType type, Integer randomBase) {
        this(name, randomBase);
        this.type = type;
    }

    private static String generateName() {
        int number = new Random().nextInt(4);
        String name = "Чёрт";
        switch (number) {
            case 0:
                name = "Культист";
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

    public static NPC generateEnemy(Player player, Double difficulty) {
        String name = generateName();
        OpponentType type = makeTypeByName(name);
        Integer base = 5 + (int) ((player.getAgility() + player.getIntellect() + player.getStrength()) * difficulty / 3);
        return new DummyEnemy(name, type, base);
    }

    private static OpponentType makeTypeByName(String name) {
        switch (name) {
            case "Культист":
                return OpponentType.CULTIST;
            case "Нетрезвый пират":
                return OpponentType.DRUNK_PIRATE;
            case "Злой двойник":
                return OpponentType.DARK_TWIN;
            default:
                return OpponentType.DRUNK_PIRATE;
        }
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
        if (HP * 1.0 / maxHP < 0.15) {
            action = new BasicHeal("Пуф!", 4*enemy.getIntellect());
        } else {
            action = new BasicAttack("Бац", 1.0);
        }
        return action;
    }

    @Override
    public Boolean isValuableForLore() {
        return false;
    }

    @Override
    public OpponentType getType() {
        return type;
    }

    @Override
    public Boolean isAgressive() {
        return true;
    }

    @Override
    public String getDescription() {
        //Заглушка
        return "Противник средней сложности, имеющий примерно равные характеристики с игроком. Постарайтесь не продуть ему!";
    }
}
