package ru.polytech.course.pashnik.lines.Core;

public class WinLine {
    private int length;
    private Cell startCell;
    private Cell direction;

    public void setLength(int length) {
        this.length = length;
    }

    public void setStartCell(Cell startCell) {
        this.startCell = startCell;
    }

    public void setDirection(Cell direction) {
        this.direction = direction;
    }

    public int getLength() {
        return length;
    }

    public Cell getStartCell() {
        return startCell;
    }

    public Cell getDirection() {
        return direction;
    }
}
