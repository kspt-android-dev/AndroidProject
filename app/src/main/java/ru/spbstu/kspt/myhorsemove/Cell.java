package ru.spbstu.kspt.myhorsemove;

class Cell {

    private boolean black; //false = white
    private int num;
    private boolean horse;

    public void setBlack(boolean black) {
        this.black = black;
    }

    public void setHorse(boolean horse) {
        this.horse = horse;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean isBlack() {
        return black;
    }

    public int getNum() {
        return num;
    }

    public boolean isHorse() {
        return horse;
    }
}
