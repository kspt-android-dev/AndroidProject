package com.shminesweeper.shminesweeper;

import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Point;

import java.util.ArrayList;

public class Cell {

    private Point startPoint;

    private boolean containsFlag;
    private boolean containsQuestion;
    private boolean containsMine;
    private boolean isOpen;

    private Cell eastBrother;
    private Cell southeastBrother;
    private Cell southwesternBrother;
    private Cell westBrother;
    private Cell northwesternBrother;
    private Cell northeasternBrother;

    private Path path;

    Cell(Point point) {

        // точка, с которой начинается рисование ячейчи
        this.startPoint = point;

        this.containsFlag = false;
        this.containsQuestion = false;
        this.containsMine = false;
        this.isOpen = false;

        // соседние ячейки
        this.eastBrother = null;
        this.southeastBrother = null;
        this.southwesternBrother = null;
        this.westBrother = null;
        this.northwesternBrother = null;
        this.northeasternBrother = null;

        this.setPath();
    }

    // построение пути, по которому на игровом поле будет нарисована данная ячейка
    // путь каждой ячейки зависит от точки startPoint(она уникальна для каждой ячейки и передаётся при создании ячейки)
    private void setPath(){
        path = new Path();

        path.moveTo(startPoint.x, startPoint.y);
        path.rLineTo(100, -100);
        path.rLineTo(100, 100);
        path.rLineTo(0,100);
        path.rLineTo(-100, 100);
        path.rLineTo(-100, -100);
        path.rLineTo(0, -100);
        path.close();
    }

    // определение принадлежности координат, полученных с экрана, данной ячейке
    // для шестигранной ячейки проверка происходит при помощи разбиения на квадрат и 4 треугольника
    public boolean isContainsPoint(Point point){
        //top-left triangle
        if ((point.x <= startPoint.x + 100) && (point.y <= startPoint.y)) {
            return ((((startPoint.x + 100) - point.x)*50) + ((startPoint.y - point.y)*50) <= 5000);
        }
        //top-right triangle
        if ((point.x >= startPoint.x + 100) && (point.y <= startPoint.y)) {
            return (((point.x - (startPoint.x + 100))*50) + ((startPoint.y - point.y)*50) <= 5000);
        }
        //bottom-left triangle
        if ((point.x <= startPoint.x+100) && (point.y >= startPoint.y+100)){
            return ((((startPoint.x + 100) - point.x)*50) + ((point.y - (startPoint.y + 100))*50) <= 5000);
        }
        //bottom-right triangle
        if (point.x >= startPoint.x+100 && point.y >= startPoint.y+100){
            return ((point.x - (startPoint.x + 100))*50 + (point.y - (startPoint.y + 100))*50 <= 5000);
        }
        //cube
        return (point.x >= startPoint.x && point.x <= startPoint.x+200 &&
                point.y >= startPoint.y && point.y <= startPoint.y+100);
    }

    public boolean isContainsMine(){
        return containsMine;
    }

    public boolean isContainsFlag() { return containsFlag; }

    public boolean isContainsQuestion() { return containsQuestion; }

    public boolean isOpen() { return isOpen; }

    // --------------- getters -----------------

    public Point getStartPoint(){ return this.startPoint;}

    public Path getPath(){ return path; }

    // получение цвета ячейки в зависимости от её состояния (открыта / закрыта)
    public int getColor() {
        if (this.isOpen) return Color.argb(0, 0, 0, 0);
        else return Color.argb(255,121, 121, 121);
    }

    public int getBorderColor(){
        return R.color.gray400;
    }

    public int getMinesBeside(){
        int result = 0;
        result += westBrother == null ? 0 : westBrother.isContainsMine() ? 1 : 0;
        result += northwesternBrother == null ? 0 : northwesternBrother.isContainsMine() ? 1 : 0;
        result += northeasternBrother == null ? 0 : northeasternBrother.isContainsMine() ? 1 : 0;
        result += eastBrother == null ? 0 : eastBrother.isContainsMine() ? 1 : 0;
        result += southeastBrother == null ? 0 : southeastBrother.isContainsMine() ? 1 : 0;
        result += southwesternBrother == null ? 0 : southwesternBrother.isContainsMine() ? 1 : 0;

        return result;
    }

    public ArrayList<Cell> getBrothers(){
        ArrayList<Cell> brothers = new ArrayList<>();

        if (westBrother !=null) brothers.add(westBrother);
        if (northwesternBrother != null) brothers.add(northwesternBrother);
        if (northeasternBrother != null) brothers.add(northeasternBrother);
        if (eastBrother != null) brothers.add(eastBrother);
        if (southeastBrother != null) brothers.add(southeastBrother);
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

    public void setEastBrother(Cell brother) { eastBrother = brother; }

    public void setSoutheastBrother(Cell brother) {
        southeastBrother = brother;
    }

    public void setSouthwesternBrother(Cell brother) {
        southwesternBrother = brother;
    }

    public void setWestBrother(Cell brother) {
        westBrother = brother;
    }

    public void setNorthwesternBrother(Cell brother) {
        northwesternBrother = brother;
    }

    public void setNortheasternBrother(Cell brother) {
        northeasternBrother = brother;
    }
}
