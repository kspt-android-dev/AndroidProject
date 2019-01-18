package com.shminesweeper.shminesweeper;

import android.graphics.Point;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;


import java.util.ArrayList;

@RunWith(RobolectricTestRunner.class)
public class FunctionalTest {

    //------------------ FieldLogic tests-------------------

    private MainActivity mainActivity;
    private ArrayList<Integer> assertionArray;
    private FieldLogic fieldLogic;

    @Before
    public void setMainActivity() {
        mainActivity = Robolectric.setupActivity(MainActivity.class);
        assertionArray = new ArrayList<>();
        fieldLogic = new FieldLogic(mainActivity);
    }


    @Test
    public void putQuestionTest() {

        fieldLogic.update( setViewModelForFieldLogicTests() );


        fieldLogic.putQuestion(new Point(50, 150));                           // в пустую
        assertionArray.add(2);
        assertionArray.add(3);
        Assert.assertEquals(assertionArray, fieldLogic.getCellsWithQuestion());

        assertionArray.clear();
        fieldLogic.putQuestion(new Point(50,50));                            // с флагом
        assertionArray.add(0);
        assertionArray.add(2);
        assertionArray.add(3);
        Assert.assertEquals(assertionArray, fieldLogic.getCellsWithQuestion());

        assertionArray.clear();
        fieldLogic.putQuestion(new Point(150, 150));                        // с вопросом
        assertionArray.add(0);
        assertionArray.add(2);
        Assert.assertEquals(assertionArray, fieldLogic.getCellsWithQuestion());

        fieldLogic.putQuestion(new Point(150, 50));                            // в открытую
        Assert.assertEquals(assertionArray, fieldLogic.getCellsWithQuestion());


    }

    private SharedViewModel setViewModelForFieldLogicTests() {

        //  field:
        //  (flag)  (open)
        //  (empty) (question)

        SharedViewModel sharedViewModel = new SharedViewModel();

        sharedViewModel.setGameCondition(FieldLogic.GameCondition.NOT_MINED);   // gameCondition;
        sharedViewModel.setTotalOpenedCells(0);                                 // totalOpenedCells;
        sharedViewModel.setFieldMode(FieldLogic.FieldMode.SQUARE);              // fieldMode;
        // cellsWithMine;  default(empty)
        ArrayList<Integer> cellsWithFlag = new ArrayList<>();
        cellsWithFlag.add(0);
        sharedViewModel.setCellsWithFlag(cellsWithFlag);                        // cellsWithFlag;

        ArrayList<Integer> cellsWithQuestion = new ArrayList<>();
        cellsWithQuestion.add(3);
        sharedViewModel.setCellsWithQuestion(cellsWithQuestion);                // cellsWithQuestion;

        ArrayList<Integer> openCells = new ArrayList<>();
        openCells.add(1);
        sharedViewModel.setOpenCells(openCells);                                // openCells;
        sharedViewModel.setCheckedButton(PlayingField.CheckedButton.QUESTION);  // checkedButton;
        // shownDialog;  default(NONE)
        sharedViewModel.setCellWidth(100);                                      // cellWidth;
        sharedViewModel.setCellHeight(100);                                     // cellHeight;
        sharedViewModel.setNumberOfMines(1);                                    // numberOfMines;
        sharedViewModel.setFieldWidth(2);                                       // fieldWidth;
        sharedViewModel.setFieldHeight(2);                                      // fieldHeight;

        return sharedViewModel;
    }

    @Test
    public void putFlagTest() {

        fieldLogic.update( setViewModelForFieldLogicTests() );

        fieldLogic.putFlag(new Point(50,50));                           // с флагом
        Assert.assertEquals(assertionArray, fieldLogic.getCellsWithFlag());

        fieldLogic.putFlag(new Point(150, 50));                         // в открытую
        Assert.assertEquals(assertionArray, fieldLogic.getCellsWithFlag());

        fieldLogic.putFlag(new Point(50, 150));                         // в пустую
        assertionArray.add(2);
        Assert.assertEquals(assertionArray, fieldLogic.getCellsWithFlag());

        fieldLogic.putFlag(new Point(150, 150));                        // с вопросом
        assertionArray.add(3);
        Assert.assertEquals(assertionArray, fieldLogic.getCellsWithFlag());
    }

