package com.example.danila.minerandroid;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.widget.Chronometer;
import android.widget.GridLayout;
import android.widget.TextView;

class Graphic {
    private GraphicCell[] graphicCells;
    private Chronometer chronometer;
    private TextView minesNumberView;

    @SuppressLint("SetTextI18n")
    Graphic(Activity gameActivity, GridLayout gameField, TextView minesNumberView, Logic logic) {

        minesNumberView.setText("" + (logic.getMinesDigit() - logic.getFindedMinesDigit()));

        graphicCells = new GraphicCell[logic.getLevelWidth() * logic.getLevelHight()];


        gameField.setColumnCount(logic.getLevelWidth());

        for (int y = 0; y < logic.getLevelHight(); y++)
            for (int x = 0; x < logic.getLevelWidth(); x++) {

                graphicCells[y * logic.getLevelWidth() + x] =
                        new GraphicCell(gameActivity, minesNumberView, logic, this, logic.getLogicCells()[y * logic.getLevelWidth() + x]);


                gameField.addView(graphicCells[y * logic.getLevelWidth() + x]);

            }


    }


    //Перезагрузка уровня
    void reload() {
        for (GraphicCell graphicCell : graphicCells) {
            graphicCell.setBackgroundColor(GraphicCell.CLOSED_COLOR);
            graphicCell.setText("");
        }

    }


    //Показывает изначальные условия(для кнопки ESC)
    @SuppressLint("SetTextI18n")
    void checkAll() {
        for (GraphicCell graphicCell : graphicCells)
            if (!graphicCell.getLogicCell().isFlag())
                if (graphicCell.getLogicCell().getConditon() == 9)
                    graphicCell.setBackgroundColor(GraphicCell.MINE_COLOR);
                else {
                    graphicCell.setBackgroundColor(GraphicCell.EMPTY_COLOR);
                    graphicCell.setText("" + graphicCell.getLogicCell().getConditon());
                }


    }

    void update() {
        for (GraphicCell graphicCell : graphicCells)
            graphicCell.update();


    }


    //Геттеры


}
