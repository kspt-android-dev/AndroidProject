package ru.polytech.course.pashnik.lines.Core;

import java.util.HashMap;

import ru.polytech.course.pashnik.lines.Presentation.MainContract;

public class Board implements MainContract.Model {

    private final HashMap<Cell, ColorType> map = new HashMap<>();
    private final WinLines winLines = new WinLines();

    private final Cell[] DIRECTIONS = {
            new Cell(1, 0), // x-axis
            new Cell(0, 1), // y-axis
            new Cell(-1, 1), // main diagonal
            new Cell(1, 1)}; // secondary diagonal

    @Override
    public ColorType getColor(Cell cell) {
        return map.get(cell);
    }

    @Override
    public boolean haveCell(Cell cell) {
        return map.get(cell) != null;
    }

    @Override
    public void addCell(Cell cell, ColorType color) {
        map.put(cell, color);
    }

    /*
    Added ball in the sequence divides it into two parts called positive and negative parts.
    It is necessary to check the number of balls in each part of direction. That's why i need
    posDirection and negDirection.
     */

    @Override
    public boolean isWin(Cell currentCell) {
        ColorType color = getColor(currentCell);
        for (Cell directionCell : DIRECTIONS) {
            int currentLength = 0;
            Cell posDirection = currentCell;
            Cell negDirection = currentCell;
            while (haveCell(posDirection.plus(directionCell))
                    && getColor(posDirection.plus(directionCell)) == color) {
                posDirection = posDirection.plus(directionCell);
                currentLength++;
            }
            while (haveCell(negDirection.minus(directionCell))
                    && getColor(negDirection.minus(directionCell)) == color) {
                negDirection = negDirection.minus(directionCell);
                currentLength++;
            }
            if (isWinLength(currentLength + 1))
                winLines.addWinLine(currentLength + 1, negDirection, directionCell);
        }
        return !winLines.isEmpty();
    }

    @Override
    public WinLines getWinLines() {
        return winLines;
    }

    @Override
    public void removeCell(Cell cell) {
        map.remove(cell);
    }

    @Override
    public boolean isFull() {
        return map.size() >= 79;
    }

    private boolean isWinLength(int currentLength) {
        return currentLength > 4;
    }
}