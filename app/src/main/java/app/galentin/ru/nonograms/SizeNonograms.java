package app.galentin.ru.nonograms;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.Random;

public class SizeNonograms extends AppCompatActivity {
    private int size;
    String file[][]= {{"/assets/nonograms10x10e1.txt", "/assets/nonograms10x10e2.txt", "/assets/nonograms10x10e3.txt","/assets/nonograms10x10e4.txt", "/assets/nonograms10x10e5.txt"},
            {"/assets/nonograms15x15e1.txt","/assets/nonograms15x15e2.txt","/assets/nonograms15x15e3.txt","/assets/nonograms15x15e4.txt", "/assets/nonograms15x15e5.txt"}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.size_nonograms);
        Button button10x10 = (Button) findViewById(R.id.button10x10);
        Button button15x15 = (Button) findViewById(R.id.button15x15);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.button10x10:
                        size = 0;
                        break;
                    case R.id.button15x15:
                        size = 1;
                        break;
                }
                try{
                    Intent intent = new Intent(SizeNonograms.this, Gameboard.class);
                    intent.putExtra("fileName", getRandom(file[size]));
                    startActivity(intent);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        button10x10.setOnClickListener(onClickListener);
        button15x15.setOnClickListener(onClickListener);
        Typeface typeFace=Typeface.createFromAsset(getAssets(),"EastAP.ttf");
        button10x10.setTypeface(typeFace);
        button15x15.setTypeface(typeFace);
    }

    public String getRandom(String[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }
}
