package ru.gdcn.beastmaster64revelations.GameClass.Characters;

import ru.gdcn.beastmaster64revelations.GameInterface.Character.Character;
import ru.gdcn.beastmaster64revelations.GameInterface.Character.NPC.Player;
import ru.gdcn.beastmaster64revelations.GameInterface.Items.ItemContainer;
import ru.gdcn.beastmaster64revelations.GameInterface.World.Location.Location;

import java.util.ArrayList;
import java.util.List;

public class PlayerClass extends CharacterClass implements Player {

    //TODO Есть геттеры для полей, но нет методов добавления новых данных

    private ArrayList<Character> opponentsKilled;
    private ArrayList<Character> civilsTalked;
    private ArrayList<Character> civilsKilled;
    private ArrayList<Location> locationsTravelled;
    private ArrayList<Location> dungeonsCleared;

    //TODO А когда инициализируются ArrayList'ы? Поля нигде не инициализируются
    public PlayerClass(String name, Location location, Integer strength, Integer agility, Integer intellect, Integer luck) {
        super(name, location, strength, agility, intellect, luck);
    }

    @Override
    public List<Character> getKilledOpponents() {
        return opponentsKilled;
    }

    //TODO Лучше указать что содержит лист
    @Override
    public List getTalkedCivils() {
        return civilsTalked;
    }

    //TODO Лучше указать что содержит лист
    //Почему Civils?
    @Override
    public List getKilledCivils() {
        return civilsKilled;
    }

    //TODO Лучше указать что содержит лист
    @Override
    public List getTravelledLocations() {
        return locationsTravelled;
    }

    //TODO Лучше указать что содержит лист
    @Override
    public List getDungeonsCleared() {
        return dungeonsCleared;
    }

    //TODO У Character уже есть его инвентарь и экипированные предметы
    @Override
    public ItemContainer getShmot() {
        return null;
    }

    @Override
    public Location getPlayerLocation() {
        return currentLocation;
    }
}
