package name.mizunotlt.eruditkurs;

import android.graphics.Path;
import android.graphics.Point;

public class CellForLetter {

    private Point startPoint;
    private Path path;
    private int numCell;
    private boolean isSelect = false;
    private final int SIZE = 60;

    CellForLetter(Point startPoint, int numCell){
        this.startPoint = startPoint;
        this.numCell = numCell;
        this.setPath();
    }

    private void setPath(){
        path = new Path();
        path.moveTo(startPoint.x, startPoint.y);
        path.rLineTo(SIZE,0);
        path.rLineTo(0, SIZE);
        path.rLineTo(-SIZE,0);
        path.rLineTo(0, -SIZE);
        path.close();
    }
    public Path getPath(){
        return path;
    }

    public Point getStartPoint() {
        return startPoint;
    }
    public int getNumCell(){
        return numCell;
    }
    public boolean isSelect(){
        return isSelect;
    }

    public void changeSelect(){
        if (isSelect){
            isSelect = false;
        }
        else
            isSelect = true;
    }
}
