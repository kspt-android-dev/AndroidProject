package ru.gdcn.beastmaster64revelations.GameClass.Actions;

import ru.gdcn.beastmaster64revelations.GameInterface.Action.Action;
import ru.gdcn.beastmaster64revelations.GameInterface.Action.ActionResult;
import ru.gdcn.beastmaster64revelations.GameInterface.Action.ActionType;
import ru.gdcn.beastmaster64revelations.GameInterface.Character.Character;

public class BasicHeal implements Action {

    protected final String name;
    //    protected final Integer AP;
    @SuppressWarnings("WeakerAccess")
    protected final Integer pointsHealed;

    public BasicHeal(String name, Integer pointsHealed){
        this.name = name;
//        this.AP = AP;
        this.pointsHealed = pointsHealed;
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
        return ActionType.DEFENSE;
    }

    @Override
    public ActionResult use(Character user, Character other) {
        user.dealHeal(pointsHealed);
        return ActionResult.HIT;
    }

    @Override
    public String toString() {
        return "АТАКА{" +
                "name='" + name + '\'' +
                ", pointsHealed=" + pointsHealed +
                '}';
    }

}
