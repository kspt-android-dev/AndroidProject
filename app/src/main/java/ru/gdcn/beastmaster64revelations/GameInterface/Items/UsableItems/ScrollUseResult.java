package ru.gdcn.beastmaster64revelations.GameInterface.Items.UsableItems;

public enum ScrollUseResult {

    SUCCESS, MISS, IMMUNE

    /**
     * SUCCESS - Заклинание успешно приминилось
     * MISS - Заклинение не достигло цели
     * IMMUNE - Цель имела имунитет к заклинаниям и никакого эффекта нет (в отличии от MISS свиток не тратится)
     * **/

}
