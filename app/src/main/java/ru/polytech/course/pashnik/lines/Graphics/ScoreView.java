package ru.polytech.course.pashnik.lines.Graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class ScoreView extends View {

    private Canvas canvas;
    private Bitmap bitmap;
    private Paint bitmapPaint = new Paint();


    public ScoreView(Context context) {
        super(context);
    }

    public ScoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setBackgroundColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (this.canvas == null) {
            int viewHeight = getWidth() / GameView.getCellNumber();
            bitmap = Bitmap.createBitmap(getWidth(), viewHeight, Bitmap.Config.ARGB_8888);
            this.canvas = new Canvas(bitmap);
        }
        canvas.drawBitmap(bitmap, 0, 0, bitmapPaint);
    }

}
