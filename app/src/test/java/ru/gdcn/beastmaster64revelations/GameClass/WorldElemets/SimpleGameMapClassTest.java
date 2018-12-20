package ru.gdcn.beastmaster64revelations.GameClass.WorldElemets;

import org.junit.Test;

import java.util.Random;

import ru.gdcn.beastmaster64revelations.GameInterface.World.GameMap;
import ru.gdcn.beastmaster64revelations.GameInterface.World.Location.Location;
import ru.gdcn.beastmaster64revelations.GameInterface.World.MapDirection;
import ru.gdcn.beastmaster64revelations.GameInterface.World.MapPoint;
import ru.gdcn.beastmaster64revelations.GameInterface.World.World;

import static org.junit.Assert.*;

public class SimpleGameMapClassTest {

    World world = new SimpleWorldClass();

    @Test
    public void getLocationAt() {

        GameMap map = new SimpleGameMapClass(world, 10, 10);
        Random random = new Random();

        int x, y;

        for (int i = 0; i < 1000; i++) {
            x = random.nextInt(map.getWidth());
            y = random.nextInt(map.getHeight());
            MapPoint point = new MapPoint(x, y);
            Location location = map.getLocationAt(point);

            assertNotNull(location);
            assertEquals(point, location.getCoordinates());
        }
    }

    @Test
    public void getNeighbours() {
        //Not used in this version
    }

    @Test
    public void getNeightbour() {

        GameMap map = new SimpleGameMapClass(world, 3, 3);
        Location location = map.getLocationAt(new MapPoint(0, 0));

        assertNull(map.getNeightbour(location.getCoordinates(), MapDirection.UP));
        assertNull(map.getNeightbour(location.getCoordinates(), MapDirection.LEFT));
        assertNotNull(map.getNeightbour(location.getCoordinates(), MapDirection.RIGHT));
        assertNotNull(map.getNeightbour(location.getCoordinates(), MapDirection.DOWN));

        location = map.getLocationAt(new MapPoint(1,1));

        assertNotNull(map.getNeightbour(location.getCoordinates(), MapDirection.UP));
        assertNotNull(map.getNeightbour(location.getCoordinates(), MapDirection.LEFT));
        assertNotNull(map.getNeightbour(location.getCoordinates(), MapDirection.RIGHT));
        assertNotNull(map.getNeightbour(location.getCoordinates(), MapDirection.DOWN));

        location = map.getLocationAt(new MapPoint(2,2));

        assertNotNull(map.getNeightbour(location.getCoordinates(), MapDirection.UP));
        assertNotNull(map.getNeightbour(location.getCoordinates(), MapDirection.LEFT));
        assertNull(map.getNeightbour(location.getCoordinates(), MapDirection.RIGHT));
        assertNull(map.getNeightbour(location.getCoordinates(), MapDirection.DOWN));


    }

}