    @Test
    public void openCellTest() {

        SharedViewModel viewModel = setViewModelForFieldLogicTests();
        viewModel.setGameCondition(FieldLogic.GameCondition.IN_PROGRESS);        // чтобы не минировалось после открытия
        fieldLogic.update( viewModel );

        fieldLogic.openCell(new Point(50,50));                              // с флагом
        assertionArray.add(1);
        Assert.assertEquals(assertionArray, fieldLogic.getOpenCells());

        fieldLogic.openCell(new Point(150, 50));                            // открытая
        Assert.assertEquals(assertionArray, fieldLogic.getOpenCells());

        assertionArray.clear();
        fieldLogic.openCell(new Point(50, 150));                            // пустая
        assertionArray.add(0);
        assertionArray.add(1);
        assertionArray.add(2);
        assertionArray.add(3);
        Assert.assertEquals(assertionArray, fieldLogic.getOpenCells());           // все вокруг открываются

        fieldLogic.update( viewModel );
        fieldLogic.openCell(new Point(150, 150));                           // с вопросом
        Assert.assertEquals(assertionArray, fieldLogic.getOpenCells());            // все вокруг открываются

    }

    @Test
    public void updateTest() {

        fieldLogic.update( setViewModelForFieldLogicTests() );

        Assert.assertEquals(2, fieldLogic.getFieldHeight());

        Assert.assertEquals(FieldLogic.GameCondition.NOT_MINED, fieldLogic.getGameCondition());

        Assert.assertEquals(100, fieldLogic.getCellHeight());

        Assert.assertEquals(100, fieldLogic.getCellWidth());

        Assert.assertEquals(2, fieldLogic.getFieldWidth());

        Assert.assertEquals(assertionArray, fieldLogic.getCellsWithMine());

        assertionArray.clear();
        assertionArray.add(0);
        Assert.assertEquals(assertionArray, fieldLogic.getCellsWithFlag());

        assertionArray.clear();
        assertionArray.add(3);
        Assert.assertEquals(assertionArray, fieldLogic.getCellsWithQuestion());

        assertionArray.clear();
        assertionArray.add(1);
        Assert.assertEquals(assertionArray, fieldLogic.getOpenCells());

        Assert.assertEquals(0, fieldLogic.getTotalOpenCells());

        Assert.assertEquals(FieldLogic.FieldMode.SQUARE, fieldLogic.getFieldMode());

        fieldLogic.update( setViewModelForUpdateTest() );                                  // update

        Assert.assertEquals(10, fieldLogic.getFieldHeight());

        Assert.assertEquals(10, fieldLogic.getFieldWidth());

        Assert.assertEquals(FieldLogic.GameCondition.IN_PROGRESS, fieldLogic.getGameCondition());

        Assert.assertEquals(50, fieldLogic.getCellHeight());

        Assert.assertEquals(50, fieldLogic.getCellWidth());

        assertionArray.clear();
        Assert.assertEquals(assertionArray, fieldLogic.getCellsWithMine());

        assertionArray.clear();
        assertionArray.add(5);
        Assert.assertEquals(assertionArray, fieldLogic.getCellsWithFlag());

        assertionArray.clear();
        assertionArray.add(7);
        Assert.assertEquals(assertionArray, fieldLogic.getCellsWithQuestion());

        assertionArray.clear();
        assertionArray.add(1);
        Assert.assertEquals(assertionArray, fieldLogic.getOpenCells());

        Assert.assertEquals(1, fieldLogic.getTotalOpenCells());

        Assert.assertEquals(FieldLogic.FieldMode.HEXAGONAL, fieldLogic.getFieldMode());
    }

    private SharedViewModel setViewModelForUpdateTest() {

        SharedViewModel sharedViewModel = new SharedViewModel();

        sharedViewModel.setGameCondition(FieldLogic.GameCondition.IN_PROGRESS);   // gameCondition;
        sharedViewModel.setTotalOpenedCells(1);                                 // totalOpenedCells;
        sharedViewModel.setFieldMode(FieldLogic.FieldMode.HEXAGONAL);              // fieldMode;
        // cellsWithMine;  default(empty)
        ArrayList<Integer> cellsWithFlag = new ArrayList<>();
        cellsWithFlag.add(5);
        sharedViewModel.setCellsWithFlag(cellsWithFlag);                        // cellsWithFlag;

        ArrayList<Integer> cellsWithQuestion = new ArrayList<>();
        cellsWithQuestion.add(7);
        sharedViewModel.setCellsWithQuestion(cellsWithQuestion);                // cellsWithQuestion;

        ArrayList<Integer> openCells = new ArrayList<>();
        openCells.add(1);
        sharedViewModel.setOpenCells(openCells);                                // openCells;
        sharedViewModel.setCheckedButton(PlayingField.CheckedButton.FLAG);  // checkedButton;
        // shownDialog;  default(NONE)
        sharedViewModel.setCellWidth(50);                                      // cellWidth;
        sharedViewModel.setCellHeight(50);                                     // cellHeight;
        sharedViewModel.setNumberOfMines(2);                                    // numberOfMines;
        sharedViewModel.setFieldWidth(10);                                       // fieldWidth;
        sharedViewModel.setFieldHeight(10);                                      // fieldHeight;

        return sharedViewModel;
    }

