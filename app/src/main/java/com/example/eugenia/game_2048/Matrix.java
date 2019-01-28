package com.example.eugenia.game_2048;

//import java.awt.Point;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.Random;
public class Matrix {
    private final int CELLS_NUMBER = 4;
    private final int INIT_CELLS = 2;
    Point p; // p.x = j  p.y = i (индексы матрицы m[][])
    int m[][];
    ArrayList<Integer> allList; // одномерный массив - матрица m[][] в виде одномерного массива
    Random rnd;
     boolean victory;
    int score =0;
    public Matrix() {
        m = new int[][]{    {0, 0, 0, 0 },
                {0, 0, 0, 0 },
                {0, 0, 0, 0 },
                {0, 0, 0, 0 } };


        allList = new ArrayList<>();

        for( int i=0; i<m.length; i++)
            for( int j=0; j<m[i].length; j++ )
                setAllList(i,j,m[i][j]);
        rnd = new Random();
    }

    // занести в одномерный массив allList число, по индексу N, соответствующее m[i][j]
    void setAllList(int i, int j, int value) {
        int N; // index allList
        N = i* CELLS_NUMBER +j;
        allList.add(N, value);
    }

    // получить по индексу N массива allList индексы для матрицы m[][]
    Point getAllList( int N) {
        int i = N/ CELLS_NUMBER;
        int k = i* CELLS_NUMBER;
        int j = N-k;
        Point p = new Point(j,i);
        return p;
    }

    // добавить новое число 2
    boolean newStep() {
        if( !allList.contains(0)) // если нет свободных мест
            return false;
        // массив индексов свободных мест в allList (для быстрого поиска рандома)
        ArrayList<Integer> index0 = new ArrayList<>();

        for( int i=0; i<allList.size(); i++) {
            if( allList.get(i).compareTo(0) == 0) // место свободное
                index0.add(i);
        }

        int freeIndex = rnd.nextInt(index0.size()); // берем рандомный свободный индекс
        int indexInList= index0.get(freeIndex);
        allList.set(indexInList, INIT_CELLS); // заносим на свободное место 2
        // заносим и в матрицу m[][] эту 2
        Point p = getAllList( indexInList);
        m[p.y][p.x] = INIT_CELLS;

        return true;
    }

    boolean m_down( ) {
        for( int i=0; i<m.length; i++) {
            int[] arg = getColumn(i);
            int[] rab= new int[arg.length];  //меняем порядок элементов на обратный
            for(int a=0; a<rab.length;a++){
                rab[a]=arg[rab.length-a-1];
            }
            arg=rab;
            int[] result= moveLine(arg);          //сдвигаем
            for(int a=0; a<rab.length;a++){       //возвращаем исходный порядок
                rab[a]=result[rab.length-a-1];
            }
            result=rab;
            setColumn(i,result);
        }
        allList.clear(); // чистим одномерный массив
        for( int i=0; i<m.length; i++) // записываем новые значения
            for( int j=0; j<m[i].length; j++ )
                setAllList(i,j,m[i][j]);
        return newStep(); // добавляем (если можно) новую 2
    }

    boolean m_up( ) {
        for( int i=0; i<m.length; i++) {
            int[] arg = getColumn(i);
            int[] result= moveLine(arg);
            setColumn(i,result);
        }
        allList.clear(); // чистим одномерный массив
        for( int i=0; i<m.length; i++) // записываем новые значения
            for( int j=0; j<m[i].length; j++ )
                setAllList(i,j,m[i][j]);
        return newStep(); // добавляем (если можно) новую 2
    }

    boolean m_left( ) {
        for( int i=0; i<m.length; i++) {
            int[] arg = getRow(i);
            int[] result= moveLine(arg);
            setRow(i,result);
        }

        allList.clear(); // чистим одномерный массив
        for( int i=0; i<m.length; i++) // записываем новые значения
            for( int j=0; j<m[i].length; j++ )
                setAllList(i,j,m[i][j]);
        return newStep(); // добавляем (если можно) новую 2
    }
    boolean m_right( ) {
        for( int i=0; i<m.length; i++) {
            int[] arg = getRow(i);
            int[] rab= new int[arg.length];
            for(int a=0; a<rab.length;a++){
                rab[a]=arg[rab.length-a-1];
            }
            arg=rab;
            int[] result= moveLine(arg);
            for(int a=0; a<rab.length;a++){
                rab[a]=result[rab.length-a-1];
            }
            result=rab;
            setRow(i,result);
        }
        allList.clear(); // чистим одномерный массив
        for( int i=0; i<m.length; i++) // записываем новые значения
            for( int j=0; j<m[i].length; j++ )
                setAllList(i,j,m[i][j]);
        return newStep(); // добавляем (если можно) новую 2
    }

    int get( int i, int j) {
        return m[i][j];
    }

    //возвращает массив ячеек i-ой строки
    int[] getRow(int i){
        return m[i];
    }
    //меняет i-ую строку
    void setRow(int i,int[] newRow){
        m[i]=newRow;
    }
    //Возвращает i столбец
    int[] getColumn(int i){
        int[] res= new int[CELLS_NUMBER];
        for(int j = 0; j< CELLS_NUMBER; j++){
            res[j]=m[j][i];
        }
        return res;
    }

    //Меняет сотояние ячеек i столбца
    void setColumn(int i,int[] newColumn){
        for(int j = 0; j< CELLS_NUMBER; j++){
            m[j][i]=newColumn[j];
        }
    }

    private final int INCREASE_SCORE = 2;
    private final int CELL_2048 = 2048;

    private  int[] moveLine(int[]oldLine){

        int[]noZero= new int[oldLine.length];  //убираем нули

        int k=0;
        for (int i=0; i<oldLine.length;i++){
            if(oldLine[i]!=0){
                noZero[k]=oldLine[i];
                k++;
            }
        }
        for(int i=k; i<noZero.length; i++){    // чтобы массив не содержал null, заполним его нулями
            noZero[i]=0;
        }

        int[] newLine=new int[noZero.length];
        int f=0;
        int i=0;
        while (i<noZero.length){
            if((noZero[i]!=0)&&(i+1<noZero.length)&&(noZero[i]==noZero[i+1])){
                newLine[f]=noZero[i]*INCREASE_SCORE;
                score +=noZero[i]*INCREASE_SCORE;
                // если получили 2048 завершить игру
                if(newLine[f]==CELL_2048) victory = true;
                i++;
            } else{
                newLine[f]=noZero[i];
            }
            i++;
            f++;

        }

        for(int j=f; j<newLine.length; j++){
            newLine[f]=0;
        }



        return newLine;
    }
}


