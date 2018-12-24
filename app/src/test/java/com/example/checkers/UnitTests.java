package com.example.checkers;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class UnitTests {

    //---------------- gameLogic -------------------
    @Test
    public void startNewGameTest() {
        GameLogic gameLogic = new GameLogic();

        gameLogic.setParams(1080, new Point(100, 100));
        gameLogic.startNewGame();

        ArrayList<Integer> expectedWhiteCheckersCells = setStartNewGameWhiteCells();
        ArrayList<Integer> expectedBlackCheckersCells = setStartNewGameBlackCells();
        assertEquals(expectedWhiteCheckersCells, gameLogic.getWiteCheckersPlaces());
        assertEquals(expectedBlackCheckersCells, gameLogic.getBlackCheckersPlecas());

    }

    private ArrayList<Integer> setStartNewGameWhiteCells() {
        ArrayList<Integer> result = new ArrayList<>();
        result.add(61);
        result.add(63);
        result.add(65);
        result.add(67);
        result.add(69);
        result.add(70);
        result.add(72);
        result.add(74);
        result.add(76);
        result.add(78);
        result.add(81);
        result.add(83);
        result.add(85);
        result.add(87);
        result.add(89);
        result.add(90);
        result.add(92);
        result.add(94);
        result.add(96);
        result.add(98);
        return result;
    }

    private ArrayList<Integer> setStartNewGameBlackCells() {
        ArrayList<Integer> result = new ArrayList<>();
        result.add(1);
        result.add(3);
        result.add(5);
        result.add(7);
        result.add(9);
        result.add(10);
        result.add(12);
        result.add(14);
        result.add(16);
        result.add(18);
        result.add(21);
        result.add(23);
        result.add(25);
        result.add(27);
        result.add(29);
        result.add(30);
        result.add(32);
        result.add(34);
        result.add(36);
        result.add(38);
        return result;
    }

    @Test
    public void recoverParamsTest() {
        GameLogic gameLogic = new GameLogic();
        gameLogic.setParams(1080, new Point(100, 100));
        gameLogic.startNewGame();

        Bundle savedInstanceState = setFieldConfiguration();

        gameLogic.recoverParams(savedInstanceState);

        ArrayList<Integer> witeChechersPlaces = new ArrayList<>();
        witeChechersPlaces.add(10);
        witeChechersPlaces.add(17);
        ArrayList<Integer> blackCheckersPlaces = new ArrayList<>();
        blackCheckersPlaces.add(12);
        blackCheckersPlaces.add(16);
        ArrayList<Integer> whiteCrownPlaces = new ArrayList<>();
        ArrayList<Integer> blackCrownsPlaces = new ArrayList<>();
        blackCrownsPlaces.add(5);
        blackCrownsPlaces.add(7);

        assertEquals(witeChechersPlaces, gameLogic.getWiteCheckersPlaces());
        assertEquals(blackCheckersPlaces, gameLogic.getBlackCheckersPlecas());
        assertEquals(whiteCrownPlaces, gameLogic.getWhiteCrownPlaces());
        assertEquals(blackCrownsPlaces, gameLogic.getBlackCrownsPlaces());
        assertEquals(1, gameLogic.getNumbOfBlackDead());
        assertEquals(7, gameLogic.getNumbOfWiteDead());
        assertEquals(Color.BLACK, gameLogic.getUpperPlayerColor());
        assertEquals(Color.WHITE, gameLogic.getBottomPlayerColor());
        assertEquals(GameLogic.TurnColor.BLACK, gameLogic.getTurnColor());
        assertEquals(GameLogic.TurnSide.TOP,gameLogic.getTurnSide());
        assertFalse(false);
    }

    private Bundle setFieldConfiguration() {
        ArrayList<Integer> witeChechersPlaces = new ArrayList<>();
        witeChechersPlaces.add(10);
        witeChechersPlaces.add(17);
        ArrayList<Integer> blackCheckersPlaces = new ArrayList<>();
        blackCheckersPlaces.add(12);
        blackCheckersPlaces.add(16);
        ArrayList<Integer> whiteCrownPlaces = new ArrayList<>();
        ArrayList<Integer> blackCrownsPlaces = new ArrayList<>();
        blackCrownsPlaces.add(5);
        blackCrownsPlaces.add(7);
        Integer numbOfBlackDead = 1;
        Integer numbOfWiteDead = 7;
        Integer upperPlayerColor = Color.BLACK;
        Integer bottomPlayerColor = Color.WHITE;
        String turnColor = "BLACK";
        String turnSide = "TOP";
        Boolean isSomeoneKilledJustNow = false;

        return setSavedInstanceState(witeChechersPlaces,
                blackCheckersPlaces,
                whiteCrownPlaces,
                blackCrownsPlaces,
                numbOfBlackDead,
                numbOfWiteDead,
                upperPlayerColor,
                bottomPlayerColor,
                turnColor,
                turnSide,
                isSomeoneKilledJustNow);

    }

    private Bundle setSavedInstanceState(ArrayList<Integer> witeCheckersPlaces,
                                         ArrayList<Integer> blackCheckersPlecas,
                                         ArrayList<Integer> whiteCrownPlaces,
                                         ArrayList<Integer> blackCrownsPlaces,
                                         Integer numbOfBlackDead,
                                         Integer numbOfWiteDead,
                                         Integer upperPlayerColor,
                                         Integer bottomPlayerColor,
                                         String turnColor,
                                         String turnSide,
                                         Boolean isSomeoneKilledJustNow) {
        Bundle result = new Bundle();

        result.putIntegerArrayList("whiteCheckers", witeCheckersPlaces);
        result.putIntegerArrayList("blackCheckers", blackCheckersPlecas);
        //клетки с дамками
        result.putIntegerArrayList("whiteCrowns", whiteCrownPlaces);
        result.putIntegerArrayList("blackCrowns", blackCrownsPlaces);
        // количество битых
        result.putInt("numbOfBlackDead", numbOfBlackDead);
        result.putInt("numbOfWiteDead", numbOfWiteDead);
        // цвета игроков
        result.putInt("upperPlayerColor", upperPlayerColor);
        result.putInt("bottomPlayerColor", bottomPlayerColor);
        // чей ход
        result.putString("turnColor", turnColor);
        result.putString("turnSide", turnSide);

        result.putBoolean("someOneKilled", isSomeoneKilledJustNow);

        return result;
    }

    @Test
    public void hideCheckerTest() {
        final int fieldSize = 1200;
        final int padding = 100;
        GameLogic gameLogic = new GameLogic();

        gameLogic.setParams(fieldSize, new Point(padding, padding));
        Bundle savedInstanceState = setFieldConfiguration();
        gameLogic.recoverParams(savedInstanceState);

        // прячем дамку
        gameLogic.hideChecker(new Point(padding+fieldSize/12+5*fieldSize/12+10,padding+fieldSize/12+10));
        ArrayList<Integer> blackCrownsPlaces = new ArrayList<>();
        blackCrownsPlaces.add(7);
        assertEquals(blackCrownsPlaces, gameLogic.getBlackCrownsPlaces());

        // прячем шашку
        gameLogic.hideChecker(new Point(padding+fieldSize/12+10,padding+fieldSize/12+fieldSize/12+10));
        ArrayList<Integer> witeChechersPlaces = new ArrayList<>();
        witeChechersPlaces.add(17);
        assertEquals(witeChechersPlaces, gameLogic.getWiteCheckersPlaces());


    }

    @Test
    public void suitableForGrabTest() {
        final int fieldSize = 1200;
        final int padding = 100;
        GameLogic gameLogic = new GameLogic();

        gameLogic.setParams(fieldSize, new Point(padding, padding));
        Bundle savedInstanceState = setFieldConfiguration();
        gameLogic.recoverParams(savedInstanceState);

        // взять своего хода (12)
        assertTrue(gameLogic.cellSuitableForGrab(new Point(padding+3*fieldSize/12+10, padding+fieldSize/12+fieldSize/12+10)));

        // взять чужого хода (10)
        assertFalse(gameLogic.cellSuitableForGrab(new Point(padding+fieldSize/12+10,padding+fieldSize/12+fieldSize/12+10)));

        // взять пустую клетку (0)
        assertFalse(gameLogic.cellSuitableForGrab(new Point(padding+fieldSize/12+10,padding+fieldSize/12+10)));

    }

}