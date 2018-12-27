package com.example.checkers;

import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;

public class Cell {

    private Point startPoint;
    private int backgroundColor;
    private int size;
    private Path path;
    private int checkerColor;

    // ближайшие клетки, в которые можно сходить
    private Cell closeTopLeft;
    private Cell closeTopRight;
    private Cell closeBottomLeft;
    private Cell closeBottomRight;

    // дальние клетки, в которые можно сходить
    private Cell farTopLeft;
    private Cell farTopRight;
    private Cell farBottomLeft;
    private Cell farBottomRight;

    enum CellCondition { CONTAINS_CHECKER, EMPTY, CONTAINS_GRABBED_CHECKER, CONTAINS_CROWN, CONTAINS_GRABBED_CROWN}
    private CellCondition condition;

    public Cell(Point startPoint, int size, int color, int white) {
        this.startPoint = startPoint;
        this.backgroundColor = color;
        this.size = size;
        this.checkerColor = white;
        this.condition = CellCondition.EMPTY;

        this.closeBottomLeft = null;
        this.closeBottomRight = null;
        this.closeTopLeft = null;
        this.closeTopRight = null;
        this.farBottomLeft = null;
        this.farBottomRight = null;
        this.farTopLeft = null;
        this.farTopRight = null;

        setPath();
    }

    private void setPath() {
        path = new Path();
        path.moveTo(startPoint.x, startPoint.y);
        path.rLineTo(size, 0);
        path.rLineTo(0,  size);
        path.rLineTo(- size, 0);
        path.rLineTo(0, - size);
    }

    public boolean contains(Point point) {
        return point.x > startPoint.x && point.y > startPoint.y
                && point.x < startPoint.x + size && point.y < startPoint.y + size;
    }

    public Path getPath() {
        return path;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public CellCondition getCondition() { return this.condition; }

    public Point center() {
         return new Point(startPoint.x + (size / 2), startPoint.y + (size / 2));
    }

    public void setCheckerColor(int color) {
        this.checkerColor = color;
    }

    public void setCondition(CellCondition condition) { this.condition = condition; }

    public void setCloseTopLeft(Cell cell) { this.closeTopLeft = cell; }

    public void setCloseTopRight( Cell cell) { this.closeTopRight = cell; }

    public void setCloseBottomLeft(Cell cell) { this.closeBottomLeft = cell; }

    public void setCloseBottomRight (Cell cell) { this.closeBottomRight = cell; }

    public void setFarTopLeft (Cell cell) { this.farTopLeft = cell; }

    public void setFarTopRight (Cell cell) { this.farTopRight = cell; }

    public void setFarBottomLeft (Cell cell) {this.farBottomLeft = cell; }

    public void setFarBottomRight (Cell cell) { this.farBottomRight = cell; }

    public int getCheckerColor() {
        return checkerColor;
    }


    public Cell getCloseTopLeft() { return closeTopLeft; }

    public Cell getCloseTopRight() { return closeTopRight; }

    public Cell getCloseBottomLeft() { return closeBottomLeft; }

    public Cell getCloseBottomRight () { return closeBottomRight; }

    public Cell getFarTopLeft () { return farTopLeft; }

    public Cell getFarTopRight () { return farTopRight; }

    public Cell getFarBottomLeft () { return farBottomLeft; }

    public Cell getFarBottomRight () { return farBottomRight; }

    // являестя ли ячейка ближней
    public boolean isCloseCell(Point point) {
        if (closeTopLeft != null && closeTopLeft.contains(point)) return true;
        if (closeTopRight != null && closeTopRight.contains(point)) return true;
        if (closeBottomLeft != null && closeBottomLeft.contains(point)) return true;
        if (closeBottomRight != null && closeBottomRight.contains(point)) return true;

        return false;
    }

    // является ли ячейка даленей для данной
    public boolean isFarCell(Point point) {
        if (farTopLeft != null && farTopLeft.contains(point)) return true;
        if (farTopRight != null && farTopRight.contains(point)) return true;
        if (farBottomRight != null && farBottomRight.contains(point)) return true;
        if (farBottomLeft != null && farBottomLeft.contains(point)) return true;

        return false;
    }

}

