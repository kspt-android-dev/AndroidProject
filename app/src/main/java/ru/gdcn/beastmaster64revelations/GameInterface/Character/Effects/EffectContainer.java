package ru.gdcn.beastmaster64revelations.GameInterface.Character.Effects;

import java.util.List;

public interface EffectContainer {

    /** Контейнер эффектов существует для удобного хранения эффектов, "висящих" на
     * одном Персонаже. **/

    /**
     * Возвращает список всех действующих эффектов
     **/
    public List<Effect> getEffects();

    /**
     * Обновляет все эффекты из списка. (вызывает у каждого эффекта метод update, см. "Effect")
     * Возвращает false в исключительных ситуациях
     **/
    public Boolean updateAll();

    /**
     * Добавляет эффект в список
     * Возвращает:
     * True  - если получилось добавить эффект
     * False - если по каким-то причинам не получилось добавить эффект (например, он уже висит на Персонаже)
     **/
    public Boolean addEffect(Effect effect);

    /**
     * Удаляет эффект из списка
     * Возвращает:
     * True  - если получилось удалить эффект из списка
     * False - если эффект отсутствовал в списке
     **/
    public Boolean removeEffect(Effect effect);

}
