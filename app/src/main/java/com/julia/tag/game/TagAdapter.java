package com.julia.tag.game;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

public class TagAdapter extends BaseAdapter {

    public static final int NUM_COLUMN_IN_P = 4;
    public static final int NUM_COLUMN_IN_LAND_VERTICAL = 5; //чтоб не вылазило за границы, пришлось увеличить на один
    public static final int NUM_COLUMN_IN_LAND_HORIZONTAL = 8; //чтоб было красиво нужно именно так

    public interface AdapterListener {
        void onChangedMoves(int moves);
        void onWin();
    }

    private Context context;
    private PlayField playField;

    private AdapterListener adapterListener;

    public TagAdapter(Context context) {
        this.context = context;
        this.adapterListener = (AdapterListener) context;
        setPlayField(new PlayField());
    }

    @Override
    public int getCount() {
        return playField.getSize();
    }

    @Override
    public Object getItem(int position) {
        return playField.getCell(position);
    }

    @Override
    public long getItemId(int position) {
        return playField.getCell(position);
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        final Button button;

        Display display = ((AppCompatActivity) context).getWindowManager().getDefaultDisplay();
        DisplayMetrics metricsB = new DisplayMetrics();
        display.getMetrics(metricsB);

        if (convertView == null) {
            button = new Button(context);
            button.setText(String.valueOf((int) getItem(position)));
            if ((int) getItem(position) == 0)
                button.setVisibility(Button.INVISIBLE);

            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                button.setWidth(metricsB.widthPixels / NUM_COLUMN_IN_P);
                button.setHeight(metricsB.widthPixels / NUM_COLUMN_IN_P);
            } else {
                button.setWidth(metricsB.widthPixels / NUM_COLUMN_IN_LAND_HORIZONTAL);
                button.setHeight(metricsB.heightPixels / NUM_COLUMN_IN_LAND_VERTICAL);
            }
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Directions directions = playField.getDirection(position);
                    if (directions != Directions.NONE){
                        playField.swap(position);
                        adapterListener.onChangedMoves(playField.getMoves());
                        notifyDataSetChanged();
                        if (playField.isWin())
                            adapterListener.onWin();
                    }
                }
            });
        } else {
            button = (Button) convertView;
            button.setText(String.valueOf(getItem(position)));
            if (button.getText().equals(String.valueOf(0)))
                button.setVisibility(View.INVISIBLE);
            else
                button.setVisibility(View.VISIBLE);
        }

        return button;
    }

    public void setPlayField(PlayField playField) {
        this.playField = playField;
        adapterListener.onChangedMoves(playField.getMoves());
        notifyDataSetChanged();
    }

    public void setWin() {
        playField.setWin();
        notifyDataSetChanged();
    }

    public PlayField getPlayField() {
        return playField;
    }

    public void load(SharedPreferences sharedPreferences) {
        setPlayField(playField.load(sharedPreferences));
    }

    public void saveGame(SharedPreferences sharedPreferences){
        playField.save(sharedPreferences);
    }
}
