package ru.spbstu.kspt.myhorsemove;

import android.graphics.Point;

import java.util.ArrayList;

public class Matr {
    final int sz = 8;
    Cell m[][];
    Point oldCell; // предыдущая ячейка
    ArrayList<Point> list = new ArrayList<Point>(); //список возможных ходов (новый для каждого хода)

    public Matr() {
        m = new Cell[sz][sz];
        for (int i=0; i<sz; i++)
            for (int j=0; j<sz; j++)
                m[i][j] = new Cell(); //под каждую ячейку выделяю память
        oldCell = new Point();
    }

    void clear() {
        boolean f = true;
        oldCell.x = oldCell.y = -1;
        boolean startBlack = false; //первая клетка в левом верхнем углу белая
        for (int i=0; i<sz; i++) {
            f=!f;
            if (!f)
                startBlack = false; //белая клетка
            else
                startBlack = true; //черная клетка
            for (int j=0; j<sz; j++) {
                m[i][j].setBlack(startBlack);
                startBlack = !startBlack;
                m[i][j].setNum(0);
                m[i][j].setHorse(false);
            }
        }
        list.clear(); //очищаю список возможных ходов - ходов нет
    }

    void setHorse(int i, int j) {
        if ( i<0 || i>7 || j<0 || j>7 ) //неверные координаты
            return;
        if (oldCell.y != -1) //лошадь уже хоть раз пошла
            m[oldCell.y][oldCell.x].setHorse(false); //в старых координатах убираю лошадь
       oldCell.x = j;
       oldCell.y = i;
       m[i][j].setHorse(true); //ставлю лошадь на новые координаты
    }

    void set (int i, int j, int num) { //ставить число в клетку (восстановить значение после лошади)
        if ( i<0 || i>7 || j<0 || j>7 ) //неверные координаты
            return;
        m[i][j].setNum(num);
    }

    boolean isSteps (int curi, int curj) { //принимает на вход ТЕКУЩИЕ координаты
        list.clear();
        //рассматриваю точки
        /*
                3     4
            1             2
                  cur
        */
        int newi = curi - 1;
        for (int k=2; k>0 && newi>=0; k--, newi--) {
            if (curj-k >= 0 && (m[newi][curj-k].getNum() == 0)) //если на клетку еще не ходили
                list.add(new Point(curj-k, newi));
            if (curj+k <sz && (m[newi][curj+k].getNum() == 0))
                list.add(new Point(curj+k, newi));
        }
        //то же самое для нижней части доски
        newi = curi + 1;
        for (int k=2; k>0 && newi<sz; k--, newi++) {
            if (curj-k >= 0 && (m[newi][curj-k].getNum() == 0))
                list.add(new Point(curj-k, newi));
            if (curj+k <sz && (m[newi][curj+k].getNum() == 0))
                list.add(new Point(curj+k, newi));
        }
        return !list.isEmpty(); //ходить можно только тогда, когда список ходов не пустой(есть хоть 1 вариант хода)
    }

    boolean step (int curi, int curj) { //сравнивает ход со списком корректных ходов
        Point p = new Point(curj, curi);
        if (!list.contains(p))
            return false;
        return true;
    }

    Cell get(int i, int j) {
        return m[i][j];
    }

}
