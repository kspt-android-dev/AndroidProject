package com.dreamteam.monopoly.Heuristics;

import com.dreamteam.monopoly.game.board.cell.GameCell;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Genetic {

    private int chrLength, generation, populationSize, bestMutationChance, worstMutationChance;
    private float maxWeight, bestWeight, bestCost;
    private List<GameCell> elements;

    public Genetic(List<GameCell> elements, float maxWeight) {
        this.elements = elements;
        this.chrLength = elements.size();
        this.maxWeight = maxWeight;
        this.generation = 150;
        this.populationSize = 10;
        this.bestMutationChance = 75;
        this.worstMutationChance = 15;
        this.bestWeight = 0;
        this.bestCost = 0;
    }

    private List<boolean[]> generateFirstPopulation(int N) {
        List<boolean[]> population = new ArrayList<>(N);
        for (int i = 0; i < N; i++) {
            boolean[] chromosome = new boolean[chrLength];
            for (int k = 0; k < chrLength; k++) {
                Random rnd = new Random();
                chromosome[k] = rnd.nextBoolean();
            }
            population.add(chromosome);
        }
        return population;
    }

    private List<boolean[]> selection(List<boolean[]> population) {
        population.remove(findTheWorst(population));
        population.remove(findTheWorst(population));
        population.remove(findTheWorst(population));
        return population;
    }

    private boolean[] crossing(boolean[] chr1, boolean[] chr2) {
        boolean[] newChr = chr1.clone();
        for (int i = 0; i < chrLength; i++) {
            if (newChr[i] != chr2[i]) {
                Random rnd = new Random();
                newChr[i] = rnd.nextBoolean();
                float f = fittingFunction(newChr);
                if (f <= fittingFunction(chr1) && f <= fittingFunction(chr2)) {
                    newChr[i] = chr2[i];
                }
            }
        }
        return newChr;
    }

    private boolean[] mutation(boolean[] chr) {
        float startF = fittingFunction(chr);
        Random rnd = new Random();
        int i = rnd.nextInt(chrLength);
        chr[i] = !chr[i];
        if (rnd.nextInt(100) > bestMutationChance) chr = mutation(chr);
        if (fittingFunction(chr) > startF)
            return chr;
        else return mutation(chr);
    }

    private List<boolean[]> takeTheBest(List<boolean[]> population) {
        float bestFitting = fittingFunction(population.get(0));
        boolean[] bestChr = population.get(0);
        List<boolean[]> result = new ArrayList<>();
        result.add(bestChr);
        result.add(bestChr);
        for (boolean[] chr : population) {
            float currentFit = fittingFunction(chr);
            if (currentFit > bestFitting) {
                bestFitting = currentFit;
                result.set(0, bestChr);
                bestChr = chr;
                result.set(1, bestChr);
            }
        }
        return result;
    }

    private boolean[] findTheWorst(List<boolean[]> population) {
        float worstFitting = fittingFunction(population.get(0));
        boolean[] worstChr = population.get(0);
        for (boolean[] chr : population) {
            float currentFit = fittingFunction(chr);
            if (currentFit < worstFitting) {
                worstFitting = currentFit;
                worstChr = chr;
            }
        }
        return worstChr;
    }

    private float fittingFunction(boolean[] chromosome) {
        float currentWeight = 0;
        float currentCost = 0;
        for (int k = 0; k < chrLength; k++) {
            if (chromosome[k]) {
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
        List<boolean[]> population = generateFirstPopulation(populationSize);

        Random rnd = new Random();

        while (generation > 0) {
            population = selection(population);

            population.add(crossing(takeTheBest(population).get(0), takeTheBest(population).get(1)));
            population.add(crossing(
                    population.get(rnd.nextInt(population.size())),
                    takeTheBest(population).get(1)));

            population.add(crossing(
                    population.get(rnd.nextInt(population.size())), population.get(rnd.nextInt(population.size()))));

            if (rnd.nextInt(100) > worstMutationChance) {
                int mutationIndex = population.indexOf(findTheWorst(population));
                population.set(mutationIndex, mutation(population.get(mutationIndex)));
            }
            if (rnd.nextInt(100) > bestMutationChance) {
                int mutationIndex = population.indexOf(takeTheBest(population).get(1));
                population.set(mutationIndex, mutation(population.get(mutationIndex)));
            }
            generation--;
        }
        convertToCostWeight(takeTheBest(population).get(1));
    }

    private void convertToCostWeight(boolean[] chr) {
        float totalWeight = 0;
        float totalCost = 0;
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < chrLength; i++) {
            if (chr[i]) {
                totalWeight += elements.get(i).getInfo().getCost().getCostSell();
                totalCost += elements.get(i).getInfo().getCost().getCostCharge();
            }
            str.append(chr[i] ? 1 : 0);
        }
        bestWeight = totalWeight;
        bestCost = totalCost;
        System.out.println("Best choice: Weight = " + totalWeight + " Cost = " + totalCost +
                "  [" + str.toString() + "]");
    }

    public float getBestWeight() {
        return bestWeight;
    }

    public float getBestCost() {
        return bestCost;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }
}

