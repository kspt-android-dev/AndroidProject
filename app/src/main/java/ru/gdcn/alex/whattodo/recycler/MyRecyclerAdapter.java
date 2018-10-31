package ru.gdcn.alex.whattodo.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
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
import ru.gdcn.alex.whattodo.customviews.Card;
import ru.gdcn.alex.whattodo.data.DBConnector;
import ru.gdcn.alex.whattodo.utilities.TextFormer;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.CardViewHolder>
                                implements SwipeDragHelperCallback.ActionCompletionContract {

    private static final String TAG = "ToDO_Logger";
    private static final String className = "MyRecyclerAdapter";

    private List<Card> cardList = new ArrayList<>();
    private List<Integer> selectedCardList = new ArrayList<>();

    public void addItem(Card card){
        Log.d(TAG, TextFormer.getStartText(className) + "Добавление карточки в список...");
        cardList.add(card);
        notifyItemInserted(cardList.size() - 1);
        Log.d(TAG, TextFormer.getStartText(className) + "Карточка добавлена!");
    }

    public void addItem(Card card, int position){
        Log.d(TAG, TextFormer.getStartText(className) + "Добавление карточки в список...");
        cardList.add(position, card);
        notifyItemInserted(position);
        Log.d(TAG, TextFormer.getStartText(className) + "Карточка добавлена!");
    }

    public void addItems(Collection<Card> cards){
        Log.d(TAG, TextFormer.getStartText(className) + "Добавление карточек в список...");
        cardList.addAll(cards);
        notifyDataSetChanged();
        Log.d(TAG, TextFormer.getStartText(className) + "Карточки добавлены!");
    }

    public void removeItem(Card card){
        Log.d(TAG, TextFormer.getStartText(className) + "Удаление карточки из списка...");
        int i = cardList.indexOf(card);
        cardList.remove(card);
//        selectedCardList.remove((Object) card.getId());
        notifyItemRemoved(i);
        Log.d(TAG, TextFormer.getStartText(className) + "Карточка удалена из списка!");
    }

    public void clearItems(){
        Log.d(TAG, TextFormer.getStartText(className) + "Удаление карточек из списка...");
        cardList.clear();
        notifyDataSetChanged();
        Log.d(TAG, TextFormer.getStartText(className) + "Карточки удалены!");
    }

    public Collection<Card> getItems(){
        return cardList;
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

    public Card getItem(int index){
        return cardList.get(index);
    }

    @Override
    public int getItemCount() {
        return cardList.size();
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
        cardViewHolder.bind(cardList.get(i));

//        holder.title.setText(list.get(position).getTitle());
//        int id = cardList.get(i).getId();

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
        Log.d(TAG, TextFormer.getStartText(className) + "Словил свайп...");
    }

    private void swapCard(int oldPosition, int newPosition){
//        Card card = cardList.get(oldPosition);
//        Collections.swap(cardList, oldPosition, newPosition);
    }

    class CardViewHolder extends RecyclerView.ViewHolder {

        private TextView headerView;
//        private CardView cardView;

        CardViewHolder(@NonNull View itemView) {
            super(itemView);
            headerView = itemView.findViewById(R.id.notes_recyclerview_header);
//            cardView = itemView.findViewById(R.id.notes_recyclerview_card);
        }

        void bind(Card card) {
            headerView.setText(card.getHeader());
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
