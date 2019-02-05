package max.myminesweeper;

import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;

public class MyViewModel extends ViewModel {

    private static final int DEFAULT_NUMBER_OF_MINES = 30;
    private static final int DEFAULT_FIELD_WIDTH = 10;
    private static final int DEFAULT_FIELD_HEIGHT = 10;

    private Logic.Condition condition;
    private Integer totalOpenedCells;
    private ArrayList<Cell> cells;
    private ArrayList<Integer> cellsWithMine;
    private ArrayList<Integer> openCells;
    private MainActivity.ShownDialog shownDialog;
    private int cellSide;
    private Integer numberOfMines;
    private Integer fieldWidth;
    private Integer fieldHeight;

    public Logic.Condition getCondition() {
        if (condition == null) condition = Logic.Condition.NOT_MINED;
        return condition;
    }

    public int getTotalOpenedCells() {
        if (totalOpenedCells == null) totalOpenedCells = 0;
        return totalOpenedCells;
    }

    public ArrayList<Integer> getCellsWithMine() {
        if (cellsWithMine == null) cellsWithMine = new ArrayList<>();
        return cellsWithMine;
    }

    public ArrayList<Integer> getOpenCells() {
        if (openCells == null) openCells = new ArrayList<>();
        return openCells;
    }

    public MainActivity.ShownDialog getShownDialog() {
        if (shownDialog == null) shownDialog = MainActivity.ShownDialog.NONE;
        return shownDialog;
    }

    public int getCellSide() {
        return cellSide;
    }

    public int getNumberOfMines() {
        if (numberOfMines == null) return DEFAULT_NUMBER_OF_MINES;
        return numberOfMines;
    }

    public int getFieldWidth() {
        if (fieldWidth == null) return DEFAULT_FIELD_WIDTH;
        return fieldWidth;
    }

    public int getFieldHeight() {
        if (fieldHeight == null) return DEFAULT_FIELD_HEIGHT;
        return fieldHeight;
    }


    public void setCondition(Logic.Condition condition) {
        this.condition = condition;
    }

    public void setTotalOpenedCells(int totalOpenedCells) {
        this.totalOpenedCells = totalOpenedCells;
    }

    public void setCellsWithMine(ArrayList<Integer> cellsWithMine) {
        this.cellsWithMine = cellsWithMine;
    }

    public void setOpenCells(ArrayList<Integer> openCells) {
        this.openCells = openCells;
    }

    public void setShownDialog(MainActivity.ShownDialog shownDialog) {
        this.shownDialog = shownDialog;
    }

    public void setCellSide(int cellSide) {
        this.cellSide = cellSide;
    }

    public void setNumberOfMines(int numberOfMines) {
        this.numberOfMines = numberOfMines;
    }

    public void setFieldWidth(int fieldWidth) {
        this.fieldWidth = fieldWidth;
    }

    public void setFieldHeight(int fieldHeight) {
        this.fieldHeight = fieldHeight;
    }

    public ArrayList<Cell> getCells() {
        if (cells == null) cells = new ArrayList<>();
        return cells;
    }

    public void setCells(ArrayList<Cell> cells) {
        this.cells = cells;
    }
}