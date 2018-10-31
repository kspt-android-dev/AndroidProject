package ru.polytech.course.pashnik.lines.Graphics;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

public class Painter extends View {

    public static final int VIEW_SIZE = 405;

    private Bitmap bitmap;
    private Paint bitmapPaint;

    public Painter(Context context, AttributeSet attrs) {
        super(context, attrs);
        // converting view size from dp to pixels for painting
        float pixelViewSize = convertDpToPixel(VIEW_SIZE, context);

        // creating a Bitmap and setting Bitmap on Canvas
        bitmap = Bitmap.createBitmap((int) pixelViewSize, (int) pixelViewSize,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        bitmapPaint = new Paint();

        // test for drawing a board
        GameView gameView = new GameView(context, canvas);
        gameView.draw();

        // test for drawing a ball with coordinates (4,5);
        Ball ball = new Ball(context, 3, 5, Color.GREEN);
        ball.drawBall(canvas);
        ball.clearBall(canvas);
    }

    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * (metrics.densityDpi / 160f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, 0, 0, bitmapPaint);
    }
}
