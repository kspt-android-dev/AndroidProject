package ru.gdcn.alex.whattodo.utilities;

public class TextFormer {
    public static String getStartText(String className) {
        String space = "";
        for (int i = 0; i < 13 - className.length(); i++) {
            space = space.concat(" ");
        }
        return "[" + className + "]:" + space + " ";
    }
}
