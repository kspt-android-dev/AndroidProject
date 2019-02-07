package app.galentin.ru.nonograms;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class FieldAdapter extends BaseAdapter {

    private Context context;

    Cell cells[];

    private LayoutInflater inflater;

    public FieldAdapter(Context context, Cell buttons[]){
        this.context = context;
        this.cells = buttons;
    }

    @Override
    public int getCount() {
        return cells.length;
    }

    @Override
    public Object getItem(int position) {
        return cells[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridView = convertView;

        if(convertView == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = inflater.inflate(R.layout.cell, null);

        }
        TextView textView= gridView.findViewById(R.id.text_view);
        textView.setBackgroundColor(Color.parseColor(cells[position].getColor()));
        textView.setText(cells[position].getNum());
        return gridView;
    }
}
