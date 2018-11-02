package ru.polytech.course.pashnik.lines.Graphics;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import ru.polytech.course.pashnik.lines.Core.Cell;
import ru.polytech.course.pashnik.lines.Core.ColorTypes;
import ru.polytech.course.pashnik.lines.Core.Intellect;

public class Painter extends View {

    public static final int VIEW_SIZE = 405;
    public static final int CELL_SIZE = 45;
    public static final int CELL_NUMBER = 9;

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

        //drawing a board
        GameView gameView = new GameView(context, canvas);
        gameView.draw();

        // test for drawing balls with dummy intellect
        Intellect intellect = new Intellect();

        for (int i = 0; i < 3; i++) {
            Cell nextCell = intellect.nextCell();
            ColorTypes nextColor = ColorTypes.getColorType(intellect.nextColor());
            Ball ball = new Ball(context, nextCell, nextColor);
            ball.drawBall(canvas);
        }
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
