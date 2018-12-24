package com.logiccombine.artmate.logiccombine;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InGameTest {

    private InGame inGame;

    @Before
    public  void  setUp() {
        inGame = new InGame();
    }

    @Test
    public void countResultTests() {
        inGame = new InGame();
        int[] operations1 = {1,1,1,1,1,6,1,1};
        assertEquals(99, inGame.countResult(operations1), 0);
        int[] operations2 = {5,3,1,1,1,3,3,1};
        assertEquals(357, inGame.countResult(operations2), 0);
        int[] operations3 = {6,6,3,1,6,6,1,1};
        assertEquals(1076, inGame.countResult(operations3), 0);
        int[] operations4 = {3,1,1,1,3,6,6,1};
        assertEquals(3408, inGame.countResult(operations4), 0);
        int[] operations5 = {1,5,3,5,1,1,1,6};
        assertEquals(8295, inGame.countResult(operations5), 0);
    }
}