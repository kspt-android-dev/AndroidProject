package com.example.myapp;


class Cell {

    private final int kek; // 1 -> nolik, 0 -> krestik

    private int x;  // эти два поля созданы ИСКЛЮЧИТЕЛЬНО для Unit Test
    private int y;  // эти два поля созданы ИСКЛЮЧИТЕЛЬНО для Unit Test

    void setX(int x) {
        this.x = x;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return kek == cell.kek && x == cell.x && y == cell.y;
    }

    void setY(int y) {
        this.y = y;

    }

    public int getX() {

        return x;
    }

    public int getY() {
        return y;
    }

    int getKek() {
        return kek;
    }

    Cell(int x, int y, int kek) { // конструктор создан ИСКЛЮЧИТЕЛЬНО для Unit Test
        this.x = x;
        this.y = y;
        this.kek = kek;
    }

}
