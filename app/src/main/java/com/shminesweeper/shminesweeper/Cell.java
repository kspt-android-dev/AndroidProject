package com.shminesweeper.shminesweeper;

import android.graphics.Path;
import android.graphics.Point;

import java.util.ArrayList;

public class Cell {

    private final FieldLogic.FieldMode fieldMode;

    private final Point startPoint;

    enum CellCondition {FLAG, QUESTION, OPEN, CLOSED_EMPTY}
    private CellCondition condition;

    private boolean containsMine;

    private final ArrayList<Cell> brothers;

    private Path path;

    private final int offset;

    public Cell(Point point,int offset, FieldLogic.FieldMode fieldMode) {

        // точка, с которой начинается рисование ячейчи
        this.startPoint = point;

        condition = CellCondition.CLOSED_EMPTY;

        this.containsMine = false;

        // соседние ячейки
        this.brothers = new ArrayList<>();

        this.fieldMode = fieldMode;

        this.offset = offset;

    }

    // построение пути, по которому на игровом поле будет нарисована данная ячейка
    // путь каждой ячейки зависит от точки startPoint(она уникальна для каждой ячейки и передаётся при создании ячейки)
    private Path setHexagonalPath() {

        path = new Path();
        path.moveTo(startPoint.x, startPoint.y);
        path.rLineTo(offset, -offset);
        path.rLineTo(offset, offset);
        path.rLineTo(0, offset);
        path.rLineTo(-offset, offset);
        path.rLineTo(-offset, -offset);
        path.rLineTo(0, -offset);
        path.close();

        return path;
    }

    private Path setSquarePath() {

        path = new Path();
        path.moveTo(startPoint.x, startPoint.y);
        path.rLineTo(offset, 0);
        path.rLineTo(0, offset);
        path.rLineTo(-offset, 0);
        path.rLineTo(0, -offset);
        path.close();

        return path;
    }


    public boolean isContainsPoint(Point point) {
        if (fieldMode.equals(FieldLogic.FieldMode.HEXAGONAL)) return isContainsPointForHexagonal(point);
        else return isContainsPointForSquare(point);
    }
    // определение принадлежности координат, полученных с экрана, данной ячейке.
    // для шестигранной ячейки проверка происходит при помощи разбиения на квадрат и 4 треугольника.
    // проверка принадлежности точки треугольнику происходит следующим образом:
    //   если сумма площадей 2ух треугольников меньше площади рассматриваемого, то точка принадлежит рассматриваемому треугольнику;
    //   1ый треугольник: основание - катет рассматриваемого, высота - перпендикуляр от рассматриваемой точки до основания
    //   2ой треугольник: основание - другой катет рассматриваемого треугольника, высота - перпендикуляр от рассматриваемой точки до основания
    private boolean isContainsPointForHexagonal(Point point) {

        //top-left triangle
        if ((point.x <= startPoint.x + offset) && (point.y <= startPoint.y)) {
            return ((((startPoint.x + offset) - point.x) * offset / 2) +
                    ((startPoint.y - point.y) * offset / 2) <= offset*offset/2);
        }
        //top-right triangle
        if ((point.x >= startPoint.x + offset) && (point.y <= startPoint.y)) {
            return (((point.x - (startPoint.x + offset)) * offset / 2) +
                    ((startPoint.y - point.y) * offset / 2) <= offset*offset/2);
        }
        //bottom-left triangle
        if ((point.x <= startPoint.x + offset) && (point.y >= startPoint.y + offset)) {
            return ((((startPoint.x + offset) - point.x) * offset / 2) +
                    ((point.y - (startPoint.y + offset)) * offset / 2) <= offset*offset/2);
        }
        //bottom-right triangle
        if (point.x >= startPoint.x + offset && point.y >= startPoint.y + offset) {
            return ((point.x - (startPoint.x + offset)) * offset / 2 +
                    (point.y - (startPoint.y + offset)) * offset / 2 <= offset*offset/2);
        }
        //cube
        return (point.x >= startPoint.x && point.x <= startPoint.x + offset * 2 &&
                point.y >= startPoint.y && point.y <= startPoint.y + offset);
    }

    // определение принадлежности координат, полученных с экрана, данной ячейке
    private boolean isContainsPointForSquare(Point point) {
        return (point.x >= startPoint.x && point.x <= startPoint.x + offset) &&
                (point.y >= startPoint.y && point.y <= startPoint.y + offset);
    }

    public boolean isContainsMine() {
        return this.containsMine;
    }

    public boolean isContainsFlag() {
        return condition.equals(CellCondition.FLAG);
    }

    public boolean isContainsQuestion() {
        return condition.equals(CellCondition.QUESTION);
    }

    public boolean isOpen() {
        return condition.equals(CellCondition.OPEN);
    }

    // --------------- getters -----------------

    public Point getStartPoint() {
        return this.startPoint;
    }

    public Path getPath() {
        if (fieldMode.equals(FieldLogic.FieldMode.HEXAGONAL)) return setHexagonalPath();
        else return setSquarePath();
    }


    public int getMinesBeside() {
        int result = 0;
        for (Cell brother : brothers) {
            result += (brother.isContainsMine()) ? 1 : 0;
        }
        return result;
    }

    // c "западного" по часовой
    public ArrayList<Cell> getBrothers() {
        return brothers;
    }


    // ----------- setters -------------

    public void setIsOpen() {
        this.condition = Cell.CellCondition.OPEN;
    }

    public void setContainsMine(boolean condition) {
        containsMine = condition;
    }

    public void setContainsFlag() {
        this.condition = CellCondition.FLAG;
    }

    public void setContainsQuestion() {
        this.condition = CellCondition.QUESTION;
    }

    public void setContainsNothing() {
        this.condition = CellCondition.CLOSED_EMPTY;
    }

    public void setBrother(Cell cell) {
        this.brothers.add(cell);
    }

}
