package app.galentin.ru.nonograms;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Gameover extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end);
        Button end = (Button) findViewById(R.id.textEnd);
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "EastAP.ttf");
        end.setTypeface(typeFace);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.textEnd:
                        try {
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        };
        end.setOnClickListener(onClickListener);
    }
}
