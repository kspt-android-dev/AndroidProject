package app.galentin.ru.nonograms;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startButton = (Button)findViewById(R.id.playButton);
        Button rulesButton = (Button)findViewById(R.id.rulesButton);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.playButton :
                        try{
                            Intent intent = new Intent(MainActivity.this, SizeNonograms.class);
                            startActivity(intent);
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case R.id.rulesButton :
                        try{
                            Intent intent = new Intent(MainActivity.this, Rules.class);
                            startActivity(intent);
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        };
        startButton.setOnClickListener(onClickListener);
        rulesButton.setOnClickListener(onClickListener);

        TextView nameTextView=(TextView)findViewById(R.id.gameName);
        Typeface typeFace=Typeface.createFromAsset(getAssets(),"EastAP.ttf");
        nameTextView.setTypeface(typeFace);
        rulesButton.setTypeface(typeFace);
        startButton.setTypeface(typeFace);
    }
}
