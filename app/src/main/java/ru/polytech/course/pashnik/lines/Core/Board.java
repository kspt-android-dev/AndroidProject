package ru.polytech.course.pashnik.lines.Core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Board {

    private final HashMap<Cell, ColorType> map = new HashMap<>();
    private final List<WinLine> winLines = new ArrayList<>();

    private final Cell[] DIRECTIONS = {
            new Cell(1, 0), // x-axis
            new Cell(0, 1), // y-axis
            new Cell(-1, 1), // main diagonal
            new Cell(1, 1)}; // secondary diagonal

    public ColorType getColor(int x, int y) {
        return getColor(new Cell(x, y));
    }

    public ColorType getColor(Cell cell) {
        return map.get(cell);
    }

    public void addCell(Cell cell, ColorType color) {
        map.put(cell, color);
    }

    public void addCell(int x, int y, ColorType color) {
        addCell(new Cell(x, y), color);
    }

    public boolean isWin(Cell currentCell) {
        ColorType currentColor = map.get(currentCell);
        for (Cell directionCell : DIRECTIONS) {
            int currentLength = 0;
            Cell positiveDir = currentCell;
            Cell negativeDir = currentCell;
            while (map.get(positiveDir) != null && map.get(positiveDir) == currentColor) {
                positiveDir = positiveDir.plus(directionCell);
                currentLength++;
            }
            while (map.get(negativeDir) != null && map.get(negativeDir) == currentColor) {
                negativeDir = negativeDir.minus(directionCell);
                currentLength++;
            }
            if (isWinLength(currentLength - 1)) {
                winLines.add(new WinLine(currentLength - 1, negativeDir, directionCell));
            }
        }
        return !winLines.isEmpty();
    }

    private boolean isWinLength(int currentLength) {
        return currentLength > 4;
    }

    public void removeCell(Cell cell) {
        map.remove(cell);
    }

    public List<WinLine> getWinLines() {
        return winLines;
    }
}
