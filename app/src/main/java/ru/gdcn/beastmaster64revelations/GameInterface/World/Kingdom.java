package ru.gdcn.beastmaster64revelations.GameInterface.World;

import ru.gdcn.beastmaster64revelations.GameInterface.World.Location.Location;

import java.util.List;

public interface Kingdom {

    /** Возвращает название королевства **/
    public String getName();

    /** Возвращает описание королевства **/
    public String getDescription();

    /** Возвращает список локаций в королевстве **/
    public List<Location> getLocations();

    /** Возвращает список важных для лора локаций. Как только игрок
     *  пройдет по каждой из этих локаций, часть лора этого королевства
     *  считается открытой. Сложность следующих королевств снижается **/
    public List<Location> getLoreValuableLocations();

    /** Возвращает true, если игрок находился в данном королевстве **/
    public Boolean isTravelled();

    /** Возвращает ссылку на подземелье данного королевства **/
    public Location getDungeon();

    /** Возвращает список скал, через которые нельзя ходить **/
    public List<Location> getRocks();

    /** Возвращает численное значение сложности данного королевства.
     *  Если лор одного королевства не открыт, сложность других
     *  королевств возрастает и снижается при открытии всех частей лора
     *  данного королевства **/
    public Double getDifficulty();

    /** Возвращает true, если все части лора данного королевства открыты **/
    public Boolean isDeveloped();
}
