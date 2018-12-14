package ru.gdcn.beastmaster64revelations.GameInterface.World.Location;

import ru.gdcn.beastmaster64revelations.GameInterface.Character.NPC.NPC;
import ru.gdcn.beastmaster64revelations.GameInterface.World.MapPoint;

import java.util.List;

public interface Location {

    /**
     * Возвращает имя локации
     **/
    public String getName();

    /**
     * Возвращает описание локации
     **/
    public String getDescription();

    /**
     * Возвращает true, если данная локация важна для лора. Предполагается,
     * что нельзя перейти в следующее королевство, не изучив все локации,
     * важные для лора во всех предыдущих королевствах
     **/
    public Boolean valueForLore();

    /**
     * Возвращает численное значение сложности локации
     **/
    public Double getDifficulty();

    /**
     * Возвращает список локаций, с которыми связана данная локация.
     * Предполагается использование структуры связного списка, в котором каждая локация
     * имеет ссылки на все локации, в которые можно перейти
     **/
    public List<Location> getLinksToOtherLocations();

    /**
     * Возвращает ссылку на локацию, являющуюся первой локацией в подземелье.
     * Возвращает ROCK, если в данной локации нет входа в подземелье
     **/
    public Location getEnterToDungeon();

    /**
     * Возвращает ссылку на локацию, в которую можно перейти из текущей и она при этом является
     * локацией другого королевства
     **/
    public Location getEnterToAnotherKingdom();

    /**
     * Возвращает true, если игрок уже находился в этой локации
     **/
    public Boolean isTravelled();

    //TODO
    public Boolean hasTreasure();

    /** Возвращает сокровище, находящееся в локации. Если оно уже было собрано
     *  игроком, возвращает NULL
     **/
    //TODO
    public Treasure getTreasure();

    /**
     * Возвращает один из типов локации: DUNGEON, FOREST FLATLAND или ROCK
     **/
    public LocationType getType();

    /**
     * Возвращает true, если в локации находится игрок
     **/
    public Boolean hasPlayer();

    /**
     * Возвращает координаты данной локации ( для представления координат
     * используется вспомогательный класс MapPoint)
     **/
    public MapPoint getCoordinates();

    /** Возвращает true, если локация является мирной (т.е. в ней нет противника) **/
    public Boolean isCivil();

    /** Возвращает true, если в локации находится памятник. Кроме этого, при наличии
     *  памятника локация автоматически становится значимой для лора **/
    public Boolean hasMonument();

    //TODO
    public Boolean hasNPC();
    //TODO
    public NPC getNPC();
    //TODO
    public void playerGone();
    //TODO
    public void playerCame();

}
