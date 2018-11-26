package ru.gdcn.alex.whattodo.creation;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ru.gdcn.alex.whattodo.data.DBConnector;
import ru.gdcn.alex.whattodo.objects.Item;
import ru.gdcn.alex.whattodo.objects.Note;
import ru.gdcn.alex.whattodo.utilities.TextFormer;

public class CreationManager {

    private static final String TAG = "ToDO_Logger";
    private static final String className = "CreationManager";

    private Context activity;

    private Note note;
    private int countNotes;

    private List<Item> items;
    private List<Item> deleteItems = new ArrayList<>();

    public boolean clickCreate = false;


    public CreationManager(Context activity) {
        this.activity = activity;
    }

    //TODO переделать
    public void init(Intent data) {
        Log.d(TAG, TextFormer.getStartText(className) + "Инициализирую данные...");
        countNotes = DBConnector.loadNotes(activity).size();

        note = (Note) data.getSerializableExtra("note");
        if (note == null) {
            note = new Note(countNotes + 1, countNotes + 1);
            clickCreate = true;
            items = new ArrayList<>();
        }
        else
            items = (List<Item>) DBConnector.loadItems(activity, note.getId());

        Log.d(TAG, TextFormer.getStartText(className) + "Инициализация данных завершена!");
    }

    public Note getNote() {
        return note;
    }

    public List<Item> getItems() {
        return items;
    }

    public List<Item> getDeleteItems() {
        return deleteItems;
    }

    public boolean isClickCreate() {
        return clickCreate;
    }


}
