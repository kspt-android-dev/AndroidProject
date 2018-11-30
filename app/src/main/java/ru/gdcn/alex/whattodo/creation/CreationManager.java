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

    public CreationManager(Context activity) {
        this.activity = activity;
    }

    //TODO переделать
    public void init(Intent data) {
        Log.d(TAG, TextFormer.getStartText(className) + "Инициализирую данные...");
        countNotes = DBConnector.loadNotes(activity).size();

        note = (Note) data.getSerializableExtra("note");
        if (note == null) {
            note = new Note(countNotes + 1);
            note.setId(DBConnector.insertNote(activity, note));
        }
        if (note.getType().equals("list"))
            items = (List<Item>) DBConnector.loadItems(activity, note.getId());
        else
            items = new ArrayList<>();

        Log.d(TAG, TextFormer.getStartText(className) + "Инициализация данных завершена!");
    }

    public void save() {

        if (note.getHeader().equals("") && note.getContent().equals("")) {
            Log.d(TAG, TextFormer.getStartText(className) + "Пустые поля. Такую запись не добавляю или удаляем!");
            DBConnector.deleteNote(activity, note);
            return;
        }
//            DBConnector.insertNote(activity, note);
//            if (note.getType().equals("list"))
//                for (Item item : items) {
//                    DBConnector.insertItem(activity, item);
//                }
//            Log.d(TAG, TextFormer.getStartText(className) + "Пунктов сохранено - " + items.size());
//            Log.d(TAG, TextFormer.getStartText(className) + "Данные добавлены!");

        DBConnector.updateNote(activity, note);
        for (Item item : deleteItems) {
            DBConnector.deleteItem(activity, item);
        }
        if (note.getType().equals("list"))
            for (Item item : items) {
                if (item.getId() == Item.NEW_ITEM)
                    DBConnector.insertItem(activity, item);
                else
                    DBConnector.updateItem(activity, item);
            }
        Log.d(TAG, TextFormer.getStartText(className) + "Пунктов сохранено - " + items.size());
        Log.d(TAG, TextFormer.getStartText(className) + "Данные обновлены!");

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
}
