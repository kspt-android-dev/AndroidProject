package ru.alexandra.forum.objects;

import java.io.Serializable;

public class User implements Serializable {

    private static final String TAG = "LOGGER";

    private long id;
    private String name;
    private String pass;
    private int themes;
    private int answers;

    public User(String name, String pass, int themes, int answers) {
        this.name = name;
        this.pass = pass;
        this.themes = themes;
        this.answers = answers;
    }

    public User(long id, String name, String pass, int themes, int answers) {
        this.id = id;
        this.name = name;
        this.pass = pass;
        this.themes = themes;
        this.answers = answers;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public int getThemes() {
        return themes;
    }

    public int getAnswers() {
        return answers;
    }
}
