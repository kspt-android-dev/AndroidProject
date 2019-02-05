package com.julia.tag;

import com.julia.tag.game.Directions;
import com.julia.tag.game.PlayField;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayFieldTest {

    @Test
    public void getDirection() {
        Integer field[][] = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 0, 11}, {12, 13, 14, 15}};

        PlayField playField = new PlayField();
        playField.setField(field);
        Assert.assertEquals(Directions.NONE, playField.getDirection(0));
        assertEquals(Directions.NONE, playField.getDirection(4));
        assertEquals(Directions.NONE, playField.getDirection(12));
        assertEquals(Directions.UP, playField.getDirection(14));
        assertEquals(Directions.LEFT, playField.getDirection(11));
        assertEquals(Directions.RIGHT, playField.getDirection(9));
        assertEquals(Directions.DOWN, playField.getDirection(6));
    }

    @Test
    public void swap() {
        Integer field[][] = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 0, 11}, {12, 13, 14, 15}};

        PlayField playField = new PlayField();
        playField.setField(new Integer[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 0, 11}, {12, 13, 14, 15}});
        assertTrue(equalsDoubleArray(field, playField.getField()));
        playField.swap(9);
        assertFalse(equalsDoubleArray(field, playField.getField()));
        assertTrue(equalsDoubleArray(
                new Integer[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 0, 10, 11}, {12, 13, 14, 15}},
                playField.getField()
        ));
        playField.swap(5);
        assertFalse(equalsDoubleArray(field, playField.getField()));
        assertTrue(equalsDoubleArray(
                new Integer[][]{{1, 2, 3, 4}, {5, 0, 7, 8}, {9, 6, 10, 11}, {12, 13, 14, 15}},
                playField.getField()
        ));
    }

    private boolean equalsDoubleArray(Integer[][] arr1, Integer[][] arr2){
        for (int i = 0; i < arr1.length; i++) {
            for (int j = 0; j < arr1.length; j++) {
                if (!arr1[i][j].equals(arr2[i][j]))
                    return false;
            }
        }
        return true;
    }

    @Test
    public void getCell() {
        Integer field[][] = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 0, 11}, {12, 13, 14, 15}};

        PlayField playField = new PlayField();
        playField.setField(field);
        assertEquals(1, playField.getCell(0));
        assertEquals(15, playField.getCell(15));
        assertEquals(14, playField.getCell(14));
        assertEquals(10, playField.getCell(9));
    }

    @Test
    public void getEmpty() {
        Integer field[][] = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 0, 11}, {12, 13, 14, 15}};

        PlayField playField = new PlayField();
        playField.setField(field);
        assertEquals(10, playField.getEmpty());
        playField.swap(9);
        assertEquals(9, playField.getEmpty());
        playField.swap(10);
        assertEquals(10, playField.getEmpty());
        playField.swap(11);
        assertEquals(11, playField.getEmpty());
        playField.swap(15);
        assertEquals(15, playField.getEmpty());
    }

    @Test
    public void isWin() {
        Integer field[][] = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 0, 11}, {12, 13, 14, 15}};

        PlayField playField = new PlayField();
        playField.setField(field);
        assertFalse(playField.isWin());
        playField.setField(new Integer[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 0}});
        assertTrue(playField.isWin());
    }
}