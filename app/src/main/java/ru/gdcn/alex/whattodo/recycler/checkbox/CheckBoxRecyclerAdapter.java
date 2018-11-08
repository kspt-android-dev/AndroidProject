package ru.gdcn.alex.whattodo.recycler.checkbox;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ru.gdcn.alex.whattodo.CheckBoxLine;
import ru.gdcn.alex.whattodo.R;
import ru.gdcn.alex.whattodo.utilities.TextFormer;

public class CheckBoxRecyclerAdapter extends RecyclerView.Adapter<CheckBoxRecyclerAdapter.LineViewHolder> {

    private static final String TAG = "ToDO_Logger";
    private static final String className = "CheckBoxRecyclerAdapter";

    private List<CheckBoxLine> lines = new ArrayList<>();

    private Context context;

    public CheckBoxRecyclerAdapter(Context context) {
        this.context = context;
    }

    public void addItem(CheckBoxLine checkBoxLine){
        Log.d(TAG, TextFormer.getStartText(className) + "Добавление карточки в список...");
        lines.add(checkBoxLine);
        notifyItemInserted(lines.size() - 1);
        Log.d(TAG, TextFormer.getStartText(className) + "Карточка добавлена!");
    }

    public void addItem(CheckBoxLine checkBoxLine, int position){
        Log.d(TAG, TextFormer.getStartText(className) + "Добавление карточки в список...");
        lines.add(position, checkBoxLine);
        notifyItemInserted(position);
        Log.d(TAG, TextFormer.getStartText(className) + "Карточка добавлена!");
    }

    public void addItems(Collection<CheckBoxLine> checkBoxLines){
        Log.d(TAG, TextFormer.getStartText(className) + "Добавление карточек в список...");
        lines.addAll(checkBoxLines);
        notifyDataSetChanged();
        Log.d(TAG, TextFormer.getStartText(className) + "Карточки добавлены!");
    }

    public CheckBoxLine getItem(int index){
        return lines.get(index);
    }

    public Collection<CheckBoxLine> getItems(){
        return lines;
    }

    public void removeItem(CheckBoxLine checkBoxLine){
        Log.d(TAG, TextFormer.getStartText(className) + "Удаление карточки из списка...");
        int i = lines.indexOf(checkBoxLine);
        lines.remove(checkBoxLine);
        notifyItemRemoved(i);
        Log.d(TAG, TextFormer.getStartText(className) + "Карточка удалена из списка!");
    }

    public void removeItems(){
        Log.d(TAG, TextFormer.getStartText(className) + "Удаление карточек из списка...");
        lines.clear();
        notifyDataSetChanged();
        Log.d(TAG, TextFormer.getStartText(className) + "Карточки удалены!");
    }

    @NonNull
    @Override
    public LineViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.creation_recyclerview_checkbox, viewGroup, false);
        return new LineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LineViewHolder lineViewHolder, int i) {
        lineViewHolder.bind(lines.get(i));
    }

    @Override
    public int getItemCount() {
        return lines.size();
    }

    class LineViewHolder extends RecyclerView.ViewHolder {

        CheckedTextView checkedTextView;

        public LineViewHolder(@NonNull View itemView) {
            super(itemView);
            checkedTextView = itemView.findViewById(R.id.creation_checkbox);
        }

        public void bind(CheckBoxLine checkBoxLine){
            checkedTextView.setText(checkBoxLine.getContent());
            checkedTextView.setChecked(checkBoxLine.getChecked());
        }

    }
}
