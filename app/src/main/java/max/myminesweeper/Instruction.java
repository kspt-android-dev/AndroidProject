package max.myminesweeper;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;

public class Instruction extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instuction);
        setText();
    }

    private void setText() {
        String htmlAsString = getString(R.string.html);
        Spanned htmlAsSpanned = Html.fromHtml(htmlAsString);
        TextView textView = findViewById(R.id.textView);
        textView.setText(htmlAsSpanned);
    }
}