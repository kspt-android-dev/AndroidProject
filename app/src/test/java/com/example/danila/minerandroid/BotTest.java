package com.example.danila.minerandroid;

import org.junit.Test;

import static org.junit.Assert.*;

public class BotTest {


    private Logic logic;
    private Bot bot;


    @Test
    public void easyWinRate() {
        logic = new Logic(9, 9, 10);
        bot = new Bot(logic);
        int numberOfGames = 10000;
        for (int i = 0; i < numberOfGames; ) {
            bot.helpMeBot();
            if (logic.isGameOver()) {
                logic.reload();
                bot.reload();
                i++;
            }
        }
        assertEquals(bot.getWin() + bot.getLose(), numberOfGames);
        assertTrue((double) bot.getWin() / numberOfGames * 100 > 70);
        System.out.println("Easy mode = " + (double) bot.getWin() / numberOfGames * 100+"%");


    }

    @Test
    public void mediumWinRate(){
        logic = new Logic(16, 16, 40);
        bot = new Bot(logic);
        int numberOfGames = 10000;
        for (int i = 0; i < numberOfGames; ) {
            bot.helpMeBot();
            if (logic.isGameOver()) {
                logic.reload();
                bot.reload();
                i++;
            }
        }
        assertEquals(bot.getWin() + bot.getLose(), numberOfGames);
        assertTrue((double) bot.getWin() / numberOfGames * 100 > 45);
        System.out.println("Middle mode = " + (double) bot.getWin() / numberOfGames * 100+"%");



    }

    @Test
    public void expertWinRate() {
        logic = new Logic(30, 16, 99);
        bot = new Bot(logic);
        int numberOfGames = 1000;
        for (int i = 0; i < numberOfGames; ) {
            bot.helpMeBot();
            if (logic.isGameOver()) {
                logic.reload();
                bot.reload();
                i++;
            }
        }
        assertEquals(bot.getWin() + bot.getLose(), numberOfGames);
        assertTrue((double) bot.getWin() * 100 / numberOfGames > 2);
        System.out.println("Expert mode = " + (double) bot.getWin()*100 / numberOfGames+"%");

    }

    @Test
    public void easyStep() {
        int massMine[] = {3, 8, 9, 11, 13, 15};
        logic = new Logic(8, 2, massMine);
        bot = new Bot(logic);

        bot.check(0);
        bot.check(1);
        bot.check(2);
        bot.check(4);
        bot.check(5);
        bot.check(6);
        bot.check(7);
        bot.check(10);
        bot.check(12);
        bot.check(14);
        bot.helpMeBot();
        assertEquals(6, bot.getFindedMines());
        assertEquals(10, bot.getBotCells().size());
        assertTrue(logic.isGameOver());
    }

    @Test
    public void notEasyStep() {
        int massMine[] = {3, 5};
        logic = new Logic(3, 2, massMine);
        bot = new Bot(logic);
        for (int i = 0; i < 10; i++) {
            bot.check(0);
            bot.check(1);
            bot.check(2);
            while (!logic.isGameOver())
                bot.helpMeBot();
            assertEquals(bot.getBotCells().size(), logic.getLogicCells().length - massMine.length);
            logic.reloadLast();
            bot.reload();
        }
        assertEquals(10, bot.getWin());
        assertEquals(0, bot.getLose());

        int massMine2[] = {1, 5};
        logic = new Logic(2, 3, massMine2);
        bot = new Bot(logic);
        for (int i = 0; i < 10; i++) {
            bot.check(0);
            bot.check(2);
            bot.check(4);
            while (!logic.isGameOver())
                bot.helpMeBot();
            assertEquals(bot.getBotCells().size(), logic.getLogicCells().length - massMine.length);
            logic.reloadLast();
            bot.reload();
        }
        assertEquals(10, bot.getWin());
        assertEquals(0, bot.getLose());

        int massMine3[] = {5, 6};
        logic = new Logic(4, 2, massMine3);
        bot = new Bot(logic);
        for (int i = 0; i < 10; i++) {
            bot.check(0);
            bot.check(1);
            bot.check(2);
            bot.check(3);
            while (!logic.isGameOver())
                bot.helpMeBot();
            assertEquals(bot.getBotCells().size(), logic.getLogicCells().length - massMine3.length);
            logic.reloadLast();
            bot.reload();
        }
        assertEquals(10, bot.getWin());
        assertEquals(0, bot.getLose());


        int massMine4[] = {3, 5};
        logic = new Logic(2, 4, massMine4);
        bot = new Bot(logic);
        for (int i = 0; i < 10; i++) {
            bot.check(0);
            bot.check(2);
            bot.check(4);
            bot.check(6);
            while (!logic.isGameOver())
                bot.helpMeBot();
            assertEquals(bot.getBotCells().size(), logic.getLogicCells().length - massMine4.length);
            logic.reloadLast();
            bot.reload();
        }
        assertEquals(10, bot.getWin());
        assertEquals(0, bot.getLose());

    }


}

