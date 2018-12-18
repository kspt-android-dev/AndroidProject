package com.example.danila.minerandroid;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;


class Graphic {


    private Logic logic;
    private Bot bot;
    private GraphicCell[] graphicCells;


    Graphic(AppCompatActivity gameActivity, FrameLayout gameField, Logic logic, Bot bot) {
        this.bot = bot;
        this.logic = logic;
        int height = gameField.getHeight() / logic.getLevelHight();
        int width = gameField.getWidth() / logic.getLevelWidth();

        graphicCells = new GraphicCell[logic.getLevelWidth() * logic.getLevelHight()];

        for (int x = 0; x < logic.getLevelHight(); x++)
            for (int y = 0; y < logic.getLevelWidth(); y++) {
                graphicCells[x * logic.getLevelWidth() + y] = new GraphicCell(gameActivity, gameField,
                        logic, bot, logic.getLogicCells()[x * logic.getLevelWidth() + y],
                        y, x, 60, 60);
                gameField.addView(graphicCells[x * logic.getLevelWidth() + y]);
            }


    }


    void printProabilities() {
        for (GraphicCell graphicCell : graphicCells)
            if (!graphicCell.getLogicCell().isFlag() && !graphicCell.getLogicCell().isChecked())
                graphicCell.printLabelProbabilitiys();

    }

    //Перезагрузка уровня
    void reload() {
        for (GraphicCell graphicCell : graphicCells) {
            graphicCell.setHighlightColor(GraphicCell.CLOSED_COLOR);
            graphicCell.setVisibility(View.VISIBLE);
        }
        for (GraphicCell graphicCell : graphicCells)
            graphicCell.getLabelProbabilitiys().setVisibility(View.VISIBLE);
        printProabilities();
    }

    //Перезагрузка последнего уровня
    void reloadLast() {
        System.out.println("Reload of last level");
        for (GraphicCell graphicCell : graphicCells) {
            graphicCell.setHighlightColor(GraphicCell.CLOSED_COLOR);
            graphicCell.setVisibility(View.VISIBLE);
        }
        for (GraphicCell graphicCell : graphicCells)
            graphicCell.getLabelProbabilitiys().setVisibility(View.INVISIBLE);


    }


    //Показывает изначальные условия(для кнопки ESC)
    void checkAll() {
        for (GraphicCell graphicCell : graphicCells) {
            if (graphicCell.getLogicCell().getConditon() == 9 && !graphicCell.getLogicCell().isFlag())
                graphicCell.setHighlightColor(GraphicCell.MINE_COLOR);
            else if (!graphicCell.getLogicCell().isFlag()) {
                graphicCell.setBackgroundColor(graphicCell.getContentColor());
                //graphicCell.getMyContent().setVisible(true);
            }
            graphicCell.getLabelProbabilitiys().setVisibility(View.INVISIBLE);
        }

    }


    //Геттеры

    public GraphicCell[] getGraphicCells() {
        return graphicCells;
    }


}
