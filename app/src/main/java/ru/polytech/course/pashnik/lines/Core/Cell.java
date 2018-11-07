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

    public Cell plus(int x, int y) {
        return new Cell(this.x + x, this.y + y);
    }

    public Cell plus(Cell cell) {
        return plus(cell.getX(), cell.getY());
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
