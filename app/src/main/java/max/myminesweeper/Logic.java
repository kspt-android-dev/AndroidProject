package max.myminesweeper;

import android.content.SharedPreferences;
import android.graphics.Point;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Random;

public class Logic {

    enum Condition {LOST, WON, PROCESSING, NOT_MINED, OVERVIEW}

    private Condition condition;
    private ArrayDeque<Cell> deque = new ArrayDeque<>();
    private ArrayList<Cell> cells;
    private MainActivity mainActivity;

    private int totalOpenCells;
    private int fieldWidth;
    private int fieldHeight;
    private int numberOfMines;
    private int cellSide;


    public Logic(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        cells = new ArrayList<>();
    }

    public void newGame(MyViewModel viewModel) {
        updateFieldSettings(viewModel);
        cells = setCells();
        totalOpenCells = 0;
        condition = Condition.NOT_MINED;
    }

    public void update(MyViewModel viewModel) {
        updateFieldSettings(viewModel);
        this.condition = viewModel.getCondition();
        this.totalOpenCells = viewModel.getTotalOpenedCells();
        this.cellSide = viewModel.getCellSide();
        cells = (condition.equals(Condition.NOT_MINED)) ? setCells() : viewModel.getCells();
        updateCells(viewModel);
    }

    private void updateCells(MyViewModel viewModel) {
        for (int cellNumber : viewModel.getOpenCells()) {
            cells.get(cellNumber).setCondition(Cell.CellCondition.OPEN);
        }
        for (int cellNumber : viewModel.getCellsWithMine()) {
            cells.get(cellNumber).setCondition(Cell.CellCondition.MINE);
        }
    }

    private ArrayList<Cell> setCells() {
        ArrayList<Cell> result = new ArrayList<>();
        int startPoint_x = 0;
        int startPoint_y = 0;

        for (int curFieldHeight = 1; curFieldHeight <= fieldHeight; curFieldHeight++) {
            for (int curFieldWidth = 1; curFieldWidth <= fieldWidth; curFieldWidth++) {
                Cell newCell = new Cell(new Point(startPoint_x, startPoint_y), cellSide);
                result.add(newCell);
                startPoint_x += cellSide;
            }
            startPoint_x -= cellSide * fieldWidth;
            startPoint_y += cellSide;
        }
        return result;
    }

    private void updateFieldSettings(MyViewModel viewModel) {
        this.fieldHeight = viewModel.getFieldHeight();
        this.fieldWidth = viewModel.getFieldWidth();
        this.numberOfMines = viewModel.getNumberOfMines();
    }

    public void openCell(Point point) {
        if (condition.equals(Condition.OVERVIEW)) return;
        for (Cell cell : cells) {
            if (cell.containsPoint(point) && cell.getCondition().equals(Cell.CellCondition.CLOSED)) {

                cell.setCondition(Cell.CellCondition.OPEN);
                totalOpenCells++;

                if (condition.equals(Condition.NOT_MINED)) {
                    mining();
                    condition = Condition.PROCESSING;
                }

                if (cell.getNeighbourMines() == 0) clearZeros(cell);

                if (cell.getNeighbourMines() == Cell.MINE) {
                    showMines();
                    condition = Condition.LOST;
                    mainActivity.showDefeatDialog();
                } else if (totalOpenCells == fieldHeight * fieldWidth - numberOfMines) {
                    showDefusedMines();
                    condition = Condition.WON;
                    mainActivity.showGreetingDialog();
                    break;
                }
                mainActivity.playingField.invalidate();
                return;
            }
        }

    }

    private void showMines() {
        for (Cell cell : cells) {
            if (cell.getNeighbourMines() == Cell.MINE)
                cell.setCondition(Cell.CellCondition.MINE);
            else
                cell.setCondition(Cell.CellCondition.OPEN);
        }
    }

    private void showDefusedMines() {
        for (Cell cell : cells) {
            if (cell.getNeighbourMines() == Cell.MINE)
                cell.setCondition(Cell.CellCondition.DEFUSED);
            else
                cell.setCondition(Cell.CellCondition.OPEN);
        }
    }


