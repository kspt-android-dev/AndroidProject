package polytech.vladislava.sudoku;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.Collections;
import java.util.List;

public class RecordsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        List<Record> records = DBConnector.getAllRecords(this);
        Collections.sort(records);
        if (records != null){
            RecordAdapter adapter = new RecordAdapter(this, records);

            ListView lvMain = findViewById(R.id.a_recs_listView);
            lvMain.setAdapter(adapter);
        }
    }
}
