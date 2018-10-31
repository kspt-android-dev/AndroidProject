package ru.polytech.course.pashnik.lines.Graphics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


public class Ball {

    public static final int RADIUS = 15;
    private final float x;
    private final float y;
    private final int color;
    private Context context;

    public Ball(Context context, int x, int y, int color) {
        this.context = context;
        this.x = Painter.convertDpToPixel(GameView.CELL_SIZE / 2
                + x * GameView.CELL_SIZE, this.context);
        this.y = Painter.convertDpToPixel(GameView.CELL_SIZE / 2
                + y * GameView.CELL_SIZE, this.context);
        this.color = color;
    }

    public void drawBall(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawCircle(x, y, Painter.convertDpToPixel(RADIUS, context), paint);
    }

    public void clearBall(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#C0C0C0"));
        canvas.drawCircle(x, y, Painter.convertDpToPixel(RADIUS, context), paint);
    }
}
