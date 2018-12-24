package ru.gdcn.beastmaster64revelations.GameClass.WorldElemets;

import java.util.List;
import java.util.Random;

import ru.gdcn.beastmaster64revelations.GameInterface.Character.NPC.Player;
import ru.gdcn.beastmaster64revelations.GameInterface.World.GameMap;
import ru.gdcn.beastmaster64revelations.GameInterface.World.Kingdom;
import ru.gdcn.beastmaster64revelations.GameInterface.World.Location.Location;
import ru.gdcn.beastmaster64revelations.GameInterface.World.MapPoint;
import ru.gdcn.beastmaster64revelations.GameInterface.World.World;

public class SimpleWorldClass implements World {

    GameMap map = new SimpleGameMapClass(this);

    public static World generateWorld() {
        return new SimpleWorldClass();
    }

    @Override
    public List<Kingdom> getKingdoms() {
        return null;
    }

    @Override
    public List<Kingdom> getTravelledKingdoms() {
        return null;
    }

    @Override
    public List<Kingdom> getDevelopedKingdoms() {
        return null;
    }

    @Override
    public GameMap getGameMap() {
        return map;
    }

    @Override
    public String getLore() {
        return "Тестовый мир для теста тестов";
    }

    @Override
    public Player getPlayer() {
        return null;
    }

    @Override
    public Location getRandomLocation() {
        Random random = new Random();
        int x = random.nextInt(map.getWidth());
        int y = random.nextInt(map.getHeight());
        return map.getLocationAt(new MapPoint(x, y));
    }

}
