package ru.gdcn.beastmaster64revelations.GameInterface.Character;

import java.io.Serializable;

import ru.gdcn.beastmaster64revelations.GameInterface.Character.Effects.Effect;
import ru.gdcn.beastmaster64revelations.GameInterface.Character.Effects.EffectContainer;
import ru.gdcn.beastmaster64revelations.GameInterface.Action.ActionContainer;
import ru.gdcn.beastmaster64revelations.GameInterface.Items.Equipment;
import ru.gdcn.beastmaster64revelations.GameInterface.Items.ItemContainer;
import ru.gdcn.beastmaster64revelations.GameInterface.World.Location.Location;

public interface Character extends Serializable {

    /** ==============================================
     *  Методы для получения базовых свойств персонажа
     *  ============================================== **/

    /** Возвращает имя персонажа **/
    public String getName();

    /** Возвращает количество очков здоровья персонажа **/
    public Integer getHP();

    /** Возвращает количество очков здоровья персонажа **/
    public Integer getMaxHP();

    /** Вовзращает количество очков силы персонажа **/
    public Integer getStrength();

    /** Возвращает количество очков ловкости персонажа **/
    public Integer getAgility();

    /** Вовзращает количество базовых очков атаки персонажа (без грейдов) **/
    public Integer getBasicAttack();

    /** Возвращает количество очков интеллекта персонажа **/
    public Integer getIntellect();

    /** Возвращает количество очков удачи персонажа **/
    public Integer getLuck();

    /** =====================================================
     *  Методы для получения дополнительных свойств персонажа
     *  ===================================================== **/

    /** Возвращает инвентарь персонажа **/
    public ItemContainer getItemContainer();

    //TODO
    /** Возвращает текущее оружие персонажа **/
    public Equipment getCurrentWeapon();

    //TODO
    /** Возвращает текущую броню персонажа **/
    public Equipment getCurrentArmor();

    /** Вовзращает количество очков атаки персонажа с учетом всех шмоток **/
    public Integer getFullAttack();

    /** Возвращает контейнер действий персонажа **/
    public ActionContainer getActionContainer();

    /** Возвращает количество денег в кошельке у персонажа **/
    public Integer getMoney();

    /** Возвращает все эффекты, висящие в данный момент на персонаже **/
    public EffectContainer getEffects();

    /** ===============================================
     *  Методы для обновления базовых свойств персонажа
     *  =============================================== **/

    /** NOTE: все методы этого раздела должны принимать
     *  на вход значения определенного диапазона (Например,
     *  нельзя увеличить показатель удачи с помощью метода
     *  gainLuck() на отрицательную величину. Подобные ситуации
     *  во всех нижеперечисленных методах считаются исключительными
     *  и метод вернет значение false **/

    /** Увеличивает показатель удачи персонажа на величину points
     *  Возвращает false в исключительных случаях **/
    public Boolean gainLuck(Integer points);

    /** Увеличивает показатель силы персонажа на величину points
     *  Возвращает false в исключительных случаях **/
    public Boolean gainStrength(Integer points);

    /** Увеличивает показатель ловкости персонажа на величину points
     *  Возвращает false в исключительных случаях **/
    public Boolean gainAgility(Integer points);

    /** Увеличивает показатель интеллекта персонажа на величину points
     *  Возвращает false в исключительных случаях **/
    public Boolean gainIntellect(Integer points);

    /** Уменьшает показатель удачи персонажа на величину points
     *  Возвращает false в исключительных случаях **/
    public Boolean reduceLuck(Integer points);

    /** Уменьшает показатель силы персонажа на величину points
     *  Возвращает false в исключительных случаях **/
    public Boolean reduceStrength(Integer points);

    /** Уменьшает показатель ловкости персонажа на величину points
     *  Возвращает false в исключительных случаях **/
    public Boolean reduceAgility(Integer points);

    /** Уменьшает показатель интеллекта персонажа на величину points
     *  Возвращает false в исключительных случаях **/
    public Boolean reduceIntellect(Integer points);

    //TODO
    /** Увеличивает показатель здоровья персонажа на величину points
     *  Возвращает false в исключительных случаях **/
    public Boolean dealHeal(Integer points);

    //TODO
    /** Уменьшает показатель здоровья персонажа на нужную величину,
     * рассчитывая прошедший по персонажу, либо возвращает ещё какую-нить
     * хуйню в духе блять УВОРОТ или блять чёнить ещё **/
    public Boolean dealPhysicalDamage(Integer points);

    //TODO
    /** Аналогично физическому урону, но рассчитывает урон немного иначе
     *  Возвращает false в исключительных случаях **/
    public Boolean dealMagicalDamage(Integer points);

    /** ======================================================
     *  Методы для обновления дополнительных свойств персонажа
     *  ====================================================== **/

    /** Метод для экипировки брони. Возвращает false в исключительных случаях **/
    public Boolean equipArmor(Equipment item);

    /** Метод для экипировки оружия. Возвращает false в исключительных случаях **/
    public Boolean equipWeapon(Equipment item);

    /** Снимает экипировку. После снятия предмет перемещается в инвентарь персонажа.
     *  Возвращает false в исключительных случаях **/
    public Boolean removeArmor();

    /** Убирает оружие. После снятия предмет перемещается в инвентарь персонажа.
     *  Возвращает false в исключительных случаях **/
    public Boolean removeWeapon();

    /** Увеличивает количество денег персонажа на величину points
     *  Возвращает false в исключительных случаях **/
    public Boolean receiveMoney(Integer points);

    /** Уменьшает количество денег персонажа на величину points
     *  Возвращает false в исключительных случаях **/
    public Boolean loseMoney(Integer points);

    /** Применяет переданный эффект на персонажа
     *  Возвращает false в исключительных случаях **/
    public Boolean putEffect(Effect effect);

    /** =============================
     *  Прочие вспомогательные методы
     *  ============================= **/

    /** Возвращает локацию, в которой находится персонаж в данный момент **/
    public Location getLocation();

    //TODO
    /** Перемещает персонажа в локацию (задаёт текущую локацию) **/
    public Boolean changeLocationTo(Location location);

    /** Возвращает true, если персонаж мертв **/
    public Boolean isDead();
}
