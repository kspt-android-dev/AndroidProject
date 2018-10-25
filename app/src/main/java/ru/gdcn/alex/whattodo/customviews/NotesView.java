package ru.gdcn.alex.whattodo.customviews;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NotesView extends LinearLayout {

    private TextView headerText, mainText;

    public NotesView(Context context) {
        super(context);
        this.headerText = new TextView(getContext());
        this.mainText = new TextView(getContext());
        ViewGroup.LayoutParams layoutParams = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        ((LayoutParams) layoutParams).setMargins(20, 20, 20, 20);
        setLayoutParams(layoutParams);
        setPaddingRelative(20, 20 , 20, 20);
        setBackgroundColor(Color.BLACK);
        setOrientation(VERTICAL);
    }

    public void setData(String header, String text){
        headerText.setText(header);
        headerText.setTextSize(40);
        headerText.setTextColor(Color.WHITE);
        addView(headerText);

        mainText.setText(text);
        mainText.setTextSize(20);
        mainText.setTextColor(Color.WHITE);
        addView(mainText);
    }


}
