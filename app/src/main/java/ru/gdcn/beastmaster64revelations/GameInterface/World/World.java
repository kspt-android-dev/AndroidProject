package ru.gdcn.beastmaster64revelations.GameInterface.World;

import ru.gdcn.beastmaster64revelations.GameInterface.Character.NPC.Player;
import ru.gdcn.beastmaster64revelations.GameInterface.World.Location.Location;

import java.io.Serializable;
import java.util.List;

public interface World extends Serializable {

    /** Возвращает список королевств **/
    public List<Kingdom> getKingdoms();

    /** Возвращает список королевств, которые посетил игрок.
     *  Если таких нет, возвращает NULL **/
    public List<Kingdom> getTravelledKingdoms();

    /** Возвращает список королевств, в которых был открыт лор.
     *  Если таких нет, возвращает NULL **/
    public List<Kingdom> getDevelopedKingdoms();

    /** Возвращает карту мира **/
    public GameMap getGameMap();

    /** Возвращает лор мира, доступный игроку в данный момент игры **/
    public String getLore();

    /** Возвращает ссылку на игрока **/
    public Player getPlayer();

    Location getRandomLocation();
}
