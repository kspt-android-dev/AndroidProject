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
import ru.gdcn.beastmaster64revelations.GameInterface.World.MapPoint;

public class SimpleLocationClass implements Location, Serializable {

    Boolean hasPlayer;
    MapPoint coordinates;
    NPC someSickFuck;
    String description;
    LocationType type;
    String name;
    Player player;

    public SimpleLocationClass(MapPoint coordinates){
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
                name = "Ничего не обычного. Совершенно обычное место с обычными вещами вокруг. Самая обычная горящая машина с самым обычным говорящим медведем за рулём. Вам скучно.";
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
        return 1.0;
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
        return (someSickFuck != null);
    }

    @Override
    public NPC getNPC() {
        return someSickFuck;
    }

    @Override
    public void playerGone() {
        player = null;
        hasPlayer = false;
    }

    @Override
    public void playerCame(Player player) {
        this.player = player;
        this.someSickFuck = generateEnemy();
        hasPlayer = true;
    }

    private NPC generateEnemy() {
        return new DummyEnemy(player);
    }

    public Player getPlayer(){
        return player;
    }

}
