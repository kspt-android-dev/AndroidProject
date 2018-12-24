package ru.gdcn.beastmaster64revelations.GameInterface.Items.UsableItems;

import ru.gdcn.beastmaster64revelations.GameInterface.Action.Action;
import ru.gdcn.beastmaster64revelations.GameInterface.Character.Character;
import ru.gdcn.beastmaster64revelations.GameInterface.Items.GameItem;

public interface Scroll extends GameItem {

    /**
     * Возвращает действие приминения данного свитка
     * Когда свиток попадает в инвентарь Персонажа, действие попадает в список
     * доступных ему действий (в его ActionContainer)
     * Если свиток имеет тип SELF_USABLE, то данное действие доступно даже из инвентаря, вне боя
     **/
    public Action getAction();

    /**
     * Врзвращает тип свитка
     **/
    public ScrollType getType();

    /**
     * Описывает логику применения свитка
     * Например, оценивает интеллект того, кто использует свиток, рассчитывает урон
     * и наносит его другому Персонажу
     **/
    public ScrollUseResult use(Character user, Character other);

}
