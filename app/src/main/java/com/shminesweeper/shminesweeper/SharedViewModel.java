package com.shminesweeper.shminesweeper;

import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;

public class SharedViewModel extends ViewModel {

    private FieldLogic.GameCondition gameCondition;
    private Integer totalOpenedCells;
    private FieldLogic.FieldMode fieldMode;
    private ArrayList<Integer> cellsWithMine;
    private ArrayList<Integer> cellsWithFlag;
    private ArrayList<Integer> cellsWithQuestion;
    private ArrayList<Integer> openCells;
    private PlayingField.CheckedButton checkedButton;
    private MainActivity.ShownDialog shownDialog;
    private int cellWidth;
    private int cellHeight;
    private Integer numberOfMines;
    private Integer fieldWidth;
    private Integer fieldHeight;

    //-------------------   getters  ----------------------

    public FieldLogic.GameCondition getGameCondition() {
        if (gameCondition == null) {
            gameCondition = FieldLogic.GameCondition.NOT_MINED;
        }
        return gameCondition;
    }

    public int getTotalOpenedCells() {
        if (totalOpenedCells == null) {
            totalOpenedCells = 0;
        }
        return totalOpenedCells;
    }

    public FieldLogic.FieldMode getFieldMode(){
        if (fieldMode == null) {
            fieldMode = FieldLogic.FieldMode.HEXAGONAL;
        }
        return fieldMode;
    }

    public ArrayList<Integer> getCellsWithMine() {
        if (cellsWithMine == null) {
            cellsWithMine = new ArrayList<>();
        }
        return cellsWithMine;
    }

    public ArrayList<Integer> getCellsWithFlag() {
        if (cellsWithFlag == null) {
            cellsWithFlag = new ArrayList<>();
        }
        return cellsWithFlag;
    }

    public ArrayList<Integer> getCellsWithQuestion() {
        if (cellsWithQuestion == null) {
            cellsWithQuestion = new ArrayList<>();
        }
        return cellsWithQuestion;
    }

    public ArrayList<Integer> getOpenCells() {
        if (openCells == null) {
            openCells = new ArrayList<>();
        }
        return openCells;
    }

    public PlayingField.CheckedButton getCheckedButton() {
        if (checkedButton == null){
            checkedButton = PlayingField.CheckedButton.TOUCH;
        }
        return checkedButton;
    }

    public MainActivity.ShownDialog getShownDialog() {
        if (shownDialog == null) {
            shownDialog = MainActivity.ShownDialog.NONE;
        }
        return shownDialog;
    }

    public int getCellWidth() {
        return cellWidth;
    }

    public int getCellHeight() {
        return cellHeight;
    }

    public int getNumberOfMines() {
        if (numberOfMines == null) return 30;
        return numberOfMines;
    }

    public int getFieldWidth() {
        if (fieldWidth == null) return 10;
        return fieldWidth;
    }

    public int getFieldHeight() {
        if (fieldHeight == null) return 10;
        return fieldHeight;
    }

    // ------------------ setters  ---------------------------

    public void setGameCondition(FieldLogic.GameCondition gameCondition) { this.gameCondition = gameCondition; }

    public void setTotalOpenedCells(int totalOpenedCells) { this.totalOpenedCells = totalOpenedCells; }

    public void setFieldMode(FieldLogic.FieldMode fieldMode) { this.fieldMode = fieldMode; }

    public void setCellsWithMine(ArrayList<Integer> cellsWithMine) { this.cellsWithMine = cellsWithMine; }

    public void setCellsWithFlag(ArrayList<Integer> cellsWithFlag) { this.cellsWithFlag = cellsWithFlag; }

    public void setCellsWithQuestion(ArrayList<Integer> cellsWithQuestion){ this.cellsWithQuestion = cellsWithQuestion; }

    public void setOpenCells(ArrayList<Integer> openCells) { this.openCells = openCells; }

    public void setCheckedButton(PlayingField.CheckedButton checkedButton) { this.checkedButton = checkedButton; }

    public void setShownDialog(MainActivity.ShownDialog shownDialog) { this.shownDialog = shownDialog; }

    public void setCellWidth(int cellWidth) {
        this.cellWidth = cellWidth;
    }

    public void setCellHeight(int cellHeight) {
        this.cellHeight = cellHeight;
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
}
