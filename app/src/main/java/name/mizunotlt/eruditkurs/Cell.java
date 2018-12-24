package name.mizunotlt.eruditkurs;

import android.graphics.Path;
import android.graphics.Point;

public class Cell {

    private Point startPoint;
    private Path path;
    private char letter = ' ';
    private boolean useErly = false;
    private StateCellEnum.State state;
    private int numCell;
    private int size;

    Cell(Point startPoint, int numCell,int size){
        this.startPoint = startPoint;
        this.numCell = numCell;
        this.size = size;
        this.setPath();
    }

    public void setState(StateCellEnum.State state){
        this.state = state;
    }

    public StateCellEnum.State getState(){
        return state;
    }

    public boolean getIsLetter(){
        if (letter == ' ')
            return false;
        else return true;
    }

    private void setPath(){
        path = new Path();
        path.moveTo(startPoint.x, startPoint.y);
        path.rLineTo(size,0);
        path.rLineTo(0, size);
        path.rLineTo(-size,0);
        path.rLineTo(0, -size);
        path.close();
    }

    public Path getPath(){
        return path;
    }
    public void setLetter(char c){
        this.letter = c;
    }
    public char getLetter(){
        return letter;
    }
    public void changeUseErly(){
        useErly = true;
    }
    public boolean getUseErly(){
        return useErly;
    }
    public Point getStartPoint(){
        return startPoint;
    }
    public int getNumCell() {
        return numCell;
    }
}
