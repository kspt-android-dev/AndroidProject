package ru.alexandra.forum.objects;

public class Answer {

    private long id;
    private long themeId;
    private long userId;
    private String text;

    private User user;

    public Answer(long themeId, long userId, String text, User user) {
        this.themeId = themeId;
        this.userId = userId;
        this.text = text;
        this.user = user;
    }

    public Answer(long id, long themeId, long userId, String text, User user) {
        this.id = id;
        this.themeId = themeId;
        this.userId = userId;
        this.text = text;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public long getThemeId() {
        return themeId;
    }

    public long getUserId() {
        return userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }
}
