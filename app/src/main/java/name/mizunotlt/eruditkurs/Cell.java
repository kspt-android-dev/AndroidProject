package name.mizunotlt.eruditkurs;

import android.graphics.Path;
import android.graphics.Point;
enum State{
    DEF_CELL, X2WORD, X2LETTER, X3WORD, X3LETTER,START_POSITION
}
public class Cell {

    private Point startPoint;
    private Path path;
    private char letter = ' ';
    private boolean useEarly = false;
    private State state;
    private int numCell;
    private int size;

    Cell(Point startPoint, int numCell,int size){
        this.startPoint = startPoint;
        this.numCell = numCell;
        this.size = size;
        this.setPath();
    }

    public void setState(State state){
        this.state = state;
    }

    public State getState(){
        return state;
    }

    public boolean getIsLetter(){
        return letter != ' ';
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
    public void changeUseEarly(){
        useEarly = true;
    }
    public boolean getUseEarly(){
        return useEarly;
    }
    public Point getStartPoint(){
        return startPoint;
    }
    public int getNumCell() {
        return numCell;
    }
}
