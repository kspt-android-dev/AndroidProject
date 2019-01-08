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
    private static final float ANIMATION_COEFFICIENT = 0.37f;
    private static int cellSize;

    private ValueAnimator upDownAnimator;
    private static float UP_DOWN_START; // depends on a cell size
    private static float UP_DOWN_END;
    private static final int UP_DOWN_DURATION = 400;
    private boolean upDownFlag;
    private Cell upDownCell;
    private ColorType upDownColor;
    private float currentDx;

    private ValueAnimator appearanceAnimator;
    private static final float APPEARANCE_START = 0.15f;
    private static final float APPEARANCE_END = 0.3f;
    private static final int APPEARANCE_DURATION = 300;

    public void setAppearanceFlag(boolean appearanceFlag) {
        this.appearanceFlag = appearanceFlag;
    }

    private boolean appearanceFlag;
    private Cell appearanceCell;
    private ColorType appearanceColor;
    private float currentRadius;

    private Paint bitmapPaint = new Paint();
    private Bitmap bitmap;
    private Canvas canvas;
    private MainContract.Presenter presenter;
    private Bundle savedState;

    private boolean initFlag = true;
    private boolean rotateFlag = false;

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
            UP_DOWN_START = cellSize * ANIMATION_COEFFICIENT;
            UP_DOWN_END = cellSize * (1 - ANIMATION_COEFFICIENT);
            this.canvas = new Canvas(bitmap);
            //drawing a board
            drawGameView();
            GameActivity.setGameViewCanvas(this.canvas);
            if (initFlag) presenter.initGameView();
            else presenter.restoreModel(savedState);
        }
        canvas.drawBitmap(bitmap, 0, 0, bitmapPaint);
        if (upDownFlag) new Ball(upDownCell, upDownColor, currentDx, 1).drawBall(canvas);
        if (appearanceFlag)
            new Ball(appearanceCell, appearanceColor, currentRadius).drawBall(canvas);
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

    public void setUpDownAnimation(final Cell cell, final ColorType colorType) {
        stopAppearanceAnimation();
        upDownFlag = true;
        upDownCell = cell;
        upDownColor = colorType;
        new Ball(cell).clearBall(canvas);
        upDownAnimator = ValueAnimator.ofFloat(UP_DOWN_START, UP_DOWN_END);
        upDownAnimator.setDuration(UP_DOWN_DURATION);
        upDownAnimator.setRepeatCount(ValueAnimator.INFINITE);
        upDownAnimator.setRepeatMode(ValueAnimator.REVERSE);
        upDownAnimator.setEvaluator(new FloatEvaluator());
        upDownAnimator.setInterpolator(new LinearInterpolator());
        upDownAnimator.start();

        upDownAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                currentDx = value;
                new Ball(cell, colorType, value, 1).clearBall(canvas);
            }
        });
    }

    public void setAppearanceAnimation(final Cell cell, final ColorType color) {
        appearanceFlag = true;
        appearanceCell = cell;
        appearanceColor = color;
        appearanceAnimator = ValueAnimator.ofFloat(APPEARANCE_START, APPEARANCE_END);
        appearanceAnimator.setDuration(APPEARANCE_DURATION);
        appearanceAnimator.setEvaluator(new FloatEvaluator());
        appearanceAnimator.setInterpolator(new LinearInterpolator());
        appearanceAnimator.start();

        appearanceAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                currentRadius = value;
                new Ball(cell, color, value).drawBall(canvas);
            }
        });
    }

    public void stopUpDownAnimation() {
        upDownAnimator.cancel();
        upDownFlag = false;
    }

    public void stopAppearanceAnimation() {
        if (!rotateFlag)
            appearanceAnimator.cancel();
        appearanceFlag = false;
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

    public void setRotateFlag(boolean rotateFlag) {
        this.rotateFlag = rotateFlag;
    }
}