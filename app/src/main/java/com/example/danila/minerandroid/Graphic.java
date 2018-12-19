package com.example.danila.minerandroid;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

class Graphic {
    private GraphicCell[] graphicCellsVertMode;

    Graphic(AppCompatActivity gameActivity, FrameLayout gameField,
            int screenWidth, int screenHeight, Logic logic, Bot bot) {//TODO use screenHeight


        graphicCellsVertMode = new GraphicCell[logic.getLevelWidth() * logic.getLevelHight()];

        int cellSizeVertMode = screenWidth / logic.getLevelWidth();
        //int cellHeightHorizMode = screenHeight / logic.getLevelHight();//TODO add for swap

        for (int y = 0; y < logic.getLevelHight(); y++)
            for (int x = 0; x < logic.getLevelWidth(); x++) {
                graphicCellsVertMode[y * logic.getLevelWidth() + x] =
                        new GraphicCell(gameActivity, bot, logic.getLogicCells()[y * logic.getLevelWidth() + x],
                                x, y, cellSizeVertMode);

                gameField.addView(graphicCellsVertMode[y * logic.getLevelWidth() + x]);
            }


    }


    //Перезагрузка уровня
    void reload() {
        for (GraphicCell graphicCell : graphicCellsVertMode) {
            graphicCell.setBackgroundColor(GraphicCell.CLOSED_COLOR);
            graphicCell.setVisibility(View.VISIBLE);
        }

    }

    //Перезагрузка последнего уровня
    void reloadLast() {
        System.out.println("Reload of last level");
        for (GraphicCell graphicCell : graphicCellsVertMode) {
            graphicCell.setBackgroundColor(GraphicCell.CLOSED_COLOR);
            graphicCell.setVisibility(View.VISIBLE);
        }


    }


    //Показывает изначальные условия(для кнопки ESC)
    void checkAll() {
        for (GraphicCell graphicCell : graphicCellsVertMode)
            if (graphicCell.getLogicCell().getConditon() == 9 && !graphicCell.getLogicCell().isFlag())
                graphicCell.setHighlightColor(GraphicCell.MINE_COLOR);
            else if (!graphicCell.getLogicCell().isFlag()) {
                graphicCell.setBackgroundColor(graphicCell.getContentColor());
                graphicCell.getContent().setVisibility(View.INVISIBLE);
            }


    }


    //Геттеры

    public GraphicCell[] getGraphicCellsVertMode() {
        return graphicCellsVertMode;
    }


}
