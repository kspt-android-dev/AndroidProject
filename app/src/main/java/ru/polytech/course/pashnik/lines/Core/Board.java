package ru.polytech.course.pashnik.lines.Core;

import java.util.HashMap;

public class Board {

    private final HashMap<Cell, ColorTypes> map = new HashMap<>();

    private int totalLength;
    private Cell currentDirection;
    private Cell startCell;

    private final Cell[] DIRECTIONS = {
            new Cell(1, 0), new Cell(-1, 0), // x-axis
            new Cell(0, 1), new Cell(0, -1), // y-axis
            new Cell(1, -1), new Cell(-1, 1), // secondary diagonal
            new Cell(-1, -1), new Cell(1, 1)}; // main diagonal

    public ColorTypes getColor(int x, int y) {
        return getColor(new Cell(x, y));
    }

    public ColorTypes getColor(Cell cell) {
        return map.get(cell);
    }

    public void addCell(Cell cell, ColorTypes color) {
        map.put(cell, color);
    }

    public void addCell(int x, int y, ColorTypes color) {
        addCell(new Cell(x, y), color);
    }

    public HashMap<Cell, ColorTypes> getBoard() {
        return map;
    }

    public boolean isWin() {
        for (Cell cell : map.keySet()) {
            for (Cell directionCell : DIRECTIONS) {
                int currentLength = 0;
                Cell mapCell = cell;
                while (map.get(mapCell) != null) {
                    currentLength++;
                    if (isWinLength(currentLength)) { // fix max length only with 5 balls
                        totalLength = currentLength;
                        currentDirection = directionCell;
                        startCell = cell;
                        return true;
                    }
                    mapCell = mapCell.plus(directionCell);
                }
            }
        }
        return false;
    }

    private boolean isWinLength(int currentLength) {
        return currentLength > 4;
    }

    public int getTotalLength() {
        return totalLength;
    }

    public Cell getCurrentDirection() {
        return currentDirection;
    }

    public Cell getStartCell() {
        return startCell;
    }
}
