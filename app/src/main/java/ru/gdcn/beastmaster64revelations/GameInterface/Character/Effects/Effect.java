package ru.gdcn.beastmaster64revelations.GameInterface.Character.Effects;

import ru.gdcn.beastmaster64revelations.GameInterface.Character.Character;

public interface Effect {

    /**
     * Возвращает название эффекта
     **/
    public String getName();

    /**
     * Данный метод должен вызываться после каждого перемещения между локациями.
     * Он отнимает единицу от оставшихся ходов, если эффект временный. Если же
     * эффект задумывается как постоянный, этот метод по сути ничего не делает.
     * Возвращает:
     * True - если ходы ещё остались
     * False - если ходы эффекта закончились (turnsLeft = 0), тогда нужно снять эффект с Персонажа
     * (снятие эффекта подразумевает вызов метода callOff)
     **/
    public Boolean update();

    /**
     * Возвращает количество оставшихся ходов
     **/
    public Integer turnsLeft();

    /**
     * Применяет эффект на персонажа (изменяет необходимые характеристики Персонажа и добавляет
     * данный эффект в контейнер эффектов персонажа)
     **/
    public Integer applyOn(Character character);

    /**
     * Снимает эффект с персонажа, восстанавливая характеристики и удаляя эффект из контейнера
     **/
    public Boolean callOff(Character character);

}
