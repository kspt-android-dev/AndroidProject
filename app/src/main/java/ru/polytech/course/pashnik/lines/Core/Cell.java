package ru.polytech.course.pashnik.lines.Core;

import java.util.Objects;

public class Cell {
    private final int x;
    private final int y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Cell plus(Cell cell) {
        return new Cell(x + cell.getX(), y + cell.getY());
    }

    public Cell minus(Cell cell) {
        return new Cell(x - cell.getX(), y - cell.getY());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || other.getClass() != this.getClass()) return false;
        Cell cell = (Cell) other;
        return x == cell.x && y == cell.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
