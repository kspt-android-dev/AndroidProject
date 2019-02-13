package lizka.reminder;

import android.content.Context;
import android.content.SharedPreferences;

// Паттерн Singleton (одиночка) - класс имеет только 1 экземпляр и предоставляет
// глобальную точку доступа к нему
public class PreferenceHelper {

    public static final String SPLASH_IS_INVISIBLE = "splash_is_invisible";

    private static PreferenceHelper instance;

    private Context context;

    private SharedPreferences preferences;

    private PreferenceHelper(){

    }

    // получаем объект instance или создаем его
    public static PreferenceHelper getInstance(){
        if (instance == null){
            instance = new PreferenceHelper();
        }
        return instance;
    }

    // с помощью контекста получаем экземпляр класс SharedPreferences
    public void init(Context context){
        this.context = context;
        preferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
    }

    // передаем состояние checkbox для сохранения
    public void putBoolean(String key, boolean value){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    // считываем состояние
    public boolean getBoolean(String key){
        return preferences.getBoolean(key, false);
    }

}











