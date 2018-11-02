package ru.polytech.course.pashnik.lines.Core;

import java.util.HashMap;

public class Board {

    private final HashMap<Cell, ColorTypes> map = new HashMap<>();

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
}
