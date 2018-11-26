package ru.gdcn.alex.whattodo.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collection;

import ru.gdcn.alex.whattodo.R;
import ru.gdcn.alex.whattodo.creation.CreationActivity;
import ru.gdcn.alex.whattodo.objects.Note;
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
    private NotesRecyclerAdapter notesRecyclerAdapter;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;

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
        fab = getActivity().findViewById(R.id.main_fab_create);
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, TextFormer.getStartText(className) + "Инициализация NotesFragment закончена!");
    }

    @Override
    public void onStart() {
        Log.d(TAG, TextFormer.getStartText(className) + "Сработал onStart");
        //TODO изменить логику обнолвения списка. Возвращать данные из пред. активити
        notesRecyclerAdapter.clearItems();
        notesRecyclerAdapter.addItems(loadCards());
        super.onStart();
    }

    private void initRecyclerView() {
        Log.d(TAG, TextFormer.getStartText(className) + "Инициализация списка...");
        recyclerView = getActivity().findViewById(R.id.notes_fragment_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        notesRecyclerAdapter = new NotesRecyclerAdapter(getContext());
        SwipeDragHelperCallback swipeDragHelperCallback = new SwipeDragHelperCallback(notesRecyclerAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(swipeDragHelperCallback);
        recyclerView.setAdapter(notesRecyclerAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerView, this));
        touchHelper.attachToRecyclerView(recyclerView);
        Log.d(TAG, TextFormer.getStartText(className) + "Список инициализирован!");
    }

    private Collection<Note> loadCards() {
        return DBConnector.loadNotes(getContext());
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.d(TAG, TextFormer.getStartText(className) + "Словил клик на элемент!");
        if (isMultiSelect) {
            multiSelect(position);
        } else {
            Note note = notesRecyclerAdapter.getItem(position);
            Intent intent = new Intent(getContext(), CreationActivity.class);
            intent.putExtra("note", note);
            startActivity(intent);
        }
    }

    @Override
    public void onItemLongClick(View view, int position) {
        Log.d(TAG, TextFormer.getStartText(className) + "Словил долгий клик на элемент!");
        if (!isMultiSelect) {
            isMultiSelect = true;
            if (actionMode == null) {
                actionMode = getActivity().startActionMode(NotesFragment.this); //show ActionMode.
            }
            multiSelect(position);
        }
    }

    @Override
    public void onScroll(float startY, float endY) {
        Log.d(TAG, TextFormer.getStartText(className) + "Словил прокрутку!");
        if (recyclerView.getItemDecorationCount() < notesRecyclerAdapter.getItemCount()) {
            if (startY > endY)
                fab.hide();
            if (startY < endY)
                fab.show();
        }
    }

    //Дальше пока не используется. Нужно для выделения нескольких элементов
    private void multiSelect(int position) {
        Log.d(TAG, TextFormer.getStartText(className) + "Обрабатываю выдиление...");
        Note note = notesRecyclerAdapter.getItem(position);
        if (note != null) {
            if (actionMode != null) {
                if (notesRecyclerAdapter.getSelectedItems().contains(note.getId()))
                    notesRecyclerAdapter.removeSelectedItem(note.getId());
                else
                    notesRecyclerAdapter.addSelectedItem(note.getId());

                if (notesRecyclerAdapter.getSelectedItems().size() > 0)
                    actionMode.setTitle(String.valueOf(notesRecyclerAdapter.getSelectedItems().size())); //show selected item count on action mode.
                else {
                    actionMode.setTitle(""); //remove item count from action mode.
                    actionMode.finish(); //hide action mode.
                }
            }
        }
        Log.d(TAG, TextFormer.getStartText(className) + "Выдиление обработано!");
    }

    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        actionMode.getMenuInflater().inflate(R.menu.menu_selecet, menu);
        Log.d(TAG, TextFormer.getStartText(className) + "ActionMode создано!");
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        Log.d(TAG, TextFormer.getStartText(className) + "Словил клик на элемент ActionMode...");
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
        Log.d(TAG, TextFormer.getStartText(className) + "Уничтожение ActionMode...");
        isMultiSelect = false;
        this.actionMode = null;
        notesRecyclerAdapter.clearSelectedItems();
        Log.d(TAG, TextFormer.getStartText(className) + "ActionMode уничтожено!");
    }
}
