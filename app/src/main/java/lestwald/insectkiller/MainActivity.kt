package lestwald.insectkiller

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startButton = findViewById<ImageButton>(R.id.startButton)
        startButton.setOnClickListener {
            intent = Intent(this, GameFieldActivity::class.java)
            startActivity(intent) }
        val leaderBoardButton = findViewById<ImageButton>(R.id.leaderboardButton)
        leaderBoardButton.setOnClickListener {
            intent = Intent(this, LeaderBoardActivity::class.java)
            startActivity(intent) }
    }

}
