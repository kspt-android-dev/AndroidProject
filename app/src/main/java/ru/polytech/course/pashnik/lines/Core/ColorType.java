package ru.polytech.course.pashnik.lines.Core;

import android.graphics.Color;

public enum ColorType {
    BROWN, LAGOON, RED, YELLOW, GREEN, BLUE, PURPLE;

    public static int chooseColor(ColorType color) {
        switch (color) {
            case RED:
                return Color.parseColor("#FF0000");
            case BLUE:
                return Color.parseColor("#0000FF");
            case BROWN:
                return Color.parseColor("#A0522D");
            case GREEN:
                return Color.parseColor("#00FF00");
            case LAGOON:
                return Color.parseColor("#87CEEB");
            case PURPLE:
                return Color.parseColor("#800080");
            case YELLOW:
                return Color.parseColor("#FFFF00");
        }
        return -1;
    }

    public static ColorType getColorType(int index) {
        return ColorType.values()[index];
    }

}
