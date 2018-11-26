package ru.gdcn.alex.whattodo.creation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.Collection;
import java.util.List;

import ru.gdcn.alex.whattodo.R;
import ru.gdcn.alex.whattodo.objects.Item;
import ru.gdcn.alex.whattodo.utilities.TextFormer;

public class ItemsRecyclerAdapter extends RecyclerView.Adapter<ItemsRecyclerAdapter.ItemViewHolder> {

    private static final String TAG = "ToDO_Logger";
    public static final String className = "ItemRecyclerAdapter";

    private CreationActivity activity;

    private List<Item> itemList;
    private List<Item> deleteItemList;

    public ItemsRecyclerAdapter(Context context) {
        this.activity = (CreationActivity) context;
        this.itemList = activity.getNoteManager().getItems();
        this.deleteItemList = activity.getNoteManager().getDeleteItems();
    }

    public void addItem(Item item) {
        Log.d(TAG, TextFormer.getStartText(className) + "Добавление карточки в список...");
        itemList.add(item);
        notifyItemInserted(itemList.size() - 1);
        Log.d(TAG, TextFormer.getStartText(className) + "Карточка добавлена!");
    }

    public void addItem(Item item, int position) {
        Log.d(TAG, TextFormer.getStartText(className) + "Добавление карточки в список...");
        itemList.add(position, item);
        notifyItemInserted(position);
        Log.d(TAG, TextFormer.getStartText(className) + "Карточка добавлена!");
    }

    public void addItems(Collection<Item> items) {
        Log.d(TAG, TextFormer.getStartText(className) + "Добавление карточек в список...");
        itemList.addAll(items);
        notifyDataSetChanged();
        Log.d(TAG, TextFormer.getStartText(className) + "Карточки добавлены!");
    }

    public void removeItem(Item item) {
        Log.d(TAG, TextFormer.getStartText(className) + "Удаление карточки из списка...");
        int i = itemList.indexOf(item);
        itemList.remove(item);
        if (item.getId() != Item.NEW_ITEM)
            deleteItemList.add(item);
        notifyItemRemoved(i);
        Log.d(TAG, TextFormer.getStartText(className) + "Карточка удалена из списка!");
    }

    public Item getItem(int index) {
        return itemList.get(index);
    }

    public Collection<Item> getItems() {
        return itemList;
    }

    public void clearItems() {
        Log.d(TAG, TextFormer.getStartText(className) + "Удаление карточек из списка...");
        deleteItemList.addAll(itemList);
        itemList.clear();
        notifyDataSetChanged();
        Log.d(TAG, TextFormer.getStartText(className) + "Карточки удалены!");
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.creation_list_fragment_recycler_item, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        itemViewHolder.bind(itemList.get(i));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {

        private CheckBox checkBox;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.creation_list_fragment_recycler_item_checkbox);
        }

        void bind(Item note) {
            checkBox.setText(note.getContent());
            checkBox.setChecked(note.getChecked() == 1);
        }


    }
}
