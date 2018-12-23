package ru.alexandra.forum.objects;

import java.io.Serializable;

public class User implements Serializable {

    private static final String TAG = "LOGGER";

    private long id;
    //TODO тут будет аватар
    private String name;
    private String pass;
    private String status;
    private int themes;
    private int answers;
    //TODO здесь будет дата создания


    public User(String name, String pass) {
        this.name = name;
        this.pass = pass;
        this.status = "user";
        this.themes = 0;
        this.answers = 0;
    }

    public User(String name, String pass, String status, int themes, int answers) {
        this.name = name;
        this.pass = pass;
        this.status = status;
        this.themes = themes;
        this.answers = answers;
    }

    public User(long id, String name, String pass, String status, int themes, int answers) {
        this.id = id;
        this.name = name;
        this.pass = pass;
        this.status = status;
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

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getThemes() {
        return themes;
    }

    public void setThemes(int themes) {
        this.themes = themes;
    }

    public int getAnswers() {
        return answers;
    }

    public void setAnswers(int answers) {
        this.answers = answers;
    }
}
