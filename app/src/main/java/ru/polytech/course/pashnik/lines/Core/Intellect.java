package ru.polytech.course.pashnik.lines.Core;

import java.util.Random;

import ru.polytech.course.pashnik.lines.Scene;

public class Intellect {

    private Random random = new Random();

    public Cell nextCell() {
        int x = random.nextInt(Scene.CELL_NUMBER);
        int y = random.nextInt(Scene.CELL_NUMBER);
        return new Cell(x, y);
    }

    public int nextColor() {
        return random.nextInt(7);
    }
}
