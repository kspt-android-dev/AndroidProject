package ru.alexandra.forum.objects;

import java.io.Serializable;

public class Theme implements Serializable {
    private static final String TAG = "LOGGER";

    private long id;
    private long userId;
    private String header;
    private String text;
    private int answers;
    //TODO здесь будет дата создания
    private String status;

    private User user;

    public Theme(long userId, String header, String text, int answers, String status, User user) {
        this.userId = userId;
        this.header = header;
        this.text = text;
        this.answers = answers;
        this.status = status;
        this.user = user;
    }

    public Theme(long id, long userId, String header, String text, int answers, String status, User user) {
        this.id = id;
        this.userId = userId;
        this.header = header;
        this.text = text;
        this.answers = answers;
        this.status = status;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
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

    public void setAnswers(int answers) {
        this.answers = answers;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
