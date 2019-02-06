package com.example.eugenia.game_2048;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ImageAdapter extends BaseAdapter {
    Context mContext;
    Integer [] picsId;
    public ImageAdapter(Context c, Integer[] list) {
        mContext = c;
        picsId = list;
    }
    @Override
    public int getCount() {

        return picsId.length;
    }

    @Override
    public Object getItem(int position) {

        return picsId[position];
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
           imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        } else {
            imageView = (ImageView) convertView;

        }
        imageView.setImageResource(picsId[position]);
        return imageView;
    }


}
