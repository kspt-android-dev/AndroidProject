package ru.spbstu.kspt.myhorsemove;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void test_isSteps() {
        Matr matr = new Matr();
        matr.clear();
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                matr.set(i, j, 1); //заполняю всю матрицу единицами - некуда ходить
            }
        }
        boolean step1 = matr.isSteps(2, 4); //ходов нет - false
        boolean step3 = matr.isSteps(1, 6); //ходов нет - false
        matr.set(7, 5, 0); //есть ход из клетки [3,6], [5,6], [5,4]
        boolean step2 = matr.isSteps(6, 3); //ход есть - true
        boolean step4 = matr.isSteps(5, 6); //ход есть - true
        boolean step5 = matr.isSteps(5, 4); //ход есть - true
        boolean step6 = matr.isSteps(6, 7); //ход есть - true
        assertFalse(step1);
        assertTrue(step2);
        assertFalse(step3);
        assertTrue(step4);
        assertTrue(step5);
        assertTrue(step6);
    }

    @Test
    public void test_getCell() {
        Matr matr = new Matr();
        matr.clear();
        matr.set(1, 1, 13);
        matr.setHorse(1, 2);
        boolean horse = matr.get(1, 2).isHorse(); //true
        boolean horse2 = matr.get(1, 3).isHorse(); //false
        int num = matr.get(1, 1).getNum(); //13
        boolean black = matr.get(0,1).isBlack(); //true
        assertTrue(horse);
        assertFalse(horse2);
        assertTrue(black);
        assertEquals(13, num);
    }

}