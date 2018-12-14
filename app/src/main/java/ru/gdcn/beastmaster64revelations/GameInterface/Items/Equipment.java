package ru.gdcn.beastmaster64revelations.GameInterface.Items;

import ru.gdcn.beastmaster64revelations.GameInterface.Action.ActionContainer;

public interface Equipment extends GameItem {

    /** Интерфейс экипировки, носимой Персонажами. Может относится
     * к одному из типов экипируемых предметов (EquipmentType). В зависимости
     * от типа, очки экипировки (Points) означают определённое свойство экипировки.
     * Например:
     * Если тип = ARMOR
     * то Points воспринимается как броня
     * Если тип = WEAPON
     * то Points воспринимается как урон
     * Данная система придумана для лёгкого добавления новых типов экипировки.
     *
     * В перспективе, если у экипировки есть дополнительные очки (extraPoints),
     * они увеличивают характеристику экипировки (суммируются с basePoints).
     * fullDurability - количество боёв, которое должно пройти, чтобы extraPoints
     * понизились на единицу.
     * currentDurability - оставшаяся прочность. Когда достигает нуля, от
     * дополнительных очков отнимается единица, а currentDurability восстанавливается
     * до значения fullDurability.
     *
     * fullDurability и basePoints никогда не меняются.**/


    /** Конструктор для создания экипировки без дополнительных очков. **/

//    public void Equipment(EquipmentType type,
//                          Integer basePoint);


    /** Конструктор для создания экипировки без дополнительных очков и с
     * явным указанием имени. (например для создания уникального оружия) **/

//    public void Equipment(EquipmentType type,
//                          Integer basePoint,
//                          String name);


    /** Конструктор для создания экипировки с дополнительными очками и прочностью **/

//    public void Equipment(EquipmentType type,
//                          Integer basePoint,
//                          Integer extraPoints,
//                          Integer durability);

    /** Конструктор для создания экипировки с дополнительными очками и прочностью, с явным
     * указанием имени. **/

//    public void Equipment(EquipmentType type,
//                          Integer basePoint,
//                          Integer extraPoints,
//                          Integer durability);

    /**
     * Возвращает тип экипировки.
     * Например ARMOR или WEAPON
     **/

    public EquipmentType getType();

    /**
     * Возвращает действия, которые предоставляет экипировка при ношении
     * **/

    public ActionContainer getActions();


    /**
     * Возвращает базовые очки
     **/

    public Integer getBasePoints();

    /**
     * Возвращает дополнительные очки
     **/

    public Integer getExtraPoints();

    /**
     * Возвращает текущее полное количество очков (типа мощность экипировки).
     * По сути должен возвращать сумму базовых и дополнительных очков.
     **/

    public Integer getPoints();

    /**
     * Возвращает полную прочность экипировки.
     **/

    public Integer getFullDurability();

    /**
     * Возвращает текущую (оставшуюся) прочность
     **/

    public Integer getCurrentDurability();

    /**
     * Вызывается после боя. Отнимает единицу от текущей прочности и при необходимости
     * восстанавливает её до полной прочности, при этом отнимая единицу от дополнительных
     * очков.
     * Возвращает:
     * True - в случае если всё прошло без проблем.
     * False - в исключительных ситуациях.
     **/

    public Boolean afterMove();

}
