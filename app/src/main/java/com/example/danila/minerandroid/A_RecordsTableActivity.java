package com.example.danila.minerandroid;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.TextView;
import java.util.List;

public class A_RecordsTableActivity extends Activity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.records_table_layout);

        GridLayout gridRecords = findViewById(R.id.records_layout);
        gridRecords.setColumnCount(2);

        List<Record> records = DBConnector.getAllRecords(this);

        final int TEXT_SIZE = 40;

        int i = 1;
        if (records != null)
            for (Record record : records) {
                TextView nameView = new TextView(this);
                TextView timeView = new TextView(this);


                nameView.setText(i + "\nName\n" + record.getName());
                nameView.setTextSize(TEXT_SIZE);
                nameView.setTextColor(getResources().getColor(R.color.colorAccent));


                timeView.setText("Time\n" + record.getTime() + "\n-------------");
                timeView.setTextSize(TEXT_SIZE * 3 / 4);
                timeView.setTextColor(getResources().getColor(R.color.colorAccent));

                gridRecords.addView(nameView);
                gridRecords.addView(timeView);

                i++;

            }

    }


}

