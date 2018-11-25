package ru.polytech.course.pashnik.lines;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import ru.polytech.course.pashnik.lines.Core.Board;
import ru.polytech.course.pashnik.lines.Core.Cell;
import ru.polytech.course.pashnik.lines.Core.ColorType;
import ru.polytech.course.pashnik.lines.Core.Line;
import ru.polytech.course.pashnik.lines.Core.WinLines;
import ru.polytech.course.pashnik.lines.Graphics.Ball;
import ru.polytech.course.pashnik.lines.Graphics.GameView;

public class Scene extends View implements View.OnTouchListener {

    private static final int CELL_NUMBER = 9;
    private static int CELL_SIZE;
    private static int VIEW_SIZE;

    private Bitmap bitmap;
    private Paint bitmapPaint;
    private Canvas canvas;

    private Board board = new Board();

    public Scene(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);
    }

    public static int getCellNumber() {
        return CELL_NUMBER;
    }

    public static int getCellSize() {
        return CELL_SIZE;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (this.canvas == null) {
            setViewSize();
            setBitmap(VIEW_SIZE, VIEW_SIZE);
            CELL_SIZE = VIEW_SIZE / CELL_NUMBER;
            this.canvas = new Canvas(bitmap);
            //drawing a board
            GameView gameView = new GameView(this.canvas);
            gameView.draw();
        }
        canvas.drawBitmap(bitmap, 0, 0, bitmapPaint);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // one-touch event handling
                Cell definedCell = defineCell(x, y);
                new Ball(definedCell, ColorType.RED).drawBall(canvas);
                board.addCell(definedCell, ColorType.RED);
                if (board.isWin(definedCell)) clearWinLines(board.getWinLines());
                invalidate();
                break;
        }
        return false;
    }

    private void setViewSize() {
        VIEW_SIZE = getWidth();
        View view = findViewById(R.id.game_view);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        params.height = VIEW_SIZE;
        view.setLayoutParams(params);
    }

    private void setBitmap(float height, float width) {
        // creating a Bitmap and setting Bitmap on Canvas
        bitmap = Bitmap.createBitmap((int) width, (int) height, Bitmap.Config.ARGB_8888);
        bitmapPaint = new Paint();
    }


    private Cell defineCell(float x, float y) {
        // when you get x and y, appear new coordinate system, in which the cell has a size 120
        return new Cell((int) x / CELL_SIZE, (int) y / CELL_SIZE);
    }

    private void clearWinLines(WinLines winLines) {
        for (int i = 0; i < winLines.getSize(); i++) {
            Line currentWinLine = winLines.getWinLine(i);
            Cell startCell = currentWinLine.getStartCell();
            for (int j = 0; j < currentWinLine.getLength(); j++) {
                new Ball(startCell).clearBall(canvas);
                board.removeCell(startCell);
                startCell = startCell.plus(currentWinLine.getDirection());
            }
        }
        winLines.removeAllWinLines();
    }
}