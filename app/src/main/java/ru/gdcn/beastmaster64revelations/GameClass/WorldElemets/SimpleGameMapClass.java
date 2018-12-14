package ru.gdcn.beastmaster64revelations.GameClass.WorldElemets;

import java.util.LinkedList;
import java.util.List;

import ru.gdcn.beastmaster64revelations.GameInterface.World.GameMap;
import ru.gdcn.beastmaster64revelations.GameInterface.World.Location.Location;
import ru.gdcn.beastmaster64revelations.GameInterface.World.Location.LocationType;
import ru.gdcn.beastmaster64revelations.GameInterface.World.MapPoint;

public class SimpleGameMapClass implements GameMap {

    Location[][] map = new Location[3][3];

    public SimpleGameMapClass(){
        for (int y = 0; y < map.length; y++){
            for (int x = 0; x < map[0].length; x++){
                map[x][y] = new SimpleLocationClass(new MapPoint(x, y));
            }
        }
    }

    @Override
    public Location getLocationAt(MapPoint coordinates) {
        if (coordinates.isWithin(map))
            return map[coordinates.getX()][coordinates.getY()];
        else
            return null;
    }

    @Override
    public List<Location> getNeighbours(MapPoint coordinates) {
        List neighbours = new LinkedList();
        MapPoint next;
        next = coordinates.up();
        if (next.isWithin(map) && !get(next).getType().equals(LocationType.ROCK))
            neighbours.add(get(next));
        next = coordinates.left();
        if (next.isWithin(map) && !get(next).getType().equals(LocationType.ROCK))
            neighbours.add(get(next));
        next = coordinates.right();
        if (next.isWithin(map) && !get(next).getType().equals(LocationType.ROCK))
            neighbours.add(get(next));
        next = coordinates.down();
        if (next.isWithin(map) && !get(next).getType().equals(LocationType.ROCK))
            neighbours.add(get(next));
        return neighbours;
    }

    private Location get(MapPoint point) {
        return map[point.getX()][point.getY()];
    }

    @Override
    public Integer getWidth() {
        return null;
    }

    @Override
    public Integer getHeight() {
        return null;
    }
}
