package ru.polytech.course.pashnik.lines.Core;

import java.util.Random;

import ru.polytech.course.pashnik.lines.Graphics.GameView;
import ru.polytech.course.pashnik.lines.MainContract;

public class Intellect {

    private Random random = new Random();
    private MainContract.Model model;
    private int cellNumber;

    public Intellect(MainContract.Model model) {
        this.model = model;
        cellNumber = GameView.getCellNumber();
    }

    /*
    Dummy intellect will generate a cell in which there is no ball
     */
    public Cell generateNextCell() {
        while (true) {
            int x = random.nextInt(cellNumber);
            int y = random.nextInt(cellNumber);
            Cell cell = new Cell(x, y);
            if (!model.haveCell(cell)) return cell;
        }
    }

    public ColorType generateNextColor() {
        return ColorType.getColorType(random.nextInt(7));
    }
}
