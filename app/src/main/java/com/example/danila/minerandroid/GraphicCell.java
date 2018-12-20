package com.example.danila.minerandroid;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import org.w3c.dom.Text;


@SuppressLint("ViewConstructor")
class GraphicCell extends android.support.v7.widget.AppCompatButton {
    private LogicCell logicCell;
    public static final int FLAG_COLOR = Color.BLUE;
    public static final int EMPTY_COLOR = Color.WHITE;
    public static final int CLOSED_COLOR = Color.GRAY;
    public static final int MINE_COLOR = Color.RED;


    @SuppressLint("SetTextI18n")
    GraphicCell(Activity gameActivity, TextView minesNumberView, Logic logic, Bot bot, LogicCell logicCell) {
        super(gameActivity, null, R.style.grid_button);

        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = 0;
        params.height = 0;
        params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        params.setGravity(Gravity.FILL);
        setLayoutParams(params);


        this.logicCell = logicCell;

        setBackgroundColor(CLOSED_COLOR);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                bot.check(logicCell.getNumberInArray());
                check();
                minesNumberView.setText("" + (logic.getMinesDigit() - bot.getFindedMines()));
            }


        });

        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                logicCell.changeFlag();
                changeFlag();
                minesNumberView.setText("" + (logic.getMinesDigit() - bot.getFindedMines()));
                return true;
            }
        });


    }


    //Проверка клетки для бота
    @SuppressLint("SetTextI18n")
    void checkBot() {
        if (logicCell.getConditon() == 9)
            setBackgroundColor(MINE_COLOR);
        else {
            setBackgroundColor(EMPTY_COLOR);
            setText("" + logicCell.getConditon());
        }

    }


    //Проверка клетки,не для бота
    @SuppressLint("SetTextI18n")
    private void check() {
        if (logicCell.isChecked())
            return;
        logicCell.setChecked(true);

        if (logicCell.getConditon() == 9)
            setBackgroundColor(MINE_COLOR);
        else {
            setBackgroundColor(EMPTY_COLOR);
            setText("" + logicCell.getConditon());
        }
    }


    //Установка флага(для бота)
    void setFlag() {
        setBackgroundColor(FLAG_COLOR);
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


}
