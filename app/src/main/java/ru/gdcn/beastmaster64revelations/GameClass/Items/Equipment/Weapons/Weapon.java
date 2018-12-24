package ru.gdcn.beastmaster64revelations.GameClass.Items.Equipment.Weapons;

import ru.gdcn.beastmaster64revelations.GameClass.Actions.BasicAttack;
import ru.gdcn.beastmaster64revelations.GameClass.Constants.Integers;
import ru.gdcn.beastmaster64revelations.GameClass.Items.GameItemClass;
import ru.gdcn.beastmaster64revelations.GameClass.Utilities.StringGenerator;
import ru.gdcn.beastmaster64revelations.GameClass.Utilities.Utilities;
import ru.gdcn.beastmaster64revelations.GameInterface.Action.Action;
import ru.gdcn.beastmaster64revelations.GameInterface.Action.ActionContainer;
import ru.gdcn.beastmaster64revelations.GameInterface.Items.Equipment;
import ru.gdcn.beastmaster64revelations.GameInterface.Items.EquipmentType;
import ru.gdcn.beastmaster64revelations.GameInterface.Items.ItemRarity;

import java.util.Random;

public class Weapon extends GameItemClass implements Equipment {

    /**
     * Данная реализация класса "Оружие" не подразумевает наличие extraPoints (см. Equipment)
     * **/

    /**
     * Основная характеристика оружия, содержащая урон
     * **/
    protected Integer baseDamage;

    /**
     * Действия, предоставляемые оружием при ношении
     * **/
    protected ActionContainer actions;

    /**
     * Тип оружия
     * От типа меняется урон и действия оружия
     * **/
    protected WeaponType weaponType;

    /**
     * Полный конструктор с явным указанием всех параметров
     * **/
    public Weapon(String name,
                  ItemRarity rarity,
                  Integer weight,
                  Integer baseCost,
                  Integer baseDamage,
                  ActionContainer actions,
                  WeaponType weaponType) {
        super(name, rarity, weight, baseCost);
        this.baseDamage = baseDamage;
        this.actions = actions;
        this.weaponType = weaponType;
    }

    /**
     * Конструктор с указанием суровости локации и редкости, исходя из которых генерируется предмет
     * **/
    //TODO Зависимость от редкости
    public Weapon(Integer hardnessPoints, ItemRarity rarity) {
        this.rarity = rarity;
        Random randomGenerator = new Random();
        this.generateDamage(randomGenerator, hardnessPoints);
        this.generateCost(randomGenerator);
        this.generateWeight(randomGenerator);
        this.generateType(randomGenerator);
        this.generateActions(randomGenerator);
        this.generateName(randomGenerator);
    }

    /**
     * Генерирует простые действия для некоторых типов оружия
     * **/
    //TODO Вынести констранты
    private void generateActions(Random randomGenerator) {
        Action basicAttack = new BasicAttack("Удар оружием", 1.0);
//        boolean success = actions.addAction(basicAttack);
        System.out.println(basicAttack);
//        if (!success){
//            return;
            //TODO
//        }
        Action secondaryAttack;
        Double randomOffset = (-2 + randomGenerator.nextDouble()*4)/10;
        switch (weaponType){
            case AXE:
                secondaryAttack = new BasicAttack("Сильный удар", 1.8 + randomOffset);
                break;
            case KNIFE:
                secondaryAttack = new BasicAttack("Укол в печень", 1.3 + randomOffset);
                break;
            case SWORD:
                secondaryAttack = new BasicAttack("Рыцарский приём", 1.5 + randomOffset);
                break;
                default:
                    secondaryAttack = new BasicAttack("Арабское сальто", 0.1);
        }
//        success = actions.addAction(secondaryAttack);
        System.out.println(secondaryAttack);
//        if (!success){
//            return;
            //TODO
//        }
    }

    /**
     * Случайным образом выбирает тип оружия
     * **/
    private void generateType(Random randomGenerator) {
        WeaponType[] types = WeaponType.values();
        WeaponType type = (WeaponType) Utilities.getRandomElement(types, randomGenerator);
        this.weaponType = type;
    }

    /**
     * Генерирует случайный урон оружия основываясь на суровости локации
     * **/
    //TODO Вынести констранты
    private void generateDamage(Random randomGenerator, Integer hardnessPoints) {
        this.baseDamage = 5 + randomGenerator.nextInt(4) + hardnessPoints*(5 + randomGenerator.nextInt(4));
    }

    /**
     * Весёлая генерация имени
     * **/
    private void generateName(Random randomGenerator) {
        StringBuilder sb = new StringBuilder();

        if (randomGenerator.nextInt(100) < Integers.WEAPON_NAME_ADJECTIVE_CHANCE)
            sb.append(StringGenerator.weaponAdjective()).append(" ");

        sb.append(StringGenerator.weaponMainName(this.weaponType));
        if (randomGenerator.nextInt(100) < Integers.WEAPON_NAME_POST_CHANCE)
            sb.append(" ").append(StringGenerator.weaponPostName());
        this.name = sb.toString();
    }

    /**
     * Примерная зависимость ценности оружия от урона
     * **/
    //TODO Вынести подсчёт цены в какой-нибудь другой класс
    private void generateCost(Random randomGenerator) {
        Integer cost = 5*baseDamage + randomGenerator.nextInt(baseDamage*3);
        this.baseCost = cost;
    }

    /**
     * Примерная генерация веса (может изменяться в зависимости от типа оружия)
     **/
    //TODO Вынести констранты
    private void generateWeight(Random randomGenerator) {
        Integer weight = 3 + randomGenerator.nextInt(3);
        this.weight = weight;
    }

    @Override
    public EquipmentType getType() {
        return EquipmentType.WEAPON;
    }

    @Override
    public ActionContainer getActions() {
        return actions;
    }

    @Override
    public Integer getBasePoints() {
        return baseDamage;
    }

    @Override
    public Integer getExtraPoints() {
        return 0;
    }

    @Override
    public Integer getPoints() {
        return baseDamage;
    }

    @Override
    public Integer getFullDurability() {
        return 0;
    }

    @Override
    public Integer getCurrentDurability() {
        return 0;
    }

    @Override
    public Boolean afterMove() {
        return true;
    }

    @Override
    public String toString() {
        return "Weapon{" +
                "baseDamage=" + baseDamage +
                //", actions=" + actions +
                ", weaponType=" + weaponType +
                ", name='" + name + '\'' +
                ", rarity=" + rarity +
                //", weight=" + weight +
                ", baseCost=" + baseCost +
                '}';
    }
}
