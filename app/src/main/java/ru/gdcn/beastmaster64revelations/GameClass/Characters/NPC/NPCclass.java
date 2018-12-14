package ru.gdcn.beastmaster64revelations.GameClass.Characters.NPC;

import ru.gdcn.beastmaster64revelations.GameClass.Actions.BasicAttack;
import ru.gdcn.beastmaster64revelations.GameClass.Characters.CharacterClass;
import ru.gdcn.beastmaster64revelations.GameInterface.Action.Action;
import ru.gdcn.beastmaster64revelations.GameInterface.Character.Character;
import ru.gdcn.beastmaster64revelations.GameInterface.Character.NPC.NPC;
import ru.gdcn.beastmaster64revelations.GameInterface.Items.ItemContainer;
import ru.gdcn.beastmaster64revelations.GameInterface.World.Location.Location;

import java.util.List;

public class NPCclass extends CharacterClass implements NPC {

    private Boolean isValuableForLore;
    private Boolean isTalkable;
    private Boolean isAvoidable;
    private Boolean isAttackable;

    public NPCclass(String name,
                    Location location,
                    Integer strength,
                    Integer agility,
                    Integer intellect,
                    Integer luck,
                    Boolean isTalkable,
                    Boolean isAvoidable,
                    Boolean isAttackable) {
        super(name, location, strength, agility, intellect, luck);
        this.isValuableForLore = isValuableForLore;
        this.isTalkable = isTalkable;
        this.isAvoidable = isAvoidable;
        this.isAttackable = isAttackable;
    }

    @Override
    public Boolean isAttackable() {
        return isAttackable != null && isAttackable;
    }

    @Override
    public Boolean isTalkable() {
        return isTalkable != null && isTalkable;
    }

    @Override
    public Boolean isAvoidable() {
        return isAvoidable != null && isAvoidable;
    }

    @Override
    public List<String> getWarPhrases() {
        return null;
    }//TODO

    @Override
    public ItemContainer getLoot() {
        return null;
    }//TODO

    @Override
    public Action makeNextFightTurn(Character enemy) {
        Action action = new BasicAttack("Бац", 1.0);
        action.use(this, enemy);
        return action;
    }

    @Override
    public Boolean isValuableForLore() {
        return isValuableForLore != null && isValuableForLore;
    }
}
