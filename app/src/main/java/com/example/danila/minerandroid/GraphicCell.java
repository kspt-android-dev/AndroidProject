package com.example.danila.minerandroid;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.GridLayout;


@SuppressLint("ViewConstructor")
class GraphicCell extends android.support.v7.widget.AppCompatButton {
    private LogicCell logicCell;
    public static final int FLAG_COLOR = Color.BLUE;
    public static final int EMPTY_COLOR = Color.WHITE;
    public static final int CLOSED_COLOR = Color.GRAY;
    public static final int MINE_COLOR = Color.RED;


    @SuppressLint("SetTextI18n")
    GraphicCell(Activity gameActivity, LogicCell logicCell) {
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
        } else if (logicCell.isFlag()) {
            setBackgroundColor(FLAG_COLOR);
            setText("");
        } else if (!logicCell.isChecked()) {
            setBackgroundColor(CLOSED_COLOR);
            setText("");
        }
    }


    /*getters*/

    LogicCell getLogicCell() {
        return logicCell;
    }


}
