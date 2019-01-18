package lestwald.insectkiller

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class GameOverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        val difficulty = sharedPreferences.getString("difficulty", "2").toInt()
        val finalScoreTextView = findViewById<TextView>(R.id.final_score)
        val finalScore = intent.getIntExtra("finalScore", 0)
        finalScoreTextView.text = getString(R.string.your_score) + "\n" + finalScore
        val userName = findViewById<EditText>(R.id.user_name)
        val saveResultButton = findViewById<Button>(R.id.save_result_button)
        saveResultButton.setOnClickListener {
            if (userName.text.isNotEmpty()) {
                val dbName = "leaderboard$difficulty"
                addScore(this, dbName, userName.text.toString(), finalScore)
                intent = Intent(this, LeaderBoardActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}
