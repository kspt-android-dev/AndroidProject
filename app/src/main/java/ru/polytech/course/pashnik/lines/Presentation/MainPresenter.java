package ru.polytech.course.pashnik.lines.Presentation;

import java.util.ArrayDeque;
import java.util.Queue;

import ru.polytech.course.pashnik.lines.Core.Board;
import ru.polytech.course.pashnik.lines.Core.Cell;
import ru.polytech.course.pashnik.lines.Core.ColorType;
import ru.polytech.course.pashnik.lines.Core.Intellect;
import ru.polytech.course.pashnik.lines.Core.Line;
import ru.polytech.course.pashnik.lines.Core.WinLines;
import ru.polytech.course.pashnik.lines.Graphics.GameView;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.Model model;
    private MainContract.ViewInterface view;

    private boolean isPressed;
    private Cell pressedCell;
    private Queue<ColorType> queue = new ArrayDeque<>();
    private Intellect intellect;
    private int score = 0;

    public MainPresenter(MainContract.ViewInterface view) {
        this.view = view;
        this.model = new Board();
        intellect = new Intellect(model);
    }

    @Override
    public void onCellWasClicked(float x, float y) {
        Cell definedCell = defineCell(x, y);
        if (model.isFull()) view.createDialog();
        if (isPressed) {
            if (model.haveCell(definedCell)) {
                pressedCell = definedCell;
            } else {
                view.drawBallOnBoard(definedCell, model.getColor(pressedCell));
                model.addCell(definedCell, model.getColor(pressedCell));
                view.clearBallOnBoard(pressedCell);
                model.removeCell(pressedCell);
                if (model.isWin(definedCell)) {
                    WinLines winLines = model.getWinLines();
                    for (int i = 0; i < winLines.getSize(); i++) {
                        Line line = winLines.getWinLine(i);
                        checkScore(line.getLength());
                    }
                    clearWinLines(winLines);
                    view.setScore(String.valueOf(score));
                }
                drawThreeBalls();
                fillQueue();
                drawNextBallsOnScoreView();
                isPressed = false;
            }
        } else {
            if (model.haveCell(definedCell)) {
                isPressed = true;
                pressedCell = definedCell;
            }
        }
    }

    @Override
    public void setWinnerName(String name) {
        // TODO: 23/12/2018
    }

    @Override
    public void initGameView() {
        fillQueue();
        drawThreeBalls();
        fillQueue();
        drawNextBallsOnScoreView();
    }

    private void drawThreeBalls() {
        while (!queue.isEmpty()) {
            ColorType colorType = queue.poll();
            Cell nextCell = intellect.generateNextCell();
            view.drawBallOnBoard(nextCell, colorType);
            model.addCell(nextCell, colorType);
        }
    }

    private void drawNextBallsOnScoreView() {
        Cell direction = new Cell(1, 0);
        Cell start = new Cell(0, 0);
        for (ColorType color : queue) {
            view.drawBallOnScoreView(start, color);
            start = start.plus(direction);
        }
    }

    private Cell defineCell(float x, float y) {
        return new Cell((int) x / GameView.getCellSize(), (int) y / GameView.getCellSize());
    }

    private void checkScore(int length) {
        if (length != 5) {
            int scoreMinus = length - 5;
            this.score += (5 + scoreMinus) * (scoreMinus + 1);
        } else this.score += 5;
    }

    private void clearWinLines(WinLines winLines) {
        for (int i = 0; i < winLines.getSize(); i++) {
            Line currentWinLine = winLines.getWinLine(i);
            Cell startCell = currentWinLine.getStartCell();
            for (int j = 0; j < currentWinLine.getLength(); j++) {
                view.clearBallOnBoard(startCell);
                model.removeCell(startCell);
                startCell = startCell.plus(currentWinLine.getDirection());
            }
        }
        winLines.removeAllWinLines();
    }

    private void fillQueue() {
        for (int i = 0; i < 3; i++) {
            ColorType nextColor = intellect.generateNextColor();
            queue.add(nextColor);
        }
    }
}
