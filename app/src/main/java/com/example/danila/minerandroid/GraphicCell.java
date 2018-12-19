package com.example.danila.minerandroid;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;


@SuppressLint("ViewConstructor")
class GraphicCell extends android.support.v7.widget.AppCompatButton {
    private LogicCell logicCell;
    public static final int FLAG_COLOR = Color.BLUE;
    public static final int EMPTY_COLOR = Color.WHITE;
    public static final int CLOSED_COLOR = Color.GRAY;
    public static final int MINE_COLOR = Color.RED;

    public TextView content;


    @SuppressLint("SetTextI18n")
    GraphicCell(AppCompatActivity gameActivity, Bot bot, LogicCell logicCell, int x, int y, int cellSize) {
        super(gameActivity);

        this.logicCell = logicCell;

        content = new TextView(gameActivity);
        content.setVisibility(INVISIBLE);
        content.setTextSize(cellSize);
        content.setTranslationX(x * cellSize);
        content.setTranslationY(y * cellSize);
        content.setText("" + logicCell.getConditon());

        setLayoutParams(new LinearLayout.LayoutParams(cellSize, cellSize));
        setBackgroundColor(CLOSED_COLOR);
        setTranslationX(x * cellSize);
        setTranslationY(y * cellSize);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                bot.check(logicCell.getNumberInArray());
                check();
            }


        });

        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                logicCell.changeFlag();
                changeFlag();
                return true;
            }
        });

    }


    //Проверка клетки для бота
    void checkBot() {

        setBackgroundColor(getContentColor());

        if (logicCell.getConditon() == 9)
            setBackgroundColor(MINE_COLOR);

    }

    //Установка флага(для бота)
    void setFlag() {
        setBackgroundColor(FLAG_COLOR);
    }


    //Проверка клетки,не для бота
    private void check() {
        if (logicCell.isChecked())
            return;
        logicCell.setChecked(true);

        setBackgroundColor(getContentColor());
        content.setVisibility(VISIBLE);


    }


    //Установка/снятие флага(для игрока)
    private void changeFlag() {
        if (logicCell.isFlag())
            setBackgroundColor(FLAG_COLOR);
        else
            setBackgroundColor(CLOSED_COLOR);

    }


    //Сеттеры, геттеры

    LogicCell getLogicCell() {
        return logicCell;
    }

    public int getContentColor() {
        if (getLogicCell().getConditon() == 9)
            return MINE_COLOR;
        else
            return EMPTY_COLOR;
    }

    public TextView getContent() {
        return content;
    }
}
