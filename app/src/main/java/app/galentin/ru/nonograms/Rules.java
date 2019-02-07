package app.galentin.ru.nonograms;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class Rules extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rules);
        TextView head_rules = findViewById(R.id.headline_rules);
        TextView rules = findViewById(R.id.rules_text);
        rules.setMovementMethod(new ScrollingMovementMethod());
        Typeface typeFace=Typeface.createFromAsset(getAssets(),"EastAP.ttf");
        rules.setTypeface(typeFace);
        head_rules.setTypeface(typeFace);
    }
}
