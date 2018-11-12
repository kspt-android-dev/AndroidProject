package ru.polytech.course.pashnik.lines.Core;

import java.util.ArrayList;
import java.util.List;

public class WinLines {
    private final List<Line> winLines = new ArrayList<>();

    public void addWinLine(int length, Cell start, Cell direction) {
        winLines.add(new Line(length, start, direction));
    }

    public void removeAllWinLines() {
        winLines.clear();
    }

    public boolean isEmpty() {
        return winLines.isEmpty();
    }

    public int getSize() {
        return winLines.size();
    }

    public Line getWinLine(int index) {
        return winLines.get(index);
    }
}
