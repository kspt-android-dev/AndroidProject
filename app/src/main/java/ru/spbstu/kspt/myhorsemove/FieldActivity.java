package ru.spbstu.kspt.myhorsemove;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FieldActivity extends Activity  implements View.OnClickListener{

    private FieldView fieldView = null;
    private Data data = new Data();
    private int max = 1; //считанный из файла рекорд
    TextView textView1, textView2;

    private Intent intentMus=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadRes();
        setContentView(R.layout.activity_field);
        fieldView = new FieldView(this); //передаю текущую активити вьюеру
        fieldView = (FieldView)findViewById(R.id.FV2);
        Button buttonStart = (Button) findViewById(R.id.button2);
        buttonStart.setOnClickListener(this);
        textView1 = (TextView)findViewById(R.id.textView1);
        textView2 = (TextView)findViewById(R.id.textView2);
        data = (Data)getLastNonConfigurationInstance();
        if (data != null)
            fieldView.setData(data);

        intentMus = new Intent(FieldActivity.this, MyService.class);
        startService( intentMus);
    }

    @Override
    public void onBackPressed() {
        stopService(intentMus);
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        MyService.player.pause();
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if( MyService.player!=null)
        MyService.player.start();
        else
        startService(intentMus);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(intentMus);
    }

    @Override
    public void onClick(View v) {
        fieldView.newGame();
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

    @Override
    public Object onRetainNonConfigurationInstance() {
        if (fieldView != null)
            return fieldView.getData();
        return null;
    }

    public Data getData() {
        return data;
    }

}
