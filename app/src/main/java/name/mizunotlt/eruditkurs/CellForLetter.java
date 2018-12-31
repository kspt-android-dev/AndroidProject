package name.mizunotlt.eruditkurs;

import android.graphics.Path;
import android.graphics.Point;

class CellForLetter {
    private Point startPoint;
    private Path path;
    private int numCell;
    private boolean isSelect = false;
    private int size;

    CellForLetter(Point startPoint, int numCell, int size){
        this.startPoint = startPoint;
        this.numCell = numCell;
        this.size = size - 10;
        this.setPath();
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
        isSelect = !isSelect;
    }
}
