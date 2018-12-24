package ru.gdcn.beastmaster64revelations.GameInterface.Character.Interactions.Fight;

import ru.gdcn.beastmaster64revelations.GameInterface.Character.Interactions.Interaction;

import java.util.List;
import java.util.Queue;

public interface Fight extends Interaction {

    //TODO

    /**
     * Возвращает всех Персонажей, замешанных во взаимодействии (например торговца и игрока)
     **/
    public List<Character> getCharacters();

    /**
     * Возвращает всех Персонажей "левой" стороны
     * Например во время боя "слева" находится Игрок, а "справа" противники.
     * В данном случае данный метод вернёт список содержащий игрока.
     **/
    public List<Character> getLeftSide();

    /**
     * Аналогичен предыдущему методу, но возвращает противников Персонажа
     **/
    public List<Character> getRightSide();

    /**
     * Возвращает персонажа, который сейчас ходит
     **/
    public Character getCurrentTurn();

    /**
     * Возвращает очередь ходов
     **/
    public Queue<Character> getTurnQueue();

    /**
     * Совершает ход и отдаёт очередь к следующему персонажу.
     **/
    public Boolean makeMove(FightTurn turn);

}
