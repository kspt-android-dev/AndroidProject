package ru.gdcn.alex.whattodo.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collection;

import ru.gdcn.alex.whattodo.Card;
import ru.gdcn.alex.whattodo.CheckBoxLine;
import ru.gdcn.alex.whattodo.R;
import ru.gdcn.alex.whattodo.data.DBConnector;
import ru.gdcn.alex.whattodo.recycler.RecyclerItemClickListener;
import ru.gdcn.alex.whattodo.recycler.SwipeDragHelperCallback;
import ru.gdcn.alex.whattodo.recycler.checkbox.CheckBoxRecyclerAdapter;
import ru.gdcn.alex.whattodo.recycler.main.MainRecyclerAdapter;
import ru.gdcn.alex.whattodo.utilities.TextFormer;

public class ListFragment extends Fragment implements RecyclerItemClickListener.OnItemClickListener {

    private static final String TAG = "ToDO_Logger";
    private static final String className = "ListFragment";

    private RecyclerView recyclerView;
    private CheckBoxRecyclerAdapter checkBoxRecyclerAdapter;

    private Card note;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.creation_list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecyclerView();
        note = (Card) savedInstanceState.getSerializable("card");

        //TODO инициализировать список
        checkBoxRecyclerAdapter.addItems(note.getLines());
    }

    private void initRecyclerView() {
        Log.d(TAG, TextFormer.getStartText(className) + "Инициализация списка...");
        recyclerView = getActivity().findViewById(R.id.notes_fragment_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        checkBoxRecyclerAdapter = new CheckBoxRecyclerAdapter(getContext());

//        SwipeDragHelperCallback swipeDragHelperCallback = new SwipeDragHelperCallback(mainRecyclerAdapter);
//        ItemTouchHelper touchHelper = new ItemTouchHelper(swipeDragHelperCallback);
        recyclerView.setAdapter(checkBoxRecyclerAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerView, this));
//        touchHelper.attachToRecyclerView(recyclerView);
        Log.d(TAG, TextFormer.getStartText(className) + "Список инициализирован!");
    }

    //TODO добавить отслеживание Enter, добавляя новые строки в список

    @Override
    public void onItemClick(View view, int position) {
        CheckBoxLine checkBoxLine = checkBoxRecyclerAdapter.getItem(position);
        if (checkBoxLine.getChecked())
            checkBoxLine.setChecked(0);
        else
            checkBoxLine.setChecked(1);
        checkBoxRecyclerAdapter.notifyItemChanged(position);
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    @Override
    public void onScroll(float startY, float endY) {

    }
}
