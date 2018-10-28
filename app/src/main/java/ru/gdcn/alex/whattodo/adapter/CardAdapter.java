package ru.gdcn.alex.whattodo.adapter;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ru.gdcn.alex.whattodo.R;
import ru.gdcn.alex.whattodo.customviews.Card;
import ru.gdcn.alex.whattodo.utilities.TextFormer;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private static final String TAG = "ToDO_Logger";
    private static final String className = "CardAdapter";

    private List<Card> cardList = new ArrayList<>();

    public void setItems(Collection<Card> cards){
        Log.d(TAG, TextFormer.getStartText(className) + "Добавление карточек в список...");
        cardList.addAll(cards);
        notifyDataSetChanged();
        Log.d(TAG, TextFormer.getStartText(className) + "Карточки добавлены!");
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
