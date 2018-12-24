package com.example.checkers;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;


public class InstructionActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instruction);

        setText();

    }

    // получение текста инструкции, записанного в html формате, из ресурсов
    // и подстановка его в textView
    private void setText() {
        String htmlAsString = getString(R.string.html);
        Spanned htmlAsSpanned = Html.fromHtml(htmlAsString);
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(htmlAsSpanned);
    }
}


