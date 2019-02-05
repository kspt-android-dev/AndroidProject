package org.easyeng.easyeng;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.annotations.NotNull;

import org.easyeng.easyeng.db.AsyncDBManager;
import org.easyeng.easyeng.db.MyDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class WordsFragment extends Fragment {

    private MyDatabase myDatabase;
    private RecyclerView recyclerView;
    private WordAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_words, null);
        setUpRecyclerView(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void setUpRecyclerView(@NotNull View view) {
        RecyclerView recyclerView = view.findViewById(R.id.words_rv);
        adapter = new WordAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        WordViewModel model = ViewModelProviders.of(this).get(WordViewModel.class);
        model.getAllWords().observe(this, words -> {
            adapter.setWords(words);
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT
        ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                model.delete(adapter.getWord(viewHolder.getAdapterPosition()).getId());
                adapter.deleteWord(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recyclerView);
    }
}