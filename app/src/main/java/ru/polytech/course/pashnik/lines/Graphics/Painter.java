package ru.polytech.course.pashnik.lines.Graphics;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import ru.polytech.course.pashnik.lines.Core.Board;
import ru.polytech.course.pashnik.lines.Core.Cell;
import ru.polytech.course.pashnik.lines.Core.ColorType;
import ru.polytech.course.pashnik.lines.Core.WinLine;

public class Painter extends View implements View.OnTouchListener {

    public static final int VIEW_SIZE = 405;
    public static final int CELL_SIZE = 45;
    public static final int CELL_NUMBER = 9;

    private Bitmap bitmap;
    private Paint bitmapPaint;
    private Canvas canvas;

    private Board board = new Board();

    public Painter(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);
        // converting view size from dp to pixels for painting
        float pixelViewSize = convertDpToPixel(VIEW_SIZE, context);

        // creating a Bitmap and setting Bitmap on Canvas
        bitmap = Bitmap.createBitmap((int) pixelViewSize, (int) pixelViewSize,
                Bitmap.Config.ARGB_8888);
        this.canvas = new Canvas(bitmap);
        bitmapPaint = new Paint();

        //drawing a board
        GameView gameView = new GameView(context, canvas);
        gameView.draw();
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

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // one-touch event handling
                Cell definedCell = defineCell(x, y);
                new Ball(getContext(), definedCell, ColorType.RED).drawBall(canvas);
                board.addCell(definedCell, ColorType.RED);
                if (board.isWin()) {
                    WinLine winLine = board.getWinLine();
                    clearWinLine(winLine.getLength(), winLine.getDirection(),
                            winLine.getStartCell());
                }
                invalidate();
                break;
        }
        return false;
    }

    private Cell defineCell(float x, float y) {
        // when you get x and y, appear new coordinate system, in which the cell has a size 120
        int gestureCell = 120;
        return new Cell((int) x / gestureCell, (int) y / gestureCell);
    }

    private void clearWinLine(int totalLength, Cell direction, Cell startCell) {
        for (int i = 0; i < totalLength; i++) {
            new Ball(getContext(), startCell).clearBall(canvas);
            board.removeCell(startCell);
            startCell = startCell.plus(direction);
        }
    }
}
