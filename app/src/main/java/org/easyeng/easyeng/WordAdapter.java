package org.easyeng.easyeng;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.easyeng.easyeng.db.entities.Word;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordHolder> {

    private List<Word> words;

    @NonNull
    @Override
    public WordHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_card, parent, false);
        return new WordAdapter.WordHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordHolder holder, int position) {
        if (words != null) {
            holder.original.setText(words.get(position).getWord());
            holder.translation.setText(words.get(position).getTranslation());
        }
    }

    @Override
    public int getItemCount() {
        if (words != null) return words.size();
        return 0;
    }

    public void setWords(List<Word> words) {
        this.words = words;
        notifyDataSetChanged();
    }

    public Word getWord(int position) {
        if (position > getItemCount()) throw new IllegalArgumentException();
        return words.get(position);
    }

    public void deleteWord(int position) {
        words.remove(position);
        notifyDataSetChanged();
    }

    class WordHolder extends RecyclerView.ViewHolder {
        private TextView original;
        private TextView translation;

        public WordHolder(@NonNull View itemView) {
            super(itemView);
            original = itemView.findViewById(R.id.orig_word);
            translation = itemView.findViewById(R.id.trans_word);
        }
    }
}