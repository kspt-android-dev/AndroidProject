package ru.gdcn.beastmaster64revelations.GameClass.WorldElemets;

import org.junit.Test;

import ru.gdcn.beastmaster64revelations.GameInterface.World.GameMap;
import ru.gdcn.beastmaster64revelations.GameInterface.World.Location.Location;
import ru.gdcn.beastmaster64revelations.GameInterface.World.MapPoint;
import ru.gdcn.beastmaster64revelations.GameInterface.World.World;

import static org.junit.Assert.*;

public class SimpleWorldClassTest {

    @Test
    public void generateWorld() {

        World world = SimpleWorldClass.generateWorld();

        assertNotNull(world);
        assertNotNull(world.getGameMap());
        assertNull(world.getPlayer());
        assertNotNull(world.getRandomLocation());

        Location random = world.getRandomLocation();

        Location same = world.getGameMap().getLocationAt(random.getCoordinates());
        assertNotNull(same);
        assertSame(same, random);
        assertSame(random.getWorld(), world);

    }

    @Test
    public void getGameMap() {

        World world = new SimpleWorldClass();
        GameMap map = world.getGameMap();

        assertNotNull(map);
        assertSame(map.getLocationAt(new MapPoint(0, 0)).getWorld(), world);

    }

    @Test
    public void getRandomLocation() {

        World world = new SimpleWorldClass();
        Location location = world.getRandomLocation();

        assertNotNull(location);
        assertSame(location.getWorld(), world);

    }

}