package ru.gdcn.beastmaster64revelations.GameInterface.Action;

import ru.gdcn.beastmaster64revelations.GameInterface.Character.Character;

public interface Action {

    /** Описывает все действия совершаемые с Персонажами**/

    /**
     * Возвращает название действия
     **/

    public String getName();

    /**
     * Возвращает кол-во необходимых очков действия
     **/

    public Integer getRequiredAP();

    /**
     * Возвращает тип действия
     **/

    public ActionType getType();

    /**
     * Применяет действие, совершая необходимые действия с
     * "исполнителем" и "жервтой".
     **/

    public ActionResult use(Character user, Character other);

}
