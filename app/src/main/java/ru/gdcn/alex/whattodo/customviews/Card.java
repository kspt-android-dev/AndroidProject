package ru.gdcn.alex.whattodo.customviews;

import android.util.Log;

import java.io.Serializable;

import ru.gdcn.alex.whattodo.R;
import ru.gdcn.alex.whattodo.utilities.TextFormer;

public class Card implements Serializable {

    private static final String TAG = "ToDO_Logger";
    private static final String className = "Card";

    private int id;
    private int parentId;
    private int position;
    private String header;
    private String content;
    private String type;
    private String date;
    private int fixed;
    private int icon;

    public Card(String header, int icon) {
        Log.e(TAG, TextFormer.getStartText(className) + "Создание карточки...");
        this.header = header;
        this.icon = icon;
        Log.e(TAG, TextFormer.getStartText(className) + "Карточка создана!");
    }

    public Card(int id, int parentId, String header, String content, String type, String date, int fixed) {
        this.id = id;
        this.parentId = parentId;
        this.header = header;
        this.content = content;
        if (type == null)
            this.type = "";
        else
            this.type = type;
        this.date = date;
        this.fixed = fixed;
        this.icon = R.drawable.ic_description_black_24dp;
    }

    public Card(int id, int position, int parentId, String header, String content, String type, String date, int fixed) {
        this.id = id;
        this.parentId = parentId;
        this.position = position;
        this.header = header;
        this.content = content;
        if (type == null)
            this.type = "";
        else
            this.type = type;
        this.date = date;
        this.fixed = fixed;
        this.icon = R.drawable.ic_description_black_24dp;
    }

    public int getId() {
        return id;
    }

    public String getHeader() {
        return header;
    }

    public int getIcon() {
        return icon;
    }

    public int getParentId() {
        return parentId;
    }

    public String getContent() {
        return content;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public int isFixed() {
        return fixed;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
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

    public void setDate(String date) {
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
}
