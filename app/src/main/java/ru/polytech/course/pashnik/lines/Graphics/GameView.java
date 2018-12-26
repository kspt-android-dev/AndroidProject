package ru.polytech.course.pashnik.lines.Graphics;

import android.animation.FloatEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

import ru.polytech.course.pashnik.lines.Activities.GameActivity;
import ru.polytech.course.pashnik.lines.Core.Cell;
import ru.polytech.course.pashnik.lines.Core.ColorType;
import ru.polytech.course.pashnik.lines.Presentation.MainContract;

public class GameView extends View implements View.OnTouchListener {

    public static final int CELL_NUMBER = 9;
    private static final float LINE_THICKNESS = 5f;
    private static int cellSize;

    private Paint bitmapPaint = new Paint();
    private Bitmap bitmap;
    private Canvas canvas;
    private MainContract.Presenter presenter;
    private Bundle savedState;
    private ValueAnimator animation;

    private boolean clearBall;
    private Cell clearCell;
    private ColorType clearColor;
    private float currentRadius;


    private boolean initFlag = true;

    public GameView(Context context) {
        super(context);
        this.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        this.setBackgroundColor(Color.LTGRAY);
        setOnTouchListener(this);
    }

    public static int getCellSize() {
        return cellSize;
    }

    public void drawGameView() {
        // creating a Paint and set default settings
        Paint paint = new Paint();
        setPaintSettings(paint);
        // drawing a board
        for (int i = 0; i <= CELL_NUMBER; i++) {
            drawLine(canvas, i, 0, i, CELL_NUMBER, paint); // vertical line
            drawLine(canvas, 0, i, CELL_NUMBER, i, paint); // horizontal line
        }
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        if (this.canvas == null) { // first draw of a bitmap
            int height = getHeight();
            int width = getWidth();
            int viewSize = width > height ? height : width;
            setViewHeight(viewSize);

            // creating a Bitmap and setting Bitmap on Canvas
            bitmap = Bitmap.createBitmap(viewSize, viewSize, Bitmap.Config.ARGB_8888);

            cellSize = viewSize / CELL_NUMBER;
            this.canvas = new Canvas(bitmap);
            //drawing a board
            drawGameView();
            GameActivity.setGameViewCanvas(this.canvas);
            if (initFlag) presenter.initGameView();
            else presenter.restoreModel(savedState);
        }
        canvas.drawBitmap(bitmap, 0, 0, bitmapPaint);
        if (clearBall) new Ball(clearCell, clearColor, currentRadius).drawBall(canvas);
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
        paint.setStrokeWidth(LINE_THICKNESS); // line thickness
    }

    private void setViewHeight(int viewSize) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) getLayoutParams();
        params.height = viewSize;
        params.width = viewSize;
        this.setLayoutParams(params);
    }

    public void makeAnimation(final Cell cell, final ColorType colorType) {
        clearBall = true;
        clearCell = cell;
        clearColor = colorType;
        new Ball(cell, clearColor).clearBall(canvas);
        animation = ValueAnimator.ofFloat(0.15f, 0.3f);
        animation.setDuration(1000);
        animation.setRepeatCount(ValueAnimator.INFINITE);
        animation.setRepeatMode(ValueAnimator.REVERSE);
        animation.setEvaluator(new FloatEvaluator());
        animation.setInterpolator(new LinearInterpolator());
        animation.start();

        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                currentRadius = value;
                new Ball(cell, colorType, value).clearBall(canvas);
            }
        });
    }

    public void stopAnimation() {
        animation.cancel();
        clearBall = false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        presenter.onCellWasClicked(event.getX(), event.getY());
        return false;
    }

    public void setPresenter(MainContract.Presenter presenter) {
        this.presenter = presenter;
    }

    public void makeInit(boolean flag) {
        this.initFlag = flag;
    }

    public void setBundle(Bundle bundle) {
        this.savedState = bundle;
    }
}