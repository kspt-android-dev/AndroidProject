package com.example.danila.minerandroid;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;


@SuppressLint("ViewConstructor")
class GraphicCell extends android.support.v7.widget.AppCompatButton {
    private LogicCell logicCell;
    public static final int FLAG_COLOR = Color.BLUE;
    public static final int EMPTY_COLOR = Color.WHITE;
    public static final int CLOSED_COLOR = Color.GRAY;
    public static final int MINE_COLOR = Color.RED;


    @SuppressLint("SetTextI18n")
    GraphicCell(Activity gameActivity, TextView minesNumberView, Logic logic, Graphic graphic, LogicCell logicCell) {
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
        //setBackground(getResources().getDrawable(R.drawable.rectangle_border));

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (logic.checkGameCondition()) {
                    case 0:
                        logic.checkCell(logicCell.getNumberInArray());
                        graphic.update();
                        minesNumberView.setText("" + (logic.getMinesDigit() - logic.getFindedMinesDigit()));
                        break;
                    case -1:
                        logic.checkAll();
                        graphic.checkAll();
                        break;
                    case 1:
                        logic.checkAll();
                        graphic.checkAll();
                        break;

                }

            }


        });

        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (logic.checkGameCondition() == 0) {
                    logic.changeFlag(logicCell.getNumberInArray());
                    changeFlag();
                    minesNumberView.setText("" + (logic.getMinesDigit() - logic.getFindedMinesDigit()));
                } else {
                }//TODO

                return true;
            }
        });


    }


    //Проверка клетки,не для бота
    @SuppressLint("SetTextI18n")
    void update() {
        if (logicCell.isChecked()) {
            if (logicCell.getConditon() == 9)
                setBackgroundColor(MINE_COLOR);
            else {
                setBackgroundColor(EMPTY_COLOR);
                setText("" + logicCell.getConditon());
            }
        } else if (logicCell.isFlag())
            setBackgroundColor(FLAG_COLOR);
        else if (!logicCell.isChecked())
            setBackgroundColor(CLOSED_COLOR);
    }


    //Установка/снятие флага(для игрока)
    private void changeFlag() {
        if (!logicCell.isChecked())
            if (logicCell.isFlag())
                setBackgroundColor(FLAG_COLOR);
            else
                setBackgroundColor(CLOSED_COLOR);
        //setBackground(getResources().getDrawable(R.drawable.rectangle_border));


    }


    //Сеттеры, геттеры

    LogicCell getLogicCell() {
        return logicCell;
    }


}
