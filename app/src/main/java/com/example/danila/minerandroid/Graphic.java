package com.example.danila.minerandroid;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

class Graphic {


    private Logic logic;
    private Bot bot;
    private GraphicCell[] graphicCellsVertMode;
    private Button modeButton;


    Graphic(AppCompatActivity gameActivity, FrameLayout gameField, Button helpMeBotButton, int screenWidth, int screenHeight, Logic logic, Bot bot) {
        this.logic = logic;
        this.bot = bot;
        this.modeButton = helpMeBotButton;

        this.modeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bot.helpMeBot();
            }
        });

        graphicCellsVertMode = new GraphicCell[logic.getLevelWidth() * logic.getLevelHight()];

        int cellSizeVertMode = screenWidth / logic.getLevelWidth();
        //int cellHeightHorizMode = screenHeight / logic.getLevelHight();

        for (int y = 0; y < logic.getLevelHight(); y++)
            for (int x = 0; x < logic.getLevelWidth(); x++) {
                graphicCellsVertMode[y * logic.getLevelWidth() + x] = new GraphicCell(gameActivity, gameField,
                        logic, bot, logic.getLogicCells()[y * logic.getLevelWidth() + x],
                        x, y, cellSizeVertMode);
                gameField.addView(graphicCellsVertMode[y * logic.getLevelWidth() + x]);
            }


    }


    void printProabilities() {
        for (GraphicCell graphicCell : graphicCellsVertMode)
            if (!graphicCell.getLogicCell().isFlag() && !graphicCell.getLogicCell().isChecked())
                graphicCell.printLabelProbabilitiys();

    }

    //Перезагрузка уровня
    void reload() {
        for (GraphicCell graphicCell : graphicCellsVertMode) {
            graphicCell.setHighlightColor(GraphicCell.CLOSED_COLOR);
            graphicCell.setVisibility(View.VISIBLE);
        }
        for (GraphicCell graphicCell : graphicCellsVertMode)
            graphicCell.getLabelProbabilitiys().setVisibility(View.VISIBLE);
        printProabilities();
    }

    //Перезагрузка последнего уровня
    void reloadLast() {
        System.out.println("Reload of last level");
        for (GraphicCell graphicCell : graphicCellsVertMode) {
            graphicCell.setHighlightColor(GraphicCell.CLOSED_COLOR);
            graphicCell.setVisibility(View.VISIBLE);
        }
        for (GraphicCell graphicCell : graphicCellsVertMode)
            graphicCell.getLabelProbabilitiys().setVisibility(View.INVISIBLE);


    }


    //Показывает изначальные условия(для кнопки ESC)
    void checkAll() {
        for (GraphicCell graphicCell : graphicCellsVertMode) {
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

    public GraphicCell[] getGraphicCellsVertMode() {
        return graphicCellsVertMode;
    }


}
