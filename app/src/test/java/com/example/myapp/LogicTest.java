package com.example.myapp;

import org.junit.Test;

import static org.junit.Assert.*;

public class LogicTest {

    private final Logic logic = new Logic();

    @Test
    public void checkWin3x3test() {

        boolean actualValue;

        // победа ноликов верхняя горизонталь
        Logic.cells.add(new Cell(163, 163, 1));
        Logic.cells.add(new Cell(490, 163, 1));
        Logic.cells.add(new Cell(817, 163, 1));

        actualValue = logic.checkWin3x3();
        assertTrue(actualValue);
        Logic.cells.clear();


        // победа крестиков нижняя горизонталь
        Logic.cells.add(new Cell(163, 861, 0));
        Logic.cells.add(new Cell(490, 861, 0));
        Logic.cells.add(new Cell(817, 861, 0));

        actualValue = logic.checkWin3x3();
        assertTrue(actualValue);
        Logic.cells.clear();

        // победа нолииков левая вертикаль
        Logic.cells.add(new Cell(163, 163, 1));
        Logic.cells.add(new Cell(163, 523, 1));
        Logic.cells.add(new Cell(163, 861, 1));

        actualValue =  logic.checkWin3x3();
        assertTrue(actualValue);
        Logic.cells.clear();

        // победа крестиков \ диагональ
        Logic.cells.add(new Cell(163, 163, 0));
        Logic.cells.add(new Cell(490, 523, 0));
        Logic.cells.add(new Cell(817, 861, 0));
        actualValue =  logic.checkWin3x3();
        assertTrue(actualValue);
        Logic.cells.clear();

        // не победа
        Logic.cells.add(new Cell(10, 10, 0));
        Logic.cells.add(new Cell(100, 100, 0));
        Logic.cells.add(new Cell(1000, 1000, 0));
        actualValue =  logic.checkWin3x3();
        assertFalse(actualValue);
        Logic.cells.clear();

    }

    @Test
    public void checkWin5x5test() {

        boolean actualValue;

        // победа крестиков верхняя горизонталь
        Logic.cells.add(new Cell(102, 104, 0));
        Logic.cells.add(new Cell(297, 104, 0));
        Logic.cells.add(new Cell(496, 104, 0));
        Logic.cells.add(new Cell(695, 104, 0));
        Logic.cells.add(new Cell(886, 104, 0));
        actualValue = logic.checkWin5x5();
        assertTrue(actualValue);
        Logic.cells.clear();


        // победа нолииков нижняя горизонталь
        Logic.cells.add(new Cell(102, 946, 1));
        Logic.cells.add(new Cell(297, 946, 1));
        Logic.cells.add(new Cell(496, 946, 1));
        Logic.cells.add(new Cell(695, 946, 1));
        Logic.cells.add(new Cell(886, 946, 1));
        actualValue =  logic.checkWin5x5();
        assertTrue(actualValue);
        Logic.cells.clear();

        // победа крестиков левая вертикаль
        Logic.cells.add(new Cell(102, 104, 0));
        Logic.cells.add(new Cell(102, 312, 0));
        Logic.cells.add(new Cell(102, 525, 0));
        Logic.cells.add(new Cell(102, 735, 0));
        Logic.cells.add(new Cell(102, 946, 0));
        actualValue =  logic.checkWin5x5();
        assertTrue(actualValue);
        Logic.cells.clear();

        // победа ноликов \ диагональ
        Logic.cells.add(new Cell(102, 104, 1));
        Logic.cells.add(new Cell(297, 312, 1));
        Logic.cells.add(new Cell(496, 525, 1));
        Logic.cells.add(new Cell(695, 735, 1));
        Logic.cells.add(new Cell(886, 946, 1));
        actualValue =  logic.checkWin5x5();
        assertTrue(actualValue);
        Logic.cells.clear();

        // не победа
        Logic.cells.add(new Cell(10, 10, 1));
        Logic.cells.add(new Cell(100, 100, 1));
        Logic.cells.add(new Cell(1000, 1000, 1));
        Logic.cells.add(new Cell(10000, 1000, 1));
        Logic.cells.add(new Cell(100000, 1000, 1));
        actualValue =  logic.checkWin5x5();
        assertFalse(actualValue);
        Logic.cells.clear();
    }
}