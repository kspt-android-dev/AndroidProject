package com.logiccombine.artmate.logiccombine;


import java.util.HashMap;
import java.util.Map;

public class Calculations {
    public double countResult(arithmeticOperations [] operations){
        double countResult = 1;
        double[] neighbours = new double[8];
        Map<String, Double> mapNeighbours;

        for (int i = 7; i>=0; i--) {
            if (operations[i] == arithmeticOperations.CONNECT) {
                mapNeighbours = findNeighbours(neighbours, i);
                countResult = mapNeighbours.get("right");
                double leftN = mapNeighbours.get("left");
                while (countResult!=0){
                    leftN *= 10;
                    countResult = Math.floor(countResult/10);
                }
                countResult = leftN + mapNeighbours.get("right");
                neighbours = setNeighbours(neighbours, mapNeighbours, i, countResult);
            }
        }
        for (int i = 7; i>=0; i--) {
            if (operations[i] == arithmeticOperations.POWER) {
                mapNeighbours = findNeighbours(neighbours, i);
                countResult = Math.pow(mapNeighbours.get("left"), mapNeighbours.get("right"));
                neighbours = setNeighbours(neighbours, mapNeighbours, i, countResult);
            }
        }
        for (int i = 7; i>=0; i--) {
            if (operations[i] == arithmeticOperations.DIVIDE) {
                mapNeighbours = findNeighbours(neighbours, i);
                countResult = mapNeighbours.get("left") / mapNeighbours.get("right");
                neighbours = setNeighbours(neighbours, mapNeighbours, i, countResult);
            }
        }
        for (int i = 7; i>=0; i--) {
            if (operations[i] == arithmeticOperations.MULTIPLY) {
                mapNeighbours = findNeighbours(neighbours, i);
                countResult = mapNeighbours.get("left") * mapNeighbours.get("right");
                neighbours = setNeighbours(neighbours, mapNeighbours, i, countResult);
            }
        }
        for (int i = 0; i<8; i++) {
            if (operations[i] == arithmeticOperations.MINUS) {
                mapNeighbours = findNeighbours(neighbours, i);
                countResult = mapNeighbours.get("left") - mapNeighbours.get("right");
                neighbours = setNeighbours(neighbours, mapNeighbours, i, countResult);
            }
        }
        for (int i = 7; i>=0; i--) {
            if (operations[i] == arithmeticOperations.PLUS){
                mapNeighbours = findNeighbours(neighbours, i);
                countResult = mapNeighbours.get("left") + mapNeighbours.get("right");
                neighbours = setNeighbours(neighbours, mapNeighbours, i, countResult);
            }
        }
        return countResult;
    }

    private Map<String, Double> findNeighbours (double[] neighbours, int position){
        Map<String, Double> result = new HashMap<>();
        result.put("left", (position!= 0 && neighbours[position-1]!= 0)? neighbours[position-1] : position+1);
        result.put("right", (position!= 7 && neighbours[position+1]!= 0)? neighbours[position+1] : position+2);
        return result;
    }

    private double[] setNeighbours(double[] neighbours,  Map<String, Double> mapNeighbours, int position, double countResult){
        for (int k = 0; k<8; k++){
            if (neighbours[k] == mapNeighbours.get("right") || neighbours[k]== mapNeighbours.get("left")){
                neighbours[k] = countResult;
            }
        }
        neighbours[position] = countResult;
        return neighbours;
    }
}
