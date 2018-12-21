package ru.spbstu.kspt.myhorsemove;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonStart;
    private Button buttonAbout;
    private Button buttonReset;
    private Button buttonExit;
    private Intent intent = null; //второе окно с доской
    static String fileName = "result"; //имя файла для сохн=ранения рекорда

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonStart = (Button)findViewById(R.id.buttonStart);
        buttonAbout = (Button)findViewById(R.id.buttonAbout);
        buttonReset = (Button)findViewById(R.id.buttonReset);
        buttonExit = (Button)findViewById(R.id.buttonExit);
        buttonStart.setOnClickListener(this);
        buttonAbout.setOnClickListener(this);
        buttonReset.setOnClickListener(this);
        buttonExit.setOnClickListener(this);
        intent = new Intent(MainActivity.this, FieldActivity.class); //второе окно с доской
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonStart: start(); break;
            case R.id.buttonAbout: about(); break;
            case R.id.buttonReset: reset(); break;
            case R.id.buttonExit: exit(); break;
        }
    }

    void start() {
        startActivity(intent);

    }

    void about() {
        String str = getString(R.string.aboutMes);
        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        dlg.setTitle(getString(R.string.rulesMes))
                .setMessage(str)
                .setPositiveButton(R.string.understoodMes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    //Андроид сохраняет свои файлы в каталоге /data/data/<название пакета>
    void reset() {
        try {
            FileOutputStream out = openFileOutput(fileName, Context.MODE_PRIVATE);//если не было файла - создаст
            out.write(Integer.toString(1).getBytes()); //сброс лучшего результата, хранящегося в файле "result", до 1
            out.close();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        Toast dlg = Toast.makeText(this, R.string.menuRecordMes, Toast.LENGTH_SHORT);
        dlg.setGravity(Gravity.CENTER, 0,0);
        dlg.show();
    }

    void exit() {
        finish();
    }
}
