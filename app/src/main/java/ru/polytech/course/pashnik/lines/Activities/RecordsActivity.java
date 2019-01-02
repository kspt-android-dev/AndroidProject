package ru.polytech.course.pashnik.lines.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import ru.polytech.course.pashnik.lines.DataBase.DataBaseHandler;
import ru.polytech.course.pashnik.lines.R;

public class RecordsActivity extends AppCompatActivity {

    private ListView listView;
    private DataBaseHandler dbHelper;
    private SQLiteDatabase db;
    private Cursor cursor;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        listView = findViewById(R.id.list_records);
        dbHelper = new DataBaseHandler(this);

        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.delete:
                int id = (int) info.id;
                dbHelper.deleteContactById(id);
                cursor.requery();
                showContacts();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        showContacts();
    }

    private void showContacts() {
        db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("select * from " + DataBaseHandler.TABLE_CONTACTS,
                null);
        String[] headers = new String[]{DataBaseHandler.KEY_NAME, DataBaseHandler.KEY_SCORE};
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this,
                R.layout.item,
                cursor, headers, new int[]{R.id.item_name, R.id.item_score}, 0);
        listView.setAdapter(simpleCursorAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        db.close();
        cursor.close();
    }

    public void onClickBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
