package ru.polytech.course.pashnik.lines.Core;

import android.graphics.Color;

public enum ColorType {
    BROWN, LAGOON, RED, YELLOW, GREEN, BLUE, PURPLE;

    private static final int EXIT_CODE = -1;

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
        return EXIT_CODE;
    }

    public static ColorType getColorType(int index) {
        return ColorType.values()[index];
    }

    public static int getIndex(ColorType colorType) {
        if (colorType == null) return EXIT_CODE;
        return colorType.ordinal();
    }

}
