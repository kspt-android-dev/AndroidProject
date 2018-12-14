package ru.gdcn.beastmaster64revelations.GameClass.Actions;

import ru.gdcn.beastmaster64revelations.GameInterface.Action.Action;
import ru.gdcn.beastmaster64revelations.GameInterface.Action.ActionResult;
import ru.gdcn.beastmaster64revelations.GameInterface.Action.ActionType;
import ru.gdcn.beastmaster64revelations.GameInterface.Character.Character;

public class BasicAttack implements Action {

    protected final String name;
//    protected final Integer AP;
    protected final Double damageModifier;

    public BasicAttack(String name, Double damageModifier){
        this.name = name;
//        this.AP = AP;
        this.damageModifier = damageModifier;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Integer getRequiredAP() {
        //TODO
        return 0;
    }

    @Override
    public ActionType getType() {
        return ActionType.ATACK;
    }

    @Override
    public ActionResult use(Character user, Character other) {
        Integer damage = (int) (user.getFullAttack() * damageModifier);
        other.dealPhysicalDamage(damage);
        //ToDO
        return ActionResult.HIT;
    }

    @Override
    public String toString() {
        return "АТАКА{" +
                "name='" + name + '\'' +
                ", pointsHealed=" + damageModifier +
                '}';
    }

}
