package com.shminesweeper.shminesweeper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.*;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Random;

public class PlayingField extends View {

    private Paint paint = new Paint();
    private ArrayList<Cell> cells;
    private ArrayDeque<Cell> deque = new ArrayDeque<>();
    private boolean firstOpening;
    private boolean gameStoped;

    enum ShownDialog {GREETING, DEFEAT, NONE}
    enum CheckedButton {FLAG, TOUCH, QUESTION}

    private ShownDialog shownDialog;
    private CheckedButton checkedButton;

    private int fieldWidth;
    private int fieldHeight;
    private int fieldMines;
    private int totalOpenCells;

    private Bitmap flag;
    private Bitmap mine;
    private Bitmap question;
    private Bitmap numb1;
    private Bitmap numb2;
    private Bitmap numb3;
    private Bitmap numb4;
    private Bitmap numb5;
    private Bitmap numb6;

    Matrix matrix;

    public PlayingField(Context context) {
        super(context);

        // field size
        this.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

        // background color
        this.setBackgroundColor(getResources().getColor(R.color.gray300));

        // for listner
        this.setClickable(true);
        this.setOnTouchListener(new PlayingFieldListner(this));

        matrix = new Matrix();

        setImages();
    }

    private void updateFieldSettings() {
        fieldHeight = Settings.cellsInHeight;
        fieldWidth = Settings.cellsInWidth;
        fieldMines = Settings.numberOfMines;
    }

