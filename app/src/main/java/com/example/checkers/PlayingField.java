package com.example.checkers;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.logging.LogRecord;

public class PlayingField extends View   {

    private Paint paint;
    private int fieldSize;
    private Point padding; // отступ до поля от края экрана
    private int hellHeight; // для dead checkers     входит в размер поля

    GameLogic gameLogic;

    // кординаты взятой шашки
    private Point grabbedChecker;


    public PlayingField(Context context) {
        super(context);

        this.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

        setParams(context);
    }

    public void setLogic(GameLogic logic) {
        this.gameLogic = logic;
        gameLogic.setParams(fieldSize, padding);
    }

    private void setParams(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        paint = new Paint();
        padding = new Point(50,0);
        setFieldSize(display);
        setPadding(display);
        hellHeight = fieldSize/12;

        grabbedChecker = new Point(0, 0);

    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    //высота нижней кнопки в пикселях
    private int getBottomMenuHeight() {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 64,r.getDisplayMetrics()));
    }

    // отступы от краёв экрана
    private void setPadding(Display display) {
        Point displaySize = new Point();
        display.getSize(displaySize);

        padding.x = (displaySize.x - fieldSize) / 2;
        padding.y = (displaySize.y - fieldSize - getStatusBarHeight() - getBottomMenuHeight()) / 2;
    }

    private void setFieldSize(Display display ){
        Point displaySize = new Point();
        display.getSize(displaySize);

        fieldSize = (displaySize.x > displaySize.y) ? ( displaySize.y - getStatusBarHeight() - getBottomMenuHeight() - 50 ) : (displaySize.x - 50 );
    }


    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (Cell cell : gameLogic.getCells()) {
            // рисуем клетки
            paint.setColor(Color.rgb(184,134,11));
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(5);
            canvas.drawPath(cell.getPath(), paint);

            paint.setStyle(Paint.Style.FILL);
            paint.setColor(cell.getBackgroundColor());
            canvas.drawPath(cell.getPath(), paint);

            // рисуем шашки
            if (cell.getCondition().equals(Cell.CellCondition.CONTAINS_CHECKER ) ||
                    cell.getCondition().equals(Cell.CellCondition.CONTAINS_CROWN )) {
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(cell.getCheckerColor());
                canvas.drawCircle(cell.center().x, cell.center().y, fieldSize/24, paint);
                if (cell.getCondition().equals(Cell.CellCondition.CONTAINS_CHECKER)) {
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setStrokeWidth(3);
                    paint.setColor( (cell.getCheckerColor() == Color.BLACK) ? Color.WHITE : Color.BLACK);
                }
                else {
                    //выделяем дамку
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(Color.RED);
                }
                canvas.drawCircle(cell.center().x, cell.center().y, fieldSize/48, paint);
            }

            // рисование взятой шашки
            if (grabbedChecker.x != 0) {
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(gameLogic.getTurnColor().equals(GameLogic.TurnColor.WHITE) ? Color.WHITE : Color.BLACK);
                canvas.drawCircle(grabbedChecker.x, grabbedChecker.y, fieldSize / 24, paint);
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(5);
                paint.setColor(gameLogic.getTurnColor().equals(GameLogic.TurnColor.WHITE) ? Color.BLACK : Color.WHITE);
                canvas.drawCircle(grabbedChecker.x, grabbedChecker.y, fieldSize / 48, paint);
            }

            // рисуем мёртвых СНИЗУ
            Point drawPoint = new Point(padding.x + hellHeight, padding.y + fieldSize - hellHeight/2);
            for (int i=0; i < gameLogic.getNumbOfBlackDead(); i++) {
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(gameLogic.getUpperPlayerColor());
                canvas.drawCircle(drawPoint.x, drawPoint.y, hellHeight/2, paint);
                //обводка
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(3);
                paint.setColor(gameLogic.getBottomPlayerColor());
                canvas.drawCircle(drawPoint.x, drawPoint.y, hellHeight/4, paint);

                drawPoint.x += hellHeight/2;
            }

            //рисуем мёртвых СВЕРХУ
            drawPoint = new Point(padding.x + hellHeight, padding.y + hellHeight/2);
            for (int i=0; i < gameLogic.getNumbOfWiteDead(); i++) {
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(gameLogic.getBottomPlayerColor());
                canvas.drawCircle(drawPoint.x, drawPoint.y, hellHeight/2, paint);
                //обводка
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(3);
                paint.setColor(gameLogic.getUpperPlayerColor());
                canvas.drawCircle(drawPoint.x, drawPoint.y, hellHeight/4, paint);

                drawPoint.x += hellHeight/2;
            }
        }
    }


    public void setGrabbedChecker(Point grabbedChecker) {
        this.grabbedChecker = grabbedChecker;
        invalidate();
    }

}