    private void clearZeros(Cell cellToClear) {
        deque.add(cellToClear);
        while (!deque.isEmpty()) {
            Cell newCell = deque.removeFirst();
            if (newCell.getCondition().equals(Cell.CellCondition.CLOSED)) {
                newCell.setCondition(Cell.CellCondition.OPEN);
                totalOpenCells++;
            }

            if (newCell.getNeighbourMines() == 0) {
                int index = cells.indexOf(newCell);
                // up left
                if ((index % fieldWidth != 0) && (index >= fieldWidth)
                        && cells.get(index - fieldWidth - 1).getCondition().equals(Cell.CellCondition.CLOSED)) {
                    deque.addFirst(cells.get(index - fieldWidth - 1));
                }
                // up
                if ((index >= fieldWidth)
                        && cells.get(index - fieldWidth).getCondition().equals(Cell.CellCondition.CLOSED)) {
                    deque.addFirst(cells.get(index - fieldWidth));
                }
                // up right
                if (((index + 1) % fieldWidth != 0) && (index >= fieldWidth)
                        && cells.get(index - fieldWidth + 1).getCondition().equals(Cell.CellCondition.CLOSED)) {
                    deque.addFirst(cells.get(index - fieldWidth + 1));
                }
                // left
                if ((index % fieldWidth != 0) && (index >= fieldWidth)
                        && cells.get(index - 1).getCondition().equals(Cell.CellCondition.CLOSED)) {
                    deque.addFirst(cells.get(index - 1));
                }
                // right
                if (((index + 1) % fieldWidth != 0)
                        && cells.get(index + 1).getCondition().equals(Cell.CellCondition.CLOSED)) {
                    deque.addFirst(cells.get(index + 1));
                }
                // down left
                if ((index % fieldWidth != 0) && (index < fieldWidth * (fieldHeight - 1))
                        && cells.get(index + fieldWidth - 1).getCondition().equals(Cell.CellCondition.CLOSED)) {
                    deque.addFirst(cells.get(index + fieldWidth - 1));
                }
                // down
                if (index < fieldWidth * (fieldHeight - 1)
                        && cells.get(index + fieldWidth).getCondition().equals(Cell.CellCondition.CLOSED)) {
                    deque.addFirst(cells.get(index + fieldWidth));
                }
                // down right
                if (((index + 1) % fieldWidth != 0) && (index < fieldWidth * (fieldHeight - 1))
                        && cells.get(index + fieldWidth + 1).getCondition().equals(Cell.CellCondition.CLOSED)) {
                    deque.addFirst(cells.get(index + fieldWidth + 1));
                }
            }
        }
    }

    private void mining() {
        int totalMines;
        if (numberOfMines > fieldWidth * fieldHeight / 2) {
            totalMines = fieldWidth * fieldHeight - 1;
            for (Cell cell: cells)
                if (cell.getCondition().equals(Cell.CellCondition.CLOSED))
                    cell.setNeighbourMines(Cell.MINE);
            while (totalMines > numberOfMines) {
                Random random = new Random();
                int num = random.nextInt(fieldHeight * fieldWidth - 1);
                if ((cells.get(num).getNeighbourMines() == Cell.MINE) &&
                        cells.get(num).getCondition().equals(Cell.CellCondition.CLOSED)) {
                    cells.get(num).setNeighbourMines(0);
                    totalMines--;
                }
            }
        } else {
            totalMines = 0;
            while (totalMines < numberOfMines) {
                Random random = new Random();
                int num = random.nextInt(fieldHeight * fieldWidth - 1);
                if ((cells.get(num).getNeighbourMines() != Cell.MINE) &&
                        cells.get(num).getCondition().equals(Cell.CellCondition.CLOSED)) {
                    cells.get(num).setNeighbourMines(Cell.MINE);
                    totalMines++;
                }
            }
        }

        // counting others
        for (Cell currentCell : cells) {
            if (currentCell.getNeighbourMines() != Cell.MINE) {
                int index = cells.indexOf(currentCell);
                int count = 0;
                // up left
                if ((index % fieldWidth != 0) && (index >= fieldWidth)) {
                    if (cells.get(index - fieldWidth - 1).getNeighbourMines() == Cell.MINE) count++;
                }
                // up
                if (index >= fieldWidth) {
                    if (cells.get(index - fieldWidth).getNeighbourMines() == Cell.MINE) count++;
                }
                // up right
                if (((index + 1) % fieldWidth != 0) && (index >= fieldWidth)) {
                    if (cells.get(index - fieldWidth + 1).getNeighbourMines() == Cell.MINE) count++;
                }
                // left
                if ((index % fieldWidth != 0) && (index >= fieldWidth)) {
                    if (cells.get(index - 1).getNeighbourMines() == Cell.MINE) count++;
                }
                // right
                if ((index + 1) % fieldWidth != 0) {
                    if (cells.get(index + 1).getNeighbourMines() == Cell.MINE) count++;
                }
                // down left
                if ((index % fieldWidth != 0) && (index < fieldWidth * (fieldHeight - 1))) {
                    if (cells.get(index + fieldWidth - 1).getNeighbourMines() == Cell.MINE) count++;
                }
                // down
                if (index < fieldWidth * (fieldHeight - 1)) {
                    if (cells.get(index + fieldWidth).getNeighbourMines() == Cell.MINE) count++;
                }
                // down right
                if (((index + 1) % fieldWidth != 0) && (index < fieldWidth * (fieldHeight - 1))) {
                    if (cells.get(index + fieldWidth + 1).getNeighbourMines() == Cell.MINE) count++;
                }
                currentCell.setNeighbourMines(count);
            }
        }
    }

    public void setConditionOverview() {
        this.condition = Condition.OVERVIEW;
    }

    public int getFieldHeight() {
        return fieldHeight;
    }

    public int getFieldWidth() {
        return fieldWidth;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition cond) {
        this.condition = cond;
    }

    public int getCellSide() {
        return cellSide;
    }

    public ArrayList<Cell> getCells() {
        return cells;
    }

    public ArrayList<Integer> getCellsWithMine() {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < cells.size(); i++) {
            if (cells.get(i).getCondition().equals(Cell.CellCondition.MINE)) result.add(i);
        }
        return result;
    }

    public ArrayList<Integer> getOpenCells() {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < cells.size(); i++) {
            if (cells.get(i).getCondition().equals(Cell.CellCondition.OPEN)) result.add(i);
        }
        return result;
    }

    public int getTotalOpenCells() {
        return totalOpenCells;
    }
}
