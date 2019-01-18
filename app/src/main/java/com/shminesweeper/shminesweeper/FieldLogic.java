package com.shminesweeper.shminesweeper;

import android.graphics.Point;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Random;

public class FieldLogic {

    enum GameCondition{LOST, WON, IN_PROGRESS, NOT_MINED, OVERVIEW}
    private GameCondition gameCondition;

    private ArrayDeque<Cell> deque = new ArrayDeque<>();

    private ArrayList<Cell> cells;

    private final MainActivity mainActivity;

    private int totalOpenCells;
    private int fieldWidth;
    private int fieldHeight;
    private int fieldMines;
    private int cellHeight;
    private int cellWidth;

    enum FieldMode {SQUARE, HEXAGONAL}
    private FieldMode fieldMode;

    public FieldLogic(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        cells = new ArrayList<>();
    }

    public void startNewGame(SharedViewModel viewModel) {

        updateFieldSettings(viewModel);
        cells = (fieldMode.equals(FieldMode.HEXAGONAL)) ?
                setHexagonalFieldCells() : setSquareFieldCells();
        totalOpenCells = 0;
        gameCondition = GameCondition.NOT_MINED;
    }

    public void update(SharedViewModel viewModel) {
        updateFieldSettings(viewModel);
        this.gameCondition = viewModel.getGameCondition();
        this.totalOpenCells = viewModel.getTotalOpenedCells();
        this.cellWidth = viewModel.getCellWidth();
        this.cellHeight = viewModel.getCellHeight();

        cells = (fieldMode.equals(FieldMode.HEXAGONAL)) ? setHexagonalFieldCells() : setSquareFieldCells();

        restoreCellsWithMine(viewModel.getCellsWithMine());
        restoreCellsWithFlag(viewModel.getCellsWithFlag());
        restoreCellsWithQuestion(viewModel.getCellsWithQuestion());
        restoreOpenCells(viewModel.getOpenCells());
    }

    private void restoreOpenCells(ArrayList<Integer> openCells) {
        for (int cellNumber: openCells){
            cells.get(cellNumber).setIsOpen();
        }
    }

    private void restoreCellsWithQuestion(ArrayList<Integer> cellsWithQuestion) {
        for (int cellNumber: cellsWithQuestion){
            cells.get(cellNumber).setContainsQuestion();
        }
    }

    private void restoreCellsWithFlag(ArrayList<Integer> cellsWithFlag) {
        for (int cellNumber: cellsWithFlag) {
            cells.get(cellNumber).setContainsFlag();
        }
    }

    private void restoreCellsWithMine(ArrayList<Integer> cellsWithMine) {
        for(int cellNumber: cellsWithMine) {
            cells.get(cellNumber).setContainsMine(true);
        }
    }

    // создание массива шестигранных ячеек игрового поля
    // при этом происходит определение соседних для ячеек для данной
    private ArrayList<Cell> setHexagonalFieldCells() {

        ArrayList<Cell> result = new ArrayList<>();

        int startPoint_x = 10;
        int startPoint_y = 100;

        for (int curFieldHeight = 1; curFieldHeight <= fieldHeight; curFieldHeight++) {
            for (int curFieldWidth = 1; curFieldWidth <= fieldWidth; curFieldWidth++) {
                Cell newCell = new Cell(new Point(startPoint_x, startPoint_y), cellWidth/2 , FieldMode.HEXAGONAL);
                result.add(newCell);

                if (curFieldWidth != 1) {
                    result.get(result.size() - 1).setBrother(result.get(result.size() - 2));    //west
                    result.get(result.size() - 2).setBrother(result.get(result.size() - 1));    //east
                }
                if (curFieldHeight % 2 == 0) {  //чётный ярус,
                    result.get(result.size() - 1).setBrother(result.get(result.size() - fieldWidth - 1)); //NW
                    result.get(result.size() - fieldWidth - 1).setBrother(result.get(result.size() - 1));  //SE
                    if (curFieldWidth != fieldWidth) {
                        result.get(result.size() - 1).setBrother(result.get(result.size() - fieldWidth));   //NE
                        result.get(result.size() - fieldWidth).setBrother(result.get(result.size() - 1));   //SW
                    }
                } else if (curFieldHeight != 1) {   // нечётный ярус
                    result.get(result.size() - 1).setBrother(result.get(result.size() - fieldWidth - 1));   //NE
                    result.get(result.size() - fieldWidth - 1).setBrother(result.get(result.size() - 1));   //SW
                    if (curFieldWidth != 1) {
                        result.get(result.size() - 1).setBrother(result.get(result.size() - fieldWidth - 2));   //NW
                        result.get(result.size() - fieldWidth - 2).setBrother(result.get(result.size() - 1));  //SE
                    }
                }

                startPoint_x += cellWidth;
            }

            startPoint_x = startPoint_x - (fieldWidth * cellWidth) +
                    (curFieldHeight % 2 == 0 ? - cellWidth/2 : cellWidth/2);
            startPoint_y += cellHeight/3*2;
        }

        return result;
    }


