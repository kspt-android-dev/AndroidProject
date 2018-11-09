package ru.polytech.course.pashnik.lines.Core;

import java.util.HashMap;

public class Board {

    private final HashMap<Cell, ColorType> map = new HashMap<>();
    private final WinLine winLine = new WinLine();

    private final Cell[] DIRECTIONS = {
            new Cell(1, 0), new Cell(-1, 0), // x-axis
            new Cell(0, 1), new Cell(0, -1), // y-axis
            new Cell(1, -1), new Cell(-1, 1), // secondary diagonal
            new Cell(-1, -1), new Cell(1, 1)}; // main diagonal

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

    public boolean isWin() {
        for (Cell currentCell : map.keySet()) {
            ColorType currentColor = map.get(currentCell);
            for (Cell directionCell : DIRECTIONS) {
                int currentLength = 0;
                Cell startCell = currentCell;
                while (map.get(startCell) != null && map.get(startCell) == currentColor) {
                    currentLength++;
                    startCell = startCell.plus(directionCell);
                }
                if (isWinLength(currentLength)) {
                    setWinLineParam(currentLength, currentCell, directionCell);
                    return true;
                }
            }
        }
        return false;
    }

    private void setWinLineParam(int length, Cell start, Cell direction) {
        winLine.setLength(length);
        winLine.setStartCell(start);
        winLine.setDirection(direction);
    }

    private boolean isWinLength(int currentLength) {
        return currentLength > 4;
    }

    public void removeCell(Cell cell) {
        map.remove(cell);
    }

    public WinLine getWinLine() {
        return winLine;
    }
}
