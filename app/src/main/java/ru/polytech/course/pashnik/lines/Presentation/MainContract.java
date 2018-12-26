package ru.polytech.course.pashnik.lines.Presentation;

import android.os.Bundle;

import java.util.HashMap;

import ru.polytech.course.pashnik.lines.Core.Cell;
import ru.polytech.course.pashnik.lines.Core.ColorType;
import ru.polytech.course.pashnik.lines.Core.WinLines;

public interface MainContract {
    interface ViewInterface {
        void drawBallOnBoard(Cell cell, ColorType color);

        void clearBallOnBoard(Cell cell);

        void drawBallOnScoreView(Cell cell, ColorType color);

        void setScore(String score);

        void createDialog();
    }

    interface Presenter {
        void onCellWasClicked(float x, float y);

        int getScore();

        void initGameView();

        void restoreModel(Bundle savedState);

        void saveModel(Bundle saveState);
    }

    interface Model {
        boolean isWin(Cell currentCell);

        boolean haveCell(Cell cell);

        ColorType getColor(Cell cell);

        void addCell(Cell cell, ColorType color);

        WinLines getWinLines();

        void removeCell(Cell cell);

        boolean isFull();

        HashMap getModel();

        void setModel(HashMap<Cell, ColorType> model);

    }

}
