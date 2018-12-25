package ru.gdcn.beastmaster64revelations.GameClass.Characters.TestCharacters;

import java.io.Serializable;
import java.util.Random;

import ru.gdcn.beastmaster64revelations.GameClass.Items.Equipment.Weapons.Weapon;
import ru.gdcn.beastmaster64revelations.GameClass.Items.ItemContainerClass;
import ru.gdcn.beastmaster64revelations.GameInterface.Action.ActionContainer;
import ru.gdcn.beastmaster64revelations.GameInterface.Character.Character;
import ru.gdcn.beastmaster64revelations.GameInterface.Character.Effects.Effect;
import ru.gdcn.beastmaster64revelations.GameInterface.Character.Effects.EffectContainer;
import ru.gdcn.beastmaster64revelations.GameInterface.Items.Equipment;
import ru.gdcn.beastmaster64revelations.GameInterface.Items.ItemContainer;
import ru.gdcn.beastmaster64revelations.GameInterface.Items.ItemRarity;
import ru.gdcn.beastmaster64revelations.GameInterface.World.Location.Location;

@SuppressWarnings("WeakerAccess")
public class DummyCharacter implements Character, Serializable {

    protected Integer HP = 100;
    protected String name;
    protected Location currentLocation;
    protected ItemContainer inventory = new ItemContainerClass(100, false);
    protected Weapon weapon = new Weapon(10, ItemRarity.COMMON);

    public DummyCharacter() {
        name = generateName();
    }

    private String generateName() {
        int number = new Random().nextInt(4);
        String name = "Чёрт";
        switch (number){
            case 0:
                name = "Бешеный пёс";
                break;
            case 1:
                name = "Нетрезвый пират";
                break;
            case 2:
                name = "Злой двойник";
                break;
        }
        return name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Integer getHP() {
        return HP;
    }

    @Override
    public Integer getMaxHP() {
        return getStrength() * 10 / getAgility();
    }

    @Override
    public Integer getStrength() {
        return 10;
    }

    @Override
    public Integer getAgility() {
        return 10;
    }

    @Override
    public Integer getBasicAttack() {
        return getAgility()/2 + getStrength();
    }

    @Override
    public Integer getIntellect() {
        return 10;
    }

    @Override
    public Integer getLuck() {
        return 10;
    }

    @Override
    public ItemContainer getItemContainer() {
        return inventory;
    }

    @Override
    public Equipment getCurrentWeapon() {
        return weapon;
    }

    @Override
    public Equipment getCurrentArmor() {
        return null;
    }

    @Override
    public Integer getFullAttack() {
        return getBasicAttack();
    }

    @Override
    public ActionContainer getActionContainer() {
        return null;
    }

    @Override
    public Integer getMoney() {
        return 0;
    }

    @Override
    public EffectContainer getEffects() {
        return null;
    }

    @Override
    public Boolean gainLuck(Integer points) {
        return false;
    }

    @Override
    public Boolean gainStrength(Integer points) {
        return false;
    }

    @Override
    public Boolean gainAgility(Integer points) {
        return false;
    }

    @Override
    public Boolean gainIntellect(Integer points) {
        return false;
    }

    @Override
    public Boolean reduceLuck(Integer points) {
        return false;
    }

    @Override
    public Boolean reduceStrength(Integer points) {
        return false;
    }

    @Override
    public Boolean reduceAgility(Integer points) {
        return false;
    }

    @Override
    public Boolean reduceIntellect(Integer points) {
        return false;
    }

    @Override
    public Boolean dealHeal(Integer points) {
        HP+=points;
        return true;
    }

    @Override
    public Boolean dealPhysicalDamage(Integer points) {
        HP-=points;
        return true;
    }

    @Override
    public Boolean dealMagicalDamage(Integer points) {
        HP-=points;
        return true;
    }

    @Override
    public Boolean equipArmor(Equipment item) {
        return false;
    }

    @Override
    public Boolean equipWeapon(Equipment item) {
        return false;
    }

    @Override
    public Boolean removeArmor() {
        return null;
    }

    @Override
    public Boolean removeWeapon() {
        return null;
    }

    @Override
    public Boolean receiveMoney(Integer points) {
        return false;
    }

    @Override
    public Boolean loseMoney(Integer points) {
        return false;
    }

    @Override
    public Boolean putEffect(Effect effect) {
        return false;
    }

    @Override
    public Location getLocation() {
        return currentLocation;
    }

    @Override
    public Boolean changeLocationTo(Location location) {
        currentLocation = location;
        return true;
    }

    @Override
    public Boolean isDead() {
        return (HP <= 0);
    }

    @Override
    public void kill() {
        HP = 0;
    }
}
