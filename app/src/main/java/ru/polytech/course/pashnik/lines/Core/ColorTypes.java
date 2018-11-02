package ru.polytech.course.pashnik.lines.Core;

public enum ColorTypes {
    BROWN, LAGOON, RED, YELLOW, GREEN, BLUE, PURPLE;

    public static ColorTypes getColorType(int index) {
        return ColorTypes.values()[index];
    }

}
