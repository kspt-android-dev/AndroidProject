package ru.polytech.course.pashnik.lines.Core;

import java.util.Random;

import ru.polytech.course.pashnik.lines.Scene;

public class Intellect {

    private Random random = new Random();
    private Board board;

    public Intellect(Board board) {
        this.board = board;
    }

    /*
    Dummy intellect will generate a cell in which there is no ball
     */
    public Cell generateNextCell() {
        while (true) {
            int x = random.nextInt(Scene.CELL_NUMBER);
            int y = random.nextInt(Scene.CELL_NUMBER);
            Cell cell = new Cell(x, y);
            if (!board.haveCell(cell)) return cell;
        }
    }

    public ColorType generateNextColor() {
        return ColorType.getColorType(random.nextInt(7));
    }
}
