package ru.alexandra.forum.objects;

public class Answer {
    private static final String TAG = "LOGGER";

    private long id;
    private long themeId;
    private long userId;
    private String text;
    //TODO здесь будет дата создания

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

    public void setThemeId(long theme_id) {
        this.themeId = theme_id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long user_id) {
        this.userId = user_id;
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

    public void setUser(User user) {
        this.user = user;
    }
}
