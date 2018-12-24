package ru.gdcn.beastmaster64revelations.GameClass.Actions;

import ru.gdcn.beastmaster64revelations.GameInterface.Action.Action;
import ru.gdcn.beastmaster64revelations.GameInterface.Action.ActionContainer;
import ru.gdcn.beastmaster64revelations.GameInterface.Action.ActionType;

import java.util.LinkedList;
import java.util.List;

public class ActionContainerClass implements ActionContainer {

    List<Action> actions;

    public ActionContainerClass() {
        this.actions = new LinkedList<Action>();
    }

    @Override
    public List<Action> getActions() {
        if (actions == null)
            return null;
        if (actions.isEmpty())
            return null;
        return actions;
    }

    //TODO
    @Override
    public List<Action> getActions(ActionType type) {
        if (actions == null)
            return null;
        if (actions.isEmpty())
            return null;
        List<Action> actionsByType = new LinkedList<>();
        for (Action action: actions){
            if (action.getType().equals(type)){
                actionsByType.add(action);
            }
        }
        return actionsByType;
    }

    //TODO
    @Override
    public List<Action> getAvalableActions(Integer actionPoints) {
        if (actions == null)
            return null;
        if (actions.isEmpty())
            return null;
        List<Action> actionsByAP = new LinkedList<>();
        for (Action action: actions){
            if (action.getRequiredAP() <= actionPoints){
                actionsByAP.add(action);
            }
        }
        return actionsByAP;
    }

    @Override
    public List<Action> getAvalableActions(Integer actionPoints, ActionType type) {
        if (actions == null)
            return null;
        if (actions.isEmpty())
            return null;
        List<Action> actionsByTypeAndAP = new LinkedList<>();
        for (Action action: actions){
            if (action.getRequiredAP() <= actionPoints && action.getType().equals(type)){
                actionsByTypeAndAP.add(action);
            }
        }
        return actionsByTypeAndAP;
    }

    @Override
    public Boolean addAction(Action action) {
        if (action == null){
            //TODO
            return false;
        }
        if (actions == null){
            //TODO
            return false;
        }
        if (actions.contains(action)){
            //TODO
            return false;
        }
        actions.add(action);
        return true;
    }

    @Override
    public Boolean removeAction(Action action) {
        if (action == null){
            //TODO
            return false;
        }
        if (actions == null){
            //TODO
            return false;
        }
        if (!actions.contains(action)){
            //TODO
            return false;
        }
        actions.remove(action);
        return true;
    }

    @Override
    public Boolean addAllActions(List<Action> actions) {
        if (actions == null || actions.isEmpty()){
            //TODO
            return false;
        }
        if (this.actions == null){
            //TODO
            return false;
        }
        for (Action action: actions) {
            boolean success = addAction(action);
            if (!success){
                //TODO
            }
        }
        return true;
    }

    @Override
    public Boolean addAllActions(ActionContainer other) {
        if (other == null){
            return false;
        }
        return addAllActions(other.getActions());
    }

}
