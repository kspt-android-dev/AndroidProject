package ru.polytech.course.pashnik.lines.Graphics;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import ru.polytech.course.pashnik.lines.Core.Cell;
import ru.polytech.course.pashnik.lines.Core.ColorType;

public class Ball {

    private static float RADIUS_COEFFICIENT = 0.3f;

    private final float radius;
    private final float x;
    private final float y;
    private final ColorType color;
    private final int cellSize = GameView.getCellSize();

    public Ball(int x, int y, ColorType color, float radius) {
        this.x = cellSize / 2 + x * cellSize;
        this.y = cellSize / 2 + y * cellSize;
        this.color = color;
        this.radius = cellSize * radius;
    }

    public Ball(Cell cell, ColorType color, float dy, float dx) {
        this.x = cellSize / 2 + cell.getX() * cellSize;
        this.y = dy + cell.getY() * cellSize;
        this.color = color;
        this.radius = cellSize * RADIUS_COEFFICIENT;
    }

    public Ball(Cell cell, ColorType color) {
        this(cell.getX(), cell.getY(), color, RADIUS_COEFFICIENT);
    }

    public Ball(Cell cell, ColorType colorType, float radius) {
        this(cell.getX(), cell.getY(), colorType, radius);
    }

    public Ball(Cell cell) {
        this(cell.getX(), cell.getY(), ColorType.BLUE, RADIUS_COEFFICIENT);
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
