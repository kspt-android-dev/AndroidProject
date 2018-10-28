package ru.gdcn.alex.whattodo.customviews;

import android.util.Log;

import ru.gdcn.alex.whattodo.utilities.TextFormer;

public class Card {

    private static final String TAG = "ToDO_Logger";
    private static final String className = "Card";

    private String header;
    private int icon;

    public Card(String header, int icon) {
        Log.e(TAG, TextFormer.getStartText(className) + "Создание карточки...");
        this.header = header;
        this.icon = icon;
        Log.e(TAG, TextFormer.getStartText(className) + "Карточка создана!");
    }

    public String getHeader() {
        return header;
    }

    public int getIcon() {
        return icon;
    }
}
