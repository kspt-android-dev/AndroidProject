package org.easyeng.easyeng;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {
    private int id;
    private String title;
    private int level;
    private String text;
    private String tags;
    private String words;

    public Item() {
    }

    public Item(String title, int id, String text, int level, String tags, String words) {
        this.title = title;
        this.id = id;
        this.text = text;
        this.level = level;
        this.tags = tags;
        this.words = words;
    }

    public Item(Parcel in) {
        id = in.readInt();
        title = in.readString();
        level = in.readInt();
        text = in.readString();
        tags = in.readString();
        words = in.readString();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public int getLevel() {
        return level;
    }

    public String getTags() {
        return tags;
    }

    public String getWords() {
        return words;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeInt(level);
        dest.writeString(text);
        dest.writeString(tags);
        dest.writeString(words);
    }
}
