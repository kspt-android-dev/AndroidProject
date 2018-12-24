package ru.gdcn.beastmaster64revelations.GameInterface.World.Location;

public interface Treasure {

    /**
     * Сокровище появляется во всех/почти во всех локациях. Игрок может забрать его, только если
     * в локации нет Противника либо если Противник уже убит.
     * Сокровище - основной способ получения денег в игре.
     * **/

    /**
     * Название сокровища
     **/
    public String getName();

    /**
     * Описание сокровища
     **/
    public String getDescription();

    /**
     * Стоимость сокровища, которая должна начислится игроку, когда он его заберёт
     **/
    public Integer getCost();

}
