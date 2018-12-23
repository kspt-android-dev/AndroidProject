package ru.spbstu.kspt.myhorsemove;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class FieldView extends View {

    private final FieldActivity field;
    private final Paint paint;
    private final Paint text;
    private final int colField;
    private final Bitmap imWhite;
    private final Bitmap imBlack;
    private final Bitmap imHorse;
    private Data data;
    private final int LEFT = 70; //35х2 - отступ сверху в пикселях
    private final int UP = 70;
    private final int CELL = 70; //размер ячейки(картинки) в пикселях
    private final String alphabet[] = {"a", "b", "c", "d", "e", "f", "g", "h"};
    private boolean fgame = true; //идёт или не идёт игра (true по умолчанию - чтобы при начальной отрисовке не выводить count)
    private int max = 1; //лучший результат
    private boolean frec = false; //был ли рекорд

    public FieldView(Context context) {
        this(context,null);
    }

    public FieldView(Context context, AttributeSet attrs) {
        super(context, attrs);
        field = (FieldActivity) context;
        paint = new Paint();
        text = new Paint();
        Resources res = getContext().getResources();
        colField = res.getColor(R.color.colorField);
        imBlack = BitmapFactory.decodeResource( field.getResources(), R.drawable.black35);
        imWhite = BitmapFactory.decodeResource(field.getResources(), R.drawable.white35);
        imHorse = BitmapFactory.decodeResource(field.getResources(), R.drawable.horse);
        if (data == null)
            data = new Data();
        else
            data = field.getData();
        if (data.count == 0)
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

        int sz = 8;
        for (int i = 0; i < sz; i++) {
            canvas.drawText(alphabet[i], LEFT + 20 + i * CELL, UP - 10, text);
        }
        for (int j = sz - 1; j >= 0; j--) {
            canvas.drawText(Integer.toString(j + 1), LEFT - 40, UP + 50 + j * CELL, text);
        }

        for (int i = 0; i < sz; i++) {
            for (int j = 0; j < sz; j++) {
                Cell cell = data.matr.get(i, j);
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
        String str = field.getString(R.string.yourResMes) + Integer.toString(data.count) + " ";
        if (frec)
            str += field.getString(R.string.newRecordMes);
        field.textView1.setText(str);
        field.textView2.setText(field.getString(R.string.currentRecordMes) + Integer.toString(max));
    }
//
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x, y; //координаты клика
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) //нажатие
            return true;
        if (action == MotionEvent.ACTION_UP) { //отпускание
            x = (int)event.getX();
            y = (int)event.getY();
            if (!fgame) //игра закончена
                return false;
            if (!fieldToMatr(x,y)) { //некорректный ход
                if (data.count < 64) //игра не закончена
                    return true;
                }
        }
        invalidate(); //перерисовка после хода

        //проверка, если после этого хода некуда ходить
        if (data.count > 0) {
            if (!data.matr.isSteps(data.oldi, data.oldj) && fgame) { //если ходов нет и игра еще идёт
                if (data.count < 64)
                    stop(0);
                else
                    stop(1);
                return false;
            }
        }
        return false;
    }

    //вернёт то, корректный ли клик - по доске ли
    private boolean fieldToMatr(int x, int y) { //перевод координат экрана в индексы матрицы и заполнение её соответствующей ячейки
        if (x < LEFT || x > LEFT+8*CELL || y<UP || y>UP+8*CELL) //клик не по доске
            return false;
        int i = (y-UP)/CELL;
        int j = (x-LEFT)/CELL;
        if (data.count > 0) { //не первый код выставления коня (потому что первый всегда возможен)
            if (!data.matr.step(i, j)) //некорректный ход
                return false;
        }
        data.matr.setHorse(i, j);
        data.oldi = i;
        data.oldj = j;
        data.count++; //ход сделан
        data.matr.set(i, j, data.count);
        return data.count != 64;
    }

    void newGame () {
        data.matr.clear();
        fgame = true;
        frec = false;
        data.count = 0;
        data.oldi = -1;
        data.oldj = -1;
        if (max == 1)
            max = field.getMax();
        this.requestFocus();
        field.setFieldView(this);
        invalidate(); //перерисовка
    }

    private void stop(int k) { //нет ходов - k=0, победа - k=1
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
        if (data.count > max) {
            max = data.count;
            frec = true;
            field.saveRes(max);
        }
        invalidate();
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
