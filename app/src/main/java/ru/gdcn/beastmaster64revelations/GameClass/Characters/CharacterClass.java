package ru.gdcn.beastmaster64revelations.GameClass.Characters;

import java.io.Serializable;

import ru.gdcn.beastmaster64revelations.GameClass.Constants.Integers;
import ru.gdcn.beastmaster64revelations.GameClass.Items.Equipment.Weapons.Weapon;
import ru.gdcn.beastmaster64revelations.GameClass.Items.ItemContainerClass;
import ru.gdcn.beastmaster64revelations.GameClass.Utilities.Utilities;
import ru.gdcn.beastmaster64revelations.GameInterface.Action.ActionContainer;
import ru.gdcn.beastmaster64revelations.GameInterface.Character.Character;
import ru.gdcn.beastmaster64revelations.GameInterface.Character.Effects.Effect;
import ru.gdcn.beastmaster64revelations.GameInterface.Character.Effects.EffectContainer;
import ru.gdcn.beastmaster64revelations.GameInterface.Items.Equipment;
import ru.gdcn.beastmaster64revelations.GameInterface.Items.EquipmentType;
import ru.gdcn.beastmaster64revelations.GameInterface.Items.ItemContainer;
import ru.gdcn.beastmaster64revelations.GameInterface.World.Location.Location;

public class CharacterClass implements Character, Serializable {

    protected Integer HP;
    protected Integer maxHP;
    protected Location currentLocation;
    protected String name;
    protected Integer strength;
    protected Integer agility;
    protected Integer intellect;
    protected Integer luck;
    protected Integer money;
    protected ItemContainerClass equipment;
    protected Equipment currentWeapon;
    protected Equipment currentArmor;

    public CharacterClass(String name,
                          Location location,
                          Integer strength,
                          Integer agility,
                          Integer intellect,
                          Integer luck) {
        this.maxHP = strength * 10 / agility;
        this.HP = maxHP;
        this.name = name;
        this.currentLocation = location;
        this.strength = strength;
        this.agility = agility;
        this.intellect = intellect;
        this.luck = luck;
        this.money = 0;
    }

    public Integer getMaxHP(){
        return maxHP;
    };

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Integer getHP() {
        return HP;
    }

    @Override
    public Integer getStrength() {
        return strength;
    }

    @Override
    public Integer getAgility() {
        return agility;
    }

    @Override
    public Integer getBasicAttack() {
        return getAgility() / 2 + getStrength();
    }

    @Override
    public Integer getIntellect() {
        return intellect;
    }

    @Override
    public Integer getLuck() {
        return luck;
    }

    @Override
    public ItemContainer getItemContainer() {
        return equipment;
    }

    @Override
    public Equipment getCurrentWeapon() {
        return currentWeapon;
    }

    @Override
    public Equipment getCurrentArmor() {
        return currentArmor;
    }

    @Override
    public Integer getFullAttack() {
        //return getBasicAttack() + currentWeapon.getBasePoints() + currentWeapon.getExtraPoints();
        //TODO
        return strength+agility;
    }

    @Override
    public ActionContainer getActionContainer() {
        return null;
    }//TODO

    @Override
    public Integer getMoney() {
        return money;
    }

    @Override
    public EffectContainer getEffects() {
        return null;
    }//TODO

    @Override
    public Boolean gainLuck(Integer points) {
        if (points == null || this.isDead() || points < 0)
            return false;
        luck += points;
        return true;
    }

    @Override
    public Boolean gainStrength(Integer points) {
        if (points == null || this.isDead() || points < 0)
            return false;
        strength += points;
        return true;
    }

    @Override
    public Boolean gainAgility(Integer points) {
        if (points == null || this.isDead() || points < 0)
            return false;
        agility += points;
        return true;
    }

    @Override
    public Boolean gainIntellect(Integer points) {
        if (points == null || this.isDead() || points < 0)
            return false;
        intellect += points;
        return true;
    }

    @Override
    public Boolean reduceLuck(Integer points) {
        if (points == null || this.isDead() || points < 0 || luck - points <= 0)
            return false;
        luck -= points;
        return true;
    }

    @Override
    public Boolean reduceStrength(Integer points) {
        if (points == null || this.isDead() || points < 0 || strength - points <= 0)
            return false;
        strength -= points;
        return true;
    }

    @Override
    public Boolean reduceAgility(Integer points) {
        if (points == null || this.isDead() || points < 0 || agility - points <= 0)
            return false;
        agility -= points;
        return true;
    }

    @Override
    public Boolean reduceIntellect(Integer points) {
        if (points == null || this.isDead() || points < 0 || intellect - points <= 0)
            return false;
        intellect -= points;
        return true;
    }

    @Override
    public Boolean dealHeal(Integer points) {
        if (points == null || this.isDead() || points < 0 || HP + points >= maxHP)
            return false;
        HP += points;
        return true;
    }

    @Override
    public Boolean dealPhysicalDamage(Integer points) {
        if (points == null || this.isDead() || points < 0)
            return false;
        int tempPoints;
        if (currentArmor == null)
            tempPoints = points;
        else
            tempPoints = points / (points + currentArmor.getBasePoints());
        HP -= tempPoints;
        if (HP < 0) {
            System.out.println("ЛОЛЕКС");
            HP = 0;
        }
        return true;
    }

    @Override
    public Boolean dealMagicalDamage(Integer points) {
        if (points == null || this.isDead() || points < 0)
            return false;
        HP -= points;
        if (HP < 0)
            HP = 0;
        return true;
    }

    @Override
    public Boolean equipArmor(Equipment item) {
        if (item == null || this.isDead() || item.getType() != EquipmentType.ARMOR)
            return false;
        currentArmor = item;
        return true;
    }

    @Override
    public Boolean equipWeapon(Equipment item) {
        if (item == null || this.isDead() || item.getType() != EquipmentType.WEAPON)
            return false;
        currentWeapon = item;
        return true;
    }

    @Override
    public Boolean removeArmor() {
        if (currentArmor == null)
            return false;
        currentArmor = null;
        return true;
    }

    @Override
    public Boolean removeWeapon() {
        if (currentWeapon == null)
            return false;
        currentWeapon = null;
        return true;
    }

    @Override
    public Boolean receiveMoney(Integer points) {
        if (points == null || points < 0)
            return false;
        money += points;
        return true;
    }

    @Override
    public Boolean loseMoney(Integer points) {
        if (points == null || money < points || points < 0)
            return false;
        money -= points;
        return true;
    }

    @Override
    public Boolean putEffect(Effect effect) {
        return false;
    }//TODO

    @Override
    public Location getLocation() {
        return currentLocation;
    }

    @Override
    public Boolean changeLocationTo(Location location) {
        if (location == null)
            return false;
        currentLocation = location;
        return true;
    }
    
    @Override
    public Boolean isDead() {
        return (HP != null && HP <= 0 );
    }
}
