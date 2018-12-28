package com.example.pc_alyona.bullsandcows;

import android.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class GameState {

    private List<Integer> generatedNumber;
    int numberSize;

    GameState(int size) {
        numberSize = size;
    }

    List<Integer> getNumber() {
        return generatedNumber;
    }

    //генерирование числа
    void generateNumber() {
        List<Integer> numbers = new ArrayList();
        for(Integer i = 0; i < 10; i++)
            numbers.add(i);
        Collections.shuffle(numbers);
        generatedNumber = numbers.subList(0, numberSize);
    }

    //проверка введенного числа на количество быков и коров
    Pair<Integer, Integer> checkNumber(String userNumber) {
        int bulls = 0;
        int cows = 0;
        List<Integer> listNumber = new ArrayList<>();
        for(int i = 0; i < userNumber.length(); i++)
            listNumber.add(Character.getNumericValue(userNumber.charAt(i)));
        for(int i = 0; i < userNumber.length(); i++) {
            for(int j = 0; j < generatedNumber.size(); j++)
                if(listNumber.get(i).equals(generatedNumber.get(j))) {
                    if(i == j)
                        bulls++;
                    else
                        cows++;
                }
        }
        return new Pair<>(bulls, cows);
    }

}
