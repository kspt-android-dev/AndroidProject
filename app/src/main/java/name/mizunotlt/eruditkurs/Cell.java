package name.mizunotlt.eruditkurs;

import android.graphics.Path;
import android.graphics.Point;

public class Cell {

    private Point startPoint;
    private Path path;
    private boolean x2Word = false;
    private boolean x3Word = false;
    private boolean x2Letter = false;
    private boolean x3Letter = false;
    private boolean startPosition = false;
    private boolean defCell = false;
    private char letter = ' ';
    private boolean useErly = false;
    private boolean state = false;
    private int numCell;
    private int size;

    Cell(Point startPoint, int numCell,int size){
        this.startPoint = startPoint;
        this.numCell = numCell;
        this.size = size;
        this.setPath();
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

    public void setDefCell(){
        this.defCell = true;
        this.startPosition = false;
        this.x3Word = false;
        this.x2Word = false;
        this.x2Letter = false;
        this.x3Letter = false;
    }

    public int typeCell(){

        if (startPosition){
            return 2;
        }
        if (x2Letter){
            return 3;
        }
        if (x3Letter){
            return 4;
        }
        if (x2Word){
            return 5;
        }
        if (x3Word){
            return 6;
        }
        else
            return 1;
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
    public void setTrueX2Word(){
        this.defCell = false;
       this.x2Word = true;
    }

    public void setTrueX3Word(){
        this.defCell = false;
        this.x3Word = true;
    }

    public void setTrueX2Letter(){
        this.defCell = false;
        this.x2Letter = true;
    }

    public void setTrueX3Letter(){
        this.defCell = false;
        this.x3Letter = true;
    }

    public boolean isStartPosition() {
        return startPosition;
    }
    public void setStartPosition() {
        this.defCell = false;
        this.startPosition = true;
    }

    public int getNumCell() {
        return numCell;
    }
}
