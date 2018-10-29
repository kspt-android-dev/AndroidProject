package ru.gdcn.alex.whattodo.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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
import ru.gdcn.alex.whattodo.utilities.TextFormer;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.CardViewHolder>
                                implements SwipeDragHelperCallback.ActionCompletionContract {

    private static final String TAG = "ToDO_Logger";
    private static final String className = "MyRecyclerAdapter";

    private List<Card> cardList = new ArrayList<>();

    public void setItems(Collection<Card> cards){
        Log.d(TAG, TextFormer.getStartText(className) + "Добавление карточек в список...");
        cardList.addAll(cards);
        notifyDataSetChanged();
        Log.d(TAG, TextFormer.getStartText(className) + "Карточки добавлены!");
    }

    public void clearItems(){
        Log.d(TAG, TextFormer.getStartText(className) + "Удаление карточек из списка...");
        cardList.clear();
        notifyDataSetChanged();
        Log.d(TAG, TextFormer.getStartText(className) + "Карточки удалены!");
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_view_card, viewGroup, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder cardViewHolder, int i) {
        cardViewHolder.bind(cardList.get(i));
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    @Override
    public void onViewMoved(int oldPosition, int newPosition) {
        //TODO теперь надо чтоб в бд тоже местами поменялись
        Log.d(TAG, TextFormer.getStartText(className) + "Словил перемещение...");
//        Collections.swap(cardList, oldPosition, newPosition);
        notifyItemMoved(oldPosition, newPosition);
    }

    @Override
    public void onViewSwiped(int position) {
        Log.d(TAG, TextFormer.getStartText(className) + "Словил свайп...");
        //TODO порабоать со свайпом, чтоб элемент не удалялся, а выполнял действие
    }

    class CardViewHolder extends RecyclerView.ViewHolder {

        private TextView cardView;

        CardViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_header);
        }

        void bind(Card card) {
            cardView.setText(card.getHeader());
            cardView.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    itemView.getContext().getDrawable(card.getIcon()),
                    null, null, null);
        }


    }
}
