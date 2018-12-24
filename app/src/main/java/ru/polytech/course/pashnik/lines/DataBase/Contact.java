package ru.polytech.course.pashnik.lines.DataBase;

public class Contact {
    private String name;
    private int score;
    private int id;

    public Contact() {
    }


    public Contact(int id) {
        this.id = id;
    }

    public Contact(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {

        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {

        return id;
    }

    public int getScore() {
        return score;
    }
}
