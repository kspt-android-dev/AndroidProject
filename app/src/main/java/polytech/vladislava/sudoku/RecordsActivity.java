package polytech.vladislava.sudoku;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.Collections;
import java.util.List;

public class RecordsActivity extends AppCompatActivity {

    private DBConnector connector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        connector = new DBConnector(getApplicationContext());

        List<Record> records = connector.getAllRecords();
        if (records!= null) Collections.sort(records);
        if (records != null){
            RecordAdapter adapter = new RecordAdapter(this, records);

            ListView lvMain = findViewById(R.id.a_recs_listView);
            lvMain.setAdapter(adapter);
        }
    }

    @Override
    protected void onDestroy() {
        connector.close();
        super.onDestroy();
    }
}
