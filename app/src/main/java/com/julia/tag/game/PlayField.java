package com.julia.tag.game;

import android.content.SharedPreferences;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayField implements Serializable {

    private static final int SIZE_LINE = 4;
    public static final String KEY_MOVES = "moves";


    private Integer field[][]; //Игровое поле
    private int moves; //Количество ходов

    public PlayField() {
        this.field = generateFiled();
        this.moves = 0;
    }

    //Находим направление aka проверяем вохможен ли ход
    public Directions getDirection(int position) {
        int i = position / SIZE_LINE;
        int j = position % SIZE_LINE;

        if (field[i + 1 < SIZE_LINE ? i + 1 : i][j] == 0)
            return Directions.DOWN;
        else if (field[i][j + 1 < SIZE_LINE ? j + 1 : j] == 0)
            return Directions.RIGHT;
        else if (field[i][j - 1 >= 0 ? j - 1 : j] == 0)
            return Directions.LEFT;
        else if (field[i - 1 >= 0 ? i - 1 : i][j] == 0)
            return Directions.UP;
        else return Directions.NONE;
    }

    public void swap(int oldPosition) {
        setCell(getEmpty(), getCell(oldPosition));
        setCell(oldPosition, 0);
        moves++;
    }

    private void setCell(int position, int value) {
        field[position / SIZE_LINE][position % SIZE_LINE] = value;
    }

    public int getSize() {
        return SIZE_LINE * SIZE_LINE;
    }

    public int getCell(int position) {
        return field[position / SIZE_LINE][position % SIZE_LINE];
    }

    public int getEmpty() {
        for (int i = 0; i < SIZE_LINE; i++) {
            for (int j = 0; j < SIZE_LINE; j++) {
                if (field[i][j] == 0) {
                    return i * SIZE_LINE + j;
                }
            }
        }
        return -1;
    }

    public int getMoves() {
        return moves;
    }

    /*
    Сохраняем состояние игрового поля и количестов ходов.
    Ключом для значения каждой ячейки является ее порядковый номер от 0 до 15 (считая слева сверху).
     */
    public void save(SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i = 0; i < SIZE_LINE; i++) {
            for (int j = 0; j < SIZE_LINE; j++) {
                editor.putInt(String.valueOf(i * SIZE_LINE + j), field[i][j]);
            }
        }
        editor.putInt(KEY_MOVES, moves);
        editor.apply();
    }

    //Загружаем данные о поле. Ключом для значения каждой ячейки является ее порядковый номер от 0 до 15 (считая слева сверху).
    public PlayField load(SharedPreferences sharedPreferences) {
        moves = sharedPreferences.getInt(KEY_MOVES, 0);
        for (int i = 0; i < SIZE_LINE; i++) {
            for (int j = 0; j < SIZE_LINE; j++) {
                field[i][j] = sharedPreferences.getInt(String.valueOf(i * SIZE_LINE + j), -1);
                if (field[i][j] == -1) {
                    field = generateFiled();
                    return this;
                }
            }
        }
        return this;
    }

    public boolean isWin() {
        for (int i = 0; i < SIZE_LINE; i++) {
            for (int j = 0; j < SIZE_LINE; j++) {
                if (i == SIZE_LINE - 1 && j == SIZE_LINE - 1)
                    break;
                if (field[i][j] != i * SIZE_LINE + j + 1)
                    return false;
            }
        }
        return true;
    }

    //Задает правильную последовательность пятнашек. Для тестирования.
    public void setWin() {
        for (int i = 0; i < SIZE_LINE; i++) {
            for (int j = 0; j < SIZE_LINE; j++) {
                if (i == SIZE_LINE - 1 && j == SIZE_LINE - 1) {
                    field[i][j] = 0;
                    return;
                }
                field[i][j] = i * SIZE_LINE + j + 1;
            }
        }
    }

    //Проверка: решается ли данная пятнашка
    private boolean isSolvable(Integer[][] test) {
        int count = 0;
        int zero = 15; //до последней позиции цикл не доходит, поэтому инициализируем именно последней позицией
        for (int i = 0; i < SIZE_LINE * SIZE_LINE - 1; i++) {
            if (test[i / SIZE_LINE][i % SIZE_LINE] == 0) {
                zero = i;
                continue;
            }
            for (int j = i + 1; j < SIZE_LINE * SIZE_LINE; j++) {
                if (test[j / SIZE_LINE][j % SIZE_LINE] == 0)
                    continue;
                if (test[i / SIZE_LINE][i % SIZE_LINE] > test[j / SIZE_LINE][j % SIZE_LINE]) {
                    count++;
                }
            }
        }
        return (count + (zero / SIZE_LINE + 1)) % 2 == 0;
    }

    private Integer[][] generateFiled() {
        Integer result[][] = new Integer[SIZE_LINE][SIZE_LINE];

        List<Integer> temp = new ArrayList<>();
        Random random = new Random();
        int t = 0;
        for (int i = 0; i < SIZE_LINE; i++) {
            for (int j = 0; j < SIZE_LINE; j++) {
                if (i == 0 && j == 0 || i == SIZE_LINE - 1 && j == SIZE_LINE - 1)
                    continue;
                do {
                    t = random.nextInt(14);
                    t += 1;
                } while (temp.contains(t));
                temp.add(t);
                result[i][j] = t;
            }
        }
        result[0][0] = 0;
        result[SIZE_LINE - 1][SIZE_LINE - 1] = 15;
        if (!isSolvable(result)) {
            result[0][0] = 15;
            result[SIZE_LINE - 1][SIZE_LINE - 1] = 0;
        }

        return result;
    }

    //Используется исключительно для тестов
    public Integer[][] getField() {
        return field;
    }

    //Используется исключительно для тестов
    public void setField(Integer[][] field) {
        this.field = field;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < SIZE_LINE; i++) {
            for (int j = 0; j < SIZE_LINE; j++) {
                stringBuilder.append(field[i][j]).append(" ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
