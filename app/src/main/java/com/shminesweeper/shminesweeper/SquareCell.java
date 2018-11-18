package com.shminesweeper.shminesweeper;

import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Point;

import java.util.ArrayList;

public class SquareCell {

    private Point startPoint;

    private boolean containsFlag;
    private boolean containsQuestion;
    private boolean containsMine;
    private boolean isOpen;

    private SquareCell eastBrother;
    private SquareCell southeastBrother;
    private SquareCell southBrother;
    private SquareCell southwesternBrother;
    private SquareCell westBrother;
    private SquareCell northwesternBrother;
    private SquareCell northBrother;
    private SquareCell northeasternBrother;

    private Path path;

    SquareCell(Point point){

        this.startPoint = point;

        this.containsFlag = false;
        this.containsQuestion = false;
        this.containsMine = false;
        this.isOpen = false;

        this.eastBrother = null;
        this.southeastBrother = null;
        this.southBrother = null;
        this.southwesternBrother = null;
        this.westBrother = null;
        this.northwesternBrother = null;
        this.northBrother = null;
        this.northeasternBrother = null;

        this.setPath();
    }

    // построение пути, по которому на игровом поле будет нарисована данная ячейка
    // путь каждой ячейки зависит от точки startPoint(она уникальна для каждой ячейки и передаётся при создании ячейки)
    private void setPath() {
        path = new Path();

        path.moveTo(startPoint.x, startPoint.y);
        path.rLineTo(150, 0);
        path.rLineTo(0, 150);
        path.rLineTo(-150, 0);
        path.rLineTo(0, -150);
        path.close();
    }

    // определение принадлежности координат, полученных с экрана, данной ячейке
    public boolean containsPoint(Point point) {
        return (point.x >= startPoint.x && point.x <= startPoint.x + 150) &&
                (point.y >= startPoint.y && point.y <= startPoint.y + 150);
    }

    // --------------- getters -----------------

    public boolean isContainsMine(){
        return containsMine;
    }

    public boolean isContainsFlag() { return containsFlag; }

    public boolean isContainsQuestion() { return containsQuestion; }

    public boolean isOpen() { return isOpen; }

    public Point getStartPoint(){ return this.startPoint;}

    public Path getSquarePath() { return path;}

    // получение цвета ячейки в зависимости от её состояния (открыта / закрыта)
    public int getColor() {
        if (this.isOpen) return Color.argb(0, 0, 0, 0);
        else return Color.argb(255,121, 121, 121);
    }

    public int getBorderColor(){
        return R.color.gray400;
    }

    public int getMinesBeside() {
        int result = 0;
        result += westBrother == null ? 0 : westBrother.isContainsMine() ? 1 : 0;
        result += northwesternBrother == null ? 0 : northwesternBrother.isContainsMine() ? 1 : 0;
        result += northBrother == null ? 0 : northBrother.isContainsMine() ? 1 : 0;
        result += northeasternBrother == null ? 0 : northeasternBrother.isContainsMine() ? 1 : 0;
        result += eastBrother == null ? 0 : eastBrother.isContainsMine() ? 1 : 0;
        result += southeastBrother == null ? 0 : southeastBrother.isContainsMine() ? 1 : 0;
        result += southBrother == null ? 0 : southBrother.isContainsMine() ? 1 : 0;
        result += southwesternBrother == null ? 0 : southwesternBrother.isContainsMine() ? 1 : 0;

        return result;
    }

    public ArrayList<SquareCell> getBrothers() {
        ArrayList<SquareCell> brothers = new ArrayList<>();

        if (westBrother != null) brothers.add(westBrother);
        if (northwesternBrother != null) brothers.add(northwesternBrother);
        if (northBrother != null) brothers.add(northBrother);
        if (northeasternBrother != null) brothers.add(northeasternBrother);
        if (eastBrother != null) brothers.add(eastBrother);
        if (southeastBrother != null) brothers.add(southeastBrother);
        if (southBrother != null) brothers.add(southBrother);
        if (southwesternBrother != null) brothers.add(southwesternBrother);

        return brothers;
    }

    // ----------- setters -------------

    public void setIsOpen(boolean condition){ this.isOpen = condition;}

    public void setContainsMine(boolean condition) {
        containsMine = condition;
    }

    public void setContainsFlag(boolean condition) { containsFlag = condition; }

    public void setContainsQuestion(boolean condition) {
        containsQuestion = condition;
    }

    public void setEastBrother(SquareCell brother) { eastBrother = brother; }

    public void setSoutheastBrother(SquareCell brother) {
        southeastBrother = brother;
    }

    public void setSouthBrother(SquareCell brother) { southBrother = brother;}

    public void setSouthwesternBrother(SquareCell brother) {
        southwesternBrother = brother;
    }

    public void setWestBrother(SquareCell brother) {
        westBrother = brother;
    }

    public void setNorthwesternBrother(SquareCell brother) {
        northwesternBrother = brother;
    }

    public void setNorthBrother(SquareCell brother) { northBrother = brother;}

    public void setNortheasternBrother(SquareCell brother) {
        northeasternBrother = brother;
    }
}
