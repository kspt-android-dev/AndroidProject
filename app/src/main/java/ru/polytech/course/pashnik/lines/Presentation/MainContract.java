package ru.polytech.course.pashnik.lines.Presentation;

import android.content.Context;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

import ru.polytech.course.pashnik.lines.Core.Board;
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

        void makeUpDownAnimation(Cell cell, ColorType color);

        void makeAppearanceAnimation(Cell cell, ColorType color);

        void stopUpDownAnimation();

        void stopAppearanceAnimation();
    }

    interface Presenter {
        void onCellWasClicked(float x, float y);

        int getScore();

        void initGameView();

        void restoreModel(Bundle savedState);

        void saveModel(Bundle saveState);

        void exportData(Context context);

        void restoreLastGame(Context context);

        boolean fileExist(Context context);
    }

    interface Model {
        boolean isWin(Cell currentCell);

        boolean haveCell(Cell cell);

        ColorType getColor(Cell cell);

        void addCell(Cell cell, ColorType color);

        WinLines getWinLines();

        void removeCell(Cell cell);

        boolean isFull();

        Map<Cell, ColorType> getModel();

        void setMap(Map<Cell, ColorType> hashMap);
    }

}
