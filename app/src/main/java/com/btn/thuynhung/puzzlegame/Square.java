package com.btn.thuynhung.puzzlegame;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class Square extends android.support.v7.widget.AppCompatTextView {

    public Square(Context context) {
        super(context);
    }

    public Square(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Square(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int length = getMeasuredWidth();
        setMeasuredDimension(length, length);
    }

    public void setTextToItems(int numb) {
        if (numb == 0) setText(" ");
        else setText("" + numb);
    }
}
