package ru.gdcn.alex.whattodo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ru.gdcn.alex.whattodo.recycler.MyRecyclerAdapter;
import ru.gdcn.alex.whattodo.customviews.Card;
import ru.gdcn.alex.whattodo.data.DBConnector;
import ru.gdcn.alex.whattodo.recycler.RecyclerItemClickListener;
import ru.gdcn.alex.whattodo.recycler.SwipeDragHelperCallback;
import ru.gdcn.alex.whattodo.utilities.TextFormer;

public class NotesFragment extends Fragment implements ActionMode.Callback,
        RecyclerItemClickListener.OnItemClickListener {

    private static final String TAG = "ToDO_Logger";
    public static final String className = "NotesFragment";

    private ActionMode actionMode;
    private boolean isMultiSelect = false;
    private RecyclerView recyclerView;
    private MyRecyclerAdapter myRecyclerAdapter;

    private List<Integer> selectedIds = new ArrayList<>();

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

        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, TextFormer.getStartText(className) + "Инициализация NotesFragment закончена!");
    }

    @Override
    public void onStart() {
        //TODO изменить логику обнолвения списка
        myRecyclerAdapter.clearItems();
        myRecyclerAdapter.setItems(getCards());
        super.onStart();
    }

    private void initRecyclerView(){
        Log.d(TAG, TextFormer.getStartText(className) + "Инициализация списка...");
        recyclerView = getActivity().findViewById(R.id.notes_fragment_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerAdapter = new MyRecyclerAdapter();
        SwipeDragHelperCallback swipeDragHelperCallback = new SwipeDragHelperCallback(myRecyclerAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(swipeDragHelperCallback);
        recyclerView.setAdapter(myRecyclerAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerView, this));
        touchHelper.attachToRecyclerView(recyclerView);
        Log.d(TAG, TextFormer.getStartText(className) + "Список инициализирован!");
    }

    private Collection<Card> getCards(){
        return DBConnector.getData(getContext(), 0);
    }

    private void multiSelect(int position) {
        Log.d(TAG, TextFormer.getStartText(className) + "Обрабатываю выдиление...");
        Card data = myRecyclerAdapter.getItem(position);
        if (data != null) {
            if (actionMode != null) {
                if (selectedIds.contains(data.getId()))
                    selectedIds.remove(Integer.valueOf(data.getId()));
                else
                    selectedIds.add(data.getId());

                if (selectedIds.size() > 0)
                    actionMode.setTitle(String.valueOf(selectedIds.size())); //show selected item count on action mode.
                else {
                    actionMode.setTitle(""); //remove item count from action mode.
                    actionMode.finish(); //hide action mode.
                }
                myRecyclerAdapter.setSelectedIds(selectedIds);
            }
        }
        Log.d(TAG, TextFormer.getStartText(className) + "Выдиление обработано!");
    }

    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        actionMode.getMenuInflater().inflate(R.menu.menu_selecet, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
//        switch (menuItem.getItemId()){
//            case R.id.action_delete:
//                //just to show selected items.
//                StringBuilder stringBuilder = new StringBuilder();
//                for (MyData data : getList()) {
//                    if (selectedIds.contains(data.getId()))
//                        stringBuilder.append("\n").append(data.getTitle());
//                }
//                Toast.makeText(this, "Selected items are :" + stringBuilder.toString(), Toast.LENGTH_SHORT).show();
//                return true;
//        }
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
        isMultiSelect = false;

        //TODO проверить правильность действий
        actionMode = null;
        selectedIds = new ArrayList<>();
        myRecyclerAdapter.setSelectedIds(new ArrayList<Integer>());
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.d(TAG, TextFormer.getStartText(className) + "Словил клик на элемент!");
        if (isMultiSelect){
            //if multiple selection is enabled then select item on single click else perform normal click on item.
            multiSelect(position);
        }
    }

    @Override
    public void onItemLongClick(View view, int position) {
        Log.d(TAG, TextFormer.getStartText(className) + "Словил долгий клик на элемент!");
        if (!isMultiSelect){
            selectedIds = new ArrayList<>(); //TODO проверить правильность создания списка
            isMultiSelect = true;
            if (actionMode == null){
                actionMode = getActivity().startActionMode(NotesFragment.this); //show ActionMode.
            }
        }
        multiSelect(position);
    }
}
