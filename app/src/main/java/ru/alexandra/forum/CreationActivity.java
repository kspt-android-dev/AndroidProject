package ru.alexandra.forum;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import ru.alexandra.forum.data.DBConnector;
import ru.alexandra.forum.objects.Theme;
import ru.alexandra.forum.objects.User;

public class CreationActivity extends AppCompatActivity {

    private EditText headerView, textView;
    private User creator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation);

        headerView = findViewById(R.id.creation_header);
        textView = findViewById(R.id.creation_text);

        Toolbar toolbar = findViewById(R.id.creation_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Новая тема");

        creator = (User) getIntent().getSerializableExtra("user");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Опубликовать новую тему?")
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveTheme();
                        CreationActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("Удалить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CreationActivity.super.onBackPressed();
                    }
                })
                .setNeutralButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.show();
    }

    private void saveTheme() {
        Theme theme = new Theme(
                creator.getId(),
                headerView.getText().toString(),
                textView.getText().toString(),
                0,
                "open",
                creator
        );
        DBConnector.insertTheme(CreationActivity.this, theme);
    }
}
