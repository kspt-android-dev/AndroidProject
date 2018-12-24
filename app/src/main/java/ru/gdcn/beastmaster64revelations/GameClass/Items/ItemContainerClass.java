package ru.gdcn.beastmaster64revelations.GameClass.Items;

import ru.gdcn.beastmaster64revelations.GameInterface.Items.GameItem;
import ru.gdcn.beastmaster64revelations.GameInterface.Items.ItemContainer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ItemContainerClass implements ItemContainer, Serializable {

    protected Integer maxCapacity;
    protected Integer currentLoad;
    protected List<GameItem> items;
    protected Boolean isStatic;

    public ItemContainerClass(Integer maxCapacity, Boolean isStatic) {
        this.maxCapacity = maxCapacity;
        this.currentLoad = 0;
        this.items = new ArrayList<>();
        this.isStatic = isStatic;
    }

    @Override
    public Integer getCapacity() {
        return (maxCapacity == null) ? 0 : maxCapacity;
    }

    @Override
    public Integer getCurrentFill() {
        return (currentLoad == null) ? 0 : currentLoad;
    }

    @Override
    public Integer getRemainingCapacity() {
        if (maxCapacity == null || currentLoad == null)
            return 0;
        return (maxCapacity - currentLoad);
    }

    @Override
    public List<GameItem> getItems() {
        return items;
    }

    @Override
    public Boolean putItem(GameItem item) {
        if (items == null) {
            //TODO
            return false;
        }
        if (isStatic) {
            //TODO
            return false;
        }
        if (items.contains(item)) {
            //TODO
            return false;
        }
        if (getRemainingCapacity() < item.getWeight()){
            //TODO
            return false;
        }
        addItem(item);
        return true;
    }

    private void addItem(GameItem item) {
        items.add(item);
        currentLoad += item.getWeight();
    }

    @Override
    public Boolean takeItem(GameItem item) {
        if (items == null) {
            //TODO
            return false;
        }
        if (isStatic) {
            //TODO
            return false;
        }
        if (!items.contains(item)) {
            //TODO
            return false;
        }
        removeItem(item);
        return true;
    }

    private void removeItem(GameItem item) {
        items.remove(item);
        currentLoad -= item.getWeight();
        if (currentLoad > 0)
            currentLoad = 0;
    }

    @Override
    public Boolean setCapacity(Integer newCapacity) {
        if (isStatic){
            //TODO
            return false;
        }
        if (newCapacity == null)
            return false;
        maxCapacity = newCapacity;
        return true;
        //TODO
    }

    @Override
    public Boolean expandCapacity(Integer capacityExp) {
        if (isStatic){
            //TODO
            return false;
        }
        if (capacityExp == null)
            return false;
        maxCapacity += capacityExp;
        return true;
    }

    @Override
    public List<GameItem> getDrop(Integer luck) {
        if (items == null) {
            //TODO
            return null;
        }
        if (items.isEmpty()){
            //TODO
            return null;
        }
        List<GameItem> drop = new ArrayList<>();
        for (GameItem item: items) {
            if (item.isDropable() && !item.isSoulbound())
                drop.add(item);
        }
        return drop;
    }

    @Override
    public List<GameItem> getSellable() {
        if (items == null) {
            //TODO
            return null;
        }
        if (items.isEmpty()){
            //TODO
            return null;
        }
        List<GameItem> sellable = new ArrayList<>();
        for (GameItem item: items) {
            if (item.isSellable() && !item.isSoulbound())
                sellable.add(item);
        }
        return sellable;
    }

    @Override
    public Boolean isStatic() {
        return isStatic;
    }

}
