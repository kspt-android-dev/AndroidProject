package ru.gdcn.beastmaster64revelations.GameClass.WorldElemets;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ru.gdcn.beastmaster64revelations.GameClass.Characters.TestCharacters.DummyEnemy;
import ru.gdcn.beastmaster64revelations.GameInterface.Character.NPC.NPC;
import ru.gdcn.beastmaster64revelations.GameInterface.Character.NPC.Player;
import ru.gdcn.beastmaster64revelations.GameInterface.World.Location.Location;
import ru.gdcn.beastmaster64revelations.GameInterface.World.Location.LocationType;
import ru.gdcn.beastmaster64revelations.GameInterface.World.Location.Treasure;
import ru.gdcn.beastmaster64revelations.GameInterface.World.MapDirection;
import ru.gdcn.beastmaster64revelations.GameInterface.World.MapPoint;
import ru.gdcn.beastmaster64revelations.GameInterface.World.World;

public class SimpleLocationClass implements Location, Serializable {

    private Boolean hasPlayer;
    private MapPoint coordinates;
    private NPC someSickDude;
    private String description;
    private LocationType type;
    private String name;
    private Player player;
    private Double difficulty = 0.1 + new Random().nextDouble() * 2.0;

    private World world;

    public SimpleLocationClass(MapPoint coordinates, World world){
        this.world = world;
        this.coordinates = coordinates;
        name = randomLocationName();
        hasPlayer = false;
    }

    public SimpleLocationClass(){
        this.coordinates = null;
        name = randomLocationName();
        hasPlayer = false;
    }

    @Override
    public String getName() {
        return name;
    }

    private String randomLocationName() {
        int number = new Random().nextInt(4);
        String name = "Тёмный лес";
        type = LocationType.FOREST;
        switch (number){
            case 0:
                name = "Болото";
                type = LocationType.FOREST;
                break;
            case 1:
                name = "Мрачное ущелье";
                type = LocationType.ROCK;
                break;
            case 2:
                name = "Пиратское побережье";
                type = LocationType.FLATLAND;
                break;
        }
        return name;
    }

    @Override
    public String getDescription() {
        if (description == null)
            generateDescription();
        return description;
    }

    private void generateDescription() {
        int number = new Random().nextInt(4);
        String name = "Неприятное место и здесь воняет. Очень хотелось бы уйти отсюда поскорее, если только тут нет сокровища. Нужно поискать.";
        switch (number){
            case 0:
                name = "Здесь весьма красиво... на удивление! Возможно стоит остаться здесь на некоторе время, если здесь безопасно";
                break;
            case 1:
                name = "Это место выглядит обычно, но по неизвестным причинам вы испытываете глубинный ужас и страх просто находясь здесь. Вряд ли вам светит что-то хорошее, если задержитесь надолго.";
                break;
            case 2:
                name = "Ничего необычного. Совершенно обычное место с обычными вещами вокруг. Самая обычная горящая машина с самым обычным говорящим медведем за рулём. Вам скучно.";
                break;
        }
        description = name;
    }

    @Override
    public Boolean valueForLore() {
        return false;
    }

    @Override
    public Double getDifficulty() {
        return difficulty;
    }

    @Override
    public List<Location> getLinksToOtherLocations() {
        return new ArrayList<>();
    }

    @Override
    public Location getEnterToDungeon() {
        return null;
    }

    @Override
    public Location getEnterToAnotherKingdom() {
        return null;
    }

    @Override
    public Boolean isTravelled() {
        return false;
    }

    @Override
    public Boolean hasTreasure() {
        return false;
    }

    @Override
    public Treasure getTreasure() {
        return null;
    }

    @Override
    public LocationType getType() {
        return type;
    }

    @Override
    public Boolean hasPlayer() {
        return hasPlayer;
    }

    @Override
    public Location getNeightbour(MapDirection direction) {
        if (world == null)
            return null;
        return world.getGameMap().getNeightbour(coordinates, direction);
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public MapPoint getCoordinates() {
        return coordinates;
    }

    @Override
    public Boolean isCivil() {
        return false;
    }

    @Override
    public Boolean hasMonument() {
        return false;
    }

    @Override
    public Boolean hasNPC() {
        return (someSickDude != null);
    }

    @Override
    public NPC getNPC() {
        return someSickDude;
    }

    @Override
    public void playerGone() {
        player = null;
        hasPlayer = false;
    }

    @Override
    public void playerCame(Player player) {
        this.player = player;
        if (this.someSickDude == null)
            this.someSickDude = generateEnemy(player, difficulty);
        hasPlayer = true;
    }

    private NPC generateEnemy(Player player, Double difficulty) {
        return DummyEnemy.generateEnemy(player, difficulty);
    }

    @Override
    public Player getPlayer(){
        return player;
    }

}
