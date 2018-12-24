package ru.gdcn.beastmaster64revelations;

import android.util.Log;

public class GameLogger {

    public static void log(String log){
        Log.d("GDCN", log);
    };

    public static void logError(String log){
        Log.e("GDCN", log);
    };

}
