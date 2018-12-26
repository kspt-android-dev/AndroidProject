package com.example.danila.minerandroid;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

public class A_RecordsTableActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.records_table_layout);

        List<Record> records = DBConnector.getAllRecords(this);
        if (records != null){
//            RecordAdapter adapter = new RecordAdapter(this, records);

            // настраиваем список
//            ListView lvMain = findViewById(R.id.a_recs_listView);
//            lvMain.setAdapter(adapter);
        }


    }
}
