package com.shminesweeper.shminesweeper;

import android.content.Context;
import android.graphics.*;
import android.view.View;
import android.widget.FrameLayout;

public class PlayingField extends View {

    FieldLogic fieldLogic;

    private Paint paint = new Paint();

    enum CheckedButton {FLAG, TOUCH, QUESTION}

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


    }

    public void setFieldLogic(FieldLogic fieldLogic) {
        this.fieldLogic = fieldLogic;
        setImages();
    }

    private void setImages() {
        final int IMAGE_SIZE = fieldLogic.getCellWidth()*2/3;

        flag = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.flag), IMAGE_SIZE, IMAGE_SIZE, false);
        mine = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.mine), IMAGE_SIZE, IMAGE_SIZE, false);
        question = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.question), IMAGE_SIZE, IMAGE_SIZE, false);
        numb1 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.numb1), IMAGE_SIZE*2/3, IMAGE_SIZE, false);
        numb2 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.numb2), IMAGE_SIZE, IMAGE_SIZE, false);
        numb3 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.numb3), IMAGE_SIZE, IMAGE_SIZE, false);
        numb4 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.numb4), IMAGE_SIZE, IMAGE_SIZE, false);
        numb5 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.numb5), IMAGE_SIZE, IMAGE_SIZE, false);
        numb6 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.numb6), IMAGE_SIZE, IMAGE_SIZE, false);
    }

    public void startNewGame() {

        checkedButton = CheckedButton.TOUCH;

        invalidate();
    }


    public void checkCell(Point point){
        if (checkedButton.equals(CheckedButton.QUESTION))   fieldLogic.putQuestion(point);
        else if (checkedButton.equals(CheckedButton.FLAG))   fieldLogic.putFlag(point);
        else fieldLogic.openCell(point);
        invalidate();
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (Cell cell : fieldLogic.getCells()) {

            //cell
            if (cell.isOpen()) paint.setColor(getResources().getColor(R.color.openCell));
                else paint.setColor(getResources().getColor(R.color.closeCell));
            paint.setStyle(Paint.Style.FILL);
            canvas.drawPath( cell.getPath(), paint);

            //cell border
            paint.setColor(getResources().getColor(R.color.cell_border));
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(5);
            canvas.drawPath(cell.getPath(), paint);


            if (fieldLogic.getFieldMode().equals(FieldLogic.FieldMode.HEXAGONAL))
                matrix.setTranslate(cell.getStartPoint().x + fieldLogic.getCellWidth()/5,
                        cell.getStartPoint().y);
            else
                matrix.setTranslate(cell.getStartPoint().x + fieldLogic.getCellWidth()/5,
                        cell.getStartPoint().y + fieldLogic.getCellHeight()/8);
            if (!cell.isOpen()) {
                if (cell.isContainsFlag())
                    canvas.drawBitmap(flag, matrix, paint);
                if (cell.isContainsQuestion()) {
                    canvas.drawBitmap(question, matrix, paint);
                }
            }

            if ((cell.isOpen() || fieldLogic.getGameCondition().equals(FieldLogic.GameCondition.OVERVIEW)) &&
                    cell.isContainsMine()) {
                canvas.drawBitmap(mine, matrix, paint);
            }

            if (cell.isOpen() && !cell.isContainsMine()) {
                if (cell.getMinesBeside() == 1) {
                    if (fieldLogic.getFieldMode().equals(FieldLogic.FieldMode.HEXAGONAL))
                        matrix.setTranslate(cell.getStartPoint().x + fieldLogic.getCellWidth()/5,
                                cell.getStartPoint().y - fieldLogic.getCellHeight()/13);
                    else
                        matrix.setTranslate(cell.getStartPoint().x + fieldLogic.getCellWidth()/5,
                                cell.getStartPoint().y + fieldLogic.getCellHeight()/8);
                    canvas.drawBitmap(numb1, matrix, paint);
                }
                if (cell.getMinesBeside() == 2) {
                    if (fieldLogic.getFieldMode().equals(FieldLogic.FieldMode.HEXAGONAL))
                        matrix.setTranslate(cell.getStartPoint().x + fieldLogic.getCellWidth()/5,
                                cell.getStartPoint().y - fieldLogic.getCellHeight()/13);
                    else
                        matrix.setTranslate(cell.getStartPoint().x + fieldLogic.getCellWidth()/5,
                                cell.getStartPoint().y + fieldLogic.getCellHeight()/8);
                    canvas.drawBitmap(numb2, matrix, paint);
                }
                if (cell.getMinesBeside() == 3) {
                    if (fieldLogic.getFieldMode().equals(FieldLogic.FieldMode.HEXAGONAL))
                        matrix.setTranslate(cell.getStartPoint().x + fieldLogic.getCellWidth()/5,
                                cell.getStartPoint().y - fieldLogic.getCellHeight()/13);
                    else
                        matrix.setTranslate(cell.getStartPoint().x + fieldLogic.getCellWidth()/5,
                                cell.getStartPoint().y + fieldLogic.getCellHeight()/8);
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
    public void setCheckedButton(CheckedButton checkedButton) { this.checkedButton = checkedButton; }

    // --------------- getters ------------

    public int widthInPixel() { return fieldLogic.getFieldWidth() * fieldLogic.getCellWidth() + fieldLogic.getCellWidth()/2 ; }

    public int heightInPixel() { return fieldLogic.getFieldHeight() * (fieldLogic.getCellHeight()*2/3) + (fieldLogic.getCellHeight()/3); }

    public CheckedButton getCheckedButton() { return checkedButton; }
}
