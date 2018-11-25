package ru.polytech.course.pashnik.lines.Core;

import java.util.Random;

import ru.polytech.course.pashnik.lines.Scene;

public class Intellect {

    private Random random = new Random();

    public Cell nextCell() {
        int cellSize = Scene.getCellSize();
        int x = random.nextInt(cellSize);
        int y = random.nextInt(cellSize);
        return new Cell(x, y);
    }

    public int nextColor() {
        return random.nextInt(7);
    }
}
