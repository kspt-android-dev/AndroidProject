package ru.polytech.course.pashnik.lines.Graphics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import static ru.polytech.course.pashnik.lines.Graphics.Painter.convertDpToPixel;

public class GameView {

    private Canvas canvas;
    private final float pixelCellSize;

    public GameView(Context context, Canvas canvas) {
        this.canvas = canvas;
        pixelCellSize = convertDpToPixel(Painter.CELL_SIZE, context);
    }

    public void draw() {
        // creating a Paint and set default settings
        Paint paint = new Paint();
        setPaintSettings(paint);
        // drawing a board
        for (int i = 0; i <= Painter.CELL_NUMBER; i++) {
            drawLine(canvas, i, 0, i, Painter.CELL_NUMBER, paint); // vertical line
            drawLine(canvas, 0, i, Painter.CELL_NUMBER, i, paint); // horizontal line
        }
    }

    private void drawLine(Canvas canvas, int startX, int startY,
                          int endX, int endY, Paint paint) {
        canvas.drawLine(startX * pixelCellSize, startY * pixelCellSize,
                endX * pixelCellSize, endY * pixelCellSize, paint);
    }

    private void setPaintSettings(Paint paint) {
        paint.setAntiAlias(true); // smoothing edges
        paint.setDither(true); // does not reduce color
        paint.setColor(Color.DKGRAY); // color DARK_GREY
        paint.setStrokeWidth(5f); // line thickness
    }
}
