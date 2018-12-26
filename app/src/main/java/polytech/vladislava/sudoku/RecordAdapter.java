package polytech.vladislava.sudoku;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RecordAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater lInflater;
    List<Record> objects;

    RecordAdapter(Context context, List<Record> Records) {
        ctx = context;
        objects = Records;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return objects.size();
    }


    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.list_item_record, parent, false);
        }

        Record record = getRecord(position);

        ((TextView) view.findViewById(R.id.recordName)).setText(record.getName());
        ((TextView) view.findViewById(R.id.recordTime)).setText(record.getTime());
        ((TextView) view.findViewById(R.id.recordTips)).setText(String.valueOf(record.getTips()));

        return view;
    }


    Record getRecord(int position) {
        return ((Record) getItem(position));
    }

}