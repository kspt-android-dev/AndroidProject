package ru.gdcn.beastmaster64revelations.GameInterface.World;

import ru.gdcn.beastmaster64revelations.GameInterface.World.Location.Location;

import java.io.Serializable;
import java.util.List;

public interface GameMap extends Serializable {

    //public Location[][] getLocationsArray();

    /**
     * Возвращает локацию по заданным координатам
     **/
    public Location getLocationAt(MapPoint coordinates);

    /**
     * Возвращает соседей клетки с заданными координатами
     **/
    public List<Location> getNeighbours(MapPoint coordinates);

    public Location getNeightbour(MapPoint coordinates, MapDirection direction);

    /**
     * Ширина карты
     **/
    public Integer getWidth();

    /**
     * Высота карты
     **/
    public Integer getHeight();

}
