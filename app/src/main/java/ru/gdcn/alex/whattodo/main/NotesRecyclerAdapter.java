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

public class NotesRecyclerAdapter extends RecyclerView.Adapter<NotesRecyclerAdapter.CardViewHolder> {

    private static final String TAG = "ToDO_Logger";
    private static final String className = "NotesRecyclerAdapter";

    private List<Note> noteList;
    private Context context;

    public NotesRecyclerAdapter(Context context) {
        this.context = context;
    }

    public void loadNotes(){
        Log.d(TAG, TextFormer.getStartText(className) + "Добавление карточек в список...");
        noteList = DBConnector.loadNotes(context);
        notifyDataSetChanged();
        Log.d(TAG, TextFormer.getStartText(className) + "Карточки добавлены!");
    }

    public void removeItem(Note note){
        Log.d(TAG, TextFormer.getStartText(className) + "Удаление карточки из списка...");
        int i = noteList.indexOf(note);
        noteList.remove(i);
        notifyItemRemoved(i);
        DBConnector.deleteNote(context, note);
        Log.d(TAG, TextFormer.getStartText(className) + "Карточка удалена из списка!");
    }

    public void swapCard(int oldPosition, int newPosition){
        Note o = noteList.get(oldPosition);
        Note n = noteList.get(newPosition);
        int temp = o.getPosition();
        o.setPosition(n.getPosition());
        n.setPosition(temp);
        Collections.swap(noteList, oldPosition, newPosition);
        notifyItemMoved(oldPosition, newPosition);
        DBConnector.updateNote(context, o);
        DBConnector.updateNote(context, n);
    }

    public Note getItem(int index){
        return noteList.get(index);
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.notes_recyclerview_card, viewGroup, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder cardViewHolder, int i) {
        cardViewHolder.bind(noteList.get(i));
    }

    class CardViewHolder extends RecyclerView.ViewHolder {

        private TextView headerView;

        CardViewHolder(@NonNull View itemView) {
            super(itemView);
            headerView = itemView.findViewById(R.id.notes_recyclerview_header);
        }

        void bind(Note note) {
            headerView.setText(note.getHeader());
        }

    }

    //        void removeSelect(){
//
//        }
//            cardView.setCardBackgroundColor(cardView.getContext().getResources().getColor(R.color.colorPrimaryDark));
//            cardView.setBackgroundColor(cardView.getContext().getResources().getColor(R.color.colorPrimaryDark));
//    public void addSelectedItem(int id){
//        Log.d(TAG, TextFormer.getStartText(className) + "Добавление карточки в список выделенных...");
//        selectedCardList.add(id);
//        notifyDataSetChanged();
//        Log.d(TAG, TextFormer.getStartText(className) + "Карточка добавлена в список выделенных!");
//    }
//
//    public void addSelectedItems(Collection<Integer> setectedCards){
//        selectedCardList.addAll(setectedCards);
//        notifyDataSetChanged();
//    }
//
//    public void removeSelectedItem(int id){
//        Log.d(TAG, TextFormer.getStartText(className) + "Удаление карточки из списка выделенных...");
//        selectedCardList.remove((Object) id);
//        notifyDataSetChanged();
//        Log.d(TAG, TextFormer.getStartText(className) + "Карточка удалена из списка выделенных!");
//    }
//
//    public Collection<Integer> getSelectedItems(){
//        return selectedCardList;
//    }
//    public void clearSelectedItems(){
//        Log.d(TAG, TextFormer.getStartText(className) + "Удаление карточек из списка выделенных...");
//        selectedCardList.clear();
//        notifyDataSetChanged();
//        Log.d(TAG, TextFormer.getStartText(className) + "Карточки удалены из списка выделенных!");
//    }
}
