package ru.gdcn.beastmaster64revelations.GameInterface.Items;

import java.util.List;

public interface ItemContainer {

    /**
     * Возвращает общую вместительность контейнера
     **/
    public Integer getCapacity();

    /**
     * Возвращает текущую занятость контейнера
     **/
    public Integer getCurrentFill();

    /**
     * Возвращает количество оставшегося места в контейнере
     * (capacity - currentFill)
     **/
    public Integer getRemainingCapacity();

    /**
     * Возвращает список всех предметов в контейнере
     **/
    public List<GameItem> getItems();

    /**
     * Метод добавляет указанный предмет в контейнер.
     * Возвращает:
     * True  - если в контейнере хватило места, предмет добавится в список.
     * False - если в контейнере не хватило места, предмет не добавится.
     **/
    public Boolean putItem(GameItem item);

    /**
     * Метод пытается взять указанный предмет из контейнера.
     * Возвращает:
     * True  - если предмет получилоь взять, предмет удаляется из списка.
     * False - если предмет не получилось взять (или если его там не оказалось),
     * предмет остаётся, если он был там изначально. Такая ситуация может
     * возникнуть в случаях:
     * - если предмет отсутствовал в контейнере
     * - если контейнер является статическим (Static)
     * - если сам предмет является неперемещаемым (isTransferable = false)
     **/
    public Boolean takeItem(GameItem item);

    /**
     * Данный метод задаёт контейнеру указанную ёмкость.
     * Возвращает:
     * True  - если это удалось
     * False - если у данного контейнера по каким-то причинам нельзя
     * изменить ёмкость
     **/
    public Boolean setCapacity(Integer newCapacity);

    /**
     * Метод добавляет к ёмкости рюкзака указанную ёмкость.
     * Таким образом capacity = capacity + capacityExp
     **/
    public Boolean expandCapacity(Integer capacityExp);

    /**
     * Метод возвращает из контейнера дроп (Например, при убийстве NPC).
     * При этом возвращаются только те вещи, которые могут дропнуться.
     * Также часть вещей с определённым шансом остаётся в контейнере (это
     * зависит от указанной удачи)
     **/
    public List<GameItem> getDrop(Integer luck);

    /**
     * Возвращает список предметов находящихся в контейнере, которые могут
     * быть задействованы в торговле.
     **/
    public List<GameItem> getSellable();

    /**
     * Если True, то никаких изменений в данном инвентаре произведено не моет быть.
     **/
    public Boolean isStatic();

}
