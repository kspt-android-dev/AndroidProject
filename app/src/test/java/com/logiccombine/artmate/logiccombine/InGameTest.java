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
        Calculations calculations = new Calculations();
        inGame = new InGame();
        arithmeticOperations[] operations1 = {arithmeticOperations.PLUS,arithmeticOperations.PLUS,arithmeticOperations.PLUS,arithmeticOperations.PLUS,arithmeticOperations.PLUS,arithmeticOperations.CONNECT,arithmeticOperations.PLUS,arithmeticOperations.PLUS};
        assertEquals(99, calculations.countResult(operations1), 0);
        arithmeticOperations[] operations2 = {arithmeticOperations.POWER,arithmeticOperations.MULTIPLY,arithmeticOperations.PLUS,arithmeticOperations.PLUS,arithmeticOperations.PLUS,arithmeticOperations.MULTIPLY,arithmeticOperations.MULTIPLY,arithmeticOperations.PLUS};
        assertEquals(357, calculations.countResult(operations2), 0);
        arithmeticOperations[] operations3 = {arithmeticOperations.CONNECT,arithmeticOperations.CONNECT,arithmeticOperations.MULTIPLY,arithmeticOperations.PLUS,arithmeticOperations.CONNECT,arithmeticOperations.CONNECT,arithmeticOperations.PLUS,arithmeticOperations.PLUS};
        assertEquals(1076, calculations.countResult(operations3), 0);
        arithmeticOperations[] operations4 = {arithmeticOperations.MULTIPLY,arithmeticOperations.PLUS,arithmeticOperations.PLUS,arithmeticOperations.PLUS,arithmeticOperations.MULTIPLY,arithmeticOperations.CONNECT,arithmeticOperations.CONNECT,arithmeticOperations.PLUS};
        assertEquals(3408, calculations.countResult(operations4), 0);
        arithmeticOperations[] operations5 = {arithmeticOperations.PLUS,arithmeticOperations.POWER,arithmeticOperations.MULTIPLY,arithmeticOperations.POWER,arithmeticOperations.PLUS,arithmeticOperations.PLUS,arithmeticOperations.PLUS,arithmeticOperations.CONNECT};
        assertEquals(8295, calculations.countResult(operations5), 0);
    }
}