    // создание массива квадратных ячеек игрового поля
    // при этом происходит определение соседних для ячеек для данной
    private ArrayList<Cell> setSquareFieldCells() {

        ArrayList<Cell> result = new ArrayList<>();

        int startPoint_x = 0;
        int startPoint_y = 0;

        for (int curFieldHeight = 1; curFieldHeight <= fieldHeight; curFieldHeight++) {
            for (int curFieldWidth = 1; curFieldWidth <= fieldWidth; curFieldWidth++) {
                Cell newCell = new Cell(new Point(startPoint_x, startPoint_y), cellWidth, FieldMode.SQUARE);
                result.add(newCell);

                if (curFieldWidth != 1) {    //левый
                    newCell.setBrother(result.get(result.size() - 2));
                    result.get(result.size() - 2).setBrother(newCell);
                }
                if (curFieldHeight != 1) {   // верхний
                    newCell.setBrother(result.get(result.size() - fieldWidth - 1));
                    result.get(result.size() - fieldWidth - 1).setBrother(newCell);
                }
                if (curFieldHeight != 1 && curFieldWidth != 1) {    //девый-верхний
                    newCell.setBrother(result.get(result.size() - 2 - fieldWidth));
                    result.get(result.size() - 2 - fieldWidth).setBrother(newCell);
                }
                if (curFieldHeight != 1 && curFieldWidth != fieldWidth){    // правый-верхний
                    newCell.setBrother(result.get(result.size() - fieldWidth));
                    result.get(result.size() - fieldWidth).setBrother(newCell);
                }

                startPoint_x += cellWidth;
            }

            startPoint_x -= cellWidth * fieldWidth;
            startPoint_y += cellWidth;
        }

        return result;
    }


    // обновление параметров игрового поля
    private void updateFieldSettings(SharedViewModel viewModel) {
        this.fieldHeight = viewModel.getFieldHeight();
        this.fieldWidth = viewModel.getFieldWidth();
        this.fieldMines = viewModel.getNumberOfMines();
        this.fieldMode = viewModel.getFieldMode();
    }

    // открытие ячейки, если возможно
    public void openCell(Point point){
        if (gameCondition.equals(GameCondition.OVERVIEW)) return;
        for (Cell cell : cells) {
            if (cell.isContainsPoint(point) && !cell.isOpen() && !cell.isContainsFlag()) {

                cell.setIsOpen();
                totalOpenCells++;

                if (gameCondition.equals(GameCondition.NOT_MINED)) {
                    mining();
                    gameCondition = GameCondition.IN_PROGRESS;
                }

                if (cell.getMinesBeside() == 0) openBrothers(cell);

                if (cell.isContainsMine()) {
                    showMines();
                    gameCondition = GameCondition.LOST;
                    mainActivity.showDefeatDialog();
                } else if (totalOpenCells == fieldHeight * fieldWidth - fieldMines) {
                    showMines();
                    gameCondition = GameCondition.WON;
                    mainActivity.showGreetingDialog();
                    break;
                }


                return;
            }
        }

    }


