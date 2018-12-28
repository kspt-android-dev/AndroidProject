package ru.alexandra.forum.objects;

import java.io.Serializable;

public class Theme implements Serializable {

    private long id;
    private long userId;
    private String header;
    private String text;
    private int answers;

    private User user;

    public Theme(long userId, String header, String text, int answers, User user) {
        this.userId = userId;
        this.header = header;
        this.text = text;
        this.answers = answers;
        this.user = user;
    }

    public Theme(long id, long userId, String header, String text, int answers, User user) {
        this.id = id;
        this.userId = userId;
        this.header = header;
        this.text = text;
        this.answers = answers;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public String getHeader() {
        return header;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getAnswers() {
        return answers;
    }

    public User getUser() {
        return user;
    }
}
