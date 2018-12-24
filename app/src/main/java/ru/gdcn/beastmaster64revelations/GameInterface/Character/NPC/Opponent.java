package ru.gdcn.beastmaster64revelations.GameInterface.Character.NPC;

public interface Opponent extends NPC {

    /** Возвращает тип противника - один из BEAST, BANDIT, KNIGHT, BOSS **/
    public OpponentType getType();

    public Boolean isAgressive();

}
