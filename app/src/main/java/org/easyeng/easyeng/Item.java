package org.easyeng.easyeng;

public class Item {
    private int id;
    private String title;
    private int level;
    private String text;
    private String tags;
    private String words;

    public Item() {
    }

    public Item(String title, int id, String text, int level, String tags, String words) {
        this.title = title;
        this.id = id;
        this.text = text;
        this.level = level;
        this.tags = tags;
        this.words = words;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public int getLevel() {
        return level;
    }

    public String getTags() {
        return tags;
    }

    public String getWords() {
        return words;
    }
}
