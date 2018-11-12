package ru.polytech.course.pashnik.lines.Core;

public class Line {

    private int length;
    private Cell startCell;
    private Cell direction;

    public Line(int length, Cell startCell, Cell direction) {
        this.length = length;
        this.startCell = startCell;
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


