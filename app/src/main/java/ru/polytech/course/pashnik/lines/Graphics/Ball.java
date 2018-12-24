package ru.polytech.course.pashnik.lines.Graphics;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import ru.polytech.course.pashnik.lines.Core.Cell;
import ru.polytech.course.pashnik.lines.Core.ColorType;

public class Ball {

    private static final double RADIUS_COEFFICIENT = 0.3;

    private int radius;
    private final float x;
    private final float y;
    private final ColorType color;

    public Ball(int x, int y, ColorType color) {
        int cellSize = GameView.getCellSize();
        this.x = cellSize / 2 + x * cellSize;
        this.y = cellSize / 2 + y * cellSize;
        this.color = color;
        radius = (int) (cellSize * RADIUS_COEFFICIENT);
    }

    public Ball(Cell cell, ColorType color) {
        this(cell.getX(), cell.getY(), color);
    }

    public Ball(Cell cell) {
        this(cell.getX(), cell.getY(), ColorType.BLUE);
    }

    public void drawBall(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(ColorType.chooseColor(color));
        canvas.drawCircle(x, y, radius, paint);
    }

    public void clearBall(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.LTGRAY);
        canvas.drawCircle(x, y, radius, paint);
    }
}
