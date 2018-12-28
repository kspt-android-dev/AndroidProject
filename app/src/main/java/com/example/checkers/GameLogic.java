package com.example.checkers;

import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class GameLogic {

    private ArrayList<Cell> cells;

    // количество битых шашек
    private int numbOfBlackDead;
    private int numbOfWiteDead;

    //индикатор для убитой шашки
    private boolean whiteKill;
    private boolean blackKill;

    // цвет ходящего игрока
    enum TurnColor {WHITE, BLACK}
    private TurnColor turnColor;

    enum TurnSide {TOP, BOTTOM}
    private TurnSide turnSide;

    //кто-то был убит
    private boolean someoneKilledJustNow;

    // цвет ячеек поля
    private int LIGHT;
    private int DARK;

    private int black;
    private int white;

    // цвета игроков
    static int bottomPlayerColor;
    static int upperPlayerColor;

    private int fieldSize;
    private Point padding; // отступ до поля от края экрана
    private int hellHeight; // для dead checkers входит в размер поля

    public GameLogic(int colorLight, int colorDark,
                     int black, int white) {
        upperPlayerColor = black;
        bottomPlayerColor = white;
        this.LIGHT = colorLight;
        this.DARK = colorDark;
        this.black = black;
        this.white = white;
    }

    public void setParams(int fieldSize, Point padding) {
        this.fieldSize = fieldSize;
        this.padding = padding;

        hellHeight = fieldSize/12;
    }

    public void startNewGame() {

        cells = setFieldCells();

        numbOfBlackDead = 0;
        numbOfWiteDead = 0;

        turnColor = TurnColor.WHITE;
        turnSide = (upperPlayerColor == white) ? TurnSide.TOP : TurnSide.BOTTOM;

        someoneKilledJustNow = false;

        // расставляем шашки на стол
        setCheckers();
    }

    public boolean gameFinished() {
        // все убиты
        if (numbOfBlackDead == 20 || numbOfWiteDead == 20) return true;

        return topTrapped() || bottomTrapped();

    }

    // заперт ли верхний игрок
    private boolean topTrapped() {
        boolean trapped = true;

        for (Cell cell: cells) {
            if (cell.getCheckerColor() == upperPlayerColor) {
                // если нижние соседи не пустые, то походить нельзя
                if (cell.getCondition().equals(Cell.CellCondition.CONTAINS_CHECKER)) {
                    if ( (cell.getCloseBottomRight() != null &&
                                    cell.getCloseBottomRight().getCondition().equals(Cell.CellCondition.EMPTY) ) ||
                            (cell.getCloseBottomLeft() != null &&
                                    cell.getCloseBottomLeft().getCondition().equals(Cell.CellCondition.EMPTY) ) ||
                            (cell.getFarBottomLeft() != null &&
                                    cell.getFarBottomLeft().getCondition().equals(Cell.CellCondition.EMPTY) &&
                                    cell.getCloseBottomLeft().getCheckerColor() == bottomPlayerColor) ||
                            ( cell.getFarBottomRight() != null &&
                                    cell.getFarBottomRight().getCondition().equals(Cell.CellCondition.EMPTY) &&
                                    cell.getCloseBottomRight().getCheckerColor() == bottomPlayerColor))
                        trapped = false;
                }
                if (cell.getCondition().equals(Cell.CellCondition.CONTAINS_CROWN)){
                    if ( !crownTrapped(cell, bottomPlayerColor) )
                        trapped = false;
                }
            }
        }

        return trapped;
    }

    // заперка ли дамка
    private boolean crownTrapped(Cell cell, int enemyColor) {
        if ((cell.getCloseTopLeft() != null &&
                cell.getCloseTopLeft().getCondition().equals(Cell.CellCondition.EMPTY)) ||
                (cell.getCloseTopRight() != null &&
                        cell.getCloseTopRight().getCondition().equals(Cell.CellCondition.EMPTY)) ||
                (cell.getCloseBottomLeft() != null &&
                        cell.getCloseBottomLeft().getCondition().equals(Cell.CellCondition.EMPTY)) ||
                (cell.getCloseBottomRight() != null &&
                        cell.getCloseBottomRight().getCondition().equals(Cell.CellCondition.EMPTY)) ||
                (cell.getFarTopLeft() != null &&
                        cell.getFarTopLeft().getCondition().equals(Cell.CellCondition.EMPTY) &&
                        cell.getCloseTopLeft().getCheckerColor() == enemyColor) ||
                (cell.getFarTopRight() != null &&
                        cell.getFarTopRight().getCondition().equals(Cell.CellCondition.EMPTY) &&
                        cell.getCloseTopRight().getCheckerColor() == enemyColor) ||
                (cell.getFarBottomLeft() != null &&
                        cell.getFarBottomLeft().getCondition().equals(Cell.CellCondition.EMPTY) &&
                        cell.getCloseBottomLeft().getCheckerColor() == enemyColor) ||
                (cell.getFarBottomRight() != null &&
                        cell.getFarBottomRight().getCondition().equals(Cell.CellCondition.EMPTY) &&
                        cell.getCloseBottomRight().getCheckerColor() == enemyColor))
            return false;
        return true;
    }

    // заперт ли нижний игрок
    private boolean bottomTrapped() {
        boolean trapped = true;

        for (Cell cell:cells) {
            if (cell.getCheckerColor() == bottomPlayerColor) {
                // если верхние соседи не пустые, то походить нельзя
                if (    (cell.getCloseTopRight() != null &&
                                cell.getCloseTopRight().getCondition().equals(Cell.CellCondition.EMPTY) ) ||
                        (cell.getCloseTopLeft() != null &&
                                cell.getCloseTopLeft().getCondition().equals(Cell.CellCondition.EMPTY)) ||
                        (cell.getFarTopLeft() != null &&
                                cell.getFarTopLeft().getCondition().equals(Cell.CellCondition.EMPTY) &&
                                cell.getCloseTopLeft().getCheckerColor() == upperPlayerColor) ||
                        (cell.getFarTopRight() != null &&
                                cell.getFarTopRight().getCondition().equals(Cell.CellCondition.EMPTY) &&
                                cell.getCloseTopRight().getCheckerColor() == upperPlayerColor))
                    trapped = false;
                if (cell.getCondition().equals(Cell.CellCondition.CONTAINS_CROWN)){
                    if ( !crownTrapped(cell, upperPlayerColor) )
                        trapped = false;
                }
            }
        }

        return trapped;
    }

    // создание массива из клеток поля
    private ArrayList<Cell> setFieldCells(){

        int color = LIGHT;
        ArrayList<Cell> cells = new ArrayList<>();
        Point cellStartPoint = new Point(padding.x + hellHeight, padding.y + hellHeight);
        for (int i = 0; i < 100; i++) {
            Cell cell = new Cell(new Point(cellStartPoint.x, cellStartPoint.y), fieldSize/12, color, white);

            // определяем соседей для возможного хода
            if (i > 9) { //верхних ближних
                if (i%10 != 0) {
                    cell.setCloseTopLeft(cells.get(i-11)); // левый ближний
                    cells.get(i-11).setCloseBottomRight(cell);
                }
                if (i%10 != 9){
                    cell.setCloseTopRight(cells.get(i-9)); //правый ближний
                    cells.get(i-9).setCloseBottomLeft(cell);
                }
            }

            if (i > 19) { //верхних дальних
                if (i%10 < 8 ) {  // верхних правых
                    cell.setFarTopRight(cells.get(i-18));
                    cells.get(i-18).setFarBottomLeft(cell);
                }
                if (i%10 > 1 ) {  // верхних левых
                    cell.setFarTopLeft(cells.get(i-22));
                    cells.get(i-22).setFarBottomRight(cell);
                }
            }
            cells.add(cell);

            if (cells.size() % 10 == 0) {
                cellStartPoint.x -= fieldSize/12*9;
                cellStartPoint.y += fieldSize/12;
            }
            else{
                cellStartPoint.x += fieldSize/12;
                color = (color == LIGHT) ? DARK : LIGHT;
            }
        }
        return cells;
    }

    // начальное построение шашек
    private void setCheckers() {

        for (int i = 0; i < 40; i++) {
            //расставляем сверху
            if (cells.get(i).getBackgroundColor() == DARK) {
                cells.get(i).setCondition(Cell.CellCondition.CONTAINS_CHECKER);
                cells.get(i).setCheckerColor(upperPlayerColor);


            }

            //расставляем снизу
            if (cells.get(cells.size() - 1 - i).getBackgroundColor() == DARK) {
                cells.get(cells.size() - 1 - i).setCondition(Cell.CellCondition.CONTAINS_CHECKER);
                cells.get(cells.size() - 1 - i).setCheckerColor(bottomPlayerColor);
            }
        }

    }


    // проверяем, должна ли шашка стать дамкой
    private boolean checkerBecomeCrown(Point dropCheckerPoint) {
        for (int i = 0; i < 10; i++) {
            if (cells.get(i).contains(dropCheckerPoint) && turnSide.equals(TurnSide.BOTTOM)){
                Log.i("Checker", "Become Crown");
                return true;
            }
            if (cells.get(cells.size()-1 - i ).contains(dropCheckerPoint) && turnSide.equals(TurnSide.TOP)) {
                Log.i("Checker", "Become Crown");
                return true;
            }
        }

        return false;
    }


    public void placeChecker(Point riseCheckerPoint, Point dropCheckerPoint) {
        if (cellIsSuitableForPut( dropCheckerPoint, riseCheckerPoint)) {

            setCheckerToCell(dropCheckerPoint, riseCheckerPoint);
            setCellIsEmpty(riseCheckerPoint);
            setCellCheckerColor(dropCheckerPoint);
            if ( !(someoneKilledJustNow && possibleKillOneMore(dropCheckerPoint)) ) changeTurn();

        }
        else {
            setCheckerToCell(riseCheckerPoint, riseCheckerPoint);
        }
        if ( gameFinished() ) {
            Log.i("Game", "IsFinashed");

        }
        someoneKilledJustNow = false;
    }

    // если шашку в клетке можно взять
    public boolean cellSuitableForGrab(Point point) {
        for (Cell cell : cells) {
            if (cell.contains(point) &&
                    ( cell.getCondition().equals(Cell.CellCondition.CONTAINS_CHECKER) ||
                            cell.getCondition().equals(Cell.CellCondition.CONTAINS_CROWN )) &&
                    ((turnColor.equals(TurnColor.WHITE) && cell.getCheckerColor() == white) ||
                            (turnColor.equals(TurnColor.BLACK) && cell.getCheckerColor() == black)))
                return true;

        }
        return  false;
    }

    // проверяем, можно ли походить в соседнюю клетку
    private boolean checkerCanMakeCloseMove(Cell cell, Point riseCheckerPoint) {
        // если ходит верхний
        if (turnSide.equals(TurnSide.TOP)) {
            // ходим близко
            if (cell.isCloseCell(riseCheckerPoint)) {
                if ( (cell.getCloseTopLeft() != null && cell.getCloseTopLeft().contains(riseCheckerPoint) ) ||
                        ( cell.getCloseTopRight() != null && cell.getCloseTopRight().contains(riseCheckerPoint)) )
                    return true;
            }
        }
        else { //ходит нижний
            // ходим близко
            if (cell.isCloseCell(riseCheckerPoint)) {
                if ( (cell.getCloseBottomLeft() != null && cell.getCloseBottomLeft().contains(riseCheckerPoint) ) ||
                        (cell.getCloseBottomRight() != null && cell.getCloseBottomRight().contains(riseCheckerPoint)) )
                    return true;
            }
        }
        return false;
    }

    // проверка на возможность срубить кого-нибудь
    private boolean checkerCanKillSomeone(Cell cell, Point riseCheckerPoint) {

        // рубим вправо
        if (cell.getFarTopLeft() != null && cell.getFarTopLeft().contains(riseCheckerPoint) &&
                cell.getCloseTopLeft() != null && !cell.getCloseTopLeft().getCondition().equals(Cell.CellCondition.EMPTY) &&
                cell.getCloseTopLeft().getCheckerColor() !=  cell.getFarTopLeft().getCheckerColor()) {
            killChecker(cell.getCloseTopLeft());
            //someoneKilledJustNow = true;
            Log.i("kill", "rule 1");
            return true;
        }
        // рубим влево
        if (cell.getFarTopRight() != null && cell.getFarTopRight().contains(riseCheckerPoint) &&
                cell.getCloseTopRight() != null && !cell.getCloseTopRight().getCondition().equals(Cell.CellCondition.EMPTY) &&
                cell.getCloseTopRight().getCheckerColor() != cell.getFarTopRight().getCheckerColor()) {
            killChecker(cell.getCloseTopRight());
            //someoneKilledJustNow = true;
            Log.i("kill", "rule 2");
            return true;
        }
        // рубим вправо
        if (cell.getFarBottomLeft() != null && cell.getFarBottomLeft().contains(riseCheckerPoint) &&
                cell.getCloseBottomLeft() != null && !cell.getCloseBottomLeft().getCondition().equals(Cell.CellCondition.EMPTY) &&
                cell.getCloseBottomLeft().getCheckerColor() != cell.getFarBottomLeft().getCheckerColor()){
            killChecker(cell.getCloseBottomLeft());
            //someoneKilledJustNow = true;
            Log.i("kill", "rule 3");
            return true;
        }
        //рубим влево
        if ( cell.getFarBottomRight() != null && cell.getFarBottomRight().contains(riseCheckerPoint) &&
                cell.getCloseBottomRight() != null && !cell.getCloseBottomRight().getCondition().equals(Cell.CellCondition.EMPTY) &&
                cell.getCloseBottomRight().getCheckerColor() != cell.getFarBottomRight().getCheckerColor()) {
            killChecker(cell.getCloseBottomRight());
            //someoneKilledJustNow = true;
            Log.i("kill", "rule 4");
            return true;
        }

        return false;
    }

    // проверяем, может ли дамка походить в указанную ячейку
    private boolean crownCanMakeMove(Cell cell, Point riseCheckerPoint) {
        Log.i("Crown Move", "Check Crown");

        if (checkTopLeftDirectionForCrown(cell, riseCheckerPoint)) {
            Log.i("CrownMove", "Top-Left");
            return true;
        }
        if (checkTopRightDirectionForCrown(cell, riseCheckerPoint)) {
            Log.i("CrownMove", "Top-Right");
            return true;
        }
        if (checkBottomLeftDirectionForCrown(cell, riseCheckerPoint)) {
            Log.i("CrownMove", "Bottom-Left");
            return true;
        }
        if (checkBottomRightDirectionForCrown(cell, riseCheckerPoint)) {
            Log.i("CrownMove", "Bottom-Right");
            return true;
        }

        return false;
    }

    // проверка хода дамки вверх-влево
    private boolean checkBottomRightDirectionForCrown(Cell cell, Point riseCheckerPoint) {
        // кол-во шашек на пути
        int numberOfCheckersInWay = 0;
        boolean containsRiseCheckerPoint = false;
        // шашка, которую срубят
        Cell victim = new Cell(new Point(0,0),0,0, white);
        Cell tmpCell = cell;

        while (!tmpCell.contains(riseCheckerPoint)) {

            tmpCell = tmpCell.getCloseBottomRight();
            if (tmpCell == null) break;

            if ( !tmpCell.getCondition().equals(Cell.CellCondition.EMPTY) && !tmpCell.contains(riseCheckerPoint)) {
                numberOfCheckersInWay += 1;
                victim = tmpCell;
            }
            if (tmpCell.contains(riseCheckerPoint)) containsRiseCheckerPoint = true;
        }
        if (containsRiseCheckerPoint) {
            if (numberOfCheckersInWay == 0) return true;
            if (numberOfCheckersInWay == 1) {
                killChecker(victim);
                return true;
            }
        }

        return false;
    }

    // проверка хода дамки вверх-вправо
    private boolean checkBottomLeftDirectionForCrown(Cell cell, Point riseCheckerPoint) {
        // кол-во шашек на пути
        int numberOfCheckersInWay = 0;
        boolean containsRiseCheckerPoint = false;
        // шашка, которую срубят
        Cell victim = new Cell(new Point(0,0),0,0, white);
        Cell tmpCell = cell;

        while (!tmpCell.contains(riseCheckerPoint)) {

            tmpCell = tmpCell.getCloseBottomLeft();
            if (tmpCell == null) break;

            if ( !tmpCell.getCondition().equals(Cell.CellCondition.EMPTY) && !tmpCell.contains(riseCheckerPoint)) {
                numberOfCheckersInWay += 1;
                victim = tmpCell;
            }
            if (tmpCell.contains(riseCheckerPoint)) containsRiseCheckerPoint = true;
        }
        if (containsRiseCheckerPoint) {
            if (numberOfCheckersInWay == 0) return true;
            if (numberOfCheckersInWay == 1) {
                killChecker(victim);
                return true;
            }
        }

        return false;
    }

    // проверка хода дамки вниз-влево
    private boolean checkTopRightDirectionForCrown(Cell cell, Point riseCheckerPoint) {
        // кол-во шашек на пути
        int numberOfCheckersInWay = 0;
        boolean containsRiseCheckerPoint = false;
        // шашка, которую срубят
        Cell victim = new Cell(new Point(0,0),0,0, white);
        Cell tmpCell = cell;

        while (!tmpCell.contains(riseCheckerPoint)) {

            tmpCell = tmpCell.getCloseTopRight();
            if (tmpCell == null) break;

            if ( !tmpCell.getCondition().equals(Cell.CellCondition.EMPTY) && !tmpCell.contains(riseCheckerPoint)) {
                numberOfCheckersInWay += 1;
                victim = tmpCell;
            }
            if (tmpCell.contains(riseCheckerPoint)) containsRiseCheckerPoint = true;
        }
        if (containsRiseCheckerPoint) {
            if (numberOfCheckersInWay == 0) return true;
            if (numberOfCheckersInWay == 1) {
                killChecker(victim);
                return true;
            }
        }

        return false;
    }

    // проверка хода дамки вниз-вправо направлении
    private boolean checkTopLeftDirectionForCrown(Cell cell, Point riseCheckerPoint) {
        // кол-во шашек на пути
        int numberOfCheckersInWay = 0;
        boolean containsRiseCheckerPoint = false;
        // шашка, которую срубят
        Cell victim = new Cell(new Point(0,0),0,0, white);
        Cell tmpCell = cell;

        while (!tmpCell.contains(riseCheckerPoint)) {

            tmpCell = tmpCell.getCloseTopLeft();
            if (tmpCell == null) break;

            if (!tmpCell.getCondition().equals(Cell.CellCondition.EMPTY) && !tmpCell.contains(riseCheckerPoint)) {
                numberOfCheckersInWay += 1;
                victim = tmpCell;
            }
            if (tmpCell.contains(riseCheckerPoint)) containsRiseCheckerPoint = true;

        }
        if (containsRiseCheckerPoint) {
            if (numberOfCheckersInWay == 0) return true;
            if (numberOfCheckersInWay == 1) {
                killChecker(victim);
                return true;
            }
        }

        return false;
    }

    private boolean grabbedCheckerIsCrown(Point riseCheckerPoint) {
        for (Cell cell :cells) {
            if (cell.contains(riseCheckerPoint) && cell.getCondition().equals(Cell.CellCondition.CONTAINS_GRABBED_CROWN)) return true;
        }
        return false;
    }

    // проверяем, ожно ли поставить шашку в данную ячейку
    private boolean cellIsSuitableForPut(Point dropCheckerPoint, Point riseCheckerPoint) {
        for (Cell cell: cells){

            if (cell.contains(dropCheckerPoint) && cell.getCondition().equals(Cell.CellCondition.EMPTY)){
                if (grabbedCheckerIsCrown(riseCheckerPoint)) {
                    //ходим дамкой
                    if (crownCanMakeMove(cell, riseCheckerPoint)) return true;
                } else {
                    //ходим
                    if (checkerCanMakeCloseMove(cell, riseCheckerPoint)) return true;
                    // рубим
                    if (cell.isFarCell(riseCheckerPoint)) {
                        if (checkerCanKillSomeone(cell, riseCheckerPoint)) return true;
                    }

                }

            }

        }
        return false;
    }

    //прячем взятую шашку
    public void hideChecker(Point point) {
        for (Cell cell: cells) {
            if (cell.contains(point)) {
                if (cell.getCondition().equals(Cell.CellCondition.CONTAINS_CROWN))
                    cell.setCondition(Cell.CellCondition.CONTAINS_GRABBED_CROWN);
                else cell.setCondition(Cell.CellCondition.CONTAINS_GRABBED_CHECKER);
            }
        }
    }

    private void killChecker (Cell cell) {
        if (cell.getCheckerColor() == white){
            whiteKill = true;
            blackKill = false;
            numbOfWiteDead ++;
            Log.i("Killed", "Wite");
        }
        else {
            Log.i("Killed", "Black");
            blackKill = true;
            whiteKill = false;
            numbOfBlackDead++;
        }
        someoneKilledJustNow = true;
        cell.setCondition(Cell.CellCondition.EMPTY);
    }

    // если шашка, которую ставим - дамка
    private boolean placedCheckerIsCrown(Point riseCheckerPoint) {
        for (Cell cell :cells) {
            if (cell.contains(riseCheckerPoint) &&
                    cell.getCondition().equals(Cell.CellCondition.CONTAINS_GRABBED_CROWN)){
                Log.i("Placed Checker", "Is Crown");
                return true;
            }
        }
        return false;
    }

    // теперь ячейка содержит шашку
    private void setCheckerToCell(Point dropCheckerPoint, Point riseCheckerPoint) {
        for (Cell cell:cells) {
            if (cell.contains(dropCheckerPoint)){
                if (checkerBecomeCrown(dropCheckerPoint)) cell.setCondition(Cell.CellCondition.CONTAINS_CROWN);
                else if (placedCheckerIsCrown(riseCheckerPoint)) cell.setCondition(Cell.CellCondition.CONTAINS_CROWN);
                else cell.setCondition(Cell.CellCondition.CONTAINS_CHECKER);
            }
        }
    }

    // делаем клетку пустой
    private void setCellIsEmpty(Point point) {
        for (Cell cell: cells) {
            if (cell.contains(point)) cell.setCondition(Cell.CellCondition.EMPTY);
        }
    }

    // устанавливает цвет шашки в ячейке
    private void setCellCheckerColor(Point point) {
        for (Cell cell : cells) {
            if (cell.contains(point)) cell.setCheckerColor(turnColor.equals(TurnColor.WHITE) ? white : black);
        }
    }


    // может ли шашка совершить рубку
    private boolean checkerPossibleKillSomeone(Cell cell) {

        //моно порубить вверх-лево
        if (cell.getFarTopLeft() != null && cell.getFarTopLeft().getCondition().equals(Cell.CellCondition.EMPTY) &&
                cell.getCloseTopLeft() != null &&
                (cell.getCloseTopLeft().getCondition().equals(Cell.CellCondition.CONTAINS_CHECKER) ||
                        cell.getCloseTopLeft().getCondition().equals(Cell.CellCondition.CONTAINS_CROWN) )&&
                cell.getCloseTopLeft().getCheckerColor() != cell.getCheckerColor()) {
            Log.i("posible to kill", "rule 1");
            return true;
        }
        // верх-право
        if (cell.getFarTopRight() != null && cell.getFarTopRight().getCondition().equals(Cell.CellCondition.EMPTY) &&
                cell.getCloseTopRight() != null &&
                (cell.getCloseTopRight().getCondition().equals(Cell.CellCondition.CONTAINS_CHECKER) ||
                        cell.getCloseTopRight().getCondition().equals(Cell.CellCondition.CONTAINS_CROWN) )&&
                cell.getCloseTopRight().getCheckerColor() != cell.getCheckerColor()) {
            Log.i("posible to kill", "rule 2");
            return true;
        }
        // вниз-лево
        if (cell.getFarBottomLeft() != null && cell.getFarBottomLeft().getCondition().equals(Cell.CellCondition.EMPTY) &&
                cell.getCloseBottomLeft() != null &&
                ( cell.getCloseBottomLeft().getCondition().equals(Cell.CellCondition.CONTAINS_CHECKER) ||
                        cell.getCloseBottomLeft().getCondition().equals(Cell.CellCondition.CONTAINS_CROWN) )&&
                cell.getCloseBottomLeft().getCheckerColor() != cell.getCheckerColor()) {
            Log.i("posible to kill", "rule 3");
            return true;
        }
        if (cell.getFarBottomRight() != null && cell.getFarBottomRight().getCondition().equals(Cell.CellCondition.EMPTY) &&
                cell.getCloseBottomRight() != null &&
                ( cell.getCloseBottomRight().getCondition().equals(Cell.CellCondition.CONTAINS_CHECKER) ||
                        cell.getCloseBottomRight().getCondition().equals(Cell.CellCondition.CONTAINS_CROWN) )&&
                cell.getCloseBottomRight().getCheckerColor() != cell.getCheckerColor()) {
            Log.i("posible to kill", "rule 4");
            return true;
        }

        return false;
    }

    private boolean crownPossibleKillSomeone(Cell cell) {

        // top-left
        Cell tmpCell = cell;
        while (tmpCell.getCloseTopLeft() != null) {
            tmpCell = tmpCell.getCloseTopLeft();
            if (!tmpCell.getCondition().equals(Cell.CellCondition.EMPTY) &&
                    ((turnColor == TurnColor.BLACK && tmpCell.getCheckerColor() == white) ||
                            (turnColor == TurnColor.WHITE && tmpCell.getCheckerColor() == black))) break;
        }
        if (tmpCell.getCloseTopLeft() != null && tmpCell.getCloseTopLeft().getCondition().equals(Cell.CellCondition.EMPTY)) {
            Log.i("Crown Possible To Kilee", "Rule 1");
            return true;
        }

        //top-right
        tmpCell = cell;
        while (tmpCell.getCloseTopRight() != null) {
            tmpCell = tmpCell.getCloseTopRight();
            if (!tmpCell.getCondition().equals(Cell.CellCondition.EMPTY) &&
                    ((turnColor == TurnColor.BLACK && tmpCell.getCheckerColor() == white) ||
                            (turnColor == TurnColor.WHITE && tmpCell.getCheckerColor() == black))) break;
        }
        if (tmpCell.getCloseTopRight() != null && tmpCell.getCloseTopRight().getCondition().equals(Cell.CellCondition.EMPTY)) {
            Log.i("Crown Possible To Kilee", "Rule 2");
            return true;
        }

        //bottom-right
        tmpCell = cell;
        while (tmpCell.getCloseBottomRight() != null){
            tmpCell = tmpCell.getCloseBottomRight();
            if (!tmpCell.getCondition().equals(Cell.CellCondition.EMPTY) &&
                    ((turnColor == TurnColor.BLACK && tmpCell.getCheckerColor() == white) ||
                            (turnColor == TurnColor.WHITE && tmpCell.getCheckerColor() == black))) break;
        }
        if (tmpCell.getCloseBottomRight() != null && tmpCell.getCloseBottomRight().getCondition().equals(Cell.CellCondition.EMPTY)) {
            Log.i("Crown Possible To Kilee", "Rule 3");
            return true;
        }

        //bottom-left
        tmpCell = cell;
        while (tmpCell.getCloseBottomLeft() != null) {
            tmpCell = tmpCell.getCloseBottomLeft();
            if (!tmpCell.getCondition().equals(Cell.CellCondition.EMPTY) &&
                    ((turnColor == TurnColor.BLACK && tmpCell.getCheckerColor() == white) ||
                            (turnColor == TurnColor.WHITE && tmpCell.getCheckerColor() == black))) break;
        }
        if (tmpCell.getCloseBottomLeft() != null && tmpCell.getCloseBottomLeft().getCondition().equals(Cell.CellCondition.EMPTY)) {
            Log.i("Crown Possible To Kilee", "Rule 4");
            return true;
        }


        return false;
    }

    // можно ли продолжить рубить
    private boolean possibleKillOneMore(Point point) {
        for (Cell cell: cells) {
            if (cell.contains(point)) {

                if (cell.getCondition().equals(Cell.CellCondition.CONTAINS_CHECKER) &&
                        checkerPossibleKillSomeone(cell)){
                    return true;
                }
                if (cell.getCondition().equals(Cell.CellCondition.CONTAINS_CROWN) &&
                        crownPossibleKillSomeone(cell)){
                    return true;
                }

            }
        }
        return false;
    }

    // меняем ходящего игрока
    private void changeTurn() {
        turnColor = (turnColor.equals(TurnColor.BLACK)) ? TurnColor.WHITE : TurnColor.BLACK;
        turnSide = (turnSide.equals(TurnSide.BOTTOM) ? TurnSide.TOP : TurnSide.BOTTOM);
    }

    public void recoverParams(Bundle savedInstanceState) {
        cells = setFieldCells();

        // расставляем белые шашки
        ArrayList<Integer> whiteCheckers = (ArrayList<Integer>) savedInstanceState.get("whiteCheckers");
        for (int i = 0; i < whiteCheckers.size(); i++) {
            cells.get(whiteCheckers.get(i)).setCondition(Cell.CellCondition.CONTAINS_CHECKER);
            cells.get(whiteCheckers.get(i)).setCheckerColor(white);
        }

        // расставляем чёрные шашки
        ArrayList<Integer> blackCheckers = (ArrayList<Integer>) savedInstanceState.get("blackCheckers");
        for (int i = 0; i < blackCheckers.size(); i++) {
            cells.get(blackCheckers.get(i)).setCondition(Cell.CellCondition.CONTAINS_CHECKER);
            cells.get(blackCheckers.get(i)).setCheckerColor(black);
        }

        // расставляем белые дамки
        ArrayList<Integer> whiteCrowns = (ArrayList<Integer>) savedInstanceState.get("whiteCrowns");
        for (int i=0; i<whiteCrowns.size(); i++){
            cells.get(whiteCrowns.get(i)).setCondition(Cell.CellCondition.CONTAINS_CROWN);
            cells.get(whiteCrowns.get(i)).setCheckerColor(white);
        }

        // расставляем чёрные дамки
        ArrayList<Integer> blackCrowns = (ArrayList<Integer>) savedInstanceState.get("blackCrowns");
        for (int i=0; i<blackCrowns.size(); i++){
            cells.get(blackCrowns.get(i)).setCondition(Cell.CellCondition.CONTAINS_CROWN);
            cells.get(blackCrowns.get(i)).setCheckerColor(black);
        }

        numbOfWiteDead = (int) savedInstanceState.get("numbOfWiteDead");
        numbOfBlackDead = (int) savedInstanceState.get("numbOfBlackDead");

        upperPlayerColor = (int) savedInstanceState.get("upperPlayerColor");
        bottomPlayerColor = (int) savedInstanceState.get("bottomPlayerColor");

        turnColor = savedInstanceState.get("turnColor").equals("WHITE") ? TurnColor.WHITE : TurnColor.BLACK;
        turnSide = savedInstanceState.get("turnSide").equals("TOP") ? TurnSide.TOP : TurnSide.BOTTOM;

        someoneKilledJustNow = savedInstanceState.get("someOneKilled").equals(true);

    }

    public ArrayList<Cell> getCells() {
        return cells;
    }

    public TurnColor getTurnColor() {
        return turnColor;
    }

    public int getUpperPlayerColor() {
        return upperPlayerColor;
    }

    public int getBottomPlayerColor() {
        return bottomPlayerColor;
    }

    public int getNumbOfBlackDead() {
        return numbOfBlackDead;
    }

    public int getNumbOfWiteDead() {
        return numbOfWiteDead;
    }

    public boolean isWhiteKill() { return whiteKill;}

    public boolean isBlackKill() {
        return blackKill;
    }

    // для сохранения при перевороте
    public ArrayList<Integer> getWiteCheckersPlaces(){
        ArrayList<Integer> result = new ArrayList<>();

        for (int i=0; i < cells.size(); i++) {
            if (cells.get(i).getCondition().equals(Cell.CellCondition.CONTAINS_CHECKER) && cells.get(i).getCheckerColor() == white) result.add(i);
        }

        return result;
    }

    public ArrayList<Integer> getWhiteCrownPlaces() {
        ArrayList<Integer> result = new ArrayList<>();

        for (int i = 0; i < cells.size(); i++) {
            if (cells.get(i).getCondition().equals(Cell.CellCondition.CONTAINS_CROWN) && cells.get(i).getCheckerColor() == white) result.add(i);
        }
        return result;
    }

    public ArrayList<Integer> getBlackCrownsPlaces() {
        ArrayList<Integer> result = new ArrayList<>();

        for (int i = 0; i < cells.size(); i++) {
            if (cells.get(i).getCondition().equals(Cell.CellCondition.CONTAINS_CROWN) && cells.get(i).getCheckerColor() == black) result.add(i);
        }
        return result;
    }

    // для сохранения при перевороте
    public ArrayList<Integer> getBlackCheckersPlecas(){
        ArrayList<Integer> result = new ArrayList<>();

        for (int i=0; i < cells.size(); i++) {
            if (cells.get(i).getCondition().equals(Cell.CellCondition.CONTAINS_CHECKER) && cells.get(i).getCheckerColor() == black) result.add(i);
        }

        return result;
    }

    public TurnSide getTurnSide() {
        return turnSide;
    }

    public boolean isSomeoneKilledJustNow() {
        return someoneKilledJustNow;
    }
}
