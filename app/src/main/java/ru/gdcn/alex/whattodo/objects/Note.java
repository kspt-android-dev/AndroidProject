package ru.gdcn.alex.whattodo.objects;

import java.io.Serializable;
import java.util.Objects;

public class Note implements Serializable {

    private static final String TAG = "ToDO_Logger";
    private static final String className = "Note";

    private long id;
    private int position;
    private String header;
    private String content;
    private String type = "note";
    private Integer date;
    private int fixed = 0;
    private int deleted = 0;

    public Note(int position) {
        this.position = position;
    }

    public Note(int id, int position) {
        this.id = id;
        this.position = position;
    }

    public Note(int id, int position, String header, String content, String type, Integer date, int fixed, int deleted) {
        this.id = id;
        this.position = position;
        this.header = header;
        this.content = content;
        this.type = type;
        this.date = date;
        this.fixed = fixed;
        this.deleted = deleted;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public String getContent() {
        return content;
    }

    public String getType() {
        return type;
    }

    public Integer getDate() {
        return date;
    }

    public int getFixed() {
        return fixed;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public void setFixed(int fixed) {
        this.fixed = fixed;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return id == note.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, header, content, type, date, fixed, deleted);
    }
}
