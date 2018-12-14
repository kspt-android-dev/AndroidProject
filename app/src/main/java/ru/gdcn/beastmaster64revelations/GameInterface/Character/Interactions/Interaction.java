package ru.gdcn.beastmaster64revelations.GameInterface.Character.Interactions;

public interface Interaction {

//    /** Возвращает всех Персонажей, замешанных во взаимодействии (например торговца и игрока) **/
//    public List<Characters> getCharacters();
//
//    /** Возвращает всех Персонажей "левой" стороны
//     * Например во время боя "слева" находится Игрок, а "справа" противники.
//     * В данном случае данный метод вернёт список содержащий игрока.**/
//    public List<Characters> getLeftSide();
//
//    /** Аналогичен предыдущему методу, но возвращает противников Персонажа **/
//    public List<Characters> getRightSide();

    /**
     * Возвращает состояние взаимодействия, ничего не меняя в самом взаимодействии
     **/
    public InteractionState getState();

    /**
     * Продвигает взаимодействие на один ход (например ход переходит другому бойцу)
     * При этом возвращается новое состояние взаимодействия.
     **/
    public InteractionState proceed();

    /**
     * Закаончивает взаимодействие. Возвращаеть False, когда его нельзя закончить (например в бою)
     **/
    public Boolean quitInteraction();
}
