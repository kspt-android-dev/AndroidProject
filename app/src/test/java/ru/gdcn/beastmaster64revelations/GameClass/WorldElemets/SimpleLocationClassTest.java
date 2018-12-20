package ru.gdcn.beastmaster64revelations.GameClass.WorldElemets;

import org.junit.Test;

import java.util.Random;

import ru.gdcn.beastmaster64revelations.GameClass.Characters.PlayerClass;
import ru.gdcn.beastmaster64revelations.GameInterface.Character.NPC.Player;
import ru.gdcn.beastmaster64revelations.GameInterface.World.GameMap;
import ru.gdcn.beastmaster64revelations.GameInterface.World.Location.Location;
import ru.gdcn.beastmaster64revelations.GameInterface.World.MapDirection;
import ru.gdcn.beastmaster64revelations.GameInterface.World.MapPoint;
import ru.gdcn.beastmaster64revelations.GameInterface.World.World;

import static org.junit.Assert.*;

public class SimpleLocationClassTest {

    @Test
    public void getName() {
        Location location = new SimpleLocationClass();
        assertNotNull(location.getName());
        assertTrue(location.getName().length() > 0);
    }

    @Test
    public void getDescription() {
        Location location = new SimpleLocationClass();
        assertNotNull(location.getDescription());
        assertTrue(location.getDescription().length() > 0);
    }

    @Test
    public void getDifficulty() {
        Location location = new SimpleLocationClass();
        assertNotNull(location.getDifficulty());
        assertTrue(location.getDifficulty() < 3.0);
        assertTrue(location.getDifficulty() > 0);
    }

    @Test
    public void isTravelled() {
        //Not supported in current version of the game
    }

    @Test
    public void getType() {
        Location location = new SimpleLocationClass();
        assertNotNull(location.getType());
    }

    @Test
    public void hasPlayer() {

        Location location = new SimpleLocationClass();
        assertNull(location.getPlayer());
        assertFalse(location.hasPlayer());

        Player player = new PlayerClass("test", location, 5, 5, 5, null, null);
        location.playerCame(player);

        assertTrue(location.hasPlayer());
        assertNotNull(location.getPlayer());

    }

    @Test
    public void getNeightbour() {

        World world = SimpleWorldClass.generateWorld();
        Location location = world.getGameMap().getLocationAt(new MapPoint(0, 0));

        assertNull(location.getNeightbour(MapDirection.UP));
        assertNull(location.getNeightbour(MapDirection.LEFT));
        assertNotNull(location.getNeightbour(MapDirection.RIGHT));
        assertNotNull(location.getNeightbour(MapDirection.DOWN));

    }

    @Test
    public void getWorld() {

        World world = SimpleWorldClass.generateWorld();
        Location location = world.getRandomLocation();

        assertSame(location.getWorld(), world);

    }

    @Test
    public void getCoordinates() {

        World world = SimpleWorldClass.generateWorld();
        GameMap map = world.getGameMap();
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
    public void hasNPC() {

        Location location = new SimpleLocationClass();
        assertNull(location.getNPC());

        Player player = new PlayerClass("test", location, 1, 1, 1, null, null);
        location.playerCame(player);
        assertNotNull(location.getNPC());

    }

    @Test
    public void getNPC() {

        Location location = new SimpleLocationClass();
        assertFalse(location.hasNPC());

        Player player = new PlayerClass("test", location, 1, 1, 1, null, null);
        location.playerCame(player);
        assertTrue(location.hasNPC());

    }

    @Test
    public void playerGone() {

        Location location = new SimpleLocationClass();
        assertFalse(location.hasPlayer());

        Player player = new PlayerClass("test", location, 1, 1, 1, null, null);

        location.playerCame(player);
        assertTrue(location.hasPlayer());
        assertNotNull(location.getPlayer());
        location.playerGone();
        assertNull(location.getPlayer());
        assertFalse(location.hasPlayer());
        location.playerCame(player);
        assertTrue(location.hasPlayer());
        assertNotNull(location.getPlayer());
        location.playerGone();
        assertNull(location.getPlayer());
        assertFalse(location.hasPlayer());

    }

    @Test
    public void playerCame() {
        Location location = new SimpleLocationClass();
        assertNull(location.getPlayer());
        assertFalse(location.hasPlayer());

        Player player = new PlayerClass("test", location, 1, 1, 1, null, null);

        location.playerCame(player);
        assertTrue(location.hasPlayer());
        assertNotNull(location.getPlayer());
        location.playerGone();
        assertNull(location.getPlayer());
        assertFalse(location.hasPlayer());
        location.playerCame(player);
        assertTrue(location.hasPlayer());
        assertNotNull(location.getPlayer());
        location.playerGone();
        assertNull(location.getPlayer());
        assertFalse(location.hasPlayer());
    }

    @Test
    public void getPlayer() {
        Location location = new SimpleLocationClass();
        assertNull(location.getPlayer());

        Player player = new PlayerClass("test", location, 5, 5, 5, null, null);
        location.playerCame(player);

        assertNotNull(location.getPlayer());
        location.playerGone();
        assertNull(location.getPlayer());
    }

}