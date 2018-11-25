package com.btn.thuynhung.puzzlegame;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class NumbsAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<Integer> arr;

    public NumbsAdapter(@NonNull Context context, int resource, @NonNull List<Integer> objects) {
        super(context, resource, objects);
        this.context = context;
        this.arr = new ArrayList<>(objects);
    }

    @Override
    public void notifyDataSetChanged() {
        arr = DataGame.getDatagame().getArrNumbs();
        super.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_square, null);
        }

        if (arr.size() > 0) {
            Square square = (Square) convertView.findViewById(R.id.text_square);
            square.setTextToItems(arr.get(position));
        }

        return convertView;
    }
}
