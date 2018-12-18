package com.example.danila.minerandroid;


import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;


class GraphicCell extends android.support.v7.widget.AppCompatButton {
    private LogicCell logicCell;
    //private Content myContent;
    private TextView labelProbabilitiys;//Визуализация вероятности мины на клетке
    public static final int FLAG_COLOR = Color.BLUE;
    public static final int EMPTY_COLOR = Color.WHITE;
    public static final int CLOSED_COLOR = Color.GRAY;
    public static final int MINE_COLOR = Color.RED;
    public int contentColor;


    GraphicCell(AppCompatActivity gameActivity, FrameLayout frameLayout, Logic logic, Bot bot, LogicCell logicCell,
                int x, int y, int height, int width) {
        super(gameActivity);

        this.logicCell = logicCell;


        setLayoutParams(new LinearLayout.LayoutParams(width, height));
        setBackgroundColor(CLOSED_COLOR);
        setTranslationX(x * 50);
        setTranslationY(y * 50);
        labelProbabilitiys = new TextView(gameActivity);
        //labelProbabilitiys.setStyle("-fx-font-size:20;");TODO
        labelProbabilitiys.setY(getTranslationX());
        labelProbabilitiys.setY(getTranslationY());


        if (logicCell.getConditon() == 9)
            contentColor = MINE_COLOR;
        else
            contentColor = EMPTY_COLOR;

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (logic.getActionMode() == 1) {//если флаг
                    logicCell.setFlag();
                    dropFlag();
                }
                if (logic.getActionMode() == 0) {//если разминирование
                    bot.check(logicCell.getNumberInArray());
                    check();
                }

            }
        });

    }


    //Проверка клетки для бота
    void checkBot() {

        setBackgroundColor(contentColor);
        labelProbabilitiys.setVisibility(VISIBLE);//TODO

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
        setBackgroundColor(contentColor);


    }


    //Установка/снятие флага(для игрока)
    private void dropFlag() {
        if (logicCell.isFlag())
            setBackgroundColor(CLOSED_COLOR);

        else
            setBackgroundColor(FLAG_COLOR);

    }


    //Сеттеры, геттеры

    void printLabelProbabilitiys() {
        labelProbabilitiys.setText(String.format("%.2f", logicCell.getProbabilities()));
        labelProbabilitiys.setVisibility(VISIBLE);
    }


    TextView getLabelProbabilitiys() {
        return labelProbabilitiys;
    }

    LogicCell getLogicCell() {
        return logicCell;
    }

    public int getContentColor() {
        return contentColor;
    }
}
