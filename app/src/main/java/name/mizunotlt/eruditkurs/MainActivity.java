package name.mizunotlt.eruditkurs;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setOrientationLayout();
    }
    private void setOrientationLayout() {
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_main_land);

        } else {
            if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                setContentView(R.layout.activity_main);
            }
        }
    }

    public void onClickNewGame(View view) {
        Intent intent = new Intent(this, GameStart.class);
        startActivity(intent);
    }

    public void onClickExit(View view){
        System.exit(1);
    }

    public void onClickAbout(View view){
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }
}