    private void setImages() {
        final int IMAGE_SIZE = 120;

        flag = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.flag), IMAGE_SIZE, IMAGE_SIZE, false);
        mine = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.mine), 130, 130, false);
        question = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.question), IMAGE_SIZE, IMAGE_SIZE, false);
        numb1 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.numb1), 75, 120, false);
        numb2 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.numb2), IMAGE_SIZE, IMAGE_SIZE, false);
        numb3 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.numb3), IMAGE_SIZE, IMAGE_SIZE, false);
        numb4 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.numb4), IMAGE_SIZE, IMAGE_SIZE, false);
        numb5 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.numb5), IMAGE_SIZE, IMAGE_SIZE, false);
        numb6 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.numb6), IMAGE_SIZE, IMAGE_SIZE, false);
    }

    // создание массива ячеек игрового поля
    // при этом происходит определение соседних для ячеек для данной
    private ArrayList<Cell> setFieldCells() {
        ArrayList<Cell> result = new ArrayList<>();

        int startPoint_x = 10;
        int startPoint_y = 100;

        for (int curFieldHeight = 1; curFieldHeight <= fieldHeight; curFieldHeight++) {
            for (int curFieldWidth = 1; curFieldWidth <= fieldWidth; curFieldWidth++) {
                Cell newCell = new Cell(new Point(startPoint_x, startPoint_y));
                result.add(newCell);

                if (curFieldWidth != 1) {
                    result.get(result.size() - 1).setWestBrother(result.get(result.size() - 2));
                    result.get(result.size() - 2).setEastBrother(result.get(result.size() - 1));
                }
                if (curFieldHeight % 2 == 0) {  //чётный ярус
                    result.get(result.size() - 1).setNorthwesternBrother(result.get(result.size() - fieldWidth - 1));
                    result.get(result.size() - fieldWidth - 1).setSoutheastBrother(result.get(result.size() - 1));
                    if (curFieldWidth != fieldWidth) {
                        result.get(result.size() - 1).setNortheasternBrother(result.get(result.size() - fieldWidth));
                        result.get(result.size() - fieldWidth).setSouthwesternBrother(result.get(result.size() - 1));
                    }
                } else if (curFieldHeight != 1) {   // нечётный ярус
                    result.get(result.size() - 1).setNortheasternBrother(result.get(result.size() - fieldWidth - 1));
                    result.get(result.size() - fieldWidth - 1).setSouthwesternBrother(result.get(result.size() - 1));
                    if (curFieldWidth != 1) {
                        result.get(result.size() - 1).setNorthwesternBrother(result.get(result.size() - fieldWidth - 2));
                        result.get(result.size() - fieldWidth - 2).setSoutheastBrother(result.get(result.size() - 1));
                    }
                }

                startPoint_x += 200;
            }

            startPoint_x = startPoint_x - (fieldWidth * 200) +
                    (curFieldHeight % 2 == 0 ? - 100 : 100);
            startPoint_y += 200;
        }

        return result;
    }

    // восстановление игрового поля после поворота экрана в соответствии с сохраненным состоянием
    public void restoreField(Bundle savedInstanceState){
        updateFieldSettings();

        cells = restoreCells(savedInstanceState);

        firstOpening = (boolean) savedInstanceState.get("firstOpening");

        gameStoped = (boolean) savedInstanceState.get("gameStoped");

        totalOpenCells = (int) savedInstanceState.get("totalOpenCells");

        checkedButton = savedInstanceState.get("checkedButton").equals("flag") ? CheckedButton.FLAG :
                 savedInstanceState.get("checkedButton").equals("question") ? CheckedButton.QUESTION :
                        CheckedButton.TOUCH;

        // restore dialog
        restoreDialog(savedInstanceState);

/* TODO доделать сдвиг при перевороте
        int newScrollX = (int)savedInstanceState.get("screenSizeX") - (getScreenSize().x / 2 );
        int newScrollY = (int) savedInstanceState.get("screenSizeY") - ( getScreenSize().y / 2);
        //максимальный скролл влево
        int maxScrollX = this.widthInPixel() - getScreenSize().x + 100;
        int maxScrollY = this.heightInPixel() - getScreenSize().y + 100;

        this.setScrollX( (newScrollX < -100) ? -99 :
                ( newScrollX > maxScrollX) ? maxScrollX - 1 : newScrollX );
        this.setScrollY( (newScrollY < -100) ? -99 :
                (newScrollY > maxScrollY) ? maxScrollY - 1 : newScrollY);
                */
    }

    // восстановление ячеек игрового поля после переворота экрана в соответствии с сохранённым состоянием
    private ArrayList<Cell> restoreCells(@NotNull Bundle savedInstanceState){
        ArrayList<Cell> result = setFieldCells();

        for (int cellNumber : (ArrayList<Integer>) savedInstanceState.get("cellsWithMine")) {
            result.get(cellNumber).setContainsMine(true);
        }

        for (int cellNumber : (ArrayList<Integer>) savedInstanceState.get("cellsWithFlag")) {
            result.get(cellNumber).setContainsFlag(true);
        }

        for (int cellNumber : (ArrayList<Integer>) savedInstanceState.get("cellsWithQuestion")) {
            result.get(cellNumber).setContainsQuestion(true);
        }

        for (int cellNumber : (ArrayList<Integer>) savedInstanceState.get("openCells")) {
            result.get(cellNumber).setIsOpen(true);
        }

        return result;
    }

    // восстановление диалогового окна после поворота экрана (если оно показывалось)
    private void restoreDialog(@NotNull Bundle savedInstanceState){
        switch ((String) savedInstanceState.get("shownDialog")){
            case "defeat" :
                shownDialog = ShownDialog.DEFEAT;
                showDefeatDialog();
                break;
            case "greeting" :
                shownDialog = ShownDialog.GREETING;
                showGreetingDialog();
                break;
            case "none" :
                shownDialog = ShownDialog.NONE;
                break;
        }
    }

    private Point getScreenSize(){
        WindowManager wm = (WindowManager) this.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        size.x = display.getWidth();
        size.y = display.getHeight();
        return size;
    }

    public void startNewGame() {

        updateFieldSettings();
        cells = setFieldCells();
        totalOpenCells = 0;
        gameStoped = false;
        firstOpening = true;
        checkedButton = CheckedButton.TOUCH;
        shownDialog = ShownDialog.NONE;

        invalidate();
    }

    // поиск ячейки содержащей данную координату
    // открытие ячейки или установление на ячейку флага/вопроса в зависимости от активной кнопки (touch/flag/question)
    // показ диалогового окна при проигрыше или выйгрыше
    public void checkCell(Point point) {
        if (gameStoped) return;
        for (Cell cell : cells) {
            if (cell.isContainsPoint(point)) {
                if (!cell.isOpen()) {
                    //flag
                    if (checkedButton.equals(CheckedButton.FLAG)) {
                        if (cell.isContainsFlag()) {
                            cell.setContainsFlag(false);
                        } else {
                            cell.setContainsFlag(true);
                            cell.setContainsQuestion(false);
                        }
                    }
                    //question
                    if (checkedButton.equals(CheckedButton.QUESTION)) {
                        if (cell.isContainsQuestion()) {
                            cell.setContainsQuestion(false);
                        } else {
                            cell.setContainsQuestion(true);
                            cell.setContainsFlag(false);
                        }
                    }
                    //touch
                    if (checkedButton.equals(CheckedButton.TOUCH)) {
                        if (!cell.isContainsFlag()) {
                            cell.setIsOpen(true);
                            totalOpenCells++;

                            if (firstOpening) {
                                mining();
                                firstOpening = false;
                            }

                            if (cell.getMinesBeside() == 0) openBrothers(cell);
                        }

                        //lose
                        if (cell.isContainsMine()) {
                            gameStoped = true;
                            showMines();
                            showDefeatDialog();
                            break;
                        }

                        //win
                        if (totalOpenCells == fieldHeight * fieldWidth - fieldMines) {
                            showMines();
                            showGreetingDialog();
                            gameStoped = true;
                            break;
                        }
                    }
                }
            }
        }
        invalidate();
    }

    private void showMines() {
        for (Cell cell : cells) {
            if (cell.isContainsMine()) {
                cell.setIsOpen(true);
                cell.setContainsFlag(false);
                cell.setContainsQuestion(false);
            }
        }
    }

    private void showDefeatDialog() {
        shownDialog = ShownDialog.DEFEAT;

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setMessage(R.string.you_lose)
                .setPositiveButton(R.string.new_game, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startNewGame();
                    }
                })
                .setNegativeButton(R.string.overview, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        shownDialog = ShownDialog.NONE;
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showGreetingDialog() {
        shownDialog = ShownDialog.GREETING;

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setMessage(R.string.you_won)
                .setPositiveButton(R.string.new_game, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startNewGame();
                    }
                })
                .setNegativeButton(R.string.overview, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        shownDialog = ShownDialog.NONE;
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // открытие соседних ячеек, если текущая не соседствует ни с одной миной
    private void openBrothers(Cell cell) {
        deque.add(cell);
        while (!deque.isEmpty()) {
            Cell newCell = deque.removeFirst();
            if (!newCell.isOpen()) {
                newCell.setIsOpen(true);
                totalOpenCells++;
            }

            if (newCell.getMinesBeside() == 0) {
                for (Cell brother : newCell.getBrothers()) {
                    if (!brother.isOpen())
                        deque.addFirst(brother);
                }
            }
        }
    }

    // Минирование поля
    // Если мины занимают больше половины поля, то происходит полное заминирование поля, с дальнейшим разминированием
    //  нужного кол-ва ячеек
    private void mining() {         //минирование поля
        int totalMines;
        if (fieldMines > fieldWidth * fieldHeight % 2) {
            totalMines = fieldHeight * fieldWidth - 1;
            for (Cell cell : cells) cell.setContainsMine(!cell.isOpen());
            while (totalMines > fieldMines) {
                Random random = new Random();
                int num = random.nextInt(fieldHeight * fieldWidth - 1);
                if (cells.get(num).isContainsMine()) {
                    cells.get(num).setContainsMine(false);
                    totalMines--;
                }
            }
        } else {
            totalMines = 0;
            while (totalMines < fieldMines) {
                Random random = new Random();
                int num = random.nextInt(fieldHeight * fieldWidth - 1);
                if (!cells.get(num).isContainsMine() && !cells.get(num).isOpen()) {
                    cells.get(num).setContainsMine(true);
                    totalMines++;
                }
            }
        }
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (Cell cell : cells) {
            //cell
            paint.setColor(cell.getColor());
            paint.setStyle(Paint.Style.FILL);
            canvas.drawPath( cell.getPath(), paint);

            //cell border
            paint.setColor(cell.getBorderColor());
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(5);
            canvas.drawPath(cell.getPath(), paint);


            matrix.setTranslate(cell.getStartPoint().x + 35, cell.getStartPoint().y - 10);
            if (!cell.isOpen()) {
                if (cell.isContainsFlag())
                    canvas.drawBitmap(flag, matrix, paint);
                if (cell.isContainsQuestion())
                    canvas.drawBitmap(question, matrix, paint);
            }

            if ((cell.isOpen() || gameStoped ) && cell.isContainsMine()) {
                canvas.drawBitmap(mine, matrix, paint);
            }

            if (cell.isOpen() && !cell.isContainsMine()) {
                if (cell.getMinesBeside() == 1) {
                    matrix.setTranslate(cell.getStartPoint().x + 50, cell.getStartPoint().y - 10); // 1
                    canvas.drawBitmap(numb1, matrix, paint);
                }
                if (cell.getMinesBeside() == 2) {
                    matrix.setTranslate(cell.getStartPoint().x + 40, cell.getStartPoint().y - 10);
                    canvas.drawBitmap(numb2, matrix, paint);
                }
                if (cell.getMinesBeside() == 3) {
                    matrix.setTranslate(cell.getStartPoint().x + 40, cell.getStartPoint().y - 10);
                    canvas.drawBitmap(numb3, matrix, paint);
                }
                if (cell.getMinesBeside() == 4)
                    canvas.drawBitmap(numb4, matrix, paint);
                if (cell.getMinesBeside() == 5)
                    canvas.drawBitmap(numb5, matrix, paint);
                if (cell.getMinesBeside() == 6)
                    canvas.drawBitmap(numb6, matrix, paint);
            }
        }
    }

    // ------------ setters --------------
    // установление активной кнопки (flag/touch/question)
    public void setCheckedButton(CheckedButton checkedButton) {
        this.checkedButton = checkedButton;
    }

    // --------------- getters ------------
    public int height(){ return fieldHeight; }

    public int width() { return fieldWidth; }

    public int widthInPixel() { return fieldWidth * 200 + 100 ; }

    public int heightInPixel() { return fieldHeight * 200 + 100; }

    public boolean isGameStoped() { return gameStoped; }

    public boolean isFirstOpening() { return firstOpening; }

    public int getTotalOpenCells() { return  totalOpenCells; }

    public CheckedButton getCheckedButton() { return checkedButton; }

    public String getShownDialogString() {
        switch ( shownDialog ){
            case NONE: return "none";
            case DEFEAT: return "defeat";
            default: return "greeting";
        }
    }

    // номера заминированных ячеек
    public ArrayList<Integer> getCellsWithMine() {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < cells.size(); i++){
            if (cells.get(i).isContainsMine()) result.add(i);
        }
        return result;
     }

    // номера ячеек с флагом
    public ArrayList<Integer> getCellsWithFlag() {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < cells.size(); i++){
            if (cells.get(i).isContainsFlag()) result.add(i);
        }
        return result;
    }

    // номера ячеек с вопросом
    public ArrayList<Integer> getCellsWithQuestion() {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < cells.size(); i++){
            if (cells.get(i).isContainsQuestion()) result.add(i);
        }
        return result;
    }

    // номера открытых ячеек
    public ArrayList<Integer> getOpenCells() {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < cells.size(); i++){
            if (cells.get(i).isOpen()) result.add(i);
        }
        return result;
    }
}
