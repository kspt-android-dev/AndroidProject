package com.shminesweeper.shminesweeper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.*;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.FrameLayout;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Random;

public class SquarePlayingField extends PlayingField {

    private Paint paint = new Paint();

    private ArrayDeque<SquareCell> deque = new ArrayDeque<>();
    private ArrayList<SquareCell> cells;

    private boolean gameStoped;
    private boolean firstOpening;

    private int totalOpenCells;
    private int fieldHeight;
    private int fieldWidth;
    private int fieldMines;

    private ShownDialog shownDialog;
    private CheckedButton checkedButton;

    private Bitmap flag;
    private Bitmap mine;
    private Bitmap question;
    private Bitmap numb1;
    private Bitmap numb2;
    private Bitmap numb3;
    private Bitmap numb4;
    private Bitmap numb5;
    private Bitmap numb6;
    private Bitmap numb7;
    private Bitmap numb8;

    public SquarePlayingField(Context context) {
        super(context);

        // field size
        this.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT));

        // background color
        this.setBackgroundColor(getResources().getColor(R.color.gray300));

        this.setClickable(true);
        this.setOnTouchListener(new PlayingFieldListner(this));

        matrix = new Matrix();

        setImages();
    }


    private void setImages(){
        final int IMAGE_SIZE = 110;  //ширина и высота изображений

        flag = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.flag), IMAGE_SIZE, IMAGE_SIZE, false);
        mine = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.mine), IMAGE_SIZE, IMAGE_SIZE, false);
        question = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.question), IMAGE_SIZE, IMAGE_SIZE, false);
        numb1 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.numb1), 65, IMAGE_SIZE, false);
        numb2 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.numb2), IMAGE_SIZE, IMAGE_SIZE, false);
        numb3 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.numb3), IMAGE_SIZE, IMAGE_SIZE, false);
        numb4 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.numb4), IMAGE_SIZE, IMAGE_SIZE, false);
        numb5 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.numb5), IMAGE_SIZE, IMAGE_SIZE, false);
        numb6 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.numb6), IMAGE_SIZE, IMAGE_SIZE, false);
        numb7 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.numb7), IMAGE_SIZE, IMAGE_SIZE, false);
        numb8 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.numb8), IMAGE_SIZE, IMAGE_SIZE, false);
    }

    private void updateFieldSettings() {
        fieldHeight = Settings.cellsInHeight;
        fieldWidth = Settings.cellsInWidth;
        fieldMines = Settings.numberOfMines;
    }

    @Override
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

    @Override
    public void checkCell(Point point) {
        if (gameStoped) return;
        for (SquareCell cell: cells){
            if (cell.containsPoint(point)){
                if (!cell.isOpen()){
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
                    if (checkedButton.equals(CheckedButton.TOUCH)){
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
        for (SquareCell cell : cells) {
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

    private void mining() {         //минирование поля
        int totalMines;
        if (fieldMines > fieldWidth * fieldHeight % 2) {
            totalMines = fieldHeight * fieldWidth - 1;
            for (SquareCell cell : cells) cell.setContainsMine(!cell.isOpen());
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

    private void openBrothers(SquareCell cell) {
        deque.add(cell);
        while (!deque.isEmpty()) {
            SquareCell newCell = deque.removeFirst();
            if (!newCell.isOpen()) {
                newCell.setIsOpen(true);
                totalOpenCells++;
            }

            if (newCell.getMinesBeside() == 0) {
                for (SquareCell brother : newCell.getBrothers()) {
                    if ( !brother.isOpen())
                        deque.addFirst(brother);
                }
            }
        }
    }

    // создание массива ячеек игрового поля
    // при этом происходит определение соседних для ячеек для данной
    private ArrayList<SquareCell> setFieldCells() {
        ArrayList<SquareCell> result = new ArrayList<>();

        int startPoint_x = 0;
        int startPoint_y = 0;

        for (int curFieldHeight = 1; curFieldHeight <= fieldHeight; curFieldHeight++) {
            for (int curFieldWidth = 1; curFieldWidth <= fieldWidth; curFieldWidth++) {
                SquareCell newCell = new SquareCell(new Point(startPoint_x, startPoint_y));
                result.add(newCell);

                if (curFieldWidth != 1) {    //левый
                    newCell.setWestBrother(result.get(result.size() - 2));
                    result.get(result.size() - 2).setEastBrother(newCell);
                }
                if (curFieldHeight != 1) {   // верхний
                    newCell.setNorthBrother(result.get(result.size() - fieldWidth - 1));
                    result.get(result.size() - fieldWidth - 1).setSouthBrother(newCell);
                }
                if (curFieldHeight != 1 && curFieldWidth != 1) {    //девый-верхний
                    newCell.setNorthwesternBrother(result.get(result.size() - 2 - fieldWidth));
                    result.get(result.size() - 2 - fieldWidth).setSoutheastBrother(newCell);
                }
                if (curFieldHeight != 1 && curFieldWidth != fieldWidth){    // правый-верхний
                    newCell.setNortheasternBrother(result.get(result.size() - fieldWidth));
                    result.get(result.size() - fieldWidth).setSouthwesternBrother(newCell);
                }

                startPoint_x += 150;
            }

            startPoint_x -= 150* fieldWidth;
            startPoint_y += 150;
        }

        return result;
    }

    // восстановление игрового поля после поворота экрана в соответствии с сохраненным состоянием
    @Override
    public void restoreField(Bundle savedInstanceState) {
        updateFieldSettings();

        // restore cells
        cells = restoreCells(savedInstanceState);

        firstOpening = (boolean) savedInstanceState.get("firstOpening");

        gameStoped = (boolean) savedInstanceState.get("gameStoped");

        totalOpenCells = (int) savedInstanceState.get("totalOpenCells");

        checkedButton = savedInstanceState.get("checkedButton").equals("flag") ? CheckedButton.FLAG :
                savedInstanceState.get("checkedButton").equals("question") ? CheckedButton.QUESTION :
                        CheckedButton.TOUCH;

        restoreDialog(savedInstanceState);

    }

    // восстановление ячеек игрового поля после переворота экрана в соответствии с сохранённым состоянием
    private ArrayList<SquareCell> restoreCells(Bundle savedInstanceState){
        ArrayList<SquareCell> result = setFieldCells();

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
    private void restoreDialog(Bundle savedInstanceState){
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

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        for (SquareCell cell : cells) {
            //cell
            paint.setColor(cell.getColor());
            paint.setStyle(Paint.Style.FILL);
            canvas.drawPath( cell.getSquarePath(), paint);

            //cell border
            paint.setColor(cell.getBorderColor());
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(5);
            canvas.drawPath(cell.getSquarePath(), paint);

            matrix.setTranslate(cell.getStartPoint().x + 20, cell.getStartPoint().y + 20);

            if (!cell.isOpen()) {
                if (cell.isContainsFlag())
                    canvas.drawBitmap(flag, matrix, paint);
                if (cell.isContainsQuestion())
                    canvas.drawBitmap(question, matrix, paint);
            }

            if ((cell.isOpen() || gameStoped )&& cell.isContainsMine())
                canvas.drawBitmap(mine, matrix, paint);

            if (cell.isOpen() && !cell.isContainsMine()) {
                if (cell.getMinesBeside() == 1) {
                    matrix.setTranslate(cell.getStartPoint().x + 40, cell.getStartPoint().y + 20);
                    canvas.drawBitmap(numb1, matrix, paint);
                }
                if (cell.getMinesBeside() == 2)
                    canvas.drawBitmap(numb2, matrix, paint);
                if (cell.getMinesBeside() == 3)
                    canvas.drawBitmap(numb3, matrix, paint);
                if (cell.getMinesBeside() == 4)
                    canvas.drawBitmap(numb4, matrix, paint);
                if (cell.getMinesBeside() == 5)
                    canvas.drawBitmap(numb5, matrix, paint);
                if (cell.getMinesBeside() == 6)
                    canvas.drawBitmap(numb6, matrix, paint);
                if (cell.getMinesBeside() == 7)
                    canvas.drawBitmap(numb7, matrix, paint);
                if (cell.getMinesBeside() == 8)
                    canvas.drawBitmap(numb8, matrix, paint);
            }
        }
    }

    @Override
    public void setCheckedButton(CheckedButton checkedButton) {
        this.checkedButton = checkedButton;
    }

    //--------------------- getters ------------------------------
    @Override
    public int height() { return fieldHeight;}

    @Override
    public int width() { return  fieldWidth; }

    @Override
    public int widthInPixel() { return fieldWidth*150; }

    @Override
    public int heightInPixel() { return fieldHeight*150; }

    @Override
    public boolean isGameStoped() { return gameStoped; }

    @Override
    public boolean isFirstOpening() { return firstOpening; }

    @Override
    public int getTotalOpenCells() { return  totalOpenCells; }

    @Override
    public CheckedButton getCheckedButton() { return checkedButton; }

    @Override
    public String getShownDialogString() {
        switch ( shownDialog ){
            case NONE: return "none";
            case DEFEAT: return "defeat";
            default: return "greeting";
        }
    }

    @Override
    public ArrayList<Integer> getCellsWithMine() {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < cells.size(); i++){
            if (cells.get(i).isContainsMine()) result.add(i);
        }
        return result;
    }

    @Override
    public ArrayList<Integer> getCellsWithFlag() {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < cells.size(); i++){
            if (cells.get(i).isContainsFlag()) result.add(i);
        }
        return result;
    }

    @Override
    public ArrayList<Integer> getCellsWithQuestion() {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < cells.size(); i++){
            if (cells.get(i).isContainsQuestion()) result.add(i);
        }
        return result;
    }

    @Override
    public ArrayList<Integer> getOpenCells() {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < cells.size(); i++){
            if (cells.get(i).isOpen()) result.add(i);
        }
        return result;
    }
}
