package org.easyeng.easyeng;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WordSetViewActivity extends AppCompatActivity {
    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        item = getIntent().getParcelableExtra(getString(R.string.cart_extra_name));
        setContentView(R.layout.words_set_activity);
        ((TextView) findViewById(R.id.title)).setText(item.getTitle());
        ((WebView) findViewById(R.id.web_view)).loadData(item.getText(), "text/html", "utf-8");
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
