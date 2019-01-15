package ru.polytech.course.pashnik.lines.Core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.polytech.course.pashnik.lines.Presentation.MainContract;

public class Board implements MainContract.Model {

    private static final int MIN_FULL_SIZE = 79;
    private static final int WIN_LENGTH = 5;
    private static final double MIN_VALUE = 100;

    private Map<Cell, ColorType> map = new HashMap<>();
    private final WinLines winLines = new WinLines();
    private List<Cell> reestablishedPath = new ArrayList<>();

    private static final Cell[] DIRECTIONS = {
            new Cell(1, 0), // x-axis
            new Cell(0, 1), // y-axis
            new Cell(-1, 1), // main diagonal
            new Cell(1, 1)   // secondary diagonal
    };

    private static final Cell[] NEIGHBOURS_DIRECTIONS = {
            new Cell(1, 0),
            new Cell(-1, 0),
            new Cell(0, 1),
            new Cell(0, -1)
    };

    @Override
    public ColorType getColor(Cell cell) {
        return map.get(cell);
    }

    @Override
    public boolean haveCell(Cell cell) {
        return map.get(cell) != null;
    }

    @Override
    public void addCell(Cell cell, ColorType color) {
        map.put(cell, color);
    }

    /*
    Added ball in the sequence divides it into two parts called positive and negative parts.
    It is necessary to check the number of balls in each part of direction. That's why i need
    posDirection and negDirection.
     */

    @Override
    public boolean isWin(Cell currentCell) {
        ColorType color = getColor(currentCell);
        for (Cell directionCell : DIRECTIONS) {
            int currentLength = 0;
            Cell posDirection = currentCell;
            Cell negDirection = currentCell;
            while (haveCell(posDirection.plus(directionCell))
                    && getColor(posDirection.plus(directionCell)) == color) {
                posDirection = posDirection.plus(directionCell);
                currentLength++;
            }
            while (haveCell(negDirection.minus(directionCell))
                    && getColor(negDirection.minus(directionCell)) == color) {
                negDirection = negDirection.minus(directionCell);
                currentLength++;
            }
            if (isWinLength(currentLength + 1))
                winLines.addWinLine(currentLength + 1, negDirection, directionCell);
        }
        return !winLines.isEmpty();
    }

    @Override
    public WinLines getWinLines() {
        return winLines;
    }

    @Override
    public void removeCell(Cell cell) {
        map.remove(cell);
    }

    @Override
    public boolean isFull() {
        return map.size() >= MIN_FULL_SIZE;
    }

    @Override
    public Map<Cell, ColorType> getModel() {
        return map;
    }

    @Override
    public void setMap(Map<Cell, ColorType> map) {
        this.map = map;
    }

    private boolean isWinLength(int currentLength) {
        return currentLength >= WIN_LENGTH;
    }

    /*
    A-STAR algorithm for finding a path from start Cell to clicked Cell
     */

    @Override
    public boolean havePath(Cell start, Cell end) {
        List<Cell> path = new ArrayList<>();
        List<Cell> closed = new ArrayList<>(); // items we've already reviewed
        List<Cell> opened = new ArrayList<>(); //items required for viewing
        List<Cell> allCells = new ArrayList<>(); // all Cells with their id's

        List<Double> g = new ArrayList<>();
        List<Double> f = new ArrayList<>();

        start.setId(0);
        int id = 1;
        g.add(0.0);
        f.add(distance(start, end));
        opened.add(start);
        allCells.add(start);

        while (!opened.isEmpty()) {
            int minIndex = getMinIndex(opened, f);
            Cell current = opened.remove(minIndex);
            if (current.equals(end)) {
                path.add(current);
                reestablishPath(path);
                Collections.reverse(reestablishedPath);
                return true;
            }
            closed.add(current);
            List<Cell> neighbours = getNeighbours(current, allCells);
            for (Cell cell : neighbours) {
                if (!closed.contains(cell) && canMove(cell)) {
                    double temp = g.get(current.getId()) + distance(current, cell);
                    if (!opened.contains(cell) || temp < g.get(cell.getId())) {
                        cell.setId(id);
                        allCells.add(cell);
                        path.add(current);
                        g.add(cell.getId(), temp);
                        f.add(cell.getId(), g.get(cell.getId()) + distance(cell, end));
                        id++;
                    }
                    if (!opened.contains(cell)) opened.add(cell);
                }
            }
        }
        return false;
    }

    private double distance(Cell start, Cell end) {
        return Math.sqrt(Math.pow(start.getX() - end.getX(), 2)
                + Math.pow(start.getY() - end.getY(), 2));
    }

    private boolean canMove(Cell cell) {
        return !haveCell(cell);
    }

    private int getMinIndex(List<Cell> opened, List<Double> f) {
        double minValue = MIN_VALUE;
        int index = 0;
        int counter = 0;
        for (Cell cell : opened) {
            if (f.get(cell.getId()) < minValue) {
                minValue = f.get(cell.getId());
                index = counter;
            }
            counter++;
        }
        return index;
    }

    private List<Cell> getNeighbours(Cell cell, List<Cell> allCells) {
        List<Cell> list = new ArrayList<>();
        for (Cell direction : NEIGHBOURS_DIRECTIONS) {
            Cell current = cell.plus(direction);
            if (allCells.contains(current))
                current.setId(allCells.get(allCells.indexOf(current)).getId());
            if (current.isCorrect()) {
                if (!haveCell(current)) {
                    list.add(cell.plus(direction));
                }
            }
        }
        return list;
    }

    private void reestablishPath(List<Cell> path) {
        int pathSize = path.size();
        Cell current = path.get(pathSize - 1);
        while (current.getId() != 0) {
            reestablishedPath.add(current);
            current = path.get(current.getId() - 1);
        }
        reestablishedPath.add(current);
    }
}