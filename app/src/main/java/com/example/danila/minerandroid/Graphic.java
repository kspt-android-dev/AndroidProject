package com.example.danila.minerandroid;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

class Graphic {
    private GraphicCell[] graphicCellsVertMode;

    Graphic(Activity gameActivity, GridLayout gameField, TextView minesNumberView, TextView timerView,
            Logic logic, Bot bot) {


        graphicCellsVertMode = new GraphicCell[logic.getLevelWidth() * logic.getLevelHight()];

        gameField.setColumnCount(logic.getLevelWidth());

        for (int y = 0; y < logic.getLevelHight(); y++)
            for (int x = 0; x < logic.getLevelWidth(); x++) {

                graphicCellsVertMode[y * logic.getLevelWidth() + x] =
                        new GraphicCell(gameActivity, minesNumberView, logic, bot, logic.getLogicCells()[y * logic.getLevelWidth() + x]);


                gameField.addView(graphicCellsVertMode[y * logic.getLevelWidth() + x]);

            }


    }


    //Перезагрузка уровня
    void reload() {
        for (GraphicCell graphicCell : graphicCellsVertMode) {
            graphicCell.setBackgroundColor(GraphicCell.CLOSED_COLOR);
            graphicCell.setText("");
        }

    }


    //Показывает изначальные условия(для кнопки ESC)
    @SuppressLint("SetTextI18n")
    void checkAll() {
        for (GraphicCell graphicCell : graphicCellsVertMode)
            if (graphicCell.getLogicCell().getConditon() == 9 && !graphicCell.getLogicCell().isFlag())
                graphicCell.setHighlightColor(GraphicCell.MINE_COLOR);
            else if (!graphicCell.getLogicCell().isFlag()) {
                if (graphicCell.getLogicCell().getConditon() == 9)
                    graphicCell.setBackgroundColor(GraphicCell.MINE_COLOR);
                else {
                    graphicCell.setBackgroundColor(GraphicCell.EMPTY_COLOR);
                    graphicCell.setText("" + graphicCell.getLogicCell().getConditon());
                }
            }


    }


    //Геттеры

    public GraphicCell[] getGraphicCellsVertMode() {
        return graphicCellsVertMode;
    }


}
