package com.example.danila.minerandroid;


import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


class GraphicCell extends android.support.v7.widget.AppCompatButton {
    private LogicCell logicCell;
    private Logic logic;
    //private Content myContent;
    private TextView labelProbabilitiys;//Визуализация вероятности мины на клетке
    public static final int FLAG_COLOR = 255;
    public static final int WHITE_COLOR = 0;
    public static final int MINE_COLOR = 122;


    GraphicCell(AppCompatActivity gameActivity, Logic logic, LogicCell logicCell, int x, int y) {
        super(gameActivity);

        this.logic = logic;
        this.logicCell = logicCell;
        setHighlightColor(WHITE_COLOR);
        setHeight(50);
        setWidth(50);
        setTranslationX(x * 50);
        setTranslationY(y * 50);
        labelProbabilitiys = new TextView(gameActivity);
        //labelProbabilitiys.setStyle("-fx-font-size:20;");
        labelProbabilitiys.setTranslationX(getTranslationX());
        labelProbabilitiys.setTranslationY(getTranslationY());


//        if (logicCell.getConditon() == 9)
//            myContent = new Content(this, Color.RED);
//        else
//            myContent = new Content(this, 0);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (logic.getMoveAction() == 1) {
                    logicCell.setFlag();
                    dropFlag();
                }
                if (logic.getMoveAction() == 0) {
                    logic.getBot().check(logicCell.getNumberInArray());
                    check();
                }

            }
        });

    }


    //Проверка клетки для бота
    void checkBot() {
        setVisibility(INVISIBLE);
        labelProbabilitiys.setVisibility(INVISIBLE);
        //myContent. (true);
        if (logicCell.getConditon() == 9)
            setHighlightColor(MINE_COLOR);

    }

    //Установка флага(для бота)
    void setFlag() {
        setHighlightColor(FLAG_COLOR);
    }


    //Проверка клетки,не для бота
    private void check() {
        if (logicCell.isChecked())
            return;
        logicCell.setChecked(true);
        setVisibility(VISIBLE);
        //myContent.set(true);


    }


    //Установка/снятие флага(для игрока)
    private void dropFlag() {
        if (logicCell.isFlag())
            setHighlightColor(WHITE_COLOR);
        else
            setHighlightColor(FLAG_COLOR);

    }


    //Сеттеры, геттеры

    @SuppressLint("DefaultLocale")
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


    //    void setText() {
//        myContent.setText("" + logicCell.getConditon());
//    }

//    Content getMyContent() {
//        return myContent;
//    }


    //Значение клетки
//    class Content extends android.support.v7.widget.AppCompatImageButton {
//
//
//        Content(GraphicCell graphicCell, Color color) {
//            super("213");
//            common(graphicCell);
//
//
//        }
//
//        @SuppressLint("SetTextI18n")
//        Content(GraphicCell graphicCell, int nearlyMine) {
//            super();
//            setText("" + nearlyMine);
//            common(graphicCell);
//        }
//
//        //Метод для конструктора
//        private void common(GraphicCell graphicCell) {
//            setStyle("-fx-font-size:30;");
//            //setStyle("-fx-text-alignment:center;");
//            setTranslateX(graphicCell.getTranslateX());
//            setTranslateY(graphicCell.getTranslateY());
//            setVisible(false);
//        }
//
//
//    }

}


