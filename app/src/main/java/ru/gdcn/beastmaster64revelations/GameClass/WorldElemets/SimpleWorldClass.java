package ru.gdcn.beastmaster64revelations.GameClass.WorldElemets;

import java.util.List;

import ru.gdcn.beastmaster64revelations.GameInterface.Character.NPC.Player;
import ru.gdcn.beastmaster64revelations.GameInterface.World.GameMap;
import ru.gdcn.beastmaster64revelations.GameInterface.World.Kingdom;
import ru.gdcn.beastmaster64revelations.GameInterface.World.World;

public class SimpleWorldClass implements World {

    GameMap map = new SimpleGameMapClass();

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

}
