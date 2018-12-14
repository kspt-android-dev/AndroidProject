package ru.gdcn.beastmaster64revelations.GameClass.Items;

import java.io.Serializable;

import ru.gdcn.beastmaster64revelations.GameClass.Constants.Strings;
import ru.gdcn.beastmaster64revelations.GameInterface.Items.GameItem;
import ru.gdcn.beastmaster64revelations.GameInterface.Items.ItemContainer;
import ru.gdcn.beastmaster64revelations.GameInterface.Items.ItemRarity;

public class GameItemClass implements GameItem, Serializable {

    protected String name;
    protected ItemRarity rarity;
    protected Integer weight;
    protected Integer baseCost;
    protected Boolean isUsable;
    protected Boolean isDisposable;
    protected Boolean isDropable;
    protected Boolean isSellable;
    protected Boolean isSoulbound;
    protected Boolean isTransferable;
    protected ItemContainer currentContainer;

    /**
     * Полный конструктор с явным указанием всех параметров
     * **/
    public GameItemClass(String name,
                         ItemRarity rarity,
                         Integer weight,
                         Integer baseCost,
                         Boolean isUsable,
                         Boolean isDisposable,
                         Boolean isDropable,
                         Boolean isSellable,
                         Boolean isSoulbound,
                         Boolean isTransferable) {
        this.name = name;
        this.rarity = rarity;
        this.weight = weight;
        this.baseCost = baseCost;
        this.isUsable = isUsable;
        this.isDisposable = isDisposable;
        this.isDropable = isDropable;
        this.isSellable = isSellable;
        this.isSoulbound = isSoulbound;
        this.isTransferable = isTransferable;
    }

    /**
     * Неполный конструктор для лута, например
     * **/
    public GameItemClass(String name, ItemRarity rarity, Integer weight, Integer baseCost) {
        this.name = name;
        this.rarity = rarity;
        this.weight = weight;
        this.baseCost = baseCost;
        this.isUsable = false;
        this.isDisposable = false;
        this.isDropable = true;
        this.isSellable = true;
        this.isSoulbound = false;
        this.isTransferable = true;
    }

    /**
     * Конструктор аналогичный предыдущему, подразумевающий, что необходимые поля будут реализованы позже
     * **/
    public GameItemClass() {
        this.name = null;
        this.rarity = null;
        this.weight = null;
        this.baseCost = null;
        this.isUsable = false;
        this.isDisposable = false;
        this.isDropable = true;
        this.isSellable = true;
        this.isSoulbound = false;
        this.isTransferable = true;
    }

    @Override
    public String getName() {
        return (name == null) ? Strings.ITEM_DEFAULT_NAME : name;
    }

    @Override
    public ItemRarity getRarity() {
        return (rarity == null) ? ItemRarity.COMMON : rarity;
    }

    @Override
    public Integer getWeight() {
        return (weight == null) ? 0 : weight;
    }

    @Override
    public Integer getBaseCost() {
        return (baseCost == null) ? 0 : baseCost;
    }

    @Override
    public Boolean isUsable() {
        return isUsable;
    }

    @Override
    public Boolean isDisposable() {
        return isDisposable;
    }

    @Override
    public Boolean isDropable() {
        return isDropable;
    }

    @Override
    public Boolean isSellable() {
        return isSellable;
    }

    @Override
    public Boolean isSoulbound() {
        return isSoulbound;
    }

    @Override
    public Boolean isTransferable() {
        return isTransferable;
    }

    @Override
    public ItemContainer getContainer() {
        return currentContainer;
    }

    @Override
    public void dispose() {
        boolean canTakeFromContainer = currentContainer.takeItem(this);
        if (!canTakeFromContainer) {
            //TODO
            return;
        }
    }

    /**
     * Логика перемещения предмета в другой контейнер с отслеживанием нестандартных ситуаций
     * **/
    @Override
    public Boolean transfer(ItemContainer newContainer) {
        if (!isTransferable)
            return false;
        if (currentContainer == null){
            boolean canPutInContainer = newContainer.putItem(this);
            if (!canPutInContainer){
                //TODO
                return false;
            } else {
                this.currentContainer = newContainer;
                return true;
            }
        }
        boolean canTakeFromContainer = currentContainer.takeItem(this);
        if (!canTakeFromContainer){
            //TODO
            return false;
        } else {
            boolean canPutInContainer = newContainer.putItem(this);
            if (!canPutInContainer){
                //TODO
                currentContainer.putItem(this);
                return false;
            } else {
                this.currentContainer = newContainer;
                return true;
            }
        }
    }

}
