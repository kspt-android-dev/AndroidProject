package ru.polytech.course.pashnik.lines.Graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import ru.polytech.course.pashnik.lines.Activities.GameActivity;
import ru.polytech.course.pashnik.lines.Presentation.MainContract;

public class GameView extends View implements View.OnTouchListener {

    private static final int cellNumber = 9;
    private static int cellSize;

    private Paint bitmapPaint = new Paint();
    private Bitmap bitmap;
    private Canvas canvas;
    private MainContract.Presenter presenter;


    public GameView(Context context) {
        super(context);
        this.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        this.setBackgroundColor(Color.LTGRAY);
        setOnTouchListener(this);
    }


    public static int getCellNumber() {
        return cellNumber;
    }

    public static int getCellSize() {
        return cellSize;
    }

    public void drawGameView() {
        // creating a Paint and set default settings
        Paint paint = new Paint();
        setPaintSettings(paint);
        // drawing a board
        for (int i = 0; i <= cellNumber; i++) {
            drawLine(canvas, i, 0, i, cellNumber, paint); // vertical line
            drawLine(canvas, 0, i, cellNumber, i, paint); // horizontal line
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (this.canvas == null) { // first draw of a bitmap
            int viewSize = getWidth();
            setViewHeight(viewSize);

            // creating a Bitmap and setting Bitmap on Canvas
            bitmap = Bitmap.createBitmap(viewSize, viewSize, Bitmap.Config.ARGB_8888);

            cellSize = viewSize / cellNumber;
            this.canvas = new Canvas(bitmap);
            //drawing a board
            drawGameView();
            GameActivity.setGameViewCanvas(this.canvas);
            presenter.initGameView();
        }
        canvas.drawBitmap(bitmap, 0, 0, bitmapPaint);
        invalidate();
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

    private void setViewHeight(int viewSize) {
        // View view = findViewById(R.id.game_view);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) getLayoutParams();
        params.height = viewSize;
        this.setLayoutParams(params);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        presenter.onCellWasClicked(event.getX(), event.getY());
        return false;
    }

    public void setPresenter(MainContract.Presenter presenter) {
        this.presenter = presenter;
    }
}