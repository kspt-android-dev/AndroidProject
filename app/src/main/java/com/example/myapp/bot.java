package com.example.myapp;

import java.util.Random;

class bot {

    private final Logic logic = new Logic();

    private final Cell arrO3x3[] = {new Cell(163, 163, 1), new Cell(490, 163, 1), new Cell(817, 163, 1),
                              new Cell(163, 523, 1), new Cell(490, 523, 1), new Cell(817, 523, 1),
                              new Cell(163, 861, 1), new Cell(490, 861, 1), new Cell(817, 861, 1)};

    private final Cell arrX3x3[] = {new Cell(163, 163, 0), new Cell(490, 163, 0), new Cell(817, 163, 0),
                              new Cell(163, 523, 0), new Cell(490, 523, 0), new Cell(817, 523, 0),
                              new Cell(163, 861, 0), new Cell(490, 861, 0), new Cell(817, 861, 0)};


    private final Random random = new Random();


    void goBy3x3() {

        if (Logic.countStep != 9) {

            int index;

            do {
                index = random.nextInt(8);
            }
            while (logic.checkInGrid(arrO3x3[index]) || logic.checkInGrid(arrX3x3[index]));

            Logic.cells.add(arrO3x3[index]);
            Logic.countStep++;
        }
    }


    private final Cell arrO5x5[] = {new Cell(102, 104, 1), new Cell(297, 104, 1), new Cell(496, 104, 1), new Cell(695, 104, 1), new Cell(886, 104, 1),
                              new Cell(102, 312, 1), new Cell(297, 312, 1), new Cell(496, 312, 1), new Cell(695, 312, 1), new Cell(886, 312, 1),
                              new Cell(102, 525, 1), new Cell(297, 525, 1), new Cell(496, 525, 1), new Cell(695, 525, 1), new Cell(886, 525, 1),
                              new Cell(102, 735, 1), new Cell(297, 735, 1), new Cell(496, 735, 1), new Cell(695, 735, 1), new Cell(886, 735, 1),
                              new Cell(102, 946, 1), new Cell(297, 946, 1), new Cell(496, 946, 1), new Cell(695, 946, 1), new Cell(886, 946, 1)};



    private final Cell arrX5x5[] = {new Cell(102, 104, 0), new Cell(297, 104, 0), new Cell(496, 104, 0), new Cell(695, 104, 0), new Cell(886, 104, 0),
                              new Cell(102, 312, 0), new Cell(297, 312, 0), new Cell(496, 312, 0), new Cell(695, 312, 0), new Cell(886, 312, 0),
                              new Cell(102, 525, 0), new Cell(297, 525, 0), new Cell(496, 525, 0), new Cell(695, 525, 0), new Cell(886, 525, 0),
                              new Cell(102, 735, 0), new Cell(297, 735, 0), new Cell(496, 735, 0), new Cell(695, 735, 0), new Cell(886, 735, 0),
                              new Cell(102, 946, 0), new Cell(297, 946, 0), new Cell(496, 946, 0), new Cell(695, 946, 0), new Cell(886, 946, 0)};


    void goBy5x5() {

        if (Logic.countStep != 25) {

            int index;

            do {
                index = random.nextInt(24);
            }
            while (logic.checkInGrid(arrO5x5[index]) || logic.checkInGrid(arrX5x5[index]));

            Logic.cells.add(arrO5x5[index]);
            Logic.countStep++;
        }
    }
}
