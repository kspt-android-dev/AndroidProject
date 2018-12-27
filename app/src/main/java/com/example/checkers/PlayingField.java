package com.example.checkers;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;


public class PlayingField extends View   {

    private Paint paint;
    private int fieldSize;
    private Point padding; // отступ до поля от края экрана
    private int hellHeight; // для dead checkers  входит в размер поля

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
        Resources resources = getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    // отступы от краёв экрана
    private void setPadding(Display display) {
        Point displaySize = new Point();
        display.getSize(displaySize);

        padding.x = (displaySize.x - fieldSize) / 2; // размер выше поля со всех сторон
        padding.y = (displaySize.y - fieldSize - getStatusBarHeight() - getBottomMenuHeight()
                - (int) getResources().getDimension(R.dimen.button_height) ) / 2;
    }

    private void setFieldSize(Display display ){
        Point displaySize = new Point();
        display.getSize(displaySize);

        fieldSize = (displaySize.x > displaySize.y) ? ( displaySize.y - getStatusBarHeight() - getBottomMenuHeight() -
                (int) getResources().getDimension(R.dimen.button_height)) : displaySize.x;
    }


    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final int radiusChecker = fieldSize/24;


        for (Cell cell : gameLogic.getCells()) {
            // рисуем клетки
            paint.setColor(getResources().getColor(R.color.darkGoldenRod));
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
                canvas.drawCircle(cell.center().x, cell.center().y, radiusChecker, paint);
                if (cell.getCondition().equals(Cell.CellCondition.CONTAINS_CHECKER)) {
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setStrokeWidth(3);
                    paint.setColor( (cell.getCheckerColor() == getResources().getColor( R.color.black)) ? getResources().getColor(R.color.white):
                                        getResources().getColor(R.color.black));
                }
                else {
                    //выделяем дамку
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(getResources().getColor(R.color.red));
                }
                canvas.drawCircle(cell.center().x, cell.center().y, radiusChecker/2, paint);
            }

            // рисование взятой шашки
            if (grabbedChecker.x != 0) {
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(gameLogic.getTurnColor().equals(GameLogic.TurnColor.WHITE) ? getResources().getColor(R.color.white) : getResources().getColor(R.color.black));
                canvas.drawCircle(grabbedChecker.x, grabbedChecker.y, radiusChecker, paint);
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(5);
                paint.setColor(gameLogic.getTurnColor().equals(GameLogic.TurnColor.WHITE) ? getResources().getColor(R.color.black)
                            : getResources().getColor(R.color.white));
                canvas.drawCircle(grabbedChecker.x, grabbedChecker.y, radiusChecker / 2, paint);
            }

            // рисуем убитые шашки
            drawDead(canvas);
        }
    }

    private void drawDead(Canvas canvas){

        Point drawPoint;
        int size;
        final int radiusHell = hellHeight/2;
        boolean indicator = gameLogic.isBlackKill();
        if (indicator){
            drawPoint = new Point(padding.x + hellHeight, padding.y + fieldSize - radiusHell);
            size = gameLogic.getNumbOfBlackDead();
        }
        else {
            drawPoint = new Point(padding.x + hellHeight, padding.y + radiusHell);
            size = gameLogic.getNumbOfWiteDead();
        }

        for (int i = 0; i < size; i++) {
            paint.setStyle(Paint.Style.FILL);
            if (indicator){
                paint.setColor(gameLogic.getUpperPlayerColor());
            }
            else {
                paint.setColor(gameLogic.getBottomPlayerColor());
            }
            canvas.drawCircle(drawPoint.x, drawPoint.y, radiusHell, paint); // середина поля мертвых
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(3);
            if (indicator) {
                paint.setColor(gameLogic.getBottomPlayerColor());
            }
            else {
                paint.setColor(gameLogic.getUpperPlayerColor());
            }
            canvas.drawCircle(drawPoint.x, drawPoint.y, radiusHell/2, paint);//обводка внутренняя

            drawPoint.x += radiusHell;
        }

    }


    public void setGrabbedChecker(Point grabbedChecker) {
        this.grabbedChecker = grabbedChecker;
        invalidate();
    }

}
