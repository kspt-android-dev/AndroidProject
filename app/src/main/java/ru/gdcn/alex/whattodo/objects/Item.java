package ru.gdcn.alex.whattodo.objects;

import java.io.Serializable;

public class Item implements Serializable {

    private static final String TAG = "ToDO_Logger";
    private static final String className = "Item";

    public static final long NEW_ITEM = -13;
    public static final int ADDING_ITEM = -15;
    public static final int DEFAULT_CHECKED = 0;

    private long id;
    private long parentId;
    private int position;
    private String content;
    private int checked;

    public Item(long id, long parentId, int position, String content, int checked) {
        this.id  = id;
        this.parentId = parentId;
        this.position = position;
        this.content = content;
        this.checked = checked;
    }

    public Item(long id, int parentId, int position, String content, int checked) {
        this.id = id;
        this.parentId = parentId;
        this.position = position;
        this.content = content;
        this.checked = checked;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public long getParentId() {
        return parentId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }
}
