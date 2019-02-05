package max.myminesweeper;

import android.content.Context;
import android.graphics.*;
import android.view.View;
import android.widget.FrameLayout;

public class PlayingField extends View {

    private final static int BORDER_STROKE_WIDTH = 5;
    private final static int TEXT_STROKE_WIDTH = 2;

    private Paint paint = new Paint();
    Logic logic;
    Matrix matrix;

    public PlayingField(Context context) {
        super(context);
        this.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));

        this.setBackgroundColor(getResources().getColor(R.color.gray));

        this.setClickable(true);
        this.setOnTouchListener(new PlayingFieldListener(this));

        matrix = new Matrix();
    }

    public void setLogic(Logic logic) {
        this.logic = logic;
    }

    public void newGame() {
        invalidate();
    }

    public void checkCell(Point point) {
        logic.openCell(point);
        invalidate();
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Cell cell : logic.getCells()) {
            // cell itself
            if (cell.getCondition().equals(Cell.CellCondition.OPEN)) paint.setColor(getResources().getColor(R.color.white));
            if (cell.getCondition().equals(Cell.CellCondition.MINE)) paint.setColor(getResources().getColor(R.color.red));
            if (cell.getCondition().equals(Cell.CellCondition.DEFUSED)) paint.setColor(getResources().getColor(R.color.green));
            if (cell.getCondition().equals(Cell.CellCondition.CLOSED)) paint.setColor(getResources().getColor(R.color.lightGray));
            paint.setStyle(Paint.Style.FILL);
            canvas.drawPath(cell.getPath(), paint);

            // cell border
            paint.setColor(getResources().getColor(R.color.black));
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(BORDER_STROKE_WIDTH);
            canvas.drawPath(cell.getPath(), paint);

            // mines count
            if (cell.getCondition().equals(Cell.CellCondition.OPEN) &&
                    cell.getNeighbourMines() != Cell.MINE
                    && cell.getNeighbourMines() > 0) {
                paint.setColor(getResources().getColor(R.color.black));
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setStrokeWidth(TEXT_STROKE_WIDTH);
                paint.setTextSize(getResources().getDimension(R.dimen.number_size));
                canvas.drawText(cell.getNeighbourMines() + "",
                        cell.getPoint().x + logic.getCellSide() / 2,
                        cell.getPoint().y + getResources().getDimension(R.dimen.number_offset_y),
                        paint);
            }
        }
    }

    public int widthInPixel() {
        return logic.getFieldWidth() * logic.getCellSide() + logic.getCellSide() / 2;
    }

    public int heightInPixel() {
        return logic.getFieldHeight() * (logic.getCellSide() * 2 / 3) + (logic.getCellSide() / 3);
    }
}