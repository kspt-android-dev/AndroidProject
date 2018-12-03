package ru.gdcn.alex.whattodo.creation;

import android.content.SharedPreferences;
import android.graphics.Paint;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ru.gdcn.alex.whattodo.R;
import ru.gdcn.alex.whattodo.objects.Item;
import ru.gdcn.alex.whattodo.recycler.RecyclerItemClickListener;
import ru.gdcn.alex.whattodo.utilities.TextFormer;

public class ListFragment extends Fragment implements RecyclerItemClickListener.OnItemClickListener{

    private static final String TAG = "ToDO_Logger";
    private static final String className = "ListFragment";

    private CreationActivity activity;

    private ItemsRecyclerAdapter itemsRecyclerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = (CreationActivity) getActivity();
        Log.d(TAG, TextFormer.getStartText(className) + "onCreate!");
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
        RecyclerView recyclerView = getActivity().findViewById(R.id.creation_list_fragment_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        itemsRecyclerAdapter = new ItemsRecyclerAdapter(activity);
        recyclerView.setAdapter(itemsRecyclerAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerView, this));
        Log.d(TAG, TextFormer.getStartText(className) + "Список инициализирован!");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, TextFormer.getStartText(className) + "onStart!");
        if (activity.getNoteManager().getItems().size() == 0) {
            Log.d(TAG, TextFormer.getStartText(className) + "Не первое открытие!");
            Log.d(TAG, TextFormer.getStartText(className) + "Разделяю на элементы...");
            String[] contents = activity.getNoteManager().getNote().getContent().split("\n");
            List<Item> items = new ArrayList<>();
            if (contents.length != 0) {
                for (int i = 0; i < contents.length; i++) {
                    items.add(new Item(
                            Item.NEW_ITEM,
                            activity.getNoteManager().getNote().getId(),
                            i + 1,
                            contents[i],
                            Item.DEFAULT_CHECKED
                    ));
                }
            } else {
                items.add(new Item(
                        Item.NEW_ITEM,
                        activity.getNoteManager().getNote().getId(),
                        1,
                        "",
                        Item.DEFAULT_CHECKED
                ));
            }
            items.add(new Item(Item.LAST_ITEM));
            itemsRecyclerAdapter.addItems(items);
            Log.d(TAG, TextFormer.getStartText(className) + "Разделение произошло!");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, TextFormer.getStartText(className) + "onStop!");
        StringBuilder stringBuilder = new StringBuilder();
        List<Item> items = activity.getNoteManager().getItems();
        if (items.size() > 1) {
            for (int i = 0; i < items.size() - 2; i++) {
                stringBuilder.append(items.get(i).getContent()).append("\n");
            }
            stringBuilder.append(items.get(items.size() - 2).getContent());
        }
        activity.getNoteManager().getNote().setContent(stringBuilder.toString());
    }

    @Override
    public void onItemClick(View view, int position) {
        //Это тоже наверное можно убрать, сделав по другому
        if (view == null)
            return;
        if (itemsRecyclerAdapter.getItem(position).getId() == Item.LAST_ITEM)
            itemsRecyclerAdapter.addItem(new Item(
                    Item.NEW_ITEM,
                    activity.getNoteManager().getNote().getId(),
                    itemsRecyclerAdapter.getItemCount(),
                    "",
                    Item.DEFAULT_CHECKED
                    ), itemsRecyclerAdapter.getItemCount() - 1);
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
