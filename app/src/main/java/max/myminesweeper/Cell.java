package max.myminesweeper;

import android.graphics.Path;
import android.graphics.Point;


public class Cell {

    enum CellCondition {CLOSED, OPEN, MINE, DEFUSED}
    static final int MINE = 10;

    private Point point;
    private int neighbourMines;
    private int sideLength;
    private CellCondition condition;
    private Path path;

    public Cell(Point startPoint, int sideLength) {
        this.point = startPoint;
        this.neighbourMines = 0;
        this.sideLength = sideLength;
        this.condition = CellCondition.CLOSED;
    }

    public Path path() {
        path = new Path();
        path.moveTo(point.x, point.y);
        path.rLineTo(sideLength, 0);
        path.rLineTo(0, sideLength);
        path.rLineTo(-sideLength, 0);
        path.rLineTo(0, -sideLength);
        path.close();
        return path;
    }

    public boolean containsPoint(Point somePoint) {
        return (somePoint.x >= point.x && somePoint.x <= point.x + sideLength) &&
                (somePoint.y >= point.y && somePoint.y <= point.y + sideLength);
    }

    public Point getPoint() {
        return this.point;
    }

    public int getNeighbourMines() {
        return this.neighbourMines;
    }

    public CellCondition getCondition() {
        return this.condition;
    }

    public Path getPath() {
        return path();
    }

    public void setNeighbourMines(int count) {
        this.neighbourMines = count;
    }

    public void setCondition(CellCondition cond) {
        this.condition = cond;
    }
}