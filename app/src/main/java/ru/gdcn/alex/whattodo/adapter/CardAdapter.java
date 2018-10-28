package ru.gdcn.alex.whattodo.adapter;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ru.gdcn.alex.whattodo.R;
import ru.gdcn.alex.whattodo.customviews.Card;

import static ru.gdcn.alex.whattodo.R.color.colorPrimary;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private List<Card> cardList = new ArrayList<>();

    public void setItems(Collection<Card> cards){
        cardList.addAll(cards);
        notifyDataSetChanged();
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

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_header);
        }

        public void bind(Card card) {
            cardView.setText(card.getHeader());
//            cardView.setCompoundDrawables(itemView.getContext().getDrawable(card.getIcon()),
//                    itemView.getContext().getDrawable(card.getIcon()),
//                    itemView.getContext().getDrawable(card.getIcon()),
//                    itemView.getContext().getDrawable(card.getIcon()));
        }
    }
}
