package ru.spbstu.kspt.myhorsemove;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class FieldView extends View {

    private FieldActivity field;
    private Paint paint;
    private Paint text;
    private int colField;
    private Bitmap imWhite;
    private Bitmap imBlack;
    private Bitmap imHorse;
    private Bitmap imButStart;
    private Matr matr;
    private final int LEFT = 70; //35х2 - отступ сверху в пикселях
    private final int UP = 70;
    final int sz = 8;
    private final int CELL = 70; //размер ячейки(картинки) в пикселях
    private String alphabet[] = {"a", "b", "c", "d", "e", "f", "g", "h"};
    private int count = 0; //количество сделанных ходов
    private int oldi, oldj; //старое значение клетки, где стоял конь (чтобы выводить туда count)
    private boolean fgame = true; //идёт или не идёт игра (true по умолчанию - чтобы при начальной отрисовке не выводить count)
    private Cell cell = new Cell();
    //координаты кнопок
    private Point verticButStartLeft, verticButStartRight, horizButStartLeft, horizButStartRight; //300x100 мм - размер
    private int max = 1; //лучший результат
    private boolean frec = false; //был ли рекорд
    //координаты текста
    private Point verticTxt, horizTxt, txt;

    public FieldView(Context context) {
        super(context);
        field = (FieldActivity) context;
        paint = new Paint();
        text = new Paint();
        matr = new Matr();
        verticTxt = new Point(0, 900);
        horizTxt = new Point(LEFT+8*CELL+20, UP+300);
        txt = new Point(0,0);
        Resources res = getContext().getResources();
        colField = res.getColor(R.color.colorField);
        imBlack = BitmapFactory.decodeResource(field.getResources(), R.drawable.black35);
        imWhite = BitmapFactory.decodeResource(field.getResources(), R.drawable.white35);
        imHorse = BitmapFactory.decodeResource(field.getResources(), R.drawable.horse);
        imButStart = BitmapFactory.decodeResource(field.getResources(), R.drawable.start);
        verticButStartLeft = new Point(LEFT+5*CELL, UP+8*CELL+100);
        verticButStartRight = new Point(verticButStartLeft.x+imButStart.getWidth(), verticButStartLeft.y+imButStart.getHeight());
        horizButStartLeft = new Point(LEFT+8*CELL+300, UP+100);
        horizButStartRight = new Point(horizButStartLeft.x+imButStart.getWidth(), horizButStartLeft.y+imButStart.getHeight());
        newGame();
        setEnabled(true);
        setClickable(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(colField);
        canvas.drawPaint(paint);

        text.setAntiAlias(true); //прозрачный фон текста
        text.setColor(Color.BLACK);
        text.setTextSize(50); //размер текста в пикселях

        for (int i = 0; i < sz; i++) {
            canvas.drawText(alphabet[i], LEFT + 20 + i * CELL, UP - 10, text);
        }
        for (int j = sz - 1; j >= 0; j--) {
            canvas.drawText(Integer.toString(j + 1), LEFT - 40, UP + 50 + j * CELL, text);
        }

        for (int i = 0; i < sz; i++) {
            for (int j = 0; j < sz; j++) {
                cell = matr.get(i, j);
                if (!cell.isBlack()) {
                    canvas.drawBitmap(imWhite, LEFT + j * CELL, UP + i * CELL, paint);
                } else {
                    canvas.drawBitmap(imBlack, LEFT + j * CELL, UP + i * CELL, paint);
                }
                if (cell.isHorse()) { //если конь
                    canvas.drawBitmap(imHorse, LEFT + j * CELL, UP + i * CELL, paint); //рисую коня
                } else if (cell.getNum() > 0) { //номер хода
                    if (cell.getNum() < 10) //однозначное число (цифра) - печатаю поверединке ячейки(для красоты)
                        canvas.drawText(Integer.toString(cell.getNum()), LEFT + 20 + j * CELL, UP + 50 + i * CELL, text);
                    else //двузначное
                        canvas.drawText(Integer.toString(cell.getNum()), LEFT + 7 + j * CELL, UP + 50 + i * CELL, text);
                }
            }
        }
        //кнопки и текст, зависят от ориентации
        if (field.getScreenOrientation()) { //вертикальная
            canvas.drawBitmap(imButStart, verticButStartLeft.x, verticButStartLeft.y, paint); //кнопка
            txt = verticTxt; //вертикальная ориентация текста
        }
        else {
            canvas.drawBitmap(imButStart, horizButStartLeft.x, horizButStartLeft.y, paint); //кнопка
            txt = horizTxt; //горизонтальная ориентация текста
        }
        if (!fgame) {
            String str = field.getString(R.string.yourResMes) + Integer.toString(count) + " ";
            if (frec)
                str += field.getString(R.string.newRecordMes);
            canvas.drawText(str, txt.x, txt.y, text);
        }
        canvas.drawText(field.getString(R.string.currentRecordMes) + Integer.toString(max), txt.x+120, txt.y+200, text);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x, y; //координаты клика
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) //нажатие
            return true;
        if (action == MotionEvent.ACTION_UP) { //отпускание
            x = (int)event.getX();
            y = (int)event.getY();
            if (field.getScreenOrientation()) {
                if (x >= verticButStartLeft.x && x <= verticButStartRight.x && y >= verticButStartLeft.y && y <= verticButStartRight.y) {
                    newGame();
                    return true;
                }
            }
            else {
                if (x >= horizButStartLeft.x && x <= horizButStartRight.x && y >= horizButStartLeft.y && y <= horizButStartRight.y) {
                    newGame();
                    return true;
                }
            }
            if (!fgame) //игра закончена
                return false;
            if (!fieldToMatr(x,y)) { //некорректный ход
                if (count < 64) //игра не закончена
                    return true;
            }
        }
        invalidate(); //перерисовка после хода

        //проверка, если после этого хода некуда ходить
        if (count > 0) {
            if (!matr.isSteps(oldi, oldj) && fgame) { //если ходов нет и игра еще идёт
                if (count < 64)
                    stop(0);
                else
                    stop(1);
                return false;
            }
        }
        return false;
    }

    //вернёт то, корректный ли клик - по доске ли
    boolean fieldToMatr (int x, int y) { //перевод координат экрана в индексы матрицы и заполнение её соответствующей ячейки
        if (x < LEFT || x > LEFT+8*CELL || y<UP || y>UP+8*CELL) //клик не по доске
            return false;
        int i = (y-UP)/CELL;
        int j = (x-LEFT)/CELL;
        if (count > 0) { //не первый код выставления коня (потому что первый всегда возможен)
            if (!matr.step(i, j)) //некорректный ход
                return false;
        }
        matr.setHorse(i, j);
        oldi = i;
        oldj = j;
        count++; //ход сделан
        matr.set(i, j, count);
        if (count == 64) //заполнено всё поле
            return false;
        return true;
    }

    void newGame () {
        matr.clear();
        fgame = true;
        frec = false;
        count = 0;
        oldi = -1;
        oldj = -1;
        if (max == 1)
            max = field.getMax();
        this.requestFocus();
        field.setFieldView(this);
        invalidate(); //перерисовка
    }

    void stop (int k) { //нет ходов - k=0, победа - k=1
        fgame = false;
        String msg = "";
        if (k == 0)
            msg = field.getString(R.string.noMoreMes);
        else
            msg = field.getString(R.string.winMes);
        Toast dlg = Toast.makeText(this.getContext(), msg, Toast.LENGTH_LONG);
        dlg.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastContainer = (LinearLayout)dlg.getView();
        ImageView pict = new ImageView(this.getContext());
        if (k==0)
            pict.setImageResource(R.drawable.stop);
        else
            pict.setImageResource(R.drawable.win);
        toastContainer.addView(pict,0);
        dlg.show();
        if (count > max) {
            max = count;
            frec = true;
            field.saveRes(max);
        }
        invalidate();
    }
}