    @Test
    public void startNewGameTest() {

        fieldLogic.startNewGame(setViewModelForFieldLogicTests());

        Assert.assertEquals(2, fieldLogic.getFieldHeight());

        Assert.assertEquals(FieldLogic.GameCondition.NOT_MINED, fieldLogic.getGameCondition());

        Assert.assertEquals(0, fieldLogic.getCellHeight());

        Assert.assertEquals(0, fieldLogic.getCellWidth());

        Assert.assertEquals(2, fieldLogic.getFieldWidth());

        Assert.assertEquals(assertionArray, fieldLogic.getCellsWithMine());

        Assert.assertEquals(assertionArray, fieldLogic.getCellsWithFlag());

        Assert.assertEquals(assertionArray, fieldLogic.getCellsWithQuestion());

        Assert.assertEquals(assertionArray, fieldLogic.getOpenCells());

        Assert.assertEquals(0, fieldLogic.getTotalOpenCells());

        Assert.assertEquals(FieldLogic.FieldMode.SQUARE, fieldLogic.getFieldMode());


    }


    // -------------------- Cell tests --------------------------

    @Test
    public void isContainsPointTest() {

        // ----------------------  Square ----------------------

        Cell cell = new Cell(new Point(0,0),50, FieldLogic.FieldMode.SQUARE);
        Assert.assertTrue(cell.isContainsPoint( new Point(0,0)));
        Assert.assertTrue(cell.isContainsPoint( new Point(50,50)));
        Assert.assertTrue(cell.isContainsPoint(new Point(25,25)));
        Assert.assertFalse(cell.isContainsPoint(new Point(51,0)));
        Assert.assertFalse(cell.isContainsPoint(new Point(0, 51)));

        // --------------------- Hexagonal -----------------------

        cell = new Cell(new Point(0, 100), 100, FieldLogic.FieldMode.HEXAGONAL);
        Assert.assertTrue(cell.isContainsPoint(new Point(50, 50)));
        Assert.assertTrue(cell.isContainsPoint(new Point(150, 50)));
        Assert.assertTrue(cell.isContainsPoint(new Point(50, 250)));
        Assert.assertTrue(cell.isContainsPoint(new Point(150, 250)));
        Assert.assertTrue(cell.isContainsPoint(new Point(100, 150)));

        Assert.assertFalse(cell.isContainsPoint(new Point(50, 49)));
        Assert.assertFalse(cell.isContainsPoint(new Point(150, 49)));
        Assert.assertFalse(cell.isContainsPoint(new Point(50, 251)));
        Assert.assertFalse(cell.isContainsPoint(new Point(150,251)));
    }

    // ------------------- ViewModel tests -----------------------

    @Test
    public void viewModelTest() {
        SharedViewModel viewModel = new SharedViewModel();

        Assert.assertEquals(FieldLogic.GameCondition.NOT_MINED, viewModel.getGameCondition());

        Assert.assertEquals(0, viewModel.getTotalOpenedCells());

        Assert.assertEquals(FieldLogic.FieldMode.HEXAGONAL, viewModel.getFieldMode());

        Assert.assertEquals(assertionArray, viewModel.getCellsWithMine());

        Assert.assertEquals(assertionArray, viewModel.getCellsWithFlag());

        Assert.assertEquals(assertionArray, viewModel.getCellsWithQuestion());

        Assert.assertEquals(assertionArray, viewModel.getOpenCells());

        Assert.assertEquals(PlayingField.CheckedButton.TOUCH, viewModel.getCheckedButton());

        Assert.assertEquals(MainActivity.ShownDialog.NONE, viewModel.getShownDialog());

        Assert.assertEquals(30, viewModel.getNumberOfMines());

        Assert.assertEquals(10, viewModel.getFieldWidth());

        Assert.assertEquals(10, viewModel.getFieldHeight());

    }
  }
