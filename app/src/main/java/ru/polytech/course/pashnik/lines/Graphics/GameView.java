package ru.polytech.course.pashnik.lines.Graphics;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import ru.polytech.course.pashnik.lines.Scene;

public class GameView {

    private Canvas canvas;
    private final float cellSize;
    private final int cellNumber;

    public GameView(Canvas canvas) {
        this.canvas = canvas;
        cellSize = Scene.getCellSize();
        cellNumber = Scene.getCellNumber();
    }

    public void draw() {
        // creating a Paint and set default settings
        Paint paint = new Paint();
        setPaintSettings(paint);
        // drawing a board
        for (int i = 0; i <= cellNumber; i++) {
            drawLine(canvas, i, 0, i, cellNumber, paint); // vertical line
            drawLine(canvas, 0, i, cellNumber, i, paint); // horizontal line
        }
    }

    private void drawLine(Canvas canvas, int startX, int startY,
                          int endX, int endY, Paint paint) {
        canvas.drawLine(startX * cellSize, startY * cellSize,
                endX * cellSize, endY * cellSize, paint);
    }

    private void setPaintSettings(Paint paint) {
        paint.setAntiAlias(true); // smoothing edges
        paint.setDither(true); // does not reduce color
        paint.setColor(Color.DKGRAY); // color DARK_GREY
        paint.setStrokeWidth(5f); // line thickness
    }
}
