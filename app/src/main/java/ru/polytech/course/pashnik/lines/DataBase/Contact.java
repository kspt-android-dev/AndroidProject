package ru.polytech.course.pashnik.lines.DataBase;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return score == contact.score &&
                id == contact.id &&
                Objects.equals(name, contact.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, score, id);
    }
}
