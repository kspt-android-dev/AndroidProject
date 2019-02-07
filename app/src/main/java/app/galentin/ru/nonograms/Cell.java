package app.galentin.ru.nonograms;

import java.io.Serializable;

public class Cell implements Serializable {
    private String num = " ";
    private String color = "#ffffff";
    private State state = State.Color;

    enum State {
        NumColor, Color, Boundaries, ActiveColor;
    }

    boolean TapCell(String activeColor, String activeText) {
        if (state == State.Color) {
            if (color.equals(activeColor)) color = "#ffffff";
            else color = activeColor;
            if (num.equals(activeText)) num = " ";
            else num = activeText;
            return true;
        } else return false;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getNum() {
        return num;
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
