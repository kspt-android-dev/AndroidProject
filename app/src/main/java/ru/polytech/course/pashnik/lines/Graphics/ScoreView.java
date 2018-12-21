package ru.polytech.course.pashnik.lines.Graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.LinearLayout;

import ru.polytech.course.pashnik.lines.Activities.GameActivity;

public class ScoreView extends View {

    private Canvas canvas;
    private Bitmap bitmap;
    private Paint bitmapPaint = new Paint();


    public ScoreView(Context context) {
        super(context);
        this.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        this.setBackgroundColor(Color.DKGRAY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (this.canvas == null) {
            int viewHeight = getWidth() / 9;
            bitmap = Bitmap.createBitmap(getWidth(), viewHeight, Bitmap.Config.ARGB_8888);
            this.canvas = new Canvas(bitmap);

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) getLayoutParams();
            params.height = viewHeight;
            this.setLayoutParams(params);
            GameActivity.setScoreViewCanvas(canvas);
        }
        canvas.drawBitmap(bitmap, 0, 0, bitmapPaint);
    }
}
