package name.mizunotlt.eruditkurs;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

import static android.view.MotionEvent.ACTION_CANCEL;
import static android.view.MotionEvent.ACTION_DOWN;

//Вся дичь обработки тут а именно выбор буквы и выбор клетки куда поставить
class GameFieldListener implements View.OnTouchListener {
    private int size;
    private GameField gameField;

    GameFieldListener(GameField gameField){

        this.gameField = gameField;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch (View view, MotionEvent motionEvent){
        size = gameField.getSizeCell();
        switch (motionEvent.getAction()){
            case ACTION_DOWN:{
                float startX = motionEvent.getX();
                float startY = motionEvent.getY();

                for(Cell cell: gameField.getGames().getWorkListCells()){
                    Point startPointCell = cell.getStartPoint();
                    if (((int) startX >= startPointCell.x) & ((int) startX <= startPointCell.x + size)
                            &((int) startY >= startPointCell.y) & ((int) startY <= startPointCell.y + size)){
                        int num = cell.getNumCell();
                        gameField.updateField(num);
                        break;
                    }
                }
                for (CellForLetter cell : gameField.getGames().getListCellLetter()){
                    Point startPointLetter = cell.getStartPoint();
                    if (((int) startX >= startPointLetter.x) & ((int) startX <= startPointLetter.x + size)
                        &((int) startY >= startPointLetter.y) & ((int) startY <= startPointLetter.y + size)){
                        cell.changeSelect();
                        break;
                    }
                }
                return true;

            }
            case ACTION_CANCEL:
        }
        return false;
    }
}
