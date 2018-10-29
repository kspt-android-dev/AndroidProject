package ru.gdcn.alex.whattodo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import ru.gdcn.alex.whattodo.adapter.CardAdapter;
import ru.gdcn.alex.whattodo.customviews.Card;
import ru.gdcn.alex.whattodo.customviews.NotesView;
import ru.gdcn.alex.whattodo.data.DBConnector;
import ru.gdcn.alex.whattodo.utilities.TextFormer;

public class NotesFragment extends Fragment {

    private static final String TAG = "ToDO_Logger";
    public static final String className = "NotesFragment";

    private RecyclerView recyclerView;
    private CardAdapter cardAdapter;
    private Collection<Card> cards;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, TextFormer.getStartText(className) + "Дизайн NotesFragment загружен!");
        return inflater.inflate(R.layout.notes_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, TextFormer.getStartText(className) + "Инициализация NotesFragment...");
        initRecyclerView();

        //TODO попробовать так обработать нажатие
//        recyclerView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, TextFormer.getStartText(className) + "Инициализация NotesFragment закончена!");
    }

    @Override
    public void onStart() {
        cardAdapter.clearItems();
        cardAdapter.setItems(getCards());
        super.onStart();
    }

    private void initRecyclerView(){
        Log.d(TAG, TextFormer.getStartText(className) + "Инициализация списка...");
        recyclerView = getActivity().findViewById(R.id.notes_fragment_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cardAdapter = new CardAdapter();
        recyclerView.setAdapter(cardAdapter);
        Log.d(TAG, TextFormer.getStartText(className) + "Список инициализирован!");
    }

    private Collection<Card> getCards(){
        return DBConnector.getData(getContext(), 0);
    }
}
