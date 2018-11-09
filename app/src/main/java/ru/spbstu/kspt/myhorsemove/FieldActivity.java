package ru.spbstu.kspt.myhorsemove;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FieldActivity extends Activity {

    private FieldView fieldView = null;
    private int max = 1; //считанный из файла рекорд

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadRes();
        setContentView(R.layout.activity_field);
        fieldView = new FieldView(this); //передаю текущую активити вьюеру
        setContentView(fieldView);
    }

    public void setFieldView(FieldView fieldView) {
        this.fieldView = fieldView;
    }

    public int getMax() {
        return max;
    }

    private void loadRes() {
        try {
            InputStream is = openFileInput(MainActivity.fileName); //открытие файла с рекордом
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader in = new BufferedReader(isr);
            String txt = in.readLine();
            max = Integer.parseInt(txt); //перевод строки с рекордом в число
            in.close();
        } catch (Exception e) { //если нет файла, если в файле не число и т.п.
            saveRes(1); //создание файла и запись туда 1
        }
    }

    void saveRes(int record) {
        try {
            FileOutputStream out = openFileOutput(MainActivity.fileName, Context.MODE_PRIVATE);
            out.write(Integer.toString(record).getBytes());
            out.close();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    boolean getScreenOrientation() { //true - вертикально, false - горизонтально
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }
}
