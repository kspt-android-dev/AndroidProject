package ru.gdcn.alex.whattodo.customviews;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;

public class Card {

    private String header;
    private @DrawableRes int icon;

    public Card(String header, @DrawableRes int icon) {
        this.header = header;
        this.icon = icon;
    }

    public String getHeader() {
        return header;
    }

    public @DrawableRes int getIcon() {
        return icon;
    }
}
