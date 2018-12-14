package ru.gdcn.beastmaster64revelations.GameClass.Utilities;

import java.util.Random;

public class Utilities {

    public static Object getRandomElement(Object[] array, Random randomGenerator) {
        if (array.length == 0)
            return null;
        int index = randomGenerator.nextInt(array.length);
        return array[index];
    }

}
