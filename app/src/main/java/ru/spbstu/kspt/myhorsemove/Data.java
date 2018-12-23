package ru.spbstu.kspt.myhorsemove;

public class Data {
   final Matr matr;
    int count,  //количество сделанных ходов
        oldi, oldj; //старое значение клетки, где стоял конь (чтобы выводить туда count)

    public Data() {
        matr = new Matr();
        matr.clear();
        count = 0;
        oldi = -1;
        oldj = -1;
    }

}
