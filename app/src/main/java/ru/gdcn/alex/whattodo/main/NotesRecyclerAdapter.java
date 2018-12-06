package ru.gdcn.alex.whattodo.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import ru.gdcn.alex.whattodo.R;
import ru.gdcn.alex.whattodo.objects.Note;
import ru.gdcn.alex.whattodo.data.DBConnector;
import ru.gdcn.alex.whattodo.recycler.SwipeDragHelperCallback;
import ru.gdcn.alex.whattodo.utilities.TextFormer;

public class NotesRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "ToDO_Logger";
    private static final String className = "NotesRecyclerAdapter";

    private List<Note> noteList;
    private Context context;

    public static final int  OTHER_NOTES_HEADER = 2;
    public static final int  OTHER_NOTES = 20;


    public NotesRecyclerAdapter(Context context) {
        this.context = context;
    }

    public void loadNotes(){
        Log.d(TAG, TextFormer.getStartText(className) + "Добавление карточек в список...");
        noteList = DBConnector.loadNotes(context);
        notifyDataSetChanged();
        Log.d(TAG, TextFormer.getStartText(className) + "Карточки добавлены!");
    }

    public void removeItem(int position){
        Log.d(TAG, TextFormer.getStartText(className) + "Удаление карточки из списка...");
        DBConnector.deleteNote(context, getItem(position));
        noteList.remove(position - 1);
        notifyItemRemoved(position);
        Log.d(TAG, TextFormer.getStartText(className) + "Карточка удалена из списка!");
    }

    public void swapCard(int oldPosition, int newPosition){
        Note o = getItem(oldPosition);
        Note n = getItem(newPosition);
        int temp = o.getPosition();
        o.setPosition(n.getPosition());
        n.setPosition(temp);
        Collections.swap(noteList, oldPosition - 1, newPosition - 1);
        notifyItemMoved(oldPosition, newPosition);
        DBConnector.updateNote(context, o);
        DBConnector.updateNote(context, n);
    }

    public Note getItem(int index){
        return noteList.get(index - 1);
    }

    @Override
    public int getItemCount() {
        return noteList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return OTHER_NOTES_HEADER;
        } else {
            return OTHER_NOTES;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        RecyclerView.ViewHolder vh = null;
        switch (viewType){
            case OTHER_NOTES_HEADER:
                view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.main_notes_recycler_header, viewGroup, false);
                vh = new HeaderViewHolder(view);
                break;
            case OTHER_NOTES:
                view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.notes_recyclerview_card, viewGroup, false);
                vh = new CardViewHolder(view);
                break;
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        switch (getItemViewType(position)){
            case OTHER_NOTES:
                ((CardViewHolder)viewHolder).bind(noteList.get(position-1));
                break;
            case OTHER_NOTES_HEADER:
                ((HeaderViewHolder)viewHolder).bindAllHeader();
                break;
        }
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {

        private TextView headerView;

        CardViewHolder(@NonNull View itemView) {
            super(itemView);
            headerView = itemView.findViewById(R.id.notes_recyclerview_header);
        }

        void bind(Note note) {
            headerView.setText(note.getHeader());
        }

    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        TextView header;

        HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.main_notes_recycler_header);
        }

        public void bindAllHeader() {
            header.setText(R.string.main_notes_recycler_header_all);
        }
    }
}
