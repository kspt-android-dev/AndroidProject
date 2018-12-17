package com.example.danila.minerandroid;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.File;
import java.net.MalformedURLException;


class Graphic {


    private Logic logic;
    private Bot bot;
    private GraphicCell[] graphicCells;
    private double sceneWidth;
    private double sceneHight;
    private AppCompatActivity gameActivity;


    Graphic(AppCompatActivity gameActivity, Logic logic, Bot bot) {
        this.bot = bot;
        graphicCells = new GraphicCell[logic.getLevelWidth() * logic.getLevelHight()];
        this.logic = logic;
        for (int i = 0; i < logic.getLevelHight(); i++)
            for (int j = 0; j < logic.getLevelWidth(); j++)
                graphicCells[i * logic.getLevelWidth() + j] = new GraphicCell(gameActivity, logic, logic.getLogicCells()[i * logic.getLevelWidth() + j], j, i);


//        for (GraphicCell graphicCell : graphicCells)
//            graphicCell.setText();


//        for (GraphicCell graphicCell : graphicCells)
//            root.getChildren().addAll(graphicCell, graphicCell.getMyContent(), graphicCell.getLabelProbabilitiys());

    }


    void printProabilities() {
        for (GraphicCell graphicCell : graphicCells)
            if (!graphicCell.getLogicCell().isFlag() && !graphicCell.getLogicCell().isChecked())
                graphicCell.printLabelProbabilitiys();

    }

//    void printBotsPhrase() {
//        botsPhrase.setText(bot.getPhrase());
//    }

    //Перезагрузка уровня
    void reload() {
//        loseLabel.setVisible(false);
//        winLabel.setVisible(false);
//        defaultLabel.setVisible(true);
//        root.setVisible(true);
        for (GraphicCell graphicCell : graphicCells) {
            graphicCell.setHighlightColor(GraphicCell.WHITE_COLOR);
            //graphicCell.getMyContent().setVisib(false);
            graphicCell.setVisibility(View.VISIBLE);
            //graphicCell.setText();
        }
        for (GraphicCell graphicCell : graphicCells)
            graphicCell.getLabelProbabilitiys().setVisibility(View.VISIBLE);
        printProabilities();
    }

    //Перезагрузка последнего уровня
    void reloadLast() {
        System.out.println("Reload of last level");
//        loseLabel.setVisible(false);
//        winLabel.setVisible(false);
//        defaultLabel.setVisible(true);
//        root.setVisible(true);
        for (GraphicCell graphicCell : graphicCells) {
            graphicCell.setHighlightColor(GraphicCell.WHITE_COLOR);
            //graphicCell.getMyContent().setVisible(false);
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
                graphicCell.setVisibility(View.INVISIBLE);
                //graphicCell.getMyContent().setVisible(true);
            }
            graphicCell.getLabelProbabilitiys().setVisibility(View.INVISIBLE);
        }
//        loseLabel.setVisible(false);
//        winLabel.setVisible(false);
//        root.setVisible(true);


    }


    //Геттеры

    public GraphicCell[] getGraphicCells() {
        return graphicCells;
    }
}
