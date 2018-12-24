package ru.gdcn.beastmaster64revelations.GameInterface.Character.NPC;

import ru.gdcn.beastmaster64revelations.GameInterface.Character.Character;
import ru.gdcn.beastmaster64revelations.GameInterface.Character.Interactions.Trade;
import ru.gdcn.beastmaster64revelations.GameInterface.Items.ItemContainer;

import java.util.List;

public interface Trader extends Character {

    /** Возвращает список фраз, используемых Trader в диалоге **/
    public List<String> getPhrases();

    /** Возвращает список предметов, предлагаемых Trader на продажу **/
    public ItemContainer getGoods();

    /** Возвращает взаимодействие торговли **/
    public Trade getTrade();

}
