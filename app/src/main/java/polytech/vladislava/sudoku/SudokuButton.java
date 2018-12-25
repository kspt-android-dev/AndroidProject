package polytech.vladislava.sudoku;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.GridLayout;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;


public class SudokuButton extends android.support.v7.widget.AppCompatButton {

    public static Sudoku game;
    boolean isConstant = false;
    int value;
    final int position;

    public SudokuButton(Context context, int position, int initValue) {
        super(context);
        this.position = position;

        GridLayout.LayoutParams doubleLayoutParams = new GridLayout.LayoutParams();
        doubleLayoutParams.width = 0;
        if (context.getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE)
            doubleLayoutParams.height = 0;
        doubleLayoutParams.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 5f);
        doubleLayoutParams.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 5f);
        doubleLayoutParams.setGravity(Gravity.FILL);
        setLayoutParams(doubleLayoutParams);

        this.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        setBackground(getResources().getDrawable(R.drawable.button_background, getContext().getTheme()));
        setValue(initValue);
        setOnClickListener(v -> clicked());
    }

    private void updateContent() {
        if (value == 0)
            setText("");
        else
            setText(String.valueOf(value));
    }

    private void clicked() {
        if (isConstant)
            return;
        setValue((value + 1) % 10);
        game.setNumber(position % 9, position / 9, value);
    }

    private void setValue(int value) {
        this.value = value;
        updateContent();
    }

    public void makeStatic() {
        isConstant = true;
        setEnabled(false);
    }

    public void setHepled(int solution) {
        setValue(solution);
        makeStatic();
        setBackground(getResources().getDrawable(R.drawable.button_hepled, getContext().getTheme()));
    }

    public void setContent(int actualy) {
        setValue(actualy);
    }

}
