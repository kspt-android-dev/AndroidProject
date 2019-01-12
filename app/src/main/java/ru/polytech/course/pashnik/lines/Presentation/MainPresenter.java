package ru.polytech.course.pashnik.lines.Presentation;

import android.content.Context;
import android.os.Bundle;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;

import ru.polytech.course.pashnik.lines.Core.Board;
import ru.polytech.course.pashnik.lines.Core.Cell;
import ru.polytech.course.pashnik.lines.Core.ColorType;
import ru.polytech.course.pashnik.lines.Core.Intellect;
import ru.polytech.course.pashnik.lines.Core.JSONHandler;
import ru.polytech.course.pashnik.lines.Core.Line;
import ru.polytech.course.pashnik.lines.Core.WinLines;
import ru.polytech.course.pashnik.lines.Graphics.GameView;

public class MainPresenter implements MainContract.Presenter {

    private int score = 0;
    private static final int MIN_BALLS = 5;
    private static final int NEXT_BALLS_NUMBER = 3;
    private static final int MAX_BALLS = 81;
    private static final int NEXT_LINE = GameView.CELL_NUMBER - 1;

    private MainContract.Model model;
    private MainContract.ViewInterface view;
    private JSONHandler JSONHandler = new JSONHandler();


    private boolean isPressed;
    private Cell pressedCell;
    private Queue<ColorType> queue = new ArrayDeque<>();
    private Queue<Cell> cells = new ArrayDeque<>();
    private Intellect intellect;

    public MainPresenter(MainContract.ViewInterface view) {
        this.view = view;
        this.model = new Board();
        intellect = new Intellect(model);
    }

    @Override
    public void onCellWasClicked(float x, float y) {
        Cell definedCell = defineCell(x, y);
        if (isPressed) {
            if (model.haveCell(definedCell)) {
                view.stopUpDownAnimation();
                view.drawBallOnBoard(pressedCell, model.getColor(pressedCell));
                pressedCell = definedCell;
                view.makeUpDownAnimation(pressedCell, model.getColor(pressedCell));
            } else {
                view.drawBallOnBoard(definedCell, model.getColor(pressedCell));
                model.addCell(definedCell, model.getColor(pressedCell));
                view.clearBallOnBoard(pressedCell);
                model.removeCell(pressedCell);
                if (model.isWin(definedCell)) {
                    clearLines();
                    view.setScore(String.valueOf(score));
                    view.stopUpDownAnimation();
                    isPressed = false;
                } else {
                    view.stopUpDownAnimation();
                    drawThreeBalls();
                    fillQueue();
                    drawNextBallsOnScoreView();
                    isPressed = false;
                }
            }
        } else {
            if (model.haveCell(definedCell)) {
                isPressed = true;
                pressedCell = definedCell;
                view.makeUpDownAnimation(pressedCell, model.getColor(pressedCell));
            }
        }
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public void initGameView() {
        drawFirstBalls();
        fillQueue();
        drawNextBallsOnScoreView();
    }

    @Override
    public void restoreModel(Bundle savedState) {
        // restoring gameView
        restoreModel(Objects.requireNonNull(savedState.getIntegerArrayList("colors")));
        for (Object cell : model.getModel().keySet()) {
            view.drawBallOnBoard((Cell) cell, model.getColor((Cell) cell));
        }
        // restoring number of points
        this.score = savedState.getInt("score");
        if (score != 0)
            view.setScore(String.valueOf(score));
        // restoring scoreView
        ArrayList<Integer> scoreColors = savedState.getIntegerArrayList("score_colors");
        for (int i = 0; i < Objects.requireNonNull(scoreColors).size(); i++) {
            queue.add(ColorType.getColorType(scoreColors.get(i)));
        }
        drawNextBallsOnScoreView();
    }

    @Override
    public void saveModel(Bundle saveState) {
        saveState.putIntegerArrayList("colors", getColors());
        saveState.putInt("score", score);
        saveState.putIntegerArrayList("score_colors", getScoreColors());
    }

    @Override
    public void exportData(Context context) {
        if (!JSONHandler.exportToJSON(context, model.getModel())) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void restoreLastGame(Context context) {
        Map<Cell, ColorType> map = JSONHandler.importFromJSON(context);
        clearMap(model);
        model.setMap(map);
        view.stopAppearanceAnimation();
        drawMap(model);
    }

    @Override
    public boolean fileExist(Context context) {
        return JSONHandler.ifExists(context);
    }

    private void drawFirstBalls() {
        for (int i = 0; i < NEXT_BALLS_NUMBER; i++) {
            ColorType colorType = intellect.generateNextColor();
            Cell nextCell = intellect.generateNextCell();
            view.makeAppearanceAnimation(nextCell, colorType);
            model.addCell(nextCell, colorType);
        }
    }

    private void clearLines() {
        WinLines winLines = model.getWinLines();
        for (int i = 0; i < winLines.getSize(); i++) {
            Line line = winLines.getWinLine(i);
            checkScore(line.getLength());
        }
        clearWinLines(winLines);
    }

    private void drawThreeBalls() {
        while (!queue.isEmpty()) {
            ColorType colorType = queue.poll();
            Cell nextCell = intellect.generateNextCell();
            cells.add(nextCell);
            if (nextCell.equals(Intellect.DEFAULT_CELL)) {
                view.createDialog();
                break;
            }
            view.makeAppearanceAnimation(nextCell, colorType);
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
        if (length != MIN_BALLS) {
            int scoreMinus = length - MIN_BALLS;
            this.score += (MIN_BALLS + scoreMinus) * (scoreMinus + 1);
        } else this.score += MIN_BALLS;
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
        for (int i = 0; i < NEXT_BALLS_NUMBER; i++) {
            ColorType nextColor = intellect.generateNextColor();
            queue.add(nextColor);
        }
    }

    private ArrayList<Integer> getColors() {
        ArrayList<Integer> colors = new ArrayList<>();
        int counter = -1;
        Cell right = new Cell(1, 0);
        Cell down = new Cell(0, 1);
        Cell start = new Cell(-1, 0);
        Cell current = new Cell(-1, 0);
        for (int i = 0; i < MAX_BALLS; i++) {
            current = current.plus(right);
            colors.add(ColorType.getIndex(model.getColor(current)));
            counter++;
            if (counter == NEXT_LINE) {
                start = start.plus(down);
                current = start;
                counter = -1;
            }
        }
        return colors;
    }

    private void restoreModel(ArrayList<Integer> arrayList) {
        int x = -1;
        int y = 0;
        for (int i = 0; i < arrayList.size(); i++) {
            x++;
            if (arrayList.get(i) != -1) {
                model.addCell(new Cell(x, y), ColorType.getColorType(arrayList.get(i)));
            }
            if (x == NEXT_LINE) {
                y++;
                x = -1;
            }
        }
    }

    private ArrayList<Integer> getScoreColors() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        while (!queue.isEmpty()) {
            arrayList.add(ColorType.getIndex(queue.poll()));
        }
        return arrayList;
    }

    private void drawMap(MainContract.Model model) {
        for (Object cell : model.getModel().keySet()) {
            view.drawBallOnBoard((Cell) cell, model.getColor((Cell) cell));
        }
    }

    private void clearMap(MainContract.Model model) {
        for (Object cell : model.getModel().keySet()) {
            view.clearBallOnBoard((Cell) cell);
        }
    }
}