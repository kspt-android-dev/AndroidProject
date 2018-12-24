package ru.gdcn.beastmaster64revelations.UIElements;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

public class ProportionalImageView extends android.support.v7.widget.AppCompatImageView {

    public ProportionalImageView(Context context) {
        super(context);
    }

    public ProportionalImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProportionalImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable d = getDrawable();
        if (d != null) {
//            int w = MeasureSpec.getSize(widthMeasureSpec);
//            int h = w * d.getIntrinsicHeight() / d.getIntrinsicWidth();
//            setMeasuredDimension(w, h);
//
            int h = MeasureSpec.getSize(heightMeasureSpec);
            int w = h * d.getIntrinsicWidth() / d.getIntrinsicHeight();
            setMeasuredDimension(w, h);
        }
        else super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
