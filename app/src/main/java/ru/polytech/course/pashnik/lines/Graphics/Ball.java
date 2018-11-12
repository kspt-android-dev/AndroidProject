package ru.polytech.course.pashnik.lines.Graphics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import ru.polytech.course.pashnik.lines.Core.Cell;
import ru.polytech.course.pashnik.lines.Core.ColorType;
import ru.polytech.course.pashnik.lines.Scene;


public class Ball {

    public static final int RADIUS = 15;
    private final float x;
    private final float y;
    private final ColorType color;
    private Context context;

    public Ball(Context context, int x, int y, ColorType color) {
        this.context = context;
        this.x = Scene.convertDpToPixel(Scene.CELL_SIZE / 2
                + x * Scene.CELL_SIZE, this.context);
        this.y = Scene.convertDpToPixel(Scene.CELL_SIZE / 2
                + y * Scene.CELL_SIZE, this.context);
        this.color = color;
    }

    public Ball(Context context, Cell cell, ColorType color) {
        this(context, cell.getX(), cell.getY(), color);
    }

    public Ball(Context context, Cell cell) {
        this(context, cell.getX(), cell.getY(), ColorType.BLUE);
    }

    public void drawBall(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(ColorType.chooseColor(color));
        canvas.drawCircle(x, y, Scene.convertDpToPixel(RADIUS, context), paint);
    }

    public void clearBall(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#C0C0C0"));
        canvas.drawCircle(x, y, Scene.convertDpToPixel(RADIUS, context), paint);
    }
}
