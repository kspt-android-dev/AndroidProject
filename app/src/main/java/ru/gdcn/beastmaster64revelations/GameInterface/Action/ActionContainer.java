package ru.gdcn.beastmaster64revelations.GameInterface.Action;

import java.util.List;

public interface ActionContainer {

    /** Контейнер для действий, который будет привязан к каждому Персонажу и Экипировке.
     * Должен хранить все действия, которые доступны игроку в той или иной ситуации.
     * Когда экипируется новое оружие, связанные с ним действия должны добавиться в
     * контейнер того, кто его экипирует.**/

    /**
     * Возвращает полный список действий
     **/

    public List<Action> getActions();

    /**
     * Возвращает только действия определённого типа
     **/

    public List<Action> getActions(ActionType type);

    /**
     * Возвращает только действия, на которые хватает очков действия
     **/

    public List<Action> getAvalableActions(Integer actionPoints);

    /**
     * Возвращает действия определённого типа, на которые хватает очков действия
     **/

    public List<Action> getAvalableActions(Integer actionPoints, ActionType type);

    /**
     * Добавляет действие в контейнер.
     * Вернёт False в исключительных ситуациях. (например действие уже есть в контейнере)
     **/

    public Boolean addAction(Action action);

    /**
     * Удаляет действие из контейнера.
     * Вернёт False только в исключительных ситуациях. (например, действия нет)
     **/

    public Boolean removeAction(Action action);

    /**
     * Аналогично addAction, но работает со списком действий
     **/

    public Boolean addAllActions(List<Action> actions);

    /**
     * Аналогично addAction, но работает с другим ActionContainer'ом
     **/

    public Boolean addAllActions(ActionContainer other);

}