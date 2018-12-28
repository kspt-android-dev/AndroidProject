package com.example.pk.game15;

import android.app.Activity;
import android.view.View;
import android.widget.GridLayout;

class Cell extends android.support.v7.widget.AppCompatButton {
    //описание кнопки/клетки
    Cell (Activity activity, final Logic logic, final Graphic graphic){
        super(activity, null);
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = 0;
        params.height = 0;
        params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        setLayoutParams(params);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!logic.isGameOver()){
                    logic.move(graphic.findPosition(Cell.this));
                    graphic.update(logic);
                }
            }
        });
    }
    //задание стиля не пустой кнопки
    public void normalStyle(){
        setBackgroundResource(R.drawable.scaleshape);
        setTextAppearance(R.style.normalStyle);
    }
}
