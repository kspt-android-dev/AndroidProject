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

public class NotesRecyclerAdapter extends RecyclerView.Adapter<NotesRecyclerAdapter.CardViewHolder>
                                implements SwipeDragHelperCallback.ActionCompletionContract {

    private static final String TAG = "ToDO_Logger";
    private static final String className = "NotesRecyclerAdapter";

    private List<Note> noteList = new ArrayList<>();
    private List<Integer> selectedCardList = new ArrayList<>();
    private Context context;

    public NotesRecyclerAdapter(Context context) {
        this.context = context;
    }

    public void addItem(Note note){
        Log.d(TAG, TextFormer.getStartText(className) + "Добавление карточки в список...");
        noteList.add(note);
        notifyItemInserted(noteList.size() - 1);
        Log.d(TAG, TextFormer.getStartText(className) + "Карточка добавлена!");
    }

    public void addItem(Note note, int position){
        Log.d(TAG, TextFormer.getStartText(className) + "Добавление карточки в список...");
        noteList.add(position, note);
        notifyItemInserted(position);
        Log.d(TAG, TextFormer.getStartText(className) + "Карточка добавлена!");
    }

    public void addItems(Collection<Note> notes){
        Log.d(TAG, TextFormer.getStartText(className) + "Добавление карточек в список...");
        noteList.addAll(notes);
        notifyDataSetChanged();
        Log.d(TAG, TextFormer.getStartText(className) + "Карточки добавлены!");
    }

    public void removeItem(Note note){
        Log.d(TAG, TextFormer.getStartText(className) + "Удаление карточки из списка...");
        int i = noteList.indexOf(note);
        notifyItemRemoved(i);
//        noteList.remove(i);
//        selectedCardList.remove((Object) note.getId());
        Log.d(TAG, TextFormer.getStartText(className) + "Карточка удалена из списка!");
    }

    public void clearItems(){
        Log.d(TAG, TextFormer.getStartText(className) + "Удаление карточек из списка...");
        noteList.clear();
        notifyDataSetChanged();
        Log.d(TAG, TextFormer.getStartText(className) + "Карточки удалены!");
    }

    public Collection<Note> getItems(){
        return noteList;
    }

    public void addSelectedItem(int id){
        Log.d(TAG, TextFormer.getStartText(className) + "Добавление карточки в список выделенных...");
        selectedCardList.add(id);
        notifyDataSetChanged();
        Log.d(TAG, TextFormer.getStartText(className) + "Карточка добавлена в список выделенных!");
    }

    public void addSelectedItems(Collection<Integer> setectedCards){
        selectedCardList.addAll(setectedCards);
        notifyDataSetChanged();
    }

    public void removeSelectedItem(int id){
        Log.d(TAG, TextFormer.getStartText(className) + "Удаление карточки из списка выделенных...");
        selectedCardList.remove((Object) id);
        notifyDataSetChanged();
        Log.d(TAG, TextFormer.getStartText(className) + "Карточка удалена из списка выделенных!");
    }

    public Collection<Integer> getSelectedItems(){
        return selectedCardList;
    }

    public void clearSelectedItems(){
        Log.d(TAG, TextFormer.getStartText(className) + "Удаление карточек из списка выделенных...");
        selectedCardList.clear();
        notifyDataSetChanged();
        Log.d(TAG, TextFormer.getStartText(className) + "Карточки удалены из списка выделенных!");
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

//        holder.title.setText(list.get(position).getTitle());
//        int id = noteList.get(i).getId();

//        if (selectedCardList.contains(id)){
//            //if item is selected then,set foreground color of FrameLayout.
//            cardViewHolder.selectBack();
//
//        }
//        else {
//            //else remove selected item color.
//            cardViewHolder.removeSelect();
//        }
    }

    @Override
    public void onViewMoved(int oldPosition, int newPosition) {
        //TODO теперь надо чтоб в бд тоже местами поменялись
        Log.d(TAG, TextFormer.getStartText(className) + "Словил перемещение...");
        swapCard(oldPosition, newPosition);
        notifyItemMoved(oldPosition, newPosition);
    }

    @Override
    public void onViewSwiped(int position) {
        Log.d(TAG, TextFormer.getStartText(className) + "Словил свайп..." + position);
        removeItem(getItem(position));
        DBConnector.deleteNote(context, getItem(position));
    }

    private void swapCard(int oldPosition, int newPosition){
        Note o = noteList.get(oldPosition);
        Note n = noteList.get(newPosition);
        int temp = o.getPosition();
        o.setPosition(n.getPosition());
        n.setPosition(temp);
        Collections.swap(noteList, oldPosition, newPosition);
        DBConnector.updateNote(context, o);
        DBConnector.updateNote(context, n);
    }

    class CardViewHolder extends RecyclerView.ViewHolder {

        private TextView headerView;
//        private CardView cardView;

        CardViewHolder(@NonNull View itemView) {
            super(itemView);
            headerView = itemView.findViewById(R.id.notes_recyclerview_header);
//            cardView = itemView.findViewById(R.id.notes_recyclerview_card);
        }

        void bind(Note note) {
            headerView.setText(note.getHeader());
        }

//        void selectBack(){
//            cardView.setBackgroundColor(cardView.getContext().getResources().getColor(R.color.colorPrimaryDark));
//            cardView.setCardBackgroundColor(cardView.getContext().getResources().getColor(R.color.colorPrimaryDark));
//        }
//
//        void removeSelect(){
//            cardView.setCardBackgroundColor(cardView.getContext().getResources().getColor(R.color.colorPrimary));
//        }


    }
}
