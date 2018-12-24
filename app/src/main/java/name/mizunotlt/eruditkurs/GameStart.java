package name.mizunotlt.eruditkurs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class GameStart extends Activity {
    EditText firtsPlayer;
    EditText secondPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_start);
        firtsPlayer = (EditText) findViewById(R.id.firstPlayer);
        secondPlayer = (EditText) findViewById(R.id.secondPlayer);
    }

    public void onClickStartGame(View view) {
        Intent intent = new Intent(this, Game.class);
        intent.putExtra("firstPlayer", firtsPlayer.getText().toString());
        intent.putExtra("secondPlayer", secondPlayer.getText().toString());
        startActivity(intent);
    }
}
