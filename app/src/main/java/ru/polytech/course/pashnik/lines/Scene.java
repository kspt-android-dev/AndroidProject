package ru.polytech.course.pashnik.lines;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayDeque;
import java.util.Queue;

import ru.polytech.course.pashnik.lines.Core.Board;
import ru.polytech.course.pashnik.lines.Core.Cell;
import ru.polytech.course.pashnik.lines.Core.ColorType;
import ru.polytech.course.pashnik.lines.Core.Intellect;
import ru.polytech.course.pashnik.lines.Core.Line;
import ru.polytech.course.pashnik.lines.Core.WinLines;
import ru.polytech.course.pashnik.lines.Graphics.Ball;
import ru.polytech.course.pashnik.lines.Graphics.GameView;

public class Scene extends View implements View.OnTouchListener {

    public static final int CELL_NUMBER = 9;
    private static int CELL_SIZE;
    private static int VIEW_SIZE;

    private int score = 0;

    private Bitmap bitmap;
    private Paint bitmapPaint;
    private Canvas canvas;

    private Board board = new Board();
    private Queue<ColorType> queue = new ArrayDeque<>();
    private Intellect intellect = new Intellect(board);

    private boolean isPressed = false;
    private Cell pressedCell;

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
            initBoard(); // start state: three balls on a board
            fillQueue(); // start state of a Queue: three next colors
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
                if (isPressed) {
                    if (board.haveCell(definedCell)) {
                        pressedCell = definedCell;
                    } else {
                        new Ball(definedCell, board.getColor(pressedCell)).drawBall(canvas);
                        board.addCell(definedCell, board.getColor(pressedCell));
                        new Ball(pressedCell).clearBall(canvas);
                        board.removeCell(pressedCell);
                        if (board.isWin(definedCell)) {
                            WinLines winLines = board.getWinLines();
                            for (int i = 0; i < winLines.getSize(); i++) {
                                Line line = winLines.getWinLine(i);
                                checkScore(line.getLength());
                            }
                            clearWinLines(winLines);
                        }
                        while (!queue.isEmpty()) {
                            ColorType colorType = queue.poll();
                            Cell nextCell = intellect.generateNextCell();
                            new Ball(nextCell, colorType).drawBall(canvas);
                            board.addCell(nextCell, colorType);
                        }
                        fillQueue();
                        isPressed = false;
                    }
                    invalidate();
                } else {
                    if (board.haveCell(definedCell)) {
                        isPressed = true;
                        pressedCell = definedCell;
                    }
                }
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

    private void initBoard() {
        for (int i = 0; i < 3; i++) {
            Cell nextCell = intellect.generateNextCell();
            ColorType nextColor = intellect.generateNextColor();
            new Ball(nextCell, nextColor).drawBall(canvas);
            board.addCell(nextCell, nextColor);
        }
    }

    private void fillQueue() {
        for (int i = 0; i < 3; i++) {
            ColorType nextColor = intellect.generateNextColor();
            queue.add(nextColor);
        }
    }

    private void checkScore(int length) {
        if (length != 5) {
            int scoreMinus = length - 5;
            this.score += (5 + scoreMinus) * (scoreMinus + 1);
        } else this.score += 5;

    }
}