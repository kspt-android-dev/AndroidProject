package ru.gdcn.alex.whattodo.creation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.gdcn.alex.whattodo.R;
import ru.gdcn.alex.whattodo.objects.Item;
import ru.gdcn.alex.whattodo.utilities.TextFormer;

public class ListFragment extends Fragment {

    private static final String TAG = "ToDO_Logger";
    private static final String className = "ListFragment";

    private CreationActivity activity;

    private RecyclerView recyclerView;
    private ItemsRecyclerAdapter itemsRecyclerAdapter;

    private boolean firstView = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = (CreationActivity) getActivity();
        Log.d(TAG, TextFormer.getStartText(className) + "Создал ListFragment!");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.creation_list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecyclerView();
    }

    private void initRecyclerView() {
        Log.d(TAG, TextFormer.getStartText(className) + "Инициализация списка...");
        recyclerView = getActivity().findViewById(R.id.creation_list_fragment_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        itemsRecyclerAdapter = new ItemsRecyclerAdapter(activity);
        recyclerView.setAdapter(itemsRecyclerAdapter);
        Log.d(TAG, TextFormer.getStartText(className) + "Список инициализирован!");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, TextFormer.getStartText(className) + "Сработала Resume!");
        if (firstView && activity.getNoteManager().getItems().size() != 0) {
            Log.d(TAG, TextFormer.getStartText(className) + "Первое открытие!");
            itemsRecyclerAdapter.notifyDataSetChanged();
            firstView = false;
        }
        else {
            Log.d(TAG, TextFormer.getStartText(className) + "Не первое открытие!");
            Log.d(TAG, TextFormer.getStartText(className) + "Разделяю на элементы...");
            String[] contents = activity.getNoteManager().getNote().getContent().split("\n");
            List<Item> items = new ArrayList<>();
            for (int i = 0; i < contents.length; i++) {
                items.add(new Item(
                        activity.getNoteManager().getNote().getId(),
                        i + 1,
                        contents[i],
                        0
                ));
            }
            Log.d(TAG, TextFormer.getStartText(className) + "Разделение произошло!");
            itemsRecyclerAdapter.clearItems();
            itemsRecyclerAdapter.addItems(items);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, TextFormer.getStartText(className) + "Сработала Pause!");
        StringBuilder stringBuilder = new StringBuilder();
        for (Item item : activity.getNoteManager().getItems()) {
            stringBuilder.append(item.getContent()).append("\n");
        }
        activity.getNoteManager().getNote().setContent(stringBuilder.toString());
    }
}
