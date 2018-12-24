package ru.gdcn.beastmaster64revelations.GameClass.WorldElemets;

import java.util.LinkedList;
import java.util.List;

import ru.gdcn.beastmaster64revelations.GameInterface.World.GameMap;
import ru.gdcn.beastmaster64revelations.GameInterface.World.Location.Location;
import ru.gdcn.beastmaster64revelations.GameInterface.World.Location.LocationType;
import ru.gdcn.beastmaster64revelations.GameInterface.World.MapDirection;
import ru.gdcn.beastmaster64revelations.GameInterface.World.MapPoint;
import ru.gdcn.beastmaster64revelations.GameInterface.World.World;

public class SimpleGameMapClass implements GameMap {

    private Location[][] map;
    private World world;

    public SimpleGameMapClass(World world) {
        this(world, 7, 10);
    }

    public SimpleGameMapClass(World world, int width, int height) {
        map = new Location[height][width];
        this.world = world;
        for (int y = 0; y < map.length; y++){
            for (int x = 0; x < map[0].length; x++){
                map[y][x] = new SimpleLocationClass(new MapPoint(x, y), world);
            }
        }
    }


    @Override
    public Location getLocationAt(MapPoint coordinates) {
        if (coordinates.isWithin(map))
            return map[coordinates.getY()][coordinates.getX()];
        else
            return null;
    }

//    @Override
//    public List<Location> getNeighbours(MapPoint coordinates) {
//        List<Location> neighbours = new LinkedList();
//        MapPoint next;
//        next = coordinates.up();
//        if (next.isWithin(map) && !get(next).getType().equals(LocationType.ROCK))
//            neighbours.add(get(next));
//        next = coordinates.left();
//        if (next.isWithin(map) && !get(next).getType().equals(LocationType.ROCK))
//            neighbours.add(get(next));
//        next = coordinates.right();
//        if (next.isWithin(map) && !get(next).getType().equals(LocationType.ROCK))
//            neighbours.add(get(next));
//        next = coordinates.down();
//        if (next.isWithin(map) && !get(next).getType().equals(LocationType.ROCK))
//            neighbours.add(get(next));
//        return neighbours;
//    }

    @Override
    public Location getNeightbour(MapPoint coordinates, MapDirection direction) {
        if (!coordinates.isWithin(map))
            return null;
        MapPoint neighbourPoint;
        switch (direction){
            case UP:
                neighbourPoint = coordinates.up();
                break;
            case DOWN:
                neighbourPoint = coordinates.down();
                break;
            case LEFT:
                neighbourPoint = coordinates.left();
                break;
            case RIGHT:
                neighbourPoint = coordinates.right();
                break;
            default:
                return null;
        }
        return get(neighbourPoint);
    }

    private Location get(MapPoint point) {
        if (!point.isWithin(map))
            return null;
        return map[point.getY()][point.getX()];
    }

    @Override
    public Integer getWidth() {
        return map[0].length;
    }

    @Override
    public Integer getHeight() {
        return map.length;
    }
}
