package com.example.pk.game15;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.widget.GridLayout;

class Graphic {
    private final Cell[] cells;
    private final int side;
    private final Game game;

    Graphic(Activity activity, Game game, GridLayout field, Logic logic, int side) {
        this.side = side;
        this.game = game;
        cells = new Cell[side * side];
        //заполнение поля кнопками/клетками
        for (int i = 0; i < side * side; i++) {
            cells[i] = new Cell(activity, logic, this);
            field.addView(cells[i]);
        }
        update(logic);
    }

    //обновление игрового поля
    @SuppressLint("SetTextI18n")
    public void update(Logic logic) {
        for (int i = 0; i < side * side; i++) {
            if (i != logic.getBlankPos()) {
                cells[i].setText(Integer.toString(logic.getTiles()[i]));
                cells[i].normalStyle();
            } else {
                cells[i].setBackgroundResource(R.color.wight);
                cells[i].setText(" ");
            }
        }
        if(logic.isGameOver()) game.endGame();
    }

    //нахождение индекса клетки
    public int findPosition(Cell cell) {
        for (int i = 0; i < side * side; i++){
            if(cells[i].getText().equals(cell.getText()) ) return i;
        }
        return 0;
    }
}
