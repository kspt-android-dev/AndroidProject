package ru.polytech.course.pashnik.lines;

import ru.polytech.course.pashnik.lines.Core.Cell;
import ru.polytech.course.pashnik.lines.Core.ColorType;
import ru.polytech.course.pashnik.lines.Core.WinLines;

public interface MainContract {
    interface ViewInterface {
        void drawBallOnBoard(Cell cell, ColorType color);

        void clearBallOnBoard(Cell cell);
    }

    interface Presenter {
        void onCellWasClicked(float x, float y);

        void initGameView();
    }

    interface Model {
        boolean isWin(Cell currentCell);

        boolean haveCell(Cell cell);

        ColorType getColor(Cell cell);

        void addCell(Cell cell, ColorType color);

        WinLines getWinLines();

        void removeCell(Cell cell);
    }

}
