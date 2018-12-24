package com.example.pk.game15;

import android.app.Activity;
import android.view.Display;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;

public class Graphic {
    private Cell[] cells;
    private final int side;

    Graphic(Activity activity, GridLayout field, Logic logic, int side) {
        this.side = side;
        cells = new Cell[side * side];
        for (int i = 0; i < side * side; i++) {
            cells[i] = new Cell(activity, logic, this);
            field.addView(cells[i]);
        }
        update(logic);
    }

    public void update(Logic logic) {
        for (int i = 0; i < side * side; i++) {
            if (i != logic.getBlankPos()) {
                cells[i].setText(Integer.toString(logic.getTiles()[i]));
                cells[i].normalStyle();
            } else cells[i].setBackgroundResource(R.color.transparent);
        }
    }

    public int findPosition(Cell cell) {
        for (int i = 0; i < side * side; i++){
            if(cells[i].getText().equals(cell.getText()) ) return i;
        }
        return 0;
    }
}
