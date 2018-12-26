package com.example.danila.minerandroid;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class LogicTest {
    private Logic logic;
    private int numbersOfMines[] = {1, 2, 3, 4, 20, 45, 63, 72, 73, 76};


    private void constructorWithMassiv() {
        logic = new Logic(9, 9, numbersOfMines);

    }

    private void constructorWithDigit() {
        int minesDigit = 10;
        logic = new Logic(9, 9, minesDigit);
    }

    @Test
    public void setConditions() throws Exception {
        int minesDigit[] = {0, 1};
        logic = new Logic(3, 3, minesDigit);
        assertEquals(9, logic.getLogicCells()[0].getConditon());
        assertEquals(9, logic.getLogicCells()[1].getConditon());
        assertEquals(1, logic.getLogicCells()[2].getConditon());
        assertEquals(2, logic.getLogicCells()[3].getConditon());
        assertEquals(2, logic.getLogicCells()[4].getConditon());
        assertEquals(1, logic.getLogicCells()[5].getConditon());
        assertEquals(0, logic.getLogicCells()[6].getConditon());
        assertEquals(0, logic.getLogicCells()[7].getConditon());
        assertEquals(0, logic.getLogicCells()[8].getConditon());


    }


    @Test
    public void reload() throws Exception {
        constructorWithMassiv();
        LogicCell lastMines[] = new LogicCell[logic.getMinesDigit()];
        System.arraycopy(logic.getBombs(), 0, lastMines, 0, lastMines.length);
        logic.reload();
        assertFalse(Arrays.equals(lastMines, logic.getBombs()));

    }

    @Test
    public void reload1() throws Exception {
        constructorWithMassiv();
        LogicCell lastMines[] = new LogicCell[logic.getMinesDigit()];
        System.arraycopy(logic.getBombs(), 0, lastMines, 0, lastMines.length);
        logic.reload(numbersOfMines);
        assertTrue(Arrays.equals(lastMines, logic.getBombs()));

    }

    @Test
    public void reloadLast() throws Exception {
        constructorWithMassiv();
        LogicCell lastMines[] = new LogicCell[logic.getMinesDigit()];
        System.arraycopy(logic.getBombs(), 0, lastMines, 0, lastMines.length);
        logic.reloadLast();
        assertTrue(Arrays.equals(lastMines, logic.getBombs()));

    }


    @Test
    public void getMinesDigit() throws Exception {
        constructorWithMassiv();
        assertEquals(logic.getMinesDigit(), logic.getBombs().length);
        constructorWithDigit();
        assertEquals(logic.getMinesDigit(), logic.getBombs().length);
    }


    @Test
    public void checkGameCondition() throws Exception {
        int numbersOfMines[] = {2, 3};
        logic = new Logic(2, 2, numbersOfMines);
        assertEquals(logic.checkGameCondition(), 0);
        logic.getLogicCells()[0].setChecked(true);
        assertEquals(logic.checkGameCondition(), 0);

        logic.getLogicCells()[1].setChecked(true);
        assertEquals(logic.checkGameCondition(), 0);
        logic.getLogicCells()[2].setFlag(true);
        assertEquals(logic.checkGameCondition(), 0);
        logic.getLogicCells()[3].setFlag(true);
        assertEquals(logic.checkGameCondition(), 1);


    }

    @Test
    public void isWin() throws Exception {
        int numbersOfMines[] = {2, 3};
        logic = new Logic(2, 2, numbersOfMines);
        assertFalse(logic.isWin());
        logic.getLogicCells()[0].setChecked(true);
        assertFalse(logic.isWin());
        logic.getLogicCells()[1].setChecked(true);
        assertFalse(logic.isWin());
        logic.getLogicCells()[2].setFlag(true);
        assertFalse(logic.isWin());
        logic.getLogicCells()[3].setFlag(true);
        assertTrue(logic.isWin());
    }


}