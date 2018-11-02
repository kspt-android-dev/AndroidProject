package ru.polytech.course.pashnik.lines.Graphics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Objects;

import ru.polytech.course.pashnik.lines.Core.Cell;
import ru.polytech.course.pashnik.lines.Core.ColorTypes;


public class Ball {

    public static final int RADIUS = 15;
    private final float x;
    private final float y;
    private final ColorTypes color;
    private Context context;

    public Ball(Context context, int x, int y, ColorTypes color) {
        this.context = context;
        this.x = Painter.convertDpToPixel(Painter.CELL_SIZE / 2
                + x * Painter.CELL_SIZE, this.context);
        this.y = Painter.convertDpToPixel(Painter.CELL_SIZE / 2
                + y * Painter.CELL_SIZE, this.context);
        this.color = color;
    }

    public Ball(Context context, Cell cell, ColorTypes color) {
        this(context, cell.getX(), cell.getY(), color);
    }

    public void drawBall(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(chooseColor(color));
        canvas.drawCircle(x, y, Painter.convertDpToPixel(RADIUS, context), paint);
    }

    public void clearBall(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#C0C0C0"));
        canvas.drawCircle(x, y, Painter.convertDpToPixel(RADIUS, context), paint);
    }

    private int chooseColor(ColorTypes color) {
        switch (color) {
            case RED:
                return Color.parseColor("#FF0000");
            case BLUE:
                return Color.parseColor("#0000FF");
            case BROWN:
                return Color.parseColor("#A0522D");
            case GREEN:
                return Color.parseColor("#00FF00");
            case LAGOON:
                return Color.parseColor("#87CEEB");
            case PURPLE:
                return Color.parseColor("#800080");
            case YELLOW:
                return Color.parseColor("#FFFF00");

        }
        return -1;
    }
}
