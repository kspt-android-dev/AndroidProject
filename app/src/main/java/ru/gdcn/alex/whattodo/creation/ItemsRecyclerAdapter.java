package ru.gdcn.alex.whattodo.creation;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.inputmethodservice.Keyboard;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
        if (itemList.get(i).getId() == Item.LAST_ITEM)
            itemViewHolder.bindAdd();
        else
            itemViewHolder.bindItem(itemList.get(i));
    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {

        private CheckBox checkBox;
        private EditText editText;
        private ImageButton imageButton;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.creation_list_fragment_recycler_item_checkbox);
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Item item = getItem(position);
                    if (item.getChecked() == 1) {
                        item.setChecked(0);
                    } else {
                        item.setChecked(1);
                    }
                    bindItem(item);
                }
            });
            editText = itemView.findViewById(R.id.creation_list_fragment_recycler_item_content);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    Log.d(TAG, TextFormer.getStartText(className) + "Изменение текста.........");
                    getItem(getAdapterPosition()).setContent(s.toString());
                }
            });
            editText.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                        addItem(new Item(
                                Item.NEW_ITEM,
                                activity.getNoteManager().getNote().getId(),
                                getAdapterPosition() + 1,
                                "",
                                Item.DEFAULT_CHECKED
                        ), getAdapterPosition() + 1);
                        return true;
                    }
                    if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                        if (((EditText) v).getText().toString().equals(""))
                            removeItem(itemList.get(getAdapterPosition()));
                        return false;
                    }
                    return false;
                }
            });
            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (getAdapterPosition() == getItemCount() - 1)
                        return;
                    if (!hasFocus)
                        imageButton.setVisibility(View.INVISIBLE);
                    else
                        imageButton.setVisibility(View.VISIBLE);
                }
            });
            imageButton = itemView.findViewById(R.id.creation_list_fragment_recycler_item_delete);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeItem(getItem(getAdapterPosition()));
                }
            });
        }

        void bindItem(Item item) {
            editText.setText(item.getContent());
            if (item.getChecked() == 1) {
                checkBox.setChecked(true);
                editText.setPaintFlags(editText.getPaintFlags() ^ Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                checkBox.setChecked(false);
                if ((editText.getPaintFlags() & Paint.STRIKE_THRU_TEXT_FLAG) == Paint.STRIKE_THRU_TEXT_FLAG)
                    editText.setPaintFlags(editText.getPaintFlags() ^ Paint.STRIKE_THRU_TEXT_FLAG);
            }
        }

        void bindAdd() {
            editText.setHint("+ Новый пункт");
            editText.setEnabled(false);
            checkBox.setVisibility(View.INVISIBLE);
            checkBox.setEnabled(false);
            imageButton.setFocusable(false);
            imageButton.setEnabled(false);
        }

    }
}