    // постановка флага, если  возможно
    public void putFlag(Point point) {
        if (gameCondition.equals(GameCondition.OVERVIEW)) return;
        for (Cell cell: cells) {
            if (cell.isContainsPoint(point) && !cell.isOpen()) {
                if (cell.isContainsFlag()) {
                    cell.setContainsNothing();
                } else {
                    cell.setContainsFlag();
                }
            }
        }
    }

    // постановка вопроса, если  возможно
    public void putQuestion(Point point) {
        if (gameCondition.equals(GameCondition.OVERVIEW)) return;
        for (Cell cell: cells) {
            if (cell.isContainsPoint(point) && !cell.isOpen()) {
                if (cell.isContainsQuestion()) {
                    cell.setContainsNothing();
                } else {
                    cell.setContainsQuestion();
                }
            }
        }
    }

    // открытие всех мин на поле
    private void showMines() {
        for (Cell cell : cells) {
            if (cell.isContainsMine()) {
                cell.setIsOpen();
            }
        }
    }

    // открытие соседних ячеек, если текущая не соседствует ни с одной миной
    private void openBrothers(Cell cell) {
        deque.add(cell);
        while (!deque.isEmpty()) {
            Cell newCell = deque.removeFirst();
            if (!newCell.isOpen()) {
                newCell.setIsOpen();
                totalOpenCells++;
            }

            if (newCell.getMinesBeside() == 0) {
                for (Cell brother : newCell.getBrothers()) {
                    if (!brother.isOpen())
                        deque.addFirst(brother);
                }
            }
        }
    }

    // Минирование поля
    // Если мины занимают больше половины поля, то происходит полное заминирование поля, с дальнейшим разминированием
    //  нужного кол-ва ячеек
    private void mining() {         //минирование поля
        int totalMines;
        if (fieldMines > fieldWidth * fieldHeight % 2) {
            totalMines = fieldHeight * fieldWidth - 1;
            for (Cell cell : cells) cell.setContainsMine(!cell.isOpen());
            while (totalMines > fieldMines) {
                Random random = new Random();
                int num = random.nextInt(fieldHeight * fieldWidth - 1);
                if (cells.get(num).isContainsMine()) {
                    cells.get(num).setContainsMine(false);
                    totalMines--;
                }
            }
        } else {
            totalMines = 0;
            while (totalMines < fieldMines) {
                Random random = new Random();
                int num = random.nextInt(fieldHeight * fieldWidth - 1);
                if (!cells.get(num).isContainsMine() && !cells.get(num).isOpen()) {
                    cells.get(num).setContainsMine(true);
                    totalMines++;
                }
            }
        }
    }

    public void setGameConditionOverview() {
        this.gameCondition = GameCondition.OVERVIEW;
    }

    // ----------------- getters ------------------------------

    public int getFieldHeight() {
        return fieldHeight;
    }

    public GameCondition getGameCondition() {
        return gameCondition;
    }

    public int getCellHeight() {
        return cellHeight;
    }

    public int getCellWidth() {
        return cellWidth;
    }

    public int getFieldWidth() {
        return fieldWidth;
    }

    public ArrayList<Cell> getCells() {
        return cells;
    }

    // номера заминированных ячеек
    public ArrayList<Integer> getCellsWithMine() {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < cells.size(); i++){
            if (cells.get(i).isContainsMine()) result.add(i);
        }
        return result;
    }

    // номера ячеек с флагом
    public ArrayList<Integer> getCellsWithFlag() {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < cells.size(); i++){
            if (cells.get(i).isContainsFlag()) result.add(i);
        }
        return result;
    }

    // номера ячеек с вопросом
    public ArrayList<Integer> getCellsWithQuestion() {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < cells.size(); i++){
            if (cells.get(i).isContainsQuestion()) result.add(i);
        }
        return result;
    }

    // номера открытых ячеек
    public ArrayList<Integer> getOpenCells() {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < cells.size(); i++){
            if (cells.get(i).isOpen()) result.add(i);
        }
        return result;
    }

    public int getTotalOpenCells() {
        return totalOpenCells;
    }

    public FieldMode getFieldMode() {
        return fieldMode;
    }

}
