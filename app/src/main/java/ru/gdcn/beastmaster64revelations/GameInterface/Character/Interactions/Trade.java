package ru.gdcn.beastmaster64revelations.GameInterface.Character.Interactions;

import ru.gdcn.beastmaster64revelations.GameInterface.Character.NPC.Player;
import ru.gdcn.beastmaster64revelations.GameInterface.Character.NPC.Trader;
import ru.gdcn.beastmaster64revelations.GameInterface.Items.GameItem;
import ru.gdcn.beastmaster64revelations.GameInterface.Items.ItemContainer;

public interface Trade extends Interaction {

    /**
     * Возвращает торговца
     **/
    public Trader getTrader();

    /**
     * Возвращает игрока
     **/
    public Player getPlayer();

    /**
     * Возвращает список предметов, которые может купить игрок
     **/
    public ItemContainer getItemsToBuy();

    /**
     * Возвращает список предметов, которые игрок может продать
     **/
    public ItemContainer getItemsToSell();

    /**
     * Пытается купить предмет, проверяет количество денег у игрока,
     * проверяет наличие данного предмета у продающего.
     * В случае успеха переносит предмет в инвентарь Игрока, снимает деньги и всё такое.
     * Возвращает:
     * True  - Если никаких проблем не возникло и получилось купить предмет.
     * False - Если у игрока не хватило денег или не получилось перенести предмет из
     * одного контейнера в другой и т.п.
     **/
    public Boolean buyItem(GameItem item);

    /**
     * Аналогично покупке, но проверяет всё наоборот.
     **/
    public Boolean sellItem(GameItem item);

}
