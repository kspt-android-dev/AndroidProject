package com.dreamteam.monopoly.Heuristics;

import com.dreamteam.monopoly.game.board.cell.GameCell;

import java.util.List;
import java.util.Random;

public class AnnealingSimulation {

    private int conditionLength, iteration;
    private float maxWeight, bestWeight, bestCost, T_start, T_min, T, probabilityK, decreasingK;
    private List<GameCell> elements;

    public AnnealingSimulation(List<GameCell> elements, float maxWeight) {
        this.elements = elements;
        this.conditionLength = elements.size();
        this.maxWeight = maxWeight;
        this.T_start = 10000;
        this.T_min = 1;
        this.T = T_start;
        this.probabilityK = (float) Math.sqrt(T_start * 0.05f);
        this.decreasingK = T_start * 0.1f;
        this.iteration = 0;
        this.bestWeight = 0;
        this.bestCost = 0;
    }

    private boolean[] generateFirstCondition() {
        boolean[] condition = new boolean[conditionLength];
        for (int k = 0; k < conditionLength; k++) {
            Random rnd = new Random();
            condition[k] = rnd.nextBoolean();
        }
        return condition;
    }

    private boolean[] mutation(boolean[] condition) {
        boolean[] newC = condition.clone();
        Random rnd = new Random();
        int i = rnd.nextInt(conditionLength);
        newC[i] = !condition[i];
        return newC;
    }

    private boolean[] extremeMutation(boolean[] condition) {
        boolean[] newC = condition.clone();
        Random rnd = new Random();
        int i = rnd.nextInt(conditionLength);
        newC[i] = !condition[i];
        try {
            newC[i - 1] = !condition[i - 1];
            newC[i + 1] = !condition[i + 1];
        } catch (ArrayIndexOutOfBoundsException e) {
            // ignore
        }
        return newC;
    }

    private boolean[] maxRandomMutation(boolean[] condition) {
        boolean[] newC = condition.clone();
        Random rnd = new Random();
        int i = rnd.nextInt(conditionLength);
        int k = rnd.nextInt(conditionLength);
        int min = Math.min(i, k);
        int max = Math.max(i, k);
        for (int j = min; j <= max; j++)
            newC[j] = rnd.nextBoolean();
        return newC;
    }


    private boolean getProbability(float deltaE, float T) {
        double P = Math.exp(-deltaE * probabilityK / T);
        return Math.random() < P;
    }

    private float decreaseTemperature(int iteration) {
        return decreasingK / iteration;
    }

    private float fittingFunction(boolean[] condition) {
        float currentWeight = 0;
        float currentCost = 0;
        for (int k = 0; k < conditionLength; k++) {
            if (condition[k]) {
                currentWeight += elements.get(k).getInfo().getCost().getCostSell();
                currentCost += elements.get(k).getInfo().getCost().getCostCharge();
            }
        }
        float result;
        if (currentWeight <= maxWeight) result = currentCost;
        else result = maxWeight - currentWeight;
        return result;
    }

    public void solving() {
        boolean[] condition = generateFirstCondition();
        int sameConditionCount = 0;
        while (T > T_min) {
            boolean[] newCondition;
            if (sameConditionCount < 10) newCondition = mutation(condition);
            else if (sameConditionCount > 25) newCondition = maxRandomMutation(condition);
            else newCondition = extremeMutation(condition);

            float newE = fittingFunction(newCondition);
            float deltaE = newE - fittingFunction(condition);
            if (deltaE > 0) {
                condition = newCondition;
                sameConditionCount = 0;
            } else if (deltaE != 0 && getProbability(Math.abs(deltaE), T) && newE > 0) {
                condition = newCondition;
                sameConditionCount = 0;
            } else sameConditionCount++;
            iteration++;
            T = decreaseTemperature(iteration);
        }
        convertToCostWeight(condition);
    }

    private void convertToCostWeight(boolean[] condition) {
        float totalWeight = 0;
        float totalCost = 0;
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < conditionLength; i++) {
            if (condition[i]) {
                totalWeight += elements.get(i).getInfo().getCost().getCostSell();
                totalCost += elements.get(i).getInfo().getCost().getCostCharge();
            }
            str.append(condition[i] ? 1 : 0);
        }
        bestWeight = totalWeight;
        bestCost = totalCost;
        System.out.println("Best choice: Weight = " + totalWeight + " Cost = " + totalCost +
                "  [" + str.toString() + "]");
    }

    public void setT_start(float t_start) {
        T_start = t_start;
    }

    public float getBestWeight() {
        return bestWeight;
    }

    public float getBestCost() {
        return bestCost;
    }
}
