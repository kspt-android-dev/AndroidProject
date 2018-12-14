package ru.gdcn.beastmaster64revelations.UIElements;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import ru.gdcn.beastmaster64revelations.R;

public class RPGButton extends android.support.v7.widget.AppCompatButton {

    public RPGButton(Context context) {
        super(context);
        makeStyle();
    }

    public RPGButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        makeStyle();
    }

    private void makeStyle() {

        this.setBackgroundColor(this.getContext().getResources().getColor(R.color.colorButtonBackground));
        this.setTextColor(this.getContext().getResources().getColor(R.color.colorButtonText));
        this.setTextSize(18);
        LinearLayout.MarginLayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Integer margins = this.getContext().getResources().getInteger(R.integer.buttonAllMargins);
        params.rightMargin = margins;
        params.topMargin = margins;
        params.bottomMargin = margins;
        params.leftMargin = margins;
        this.setLayoutParams(params);

    }

}
