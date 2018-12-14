package ru.gdcn.beastmaster64revelations.GameClass.Items.Usable.Scrolls;

import ru.gdcn.beastmaster64revelations.GameClass.Actions.BasicAttack;
import ru.gdcn.beastmaster64revelations.GameClass.Items.GameItemClass;
import ru.gdcn.beastmaster64revelations.GameInterface.Action.Action;
import ru.gdcn.beastmaster64revelations.GameInterface.Character.Character;
import ru.gdcn.beastmaster64revelations.GameInterface.Items.ItemRarity;
import ru.gdcn.beastmaster64revelations.GameInterface.Items.UsableItems.Scroll;
import ru.gdcn.beastmaster64revelations.GameInterface.Items.UsableItems.ScrollType;
import ru.gdcn.beastmaster64revelations.GameInterface.Items.UsableItems.ScrollUseResult;

import static ru.gdcn.beastmaster64revelations.GameClass.Constants.Integers.*;

public class ScrollClass extends GameItemClass implements Scroll {

    protected final Action action;
    protected final ScrollType type;

    public ScrollClass(String name, Action action, ScrollType type){
        super(
                name,
                ItemRarity.COMMON,
                SCROLL_WEIGHT_DEFAULT,
                SCROLL_COST_DEFAULT,
                true,
                true,
                true,
                true,
                false,
                true
                );
        this.action = action;
        this.type = type;
    }

    public ScrollClass(){
        /**
         * Создание примитивного свитка
         * **/
        super(
                "Свиток",
                ItemRarity.COMMON,
                SCROLL_WEIGHT_DEFAULT,
                SCROLL_COST_DEFAULT,
                true,
                true,
                true,
                true,
                false,
                true
        );
        this.action = new BasicAttack("Заклинание", 1.0);
        this.type = ScrollType.OTHER_USABLE;
    }

    @Override
    public Action getAction() {
        return action;
    }

    @Override
    public ScrollType getType() {
        return type;
    }

    @Override
    public ScrollUseResult use(Character user, Character other) {
        action.use(user, other);
        return ScrollUseResult.SUCCESS;
    }

}
