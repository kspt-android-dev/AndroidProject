package ru.gdcn.alex.whattodo.creation;

import android.graphics.Paint;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import ru.gdcn.alex.whattodo.R;
import ru.gdcn.alex.whattodo.objects.Item;
import ru.gdcn.alex.whattodo.recycler.RecyclerItemClickListener;
import ru.gdcn.alex.whattodo.utilities.TextFormer;

public class ListFragment extends Fragment implements RecyclerItemClickListener.OnItemClickListener {

    private static final String TAG = "ToDO_Logger";
    private static final String className = "ListFragment";

    private CreationActivity activity;

    private RecyclerView recyclerView;
    private ItemsRecyclerAdapter itemsRecyclerAdapter;

    private int lastPosition = -1;
    private EditText lastView = null;

//    private boolean firstView = true;

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
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerView, this));
        Log.d(TAG, TextFormer.getStartText(className) + "Список инициализирован!");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, TextFormer.getStartText(className) + "Сработала Resume!");
        if (activity.getNoteManager().getItems().size() != 0) {
            Log.d(TAG, TextFormer.getStartText(className) + "Первое открытие!");
            itemsRecyclerAdapter.notifyDataSetChanged();
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
            itemsRecyclerAdapter.addItems(items);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, TextFormer.getStartText(className) + "Сработала Pause!");
        if (lastView != null) {
            itemsRecyclerAdapter.getItem(lastPosition).setContent(lastView.getText().toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Item item : activity.getNoteManager().getItems()) {
            stringBuilder.append(item.getContent()).append("\n");
        }
        activity.getNoteManager().getNote().setContent(stringBuilder.toString());
    }

    @Override
    public void onItemClick(View view, int position) {
//        ((CheckBox) view).setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        if (lastView != null) {
            itemsRecyclerAdapter.getItem(lastPosition).setContent(lastView.getText().toString());
        }
        lastPosition = position;
        lastView = view.findViewById(R.id.creation_list_fragment_recycler_item_content);

        //TODO
    }

    @Override
    public void onItemLongClick(View view, int position) {
        //Не надо
    }

    @Override
    public void onScroll(float startY, float endY) {
        //Не надо
    }
}
