package name.mizunotlt.eruditkurs;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

import static android.view.MotionEvent.ACTION_CANCEL;
import static android.view.MotionEvent.ACTION_DOWN;

//Вся дичь обработки тут а именно выбор буквы и выбор клетки куда поставить
public class GameFieldListener implements View.OnTouchListener {
    private float startX = 0;
    private float startY = 0;
    private final int incXLetter = 95;
    private final int SIZE = 60;
    private GameField gameField;
    private Point startPointCell;
    private Point startPointLetter;

    GameFieldListener(GameField gameField){

        this.gameField = gameField;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch (View view, MotionEvent motionEvent){
        switch (motionEvent.getAction()){
            case ACTION_DOWN:{
                startX = motionEvent.getX();
                startY = motionEvent.getY();

                for(Cell cell: gameField.getGames().getWorkListCells()){
                    startPointCell = cell.getStartPoint();
                    if (((int) startX  >= startPointCell.x) & ((int)startX <= startPointCell.x + SIZE)
                            &((int) startY  >= startPointCell.y) & ((int)startY <= startPointCell.y + SIZE)){
                        int num = cell.getNumCell();
                        gameField.updateField(num);
                        break;
                    }
                }
                for (CellForLetter cell : gameField.getGames().getListCellLetter()){
                    startPointLetter = cell.getStartPoint();
                    if (((int) startX  >= startPointLetter.x) & ((int)startX <= startPointLetter.x + SIZE)
                        &((int) startY  >= startPointLetter.y) & ((int)startY <= startPointLetter.y + SIZE)){
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
