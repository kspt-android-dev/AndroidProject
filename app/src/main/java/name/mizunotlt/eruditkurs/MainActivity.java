package name.mizunotlt.eruditkurs;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickNewGame(View view) {
        Intent intent = new Intent(this, GameStart.class);
        startActivity(intent);
    }

    public void onClickExit(View view){
        this.onStop();
    }

    public void onClickAbout(View view){

    }
}
