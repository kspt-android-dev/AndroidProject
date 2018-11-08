package ru.gdcn.alex.whattodo;

public class CheckBoxLine {

    private static final String TAG = "ToDO_Logger";
    private static final String className = "Card";

    private int id;
    private int parentId;
    private int position;
    private String content;
    private int checked;

    public CheckBoxLine(int parentId, int position, String content, int checked) {
        this.parentId = parentId;
        this.position = position;
        this.content = content;
        this.checked = checked;
    }

    public CheckBoxLine(int id, int parentId, int position, String content, int checked) {
        this.id = id;
        this.parentId = parentId;
        this.position = position;
        this.content = content;
        this.checked = checked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
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

    public boolean getChecked() {
        return checked != 0;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }
}
